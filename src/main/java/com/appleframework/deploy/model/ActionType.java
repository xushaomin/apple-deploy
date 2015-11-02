package com.appleframework.deploy.model;

public enum ActionType {

	NEWDEPLOY("全新部署", (int)1),
	ROLLBACK("回滚", (int)2);
	
	// 成员变量
	private String name;
	private Integer index;

	// 构造方法
	private ActionType(String name, Integer index) {
		this.name = name;
		this.index = index;
	}
	
	// 普通方法
	public static String getName(Integer index) {
		for (ActionType c : ActionType.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}	

}
