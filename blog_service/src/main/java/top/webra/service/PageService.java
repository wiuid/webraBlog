package top.webra.service;

import org.apache.ibatis.annotations.Param;
import top.webra.pojo.Page;

import java.util.List;

public interface PageService {

    /**
     * 新增一个页面
     * @param page
     * @return
     */
    Integer insertPage(Page page);

    /**
     * 删除页面
     * @param pageId
     * @return
     */
    Integer delPage(@Param("id") Integer pageId);

    /**
     * 更新页面信息
     * @param page
     * @return
     */
    Integer updPage(Page page);

    /**
     * 根据id查询页面,必须携带页面内容
     * @param pageId
     * @return
     */
    Page quePageById(@Param("id") Integer pageId);
    /**
     * 根据Name查询页面,必须携带页面内容
     * @param pageName
     * @return
     */
    Page quePageByName(@Param("name") String pageName);

    /**
     * 查询所有页面(可不返回页面内容)
     * @return
     */
    List<Page> quePageAll();

}
