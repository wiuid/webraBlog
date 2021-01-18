package top.webra.utils;

import org.springframework.util.ClassUtils;

public class PathUtil {

    public static String imagePath(){

        String systemOs = System.getProperty("os.name").toLowerCase();
        if (systemOs.contains("linux")){
            return ClassUtils.getDefaultClassLoader().getResource("").getPath()
                    .replaceFirst("file:","")
                    .replaceFirst("blog-admin-1.0-SNAPSHOT.jar!/BOOT-INF/classes!", "img");
        }else {
            return ClassUtils.getDefaultClassLoader().getResource("").getPath()
                    .replaceFirst("file:/","")
                    .replaceFirst("blog-admin-1.0-SNAPSHOT.jar!/BOOT-INF/classes!", "img");
        }
    }

    public static String favPath(){
        return ClassUtils.getDefaultClassLoader().getResource("").getPath()
                .replaceFirst("file:/","") + "/static/images/fav/";
    }

}
