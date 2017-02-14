package com.boco.eoms.partner.tempTask.model;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.base.util.StaticMethod;

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
public class PnrTempTaskWork extends BaseObject {

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
	 * 临时任务main表id
	 *
	 */
	private java.lang.String tempTaskId;
   
	public void setTempTaskId(java.lang.String tempTaskId){
		this.tempTaskId= tempTaskId;       
	}
   
	public java.lang.String getTempTaskId(){
		return this.tempTaskId;
	}

	/**
	 *
	 * 预计开始时间
	 *
	 */
	private java.sql.Timestamp startTime;
   
	public void setStartTime(java.sql.Timestamp startTime){
		this.startTime= startTime;       
	}
   
	public java.sql.Timestamp getStartTime(){
		return this.startTime;
	}
	
	private java.lang.String startTimeStr;
	
	public java.lang.String getStartTimeStr(){
		startTimeStr = StaticMethod.getTimestampString(this.startTime);
		return startTimeStr;
	}

	/**
	 *
	 * 预计结束时间
	 *
	 */
	private java.sql.Timestamp endTime;
   
	public void setEndTime(java.sql.Timestamp endTime){
		this.endTime= endTime;       
	}
   
	public java.sql.Timestamp getEndTime(){
		return this.endTime;
	}
	
	private java.lang.String endTimeStr;
	
	public java.lang.String getEndTimeStr(){
		endTimeStr = StaticMethod.getTimestampString(this.endTime);
		return endTimeStr;
	}
	/**
	 *
	 * 工作内容描述
	 *
	 */
	private java.lang.String workContent;
   
	public void setWorkContent(java.lang.String workContent){
		this.workContent= workContent;       
	}
   
	public java.lang.String getWorkContent(){
		return this.workContent;
	}
	/**
	 *
	 * 工作完成标准
	 *
	 */
	private java.lang.String workStandard;
  
	public void setWorkStandard(java.lang.String workStandard){
		this.workStandard= workStandard;       
	}
  
	public java.lang.String getWorkStandard(){
		return this.workStandard;
	}	
	/**
	 *
	 * 工作考核标准
	 *
	 */
	private java.lang.String evaStandard;
  
	public void setEvaStandard(java.lang.String evaStandard){
		this.evaStandard= evaStandard;       
	}
  
	public java.lang.String getEvaStandard(){
		return this.evaStandard;
	}
	/**
	 *
	 * 执行周期
	 *
	 */
	private java.lang.String cycle;
 
	public void setCycle(java.lang.String cycle){
		this.cycle= cycle;       
	}
 
	public java.lang.String getCycle(){
		return this.cycle;
	}
	/*
	 *
	 * 工作完成总结
	 *
	 */
	private java.lang.String summarize;
  
	public void setSummarize(java.lang.String summarize){
		this.summarize= summarize;       
	}
  
	public java.lang.String getSummarize(){
		return this.summarize;
	}	
	/*
	 *
	 * 上次执行时间
	 *
	 */
	private java.sql.Timestamp lastExecuteTime;
  
	public void setLastExecuteTime(java.sql.Timestamp lastExecuteTime){
		this.lastExecuteTime= lastExecuteTime;       
	}
  
	public java.sql.Timestamp getLastExecuteTime(){
		return this.lastExecuteTime;
	}
	/*
	 *
	 * 工作执行状态 0-未执行,1-执行中,2-执行完成
	 *
	 */
	private java.lang.String workFlag;
 
	public void setWorkFlag(java.lang.String workFlag){
		this.workFlag= workFlag;       
	}
 
	public java.lang.String getWorkFlag(){
		return this.workFlag;
	}


	/**
	 *
	 * 对应考核指标名称
	 *
	 */
	private java.lang.String workEvaName;
	
	public java.lang.String getWorkEvaName() {
		return workEvaName;
	}

	public void setWorkEvaName(java.lang.String workEvaName) {
		this.workEvaName = workEvaName;
	}	
	/**
	 *
	 * 对应考核指标所占分数
	 *
	 */
	private java.lang.String workEvaWeight;
	
	public java.lang.String getWorkEvaWeight() {
		return workEvaWeight;
	}

	public void setWorkEvaWeight(java.lang.String workEvaWeight) {
		this.workEvaWeight = workEvaWeight;
	}
	
	/**
	 *
	 * 对应考核指标详细算法
	 *
	 */
	private java.lang.String workEvaContent;

	public java.lang.String getWorkEvaContent() {
		return workEvaContent;
	}

	public void setWorkEvaContent(java.lang.String workEvaContent) {
		this.workEvaContent = workEvaContent;
	}

	/**
	 *
	 * 对应考核周期
	 *
	 */
	private java.lang.String workEvaCycle;	

	public java.lang.String getWorkEvaCycle() {
		return workEvaCycle;
	}

	public void setWorkEvaCycle(java.lang.String workEvaCycle) {
		this.workEvaCycle = workEvaCycle;
	}	

	/**
	 *
	 * 对应考核开始时间
	 *
	 */
	private java.sql.Timestamp workEvaStartTime;

	public java.sql.Timestamp getWorkEvaStartTime() {
		return workEvaStartTime;
	}

	public void setWorkEvaStartTime(java.sql.Timestamp workEvaStartTime) {
		this.workEvaStartTime = workEvaStartTime;
	}
	/**
	 *
	 * 对应考核开始时间（字符）
	 *
	 */
	private java.lang.String workEvaStartTimeStr;	
	
	public java.lang.String getWorkEvaStartTimeStr() {
		workEvaStartTimeStr = StaticMethod.getTimestampString(this.workEvaStartTime);
		int s = workEvaStartTimeStr.indexOf(" ");
		return workEvaStartTimeStr.substring(0, s);
	}	

	/**
	 *
	 * 对应考核结束时间
	 *
	 */
	private java.sql.Timestamp workEvaEndTime;	

	public java.sql.Timestamp getWorkEvaEndTime() {
		return workEvaEndTime;
	}

	public void setWorkEvaEndTime(java.sql.Timestamp workEvaEndTime) {
		this.workEvaEndTime = workEvaEndTime;
	}
	
	/**
	 *
	 * 对应考核结束时间（字符）
	 *
	 */
	private java.lang.String workEvaEndTimeStr;	
	
	public java.lang.String getWorkEvaEndTimeStr() {
		workEvaEndTimeStr = StaticMethod.getTimestampString(this.workEvaEndTime);
		int s = workEvaEndTimeStr.indexOf(" ");
		return workEvaEndTimeStr.substring(0, s);
	}	
	
	/**
	 *
	 * 工作任务类型
	 *
	 */	
	private java.lang.String workType;	
	
	public java.lang.String getWorkType() {
		return workType;
	}

	public void setWorkType(java.lang.String workType) {
		this.workType = workType;
	}

	/**
	 *
	 * 工作任务对应url
	 *
	 */	
	private java.lang.String taskUrl;	

	public java.lang.String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(java.lang.String taskUrl) {
		this.taskUrl = taskUrl;
	}		
	
	/**
	 *
	 * 工作提醒开始时间
	 *
	 */	
	private java.lang.String warnTime;	

	public java.lang.String getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(java.lang.String warnTime) {
		this.warnTime = warnTime;
	}
	
	/**
	 *
	 * 工作任务执行人
	 *
	 */	
	private java.lang.String toOrgUser;

	public java.lang.String getToOrgUser() {
		return toOrgUser;
	}

	public void setToOrgUser(java.lang.String toOrgUser) {
		this.toOrgUser = toOrgUser;
	}
	
	/**
	 *
	 * 工作任务执行人名称
	 *
	 */	
	private java.lang.String toOrgUserName;

	public java.lang.String getToOrgUserName() {
		return toOrgUserName;
	}

	public void setToOrgUserName(java.lang.String toOrgUserName) {
		this.toOrgUserName = toOrgUserName;
	}	
	/**
	 *
	 * 算法分类
	 *
	 */	
	private java.lang.String algorithmType;
	
	public java.lang.String getAlgorithmType() {
		return algorithmType;
	}

	public void setAlgorithmType(java.lang.String algorithmType) {
		this.algorithmType = algorithmType;
	}

	/**
	 *
	 * 对应考核指标详细算法(按算法分类)
	 *
	 */	
	private java.lang.String workEvaContentByType;	

	public java.lang.String getWorkEvaContentByType() {
//		0是直接得分
		if("1".equals(this.algorithmType)) {
			String[] scopeValue = this.workEvaContent.split("-");
			String[] scope = scopeValue[0].split(",");
			StringBuffer retWorkEvaContent = new StringBuffer();
			retWorkEvaContent.append("100%~");
			retWorkEvaContent.append(scope[1]);
			retWorkEvaContent.append("% 得满分 ,");
			retWorkEvaContent.append(scope[1]);
			retWorkEvaContent.append("%~0% 不得分");
			workEvaContentByType = retWorkEvaContent.toString();
			return workEvaContentByType;			
		} else if("2".equals(this.algorithmType)) {
			String[] scopeValue = this.workEvaContent.split("-");
			String[] scope = scopeValue[0].split(",");
			String[] value = scopeValue[1].split(",");
			StringBuffer retWorkEvaContent = new StringBuffer();
			for(int k = 0 ; scope.length>k+1 ; k++){
				retWorkEvaContent.append(scope[k]);
				retWorkEvaContent.append("%~");
				retWorkEvaContent.append(scope[k+1]);
				retWorkEvaContent.append("  得总分的");
				retWorkEvaContent.append(value[k]);
				retWorkEvaContent.append("%  ;");
			}
			workEvaContentByType = retWorkEvaContent.toString();
			return workEvaContentByType;
		}
		return this.workEvaContent;
	}	
	
	public boolean equals(Object o) {
		if( o instanceof PnrTempTaskWork ) {
			PnrTempTaskWork pnrTempTaskWork=(PnrTempTaskWork)o;
			if (this.id != null || this.id.equals(pnrTempTaskWork.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}




}