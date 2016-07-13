package com.appleframework.deploy.plus;

import java.util.HashMap;
import java.util.Map;

import com.appleframework.deploy.plus.shell.ShellDeployPlus;
import com.appleframework.deploy.plus.ssh.SshDeployPlus;

public class DeployPlusFactory {

	private static Map<String, DeployPlus> deployPlusMap = new HashMap<String, DeployPlus>();

	public static DeployPlus create(String plus) {
		DeployPlus deployPlus = deployPlusMap.get(plus);
		if (null == deployPlus) {
			if (plus.equals("ssh")) {
				deployPlus = new SshDeployPlus();
			} else {
				deployPlus = new ShellDeployPlus();
			}
			deployPlusMap.put(plus, deployPlus);
		}
		return deployPlus;
	}

}