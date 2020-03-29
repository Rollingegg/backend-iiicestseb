package group.iiicestseb.backend.factory;

import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.vo.author.AuthorInAffiliationVO;

/**
 * @author jh
 * @date 2020/3/29
 */
public class AuthorFactory {

    public static AuthorInAffiliationVO toAuthorVO(Author author){
        AuthorInAffiliationVO result = new AuthorInAffiliationVO(author.getId(),author.getName());
        return result;
    }
}
