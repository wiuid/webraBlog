package top.webra.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.webra.pojo.Website;

@Mapper
@Repository
public interface WebsiteMapper {
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
