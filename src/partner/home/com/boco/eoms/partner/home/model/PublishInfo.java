package com.boco.eoms.partner.home.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

	/**
 * <p>
 * Title:公告基本信息
 * </p>
 * <p>
 * Description:公告基本信息
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
public class PublishInfo extends BaseObject {
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
	 private Date    publishTime;
	 /**
	  * 内容
	  */
	 private String    publishContent;
	 /**
	  * 有效日期
	  */
	 private Date    valid;
	 /**
	  * 是否需要审批  是1 否0
	  */
	 private Integer  	   isAudit;
	 /**第一个审批人*/
	 private String auditorid;
	 private String auditorname;
	 
	 /**
	  * 公告发布范围（组织，个人）
	  */
	 private String  publishedRange;
	 private String  publishedRangeName;
	 /**
	  * 附近
	  */
	 private String  	   file;
	 /**
	  * 状态 ：作废 -1，草稿 0 ，审批中 1，发布中2，被驳回3，阅知4
	  */
    private Integer  	   type;
    
    public PublishInfo(String id,String publisherId,String publisherName,String publisherDeptId,
    		String  publisherDeptName,String  subject,
    		Date    publishTime,String    publishContent,Date    valid,
    		Integer     isAudit,String auditorid,String auditorname,
    		String  publishedRange,String  publishedRangeName,String  	   file,Integer  	   type){
    	this.id=id;
    	this.publisherId=publisherId;
    	this.publisherName=publisherName;
    	this.publisherDeptId=publisherDeptId;
    	this.publisherDeptName=publisherDeptName;
    	this.subject=subject;
    	this.publishTime=publishTime;
    	this.publishContent=publishContent;
    	this.valid=valid;
    	this.isAudit=isAudit;
    	this.auditorid=auditorid;
    	this.auditorname=auditorname;
    	this.publishedRange=publishedRange;
    	this.publishedRangeName=publishedRangeName;
    	this.file=file;
    	this.type=type;
    }
    public PublishInfo(String publisherId,String publisherName,String publisherDeptId,String  publisherDeptName,String  subject,
    		Date    publishTime,String    publishContent,Date    valid,
    		Integer  	isAudit,String auditorid,String auditorname,
    		String  publishedRange,String  publishedRangeName,String  	   file,Integer  	   type){
    	this.publisherId=publisherId;
    	this.publisherName=publisherName;
    	this.publisherDeptId=publisherDeptId;
    	this.publisherDeptName=publisherDeptName;
    	this.subject=subject;
    	this.publishTime=publishTime;
    	this.publishContent=publishContent;
    	this.valid=valid;
    	this.isAudit=isAudit;
    	this.auditorid=auditorid;
    	this.auditorname=auditorname;
    	this.publishedRange=publishedRange;
    	this.publishedRangeName=publishedRangeName;
    	this.file=file;
    	this.type=type;
    }
    
    public PublishInfo(){}
    
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
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getPublishContent() {
		return publishContent;
	}
	public void setPublishContent(String publishContent) {
		this.publishContent = publishContent;
	}
	public Date getValid() {
		return valid;
	}
	public void setValid(Date valid) {
		this.valid = valid;
	}
	public Integer getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	 /**
	  * 状态 ：作废 -1，草稿 0 ，审批中 1，发布中2，被驳回3，阅知4
	  */
	public Integer getType() {
		return type;
	}
	 /**
	  * 状态 ：作废 -1，草稿 0 ，审批中 1，发布中2，被驳回3，阅知4
	  */
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	public String getPublishedRange() {
		return publishedRange;
	}
	public void setPublishedRange(String publishedRange) {
		this.publishedRange = publishedRange;
	}
	public String getPublishedRangeName() {
		return publishedRangeName;
	}
	public void setPublishedRangeName(String publishedRangeName) {
		this.publishedRangeName = publishedRangeName;
	}
	public String getAuditorid() {
		return auditorid;
	}
	public void setAuditorid(String auditorid) {
		this.auditorid = auditorid;
	}
	public String getAuditorname() {
		return auditorname;
	}
	public void setAuditorname(String auditorname) {
		this.auditorname = auditorname;
	}
	 
	 
}
