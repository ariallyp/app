package com.yuanxin.app.app.controller.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
public class HttpClientUtil {
	    
	  public static String httpPost(String httpUrl, String data, int connectTimeout, int readTimeout) throws IOException
	    {
	        OutputStream outPut = null;
	        HttpURLConnection urlConnection = null;
	        InputStream in = null;
	        
	        try
	        {
	            URL url = new URL(httpUrl);
	            urlConnection = (HttpURLConnection)url.openConnection();          
	            urlConnection.setRequestMethod("POST");
	            urlConnection.setDoOutput(true);
	            urlConnection.setDoInput(true);
	            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
	            urlConnection.setConnectTimeout(connectTimeout);
	            urlConnection.setReadTimeout(readTimeout);
	            urlConnection.connect();
	            
	            // POST data
	            outPut = urlConnection.getOutputStream();
	            outPut.write(data.getBytes("UTF-8"));
	            outPut.flush();
	            
	            // read response
	            if (urlConnection.getResponseCode() < 400)
	            {
	                in = urlConnection.getInputStream();
	            }
	            else
	            {
	                in = urlConnection.getErrorStream();
	            }
	            
	            List<String> lines = IOUtils.readLines(in, urlConnection.getContentEncoding());
	            StringBuffer strBuf = new StringBuffer();
	            for (String line : lines)
	            {
	                strBuf.append(line);
	            }
	            System.out.println(strBuf.toString());
	           
	            return strBuf.toString();
	        }
	        finally
	        {
	            IOUtils.closeQuietly(outPut);
	            IOUtils.closeQuietly(in);
	            if (urlConnection != null)
	            {
	                urlConnection.disconnect();
	            }
	        }
	    }


	  
	  public static String call(final String URL) {
	        String result = null;
	        HttpURLConnection conn = null;
	        try {
	            URL target = new URL(URL);
	            conn = (HttpURLConnection) target.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Accept", "application/json");
	            conn.setConnectTimeout(10000);
	            conn.setReadTimeout(10000);
	            if (200 != conn.getResponseCode()) {
	               result="连接接口出现错误";
	            }
	            byte[] temp = new byte[conn.getInputStream().available()];
	            if (conn.getInputStream().read(temp) != -1) {
	                result = new String(temp);
	            }
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            conn.disconnect();
	        }
	        return result;
	    }
	  
	  public static String sendGet(String url) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 建立实际的连接
	            connection.connect();
	            // 获取所有响应头字段
	         /*   Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
	            for (String key : map.keySet()) {
	                System.out.println(key + "--->" + map.get(key));
	            }*/
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送GET请求出现异常！" + e);
	            e.printStackTrace();
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }
	  
	  
	  
	  public static void main(String[] args) {
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String urlGet1="http://puer.wizplant.online:8087/CsgiiSBTZOfflineDataService.ashx?userToken=1&methodName=basicinfo&siteContext=XMH";
		String urlGet2="http://puer.wizplant.online:8087/CsgiiSBTZOfflineDataService.ashx?userToken=1&methodName=techinfo&siteContext=XMH";
		String urlGet3="http://puer.wizplant.online:8087/CsgiiSBTZOfflineDataService.ashx?userToken=1&methodName=csbg&siteContext=XMH";
		String urlGet4="http://puer.wizplant.online:8087/CsgiiSBTZOfflineDataService.ashx?userToken=1&methodName=yxqxjl&siteContext=XMH";
		String urlGet5="http://puer.wizplant.online:8087/CsgiiSBTZOfflineDataService.ashx?userToken=1&methodName=ztpj&siteContext=XMH";
		
		//String result =httpClientUtil.call(urlGet1);
		System.err.println(httpClientUtil.sendGet(urlGet1));
	}
	  
	  
	  
	  
	  
	  
}
