package top.webra.security.login.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import top.webra.pojo.Record;
import top.webra.security.login.pojo.UserLogin;
import top.webra.security.login.service.UserLoginService;
import top.webra.service.impl.RecordServiceImpl;

import javax.servlet.http.HttpSession;

/**
 * @author webra
 */
@Slf4j
@Component
public class SelfAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private RecordServiceImpl recordService;

    @Autowired
    private HttpSession session;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        // 验证码判断
//        暂时不写

        // 交给userLoginService 看看是否存在该用户
        UserLogin userDetails = (UserLogin)userLoginService.loadUserByUsername(username);
        // 密码判断
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password,userDetails.getPassword())){
            log.info("密码不匹配!!!");
            log.info(userDetails.getPassword());
            recordService.insertRecord(new Record("登录操作","登录失败，密码错误"));
            throw new BadCredentialsException("Password Error!!");
        }else {
            session.setAttribute("webraId",userDetails.getId());
            recordService.insertRecord(new Record("登录操作","登录成功"));
        }

        return new UsernamePasswordAuthenticationToken(username,password,userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
