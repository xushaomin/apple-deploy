package com.appleframework.deploy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.deploy.dao.ProjectMapper;
import com.appleframework.deploy.dao.extend.ProjectExtendMapper;
import com.appleframework.deploy.entity.Project;
import com.appleframework.deploy.entity.ProjectWithBLOBs;
import com.appleframework.deploy.model.ProjectSo;
import com.appleframework.deploy.service.ProjectService;
import com.appleframework.exception.AppleException;
import com.appleframework.model.page.Pagination;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	
	@Resource
	private ProjectMapper projectMapper;
	
	@Resource
	private ProjectExtendMapper projectExtendMapper;

	@Override
	public Integer save(ProjectWithBLOBs record) throws AppleException {
		projectMapper.insert(record);
		return record.getId();
	}

	@Override
	public Integer update(ProjectWithBLOBs record) throws AppleException {
		projectMapper.updateByPrimaryKeyWithBLOBs(record);
		return null;
	}

	@Override
	public Integer delete(Integer id) throws AppleException {
		projectMapper.deleteByPrimaryKey(id);
		return id;
	}

	@Override
	public ProjectWithBLOBs get(Integer id) {
		return projectMapper.selectByPrimaryKey(id);
	}

	public boolean isExistByName(String name) {
	    if(this.countByName(name) > 0) {
	    	return true;
	    } else {
			return false;
		}
	}
	
	public int countByName(String name) {
		return projectExtendMapper.countByName(name);
	}
	
	public Project getByName(String name) {
		List<Project> list = projectExtendMapper.selectByName(name);
		if(list.size() > 0) {
			return list.get(0);
		}
		else {
			return null;
		}
	}
	
	public boolean isUniqueByName(String oldName, String newName) {
		if (StringUtils.equalsIgnoreCase(oldName, newName)) {
			return true;
		} else {
			if (this.isExistByName(newName)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public List<Project> findAll() {
		return projectExtendMapper.selectByAll();
	}
	
	@Override
	public Pagination findPage(Pagination page,  ProjectSo so) {
		page.setList(projectExtendMapper.selectByPage(page, so));
		return page;
	}
	

}
