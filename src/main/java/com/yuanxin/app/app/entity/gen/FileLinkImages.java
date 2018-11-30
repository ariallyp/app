package com.yuanxin.app.app.entity.gen;

import java.io.Serializable;
import java.util.Date;

public class FileLinkImages implements Serializable {
   
    private String id;

 
    private String fileId;

   
    private String fileName;

   
    private String fileUrl;
    
    private String smallFileUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getSmallFileUrl() {
		return smallFileUrl;
	}

	public void setSmallFileUrl(String smallFileUrl) {
		this.smallFileUrl = smallFileUrl;
	}

 

  
}