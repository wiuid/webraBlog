package top.webra.admin.controller.system;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.webra.admin.config.KaptchaConfig;
import top.webra.utils.CountUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Slf4j
@Controller
public class KaptchaController {
    @Autowired
    private DefaultKaptcha defa;

    @RequestMapping(value = "/defaultKaptcha",method = RequestMethod.GET)
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception{
        byte[] chatcha = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try{
            // 将生成的验证码保存在session中
//            String text = defa.createText();
            Map<String, Object> count = CountUtil.getCount();
            log.info("登录验证码--->>>" + count);
            request.getSession().setAttribute("code",count.get("number"));
            BufferedImage image = defa.createImage(count.get("formula").toString());
            ImageIO.write(image ,"jpg",outputStream);
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        chatcha = outputStream.toByteArray();
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragm","no-cache");
        response.setDateHeader("Expire",0);
        response.setContentType("image/jpg");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(chatcha);
        servletOutputStream.flush();
        servletOutputStream.close();
    }
}
