package com.yuanxin.app.app.controller.client;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.yuanxin.app.app.appobject.AssociationObjects;

import net.sf.json.JSONArray;

 
 
/**
 * 获取子节点
 */

 
public class NodeUtilForObjids {
     
    private static List<String> returnList = new ArrayList<String>();
    private static List<AssociationObjects> returnListAssObject = new ArrayList<AssociationObjects>();
     
    /**
     * 根据父节点的ID获取所有子节点
     * @param list 分类表
     * @param typeId 传入的父节点ID
     * @return String
     */
    public static List<String> getChildNodes(List<AssociationObjects> list, String typeId) {
        if(list == null && typeId == null) return null;
        if (!returnList.isEmpty()) {
        	returnList.clear();;
		}
        for (Iterator<AssociationObjects> iterator = list.iterator(); iterator.hasNext();) {
        	AssociationObjects node = (AssociationObjects) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
        	
            if ( typeId.equalsIgnoreCase(node.getAssociativeObjId())) {
                recursionFn(list, node);
            }
            // 二、遍历所有的父节点下的所有子节点
            /*if (node.getParentId()==0) {
                recursionFn(list, node);
            }*/
        }
        return returnList;
    }
     
    
    //获取子节点的id
    private static void recursionFn(List<AssociationObjects> list, AssociationObjects node) {
        List<AssociationObjects> childList = getChildList(list, node);// 得到子节点列表
        if (hasChild(list, node)) {// 判断是否有子节点
            returnList.add(node.getObjId());
            Iterator<AssociationObjects> it = childList.iterator();
            while (it.hasNext()) {
            	AssociationObjects n = (AssociationObjects) it.next();
                recursionFn(list, n);
            }
        } else {
            returnList.add(node.getObjId());
        }
    }
    
    /**
     * 获取所有的字节的属性。
     * @param list 分类表
     * @param typeId 传入的父节点ID
     * @return String
     */
    public static List<AssociationObjects> getChildNodesAll(List<AssociationObjects> list, String typeId) {
    	if (!returnListAssObject.isEmpty()) {
    		returnListAssObject.clear();;
		}
        if(list == null && typeId == null) return null;
        for (Iterator<AssociationObjects> iterator = list.iterator(); iterator.hasNext();) {
        	AssociationObjects node = (AssociationObjects) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
        	
            if ( typeId.equalsIgnoreCase(node.getAssociativeObjId())) {
            	recursionFnAll(list, node);
            }
            // 二、遍历所有的父节点下的所有子节点
            /*if (node.getParentId()==0) {
                recursionFn(list, node);
            }*/
        }
        return returnListAssObject;
    }
    
     
    //获取子节点的所有属性
    private static void recursionFnAll(List<AssociationObjects> list, AssociationObjects node) {
        List<AssociationObjects> childList = getChildList(list, node);// 得到子节点列表
        if (hasChild(list, node)) {// 判断是否有子节点
        	returnListAssObject.add(node);
            Iterator<AssociationObjects> it = childList.iterator();
            while (it.hasNext()) {
            	AssociationObjects n = (AssociationObjects) it.next();
            	recursionFnAll(list, n);
            }
        } else {
        	returnListAssObject.add(node);
        }
    }
    
    
    
    
    // 得到子节点列表
    private static List<AssociationObjects> getChildList(List<AssociationObjects> list, AssociationObjects node) {
        List<AssociationObjects> nodeList = new ArrayList<AssociationObjects>();
        Iterator<AssociationObjects> it = list.iterator();
        while (it.hasNext()) {
        	AssociationObjects n = (AssociationObjects) it.next();
            if (n.getAssociativeObjId().equalsIgnoreCase( node.getObjId())) {
                nodeList.add(n);
            }
        }
        return nodeList;
    }
 
    // 判断是否有子节点
    private static boolean hasChild(List<AssociationObjects> list, AssociationObjects node) {
        return getChildList(list, node).size() > 0 ? true : false;
    }
     
     

     
    public static List<String> getChildObjidList(String jsonString,String objId){
	        List<AssociationObjects>  temAraay =com.alibaba.fastjson.JSONObject.parseArray(jsonString, AssociationObjects.class);;
	        List<String> lastChildList= getChildNodes(temAraay, objId);
    	
	        
	        return lastChildList;
		
    }
    
    
    public static List<AssociationObjects> getChildListObjects(String jsonString,String objId){
        List<AssociationObjects>  temAraay =com.alibaba.fastjson.JSONObject.parseArray(jsonString, AssociationObjects.class);;
        AssociationObjects ass1 = new AssociationObjects();
    	ass1=getAssByObjid(temAraay, objId);
        List<AssociationObjects> childListObjects= getChildNodesAll(temAraay, objId);
        childListObjects.add(ass1);
        
        return childListObjects;
	
}
    
    public static Set<String> getObjidList(String jsonString){
   	 
	        List<AssociationObjects>  temAraay = com.alibaba.fastjson.JSONObject.parseArray(jsonString, AssociationObjects.class);
	        Set<String> allchildList= getAllObjids(temAraay);
   	
	        
	        return allchildList;
		
   }
    
    public static AssociationObjects getAssByObjid(List<AssociationObjects> sList,String objId){
    		AssociationObjects ass1= new AssociationObjects();
			Iterator<AssociationObjects> it = sList.iterator();
	        while (it.hasNext()) {
	        	AssociationObjects n = (AssociationObjects) it.next();
	        	if (n.getAssociativeObjId().equalsIgnoreCase(objId)) {
	        		ass1=n;
				}
	        }
			return ass1;
    	
    	
    	
    }
    
    public static Set<String> getAllObjids(List<AssociationObjects> sList){
		Set<String> allObjids =new HashSet<String>();
		Iterator<AssociationObjects> it = sList.iterator();
        while (it.hasNext()) {
        	AssociationObjects n = (AssociationObjects) it.next();
        	allObjids.add(n.getAssociativeObjId());
        }
		return allObjids;
	
	
	
}
    
    /**
     * 返回所有的最终子ids
     * @param jsonString
     * @param objId
     * @return
     */
	   public static  List<String> getLastChild(String jsonString,String objId) {    
		      List<String> list1 =getChildObjidList(jsonString, objId); 
		      Set<String> t2 = getObjidList(jsonString);
		      List<String> list2 = new ArrayList<String>();       
		      Collection<String> exists = new  ArrayList<String>(list1);
		      exists.removeAll(t2);
		     /* System.err.println(exists);
		      Collection<String> notexists = new  ArrayList<String>(list1);
		      notexists.removeAll(exists);
		      System.err.println(notexists);*/
		      list2=new ArrayList<>(exists);
		      return list2;    
		  }
    
	   /**
	    * 子ids 转换成 josn 字符串。
	    * @param jsonString
	    * @param oid
	    * @return
	    */
	    public  static String  getProIds(String jsonString,String oid){
	    	List objIdList=getLastChild(jsonString, oid);
	    	Set<String> objIdset = new HashSet<>(objIdList);
	    	objIdList.clear();
	    	objIdList.addAll(objIdset);
	        String ids = "[";
	        for (int i = 0 ;i < objIdset.size() ;i++){
	            String objId = "\""+objIdList.get(i)+"\"";
	           
	            	if (i<objIdset.size()-1) {
	            		 ids = ids + objId+",";
					}else {
						 ids = ids + objId;
					}
	            	
				
	           
	        }
	        return ids+"]";
	    }
	    
	    /**
	     * 获取该id以及该id下所以的设备属性信息。
	     * @param jsonString
	     * @param oid
	     * @return
	     */
	    public  static String  getAllAssociationObjects(String jsonString,String oid){
	    	List<AssociationObjects> childlistObjects=getChildListObjects(jsonString,oid);
	    	
	    	JSONArray json = JSONArray.fromObject(childlistObjects);   
	        return json.toString();
	    }
	    
	    
	    
	   
	    // 本地模拟数据测试
	    public static void main(String[] args) {
	        long start = System.currentTimeMillis();
	        List<Node> nodeList = new ArrayList<Node>();
	        NodeUtilForObjids mt = new NodeUtilForObjids();
	      //  System.out.println(mt.getChildNodes(nodeList,2l));
	        List<String> tt = new ArrayList<String>();
	        tt.add("ff74f4c1-8560-4445-88b3-6fdd77b5d023");
	        tt.add("58d78540-6526-4ff7-aac5-bfea05df3b37");
	        tt.add("7dafd7e1-6494-481c-9cdb-cb5adb75b0e0");
	      System.err.println( NodeUtilForObjids.getProIds("",""));
	        long end = System.currentTimeMillis();
	        System.out.println("用时:" + (end - start) + "ms");
	    }
	   
}