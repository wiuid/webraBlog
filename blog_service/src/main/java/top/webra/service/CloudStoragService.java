package top.webra.service;

import top.webra.pojo.CloudStorage;

import java.util.List;

public interface CloudStoragService {
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
