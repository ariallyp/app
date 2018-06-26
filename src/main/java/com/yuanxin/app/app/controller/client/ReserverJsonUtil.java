package com.yuanxin.app.app.controller.client;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yuanxin.app.app.appobject.AssociationObjects;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
public class ReserverJsonUtil {

	public static Map<String, String>  readJsonGetResult(String jsonResult){  
        JSONObject jsonObj = JSONObject.fromObject(jsonResult);  
        Map< String, String> map = new HashMap<>();
        map.put("Result", jsonObj.get("Result").toString());
        map.put("RealData", jsonObj.get("RealData").toString());
        
        return map;
		
        
    }  
	
	
	public static String  readJsonGetResultArray(String realData,String objId){  
		JSONArray jsonArr = JSONArray.fromObject(realData);  
		 Iterator<Object> it = jsonArr.iterator();  
	        while(it.hasNext()){  
	            JSONObject obj = (JSONObject)it.next();
	            obj.get("ObjId");
	            System.out.println(obj.get("ObjId"));  
	        }  
       
        
        
        return null;
		
        
    }
	
	public static String  readJsonGetAssIDFromData(String realData,String objId){  
		JSONArray jsonArr = JSONArray.fromObject(realData);  
		 Iterator<Object> it = jsonArr.iterator();
		 String assId=objId;
	        while(it.hasNext()){  
	            JSONObject obj = (JSONObject)it.next();
	        /*   if (objId.equalsIgnoreCase(obj.get("ObjId"))) {
				
			}*/
	            System.out.println(obj.get("ObjId"));  
	        }  
       
        
        
        return null;
		
        
    }
	
	
	
	
	   public static String getJsonObject1(String objid){
           JSONObject object = new JSONObject();
           JSONObject object1 = new JSONObject();
           JSONObject object2 = new JSONObject();
           JSONObject object3 = new JSONObject();
           object.put("Key","objId");  
           object.put("Value",objid);  
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
           
          // System.out.println(res.toString());
		   
		   return res.toString();
		   
		   
		   
	   }
	   public static String getJsonObject2(String objid2){
		   System.out.println(objid2);
           JSONObject object = new JSONObject();
           //objid2=objid2.replace("[", "").replaceAll("]", "");
           String[] array1 =objid2.split(",");
           StringBuffer sBuffer = new StringBuffer();
           sBuffer.append("");
           String ids=""; 
           for (int i = 0; i < array1.length; i++) {
        		/*   sBuffer.append(array1[i].replaceAll("\"","\\\\\"" ));
        		   if (i<array1.length-1) {
        			   sBuffer.append(",");
        		   }*/
        	   
        	 
               
        		   
           }
           ids="["+ids+"]";
           System.out.println(ids);
           System.out.println(sBuffer.toString());
           object.put("Key","objIdList");  
           object.put("Value",sBuffer.toString());  
           JSONArray jsonArray =new JSONArray();
           jsonArray.add(object);
           JSONObject res = new JSONObject(); 
           
           res.put("IsPost",true);
           res.put("IsProxy",false);
           
           res.put("PostParas",jsonArray.toString());  
           
           res.put("ServiceMethod","ObjectWcfRestService.svc/GetPropertiesByObjIds");
           res.put("Site","restUriBase");
           res.put("TemplateParas",null);
           res.put("Url",null);
           res.put("UserInfo","97bc30c0-9eb4-4e7a-bd1c-b26c949115a9");
           
           //System.out.println(res.toString());
		   
		   return res.toString();
		   
		   
		   
	   }
	   
	   public static String getJsonObject3(String objid3){
		   JSONObject object = new JSONObject();
           object.put("Key","objIds");  
           object.put("Value",objid3);  
           JSONArray jsonArray =new JSONArray();
           jsonArray.add(object);
           JSONObject res = new JSONObject();
           
           res.put("IsPost",true);
           res.put("IsProxy",false);
           
           res.put("PostParas",jsonArray.toString());
           
           res.put("ServiceMethod","ObjectWcfRestService.svc/GetObjRelativeFiles");
           res.put("Site","restUriBase");
           res.put("TemplateParas",null);
           res.put("Url",null);
           res.put("UserInfo","97bc30c0-9eb4-4e7a-bd1c-b26c949115a9");
           
           //System.out.println(res.toString());
		   
		   
		   return res.toString();
		   
		   
		   
	   }
	   public static void main(String[] args) {
		   List<TreeEntity> treeList = null;
		   treeList = new ArrayList<TreeEntity>();
		   List pids = new ArrayList<>();
		   List childIds = new ArrayList<>();
		   StringBuffer sBuffer = new StringBuffer();
		 String arrStr1 = "[{\"id\":\"1\",\"pid\":\"0\"},{\"id\":\"2\",\"pid\":\"1\"},{\"id\":\"3\",\"pid\":\"1\"},{\"id\":\"4\",\"pid\":\"2\"},{\"id\":\"5\",\"pid\":\"2\"},{\"id\":\"6\",\"pid\":\"4\"}]";  
			JSONArray jsonArr1 = JSONArray.fromObject(arrStr1);  
	        List<?>  temAraay1 =jsonArr1.toList(jsonArr1, new TreeEntity(),new JsonConfig());
	        System.err.println(temAraay1.toString());
		 
		 
		String arrStr="[{\"AssociationRole\":\"SourceObjectId\",\"AssociativeObjId\":\"0\",\"ClassId\":\"aa\",\"Name\":\"10kV电压等级区域\",\"ObjId\":\"1\"},{\"AssociationRole\":\"SourceObjectId\",\"AssociativeObjId\":\"1\",\"ClassId\":\"bb\",\"Name\":\"110kV#1主变区域\",\"ObjId\":\"2\"},{\"AssociationRole\":\"SourceObjectId\",\"AssociativeObjId\":\"1\",\"ClassId\":\"bb\",\"Name\":\"110kV#1主变区域\",\"ObjId\":\"3\"}]"   ;
		/*arrStr=arrStr.replaceAll("ObjId", "objId");
		arrStr=arrStr.replaceAll("AssociativeObjId", "associativeObjId");*/
		JSONArray jsonArr = JSONArray.fromObject(arrStr);  
	        List<?>  temAraay =jsonArr.toList(jsonArr, new AssociationObjects(),new JsonConfig());
	        List<AssociationObjects> tt = com.alibaba.fastjson.JSONObject.parseArray(arrStr, AssociationObjects.class);
	        
	        System.err.println(temAraay.toString());
	     //  List list= ReserverJsonUtil.findChild("2", temAraay);
	      // List list2= ReserverJsonUtil.getAllChild("2", temAraay);
	        
	        
	        Iterator<Object> it = jsonArr.iterator();  
	      /*    List<Integer> aIntegers=new ArrayList<>();
	          aIntegers.stream().filter(item->{
	        	  return true;
	          }).collect(Collectors.toList())*/
	    /*    while(it.hasNext()){  
	            JSONObject obj = (JSONObject)it.next();  
	            pids.add(obj.get("pid"));
	            childIds.add(obj.get("id"));
	        }  
	        
	        List temlist= ReserverJsonUtil.compare(pids, childIds);
		       for (Object object : temlist) {
		    	   	System.err.println(object);
		       		}*/
	        
	        
	   }
	   
	   
	   public static  List compare(List t1, List t2) {    
		      List list1 =t1;    
		      List list2 = new ArrayList();       
		      for (Object t : t2) {    
		          if (!list1.contains(t)) {    
		              list2.add(t);    
		          }    
		      }    
		      return list2;    
		  }
	   

	    public static List getAllChild(String id,List<TreeEntity> downLoadListObj){
	        List<TreeEntity> list1 = findChild(id, downLoadListObj);//根据父节点的id查询
	        List<String> strings = null;
	        if (list1!=null && list1.size()>0){
	            for(TreeEntity mp:list1){
	                getAllChild(mp.getPid(),downLoadListObj);//对其子节点继续遍历，看是否包含子节点
	            }
	        }else {
	        
				strings.add(id);
	        }
			return strings;
	    }
	   
	   
	    public static List<TreeEntity> findChild(String id,List<TreeEntity> downLoadListObj){
	        List<TreeEntity> childList = new ArrayList<>();
	       
			for (TreeEntity object : downLoadListObj){
	            if (object.getPid().equalsIgnoreCase(id))
	            {
	                childList.add(object);
	            }
	        }
	        return childList;
	    }
}
