package top.webra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import top.webra.mapper.WebsiteMapper;
import top.webra.pojo.Website;
import top.webra.service.WebsiteService;

@Service
@ComponentScan
public class WebsiteServiceImpl implements WebsiteService {
    @Autowired
    private WebsiteMapper websiteMapper;
    @Override
    public Integer updWebsite(Website website) {
        return websiteMapper.updWebsite(website);
    }

    @Override
    public Website queWebsiteAll() {
        return websiteMapper.queWebsiteAll();
    }
}
