package com.boco.eoms.deviceManagement.fiber.model;

import com.boco.eoms.base.webapp.form.BaseForm;

public class FiberInformationForm extends BaseForm implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String fiberType;//光缆类型
	private String fiberLevel;//光缆级别
	private String fiberStructure;//光缆结构
	private String fiberNumber;//数量
	private String fiberCoreNumber;//纤芯数
	private String fiberCoreType;//纤芯类型
	private String installTime;//安装日期
	private String paragraphNumber;//段落纤芯号
	private String paragraph;//段落
	private String createUser;//信息创建人
	private String createTime;//信息创建时间
	private String isDel;//0为显示 1为删除
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFiberType() {
		return fiberType;
	}
	public void setFiberType(String fiberType) {
		this.fiberType = fiberType;
	}
	public String getFiberLevel() {
		return fiberLevel;
	}
	public void setFiberLevel(String fiberLevel) {
		this.fiberLevel = fiberLevel;
	}
	public String getFiberStructure() {
		return fiberStructure;
	}
	public void setFiberStructure(String fiberStructure) {
		this.fiberStructure = fiberStructure;
	}
	public String getFiberNumber() {
		return fiberNumber;
	}
	public void setFiberNumber(String fiberNumber) {
		this.fiberNumber = fiberNumber;
	}
	public String getFiberCoreNumber() {
		return fiberCoreNumber;
	}
	public void setFiberCoreNumber(String fiberCoreNumber) {
		this.fiberCoreNumber = fiberCoreNumber;
	}
	public String getFiberCoreType() {
		return fiberCoreType;
	}
	public void setFiberCoreType(String fiberCoreType) {
		this.fiberCoreType = fiberCoreType;
	}
	public String getInstallTime() {
		return installTime;
	}
	public void setInstallTime(String installTime) {
		this.installTime = installTime;
	}
	public String getParagraphNumber() {
		return paragraphNumber;
	}
	public void setParagraphNumber(String paragraphNumber) {
		this.paragraphNumber = paragraphNumber;
	}
	public String getParagraph() {
		return paragraph;
	}
	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof FiberInformationForm) {
			FiberInformationForm fiberInformationForm = (FiberInformationForm) o;
			if (this.id != null || this.id.equals(fiberInformationForm.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
