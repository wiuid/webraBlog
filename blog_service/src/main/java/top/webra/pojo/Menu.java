package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 首页顶部到导航栏控制
 * @author webra
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {
    private Integer id;
    private String name;
    private String address;
    private Integer jump;
    private Integer superId;
    private Integer number;

    private Timestamp createTime;
    private Timestamp updateTime;
    /**
     * 二级菜单
     */
    private List<Menu> children;
}
