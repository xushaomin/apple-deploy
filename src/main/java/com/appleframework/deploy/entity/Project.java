package com.appleframework.deploy.entity;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable {
	
    private Integer id;

    private String name;

    private Integer type;
    
    private Integer env;

    private Integer status;

    private String version;

    private String nexusUrl;

    private String nexusGroup;

    private String nexusArtifact;

    private String releaseUser;

    private String releaseTo;

    private Boolean isAudit;

    private Date createAt;

    private Date updateAt;

    private String createBy;

    private String updateBy;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getNexusUrl() {
        return nexusUrl;
    }

    public void setNexusUrl(String nexusUrl) {
        this.nexusUrl = nexusUrl == null ? null : nexusUrl.trim();
    }

    public String getNexusGroup() {
        return nexusGroup;
    }

    public void setNexusGroup(String nexusGroup) {
        this.nexusGroup = nexusGroup == null ? null : nexusGroup.trim();
    }

    public String getNexusArtifact() {
        return nexusArtifact;
    }

    public void setNexusArtifact(String nexusArtifact) {
        this.nexusArtifact = nexusArtifact == null ? null : nexusArtifact.trim();
    }

    public String getReleaseUser() {
        return releaseUser;
    }

    public void setReleaseUser(String releaseUser) {
        this.releaseUser = releaseUser == null ? null : releaseUser.trim();
    }

    public String getReleaseTo() {
        return releaseTo;
    }

    public void setReleaseTo(String releaseTo) {
        this.releaseTo = releaseTo == null ? null : releaseTo.trim();
    }

    public Boolean getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Boolean isAudit) {
        this.isAudit = isAudit;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

	public Integer getEnv() {
		return env;
	}

	public void setEnv(Integer env) {
		this.env = env;
	}
    
}