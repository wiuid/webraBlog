package top.webra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import top.webra.mapper.ClassificationMapper;
import top.webra.pojo.Classification;
import top.webra.service.ClassificationService;

import java.util.List;

@Service
@ComponentScan
public class ClassificationServiceImpl implements ClassificationService {
    @Autowired
    private ClassificationMapper classificationMapper;
    @Override
    public Integer insertClassification(Classification classification) {
        return classificationMapper.insertClassification(classification);
    }

    @Override
    public Integer delClassification(Integer id) {
        return classificationMapper.delClassification(id);
    }

    @Override
    public Integer updClassification(Classification classification) {
        return classificationMapper.updClassification(classification);
    }

    @Override
    public List<Classification> queClassificationAll() {
        return classificationMapper.queClassificationAll();
    }

    @Override
    public Classification queClassificationById(Integer id) {
        return classificationMapper.queClassificationById(id);
    }

    @Override
    public Classification queClassificationByNickname(String nickname) {
        return classificationMapper.queClassificationByNickname(nickname);
    }

    @Override
    public Integer queClassificationTototal() {
        return classificationMapper.queClassificationTototal();
    }
}
