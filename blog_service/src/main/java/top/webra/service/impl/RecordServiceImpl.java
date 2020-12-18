package top.webra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import top.webra.mapper.RecordMapper;
import top.webra.pojo.Record;

import java.util.List;

@Service
@ComponentScan
public class RecordServiceImpl implements RecordMapper {

    @Autowired
    private RecordMapper recordMapper;
    @Override
    public Integer insertRecord(Record record) {
        return recordMapper.insertRecord(record);
    }

    @Override
    public List<Record> queRecordAll() {
        return recordMapper.queRecordAll();
    }
}
