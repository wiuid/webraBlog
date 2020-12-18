package top.webra.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.webra.pojo.Label;

import java.util.List;

@Mapper
@Repository
public interface LabelMapper {
    /**
     * 增加一个标签
     * @param label
     * @return
     */
    Integer insertLabel(Label label);

    /**
     * 删除一个标签
     * @param id
     * @return
     */
    Integer delLabel(@Param("id") Integer id);

    /**
     * 更新一个标签
     * @param label
     * @return
     */
    Integer updLabel(Label label);

    /**
     * 查找所有标签
     * @return
     */
    List<Label> queLabelAll();

    /**
     * 查询指定标签
     * @param id
     * @return
     */
    Label queLabelById(@Param("id") Integer id);
    /**
     * 查询一组标签
     * @param ids
     * @return
     */
    List<Label> queLabelByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据名称查询指定标签
     * @param tagName
     * @return
     */
    Label queLabelByName(@Param("name") String tagName);

    /**
     * 查找标签的数量
     * @return
     */
    Integer queLabelTotal();
}
