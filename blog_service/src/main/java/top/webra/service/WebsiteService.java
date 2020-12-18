package top.webra.service;

import top.webra.pojo.Website;

public interface WebsiteService {
    /**
     * 更新网站信息
     * @param website
     * @return
     */
    Integer updWebsite(Website website);

    /**
     * 查看网站信息
     * @return
     */
    Website queWebsiteAll();
}
