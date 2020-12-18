package top.webra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import top.webra.mapper.CommentsMapper;
import top.webra.pojo.Comments;
import top.webra.service.CommentsService;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public Integer insertComment(Comments comment) {
        return commentsMapper.insertComment(comment);
    }

    @Override
    public Integer delComment(Integer id) {
        return commentsMapper.delComment(id);
    }

    @Override
    public Integer delCommentList(List<Integer> ids) {
        return commentsMapper.delCommentList(ids);
    }

    @Override
    public List<Comments> queCommentsByArticleId(Integer articleId) {
        return commentsMapper.queCommentsByArticleId(articleId);
    }

    @Override
    public List<Comments> queCommentsBySuperId(Integer superId) {
        return commentsMapper.queCommentsBySuperId(superId);
    }

    @Override
    public List<Comments> queCommentsByUserArticleId(Integer articleId, Integer userId) {
        return commentsMapper.queCommentsByUserArticleId(articleId,userId);
    }

    @Override
    public Comments queCommentsById(Integer commentId) {
        return commentsMapper.queCommentsById(commentId);
    }

    @Override
    public Integer queCommentsTotal() {
        return commentsMapper.queCommentsTotal();
    }

    @Override
    public List<Comments> commentsHandle(List<Comments> commentsList) {
        List<Comments> comments = new ArrayList<Comments>();
        for (Comments comment : commentsList){
            if (comment.getSuperId() == 0 ){
                comments.add(comment);
            }else {
                for (Comments commentChildren : comments){
                    if (commentChildren.getId().equals(comment.getSuperId())){
                        if (null == commentChildren.getChildren()){
                            ArrayList<Comments> childrenList = new ArrayList<>();
                            childrenList.add(comment);
                            commentChildren.setChildren(childrenList);
                        }else {
                            commentChildren.getChildren().add(comment);
                        }
                    }
                }
            }
        }
        return comments;
    }

    @Override
    public List<Integer> getIds(List<Comments> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Comments comments : list) {
            ids.add(comments.getId());
        }
        return ids;
    }
}
