package top.webra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import top.webra.mapper.ArticleLabelMapper;
import top.webra.pojo.ArticleLabel;
import top.webra.service.ArticleLabelService;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan
public class ArticleLabelServiceImpl implements ArticleLabelService {

    @Autowired
    private ArticleLabelMapper articleLabelMapper;


    @Override
    public Integer insertArticleLabel(Integer articleId, Integer labelId) {
        return articleLabelMapper.insertArticleLabel(articleId,labelId);
    }

    @Override
    public Integer delArticleLabelByArticleId(Integer articleId) {
        return articleLabelMapper.delArticleLabelByArticleId(articleId);
    }

    @Override
    public Integer delArticleLabelByLabelId(Integer labelId) {
        return articleLabelMapper.delArticleLabelByLabelId(labelId);
    }


    @Override
    public List<ArticleLabel> queArticleLabelByArticleId(Integer articleId) {
        return articleLabelMapper.queArticleLabelByArticleId(articleId);
    }

    @Override
    public List<ArticleLabel> queArticleLabelByLabelId(Integer labelId) {
        return articleLabelMapper.queArticleLabelByLabelId(labelId);
    }

    @Override
    public List<Integer> getLabelIds(List<ArticleLabel> articleLabels) {
        ArrayList<Integer> ids = new ArrayList<>();
        if (!articleLabels.isEmpty()){
            for (ArticleLabel articleLabel : articleLabels) {
                ids.add(articleLabel.getLabelId());
            }
        }
        return ids;
    }
}
