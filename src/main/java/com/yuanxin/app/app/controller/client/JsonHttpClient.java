package com.yuanxin.app.app.controller.client;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.impl.client.DefaultHttpClient;  


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
public class JsonHttpClient {
	
	private static String apiURL = "http://puer.wizplant.online:9111/Mobile/RestServicePortal.svc/DoAction";  
    private HttpClient httpClient = null;  
    private HttpPost method = null;  
    private long startTime = 0L;  
    private long endTime = 0L;  
    private int status = 0;  
  
    /**  
     * 接口地址  
     *  
     * @param url  
     */  
    public JsonHttpClient(String url) {  
  
        if (url != null) {  
            this.apiURL = url;  
        }  
        if (apiURL != null) {  
            httpClient = new DefaultHttpClient();  
            method = new HttpPost(apiURL);  
  
        }  
    }  
  
    /**  
     * 调用 API  
     *  
     * @param parameters  
     * @return  
     */  
    public String post(String parameters) {  
        String body = null;  
        if (method != null & parameters != null  
                && !"".equals(parameters.trim())) {  
            try {  
  
                // 建立一个NameValuePair数组，用于存储欲传送的参数  
                method.addHeader("Content-type","application/json; charset=utf-8");  
                method.setHeader("Accept", "application/json");  
                method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));  
                startTime = System.currentTimeMillis();  
  
                HttpResponse response = httpClient.execute(method);  
  
                endTime = System.currentTimeMillis();  
                int statusCode = response.getStatusLine().getStatusCode();  
  
  
                if (statusCode != HttpStatus.SC_OK) {  
                    status = 1;  
                }  
                // Read the response body  
                body = EntityUtils.toString(response.getEntity());  
  
            } catch (IOException e) {  
                // 网络错误  
                status = 3;  
            }  
  
        }  
        return body;  
    }  
  
    public static void main(String[] args) {  
        JsonHttpClient ac = new JsonHttpClient(apiURL);  
        //创建参数列表  
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();
        object.put("Key","objId");  
        object.put("Value","1C34DC5D-8729-4652-8D6E-A4EA012A989D");  
        object1.put("Key","associationId");  
        object1.put("Value","3F67FD3c-A2F7-46D1-8E62-A379011D179D"); 
        object2.put("Key","associationRole");  
        object2.put("Value",1); 
        object3.put("Key","lastModifiedTime");  
        object3.put("Value","/Date(1294499956278+0800)/"); 
        JSONArray jsonArray =new JSONArray();
        jsonArray.add(object);
        jsonArray.add(object1);
        jsonArray.add(object2);
        jsonArray.add(object3);
        JSONObject res = new JSONObject(); 
        res.put("IsPost",true);
        res.put("IsProxy",false);
        res.put("PostParas",jsonArray.toString());  
        res.put("ServiceMethod","ObjectWcfRestService.svc/GetAllAssociationObjects");
        res.put("Site","restUriBase");
        res.put("TemplateParas",null);
        res.put("Url",null);
        res.put("UserInfo","97bc30c0-9eb4-4e7a-bd1c-b26c949115a9");
        System.out.println(ac.post(res.toString()));  
    }  
  
    /**  
     * 0.成功 1.执行方法失败 2.协议错误 3.网络错误  
     *  
     * @return the status  
     */  
    public int getStatus() {  
        return status;  
    }  
  
    /**  
     * @param status  
     *            the status to set  
     */  
    public void setStatus(int status) {  
        this.status = status;  
    }  
  
    /**  
     * @return the startTime  
     */  
    public long getStartTime() {  
        return startTime;  
    }  
  
    /**  
     * @return the endTime  
     */  
    public long getEndTime() {  
        return endTime;  
    }  

}
