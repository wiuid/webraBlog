package top.webra.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.webra.pojo.CloudStorage;

import java.util.List;

@Mapper
@Repository
public interface CloudStorageMapper {
    /**
     * 更新厂商信息
     * @param cloudStorage
     * @return
     */
    Integer updCloudStorage(CloudStorage cloudStorage);

    /**
     * 查询存储的所有厂商信息
     * @return
     */
    List<CloudStorage> queCloudStorageAll();

}
