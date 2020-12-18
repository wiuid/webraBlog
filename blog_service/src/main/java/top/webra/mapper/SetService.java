package top.webra.mapper;


import top.webra.pojo.Set;

import java.util.List;

public interface SetService {
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
