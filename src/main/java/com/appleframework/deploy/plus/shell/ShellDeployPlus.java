package com.appleframework.deploy.plus.shell;

import com.appleframework.config.core.util.StringUtils;
import com.appleframework.deploy.entity.ProjectWithBLOBs;
import com.appleframework.deploy.entity.Task;
import com.appleframework.deploy.model.DeployParam;
import com.appleframework.deploy.model.DeployType;
import com.appleframework.deploy.model.EnvType;
import com.appleframework.deploy.plus.DeployPlus;
import com.appleframework.deploy.utils.Constants;
import com.appleframework.deploy.utils.ShellUtil;
import com.appleframework.deploy.websocket.WebSocketServer;
import com.taobao.diamond.utils.JSONUtils;

public class ShellDeployPlus implements DeployPlus {
	
	public void doDeploy(Task task, ProjectWithBLOBs project) {
		Constants.BOOT_STATUS_MAP.put(task.getId(), false);
		try {
			// pre deploy
			if (!StringUtils.isEmpty(project.getPreDeploy())) {
				WebSocketServer.sendMessage(task.getId(), ShellUtil.exec(project.getPreDeploy()) + "\n");
			}

			// post deploy
			if (project.getType() == DeployType.NEXUS.getIndex()) {

				String hostStr = task.getHosts();
				String hosts[] = com.appleframework.deploy.utils.StringUtils.replaceBlank(hostStr).split(",");
				
				DeployParam param = new DeployParam();
				param.setHosts(hosts);
				param.setArtifactId(project.getNexusArtifact());
				param.setGroupId(project.getNexusGroup());
				EnvType envType = EnvType.create(project.getEnv());
				param.setEnv(envType.name().toLowerCase());
				param.setInstallPath(project.getReleaseTo());
				param.setOption("deploy");
				param.setVersion(task.getVersion());

				String paramJson = JSONUtils.serializeObject(param);
				WebSocketServer.sendMessage(task.getId(), ShellUtil.exec(project.getPostDeploy(), paramJson));

			} else {
				if (!StringUtils.isEmpty(project.getPostDeploy())) {
					WebSocketServer.sendMessage(task.getId(), ShellUtil.exec(project.getPostDeploy()) + "\n");
				}
			}

			// after deploy
			if (!StringUtils.isEmpty(project.getAfterDeploy())) {
				WebSocketServer.sendMessage(task.getId(), ShellUtil.exec(project.getAfterDeploy()) + "\n");
			}

			WebSocketServer.sendMessage(task.getId(), "OK");

		} catch (Exception e) {
			WebSocketServer.sendMessage(task.getId(), "部署出错，" + e.getMessage());
		}

	}
}
