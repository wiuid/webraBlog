package top.webra.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import top.webra.mapper.ArticleMapper;
import top.webra.pojo.Archive;
import top.webra.pojo.Article;
import top.webra.service.ArticleService;
import top.webra.utils.DateUtil;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@ComponentScan
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Integer insertArticle(Article article) {
        return articleMapper.insertArticle(article);
    }

    @Override
    public Integer delArticle(Integer id) {
        return articleMapper.delArticle(id);
    }

    @Override
    public Integer updArticle(Article article) {
        return articleMapper.updArticle(article);
    }

    @Override
    public Integer updArticleViews(Integer articleId) {
        return articleMapper.updArticleViews(articleId);
    }

    @Override
    public Integer updArticleComments(Integer articleId) {
        return articleMapper.updArticleComments(articleId);
    }


    @Override
    public List<Article> queArticleAllRotograms() {
        return articleMapper.queArticleAllRotograms();
    }


    @Override
    public Article queArticleById(Integer id) {
        return articleMapper.queArticleById(id);
    }

    @Override
    public List<Article> queArticleByIds(List<Integer> ids) {
        return articleMapper.queArticleByIds(ids);
    }

    @Override
    public List<Article> queArticleSearch(@Null String title, @Null String state, @Null Integer classificationId) {
        return articleMapper.queArticleSearch(title,state,classificationId);
    }

    @Override
    public Integer queArticleTotal() {
        return articleMapper.queArticleTotal();
    }

    @Override
    public Integer queArticleViewsTotal() {
        return articleMapper.queArticleViewsTotal();
    }

    @Override
    public Integer queArticleRotogramsTotal() {
        return articleMapper.queArticleRotogramsTotal();
    }

    @Override
    public List<Article> getRotograms() {
        ArrayList<Article> articles = new ArrayList<>();
        String[] strings = new String[3];
        strings[0] = "/static/imgs/rotograms/rotograms01.png";
        strings[1] = "/static/imgs/rotograms/rotograms02.png";
        strings[2] = "/static/imgs/rotograms/rotograms03.png";
        int coun = 0;
        for (String string : strings) {
            Article article = new Article();
            article.setId(0);
            article.setTitle("美图"+(coun+=1));
            article.setCoverMap(string);
            articles.add(article);
        }
        return articles;
    }

    @Override
    public List<Archive> getArchiveList(List<Article> articles) {
        List<Archive> archives = new ArrayList<>();
        int size = articles.size();
        for (int index=0;index<size;index++){
            List<Article> articleList = new ArrayList<>();
            Archive archive = new Archive();
            Article article = articles.get(index);
            Date date = new Date(article.getCreateTime().getTime());
            String yyyy = DateUtil.getFormatDate(date, "yyyy");
            if (!archives.isEmpty()){
                for (Archive archive1 : archives) {
                    if (archive1.getYear().equals(yyyy)) {
                        archive1.getArticles().add(article);
                        break;
                    }
                    if (archives.indexOf(archive1) == (archives.size()-1)){
                        archive.setYear(yyyy);
                        articleList.add(article);
                        archive.setArticles(articleList);
                        archives.add(archive);
                        break;
                    }
                }
            }else {
                archive.setYear(yyyy);
                articleList.add(article);
                archive.setArticles(articleList);
                archives.add(archive);
            }

        }
        return archives;
    }
}
