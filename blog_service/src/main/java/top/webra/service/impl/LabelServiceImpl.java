package top.webra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import top.webra.mapper.LabelMapper;
import top.webra.pojo.Label;
import top.webra.service.LabelService;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelMapper labelMapper;

    @Override
    public Integer insertLabel(Label label) {
        return labelMapper.insertLabel(label);
    }

    @Override
    public Integer delLabel(Integer id) {
        return labelMapper.delLabel(id);
    }

    @Override
    public Integer updLabel(Label label) {
        return labelMapper.updLabel(label);
    }

    @Override
    public List<Label> queLabelAll() {
        return labelMapper.queLabelAll();
    }

    @Override
    public Label queLabelByName(String tagName) {
        return labelMapper.queLabelByName(tagName);
    }

    @Override
    public Label queLabelById(Integer id) {
        return labelMapper.queLabelById(id);
    }

    @Override
    public List<Label> queLabelByIds(List<Integer> ids) {
        return labelMapper.queLabelByIds(ids);
    }

    @Override
    public Integer queLabelTotal() {
        return labelMapper.queLabelTotal();
    }

    @Override
    public List<Integer> inspectionLabelObject(List<Object> labelArray) {
        // List检查，判断标签中是否有新标签的加入
        List<Integer> labelIds = new ArrayList<>();
        // 遍历该标签
        for (Object object : labelArray) {

            // 判断标签是否是Integer类型的
            try {
                String s = object.toString();
                Integer integer = Integer.valueOf(s);
                // 通过该标签id查数据库是否存在该标签
                Label label = this.queLabelById(integer);
                if (label.equals(null)){
                    // 如果查询结果为空，说明该对象虽然是Integer类型 但是要添加的标签的名字就是Integer类型的标签名，就将该对象以LabelName的属性值插入进数据库
                    Label label1 = new Label();
                    label1.setName(object.toString());
                    this.insertLabel(label1);
                    // 再mybatis的xml语句中使用了·useGeneratedKeys="true" keyProperty="id"·
                    // 该属性会将插入的数据返回该数据的id值到对象上，所以下面可以直接通过对象获取id  然后赋值给labelIds
                    labelIds.add(label1.getId());
                }else {
                    // 如果查询结果不为空，则直接赋值给labelIds
                    labelIds.add(integer);
                }
            }catch (Exception e){
                // 如果不是Integer类型的说明是需要新添加的标签
                Label label = new Label();
                label.setName(object.toString());
                this.insertLabel(label);
                labelIds.add(label.getId());
            }
        }
        return labelIds;
    }
}
