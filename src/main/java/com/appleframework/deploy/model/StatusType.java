package com.appleframework.deploy.model;

//状态1新建提交，2审核通过，3审核拒绝，4上线中，5上线完成，6上线失败
public enum StatusType {

	NEW("新建提交", 1), 
	PASS("审核通过", 2), 
	REFUSE("审核拒绝", 3), 
	DEPLOYING("上线中", 4), 
	SUCCESS("上线完成", 5), 
	FAILURE("上线失败", 6);

	// 成员变量
	private String name;
	private Integer index;

	// 构造方法
	private StatusType(String name, Integer index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(Integer index) {
		for (StatusType c : StatusType.values()) {
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
