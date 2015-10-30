package com.appleframework.deploy.service;

import com.appleframework.deploy.entity.Task;
import com.appleframework.exception.AppleException;
import com.appleframework.model.page.Pagination;

public interface TaskService {

	public Integer save(Task record) throws AppleException;
	
	public Integer update(Task record) throws AppleException;	
		
	public Integer delete(Integer id) throws AppleException;
	
	public Task get(Integer id);
	
	public Pagination findPage(Pagination page, Integer projectId, String keyword);
		
}
