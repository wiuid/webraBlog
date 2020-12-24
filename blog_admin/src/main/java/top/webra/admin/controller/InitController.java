package top.webra.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.webra.constants.InfoConstant;
import top.webra.constants.MesConstant;
import top.webra.constants.ResponseStateConstant;
import top.webra.admin.bean.CustomResponse;
import top.webra.pojo.User;
import top.webra.service.impl.UserServiceImpl;

@Controller
@RequestMapping(value = "/webra/init")
public class InitController {

    @Autowired
    private CustomResponse customResponse;
    @Autowired
    private UserServiceImpl userService;


    @GetMapping
    public String init(){
        User user = userService.queUserById(1);
        if (user.getCreateTime().getTime() == InfoConstant.INIT_DATE){
            return "init/userInit";
        }
        return "redirect:/";
    }
    @ResponseBody
    @PostMapping
    public CustomResponse init(User user){
        User userInit = userService.queUserById(1);
        if (userInit.getCreateTime().getTime() == InfoConstant.INIT_DATE){
            // 执行更新
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userService.initUser(user);
            customResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
            customResponse.setMes("初始化成功，两秒后跳转至登录页");
        }else{
            customResponse.setCode(ResponseStateConstant.USER_EXCEPTION);
            customResponse.setMes(MesConstant.USER_EXCEPTION);
        }

        return customResponse;
    }
}
