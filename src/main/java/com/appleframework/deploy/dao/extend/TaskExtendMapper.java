package com.appleframework.deploy.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.appleframework.deploy.entity.Project;
import com.appleframework.deploy.model.TaskSo;
import com.appleframework.model.page.Pagination;

@Repository
public interface TaskExtendMapper {

	List<Project> selectByPage(@Param("page") Pagination page, @Param("so") TaskSo so);
	
	void deleteByProjectId(@Param("projectId") Integer projectId);

}