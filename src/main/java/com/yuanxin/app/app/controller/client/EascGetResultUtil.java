package com.yuanxin.app.app.controller.client;



import com.yuanxin.framework.logging.Logger;
import com.yuanxin.framework.logging.LoggerFactory;

import top.wiz.common.easc.EascAppHelper;
import top.wiz.common.easc.EascAuthHelper;
import top.wiz.common.easc.model.dto.Result;

public class EascGetResultUtil {

	private static Logger LOG = LoggerFactory.getLogger(EascGetResultUtil.class);


	public static Result getOrgListFromEasc(EascParam eascParam) {
		Result result = EascAppHelper.getAllOrg(eascParam.domain, eascParam.port, eascParam.appId,
				eascParam.getUserId());
		
		
		return result;

	}

	public static Result getUserListFromEasc(EascParam eascParam) {

		Result result = EascAppHelper.getAppUser(eascParam.domain, eascParam.port, eascParam.appId,
				eascParam.getUserId());
		
		return result;

	}

	public static Result getOrgUserListFromEasc(EascParam eascParam) {
		Result result = EascAppHelper.getAllOrgUser(eascParam.domain, eascParam.port, eascParam.appId,
				eascParam.getUserId());
		
		return result;

	}

	public static void main(String[] args) {
		String domain = "192.168.1.70";
		String appId = "d371c4d1-c151-4d49-bcc5-7798875ed4d4";
		// String token=EascGetResultUtil.getUid(domain, "2002", appId);
		EascParam eascParam = new EascParam();
		eascParam.setAppId(appId);
		eascParam.setDomain(domain);
		eascParam.setPort("2002");
		eascParam.setUserName("MOY4915");
		eascParam.setPwd("25F9E794323B453885F5181F1B624D0B");
		eascParam.setUserId("bc4ba096-ac22-4f68-b43d-9499679ede7a");
	/*	Result result = EascAuthHelper.login(domain, eascParam.port, eascParam.userName, eascParam.pwd, appId);
		Object data = result.getData();
			JSONObject jsonObject = JSONObject.fromObject(data);
		String 	userId = jsonObject.getString("userId");
			eascParam.setUserId(userId);*/
		
		//List<EascOrg> list =EascGetResultUtil.getOrgListFromEasc(eascParam);
		//Result result = EascAuthHelper.login(domain, eascParam.port, eascParam.userName, eascParam.pwd, appId);
		
		//Result result =EascAuthHelper.ssoLoginByUserId(domain, eascParam.port, "1480483a-33e0-4f2a-8d30-23cc9a103ace", appId);
		Result result =EascAuthHelper.login(domain, "2002", "ZHANGXN0343", eascParam.getPwd(), "d371c4d1-c151-4d49-bcc5-7798875ed4d4");
		System.err.println(result);
		// "2002", appId, token);

	}
}
