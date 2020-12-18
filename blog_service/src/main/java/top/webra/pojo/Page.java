package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page implements Serializable {
    private Integer id;
    private String name;
    private String nickname;
    private String text;
    private Timestamp createTime;
    private Timestamp updateTime;
}
