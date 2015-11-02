package com.appleframework.deploy.model;

import java.io.Serializable;

public class TaskSo implements Serializable {

	private static final long serialVersionUID = -2819301403023915774L;

	private String title;
	private Integer projectId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

}
