package com.appleframework.deploy.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.appleframework.deploy.entity.Project;
import com.appleframework.deploy.model.ProjectSo;
import com.appleframework.model.page.Pagination;

@Repository
public interface ProjectExtendMapper {

	List<Project> selectByPage(@Param("page") Pagination page, @Param("so") ProjectSo so);

	List<Project> selectByName(@Param("name") String name);

	int countByName(@Param("name") String name);

	List<Project> selectByAll();
}