package top.webra.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 映射网络路径
 */
@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    private String imagePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();

    /**
     * 注意点：使用idea的时候  下面的字符串filePath 如果不加"file:" 则默认是去掉的，使用registry.addResourceHandler映射本地路径时没有file:前缀则映射失败
     * 而将项目打包成jar之后，再另外添加file: 则会编程“file:file:\E:\Code\webraBlog\***”，这个环境它默认前缀是存在的
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String systemOs = System.getProperty("os.name").toLowerCase();
        System.out.println("_______________ApplicationConfig_________________");
        if (systemOs.contains("windows")) {
            System.out.println("_______________windows_________________");
            System.out.println(imagePath);
            String img = imagePath.replaceFirst("/blog-admin-1.0-SNAPSHOT.jar!/BOOT-INF/classes!", "");
            // 转换成windows的标识符
            String filePath = img.replaceAll("/", "\\\\");
            System.out.println(filePath);
            registry.addResourceHandler("/gallery/**").addResourceLocations(filePath);
            super.addResourceHandlers(registry);
            System.out.println("_______________windows_________________");
        }else if (systemOs.contains("linux")){
            System.out.println("_______________Linux_________________");
            System.out.println(imagePath);
            String filePath = imagePath.replaceFirst("/blog-admin-1.0-SNAPSHOT.jar!/BOOT-INF/classes!", "");
            System.out.println(filePath);
            registry.addResourceHandler("/gallery/**").addResourceLocations(filePath);
            super.addResourceHandlers(registry);
            System.out.println("_______________Linux_________________");
        }
        System.out.println("_______________ApplicationConfig_________________");
        // linux  usr/local/webra/blog/img
    }
}
