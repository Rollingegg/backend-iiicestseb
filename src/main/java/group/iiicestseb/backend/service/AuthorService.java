package group.iiicestseb.backend.service;


import group.iiicestseb.backend.vo.AuthorInfoVO;

/**
 * @author wph
 * @date 2020/03/01
 */
public interface AuthorService {

    /**
     * 作者页面所需要的作者详细信息
     * @author wph
     * @param name 作者名
     * @return 作者详细信息VO
     */
    public AuthorInfoVO getAuthorInfo(String name);
}
