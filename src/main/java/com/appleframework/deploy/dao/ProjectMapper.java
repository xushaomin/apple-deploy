package com.appleframework.deploy.dao;

import org.springframework.stereotype.Repository;

import com.appleframework.deploy.entity.Project;
import com.appleframework.deploy.entity.ProjectWithBLOBs;
@Repository
public interface ProjectMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectWithBLOBs record);

    int insertSelective(ProjectWithBLOBs record);

    ProjectWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProjectWithBLOBs record);

    int updateByPrimaryKey(Project record);
}