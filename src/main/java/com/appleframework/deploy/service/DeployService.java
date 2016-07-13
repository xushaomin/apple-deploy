package com.appleframework.deploy.service;

import com.appleframework.exception.AppleException;

public interface DeployService {

	public void doDeploy(Integer taskId) throws AppleException;
		
}
