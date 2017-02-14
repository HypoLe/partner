package com.boco.activiti.partner.process.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 	 一次核验报表Model类
 *   @author WangJun
 */
public class CycleCollarReportModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//工单流程id
    private String processInstanceId;
   
    //区县
    private String county;
    
    //开始时间
    private Date startDate;
    
    //结束时间
    private Date endDate;
    
    //归属时间
    private Date homeDate;
    
    //报表编号
    private String reportNumber;
    
    //生成报表的操作人id
    private String generateUserId;
   
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

	public String getReportNumber() {
		return reportNumber;
	}

	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}

	public String getGenerateUserId() {
		return generateUserId;
	}

	public void setGenerateUserId(String generateUserId) {
		this.generateUserId = generateUserId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getHomeDate() {
		return homeDate;
	}

	public void setHomeDate(Date homeDate) {
		this.homeDate = homeDate;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}
}
