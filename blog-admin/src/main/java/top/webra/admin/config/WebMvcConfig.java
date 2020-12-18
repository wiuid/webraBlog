package top.webra.admin.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${upload.image.path}")
    private String imagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //获取当前jar 的执行路径
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        String parent = jarFile.getParent();
        System.out.println(parent);

        //文件磁盘图片url 映射
        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/image/**").addResourceLocations(imagePath);
    }

}
