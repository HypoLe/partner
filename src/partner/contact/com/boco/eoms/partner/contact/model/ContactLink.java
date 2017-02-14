package com.boco.eoms.partner.contact.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

	/**
 * <p>
 * Title:联系函 流转记录/对联系函的操作的历史一一记录
 * </p>
 * <p>
 * Description:联系函 流转记录/对联系函的操作的历史一一记录
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
public class ContactLink extends BaseObject {
	/**
	 * 主键
	 */
	 private String id;
	 /**
	  * 公告Id
	  */
	 private String mainId;
	/**
	 * 操作人Id
	 */
	 private String operateUserId;
	 /**
	  * 操作人部门
	  */
     private String operateDeptId;
     /**
	  * 操作部门名称
	  */
	 private String   operateDeptName;
	 /**
	  * 操作时间
	  */
     private Date operateTime;
     /**
	  * 转移状态
	  */
     private String displayLinkname;
     /**
	  * 操作类型
	  * 46：终止，8：阅知，3：转发
	  */
     private String operateType;
     /**
	  * 操作结果
	  */
     private String effect;
     /**
	  * 操作人记录
	  */
     private String remark;
     /**
	  * 发布范围ID
	  */
     private String publishedRange;
     /**
	  * 发布范围名称
	  */
     private String publishedRangeNmae;
     
     
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

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}

	public String getOperateDeptId() {
		return operateDeptId;
	}

	public void setOperateDeptId(String operateDeptId) {
		this.operateDeptId = operateDeptId;
	}

	public String getOperateDeptName() {
		return operateDeptName;
	}

	public void setOperateDeptName(String operateDeptName) {
		this.operateDeptName = operateDeptName;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getDisplayLinkname() {
		return displayLinkname;
	}

	public void setDisplayLinkname(String displayLinkname) {
		this.displayLinkname = displayLinkname;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPublishedRange() {
		return publishedRange;
	}

	public void setPublishedRange(String publishedRange) {
		this.publishedRange = publishedRange;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getPublishedRangeNmae() {
		return publishedRangeNmae;
	}

	public void setPublishedRangeNmae(String publishedRangeNmae) {
		this.publishedRangeNmae = publishedRangeNmae;
	}


}
