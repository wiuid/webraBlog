package top.webra.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.webra.pojo.Record;

import java.util.List;

@Mapper
@Repository
public interface RecordMapper {
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
