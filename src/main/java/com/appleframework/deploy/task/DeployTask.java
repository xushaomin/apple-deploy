package com.appleframework.deploy.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.appleframework.deploy.entity.ProjectWithBLOBs;
import com.appleframework.deploy.service.ProjectService;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Component
public class DeployTask {

	@Resource
	private ProjectService projectService;
		
	//@Scheduled(cron = "0/30 * * * * ?")
    public void deploy() {
		ProjectWithBLOBs project = projectService.get(1);
		String hostStr = project.getHosts();
		String hosts[] = hostStr.trim().split(",");
		for (String host : hosts) {
			StringBuffer commandBuffer = new StringBuffer();
			commandBuffer.append("mkdir -p " + project.getReleaseTo() + "\n");
			commandBuffer.append("/work/shell/deploy/deploy.sh ");
			commandBuffer.append(project.getReleaseTo() + " ");
			commandBuffer.append(project.getNexusUrl() + " ");
			commandBuffer.append(project.getNexusGroup() + " ");
			commandBuffer.append(project.getNexusArtifact() + " ");
			commandBuffer.append(project.getVersion() + " ");

			String result = "";
			Session session = null;
			ChannelExec openChannel = null;
			try {
				JSch jsch = new JSch();
				session = jsch.getSession(project.getReleaseUser(), host, 22);
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				session.setPassword("123456");
				session.connect();
				openChannel = (ChannelExec) session.openChannel("exec");
				openChannel.setCommand(commandBuffer.toString());
				int exitStatus = openChannel.getExitStatus();
				System.out.println(exitStatus);
				openChannel.connect();
				InputStream in = openChannel.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String buf = null;
				while ((buf = reader.readLine()) != null) {
					result += new String(buf.getBytes("utf-8"), "UTF-8") + "\r\n";
					System.out.println(result);
				}
			} catch (Exception e) {
				result += e.getMessage();
				System.out.println(result);
			} finally {
				if (openChannel != null && !openChannel.isClosed()) {
					openChannel.disconnect();
				}
				if (session != null && session.isConnected()) {
					session.disconnect();
				}
			}
			System.out.println("---------------------------------------------------------OK");
		}
    }
}
