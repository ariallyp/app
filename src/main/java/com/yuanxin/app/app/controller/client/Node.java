package com.yuanxin.app.app.controller.client;

/**
 * 无限级节点模型
 */
public class Node {
    /**
     * 节点id
     */
    private Long id;
 
    /**
     * 节点名称
     */
 
    /**
     * 父节点id
     */
    private Long parentId;
 
    public Node() {
    }
 
    Node(Long id, Long parentId) {
        this.id = id;
        this.parentId = parentId;
    }
 
   
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public Long getParentId() {
        return parentId;
    }
 
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
 

 
}
