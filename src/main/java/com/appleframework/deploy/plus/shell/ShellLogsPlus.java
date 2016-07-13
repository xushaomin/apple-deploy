package com.appleframework.deploy.plus.shell;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.appleframework.config.core.util.StringUtils;
import com.appleframework.deploy.entity.ProjectWithBLOBs;
import com.appleframework.deploy.entity.Task;
import com.appleframework.deploy.model.DeployType;
import com.appleframework.deploy.plus.DeployPlus;
import com.appleframework.deploy.utils.Constants;
import com.appleframework.deploy.websocket.WebSocketServer;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class ShellLogsPlus implements DeployPlus {
	
	private static String APPEND = "----------";

	public void doDeploy(Task task, ProjectWithBLOBs project) {
		Constants.BOOT_STATUS_MAP.put(task.getId(), false);

		String hostStr = project.getHosts();
		String hosts[] = com.appleframework.deploy.utils.StringUtils.replaceBlank(hostStr).split(",");
		for (String host : hosts) {
			StringBuffer commandBuffer = new StringBuffer();
			commandBuffer.append("mkdir -p " + project.getReleaseTo() + "\n");

			// pre deploy
			if(!StringUtils.isEmpty(project.getPreDeploy())) {
				commandBuffer.append(project.getPreDeploy() + "\n");
			}
			
			// post deploy
			if (project.getType() == DeployType.NEXUS.getIndex()) {
				commandBuffer.append(project.getPostDeploy() + " ");
				commandBuffer.append(project.getReleaseTo() + " ");
				commandBuffer.append(project.getNexusUrl() + " ");
				commandBuffer.append(project.getNexusGroup() + " ");
				commandBuffer.append(project.getNexusArtifact() + " ");
				commandBuffer.append(project.getVersion() + " ");
			}
			else {
				if(!StringUtils.isEmpty(project.getPostDeploy())) {
					commandBuffer.append(project.getPostDeploy() + "\n");
				}
			}
			
			// after deploy
			if(!StringUtils.isEmpty(project.getAfterDeploy())) {
				commandBuffer.append(project.getAfterDeploy() + "\n");
			}
			
			Session session = null;
			ChannelExec openChannel = null;
			try {
				JSch jsch = new JSch();
				jsch.addIdentity("/root/.ssh/id_dsa");
				session = jsch.getSession(project.getReleaseUser(), host, 22);
				java.util.Properties config = new java.util.Properties();
				//设置第一次登陆的时候提示，可选值：(ask | yes | no)
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				//session.setPassword("123456");
				session.connect();
				openChannel = (ChannelExec) session.openChannel("exec");
				openChannel.setCommand(commandBuffer.toString());
				int exitStatus = openChannel.getExitStatus();
				if(exitStatus != -1) {
					WebSocketServer.sendMessage(task.getId(), host + APPEND + "认证失败:" + exitStatus);
				}
				openChannel.connect();
				InputStream in = openChannel.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String buf = null;
				while ((buf = reader.readLine()) != null) {
					String result = new String(buf.getBytes("utf-8"), "UTF-8") + "\r\n";

					System.out.print(host + "-------------" + result);
					
					WebSocketServer.sendMessage(task.getId(), host + "-------------" + result);
					
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
				WebSocketServer.sendMessage(task.getId(), host + APPEND + e.getMessage());
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
	}
}
