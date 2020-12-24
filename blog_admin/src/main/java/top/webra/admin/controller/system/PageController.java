package top.webra.admin.controller.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.webra.constants.MesConstant;
import top.webra.admin.bean.CustomResponse;
import top.webra.pojo.Page;
import top.webra.pojo.Record;
import top.webra.service.impl.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/system/page")
public class PageController {
    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private CustomResponse customResponse;
    @Autowired
    private ArticleLabelServiceImpl articleLabelService;
    @Autowired
    private ClassificationServiceImpl classificationService;
    @Autowired
    private RecordServiceImpl recordService;
    @Autowired
    private PageServiceImpl pageService;

    /**
     * 所有页面
     * 所有页面中有关键页面,默认不可删除的页面,可修改
     * 页面存储内容放到文章表
     * 1. About
     *
     */
    @GetMapping
    public String page(){
        return "system/page/page";
    }

    @GetMapping("/query")
    public String queryPage(Model model){
        List<Page> pages = pageService.quePageAll();
        model.addAttribute("pages",pages);
        return "system/page/page::page";
    }
    @ResponseBody
    @PostMapping("/del")
    public CustomResponse delPage(Integer pageId){
        if (pageId.equals(1)){
            customResponse.setCode(604);
            customResponse.setMes("该数据禁止操作");
        }else if (pageService.quePageById(pageId) == null){
            customResponse.setCode(602);
            customResponse.setMes("数据不存在");
        }else {
            Page page = pageService.quePageById(pageId);
            pageService.delPage(pageId);
            customResponse.setCode(200);
            customResponse.setMes(MesConstant.DELETE_SUCCESS);
            recordService.insertRecord(new Record("页面管理","删除页面："+ page.getName()));
        }
        return customResponse;
    }
    @ResponseBody
    @PostMapping("/set")
    public CustomResponse saveSet(Page page){
        if (page == null){
            customResponse.setCode(611);
            customResponse.setMes("请求内容异常");
        }else {
            pageService.updPage(page);
            customResponse.setCode(200);
            customResponse.setMes(MesConstant.UPDATE_SUCCESS);
            recordService.insertRecord(new Record("页面管理","更新页面："+ page.getName()));
        }
        return customResponse;
    }


    /**
     * 编辑页面
     */
    @GetMapping("/edit")
    public String pageEdit(Model model){
        Page page = new Page();
        page.setId(0);
        model.addAttribute("page",page);
        return "system/page/edit";
    }
    @GetMapping("/edit/{pageName}")
    public String pageUpdate(@PathVariable String pageName, Model model){
        Page page = pageService.quePageByName(pageName);
        model.addAttribute("page",page);
        return "system/page/edit";
    }
    @ResponseBody
    @PostMapping("/edit/save")
    public CustomResponse editSave(Page page){
        if (page.getId().equals(0)){
            pageService.insertPage(page);
            customResponse.setCode(200);
            customResponse.setMes(MesConstant.CREATE_SUCCESS);
            recordService.insertRecord(new Record("页面管理","新建页面："+ page.getName()));
        }else{
            pageService.updPage(page);
            customResponse.setCode(200);
            customResponse.setMes(MesConstant.UPDATE_SUCCESS);
            recordService.insertRecord(new Record("页面管理","更新页面："+ page.getName()));
        }
        return customResponse;
    }
}
