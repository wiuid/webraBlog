package top.webra.admin;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpSession;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {

    @Autowired
    private HttpSession session;

    private String  imagePath =  ClassUtils.getDefaultClassLoader().getResource("").getPath()
            .replaceFirst("blog-admin-1.0-SNAPSHOT.jar!/BOOT-INF/classes!/", "")
            .replaceFirst("file:","");

    @Test
    public void webraTest(){
        File filePath = new File(imagePath+"/img/");
        if (!filePath.getParentFile().exists()) {
            // 如果目录不存在则创建目录
            filePath.getParentFile().mkdirs();
        }
    }
    @Test
    public void asdasdasd(){
        String str = "/E:/Code/webraBlog/blog_admin/target/classes/";
        String filePath = str.replaceAll("/", "\\\\");
        System.out.println(filePath);
    }
}


























