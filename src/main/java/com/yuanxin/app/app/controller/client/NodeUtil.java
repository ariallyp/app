package com.yuanxin.app.app.controller.client;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
 
/**
 * 获取子节点
 */
public class NodeUtil {
     
    private List<Long> returnList = new ArrayList<Long>();
     
    /**
     * 根据父节点的ID获取所有子节点
     * @param list 分类表
     * @param typeId 传入的父节点ID
     * @return String
     */
    public String getChildNodes(List<Node> list, Long typeId) {
        if(list == null && typeId == null) return "";
        for (Iterator<Node> iterator = list.iterator(); iterator.hasNext();) {
            Node node = (Node) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if ( typeId==node.getParentId()) {
                recursionFn(list, node);
            }
            // 二、遍历所有的父节点下的所有子节点
            /*if (node.getParentId()==0) {
                recursionFn(list, node);
            }*/
        }
        return returnList.toString();
    }
     
    private void recursionFn(List<Node> list, Node node) {
        List<Node> childList = getChildList(list, node);// 得到子节点列表
        if (hasChild(list, node)) {// 判断是否有子节点
            returnList.add(node.getId());
            Iterator<Node> it = childList.iterator();
            while (it.hasNext()) {
                Node n = (Node) it.next();
                recursionFn(list, n);
            }
        } else {
            returnList.add(node.getId());
        }
    }
     
    // 得到子节点列表
    private List<Node> getChildList(List<Node> list, Node node) {
        List<Node> nodeList = new ArrayList<Node>();
        Iterator<Node> it = list.iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            if (n.getParentId() == node.getId()) {
                nodeList.add(n);
            }
        }
        return nodeList;
    }
 
    // 判断是否有子节点
    private boolean hasChild(List<Node> list, Node node) {
        return getChildList(list, node).size() > 0 ? true : false;
    }
     
     
    // 本地模拟数据测试
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<Node> nodeList = new ArrayList<Node>();
        Node node1 = new Node(1l,  0l);
        Node node2 = new Node(2l,  1l);
        Node node3 = new Node(3l,  1l);
        Node node4 = new Node(4l,  2l);
        Node node5 = new Node(5l,  2l);
        Node node6 = new Node(6l,  4l);
       
         
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        nodeList.add(node4);
        nodeList.add(node5);
        nodeList.add(node6);
     
         
        NodeUtil mt = new NodeUtil();
        System.out.println(mt.getChildNodes(nodeList,2l));
        long end = System.currentTimeMillis();
        System.out.println("用时:" + (end - start) + "ms");
    }
     
}