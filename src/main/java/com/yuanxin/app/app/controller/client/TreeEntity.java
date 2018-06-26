package com.yuanxin.app.app.controller.client;

public class TreeEntity {
	private String id;
	private String pid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	@Override
	public String toString() {
		return "TreeEntity [id=" + id + ", pid=" + pid + "]";
	}
	
	
	
	

}
