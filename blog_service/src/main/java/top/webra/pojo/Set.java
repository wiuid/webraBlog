package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 一些杂乱的设置
 * @author webra
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Set implements Serializable {
    private Integer id;

    /**
     * 文章设置
     */
    private Integer indexArticleNumber;
    private Integer searchArticleNumber;
    private Integer labelArticleNumber;
    private Integer classifyArticleNumber;
    private Integer commentsOffOn;

    /**
     * 邮件设置
     */
    private Integer emailOffOn;
    private String smtpAddress;
    private String smtpAgreement;
    private Integer smtpSsl;
    private String smtpUsername;
    private String smtpPassword;
    private String smtpNickname;

}
