package com.boco.eoms.partner.home.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

	/**
 * <p>
 * Title:资料库信息
 * </p>
 * <p>
 * Description:资料库信息
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class MaterialLibForm extends BaseForm {
	/**
	 * 主键
	 */
	 private String id;
	/**
	 * 发布人Id
	 */
	 private String publisherId;
	 /**
	* 发布人姓名
	 */
	 private String publisherName;
	/**
	 * 发布人部门Id
	 */
	 private String publisherDeptId;
	 /**
	  * 发布人部门名称
	  */
	 private String  publisherDeptName;
	 /**
	  * 主题
	  */
	 private String  subject;
	 /**
	  * 发布时间
	  */
	 private String  publishTime;
	 /**
	  * 概述
	  */
	 private String   outline;
	 /**
	  * 关键词
	  */
	 private String   keyWord;
	 /**
	  * 代维专业
	  */
	 private String   specialty;
	 /**
	  * 附件
	  */
	 private String   file;
	 /**
	  * 发布范围（组织Id）
	  */
	 private String   scopeIds ;
	 /**
	  * 发布范围（组织Names）
	  */
	 private String   scopeNames ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getPublisherDeptId() {
		return publisherDeptId;
	}
	public void setPublisherDeptId(String publisherDeptId) {
		this.publisherDeptId = publisherDeptId;
	}
	public String getPublisherDeptName() {
		return publisherDeptName;
	}
	public void setPublisherDeptName(String publisherDeptName) {
		this.publisherDeptName = publisherDeptName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getOutline() {
		return outline;
	}
	public void setOutline(String outline) {
		this.outline = outline;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getScopeIds() {
		return scopeIds;
	}
	public void setScopeIds(String scopeIds) {
		this.scopeIds = scopeIds;
	}
	public String getScopeNames() {
		return scopeNames;
	}
	public void setScopeNames(String scopeNames) {
		this.scopeNames = scopeNames;
	}
	 
	 
}
