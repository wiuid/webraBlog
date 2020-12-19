package top.webra.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String showError(HttpServletRequest request){
        return getErrorPath();
    }

    @Override
    public String getErrorPath() {
        return "error/404";
    }
}
