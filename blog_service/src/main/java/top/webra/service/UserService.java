package top.webra.service;

import org.apache.ibatis.annotations.Param;
import top.webra.pojo.User;

import java.util.List;


public interface UserService {
    /**
     * 根据用户名 查 用户
     * @param username
     * @return
     */
    User queUserByUsername(@Param("username") String username);

    /**
     * 根据用户名 查 用户
     * @param nickname
     * @return
     */
    List<User> queUserByNickname(@Param("nickname") String nickname);

    /**
     * 根据用户名 查 用户
     * @param id
     * @return
     */
    User queUserById(@Param("id") Integer id);

    /**
     * 插入一个用户
     * @param user
     * @return
     */
    Integer insertUser(User user);
    /**
     * 初始化用户，仅运行一次
     * @param user
     * @return
     */
    Integer initUser(User user);

    /**
     * 根据用户id 更新用户信息
     * @param user
     * @return
     */
    Integer updUser(User user);
    /**
     * 根据用户邮箱查询游客账户
     */
    User queUserByEmail(@Param("email")String email);
}
