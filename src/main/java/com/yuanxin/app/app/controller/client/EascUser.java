package com.yuanxin.app.app.controller.client;

public class EascUser {
	
	private String uid;
	private String userName;
	private String nickName;
	private String orgName;
	private String tenantId;
	private String token;
	private String message;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "EascUser [uid=" + uid + ", userName=" + userName + ", nickName=" + nickName + ", orgName=" + orgName
				+ ", tenantId=" + tenantId + ", token=" + token + ", message=" + message + "]";
	}
	
	

}
