package com.appleframework.deploy.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.deploy.dao.TaskMapper;
import com.appleframework.deploy.dao.extend.TaskExtendMapper;
import com.appleframework.deploy.entity.Task;
import com.appleframework.deploy.service.TaskService;
import com.appleframework.exception.AppleException;
import com.appleframework.model.page.Pagination;

@Service("taskService")
public class TaskServiceImpl implements TaskService {
	
	@Resource
	private TaskMapper taskMapper;
	
	@Resource
	private TaskExtendMapper taskExtendMapper;

	@Override
	public Integer save(Task record) throws AppleException {
		taskMapper.insert(record);
		return record.getId();
	}

	@Override
	public Integer update(Task record) throws AppleException {
		taskMapper.updateByPrimaryKey(record);
		return null;
	}

	@Override
	public Integer delete(Integer id) throws AppleException {
		taskMapper.deleteByPrimaryKey(id);
		return id;
	}

	@Override
	public Task get(Integer id) {
		return taskMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public Pagination findPage(Pagination page, Integer projectId, String keyword) {
		page.setList(taskExtendMapper.selectByPage(page, projectId, keyword));
		return page;
	}
	

}
