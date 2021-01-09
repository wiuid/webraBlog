package top.webra.utils;

import org.springframework.util.ClassUtils;

public class PathUtil {

    public static String imagePatg(){
        return ClassUtils.getDefaultClassLoader().getResource("").getPath()
                .replaceFirst("file:/","")
                .replaceFirst("blog-admin-1.0-SNAPSHOT.jar!/BOOT-INF/classes!", "img");
    }

    public static String favPatg(){
        return ClassUtils.getDefaultClassLoader().getResource("").getPath()
                .replaceFirst("file:/","") + "/static/images/fav/";
    }

}
