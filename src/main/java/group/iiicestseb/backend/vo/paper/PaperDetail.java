package group.iiicestseb.backend.vo.paper;

import group.iiicestseb.backend.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * 文献管理页面 文献的详细信息
 * @author wph
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperDetail {

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
    private Collection<Author> authorList;

    /**
     * 术语列表
     */
    private Collection<Term> termList;

    /**
     * 引用列表
     */
    private Collection<Reference> referenceList;


}
