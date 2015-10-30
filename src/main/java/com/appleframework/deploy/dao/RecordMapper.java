package com.appleframework.deploy.dao;

import org.springframework.stereotype.Repository;

import com.appleframework.deploy.entity.Record;
import com.appleframework.deploy.entity.RecordWithBLOBs;

@Repository
public interface RecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordWithBLOBs record);

    int insertSelective(RecordWithBLOBs record);

    RecordWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(RecordWithBLOBs record);

    int updateByPrimaryKey(Record record);
}