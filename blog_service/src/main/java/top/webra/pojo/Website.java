package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 网站信息表
 * @author webra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Website implements Serializable {
    private Integer id;
    private String name;
    // 域名,用于网站资源的绝对定位
    private String address;
    private String info;
}
