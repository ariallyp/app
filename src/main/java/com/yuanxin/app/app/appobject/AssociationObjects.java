package com.yuanxin.app.app.appobject;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssociationObjects implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 753582447552227121L;
	@JsonProperty(value = "AssociationRole")  
	public String associationRole;
	@JsonProperty(value = "AssociativeObjId")  
	public String associativeObjId;
	@JsonProperty(value = "ClassId")
	public String classId;
	@JsonProperty(value = "Name")
	public String name;
	@JsonProperty(value = "ObjId") 
	public String objId;
	
	
	public String getAssociationRole() {
		return associationRole;
	}
	public void setAssociationRole(String associationRole) {
		this.associationRole = associationRole;
	}
	public String getAssociativeObjId() {
		return associativeObjId;
	}
	public void setAssociativeObjId(String associativeObjId) {
		this.associativeObjId = associativeObjId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	@Override
	public String toString() {
		return " [associationRole=" + associationRole + ", associativeObjId=" + associativeObjId
				+ ", classId=" + classId + ", name=" + name + ", objId=" + objId + "]";
	}
	
	

	
	
	

}
