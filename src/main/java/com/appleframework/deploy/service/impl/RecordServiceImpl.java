package com.appleframework.deploy.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.deploy.dao.RecordMapper;
import com.appleframework.deploy.entity.RecordWithBLOBs;
import com.appleframework.deploy.service.RecordService;
import com.appleframework.exception.AppleException;

@Service("recordService")
public class RecordServiceImpl implements RecordService {
	
	@Resource
	private RecordMapper recordMapper;

	@Override
	public Long save(RecordWithBLOBs record) throws AppleException {
		record.setCreateAt(new Date());
		recordMapper.insert(record);
		return record.getId();
	}

	@Override
	public Long update(RecordWithBLOBs record) throws AppleException {
		recordMapper.updateByPrimaryKeyWithBLOBs(record);
		return null;
	}

	@Override
	public Long delete(Long id) throws AppleException {
		recordMapper.deleteByPrimaryKey(id);
		return id;
	}

	@Override
	public RecordWithBLOBs get(Long id) {
		return recordMapper.selectByPrimaryKey(id);
	}
	
}
