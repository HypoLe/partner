package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 传输局工单处理
 * 
 *
 */
public class PnrTransferOfficeHandle  implements Serializable{
	
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
	//故障处理情况 字典值
	private String faultHandle;
	//故障原因
	private String faultCause;
	//故障回复
	private String isRecover;
	//返回结果
	private String  returnResult;
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
	
	//公司
	private String company;
	//电话
	private String telephone;
	//审批人和方案指定人 
	private String doPerson;
	//当前环节的task_def_key_
	private String linkName;
	//经度
	private String longitude;
	//纬度
	private String latitude;
	//1 代表审批；2 代表驳回；3 代表处理信息；4 代表审核信息；06 验收；09 代表抽查环节驳回；10 代表手机端确认抽查，WEB端通过归档；11 代表手机端没提交抽查，WEB端直接归档；
	//61 代表区县公司验收通过；62 代表区县公司验收驳回；
	private String operation;
	
	//决算金额-工费
	private Double balanceOperatingCosts;
	
	//决算金额-材料费
	private Double balanceMaterialsCosts;
	
	//审减金额-工费
	private Double shenJianOperatingCosts;
	
	//审减金额-材料费
	private Double shenJianMaterialsCosts;
	
	//是否在500内
	private String isFiveHundredInside;
	
	//故障类型
	private String faultType;
	
	//故障原因
	private String troubleReason;
	
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





	public String getFaultHandle() {
		return faultHandle;
	}

	public void setFaultHandle(String faultHandle) {
		this.faultHandle = faultHandle;
	}

	public String getFaultCause() {
		return faultCause;
	}

	public void setFaultCause(String faultCause) {
		this.faultCause = faultCause;
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

	public PnrTransferOfficeHandle() {
		
	}

	public String getIsRecover() {
		return isRecover;
	}

	public void setIsRecover(String isRecover) {
		this.isRecover = isRecover;
	}

	public String getReturnResult() {
		return returnResult;
	}

	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDoPerson() {
		return doPerson;
	}

	public void setDoPerson(String doPerson) {
		this.doPerson = doPerson;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Double getBalanceOperatingCosts() {
		return balanceOperatingCosts;
	}

	public void setBalanceOperatingCosts(Double balanceOperatingCosts) {
		this.balanceOperatingCosts = balanceOperatingCosts;
	}

	public Double getBalanceMaterialsCosts() {
		return balanceMaterialsCosts;
	}

	public void setBalanceMaterialsCosts(Double balanceMaterialsCosts) {
		this.balanceMaterialsCosts = balanceMaterialsCosts;
	}

	public Double getShenJianOperatingCosts() {
		return shenJianOperatingCosts;
	}

	public void setShenJianOperatingCosts(Double shenJianOperatingCosts) {
		this.shenJianOperatingCosts = shenJianOperatingCosts;
	}

	public Double getShenJianMaterialsCosts() {
		return shenJianMaterialsCosts;
	}

	public void setShenJianMaterialsCosts(Double shenJianMaterialsCosts) {
		this.shenJianMaterialsCosts = shenJianMaterialsCosts;
	}

	public String getIsFiveHundredInside() {
		return isFiveHundredInside;
	}

	public void setIsFiveHundredInside(String isFiveHundredInside) {
		this.isFiveHundredInside = isFiveHundredInside;
	}

	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public String getTroubleReason() {
		return troubleReason;
	}

	public void setTroubleReason(String troubleReason) {
		this.troubleReason = troubleReason;
	}
}
