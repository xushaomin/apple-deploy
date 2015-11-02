package com.appleframework.deploy.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.appleframework.config.core.util.StringUtils;
import com.appleframework.deploy.entity.ProjectWithBLOBs;
import com.appleframework.deploy.entity.Task;
import com.appleframework.deploy.model.DeployType;
import com.appleframework.deploy.model.StatusType;
import com.appleframework.deploy.service.DeployService;
import com.appleframework.deploy.service.ProjectService;
import com.appleframework.deploy.service.TaskService;
import com.appleframework.deploy.utils.Constants;
import com.appleframework.deploy.websocket.WebSocketServer;
import com.appleframework.exception.AppleException;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Service("deployService")
public class DeployServiceImpl implements DeployService {
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private ProjectService projectService;
	
	@Resource
	private TaskExecutor taskExecutor;
	
	private static String APPEND = "----------";
	
	public void doDeploy(final Integer taskId) throws AppleException {
		taskExecutor.execute(new Runnable() {  
            @Override  
            public void run() {  
            	try {
            		Thread.sleep(3000);
					doDeployTask(taskId);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }  
        }); 
	}
	
	
	@Async
	private void doDeployTask(Integer taskId) throws AppleException {
		Constants.BOOT_STATUS_MAP.put(taskId, false);
		Task task = taskService.get(taskId);
		task.setStatus(StatusType.DEPLOYING.getIndex());
		taskService.update(task);
		ProjectWithBLOBs project = projectService.get(task.getProjectId());
		
		String hostStr = project.getHosts();
		String hosts[] = hostStr.trim().split(",");
		for (String host : hosts) {
			StringBuffer commandBuffer = new StringBuffer();
			commandBuffer.append("mkdir -p " + project.getReleaseTo() + "\n");

			if(!StringUtils.isEmpty(project.getPreDeploy())) {
				commandBuffer.append(project.getPreDeploy() + "\n");
			}
			if (project.getType() == DeployType.NEXUS.getIndex()) {
				commandBuffer.append(project.getPostDeploy() + " ");
				commandBuffer.append(project.getReleaseTo() + " ");
				commandBuffer.append(project.getNexusUrl() + " ");
				commandBuffer.append(project.getNexusGroup() + " ");
				commandBuffer.append(project.getNexusArtifact() + " ");
				commandBuffer.append(project.getVersion() + " ");
			}
			
			Session session = null;
			ChannelExec openChannel = null;
			try {
				JSch jsch = new JSch();
				session = jsch.getSession(project.getReleaseUser(), host, 22);
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				//session.setPassword("Jn+XSK!H");
				//session.setPassword("123456");
				session.connect();
				openChannel = (ChannelExec) session.openChannel("exec");
				openChannel.setCommand(commandBuffer.toString());
				int exitStatus = openChannel.getExitStatus();
				if(exitStatus != -1) {
					WebSocketServer.sendMessage(taskId, host + APPEND + "认证失败:" + exitStatus);
				}
				openChannel.connect();
				InputStream in = openChannel.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String buf = null;
				while ((buf = reader.readLine()) != null) {
					String result = new String(buf.getBytes("utf-8"), "UTF-8") + "\r\n";

					System.out.print(host + "-------------" + result);
					
					WebSocketServer.sendMessage(taskId, host + "-------------" + result);
					
					if (project.getType() == DeployType.NEXUS.getIndex()) {
						//判断启动成功
						for (String key : Constants.BOOT_KEY_SUCCESS_LIST) {
							if (result.indexOf(key) > -1) {
								if (openChannel != null && !openChannel.isClosed()) {
									openChannel.disconnect();
								}
								if (session != null && session.isConnected()) {
									session.disconnect();
								}
							} 
						}
						//判断启动失败
						for (String key : Constants.BOOT_KEY_FAILURE_LIST) {
							if (result.indexOf(key) > -1) {
								if (openChannel != null && !openChannel.isClosed()) {
									openChannel.disconnect();
								}
								if (session != null && session.isConnected()) {
									session.disconnect();
								}
							} 
						}
					}
					else {
						System.out.println();
					}
				}
			} catch (Exception e) {
				//result += e.getMessage();
				System.out.println(e.getMessage());
				WebSocketServer.sendMessage(taskId, host + APPEND + e.getMessage());
			} finally {
				if (openChannel != null && !openChannel.isClosed()) {
					openChannel.disconnect();
				}
				if (session != null && session.isConnected()) {
					session.disconnect();
				}
			}
			
			/*if(isSuccess) {
				
			}*/
			
		}
		Constants.BOOT_STATUS_MAP.put(taskId, true);
		WebSocketServer.sendMessage(taskId, "已经完成所有部署!!!");
		
		task.setStatus(StatusType.SUCCESS.getIndex());
		taskService.update(task);
		
		project.setVersion(task.getVersion());
		projectService.update(project);
	}
	

}
