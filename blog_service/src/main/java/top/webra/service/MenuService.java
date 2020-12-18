package top.webra.service;

import org.apache.ibatis.annotations.Param;
import top.webra.pojo.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {

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

    /**
     * 获取所有一级菜单
     * @param menus
     * @return
     */
    List<Map<String,Object>> getMenusSuper(List<Menu> menus);

    /**
     * 对列表进行排序 一级菜单和二级菜单一一对应
     * @param menus
     * @return
     */
    List<Menu> menusSort(List<Menu> menus);

    /**
     * 首页菜单父子嵌套排序
     * @param menus
     * @return
     */
    List<Menu> getMenu(List<Menu> menus);
}
