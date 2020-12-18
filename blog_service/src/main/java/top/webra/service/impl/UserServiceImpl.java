package top.webra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import top.webra.mapper.UserMapper;
import top.webra.pojo.User;
import top.webra.service.UserService;

import java.util.List;

/**
 * @author webra
 */
@Service
@ComponentScan
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queUserByUsername(String username) {
        return userMapper.queUserByUsername(username);
    }

    @Override
    public List<User> queUserByNickname(String nickname) {
        return userMapper.queUserByNickname(nickname);
    }

    @Override
    public User queUserById(Integer id) {
        return userMapper.queUserById(id);
    }

    @Override
    public Integer insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public Integer initUser(User user) {
        return userMapper.initUser(user);
    }

    @Override
    public Integer updUser(User user) {
        return userMapper.updUser(user);
    }

    @Override
    public User queUserByEmail(String email) {
        return userMapper.queUserByEmail(email);
    }
}
