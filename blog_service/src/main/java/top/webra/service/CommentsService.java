package top.webra.service;

import org.apache.ibatis.annotations.Param;
import top.webra.pojo.Comments;

import java.util.List;

public interface CommentsService {
    /**
     * 增加一个评论
     * @param comment
     * @return
     */
    Integer insertComment(Comments comment);

    /**
     * 删除一个评论
     * @param id
     * @return
     */
    Integer delComment(@Param("id") Integer id);
    /**
     * 删除一个一级评论的所有二级评论及自身
     * @param ids
     * @return
     */
    Integer delCommentList(@Param("ids") List<Integer> ids);

    /**
     * 根据文章id查找所有该文章的评论
     * @param articleId
     * @return
     */
    List<Comments> queCommentsByArticleId(@Param("articleId") Integer articleId);
    /**
     * 根据上级评论id查找该一级评论的二级评论
     * @param superId
     * @return
     */
    List<Comments> queCommentsBySuperId(@Param("superId") Integer superId);
    /**
     * 根据文章id及用户id查找所有该文章的评论
     * @param articleId
     * @param userId
     * @return
     */
    List<Comments> queCommentsByUserArticleId(@Param("articleId") Integer articleId,
                                              @Param("userId") Integer userId);

    /**
     * 根据评论id查找详细的评论信息
     * @param commentId
     * @return
     */
    Comments queCommentsById(@Param("commentId") Integer commentId);

    /**
     * 查找评论总数
     * @return
     */
    Integer queCommentsTotal();



    /**
     * 评论数据处理父子关系
     * @param commentsList
     * @return
     */
    List<Comments> commentsHandle(List<Comments> commentsList);

    /**
     * 获取评论列表的id并返回一个Integer列表
     * @param list
     * @return
     */
    List<Integer> getIds(List<Comments> list);





}
