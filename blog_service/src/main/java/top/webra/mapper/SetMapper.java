package top.webra.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.webra.pojo.Set;

import java.util.List;


@Mapper
@Repository
public interface SetMapper {
    /**
     * 更新设置项
     * @param set
     * @return
     */
    Integer updSet(Set set);

    /**
     * 查看设置
     * @return
     */
    List<Set> queSet();

}
