package top.webra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.webra.mapper.PageMapper;
import top.webra.pojo.Page;
import top.webra.service.PageService;

import java.util.List;

@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private PageMapper pageMapper;

    @Override
    public Integer insertPage(Page page) {
        return pageMapper.insertPage(page);
    }

    @Override
    public Integer delPage(Integer pageId) {
        return pageMapper.delPage(pageId);
    }

    @Override
    public Integer updPage(Page page) {
        return pageMapper.updPage(page);
    }

    @Override
    public Page quePageById(Integer pageId) {
        return pageMapper.quePageById(pageId);
    }

    @Override
    public Page quePageByName(String pageName) {
        return pageMapper.quePageByName(pageName);
    }

    @Override
    public List<Page> quePageAll() {
        return pageMapper.quePageAll();
    }
}
