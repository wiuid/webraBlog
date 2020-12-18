package top.webra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author webra
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileHash implements Serializable {
    private Integer manufacturerId;
    private String fileName;
    private String fileHash;
    private Timestamp createTime;

    public FileHash(Integer manufacturerId,String fileName, String fileHash){
        this.manufacturerId = manufacturerId;
        this.fileName = fileName;
        this.fileHash = fileHash;
    }

}
