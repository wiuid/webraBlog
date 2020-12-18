package top.webra.admin.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
//@Component
public class ValidateCodeFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getRequestURL().equals("/system/toLogin")) {
            String tryCode = request.getParameter("code");
            String code =  (String) request.getSession().getAttribute("code");
            if (!code.equals(tryCode)){
                log.info("tryCode --->>> " + tryCode);
                log.info("code --->>> " + code);
//                try {
//                    throw new CaptchaException("code Error");
//                } catch (CaptchaException e) {
//                    e.printStackTrace();
//                }
            }
        }

        return super.attemptAuthentication(request, response);

    }
}


