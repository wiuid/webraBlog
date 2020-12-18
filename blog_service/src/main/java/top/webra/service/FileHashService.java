package top.webra.service;

import org.apache.ibatis.annotations.Param;
import top.webra.pojo.FileHash;

import java.util.List;

public interface FileHashService {
    /**
     * 新增一个文件
     * @param fileHash
     * @return
     */
    Integer insertFileHash(FileHash fileHash);

    /**
     * 删除一个文件，根据文件的哈希值进行找到唯一值
     * @param fileHash
     * @return
     */
    Integer delFileHash(@Param("fileHash") String fileHash);

    /**
     * 查找一个厂商所有的图片
     * @param manufacturerId
     * @return
     */
    List<FileHash> queFileHashByManufacturerId(@Param("id") Integer manufacturerId);

    /**
     * 根据hash值查找唯一文件，判断是否存在
     * @param fileHash
     * @return
     */
    FileHash queFileHashByFileHash(@Param("fileHash") String fileHash);

}
