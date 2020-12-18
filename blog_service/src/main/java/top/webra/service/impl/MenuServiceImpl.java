package top.webra.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.webra.mapper.MenuMapper;
import top.webra.pojo.Menu;
import top.webra.service.MenuService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public Integer insertMenu(Menu menu) {
        return menuMapper.insertMenu(menu);
    }

    @Override
    public Integer delMenu(Integer menuId) {
        return menuMapper.delMenu(menuId);
    }

    @Override
    public Integer updMenu(Menu menu) {
        return menuMapper.updMenu(menu);
    }

    @Override
    public Integer updMenuSuperIdByIds(List<Integer> ids) {
        return menuMapper.updMenuSuperIdByIds(ids);
    }

    @Override
    public List<Menu> queMenuAll() {
        return menuMapper.queMenuAll();
    }

    @Override
    public List<Menu> queMenuBySuperId(Integer superId) {
        return menuMapper.queMenuBySuperId(superId);
    }

    @Override
    public Menu queMenuById(Integer id) {
        return menuMapper.queMenuById(id);
    }

    @Override
    public List<Map<String, Object>> getMenusSuper(List<Menu> menus) {
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getSuperId().equals(0)){
                Map<String, Object> stringObjectHashMap = new HashMap<>();
                stringObjectHashMap.put("id",menu.getId());
                stringObjectHashMap.put("text",menu.getName());
                maps.add(stringObjectHashMap);
            }else {
                break;
            }
        }
        return maps;
    }

    @Override
    public List<Menu> menusSort(List<Menu> menus) {
        List<Menu> menuList = new ArrayList<>();
        List<Menu> temporaryList = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getSuperId().equals(0)){
                menuList.add(menu);
                temporaryList.add(menu);
            }else {
                for (Menu menu1 : temporaryList) {
                    if (menu.getSuperId().equals(menu1.getId())){
                        int index = temporaryList.indexOf(menu1);
                        if (temporaryList.size() - 1  == index ){
                            menuList.add(menu);
                        }else {
                            Menu menu2 = temporaryList.get(index + 1);
                            int i = menuList.indexOf(menu2);
                            menuList.add(i,menu);
                        }
                    }
                }
            }
        }
        return menuList;
    }

    @Override
    public List<Menu> getMenu(List<Menu> menus) {
        List<Menu> menuArrayList = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getSuperId().equals(0)) {
                menuArrayList.add(menu);
            }else {
                for (Menu menu1 : menuArrayList) {
                    if (menu1.getId().equals(menu.getSuperId())) {
                        List<Menu> children = menu1.getChildren();
                        if (children==null) {
                            ArrayList<Menu> menuChildrenList = new ArrayList<>();
                            menu1.setChildren(menuChildrenList);
                        }
                        menu1.getChildren().add(menu);
                        break;
                    }
                }
            }
        }
        return menuArrayList;
    }


}
