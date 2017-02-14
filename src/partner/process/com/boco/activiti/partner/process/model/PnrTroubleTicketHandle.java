package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 故障工单处理
 * 
 *
 */
public class PnrTroubleTicketHandle  implements Serializable{
	
	private String id;
	//工单ID
	private String themeId;
	//工单主题
	private String theme;
	//回复时间
	private Date receiveTime;
	//回单人
	private String receivePerson;
	//处理人
	private String doPersons;
	//审核人
	private String auditor;
	//处理描述
	private String handleDescription;
	//故障是否恢复 字典值
	private String isRecover;
	//交通方式
	private String transport;
	
	//里程
	private Double mileage;
    private String taskId;
    private String processInstanceId;
    
    
	//审批状态  throughTheAudit：通过；auditDismissed：未通过。
	private String state ;
	
	//审核意见
	private String opinion;
	//审批时间
	private Date checkTime;
	
	//附件显示
	private String sheetAccessories;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getThemeId() {
		return themeId;
	}



	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}



	public String getTheme() {
		return theme;
	}



	public void setTheme(String theme) {
		this.theme = theme;
	}



	public Date getReceiveTime() {
		return receiveTime;
	}



	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}




	public String getReceivePerson() {
		return receivePerson;
	}



	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}



	public String getHandleDescription() {
		return handleDescription;
	}



	public void setHandleDescription(String handleDescription) {
		this.handleDescription = handleDescription;
	}




	public String getIsRecover() {
		return isRecover;
	}

	public void setIsRecover(String isRecover) {
		this.isRecover = isRecover;
	}

	public String getTransport() {
		return transport;
	}



	public void setTransport(String transport) {
		this.transport = transport;
	}



	public Double getMileage() {
		return mileage;
	}



	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getDoPersons() {
		return doPersons;
	}

	public void setDoPersons(String doPersons) {
		this.doPersons = doPersons;
	}
	
	public String getSheetAccessories() {
		return sheetAccessories;
	}

	public void setSheetAccessories(String sheetAccessories) {
		this.sheetAccessories = sheetAccessories;
	}

	public PnrTroubleTicketHandle() {
		
	}
	
	
	
}
