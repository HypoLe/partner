package com.boco.eoms.partner.tempTask.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:合作伙伴临时任务管理
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理主表信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrTempTaskMain extends BaseObject {

	/**
	 * 主键
	 */
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 *
	 * 新增时间
	 *
	 */
	private java.sql.Timestamp createTime;
   
	public void setCreateTime(java.sql.Timestamp createTime){
		this.createTime= createTime;       
	}
   
	public java.sql.Timestamp getCreateTime(){
		return this.createTime;
	}

	/**
	 *
	 * 新增人
	 *
	 */
	private java.lang.String createUser;
   
	public void setCreateUser(java.lang.String createUser){
		this.createUser= createUser;       
	}
   
	public java.lang.String getCreateUser(){
		return this.createUser;
	}

	/**
	 *
	 * 新增部门
	 *
	 */
	private java.lang.String createDept;
   
	public void setCreateDept(java.lang.String createDept){
		this.createDept= createDept;       
	}
   
	public java.lang.String getCreateDept(){
		return this.createDept;
	}

	/**
	 *
	 * 临时任务编号
	 *
	 */
	private java.lang.String tempTaskNO;
   
	public void setTempTaskNO(java.lang.String tempTaskNO){
		this.tempTaskNO= tempTaskNO;       
	}
   
	public java.lang.String getTempTaskNO(){
		return this.tempTaskNO;
	}

	/**
	 *
	 * 临时任务名称
	 *
	 */
	private java.lang.String tempTaskName;
   
	public void setTempTaskName(java.lang.String tempTaskName){
		this.tempTaskName= tempTaskName;       
	}
   
	public java.lang.String getTempTaskName(){
		return this.tempTaskName;
	}

	/**
	 *
	 * 临时任务开始时间
	 *
	 */
	private java.sql.Timestamp startTime;
   
	public void setStartTime(java.sql.Timestamp startTime){
		this.startTime= startTime;       
	}
   
	public java.sql.Timestamp getStartTime(){
		return this.startTime;
	}

	/**
	 *
	 * 临时任务结束时间
	 *
	 */
	private java.sql.Timestamp endTime;
   
	public void setEndTime(java.sql.Timestamp endTime){
		this.endTime= endTime;       
	}
   
	public java.sql.Timestamp getEndTime(){
		return this.endTime;
	}

//	/**
//	 *
//	 * 工作实际开始时间
//	 *
//	 */
//    private java.sql.Timestamp realStartTime;
//
//	public java.sql.Timestamp getRealStartTime() {
//		return realStartTime;
//	}
//
//	public void setRealStartTime(java.sql.Timestamp realStartTime) {
//		this.realStartTime = realStartTime;
//	}
//	/**
//	 *
//	 * 工作实际完成时间
//	 *
//	 */
//    private java.sql.Timestamp realEndTime;
//
//	public java.sql.Timestamp getRealEndTime() {
//		return realEndTime;
//	}
//
//	public void setRealEndTime(java.sql.Timestamp realEndTime) {
//		this.realEndTime = realEndTime;
//	}
//	/**
//	 *
//	 * 工作实际完成情况
//	 *
//	 */
//    private java.lang.String completeDescription;
//
//	public java.lang.String getCompleteDescription() {
//		return completeDescription;
//	}
//
//	public void setCompleteDescription(java.lang.String completeDescription) {
//		this.completeDescription = completeDescription;
//	}
	/**
	 *
	 * 附件
	 *
	 */
	private java.lang.String accessoriesId;

	public java.lang.String getAccessoriesId() {
		return accessoriesId;
	}

	public void setAccessoriesId(java.lang.String accessoriesId) {
		this.accessoriesId = accessoriesId;
	}
	/**
	 *
	 * 临时任务状态
	 *
	 */
	private java.lang.String state;

	public java.lang.String getState() {
		return state;
	}

	public void setState(java.lang.String state) {
		this.state = state;
	}
	/**
	 *
	 * 临时任务审核状态
	 *
	 */
	private java.lang.String auditFlag;

	public java.lang.String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(java.lang.String auditFlag) {
		this.auditFlag = auditFlag;
	}
	/**
	 *
	 * 临时任务执行者
	 *
	 */
	private java.lang.String toOrgId;

	public java.lang.String getToOrgId() {
		return toOrgId;
	}

	public void setToOrgId(java.lang.String toOrgId) {
		this.toOrgId = toOrgId;
	}
	/**
	 *
	 * 临时任务执行者类型
	 *
	 */
	private java.lang.String toOrgType;

	public java.lang.String getToOrgType() {
		return toOrgType;
	}

	public void setToOrgType(java.lang.String toOrgType) {
		this.toOrgType = toOrgType;
	}

	/**
	 *
	 * 临时任务考核人
	 *
	 */
	private java.lang.String toEvaOrgId;

	public java.lang.String getToEvaOrgId() {
		return toEvaOrgId;
	}

	public void setToEvaOrgId(java.lang.String toEvaOrgId) {
		this.toEvaOrgId = toEvaOrgId;
	}
	/**
	 *
	 * 临时任务考核类型
	 *
	 */
	private java.lang.String toEvaOrgType;

	public java.lang.String getToEvaOrgType() {
		return toEvaOrgType;
	}

	public void setToEvaOrgType(java.lang.String toEvaOrgType) {
		this.toEvaOrgType = toEvaOrgType;
	}	
	
	/**
	 *
	 * 临时任务考核模板id
	 *
	 */
	private java.lang.String evaTemplateId;

	public java.lang.String getEvaTemplateId() {
		return evaTemplateId;
	}

	public void setEvaTemplateId(java.lang.String evaTemplateId) {
		this.evaTemplateId = evaTemplateId;
	}	
	
	public boolean equals(Object o) {
		if( o instanceof PnrTempTaskMain ) {
			PnrTempTaskMain pnrTempTaskMain=(PnrTempTaskMain)o;
			if (this.id != null || this.id.equals(pnrTempTaskMain.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


}