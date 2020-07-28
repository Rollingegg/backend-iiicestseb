package group.iiicestseb.backend.utils;

import com.alibaba.fastjson.JSONObject;
import group.iiicestseb.backend.entity.Crawler;
import group.iiicestseb.backend.service.CrawlerService;
import group.iiicestseb.backend.vo.crawler.CrawlerTaskVO;
import lombok.Cleanup;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * @author jh
 * @date 2020/7/12
 */
@Component("Python")
@EnableAsync
public class PyUtil {

    public static final String TOO_LONG_TO_UPDATE_RECOMMEND_ERROR = "经过4h仍未跑完推荐更新";
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String TEST_MAIN_PY = "test_ieee.py";
    public static final String MAIN_PY = "ieee_spider.py";
    public static final String TEMP_PY_FILE = "temp.py";
    public static final String TEMP_JSON_RESULT = "temp_result.json";

    private static final String SEP = System.getProperty("file.separator");
    private static String EXE;
    private static final String PYPATH = "python";

    private static PyUtil Instance;

    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    // 暂时只允许同时进行一个爬虫任务
    private static final CrawlerTask currentTask = new CrawlerTask();

    @Resource
    private CrawlerService crawlerService;

    public PyUtil() {
        final String property = System.getProperty("os.name");
        if (property.contains("indows")) {
            EXE = "python";
        } else {
            EXE = "python3";
        }
        Instance = this;
    }

    public static CrawlerTaskVO getCurrentTask() {
        CrawlerTaskVO taskVO = new CrawlerTaskVO();
        if (currentTask.state.equals(CrawlerTask.STATE.Free) || currentTask.crawler == null) {
            return null;
        }
        taskVO.setCrawlerId(currentTask.crawler.getCrawlerId());
        taskVO.setState(currentTask.state.value);
        taskVO.setTotal(currentTask.currentTotal);
        taskVO.setCurrent(currentTask.currentI);
        return taskVO;
    }

    /**
     * python爬虫任务途中输出的flag，处理时以 startWith("[crawler]") 判断
     * 标准格式为 [crawler]flag : xxx，如 print("[crawler]total : 3000")
     * <p>
     * 自定义标记 : "[crawler]"
     * 开始 : "start"
     * 结束 : "end"
     * 待爬总文献数："paperNum : total" // 冒号和斜杠左右有空格
     * 获取子会议列表 : "subConferenceNum : total" // 同上
     * 正在爬取第几个： "current : i" // 爬取子会议和爬取文献时都是这个输出，i 从 1 开始
     * 异常 : "exception : 异常信息" // todo: 各种异常信息等遇到了再处理
     * 进程心跳 : "heartbeat : 消息" // 如果要附加信息就在后面加
     * 进程被取消 : "cancel"
     * <p>
     * 未知标记 : "unknown" // python里的误输出，注意出现这个只是因为python里写错了
     */
    public enum FLAG {
        //
        Symbol("[crawler]"),
        Start("start"),
        End("end"),
        PaperNum("paperNum"),
        SubConferenceNum("subConferenceNum"),
        Current("current"),
        Exception("exception"),
        Heartbeat("heartbeat"),
        Cancel("cancel"),
        Timeout("timeout"),
        Unknown("unknown");

        public final String value;

        /**
         * 这是 Symbol 的偏移量
         */
        public static final int OFFSET = Symbol.value.length();

        FLAG(String value) {
            this.value = value;
        }

        public static FLAG getType(String msg) {
            if (msg == null) {
                return null;
            }
            if (!msg.startsWith(FLAG.Symbol.value)) {
                System.out.println("未定义的输出：" + msg);
                throw new PythonException("未定义的输出：" + msg);
            }
            for (FLAG type : FLAG.values()) {
                if (msg.substring(OFFSET).startsWith(type.value)) {
                    return type;
                }
            }
            return Unknown;
        }
    }

    /**
     * 还原环境
     */
    public static void reset() {
        currentTask.reset();
    }

    /**
     * 杀死当前进行的爬虫任务
     */
    public static void killCurrent() {
        try {
            Instance.notifyPython("cancel\n");
        } catch (IOException e) {
            throw new PythonException("关闭爬虫线程失败");
        }
    }

    private void notifyPython(String msg) throws IOException {
        if (currentTask.state == CrawlerTask.STATE.Free) {
            return;
        }
        currentTask.process.getOutputStream().write(msg.getBytes());
        currentTask.process.getOutputStream().flush();
    }

    public static void checkCrawler() throws Exception {
        Instance.checkCrawlerInner();
    }

    /**
     * 检查当前状态，如果没有进行中的任务，则取下一个等待中的任务进行
     * //这里因为要更新状态所以不需要 @Transactional(rollbackFor = Exception.class, noRollbackFor = PythonException.class)
     */
    public void checkCrawlerInner() throws Exception {
        if (currentTask.state != CrawlerTask.STATE.Free) {
            return;
        }
        currentTask.reset();
        // 获取下一个任务
        Crawler crawler;
        if ((crawler = crawlerService.getNextTask()) == null) {
            return;
        }
        crawler.setStartTime(LocalDateTime.now());
        crawler.setState(Crawler.STATE.Running.value);
        currentTask.crawler = crawler;
        // 保存数据
        saveCrawlerState();
        String log;
        boolean success;
        try {
            Future<String> process = Instance.startNewCrawler(MAIN_PY, currentTask.crawler);
            log = process.get(4, TimeUnit.HOURS);
            success = currentTask.guarder.get();
        } catch (TimeoutException e) {
            currentTask.crawler.setState(Crawler.STATE.Fail.value);
            saveCrawlerState();
            currentTask.reset();
            throw new PythonException("爬虫执行超时: 4h");
        } catch (ExecutionException e) {
            if (e.getCause() instanceof PythonException) {
                String msg = e.getCause().getMessage();
                if ("爬虫任务被手动取消".equals(msg)) {
                    currentTask.crawler.setState(Crawler.STATE.Canceled.value);
                    saveCrawlerState();
                    currentTask.reset();
                    System.out.println(e.getMessage());
                    throw (PythonException) e.getCause();
                }
                if ("爬虫任务心跳超时".equals(msg)) {
                    currentTask.crawler.setState(Crawler.STATE.Fail.value);
                    saveCrawlerState();
                    currentTask.reset();
                    System.out.println(e.getMessage());
                    throw (PythonException) e.getCause();
                }
            }
            currentTask.crawler.setState(Crawler.STATE.Fail.value);
            saveCrawlerState();
            System.out.println(e.getMessage());
            currentTask.reset();
            throw new Exception("爬虫执行失败");
        }
        if (success) {
            // 保存日志
            crawlerService.saveLog(currentTask.crawler.getCrawlerId(), log);
            saveCrawlerState();
            // 解析并导入爬取的数据
            analyzeJson();
        }
        // 删除临时文件、还原环境
        currentTask.reset();
    }

    /**
     * 开启新的爬虫任务
     */
    @Async
    public Future<String> startNewCrawler(String filename, Crawler crawler) throws ExecutionException, InterruptedException {
        currentTask.crawler = crawler;
        currentTask.state = CrawlerTask.STATE.Starting;
        // 创建临时文件
        createTempFile(filename);
        // 执行并监控任务
        String[] cmdArgs = new String[]{EXE, "-u", TEMP_PY_FILE,
                "conferences=" + currentTask.crawler.getConferenceName(),
                "start_year=" + currentTask.crawler.getStartYear().toString(),
                "end_year=" + currentTask.crawler.getEndYear().toString(),
                "out=" + TEMP_JSON_RESULT};
        FutureTask<String> traceTask = new FutureTask<>(() -> {
            currentTask.process = exec(cmdArgs);
            return trace(currentTask);
        });
        currentTask.tracer = traceTask;
        // 返回 true 代表爬虫运行完成
        FutureTask<Boolean> timerTask = new FutureTask<>(() -> {
            while (currentTask.state != CrawlerTask.STATE.Free) {
                TimeUnit.SECONDS.sleep(1);
                currentTask.clock();
                System.out.println(DateUtil.toStringDateTime(LocalDateTime.now()) + " timer " + currentTask.heartbeat);
                if (currentTask.crawler == null) {
                    return false;
                }
                int waitTime;
                switch (currentTask.state) {
                    case GetSubConference:
                        waitTime = 120;
                        break;
                    case AnalyzingJson:
                        return true;
                    default:
                        waitTime = 30;
                }
                if (currentTask.heartbeat >= waitTime) {
                    notifyPython("timeout\n");
                    return false;
                }
            }
            return true;
        });
        currentTask.guarder = timerTask;
        threadPool.submit(timerTask);
        threadPool.submit(traceTask);
        String log = traceTask.get();
        return new AsyncResult<>(log);
    }

    /**
     * 从jar包里提取并创建临时文件
     */
    private void createTempFile(String filename) {
        ClassPathResource pyFile = new ClassPathResource(PYPATH + SEP + filename);
        File temp = new File(TEMP_PY_FILE);
        System.out.println("创建临时文件");
        try {
            Files.copy(pyFile.getInputStream(), temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new PythonException("创建临时py文件失败");
        }
    }

    /**
     * 启动爬虫进程，标准命令行格式为：
     * python ieee_spider.py conferences=['ase','icse',....] start_year=xxxx end_year=xxxx out=xxx.json
     * -u 是取消python的输出缓存
     *
     * @param cmdArgs 命令行参数，注意包含最开始的 python
     * @return 执行的进程
     */
    public static Process exec(String[] cmdArgs) {
        System.out.println("开始执行");
        Process process;
        try {
            process = new ProcessBuilder(cmdArgs).redirectErrorStream(true).start();
        } catch (IOException ioException) {
            throw new PythonException("启动爬虫任务失败");
        }
        return process;
    }

    /**
     * 追踪爬虫任务
     *
     * @param task 爬虫任务
     * @return 日志
     * @throws IOException 这个异常其实不会被抛出，是 Lombok 的 @CleanUp 需要的
     */
    private String trace(CrawlerTask task) throws IOException {
        @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(task.process.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        try {
            String msg;
            loop:
            while ((msg = reader.readLine()) != null) {
                FLAG flag = FLAG.getType(msg);
                if (!msg.startsWith(FLAG.Symbol.value)) {
                    System.out.println("未定义的输出：" + msg);
                    continue;
                }
                task.heartbeat();
                sb.append(DateUtil.toStringDateTime(LocalDateTime.now())).append(" ")
                        .append(msg).append(System.lineSeparator());
                System.out.println(DateUtil.toStringDateTime(LocalDateTime.now()) + " " + msg);
                switch (flag) {
                    case Start:
                        task.currentI = 0;
                        task.state = CrawlerTask.STATE.Starting;
                        break;
                    case End:
                        task.state = CrawlerTask.STATE.AnalyzingJson;
                        break loop;
                    case PaperNum:
                        task.state = CrawlerTask.STATE.GetPaper;
                        task.currentTotal = Integer.valueOf(msg.split(" : ")[1]);
                        break;
                    case SubConferenceNum:
                        task.state = CrawlerTask.STATE.GetSubConference;
                        task.currentTotal = Integer.valueOf(msg.split(" : ")[1]);
                        break;
                    case Current:
                        task.currentI = Integer.valueOf(msg.split(" : ")[1]);
                        break;
                    case Cancel:
                        task.state = CrawlerTask.STATE.Free;
                        throw new PythonException("爬虫任务被手动取消");
                    case Timeout:
                        task.state = CrawlerTask.STATE.Free;
                        throw new PythonException("爬虫任务心跳超时");
                    case Exception:
                    case Heartbeat:
                    case Symbol:
                    case Unknown:
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new PythonException("执行py脚本失败");
        }
        return sb.toString();
    }

    /**
     * 解析并导入数据
     */
    private void analyzeJson() {
        JSONObject result = JSONUtil.analyzeTempJson(TEMP_JSON_RESULT);
        currentTask.crawler.setTotalCount(result.getInteger(JSONUtil.RESULT_KEY.TotalCount.value));
        currentTask.crawler.setSuccessCount(result.getInteger(JSONUtil.RESULT_KEY.SuccessCount.value));
        currentTask.crawler.setExistedCount(result.getInteger(JSONUtil.RESULT_KEY.ExistedCount.value));
        currentTask.crawler.setErrorCount(result.getInteger(JSONUtil.RESULT_KEY.ErrorCount.value));
        currentTask.crawler.setState(Crawler.STATE.Finished.value);
        currentTask.crawler.setEndTime(LocalDateTime.now());

        crawlerService.updateCrawler(currentTask.crawler);
    }

    /**
     * 保存当前任务的状态信息
     */
    public void saveCrawlerState() {
        crawlerService.updateCrawler(currentTask.crawler);
    }

    /**
     * 在调用Python过程中出现的异常
     */
    public static class PythonException extends RuntimeException {

        public PythonException() {
            super();
        }

        public PythonException(String message) {
            super(message);
        }

    }

    @Data
    public static class CrawlerTask {
        private Process process = null;
        private Crawler crawler = null;
        private Integer currentI = 0;
        private Integer currentTotal = 0;
        private STATE state = STATE.Free;

        private FutureTask<Boolean> guarder = null;
        private FutureTask<String> tracer = null;

        private Integer heartbeat = 0;

        public void clock() {
            this.heartbeat++;
        }

        public void heartbeat() {
            this.heartbeat = 0;
        }

        public enum STATE {
            Free("无任务"),
            Starting("启动中"),
            GetPaper("爬取论文中"),
            GetSubConference("爬取子会议元数据中"),
            AnalyzingJson("爬取完毕，解析并导入数据中");

            public final String value;

            STATE(String value) {
                this.value = value;
            }

        }

        /**
         * 删除临时文件并还原环境
         */
        private void reset() {
            try {
                crawler = null;
                if (process != null) {
                    process.getInputStream().close();
                    process.getOutputStream().close();
                    process.getErrorStream().close();
                    process.destroyForcibly();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new PythonException("关闭爬虫线程失败");
            }
            if (tracer != null) tracer.cancel(true);
            if (guarder != null) guarder.cancel(true);
            threadPool.shutdown();
            threadPool = Executors.newCachedThreadPool();
            state = STATE.Free;
            currentI = 0;
            currentTotal = 0;
            heartbeat = 0;
            try {
                Files.deleteIfExists(Paths.get(TEMP_PY_FILE));
                Files.deleteIfExists(Paths.get(TEMP_JSON_RESULT));
            } catch (IOException e) {
                throw new PythonException("删除临时文件失败");
            }
        }

    }

}
