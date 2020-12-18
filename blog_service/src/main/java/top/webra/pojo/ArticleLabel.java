package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文章标签对应表
 * @author webra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLabel implements Serializable {
    private Integer articleId;
    private Integer labelId;
}
