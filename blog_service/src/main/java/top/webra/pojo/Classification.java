package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 文章分类表
 * @author webra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classification implements Serializable {
    private Integer id;
    private String name;
    private Integer index;
    private String nickname;
    private String remarks;

    private Timestamp createTime;
    private Timestamp updateTime;

    private Integer articleTotal;
}
