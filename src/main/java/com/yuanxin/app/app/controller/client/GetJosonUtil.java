package com.yuanxin.app.app.controller.client;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetJosonUtil {
	
	
	public static String   getObjRelativeFiles(String objIds) {
        try {
        	 JSONObject jsonObj = new JSONObject();
             jsonObj.put("IsPost", true);
             jsonObj.put("IsProxy", false);
             JSONArray jarr = new JSONArray();
             JSONObject o = new JSONObject();
             o.put("Key", "objIdList");
             o.put("Value", objIds);
             jarr.put(o);
             jsonObj.put("PostParas", jarr);
             jsonObj.put("ServiceMethod", "ObjectWcfRestService.svc/GetObjRelativeFiles");
             jsonObj.put("Site", "restUriBase");
             jsonObj.put("UserInfo", "97bc30c0-9eb4-4e7a-bd1c-b26c949115a9");
            // System.out.println(jsonObj.toString());
            return jsonObj.toString();
        } catch (Exception e) {
        	//LOG.info("hhg", e.getMessage());
        }
        return null;
    }
	
	public static String   getPropertiesByObjIds(String objIds) {
        try {
        	 JSONObject jsonObj = new JSONObject();
             jsonObj.put("IsPost", true);
             jsonObj.put("IsProxy", false);
             JSONArray jarr = new JSONArray();
             JSONObject o = new JSONObject();
             o.put("Key", "objIdList");
             o.put("Value", objIds);
             jarr.put(o);
             jsonObj.put("PostParas", jarr);
             jsonObj.put("ServiceMethod", "ObjectWcfRestService.svc/GetPropertiesByObjIds");
             jsonObj.put("Site", "restUriBase");
             jsonObj.put("UserInfo", "97bc30c0-9eb4-4e7a-bd1c-b26c949115a9");
            // System.out.println(jsonObj.toString());
            return jsonObj.toString();
        } catch (Exception e) {
        	//LOG.info("hhg", e.getMessage());
        }
        return null;
    }
	
	public static void main(String[] args) {
		String objids= "[\"7D602EF2-1407-4CB5-9F96-BB1E57676B82\",\"44BF2C09-AE01-4EC1-8ADB-A4F3014E5591\"]";
		GetJosonUtil getJosonUtil = new  GetJosonUtil();
		getJosonUtil.getObjRelativeFiles(objids);
	}

}
