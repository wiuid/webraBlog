package top.webra.admin.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import top.webra.utils.FileUtil;
import top.webra.utils.PathUtil;

import java.io.*;

@Component
public class ApplicationRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
//        File filePath = new File(PathUtil.imagePatg()+"favicon.ico");
        System.out.println("---------ApplicationRunner---------");

        File filePath = new File(PathUtil.imagePath());

        if (!filePath.exists()) {// 判断目录是否存在
            System.out.println("----------------------------------------");
            System.out.println(filePath);
            boolean mkdir = filePath.mkdir();
            if (mkdir) {
                InputStream favResourceAsStream = ApplicationRunner.class.getResourceAsStream("/static/images/fav/favicon.ico");
                InputStream logoResourceAsStream = ApplicationRunner.class.getResourceAsStream("/static/images/fav/logo.png");

                FileUtil.writeToLocal(PathUtil.imagePath()+"favicon.ico",favResourceAsStream);
                FileUtil.writeToLocal(PathUtil.imagePath()+"logo.png",logoResourceAsStream);
                System.out.println("----------------------------------------");
            }
        }


        System.out.println("---------ApplicationRunner---------");
    }
}
