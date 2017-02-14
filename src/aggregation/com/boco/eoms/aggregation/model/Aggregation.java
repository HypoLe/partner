package com.boco.eoms.aggregation.model;


import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.model.BaseObject;

public class Aggregation extends BaseObject {

	private static final long serialVersionUID = 1L;

	private String id; // 主键

	private String creatUser; // 填报人

	private String creatTime; // 填报时间

	private String moduleName; // 模块名

	private String moduleUrl; // 模块连接

	private String content; // 介绍内容

	private String deleted;// 删除标示

	private FormFile accessoryName; //批量录入图片

	private java.lang.String accessory;//附件存在文件夹下的名字
	
	private java.lang.String photo;//附件存在文件夹下的路径和名字

	private String remark; // 备注

	private String remark2;

	private String remark3;

	private String remark4;

	
	public java.lang.String getPhoto() {
		return photo;
	}


	public void setPhoto(java.lang.String photo) {
		this.photo = photo;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCreatUser() {
		return creatUser;
	}


	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}


	


	public String getCreatTime() {
		return creatTime;
	}


	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}


	public String getModuleName() {
		return moduleName;
	}


	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}


	public String getModuleUrl() {
		return moduleUrl;
	}


	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getDeleted() {
		return deleted;
	}


	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}


	public FormFile getAccessoryName() {
		return accessoryName;
	}


	public void setAccessoryName(FormFile accessoryName) {
		this.accessoryName = accessoryName;
	}


	public java.lang.String getAccessory() {
		return accessory;
	}


	public void setAccessory(java.lang.String accessory) {
		this.accessory = accessory;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getRemark2() {
		return remark2;
	}


	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}


	public String getRemark3() {
		return remark3;
	}


	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}


	public String getRemark4() {
		return remark4;
	}


	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}


	@Override
	public boolean equals(Object o) {
		if (o instanceof Aggregation) {
			Aggregation a = (Aggregation) o;
			if (this.id != null || this.id.equals(a.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
