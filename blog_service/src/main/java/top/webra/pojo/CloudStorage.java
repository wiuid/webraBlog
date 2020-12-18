package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author webra
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloudStorage implements Serializable {
    private Integer id;
    private String name;
    private String domainNameAgreement;
    private String domainName;
    private String ak;
    private String sk;
    private String bucket;
    private String regional;
    private String directory;
    private Integer offOn;
}
