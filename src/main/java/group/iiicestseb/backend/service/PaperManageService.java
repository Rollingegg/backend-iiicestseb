package group.iiicestseb.backend.service;


/**
 * @author jh
 * @date 2020/2/22
 */
public interface PaperManageService {



    //public void addPaper(PaperForm paperForm);

    /**
     * 通过文献id删除文献
     * @param id 文献id
     */
    public void deleteById(int id);

    /**
     * 通过文献名删除文献
     * @param name 文献名
     */
    public void deleteByName(String name);


}
