package com.yuanxin.app.app.dto.request;

import java.util.List;

import com.yuanxin.app.app.appobject.ClientLogAO;

public class MobilDataRequest extends Request {
   
	public String clientData;

	public String getClientData() {
		return clientData;
	}

	public void setClientData(String clientData) {
		this.clientData = clientData;
	}
	

	public static void main(String[] args) {
		String arrStr="[{\"type\":\"event\",\"time\":\"1535453895254\",\"eventPath\":\"LoginActivity__activity_login_etloginCode\",\"appVersion\":\"3.1.0\",\"osVersion\":\"1\",\"vendor\":\"vivo\",\"model\":\"1\",\"uid\":\"6e857b468185467893288a53953087f7\",\"uname\":\"mmm\",\"ip\":\"192\",\"deviceId\":\"866352038826952\"}]" ;
		 List<ClientLogAO> tt = com.alibaba.fastjson.JSONObject.parseArray(arrStr, ClientLogAO.class);
		 System.err.println(tt.toString());
	
	}
	
}
