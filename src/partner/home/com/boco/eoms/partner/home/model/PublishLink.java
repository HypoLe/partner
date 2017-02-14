package com.boco.eoms.partner.home.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

	/**
 * <p>
 * Title:Link信息
 * </p>
 * <p>
 * Description:Link信息
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
public class PublishLink extends BaseObject {
	/**
	 * 主键
	 */
	 private String id;
	 /**
	  * 公告Id
	  */
	 private String mainId;
	 /**
	  * 任务Id
	  */
	 private String taskId;
	 
	 //处理结果 ：作废 -1，草稿0，送审1，通过提交下一审批2，通过3，驳回4，阅知5
//	 /**
//	  *   处理结果 ：作废 -1，草稿0，送审1，通过提交下一审批2，通过3，驳回4
//	  */
	 //modify:操作类型。  有的查询条件，要操作类型和操作结果结合
	 private Integer   operateType;
	 
	 private String operateName;
	 
//	 /**
//	  * 审批结果 ：不通过 0、通过 1、通过提交下一审批 2  modify:操作结果,
//	  */
	 //操作结果
	 private Integer operateResult;
	 /**
	  * 审批意见  modify:操作内容
	  */
	 private String operateContent;
	 /**
	  * 操作时间
	  */
	 private Date operateTime;
	 
	 private String operateUserid;
	 private String operateDeptid;
	 private String operateDeptname;
	 
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
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	/**
	 * 处理结果 ：作废 -1，草稿0，送审1，通过提交下一审批2，通过3，驳回4
	 * @return
	 */
	public Integer getOperateType() {
		return operateType;
	}
	/**
	 * 处理结果 ：作废 -1，草稿0，送审1，通过提交下一审批2，通过3，驳回4 
	 * @param operateType
	 */
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	 	 
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public Integer getOperateResult() {
		return operateResult;
	}
	public void setOperateResult(Integer operateResult) {
		this.operateResult = operateResult;
	}
	public String getOperateContent() {
		return operateContent;
	}
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	public String getOperateUserid() {
		return operateUserid;
	}
	public void setOperateUserid(String operateUserid) {
		this.operateUserid = operateUserid;
	}
	public String getOperateDeptid() {
		return operateDeptid;
	}
	public void setOperateDeptid(String operateDeptid) {
		this.operateDeptid = operateDeptid;
	}
	public String getOperateDeptname() {
		return operateDeptname;
	}
	public void setOperateDeptname(String operateDeptname) {
		this.operateDeptname = operateDeptname;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	 
	 
}
