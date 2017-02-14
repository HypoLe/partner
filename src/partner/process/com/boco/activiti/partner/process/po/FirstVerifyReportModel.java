package com.boco.activiti.partner.process.po;

import java.io.Serializable;

/**
 * 	 一次核验报表Model类
 *   @author WangJun
 */
public class FirstVerifyReportModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//工单流程id
    private String processInstanceId;
   
    //区县
    private String county;
    
    //流程类型
    private String processType;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}
}
