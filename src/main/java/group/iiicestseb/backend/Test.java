
package group.iiicestseb.backend;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author jh
 */
public class Test {

    public enum FLAG {
        //
        Symbol("[crawler]"),
        Start("start"),
        End("end"),
        Total("total"),
        Current("current"),
        Exception("exception"),
        Heartbeat("heartbeat");

        public final String value;

        public static final int OFFSET = 9;

        FLAG(String value) {
            this.value = value;
        }

        public static FLAG getType(String flag) {
            return FLAG.valueOf(flag);
        }
    }

    static class C {
        public void print(String s) {
            System.out.println(s);
        }
    }

    public static Integer i = 0;

    public static void main(String[] args) throws Exception {
//        Process p = new ProcessBuilder("python temp.py").start();
//        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        ExecutorService executorService = Executors.newCachedThreadPool();
        FutureTask<String> task1 = new FutureTask<>(() -> {
            int i = 0;
            while (i < 10) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("task1 : " + i);
                i++;
            }
            System.out.println("task1 finished");
            return "task1 finished";
        });
        FutureTask<String> task2 = new FutureTask<>(() -> {
            int i = 0;
            while (i < 10) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("task2 : " + i);
                i++;
            }
            System.out.println("task2 finished");
            return "task2 finished";
        });
        executorService.submit(task1);
        executorService.submit(task2);
        System.out.println(task1.get(4, TimeUnit.SECONDS));
//        BiFunction<C, Collection<C>, Boolean> c = (o1, o2) -> o2.contains(o1);
//        BiPredicate<C, Collection<C>> p = (o, co) -> co.contains(o);
//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        list.add("4");
//        System.out.println(list.subList(0, 3));
//        Collection<Vertex> vertices = new LinkedList<>();
//        Vertex v1 = new Vertex("1", Vertex.TYPE.Paper);
//        v1.setSize(0.5);
//        Vertex v2 = new Vertex("2", Vertex.TYPE.Paper);
//        v2.setSize(0.7);
//        vertices.add(v1);
//        vertices.add(v2);
//        vertices.removeIf(v -> v.getId().equals(v1.getId()));
//        System.out.println(vertices);
    }

}

