package com.appleframework.deploy.dao;

import org.springframework.stereotype.Repository;

import com.appleframework.deploy.entity.Task;

@Repository
public interface TaskMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
}