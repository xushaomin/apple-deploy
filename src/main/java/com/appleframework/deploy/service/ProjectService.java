package com.appleframework.deploy.service;

import java.util.List;

import com.appleframework.deploy.entity.Project;
import com.appleframework.deploy.entity.ProjectWithBLOBs;
import com.appleframework.deploy.model.ProjectSo;
import com.appleframework.exception.AppleException;
import com.appleframework.model.page.Pagination;

public interface ProjectService {

	public Integer save(ProjectWithBLOBs record) throws AppleException;
	
	public Integer update(ProjectWithBLOBs record) throws AppleException;	
		
	public Integer delete(Integer id) throws AppleException;
	
	public ProjectWithBLOBs get(Integer id);
	
	public List<Project> findAll();
	
	public boolean isExistByName(String name);
	
	public boolean isUniqueByName(String oldName, String newName);
	
	public int countByName(String name);
	
	public Project getByName(String name);
	
	public Pagination findPage(Pagination page,  ProjectSo so);
	
}
