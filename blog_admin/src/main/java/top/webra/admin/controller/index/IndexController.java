package top.webra.admin.controller.index;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.webra.admin.bean.CustomResponse;
import top.webra.constants.*;
import top.webra.pojo.*;
import top.webra.service.impl.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Controller
public class IndexController {

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private CustomResponse customResponse;

    @Autowired
    private WebsiteServiceImpl websiteService;

    @Autowired
    private LabelServiceImpl labelService;
    @Autowired
    private CommentsServiceImpl commentsService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ArticleLabelServiceImpl articleLabelService;
    @Autowired
    private ClassificationServiceImpl classificationService;
    @Autowired
    private PageServiceImpl pageService;
    @Autowired
    private SetServiceImpl setService;




    ////////////////////////////----- 首页 -----////////////////////////////

    /**
     * 首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String index(Model model){
        String blogName = websiteService.queWebsiteAll().getName();
        model.addAttribute("blogName","首页 - "+blogName);
        List<Set> sets = setService.queSet();
        PageHelper.startPage(1,sets.get(0).getIndexArticleNumber());
        List<Article> articles = articleService.queArticleSearch(null, "发表", null);
        model.addAttribute("articlesList",articles);
        return "index/index";
    }

    /**
     * 个人信息及标签云
     * @param model
     * @return
     */
    @GetMapping("/index/info/tag")
    public String getInfoTag(Model model){

        User user = userService.queUserById(1);

        model.addAttribute("portrait",user.getPortrait());
        model.addAttribute("nickname",user.getNickname());

        List<Label> labels = labelService.queLabelAll();
        model.addAttribute("tags",labels);

        List<Classification> classifications = classificationService.queClassificationAll();
        model.addAttribute("classifys",classifications);
        return "index/index::infoTag";
    }

    @GetMapping("/index/rotograms")
    public String getRotograms(Model model){
        List<Article> articles = articleService.queArticleAllRotograms();
        model.addAttribute("articles",articles.isEmpty() ? articleService.getRotograms() : articles);
        return "index/index::rotograms";
    }

    ////////////////////////////----- 归档页面 -----////////////////////////////

    /**
     * 归档
     * @param model
     * @return
     */
    @RequestMapping(value = "/page/archive" , method = RequestMethod.GET)
    public String archive(Model model){
        String blogName = websiteService.queWebsiteAll().getName();
        model.addAttribute("blogName","归档 - "+blogName);
        List<Article> articles = articleService.queArticleSearch(null, "发表", null);
        List<Archive> archiveList = articleService.getArchiveList(articles);
        model.addAttribute("years",archiveList);
        return "index/archive";
    }
    @GetMapping("/page/{pageName}")
    public String queryPage(@PathVariable String pageName,Model model){
        String blogName = websiteService.queWebsiteAll().getName();
        model.addAttribute("blogName",pageName+" - "+blogName);
        Page page = pageService.quePageByName(pageName);
        model.addAttribute("code",page==null?ResponseStateConstant.RESPONSE_FAILURE:ResponseStateConstant.RESPONSE_SUCCESS);
        model.addAttribute("name",page==null?"空页面":page.getName());
        model.addAttribute("text",page==null?null:page.getText());
        return "index/page";
    }


    ////////////////////////////----- 文章页 -----////////////////////////////

    /**
     * 文章页
     * @param articleId
     * @param model
     * @return
     */
    @RequestMapping(value = "/article/{articleId}" , method = RequestMethod.GET)
    public String article(@PathVariable Integer articleId,Model model){
//        Object webraId = session.getAttribute("webraId");
//        log.info("session中添加的信息:"+webraId);
//        // 未登录用户显示 anonymousUser
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        log.info("当前登录的用户信息:" + principal.toString() );

        Article article = articleService.queArticleById(articleId);
        // 文章存在
        if (article!=null){
            // 获取该文章的所有标签
            model.addAttribute("code",ResponseStateConstant.RESPONSE_SUCCESS);
            List<ArticleLabel> articleLabels = articleLabelService.queArticleLabelByArticleId(articleId);
            List<Integer> labelIds = articleLabelService.getLabelIds(articleLabels);
            if (!labelIds.isEmpty()){
                List<Label> labels = labelService.queLabelByIds(labelIds);
                model.addAttribute("labels",labels);
            }else{
                // 该文章没有赋予标签
                model.addAttribute("codeLabel",ResponseStateConstant.RESPONSE_FAILURE);
            }

            articleService.updArticleViews(articleId);
            String blogName = websiteService.queWebsiteAll().getName();
            model.addAttribute("blogName",article.getTitle()+" - "+blogName);
            model.addAttribute("article",article);
        }else {
            model.addAttribute("code",ResponseStateConstant.RESPONSE_FAILURE);
        }
        return "index/article";
    }

    /**
     * 文章页评论区
     * @param articleId
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/article/comments/{articleId}")
    public String articleComments(@PathVariable Integer articleId,HttpSession session,Model model){
        // log.info("进入了获取评论的方法");
        List<Comments> comments = commentsService.queCommentsByArticleId(articleId);
        // log.info("获取到的comments数据:" + comments);
        // 提取的评论处理之后返回给model

        Article article = articleService.queArticleById(articleId);
        if (article != null){
            model.addAttribute("code",ResponseStateConstant.RESPONSE_SUCCESS);
            if (comments.isEmpty()){
                model.addAttribute("isComments",0);
                model.addAttribute("comments",MesConstant.COMMENTS_NULL);
            }else {
                List<Comments> commentsList = commentsService.commentsHandle(comments);
                model.addAttribute("isComments",1);
                model.addAttribute("comments",commentsList);
            }
            Object webraId = session.getAttribute("webraId");
            if (webraId != null){
                if (webraId instanceof Integer){
                    Integer userId = (Integer)webraId;
                    User user = userService.queUserById(userId);
                    log.info("获取评论时获取到用户:" + user.getNickname());
                    model.addAttribute("userId",userId);
                    model.addAttribute("userNickname",user.getNickname());
                }
            }else {
                model.addAttribute("userId",0);
            }
        }else {
            model.addAttribute("code",ResponseStateConstant.RESPONSE_FAILURE);
        }



//        Object webraId = session.getAttribute("webraId");
//        if (webraId == null){
//            // 获取评论时没有获取到用户
//            model.addAttribute("code",ResponseStateConstant.RESPONSE_FAILURE);
//            model.addAttribute("userId",0);
//            model.addAttribute("userNickname","notUser");
//        }else {
//            if (webraId instanceof Integer){
//                Integer userId = (Integer)webraId;
//                User user = userService.queUserById(userId);
//                log.info("获取评论时获取到用户:" + user.getNickname());
//                model.addAttribute("code",ResponseStateConstant.RESPONSE_SUCCESS);
//                model.addAttribute("userId",userId);
//                model.addAttribute("userNickname",user.getNickname());
//            }
//        }

        return "index/article::comments";
    }


    /**
     * 文章页 发布评论
     * @param user
     * @param comments
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping("/article/comment")
    public CustomResponse articleComments(User user, Comments comments, HttpSession session){
        Object webraId = session.getAttribute("webraId");
        if (webraId == null &&
                comments.getUserId().equals(0) &&
                RequestConstant.REQUEST_UNDEFINED.equals(user.getNickname())) {
            customResponse.setCode(ResponseStateConstant.RESPONSE_EXCEPTION);
            customResponse.setMes(MesConstant.REQUEST_PARAM_EXCEPTION);
            return customResponse;
        }else if(webraId == null && comments.getUserId().equals(0) && RequestConstant.REQUEST_UNDEFINED.equals(user.getEmail())){
            customResponse.setCode(ResponseStateConstant.RESPONSE_EXCEPTION);
            customResponse.setMes(MesConstant.REQUEST_PARAM_EXCEPTION);
            return customResponse;
        }else if(comments.getUserId().equals(0)){
            if (user.getEmail().matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")){
                User user1 = userService.queUserByEmail(user.getEmail());
                // 设置用户id 到session中
                if (null == user1){
                    if (user.getEmail().equals(userService.queUserById(1).getEmail())){
                        customResponse.setCode(ResponseStateConstant.USER_EMAIL_WEBRA);
                        customResponse.setMes(MesConstant.EMAIL_ALREADY_EXISTS);
                        return customResponse;
                    }else{
                        userService.insertUser(user);
                        comments.setUserId(user.getId());
                        session.setAttribute("webraId",user.getId());
                    }
                }else {
                    comments.setUserId(user1.getId());
                    session.setAttribute("webraId",user1.getId());
                }
                // 如果是回复评论
                if (!comments.getReply().equals(0)){
                    Comments comments1 = commentsService.queCommentsById(comments.getReply());
                    if (!comments1.getSuperId().equals(0)){
                        comments.setReplyUserNickname(userService.queUserById(comments1.getUserId()).getNickname());
                    }
                    if (comments1.getSuperId().equals(0)){
                        // 评论的一级评论
                        comments.setSuperId(comments1.getId());
                    }else{
                        // 评论的二级评论
                        comments.setSuperId(comments1.getSuperId());
                    }
                }
            }else{
                customResponse.setCode(ResponseStateConstant.RESPONSE_EMAIL_ERROR);
                customResponse.setMes(MesConstant.ILLEGAL_MAILBOX_FORMAT);
                return customResponse;
            }
            articleService.updArticleComments(comments.getArticleId());
            commentsService.insertComment(comments);
        }else {
            if (comments.getUserId().equals(webraId) &&
                    RequestConstant.REQUEST_UNDEFINED.equals(user.getNickname()) &&
                    RequestConstant.REQUEST_UNDEFINED.equals(user.getEmail())){
                // 如果是回复评论
                if (!CommentConstant.COMMENTS_REPLY_ID.equals(comments.getReply())){
                    Comments comments1 = commentsService.queCommentsById(comments.getReply());
//                    if (!comments1.getSuperId().equals(0)){
//                        comments.setReplyUserNickname(userService.queUserById(comments1.getUserId()).getNickname());
//                    }
                    if (CommentConstant.COMMENTS_SUPER_ID.equals(comments1.getSuperId())){
                        // 评论的一级评论
                        comments.setSuperId(comments1.getId());
                    }else{
                        // 评论的二级评论
                        comments.setReplyUserNickname(userService.queUserById(comments1.getUserId()).getNickname());
                        comments.setSuperId(comments1.getSuperId());
                    }
                }
                articleService.updArticleComments(comments.getArticleId());
                commentsService.insertComment(comments);
            }else {
                customResponse.setCode(ResponseStateConstant.USER_EXCEPTION);
                customResponse.setMes(MesConstant.USER_EXCEPTION);
                return customResponse;
            }
        }

        customResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
        customResponse.setMes(MesConstant.COMMENT_SUCCESS);
        return customResponse;
    }


    ////////////////////////////----- 搜索页 -----////////////////////////////

    /**
     * 按文章名搜索
     * @param search
     * @param model
     * @return
     */
    @GetMapping("/search/{search}")
    public String search(@PathVariable String search,
                         @Nullable @RequestParam Integer curr,
                         Model model){
        curr = curr == null ? ArticleConstant.ARTICLE_DEFAULT_CURR : curr;
        model.addAttribute("curr",curr);
        String blogName = websiteService.queWebsiteAll().getName();
        model.addAttribute("blogName",search + " - 搜索 - "+blogName);
        model.addAttribute("name",search);


        List<Set> sets = setService.queSet();

        PageHelper.startPage(curr,sets.get(0).getSearchArticleNumber());
        List<Article> articles = articleService.queArticleSearch(search, null, null);
        PageInfo<Article> articlePageInfo = new PageInfo<>(articles);
        int pages = articlePageInfo.getPages();

        model.addAttribute("count",ArticleConstant.ARTICLE_QUERY_COUNT.equals(pages) ?
                ArticleConstant.ARTICLE_DEFAULT_COUNT :
                pages);
        model.addAttribute("mes",ArticleConstant.ARTICLE_QUERY_COUNT.equals(pages) ?
                InfoConstant.SEARCH_NOT_FOUNT_ARTICLE_START +search + InfoConstant.SEARCH_NOT_FOUNT_ARTICLE_END :
                InfoConstant.SEARCH_FOUNT_ARTICLE_START +search + InfoConstant.SEARCH_FOUNT_ARTICLE_END);
        model.addAttribute("code",ArticleConstant.ARTICLE_QUERY_COUNT.equals(pages) ?
                ResponseStateConstant.RESPONSE_FAILURE :
                ResponseStateConstant.RESPONSE_SUCCESS);
        model.addAttribute("articles",articles);
        model.addAttribute("classify","search");

        return "index/search";
    }


    /**
     * 按标签名搜索
     * @param tagName
     * @param model
     * @return
     */
    @GetMapping("/tag/{tagName}")
    public String getTagArticle(@PathVariable("tagName") String tagName,
                                @Nullable @RequestParam Integer curr,
                                Model model){
        curr=curr==null?ArticleConstant.ARTICLE_DEFAULT_CURR:curr;
        model.addAttribute("curr",curr);
        String blogName = websiteService.queWebsiteAll().getName();
        model.addAttribute("blogName",tagName + " - 标签 - "+blogName);
        model.addAttribute("name",tagName);
        Label label = labelService.queLabelByName(tagName);
        if (label == null){
            model.addAttribute("code",ResponseStateConstant.RESPONSE_FAILURE);
            model.addAttribute("count",ArticleConstant.ARTICLE_DEFAULT_COUNT);
            model.addAttribute("mes", MesConstant.LABEL_CODE_ERROR);
            return "index/search";
        }else {

            List<Set> sets = setService.queSet();
            PageHelper.startPage(curr,sets.get(0).getLabelArticleNumber());
            List<ArticleLabel> articleLabels = articleLabelService.queArticleLabelByLabelId(label.getId());
            PageInfo<ArticleLabel> articleLabelPageInfo = new PageInfo<>(articleLabels);
            int pages = articleLabelPageInfo.getPages();
            model.addAttribute("count",pages==ArticleConstant.ARTICLE_QUERY_COUNT ? ArticleConstant.ARTICLE_DEFAULT_COUNT : pages);
            if (articleLabels.isEmpty()){
                model.addAttribute("code",ResponseStateConstant.RESPONSE_FAILURE);
                model.addAttribute("mes",InfoConstant.LABEL_NOT_FOUNT_ARTICLE_START+label.getName()+InfoConstant.LABEL_NOT_FOUNT_ARTICLE_END);
            }else{
                List<Integer> articleIds = new ArrayList<>();
                for (ArticleLabel articleLabel: articleLabels){
                    articleIds.add(articleLabel.getArticleId());
                }
                List<Article> articles = articleService.queArticleByIds(articleIds);
                model.addAttribute("mes",InfoConstant.LABEL_FOUNT_ARTICLE_START+label.getName()+InfoConstant.LABEL_FOUNT_ARTICLE_END);
                model.addAttribute("articles",articles);
            }
        }
        model.addAttribute("classify","label");
        return "index/search";
    }

    /**
     * 根据分类别称搜索
     * @param classifyNickname
     * @param curr
     * @param model
     * @return
     */
    @GetMapping("/classify/{classifyNickname}")
    public String getClassifyArticle(@PathVariable("classifyNickname") String classifyNickname,
                                     @Nullable @RequestParam Integer curr,
                                     Model model){
        curr = curr == null ? ArticleConstant.ARTICLE_DEFAULT_CURR : curr;
        model.addAttribute("curr",curr);
        String blogName = websiteService.queWebsiteAll().getName();
        model.addAttribute("blogName",classifyNickname + " - 分类 - "+blogName);
        model.addAttribute("name",classifyNickname);
        Classification classification = classificationService.queClassificationByNickname(classifyNickname);

        if (classification == null){
            model.addAttribute("code", ResponseStateConstant.RESPONSE_FAILURE);
            model.addAttribute("count",ArticleConstant.ARTICLE_DEFAULT_COUNT);
            model.addAttribute("mes", MesConstant.CLASSIFY_CODE_ERROR);
        }else {
            List<Set> sets = setService.queSet();
            PageHelper.startPage(curr,sets.get(0).getClassifyArticleNumber());
            List<Article> articles = articleService.queArticleSearch(null,"发表",classification.getId());
            PageInfo<Article> articleLabelPageInfo = new PageInfo<>(articles);
            int pages = articleLabelPageInfo.getPages();
            model.addAttribute("count",ArticleConstant.ARTICLE_QUERY_COUNT.equals(pages) ?
                    ArticleConstant.ARTICLE_DEFAULT_COUNT :
                    pages);
            model.addAttribute("code",articles.isEmpty() ?
                    ResponseStateConstant.RESPONSE_FAILURE :
                    ResponseStateConstant.RESPONSE_NULL);
            model.addAttribute("mes",articles.isEmpty()?
                    InfoConstant.CLASSIFY_NOT_FOUNT_ARTICLE_START+classification.getName()+InfoConstant.CLASSIFY_NOT_FOUNT_ARTICLE_END :
                    InfoConstant.CLASSIFY_FOUNT_ARTICLE_START+ classification.getName() + InfoConstant.CLASSIFY_FOUNT_ARTICLE_END);
            model.addAttribute("articles",articles.isEmpty()?null:articles);
        }
        model.addAttribute("classify","classify");
        return "index/search";
    }

}
