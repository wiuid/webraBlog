package top.webra.service;

import org.apache.ibatis.annotations.Param;
import top.webra.pojo.Archive;
import top.webra.pojo.Article;

import javax.validation.constraints.Null;
import java.util.List;

public interface ArticleService {
    /**
     * 增加一篇文章
     * @param article
     * @return
     */
    Integer insertArticle(Article article);

    /**
     * 删除一篇文章
     * @param id
     * @return
     */
    Integer delArticle(@Param("id") Integer id);

    /**
     * 更改一篇文章的数据
     * @param article
     * @return
     */
    Integer updArticle(Article article);

    /**
     * 观看数加一
     * @param articleId
     * @return
     */
    Integer updArticleViews(@Param("articleId")Integer articleId);

    /**
     * 评论数加一
     * @param articleId
     * @return
     */
    Integer updArticleComments(@Param("articleId")Integer articleId);

    /**
     * 查找所有状态为发表且是轮播图的文章
     * @return
     */
    List<Article> queArticleAllRotograms();

    /**
     * 根据文章id查找文章的所有信息
     * @param id
     * @return
     */
    Article queArticleById(@Param("id")Integer id);

    /**
     * 根据一组id查找文章
     * 用作标签查找文章
     * @param ids
     * @return
     */
    List<Article> queArticleByIds(@Param("ids") List<Integer> ids);

    /**
     * 基础检索 根据 文章名、状态、分类id
     * @param title
     * @param state
     * @param classificationId
     * @return
     */
    List<Article> queArticleSearch(@Null @Param("title") String title,
                                   @Null @Param("state") String state,
                                   @Null @Param("classificationId") Integer classificationId);

    /**
     * 查看当前文章数据(包含草稿\和回收站的)
     * @return
     */
    Integer queArticleTotal();

    /**
     * 查询所有文章总阅读数
     * @return
     */
    Integer queArticleViewsTotal();

    /**
     * 查询是轮播图的个数(用来判断当前是否可以增加新的轮播图)
     * @return
     */
    Integer queArticleRotogramsTotal();

    /**
     * 生成轮播图所需要的图和标题
     * @return
     */
    List<Article> getRotograms();

    /**
     * 归档时间分类
     * @param articles
     * @return
     */
    List<Archive> getArchiveList(List<Article> articles);


}
