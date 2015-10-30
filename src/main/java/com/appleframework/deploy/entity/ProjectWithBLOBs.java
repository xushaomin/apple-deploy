package com.appleframework.deploy.entity;

import java.io.Serializable;

public class ProjectWithBLOBs extends Project implements Serializable {
    private String hosts;

    private String preDeploy;

    private String postDeploy;

    private String preRelease;

    private String postRelease;

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

    public String getPreRelease() {
        return preRelease;
    }

    public void setPreRelease(String preRelease) {
        this.preRelease = preRelease == null ? null : preRelease.trim();
    }

    public String getPostRelease() {
        return postRelease;
    }

    public void setPostRelease(String postRelease) {
        this.postRelease = postRelease == null ? null : postRelease.trim();
    }
}