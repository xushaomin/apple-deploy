package com.appleframework.deploy.model;

public enum PlusType {

	SHELL("SHELL脚本", 1),
	SSH("远程SSH", 2);
	
	// 成员变量
	private String name;
	private Integer index;

	// 构造方法
	private PlusType(String name, Integer index) {
		this.name = name;
		this.index = index;
	}
	
	// 普通方法
	public static String getName(Integer index) {
		for (PlusType c : PlusType.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}
	
	public static PlusType create(Integer index) {
		for (PlusType c : PlusType.values()) {
			if (c.getIndex() == index) {
				return c;
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
