package top.webra.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.webra.pojo.Menu;

import java.util.List;

/**
 * @author webra
 */
@Mapper
@Repository
public interface MenuMapper {

    /**
     * 新增一个菜单
     * @param menu
     * @return
     */
    Integer insertMenu(Menu menu);

    /**
     * 删除一个菜单
     * @param menuId
     * @return
     */
    Integer delMenu(@Param("id") Integer menuId);

    /**
     * 更改一个菜单
     * @param menu
     * @return
     */
    Integer updMenu(Menu menu);

    /**
     * 批量将二级菜单转换为一级菜单
     * @param ids
     * @return
     */
    Integer updMenuSuperIdByIds(@Param("ids")List<Integer> ids);

    /**
     * 查找所有菜单
     * @return
     */
    List<Menu> queMenuAll();


    /**
     * 根据一级菜单id查出所有二级菜单
     * @param superId
     * @return
     */
    List<Menu> queMenuBySuperId(@Param("superId") Integer superId);

    /**
     *
     * @param id
     * @return
     */
    Menu queMenuById(@Param("id") Integer id);

}
