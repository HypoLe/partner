package com.boco.activiti.partner.process.po;
/**
 * 公客故障工单--条件查询所有属性
 * @author Administrator
 *
 */
public class MaleGuestSelectAttribute {
	/**开始时间*/
	private String beginTime;
	/**结束时间*/
	private String endTime;
	/**工单号*/
	private String maleGuestNumber;
	/**主题*/
	private String theme;
	/**业务号码*/
	private String wsNum;
	/**当前状态*/
	private String status;
	/**地址*/
	private String installAddress;
	/**所属区域*/
	private String dept;
	/**当前处理人*/
	private String person;
	/**业务号码*/
	
	//区县
	private String county;
	//审批人签字
	private String approvalSign;
	//报表类型
	private String reportType;
	//地市
	private String region;
	
		/**工单号-system*/
	
	private String processInstanceId;
	/**局站编号*/
	private String siteCd;
	
	//审批人联系方式
	private String approvalPhone;
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getSiteCd() {
		return siteCd;
	}
	public void setSiteCd(String siteCd) {
		this.siteCd = siteCd;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getMaleGuestNumber() {
		return maleGuestNumber;
	}
	public void setMaleGuestNumber(String maleGuestNumber) {
		this.maleGuestNumber = maleGuestNumber;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getWsNum() {
		return wsNum;
	}
	public void setWsNum(String wsNum) {
		this.wsNum = wsNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInstallAddress() {
		return installAddress;
	}
	public void setInstallAddress(String installAddress) {
		this.installAddress = installAddress;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getApprovalSign() {
		return approvalSign;
	}
	public void setApprovalSign(String approvalSign) {
		this.approvalSign = approvalSign;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getApprovalPhone() {
		return approvalPhone;
	}
	public void setApprovalPhone(String approvalPhone) {
		this.approvalPhone = approvalPhone;
	}
}
