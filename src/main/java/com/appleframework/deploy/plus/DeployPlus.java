package com.appleframework.deploy.plus;

import com.appleframework.deploy.entity.ProjectWithBLOBs;
import com.appleframework.deploy.entity.Task;

public interface DeployPlus {

	public void doDeploy(Task task, ProjectWithBLOBs project);
}
