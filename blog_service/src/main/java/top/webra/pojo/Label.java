package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 标签表
 * @author webra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Label implements Serializable {

    private Integer id;
    private String name;

    private Timestamp createTime;
    private Timestamp updateTime;
}
