package group.iiicestseb.backend.vo;

import lombok.Data;

import java.util.ArrayList;

/**
 * 作者详细信息VO
 * @author wph
 * @date 2020/3/2
 */
@Data
public class AuthorInfoVO {

    /**
     * 作者id
     */
    private Integer id;

    /**
     * 作者姓名
     */
    private String name;

    /**
     * 作者所属机构id
     */
    private Integer affiliationId;

    /**
     * 机构名
     */
    private String affiliationName;


}
