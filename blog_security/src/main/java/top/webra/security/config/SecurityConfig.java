package top.webra.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.webra.security.login.provider.SelfAuthenticationProvider;
import top.webra.security.login.service.UserLoginService;

/**
 * @author webra
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private SelfAuthenticationProvider provider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义验证逻辑
        auth.authenticationProvider(provider);
        // 官方验证逻辑
        // auth.userDetailsService(userLoginService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/","/favicon.ico","/index/**","/page/**",
                        "/webra/init",
                        "/archive/**",
                        "/article/**",
                        "/search/**","/tag/**","/classify/**",
                        "/static/**",
                        "/system/login", "/defaultKaptcha").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/system/login")
                .loginProcessingUrl("/system/toLogin")
                .defaultSuccessUrl("/system/dashboard");

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        http.rememberMe();

        http.httpBasic();


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

























