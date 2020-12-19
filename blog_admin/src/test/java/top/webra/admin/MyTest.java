package top.webra.admin;


import com.alibaba.fastjson.JSON;
import com.qiniu.util.Md5;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import top.webra.mapper.UserMapper;
import top.webra.pojo.*;
import top.webra.service.impl.*;
import top.webra.utils.oos.QiniuyunUtil;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {
    @Value("${upload.image.path}")
    private String imagePath;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private MenuServiceImpl menuService;
    @Test
    public void test(){
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    public void userTest(){
        File file = new File("E:\\Code\\webra-blog\\blog-admin\\target\\classes\\static\\images\\05555.jpg");
        try {
            String s = Md5.md5(file);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private DataSource dataSource;
    @Test
    public void jdbcTest(){
        System.out.println("----------->>>>>>>>>>>>");
        System.out.println(dataSource.getClass());
    }

    @Test
    public void passwordEncodingTest(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String admin = bCryptPasswordEncoder.encode("admin");
        System.out.println(admin);
        System.out.println(bCryptPasswordEncoder.matches("admin", admin));

    }

    @Test
    public void randomTest(){
        for (int index = 0;index<100;index++){
            int number =(int) (Math.random()*9+1);
            System.out.println(number);
        }

    }
    @Autowired
    private ClassificationServiceImpl classificationService;
    @Autowired
    private LabelServiceImpl labelService;
    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private ArticleLabelServiceImpl articleLabelService;
    @Test
    public void serviceTest(){
        Integer[] integers = new Integer[3];
        integers[0] = 14;
        integers[1] = 15;
        integers[2] = 16;

        for (Integer labelId : integers){
            articleLabelService.insertArticleLabel(6,labelId);
        }

        System.out.println(articleLabelService.queArticleLabelByArticleId(6));

    }
    @Test
    public void stringTest(){
        Object o = new Object();
        o = "webra";
        System.out.println(o instanceof Integer);
    }

    @Autowired
    private CommentsServiceImpl commentsService;
    @Test
    public void listTest(){
        List<Comments> commentsList = commentsService.queCommentsByArticleId(6);

        System.out.println(commentsList);
        List<Comments> comments = new ArrayList<Comments>();
        for (Comments comment : commentsList){
            if (comment.getSuperId() == 0 ){
                comments.add(comment);
            }else {
                for (Comments commentChildren : comments){
                    if (commentChildren.getId().equals(comment.getSuperId())){
                        if (null == commentChildren.getChildren()){
                            ArrayList<Comments> childrenList = new ArrayList<>();
                            childrenList.add(comment);
                            commentChildren.setChildren(childrenList);
                        }else {
                            commentChildren.getChildren().add(comment);
                        }
                    }
                }
            }
        }

        String s = JSON.toJSONString(comments);

        System.out.println(s);


    }

    public List<Comments> getCommentsList(List<Comments> commentsList){
        List<Comments> comments = new ArrayList<Comments>();
        for (Comments comment : commentsList){
            if (comment.getSuperId() == 0 ){
                comments.add(comment);
            }else {
                for (Comments commentChildren : comments){
                    if (commentChildren.getId().equals(comment.getSuperId())){
                        if (null == commentChildren.getChildren()){
                            ArrayList<Comments> childrenList = new ArrayList<>();
                            childrenList.add(comment);
                        }else {
                            commentChildren.getChildren().add(comment);
                        }
                    }
                }
            }
        }
        return commentsList;
    }

}


























