package top.webra.service;

import top.webra.pojo.Record;

import java.util.List;

public interface RecordService {
    /**
     * 新增一条记录
     * @param record
     * @return
     */
    Integer insertRecord(Record record);

    /**
     * 查询有所记录
     * @return
     */
    List<Record> queRecordAll();
}
