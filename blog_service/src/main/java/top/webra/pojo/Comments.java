package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/*
 * 评论表
 * @author webra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments implements Serializable {
    /*id*/
    private Integer id;
    /*文章id*/
    private Integer articleId;
    /*用户id,有一个用户id为0,是游客*/
    private Integer userId;
    /*回复谁的用户id,默认0(0代表一级评论)*/
    private Integer reply;
    /*回复的评论的人的昵称(如果有的话)*/
    private String replyUserNickname;
    /*评论主体内容*/
    private String text;
    /*上级评论id,一级是0*/
    private Integer superId;
    /*二级评论*/
    private List<Comments> children;

    private Timestamp createTime;

    /*用户昵称*/
    private String nickname;
    /*文章名称*/
    private String title;
}
