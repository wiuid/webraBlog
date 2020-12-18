package top.webra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.webra.mapper.CloudStorageMapper;
import top.webra.mapper.FileHashMapper;
import top.webra.pojo.CloudStorage;
import top.webra.service.CloudStoragService;

import java.util.List;
@Service
public class CloudStoragServiceImpl implements CloudStoragService {


    @Autowired
    private CloudStorageMapper cloudStorageMapper;

    @Override
    public Integer updCloudStorage(CloudStorage cloudStorage) {
        return cloudStorageMapper.updCloudStorage(cloudStorage);
    }

    @Override
    public List<CloudStorage> queCloudStorageAll() {
        return cloudStorageMapper.queCloudStorageAll();
    }
}
