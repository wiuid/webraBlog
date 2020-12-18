package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 文章实体类
 * @author webra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    // id
    private Integer id;
    // 标题
    private String title;
    // 封面图url
    private String coverMap;
    // 简介
    private String synopsis;
    // 状态 0发表 1草稿 2回收站
    private String state;
    // 2不是轮播图  1是轮播图
    private Integer rotograms;
    // 用户id
    private Integer userId;
    // 正文内容
    private String text;
    // 分类id
    private Integer classificationId;
    // 评论数
    private Integer comments;
    // 观看数
    private Integer views;

    private Integer classifyId;
    private String classificationName;
    private String userNickname;

    private Timestamp createTime;
    private Timestamp updateTime;
}
