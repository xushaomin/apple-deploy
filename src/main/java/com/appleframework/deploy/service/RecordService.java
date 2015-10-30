package com.appleframework.deploy.service;

import com.appleframework.deploy.entity.RecordWithBLOBs;
import com.appleframework.exception.AppleException;

public interface RecordService {

	public Long save(RecordWithBLOBs record) throws AppleException;
	
	public Long update(RecordWithBLOBs record) throws AppleException;	
		
	public Long delete(Long id) throws AppleException;
	
	public RecordWithBLOBs get(Long id);
		
}
