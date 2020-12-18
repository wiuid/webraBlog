package top.webra.mapper;

import lombok.NonNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.webra.pojo.Classification;

import java.util.List;

@Mapper
@Repository
public interface ClassificationMapper {
    /**
     * 增加一个分类
     * @param classification
     * @return
     */
    Integer insertClassification(Classification classification);

    /**
     * 删除一个分类
     * @param id
     * @return
     */
    Integer delClassification(@Param("id") Integer id);

    /**
     * 更新一个分类
     * @param classification
     * @return
     */
    Integer updClassification(Classification classification);

    /**
     * 查询有所分类
     * @return
     */
    List<Classification> queClassificationAll();

    /**
     * 查找某分类的详细信息
     * @param id
     * @return
     */
    Classification queClassificationById(@Param("id") Integer id);

    /**
     * 根据唯一的nickname进行查找
     * @param nickname
     * @return
     */
    Classification queClassificationByNickname(@Param("nickname") String nickname);

    /**
     * 查询分类总数
     * @return
     */
    Integer queClassificationTototal();
}
