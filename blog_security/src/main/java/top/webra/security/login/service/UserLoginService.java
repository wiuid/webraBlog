package top.webra.security.login.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.webra.pojo.Record;
import top.webra.security.login.pojo.UserLogin;
import top.webra.pojo.User;
import top.webra.service.impl.RecordServiceImpl;
import top.webra.service.impl.UserServiceImpl;

import javax.servlet.http.HttpSession;

/**
 * @author webra
 */
@Slf4j
@Service
public class UserLoginService implements UserDetailsService {
    @Autowired
    private UserServiceImpl userService;
    // 判断用户名是否存在,不存在则抛出异常找不到用户
    @Autowired
    private RecordServiceImpl recordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserLoginService --->>> loadUserByUsername --->>> username " + username);

        UserLogin userLogin = new UserLogin();
        User user = userService.queUserByUsername(username);
        if (user == null){
            recordService.insertRecord(new Record("登录操作","登录失败，不存在的用户名"));
            throw new UsernameNotFoundException("User Not Fount!!");
        }
        userLogin.setUsername(username);
        userLogin.setPassword(user.getPassword());
        userLogin.setId(user.getId());
        userLogin.setNickname(user.getNickname());
        return userLogin;
    }
}
