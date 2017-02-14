package com.boco.eoms.partner.tempTask.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

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
 * @moudle.getAuthor() daizhigang
 * @moudle.getVersion() 1.0
 * 
 */
public class PnrTempTaskMainForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

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
	private java.lang.String createTime;
   
	public void setCreateTime(java.lang.String createTime){
		this.createTime= createTime;       
	}
   
	public java.lang.String getCreateTime(){
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
	private java.lang.String startTime;
   
	public void setStartTime(java.lang.String startTime){
		this.startTime= startTime;       
	}
   
	public java.lang.String getStartTime(){
		return this.startTime;
	}
	
	/**
	 *
	 * 临时任务开始时间(String)
	 *
	 */
	private java.lang.String startTimeStr;

	public java.lang.String getStartTimeStr() {
		if(this.startTime==null){
			return "";
		}
		String str = this.startTime;
		startTimeStr = str.substring(0, str.indexOf(" "));
		return startTimeStr;
	}	

	/**
	 *
	 * 临时任务结束时间
	 *
	 */
	private java.lang.String endTime;
   
	public void setEndTime(java.lang.String endTime){
		this.endTime= endTime;       
	}
   
	public java.lang.String getEndTime(){
		return this.endTime;
	}
	/**
	 *
	 * 临时任务结束时间(String)
	 *
	 */
	private java.lang.String endTimeStr;
	
	public java.lang.String getEndTimeStr() {
		if(this.endTime==null){
			return "";
		}
		String str = this.endTime;
		endTimeStr = str.substring(0, str.indexOf(" "));
		return endTimeStr;
	}	
//	/**
//	 *
//	 * 工作实际开始时间
//	 *
//	 */
//    private java.lang.String realStartTime;
//
//	public java.lang.String getRealStartTime() {
//		return realStartTime;
//	}
//
//	public void setRealStartTime(java.lang.String realStartTime) {
//		this.realStartTime = realStartTime;
//	}
//	/**
//	 *
//	 * 工作实际完成时间
//	 *
//	 */
//    private java.lang.String realEndTime;
//
//	public java.lang.String getRealEndTime() {
//		return realEndTime;
//	}
//
//	public void setRealEndTime(java.lang.String realEndTime) {
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
	 * 工作内容主键id
	 *
	 */
	private java.lang.String[] workId;
	/**
	 *
	 * 工作内容计划开始时间
	 *
	 */
	private java.lang.String[] workStartTime;
	/**
	 *
	 * 工作内容计划结束时间
	 *
	 */
	private java.lang.String[] workEndTime;
	/**
	 *
	 * 工作内容描述
	 *
	 */
	private java.lang.String[] workContent;

	/**
	 *
	 * 工作完成标准
	 *
	 */
	private java.lang.String[] workStandard;
 
	public void setWorkStandard(java.lang.String[] workStandard){
		this.workStandard= workStandard;       
	}
 
	public java.lang.String[] getWorkStandard(){
		return this.workStandard;
	}	
//	/**
//	 *
//	 * 工作考核标准
//	 *
//	 */
//	private java.lang.String[] evaStandard;
// 
//	public void setEvaStandard(java.lang.String[] evaStandard){
//		this.evaStandard= evaStandard;       
//	}
// 
//	public java.lang.String[] getEvaStandard(){
//		return this.evaStandard;
//	}
	

	public java.lang.String[] getWorkStartTime() {
		return workStartTime;
	}

	public void setWorkStartTime(java.lang.String[] workStartTime) {
		this.workStartTime = workStartTime;
	}

	public java.lang.String[] getWorkEndTime() {
		return workEndTime;
	}

	public void setWorkEndTime(java.lang.String[] workEndTime) {
		this.workEndTime = workEndTime;
	}

	public java.lang.String[] getWorkContent() {
		return workContent;
	}

	public void setWorkContent(java.lang.String[] workContent) {
		this.workContent = workContent;
	}


	public java.lang.String[] getWorkId() {
		return workId;
	}

	public void setWorkId(java.lang.String[] workId) {
		this.workId = workId;
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
	 * 考核内容(对应工作下)
	 *
	 */
	private java.lang.String[] workEvaContent;
	/**
	 *
	 * 该考核内容占权重(对应工作下)
	 *
	 */
	private java.lang.String[] workEvaWeight;
	
	/**
	 *
	 * 该考核名字(对应工作下)
	 *
	 */
	private java.lang.String[] workEvaName;
	
	/**
	 *
	 * 考核周期(对应工作下)
	 *
	 */
	private java.lang.String[] workEvaCycle;
	/**
	 *
	 * 考核开始时间(对应工作下)
	 *
	 */
	private java.lang.String[] workEvaStartTime;
	
	/**
	 *
	 * 考核结束时间(对应工作下)
	 *
	 */
	private java.lang.String[] workEvaEndTime;	

	public java.lang.String[] getWorkEvaContent() {
		return workEvaContent;
	}

	public void setWorkEvaContent(java.lang.String[] workEvaContent) {
		this.workEvaContent = workEvaContent;
	}

	public java.lang.String[] getWorkEvaWeight() {
		return workEvaWeight;
	}

	public void setWorkEvaWeight(java.lang.String[] workEvaWeight) {
		this.workEvaWeight = workEvaWeight;
	}

	public java.lang.String[] getWorkEvaName() {
		return workEvaName;
	}

	public void setWorkEvaName(java.lang.String[] workEvaName) {
		this.workEvaName = workEvaName;
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

	public java.lang.String[] getWorkEvaCycle() {
		return workEvaCycle;
	}

	public void setWorkEvaCycle(java.lang.String[] workEvaCycle) {
		this.workEvaCycle = workEvaCycle;
	}

	public java.lang.String[] getWorkEvaStartTime() {
		return workEvaStartTime;
	}

	public void setWorkEvaStartTime(java.lang.String[] workEvaStartTime) {
		this.workEvaStartTime = workEvaStartTime;
	}

	public java.lang.String[] getWorkEvaEndTime() {
		return workEvaEndTime;
	}

	public void setWorkEvaEndTime(java.lang.String[] workEvaEndTime) {
		this.workEvaEndTime = workEvaEndTime;
	}	
	
	/**
	 *
	 * 工作任务类型
	 *
	 */	
	private java.lang.String[] workType;	
	

	/**
	 *
	 * 工作任务对应url
	 *
	 */	
	private java.lang.String[] taskUrl;

	public java.lang.String[] getWorkType() {
		return workType;
	}

	public void setWorkType(java.lang.String[] workType) {
		this.workType = workType;
	}

	public java.lang.String[] getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(java.lang.String[] taskUrl) {
		this.taskUrl = taskUrl;
	}	
	
	/**
	 *
	 * 执行周期
	 *
	 */	
	private java.lang.String[] workCycle;

	public java.lang.String[] getWorkCycle() {
		return workCycle;
	}

	public void setWorkCycle(java.lang.String[] workCycle) {
		this.workCycle = workCycle;
	}

	
	/**
	 *
	 * 工作任务执行人
	 *
	 */	
	private java.lang.String[] toOrgUser;

	public java.lang.String[] getToOrgUser() {
		return toOrgUser;
	}

	public void setToOrgUser(java.lang.String[] toOrgUser) {
		this.toOrgUser = toOrgUser;
	}

	/**
	 *
	 * 工作任务执行人名称
	 *
	 */	
	private java.lang.String[] toOrgUserName;

	public java.lang.String[] getToOrgUserName() {
		return toOrgUserName;
	}

	public void setToOrgUserName(java.lang.String[] toOrgUserName) {
		this.toOrgUserName = toOrgUserName;
	}	
	/**
	 *
	 * 算法分类
	 *
	 */	
	private java.lang.String[] algorithmType;


	public java.lang.String[] getAlgorithmType() {
		return algorithmType;
	}

	public void setAlgorithmType(java.lang.String[] algorithmType) {
		this.algorithmType = algorithmType;
	}

}