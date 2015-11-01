package com.appleframework.deploy.model;

import java.io.Serializable;

public class ProjectSo implements Serializable {

	private static final long serialVersionUID = -2819301403023915774L;
	
	private String name;
	private Integer env;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEnv() {
		return env;
	}

	public void setEnv(Integer env) {
		this.env = env;
	}

}
