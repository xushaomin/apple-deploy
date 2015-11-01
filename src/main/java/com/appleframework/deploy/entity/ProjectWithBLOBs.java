package com.appleframework.deploy.entity;

import java.io.Serializable;

public class ProjectWithBLOBs extends Project implements Serializable {
    
	private String hosts;

    private String preDeploy;

    private String postDeploy;

    private String afterDeploy;

    private static final long serialVersionUID = 1L;

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts == null ? null : hosts.trim();
    }

    public String getPreDeploy() {
        return preDeploy;
    }

    public void setPreDeploy(String preDeploy) {
        this.preDeploy = preDeploy == null ? null : preDeploy.trim();
    }

    public String getPostDeploy() {
        return postDeploy;
    }

    public void setPostDeploy(String postDeploy) {
        this.postDeploy = postDeploy == null ? null : postDeploy.trim();
    }

    public String getAfterDeploy() {
        return afterDeploy;
    }

    public void setAfterDeploy(String afterDeploy) {
        this.afterDeploy = afterDeploy == null ? null : afterDeploy.trim();
    }

}