package top.webra.security.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import top.webra.pojo.User;
import top.webra.service.impl.UserServiceImpl;
@Component
@Slf4j
public class UserUtil {

    @Autowired
    private UserServiceImpl userService;

    public User getUserBySecurityContextHolder(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.queUserByUsername(name);
        return user;
    }
}
