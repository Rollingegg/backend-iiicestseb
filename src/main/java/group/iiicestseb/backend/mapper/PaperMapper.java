package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Paper;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface PaperMapper {
    /**
     * 通过id删除文献
     * @param id 文献id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入文献
     * @param record 文献id
     * @return
     */
    int insert(Paper record);

    /**
     * 检测空值的 插入文献
     * @param record
     * @return
     */
    int insertSelective(Paper record);

    /**
     * 通过id选择文献
     * @param id 文献id
     * @return 文献实体
     */
    Paper selectByPrimaryKey(Integer id);

    /**
     * 检测空值的 更新文献
     * @param record 文献实体
     * @return
     */
    int updateByPrimaryKeySelective(Paper record);

    /**
     * 更新文献
     * @param record 文献实体
     * @return
     */
    int updateByPrimaryKey(Paper record);
}