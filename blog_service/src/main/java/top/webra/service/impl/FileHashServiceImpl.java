package top.webra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.webra.mapper.FileHashMapper;
import top.webra.pojo.FileHash;
import top.webra.service.FileHashService;

import java.util.List;

@Service
public class FileHashServiceImpl implements FileHashService {

    @Autowired
    private FileHashMapper fileHashMapper;

    @Override
    public Integer insertFileHash(FileHash fileHash) {
        return fileHashMapper.insertFileHash(fileHash);
    }

    @Override
    public Integer delFileHash(String fileHash) {
        return fileHashMapper.delFileHash(fileHash);
    }

    @Override
    public List<FileHash> queFileHashByManufacturerId(Integer manufacturerId) {
        return fileHashMapper.queFileHashByManufacturerId(manufacturerId);
    }

    @Override
    public FileHash queFileHashByFileHash(String fileHash) {
        return fileHashMapper.queFileHashByFileHash(fileHash);
    }
}
