package top.webra.service;

import org.apache.ibatis.annotations.Param;
import top.webra.pojo.Label;

import java.util.List;

public interface LabelService {
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
     * 根据名称查询指定标签
     * @param tagName
     * @return
     */
    Label queLabelByName(@Param("name") String tagName);

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
     * 查找标签的数量
     * @return
     */
    Integer queLabelTotal();


    /**
     * 插入文章的时候，对文章赋值的标签进行检查，如果标签中存在数据库中不存在的标签则对该标签进行添加操作
     * @param labelArray
     * @return
     */
    List<Integer> inspectionLabelObject(List<Object> labelArray);

}
