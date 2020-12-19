package top.webra.admin.controller.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.webra.constants.MesConstant;
import top.webra.constants.ResponseStateConstant;
import top.webra.pojo.*;
import top.webra.pojo.Set;
import top.webra.service.impl.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@Controller()
@RequestMapping(value = "/system/set")
public class SetController {
    @Autowired
    private ArticleLabelServiceImpl articleLabelService;
    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private ClassificationServiceImpl classificationService;
    @Autowired
    private CommentsServiceImpl commentsService;
    @Autowired
    private LabelServiceImpl labelService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private WebsiteServiceImpl websiteService;
    @Autowired
    private SetServiceImpl setService;
    @Autowired
    private CloudStoragServiceImpl cloudStoragService;
    @Autowired
    private CostomResponse costomResponse;
    @Autowired
    private RecordServiceImpl recordService;


    ////////////////////////////----- 博客设置 -----////////////////////////////
    @GetMapping("/setting")
    public String setting(){
        return "system/set/setting";
    }
    @GetMapping("/setting/user/query")
    public String getUserInfo(Model model,HttpSession session){
        Integer articleTotal = articleService.queArticleTotal();
        Integer articleViewsTotal = articleService.queArticleViewsTotal();
        Integer classificationTototal = classificationService.queClassificationTototal()-1;
        Integer labelTotal = labelService.queLabelTotal();
        Integer commentsTotal = commentsService.queCommentsTotal();
        model.addAttribute("articleTotal",articleTotal);
        model.addAttribute("articleViewsTotal",articleViewsTotal);
        model.addAttribute("classificationTototal",classificationTototal);
        model.addAttribute("labelTotal",labelTotal);
        model.addAttribute("commentsTotal",commentsTotal);
        List<User> list = new ArrayList<>();
        Object webraId = session.getAttribute("webraId");
        User user = userService.queUserById(Integer.valueOf(webraId.toString()));
        list.add(user);
        model.addAttribute("user",list);
        return "system/set/setting::userInfo";
    }
    @ResponseBody
    @PostMapping("/setting/user/update")
    public CostomResponse updateUser(User user,HttpSession session){
        Object webraId = session.getAttribute("webraId");
        if (webraId.toString().equals(String.valueOf(user.getId()))){
            user.setPassword(null);
            userService.updUser(user);
            costomResponse.setMes(MesConstant.UPDATE_SUCCESS);
            costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
            recordService.insertRecord(new Record("个人信息管理","更新信息"));
        }else {
            costomResponse.setMes(MesConstant.USER_EXCEPTION);
            costomResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
        }
        return costomResponse;
    }
    @ResponseBody
    @PostMapping("/setting/user/password")
    public CostomResponse updatePassword(String oldPassword,String newPassword,HttpSession session){
        Object webraId = session.getAttribute("webraId");
        User user = userService.queUserById(Integer.valueOf(webraId.toString()));
        String password = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldPassword,password)) {
            String encode = encoder.encode(newPassword);
            user.setPassword(encode);
            userService.updUser(user);
            costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
            costomResponse.setMes(MesConstant.UPDATE_SUCCESS);
            recordService.insertRecord(new Record("个人信息管理","更新密码"));
        }else {
            costomResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
            costomResponse.setMes(MesConstant.USER_OLD_PASSWORD_ERROR);
        }

        return costomResponse;
    }


    @GetMapping("/setting/blog/query")
    public String getBlogInfo(Model model){
        Website website = websiteService.queWebsiteAll();
        model.addAttribute("name",website.getName());
        model.addAttribute("address",website.getAddress());
        model.addAttribute("info",website.getInfo());
        return "system/set/setting::blogInfo";
    }
    @ResponseBody
    @PostMapping("/setting/blog/update")
    public CostomResponse updateBlogInfo(Website website){
        Integer integer = websiteService.updWebsite(website);
        if (integer.equals(1)){
            costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
            costomResponse.setMes(MesConstant.UPDATE_SUCCESS);
            recordService.insertRecord(new Record("博客信息管理","更新信息"));
        }else {
            costomResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
            costomResponse.setMes(MesConstant.UPDATE_FAILURE);
        }
        return costomResponse;
    }


    @GetMapping("/setting/article/query")
    public String getArticleInfo(Model model){
        List<Set> sets = setService.queSet();
        model.addAttribute("sets",sets);
        return "system/set/setting::articleInfo";
    }
    @ResponseBody
    @PostMapping("/setting/article/update")
    public CostomResponse updateArticleInfo(Set set){
        Integer integer = setService.updSet(set);
        if (integer.equals(1)){
            costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
            costomResponse.setMes(MesConstant.UPDATE_SUCCESS);
            recordService.insertRecord(new Record("文章信息管理","更新信息"));
        }else {
            costomResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
            costomResponse.setMes(MesConstant.UPDATE_FAILURE);
        }
        return costomResponse;
    }

    @GetMapping("/setting/image/query")
    public String getImageInfo(Model model){
        List<CloudStorage> cloudStorages = cloudStoragService.queCloudStorageAll();
        for (CloudStorage cloudStorage : cloudStorages) {
            if (cloudStorage.getOffOn().equals(1)){
                model.addAttribute("on",cloudStorage.getId());
            }
        }

        model.addAttribute("sms",cloudStorages.get(1));
        model.addAttribute("yos",cloudStorages.get(2));
        model.addAttribute("qis",cloudStorages.get(3));
        model.addAttribute("lis",cloudStorages.get(4));
        model.addAttribute("tes",cloudStorages.get(5));
        model.addAttribute("dus",cloudStorages.get(6));
        model.addAttribute("hus",cloudStorages.get(7));

        return "system/set/setting::imageInfo";
    }

    @Transactional
    @ResponseBody
    @PostMapping("/setting/image/update")
    public CostomResponse updateImageInfo(CloudStorage cloudStorage,Integer oldId){
        CloudStorage cloudStorageOld = new CloudStorage();
        cloudStorageOld.setId(oldId);
        cloudStorageOld.setOffOn(0);
        cloudStoragService.updCloudStorage(cloudStorageOld);
        cloudStoragService.updCloudStorage(cloudStorage);
        costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
        costomResponse.setMes(MesConstant.UPDATE_SUCCESS);
        recordService.insertRecord(new Record("图片信息管理","更新信息"));
        return costomResponse;
    }

    @GetMapping("/setting/smtp/query")
    public String getSmtpInfo(Model model){
        List<Set> sets = setService.queSet();
        model.addAttribute("smtps",sets);
        return "system/set/setting::smtpInfo";
    }

    ////////////////////////////----- 工具 -----////////////////////////////
    @RequestMapping(value = "/tools",method = RequestMethod.GET)
    public String tools(){
        return "system/set/tools";
    }
    ////////////////////////////----- 关于 -----////////////////////////////
    @RequestMapping(value = "/about",method = RequestMethod.GET)
    public String ablout(){
        return "system/set/about";
    }
}
