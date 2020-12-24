package top.webra.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import top.webra.pojo.Menu;
import top.webra.pojo.User;
import top.webra.pojo.Website;
import top.webra.service.impl.MenuServiceImpl;
import top.webra.service.impl.UserServiceImpl;
import top.webra.service.impl.WebsiteServiceImpl;

import java.util.List;

/**
 * 导航栏信息传输
 * @author webra
 */
@Controller
@Slf4j
public class NavController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private WebsiteServiceImpl websiteService;


    @GetMapping("/system/nav")
    public String getSystemNav(Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.queUserByUsername(name);

        String portrait = user.getPortrait();
        model.addAttribute("portrait",portrait);
        return "system/common/nav::nav";
    }

    @GetMapping("/index/nav")
    public String getIndexNav(Model model){
        List<Menu> menus = menuService.queMenuAll();
        List<Menu> menuList = menuService.getMenu(menus);
        model.addAttribute("menus",menuList);
        Website website = websiteService.queWebsiteAll();
        model.addAttribute("blogName",website.getName());
        return "index/common/nav::nav";
    }
    @GetMapping("/index/nav/tail")
    public String getIndexNavTail(Model model){
        Website website = websiteService.queWebsiteAll();
        model.addAttribute("info",website.getInfo());
        return "index/common/nav-tail::nav-tail";
    }
}
