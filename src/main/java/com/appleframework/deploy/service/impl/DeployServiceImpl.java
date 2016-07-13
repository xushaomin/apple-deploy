package com.appleframework.deploy.service.impl;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.appleframework.deploy.entity.ProjectWithBLOBs;
import com.appleframework.deploy.entity.Task;
import com.appleframework.deploy.model.PlusType;
import com.appleframework.deploy.model.StatusType;
import com.appleframework.deploy.plus.DeployPlus;
import com.appleframework.deploy.plus.DeployPlusFactory;
import com.appleframework.deploy.service.DeployService;
import com.appleframework.deploy.service.ProjectService;
import com.appleframework.deploy.service.TaskService;
import com.appleframework.deploy.utils.Constants;
import com.appleframework.deploy.websocket.WebSocketServer;
import com.appleframework.exception.AppleException;

@Service("deployService")
public class DeployServiceImpl implements DeployService {
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private ProjectService projectService;
	
	@Resource
	private TaskExecutor taskExecutor;
		
	@Async
	public void doDeploy(final Integer taskId) throws AppleException {
		
		taskExecutor.execute(new Runnable() {
            
            @Override  
            public void run() {
            	try {
            		Thread.sleep(1000);
            		Constants.BOOT_STATUS_MAP.put(taskId, false);
            		Task task = taskService.get(taskId);
            		task.setStatus(StatusType.DEPLOYING.getIndex());
            		taskService.update(task);
            		ProjectWithBLOBs project = projectService.get(task.getProjectId());
            		
            		PlusType plusType = PlusType.create(project.getPlus());
            		DeployPlus deployPlus = DeployPlusFactory.create(plusType.name());
            		deployPlus.doDeploy(task, project);

            		Constants.BOOT_STATUS_MAP.put(taskId, true);
            		WebSocketServer.sendMessage(taskId, "已经完成所有部署!!!");
            		
            		task.setStatus(StatusType.SUCCESS.getIndex());
            		taskService.update(task);
            		
            		project.setVersion(task.getVersion());
            		projectService.update(project);
				} catch (Exception e) {
					WebSocketServer.sendMessage(taskId, "部署出错：" + e.getMessage());
				}
            	
            }  
        }); 
		
		
	}
	
}
