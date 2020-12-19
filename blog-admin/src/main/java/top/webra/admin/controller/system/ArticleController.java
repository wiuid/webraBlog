package top.webra.admin.controller.system;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiniu.util.Md5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.webra.constants.MesConstant;
import top.webra.constants.ResponseStateConstant;
import top.webra.pojo.*;
import top.webra.service.ClassificationService;
import top.webra.service.impl.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

//@RestController
@Slf4j
@Controller()
@RequestMapping(value = "/system/article")
public class ArticleController {

    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private CostomResponse costomResponse;
    @Autowired
    private CommentsServiceImpl commentsService;
    @Autowired
    private ArticleLabelServiceImpl articleLabelService;
    @Autowired
    private ClassificationServiceImpl classificationService;
    @Autowired
    private LabelServiceImpl labelService;
    @Autowired
    private FileHashServiceImpl fileHashService;
    @Autowired
    private RecordServiceImpl recordService;

    private String imagePath = ClassUtils.getDefaultClassLoader().getResource("static/images/").getPath();

    ////////////////////////////----- 所有文章页 -----////////////////////////////

    @GetMapping
    public String article(Model model){
        List<Classification> classifications = classificationService.queClassificationAll();
        model.addAttribute("classifys",classifications);
        return "system/article/article";
    }


    /**
     * 根据条件查询文章，参数可为null
     * @param title
     * @param state
     * @param classifyId
     * @param model
     * @return
     */
    @GetMapping("/query")
    public String queArticle(@Nullable @RequestParam String title,
                             @Nullable @RequestParam String state,
                             @Nullable @RequestParam Integer classifyId,
                             @Nullable @RequestParam Integer curr,
                             Model model){

        curr = curr==null?1:curr;
        PageHelper.startPage(curr,10);
        List<Article> articles = articleService.queArticleSearch(title, state, classifyId);
        PageInfo<Article> articlePageInfo = new PageInfo<>(articles);
        int count = articlePageInfo.getPages();
        model.addAttribute("articles",articles);
        model.addAttribute("curr",curr);
        model.addAttribute("count",count = count==0?1:count);
        model.addAttribute("title",title);
        model.addAttribute("state",state);
        model.addAttribute("classifyId",classifyId);

        return "system/article/article::article";
    }

    /**
     * 删除请求
     * @param articleId
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public CostomResponse delArticle(Integer articleId){
        // 删除该文章所拥有的标签
        articleLabelService.delArticleLabelByArticleId(articleId);
        // 根据id获得该文章
        Article article = articleService.queArticleById(articleId);
        // 删除该文章的评论
        commentsService.delCommentByArticleId(articleId);
        // 删除文章
        articleService.delArticle(articleId);
        // 返回结果
        costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
        costomResponse.setMes(MesConstant.DELETE_SUCCESS);
        recordService.insertRecord(new Record("文章管理","删除文章："+article.getTitle()));
        return costomResponse;
    }
    @ResponseBody
    @PostMapping("/update")
    public CostomResponse updateArticle(Article article){
        articleService.updArticle(article);
        costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
        costomResponse.setMes(MesConstant.UPDATE_SUCCESS);
        recordService.insertRecord(new Record("文章管理","更新文章："+article.getTitle()));
        return costomResponse;
    }


    ////////////////////////////----- 编写文章 -----////////////////////////////
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Model model){
        Article article = new Article();
        article.setCoverMap("/static/images/system/placeholder.jpg");
        List<Label> labels = labelService.queLabelAll();
        List<Classification> classifications = classificationService.queClassificationAll();

        model.addAttribute("article",article);
        model.addAttribute("labels",labels);
        model.addAttribute("classifications",classifications);

        return "system/article/edit";
    }



    // 发布文章
    @ResponseBody
    @PostMapping("/edit/release")
    public CostomResponse releaseArticle(Article article,@RequestParam(required = false)List<Object> labelArray,HttpSession session){

        // 对传入参数  labelArray   进行检查
        List<Integer> labelIds = labelService.inspectionLabelObject(labelArray);

        // 重写和新建文章共通的部分
        if (null == article.getCoverMap() || article.getCoverMap().equals("/static/imgs/placeholder.jpg")){
            article.setCoverMap("/static/images/rotograms/rotograms02.png");
        }
        // 编辑文章更新
        if (!article.getId().equals(0)){
            articleService.updArticle(article);
            articleLabelService.delArticleLabelByArticleId(article.getId());
            if (labelIds.size()>0){
                for (Integer labelId : labelIds){
                    articleLabelService.insertArticleLabel(article.getId(),labelId);
                }
            }
            costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
            costomResponse.setMes(MesConstant.UPDATE_SUCCESS);
            recordService.insertRecord(new Record("文章管理","更新文章："+article.getTitle()));
            return costomResponse;
        }else{
            //编辑文章新发布
            Object webraId = session.getAttribute("webraId");
            Integer integer = Integer.valueOf(webraId.toString());
            article.setUserId(integer);
            System.out.println(article);
            articleService.insertArticle(article);
            if (labelIds.size()>0){
                for (Integer labelId : labelIds){
                    articleLabelService.insertArticleLabel(article.getId(),labelId);
                }
            }

            costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
            costomResponse.setMes(MesConstant.RELEASE_SUCCESS);
            recordService.insertRecord(new Record("文章管理","发布文章："+article.getTitle()));
            return costomResponse;
        }
    }
    // 文章改写
    @GetMapping("/edit/rewrite/{articleId}")
    public String articleRewrite(@PathVariable Integer articleId,Model model){
        Article article = articleService.queArticleById(articleId);
        List<Label> labels = labelService.queLabelAll();
        List<Classification> classifications = classificationService.queClassificationAll();
        List<ArticleLabel> articleLabels = articleLabelService.queArticleLabelByArticleId(articleId);
        ArrayList<Integer> integers = new ArrayList<>();
        if (!articleLabels.isEmpty()){
            for (ArticleLabel articleLabel : articleLabels) {
                integers.add(articleLabel.getLabelId());
            }
        }

        model.addAttribute("articleLabels",integers);
        model.addAttribute("labels",labels);
        model.addAttribute("classifications",classifications);
        model.addAttribute("article",article);
        return "system/article/edit";
    }

    ////////////////////////////----- 上传图片处理 -----////////////////////////////

    // 图片上传,不对图片处理
    @ResponseBody
    @PostMapping("/upload/image")
    public String uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        HashMap<String, String> data = new HashMap<>();
        // 后缀名获取
        String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileMd5 = Md5.md5(file.getBytes());

        FileHash fileHash = fileHashService.queFileHashByFileHash(fileMd5);
        if (fileHash==null){
            String fileName = fileMd5 + suffixName;

            // 获得静态资源路径
            File dest = new File(imagePath + fileName);
            if (!dest.getParentFile().exists()) {
                // 如果目录不存在则创建目录
                dest.getParentFile().mkdirs();
            }
            try {
                // 将文件写入目录
                file.transferTo(dest);
                fileHashService.insertFileHash(new FileHash(1,fileName, fileMd5));
            } catch (IOException e) {
                e.printStackTrace();
            }
            data.put("location","/static/images/" + fileName);
            String json = JSON.toJSONString(data);
            return json;
        }else {
            data.put("location","/static/images/" + fileHash.getFileName());
            String json = JSON.toJSONString(data);
            return json;
        }
    }
    // 封面图 处理
    @ResponseBody
    @PostMapping("/upload/cover/map")
    public String coverMap(@RequestPart("file") MultipartFile file) throws IOException {
        HashMap<String, String> data = new HashMap<>();
        String originalFilename = file.getOriginalFilename();
        // 文件名
        String fileNameOld =file.getOriginalFilename();
        // 后缀名
        String suffixName = fileNameOld.substring(fileNameOld.lastIndexOf(".") + 1);
        String fileMd5 = "f+"+Md5.md5(file.getBytes());

        FileHash fileHash = fileHashService.queFileHashByFileHash(fileMd5);
        if (fileHash==null){
            String fileName = fileMd5 + "." + suffixName;
            File dest = new File(imagePath + fileName);

            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedImage image = ImageIO.read(dest);
                int width = image.getWidth();
                int height =image.getHeight();
                double proportion = (double)width /height;
                if (proportion > 5.0/3.0){
                    // 宽度很长
                    double imageXDouble = height / 3.0d * 5;
                    int imageXInt = new Double(imageXDouble).intValue();
                    BufferedImage subimage = image.getSubimage((width-imageXInt)/2,0,imageXInt,height);
                    ImageIO.write(subimage, suffixName, dest);

                    data.put("code","200");
                    data.put("location","/static/images/" + fileName);
                }else if (proportion < 5.0/3.0){
                    // 高度很长
                    double imageYDouble = width / 5.0d * 3;
                    int imageYInt = new Double(imageYDouble).intValue();
                    BufferedImage subimage = image.getSubimage(0, (height - imageYInt) / 2,width,imageYInt);
                    ImageIO.write(subimage,suffixName,dest);

                    data.put("code","200");
                    data.put("location","/static/images/" + fileName);
                }else {
                    try {
                        file.transferTo(dest);
                        data.put("code","200");
                        data.put("location","/static/images/" + fileName);
                    } catch (IOException e) {
                        data.put("code",ResponseStateConstant.RESPONSE_FAILURE.toString());
                        data.put("location","二层--后端图片存储出错!请管理员检查");
                    }
                }
                fileHashService.insertFileHash(new FileHash(1,fileName, fileMd5));
            } catch (IOException e) {
                data.put("code",ResponseStateConstant.RESPONSE_FAILURE.toString());
                data.put("location","一层--后端图片存储出错!请管理员检查");
            }

            String json = JSON.toJSONString(data);
            return json;
        }else {
            data.put("code","200");
            data.put("location","/static/images/" + fileHash.getFileName());
            String json = JSON.toJSONString(data);
            return json;
        }


    }

    ////////////////////////////----- 分类 -----////////////////////////////
    @GetMapping("/classify")
    public String classify(){
        return "system/article/classify";
    }

    @GetMapping("/classify/query")
    public String queryClassify(Model model){
        List<Classification> classifications = classificationService.queClassificationAll();
        model.addAttribute("classifications",classifications);
        return "system/article/classify::getClassify";
    }
    @ResponseBody
    @PostMapping("/classify/update")
    public CostomResponse updateClassify(Classification classification){
        System.out.println(classification);

        if (classification.getId().equals(0)){
            classificationService.insertClassification(classification);
            costomResponse.setMes(MesConstant.CREATE_SUCCESS);
            recordService.insertRecord(new Record("分类管理","添加分类："+classification.getName()));
        }else {
            classificationService.updClassification(classification);
            costomResponse.setMes(MesConstant.UPDATE_SUCCESS);
            recordService.insertRecord(new Record("分类管理","更新分类："+classification.getName()));
        }
        costomResponse.setCode(200);
        return costomResponse;
    }

    @ResponseBody
    @PostMapping("/classify/delete")
    public CostomResponse delClassify(Integer classifyId){
        List<Article> articles = articleService.queArticleSearch(null, null, classifyId);
        if (!articles.isEmpty()){
            for (Article article : articles) {
                article.setClassificationId(1);
                articleService.updArticle(article);
            }
        }
        Classification classification = classificationService.queClassificationById(classifyId);
        classificationService.delClassification(classifyId);
        costomResponse.setCode(200);
        costomResponse.setMes("删除成功");
        recordService.insertRecord(new Record("分类管理","删除分类："+classification.getName()));

        return costomResponse;
    }

    ////////////////////////////----- 标签 -----////////////////////////////
    @GetMapping("/label")
    public String label(){
        return "system/article/label";
    }
    @GetMapping("/label/query")
    public String labelAll(Model model){
        List<Label> labels = labelService.queLabelAll();
        model.addAttribute("labels",labels);
        return "system/article/label::allLabel";
    }

    @ResponseBody
    @PostMapping("/label/update")
    public CostomResponse updateLabel(Label label){
        if (label.getId().equals(0)) {
            labelService.insertLabel(label);
            costomResponse.setMes(MesConstant.CREATE_SUCCESS);
            recordService.insertRecord(new Record("标签管理","添加标签："+label.getName()));
        }else {
            labelService.updLabel(label);
            costomResponse.setMes(MesConstant.UPDATE_SUCCESS);
            recordService.insertRecord(new Record("标签管理","更新标签："+label.getName()));
        }
        costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
        return costomResponse;
    }

    @ResponseBody
    @PostMapping("/label/delete")
    public CostomResponse delLabel(Integer labelId){
        // 检查与该标签关联的文章，删除关联
        List<ArticleLabel> articleLabels = articleLabelService.queArticleLabelByLabelId(labelId);
        if (!articleLabels.isEmpty()){
            articleLabelService.delArticleLabelByLabelId(labelId);
        }
        Label label = labelService.queLabelById(labelId);
        labelService.delLabel(labelId);
        costomResponse.setMes(MesConstant.DELETE_SUCCESS);
        costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
        recordService.insertRecord(new Record("标签管理","删除标签："+label.getName()));
        return costomResponse;
    }

























}
