package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author webra
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    // id
    private Integer id;
    // 用户名
    private String username;
    // 标注该用户是游客还是注册用户  0:游客,1注册用户
    private String tourist;
    // 密码
    private String password;
    // 昵称
    private String nickname;
    // 地址
    private String address;
    // email
    private String email;
    // 头像url
    private String portrait;
    // 个人简介
    private String explain;
    private Timestamp createTime;
    private Timestamp updateTime;
}
