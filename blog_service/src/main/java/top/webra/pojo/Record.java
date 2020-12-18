package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 操作记录表
 * @author webra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record implements Serializable {
    // id
    private Integer id;
    // 事件的名字
    private String name;
    // 事件的内容(如果事件是登录,该内容可以是用户名)
    private String text;
    // 事件发生事件
    private Timestamp createTime;

    public Record(String name,String text){
        this.name=name;
        this.text=text;
    }

}
