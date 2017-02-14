package com.boco.activiti.partner.process.po;

/**
 * 
 	* @author WANGJUN
 	* @ClassName: ParameterModel
 	* @Version 
 	* @ModifiedBy 
 	* @Copyright 
 	* @date Aug 5, 2016 11:32:02 AM
 	* @description 封装方法之间传递的参数
 */
public class ParameterModel {
	//用户ID
	private String userId;
	
	//流程号
	private String processInstanceId;
	
	//抽检结果
	private String samplingJudgments;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getSamplingJudgments() {
		return samplingJudgments;
	}

	public void setSamplingJudgments(String samplingJudgments) {
		this.samplingJudgments = samplingJudgments;
	}
}
