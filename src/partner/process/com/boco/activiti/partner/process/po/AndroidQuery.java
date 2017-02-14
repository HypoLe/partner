package com.boco.activiti.partner.process.po;

public class AndroidQuery {
	
	/**用户名id*/
	private String userId;
	/**流程引擎id*/
	private String processDefinitionKey;
	/**工单类型：抢修工单、本地网、干线、公客故障、大修改造、机房拆除*/
	private String workOrderType;
	/**工单主题*/
	private String theme;
	/**派单时间*/
	private String sendTime;
	/**工单状态：如 处理、抽查等*/
	private String workOrderState;
	//地市
	private String city;
	//区县
	private String country;
	
	private String maleGuestType ;//merge(归集)  alone(未归集)   addmerge(可加入归集)

	private String workOrderId;//工单号
	
	private String siteCd;
	
	
	public String getSiteCd() {
		return siteCd;
	}
	public void setSiteCd(String siteCd) {
		this.siteCd = siteCd;
	}
	public String getMaleGuestType() {
		return maleGuestType;
	}
	public void setMaleGuestType(String maleGuestType) {
		this.maleGuestType = maleGuestType;
	}
	public String getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	public String getWorkOrderType() {
		return workOrderType;
	}
	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getWorkOrderState() {
		return workOrderState;
	}
	public void setWorkOrderState(String workOrderState) {
		this.workOrderState = workOrderState;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
