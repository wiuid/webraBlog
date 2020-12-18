package top.webra.admin.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.webra.pojo.*;
import top.webra.service.impl.*;
import top.webra.utils.DateUtil;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author webra
 */
@Slf4j
@Controller()
@RequestMapping(value = "/system")
public class DashboardController {


    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private CommentsServiceImpl commentsService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RecordServiceImpl recordService;


    @RequestMapping("/login")
    public String login(){
        return "system/login";
    }

    @RequestMapping("/dashboard")
    public String dashboard(Model model){
        User user = userService.queUserById(1);
        model.addAttribute("articleTotal", articleService.queArticleTotal());
        model.addAttribute("commentsTotal",commentsService.queCommentsTotal());
        model.addAttribute("viewsTotal",articleService.queArticleViewsTotal());
        model.addAttribute("createTime",DateUtil.getCreateSite(user.getCreateTime()));

        return "system/dashboard/dashboard";
    }


    @GetMapping("/dashboard/articles")
    public String getArticle(Model model) {
        PageHelper.startPage(1,5);
        List<Article> articles = articleService.queArticleSearch(null,"发表",null);
        model.addAttribute("articles",articles);
        return "system/dashboard/dashboard::articles";
    }
    @GetMapping("/dashboard/comments")
    public String getComments(Model model){
        PageHelper.startPage(1,5);
        List<Comments> comments = commentsService.queCommentsByUserArticleId(null, null);
        System.out.println(comments);
        model.addAttribute("comments",comments);
        return "system/dashboard/dashboard::comments";
    }


    @GetMapping("/dashboard/record")
    public String getRecord(@RequestParam Integer curr,Model model){
        PageHelper.startPage(1,5);
        List<Record> records = recordService.queRecordAll();
        model.addAttribute("records",records);

        System.out.println("执行者..................");

        PageHelper.startPage(curr,20);
        List<Record> recordsAll = recordService.queRecordAll();
        PageInfo<Record> recordPageInfo = new PageInfo<>(recordsAll);
        model.addAttribute("recordsAll",recordsAll);
        model.addAttribute("curr",curr);
        model.addAttribute("count",recordPageInfo.getPages());
        return "system/dashboard/dashboard::records";
    }


}
