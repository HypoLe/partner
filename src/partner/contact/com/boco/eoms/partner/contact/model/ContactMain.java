package com.boco.eoms.partner.contact.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

	/**
 * <p>
 * Title:联系函 基本信息
 * </p>
 * <p>
 * Description:联系函 基本信息
 * </p>
 * <p>
 * Oct 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class ContactMain extends BaseObject {
	/**
	 * 主键
	 */
	 private String id;
	 /**
	  * 文号
	  */
	 private String code;
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
	 private Date  publishTime;
	 /**
	  * 处理期限
	  */
	 private Date   deathTime;
	 /**
	  * 发布内容
	  */
	 private String   content;
	 /**
	  * 发布范围
	  */
	 private String   publishedRange;
	 /**
	  * 发布范围Name
	  */
	 private String   publishedRangeName;
	 /**
	  * 审批人
	  */
	 private String   approver;
	 /**
	  * 审批人名字
	  */
	 private String   approverName;
	 /**
	  * 附件
	  */
	 private String   file;
	 /**
	  * 联系函 状态类型
	  */
	 private Integer type;
	 /**
	  * 是否短信通知
	  */
	 private Integer isSendSMS;
	 /**
	  * 是否紧急 紧急需置顶
	  */
	 private Integer isUrgent;
	 /**
	  * 超时标识
	  */
	 private Integer overTimeFlag;//在完成时，通过设置overTimeFlag来设置，完成的联系函是否是超时完成。 
	 //在main.type=-2即联系函完成时，起作用，持久化到数据库。 main.type!=-2时，即联系函没有完成时，不起作用，通过比较当前时间与deathTime，来临时设置但是不持久化
	 
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
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
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Date getDeathTime() {
		return deathTime;
	}
	public void setDeathTime(Date deathTime) {
		this.deathTime = deathTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIsSendSMS() {
		return isSendSMS;
	}
	public void setIsSendSMS(Integer isSendSMS) {
		this.isSendSMS = isSendSMS;
	}
	public Integer getIsUrgent() {
		return isUrgent;
	}
	public void setIsUrgent(Integer isUrgent) {
		this.isUrgent = isUrgent;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
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
	public String getapproverName() {
		return approverName;
	}
	public void setapproverName(String approverName) {
		this.approverName = approverName;
	}
	public Integer getOverTimeFlag() {
		return overTimeFlag;
	}
	public void setOverTimeFlag(Integer overTimeFlag) {
		this.overTimeFlag = overTimeFlag;
	}
	 
	 
}
