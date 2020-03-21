package group.iiicestseb.backend.vo;

import group.iiicestseb.backend.entity.Conference;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.entity.Term;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 文献管理页面 文献的详细信息
 * @author wph
 */
@Data
public class PaperInfoVO {

    /**
     * 文献
     */
    private Paper paper;

    /**
     * 会议
     */
    private Conference conference;

    /**
     * 作者列表
     */
    private List<AuthorInfoVO> authorInfoList;

    /**
     * 术语列表
     */
    private List<Term> termList;

}
