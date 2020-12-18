package top.webra.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.webra.pojo.ArticleLabel;

import java.util.List;

@Mapper
@Repository
public interface ArticleLabelMapper {
    /**
     * 添加一个文章标签关系
     * @param articleId
     * @param labelId
     * @return
     */
    Integer insertArticleLabel(@Param("articleId")Integer articleId,
                               @Param("labelId")Integer labelId);

    /**
     * 删除和文章有关的所有标签关系
     * @param articleId
     * @return
     */
    Integer delArticleLabelByArticleId(@Param("articleId") Integer articleId);

    /**
     * 删除和标签有关的所有文章关系
     * @param labelId
     * @return
     */
    Integer delArticleLabelByLabelId(@Param("labelId") Integer labelId);

    /**
     * 查找和该文章有关的标签id
     * @param articleId
     * @return
     */
    List<ArticleLabel> queArticleLabelByArticleId(@Param("articleId") Integer articleId);

    /**
     * 查找和标签有关的所有文章id
     * @param labelId
     * @return
     */
    List<ArticleLabel> queArticleLabelByLabelId(@Param("labelId") Integer labelId);
}
