package com.boco.eoms.deviceManagement.busi.line.model;

public class ProjectBaseInfo {

	private String id;
	//线路修缮申请信息
	private String projectName;
	private String projectType;
	private String projectLocation;
	private String networkType;
	private String applyer;
	private String applyCompany;
	private String applyTime;
	private String effect;
	private String projectLength;
	private String startTime;
	private String lastTime;
	private String allower;
	
	private String attachment;
	//线路修缮审定信息
	private String checker;
	private String projectPay;
	private String checkattachment;
	private String checkDate;
	private String checkRemarks;
	private String consignee;    //线路修缮审定通过后委托人
	//线路修缮委托施工信息
	private String constructor;
	private String constructorLocation;
	private String constructorPhone;
	private String constructorContacter;
	private String constructorType;
	private String constructPay;
	private String constructStartTime;
	private String constructRemarks;
	private String constructattachment;
	
	//线路修缮施工信息
	
	private String constructor2;
	private String constructorLocation2;
	private String constructorPhone2;
	private String constructorContacter2;
	private String constructStartTime2;
	private String constructEndTime2;
	private String constructRemarks2;
	private String constructReview;
	
	//线路修缮验收信息
	private String acceptResult;
	private String acceptRemarks;
	private String acceptattachment;
	private String accepter;
	private String acceptDate;
	
	private String remarks;
	private String status;
	
	public ProjectBaseInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectLocation() {
		return projectLocation;
	}

	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}


	public String getProjectLength() {
		return projectLength;
	}

	public void setProjectLength(String projectLength) {
		this.projectLength = projectLength;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setApplyCompany(String applyCompany) {
		this.applyCompany = applyCompany;
	}

	public String getApplyCompany() {
		return applyCompany;
	}

	public void setApplyer(String applyer) {
		this.applyer = applyer;
	}

	public String getApplyer() {
		return applyer;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachment() {
		return attachment;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getProjectPay() {
		return projectPay;
	}

	public void setProjectPay(String projectPay) {
		this.projectPay = projectPay;
	}

	public String getCheckattachment() {
		return checkattachment;
	}

	public void setCheckattachment(String checkattachment) {
		this.checkattachment = checkattachment;
	}

	public String getCheckRemarks() {
		return checkRemarks;
	}

	public void setCheckRemarks(String checkRemarks) {
		this.checkRemarks = checkRemarks;
	}

	public String getConstructor() {
		return constructor;
	}

	public void setConstructor(String constructor) {
		this.constructor = constructor;
	}

	public String getConstructorLocation() {
		return constructorLocation;
	}

	public void setConstructorLocation(String constructorLocation) {
		this.constructorLocation = constructorLocation;
	}

	public String getConstructorPhone() {
		return constructorPhone;
	}

	public void setConstructorPhone(String constructorPhone) {
		this.constructorPhone = constructorPhone;
	}

	public String getConstructorContacter() {
		return constructorContacter;
	}

	public void setConstructorContacter(String constructorContacter) {
		this.constructorContacter = constructorContacter;
	}

	public String getConstructorType() {
		return constructorType;
	}

	public void setConstructorType(String constructorType) {
		this.constructorType = constructorType;
	}

	public String getConstructPay() {
		return constructPay;
	}

	public void setConstructPay(String constructPay) {
		this.constructPay = constructPay;
	}

	public String getConstructStartTime() {
		return constructStartTime;
	}

	public void setConstructStartTime(String constructStartTime) {
		this.constructStartTime = constructStartTime;
	}

	public String getConstructRemarks() {
		return constructRemarks;
	}

	public void setConstructRemarks(String constructRemarks) {
		this.constructRemarks = constructRemarks;
	}

	public String getConstructattachment() {
		return constructattachment;
	}

	public void setConstructattachment(String constructattachment) {
		this.constructattachment = constructattachment;
	}

	public String getAcceptResult() {
		return acceptResult;
	}

	public void setAcceptResult(String acceptResult) {
		this.acceptResult = acceptResult;
	}

	public String getAcceptRemarks() {
		return acceptRemarks;
	}

	public void setAcceptRemarks(String acceptRemarks) {
		this.acceptRemarks = acceptRemarks;
	}

	public String getAcceptattachment() {
		return acceptattachment;
	}

	public void setAcceptattachment(String acceptattachment) {
		this.acceptattachment = acceptattachment;
	}

	public void setAllower(String allower) {
		this.allower = allower;
	}

	public String getAllower() {
		return allower;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public String getConstructor2() {
		return constructor2;
	}

	public void setConstructor2(String constructor2) {
		this.constructor2 = constructor2;
	}

	public String getConstructorLocation2() {
		return constructorLocation2;
	}

	public void setConstructorLocation2(String constructorLocation2) {
		this.constructorLocation2 = constructorLocation2;
	}

	public String getConstructorPhone2() {
		return constructorPhone2;
	}

	public void setConstructorPhone2(String constructorPhone2) {
		this.constructorPhone2 = constructorPhone2;
	}

	public String getConstructorContacter2() {
		return constructorContacter2;
	}

	public void setConstructorContacter2(String constructorContacter2) {
		this.constructorContacter2 = constructorContacter2;
	}

	public String getConstructStartTime2() {
		return constructStartTime2;
	}

	public void setConstructStartTime2(String constructStartTime2) {
		this.constructStartTime2 = constructStartTime2;
	}

	public String getConstructEndTime2() {
		return constructEndTime2;
	}

	public void setConstructEndTime2(String constructEndTime2) {
		this.constructEndTime2 = constructEndTime2;
	}

	public String getConstructRemarks2() {
		return constructRemarks2;
	}

	public void setConstructRemarks2(String constructRemarks2) {
		this.constructRemarks2 = constructRemarks2;
	}

	public String getConstructReview() {
		return constructReview;
	}

	public void setConstructReview(String constructReview) {
		this.constructReview = constructReview;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}

	public String getAccepter() {
		return accepter;
	}
	
	
	

}
