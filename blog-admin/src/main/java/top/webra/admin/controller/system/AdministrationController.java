package top.webra.admin.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import top.webra.constants.MesConstant;
import top.webra.constants.ResponseStateConstant;
import top.webra.pojo.*;
import top.webra.service.impl.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/system/administration")
public class AdministrationController {

    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private CommentsServiceImpl commentsService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CostomResponse costomResponse;
    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private FileHashServiceImpl fileHashService;
    @Autowired
    private RecordServiceImpl recordService;

    private String imagePath = ClassUtils.getDefaultClassLoader().getResource("static/images/").getPath();


    ////////////////////////////----- 图片管理 -----////////////////////////////
    @GetMapping("/images")
    public String toImages(){
        return "system/administration/images";
    }
    @GetMapping("/images/query")
    public String getImages(@RequestParam Integer cloud,@RequestParam Integer curr,Model model){

        PageHelper.startPage(curr,18);
        List<FileHash> fileHashs = fileHashService.queFileHashByManufacturerId(cloud);
        PageInfo<FileHash> fileHashPageInfo = new PageInfo<>(fileHashs);
        model.addAttribute("curr",curr);
        int count = fileHashPageInfo.getPages();
        model.addAttribute("count",count==0?1:count);
        model.addAttribute("images",fileHashs);

        return "system/administration/images::image";
    }

    @ResponseBody
    @PostMapping("/images/delete")
    public CostomResponse deleteImage(String imageName){
        String[] split = imageName.split("\\.");
        Integer integer = fileHashService.delFileHash(split[0]);
        if (integer==1){
            String s = imagePath + imageName;
            File filePath = new File(s);
            boolean delete = filePath.delete();
            recordService.insertRecord(new Record("图片操作","删除图片"));
            costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
            costomResponse.setMes(MesConstant.DELETE_SUCCESS);
        }else{
            costomResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
            costomResponse.setMes(MesConstant.DELETE_FAILURE);
        }
        return costomResponse;
    }

    ////////////////////////////----- 评论管理 -----////////////////////////////
    @GetMapping("/comments")
    public String toComments(){
        return "system/administration/comments";
    }

    // 显示评论
    @PostMapping("/comments/query")
    public String getComments(Integer curr,Integer articleId,Integer userId,Model model){
        PageHelper.startPage(curr,10);
        List<Comments> comments = commentsService.queCommentsByUserArticleId(articleId, userId);
        PageInfo<Comments> articlePageInfo = new PageInfo<>(comments);
        int pages = articlePageInfo.getPages();
        if (pages == 0){
            pages += 1;
        }
        model.addAttribute("curr",curr);
        model.addAttribute("count",pages);
        model.addAttribute("comments",comments);
        return "system/administration/comments::comments";
    }
    // 查询文章
    @ResponseBody
    @GetMapping("/comments/article")
    public CostomResponse getArticle(String title){
        if (null == title){
            return null;
        }
        title = title.replaceAll("'", "");
        log.info("获得前端需要搜索的文章字段: " + title);
        PageHelper.startPage(1,5);
        List<Article> articles = articleService.queArticleSearch(title,null,null);

        if (articles.isEmpty()){
            log.info("搜索结果为空");
            return null;
        }
        costomResponse.setData(articles);
        return costomResponse;
    }
    // 查询用户
    @ResponseBody
    @GetMapping("/comments/user")
    public CostomResponse getUser(String name){
        if (null == name){
            return null;
        }
        name = name.replaceAll("'", "");
        PageHelper.startPage(1,5);
        List<User> users = userService.queUserByNickname(name);
        if (users.isEmpty()){
            return null;
        }
        costomResponse.setData(users);
        return costomResponse;
    }

    @ResponseBody
    @PostMapping("/comments/delete")
    public CostomResponse delComments(@RequestParam Integer commentsId){
        Comments comments = commentsService.queCommentsById(commentsId);
        if (comments != null){
            // 一级评论
            if (0 == comments.getSuperId()){
                List<Comments> commentsList = commentsService.queCommentsBySuperId(comments.getId());
                if (!commentsList.isEmpty()){
                    List<Integer> ids = commentsService.getIds(commentsList);
                    ids.add(comments.getId());
                    commentsService.delCommentList(ids);
                }else {
                    commentsService.delComment(commentsId);
                }
                recordService.insertRecord(new Record("评论管理","删除一级评论"));
            }else {
                recordService.insertRecord(new Record("评论管理","删除二级评论"));
                commentsService.delComment(commentsId);
            }
            costomResponse.setCode(200);
            costomResponse.setMes("删除成功");
        }else {
            costomResponse.setCode(602);
            costomResponse.setMes("暂无该评论,请重新选择");
        }
        return costomResponse;
    }


    ////////////////////////////----- 菜单管理 -----////////////////////////////
    @GetMapping("/menu")
    public String toMenu(){
        return "system/administration/menu";
    }

    // 显示菜单
    @GetMapping("/menu/query")
    public String getMenus(Model model){
        List<Menu> menusList = menuService.queMenuAll();

        List<Map<String, Object>> menusSuper = menuService.getMenusSuper(menusList);
        model.addAttribute("menusSuper",menusSuper);

        List<Menu> menus = menuService.menusSort(menusList);
        model.addAttribute("menus",menus);
        return "system/administration/menu::getMenu";
    }

    // 更新/添加菜单
    @ResponseBody
    @PostMapping("/menu/update")
    public CostomResponse updateMenu(Menu menu){
        if (menu.getId().equals(0)){
            menuService.insertMenu(menu);
            recordService.insertRecord(new Record("菜单管理","添加菜单："+menu.getName()));
            costomResponse.setCode(200);
            costomResponse.setMes("添加菜单成功!");
        }else {
            menuService.updMenu(menu);
            recordService.insertRecord(new Record("菜单管理","更新菜单："+menu.getName()));
            costomResponse.setCode(200);
            costomResponse.setMes("更新菜单成功!");
        }
        return costomResponse;
    }
//      失效
//    /**
//     * 必须提供名称和地址
//     * @param menu
//     * @return
//     */
//    @ResponseBody
//    @PostMapping("/menu/add")
//    public CostomResponse addMenu(Menu menu){
//        System.out.println(menu);
//        menuService.insertMenu(menu);
//        costomResponse.setCode(200);
//        costomResponse.setMes("添加成功");
//        return costomResponse;
//    }

    // 删除菜单
    @ResponseBody
    @PostMapping("/menu/delete")
    public CostomResponse deleteMenu(Integer menuId, Integer superId){
        Menu menu1 = menuService.queMenuById(menuId);
        if (superId.equals(0)) {
            List<Menu> menus = menuService.queMenuBySuperId(menuId);
            for (Menu menu : menus) {
                System.out.println(menu.getName());
            }
            if (!menus.isEmpty()) {
                ArrayList<Integer> integers = new ArrayList<>();
                for (Menu menu : menus) {
                    System.out.println(menu.getId() + menu.getName());
                    integers.add(menu.getId());
                }
                Integer integer = menuService.updMenuSuperIdByIds(integers);
                System.out.println(integer);
                recordService.insertRecord(new Record("菜单管理","删除一级菜单："+menu1.getName()));
                costomResponse.setMes("删除一级菜单成功,二级菜单自动变更为一级菜单");
            }
        }else {
            recordService.insertRecord(new Record("菜单管理","删除二级菜单："+menu1.getName()));
            costomResponse.setMes("删除二级菜单成功");
        }
        menuService.delMenu(menuId);
        costomResponse.setCode(200);

        return costomResponse;
    }

    ////////////////////////////----- 首页管理 -----////////////////////////////
    @GetMapping("/index")
    public String index(){
        return "system/administration/index";
    }
    @GetMapping("/index/rotograms")
    public String getRotograms(Model model){
        List<Article> articles = articleService.queArticleAllRotograms();
        model.addAttribute("articles",articles);
        if (articles.isEmpty()){
            model.addAttribute("code",ResponseStateConstant.DATA_SEARCH_NULL);
        }
        return "system/administration/index::rotograms";
    }
    @PostMapping("/index/article")
    public String getArticle(@RequestParam String sea, @RequestParam Integer curr, Model model){
        model.addAttribute("curr",curr);
        if (sea.isEmpty()){
            PageHelper.startPage(curr,10);
            List<Article> articles = articleService.queArticleSearch(null,"发表",null);
            PageInfo<Article> articlePageInfo = new PageInfo<>(articles);
            model.addAttribute("articles",articles);
            model.addAttribute("count",articlePageInfo.getPages());
        }else {
            PageHelper.startPage(curr,10);
            List<Article> articles = articleService.queArticleSearch(sea,null,null);
            PageInfo<Article> articlePageInfo = new PageInfo<>(articles);
            model.addAttribute("articles",articles);
            model.addAttribute("count",articlePageInfo.getPages());
        }
        return "system/administration/index::seaArticle";
    }
    @ResponseBody
    @PostMapping("/index/add/rotograms")
    public CostomResponse addRotograms(boolean add,Integer articleId){
        Article article = articleService.queArticleById(articleId);
        if (article == null){
            costomResponse.setCode(611);
            costomResponse.setMes("非法请求!!");
        }else if (add){
            // 判断轮播图个数
            Integer integer = articleService.queArticleRotogramsTotal();
            if (integer >= 5){
                costomResponse.setCode(603);
                costomResponse.setMes("轮播图已达五个,若再添加,请酌情删除");
            }else{
                if ("发表".equals(article.getState())) {
                    article.setRotograms(1);
                    articleService.updArticle(article);
                    costomResponse.setCode(200);
                    costomResponse.setMes(MesConstant.UPDATE_SUCCESS);
                    recordService.insertRecord(new Record("轮播图管理","文章："+ article.getTitle() +"添加到轮播图中"));
                }else{
                    costomResponse.setCode(611);
                    costomResponse.setMes("请在网页进行操作,请勿非法操作做");
                }
            }
        }else {
            article.setRotograms(2);
            articleService.updArticle(article);
            costomResponse.setCode(200);
            costomResponse.setMes(article.getTitle() + " 从轮播图中删除");
            recordService.insertRecord(new Record("轮播图管理","文章："+ article.getTitle() +"从轮播图中删除"));
        }
        return costomResponse;
    }
}













