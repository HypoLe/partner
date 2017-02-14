package com.boco.eoms.partner.projectCost.model;



public class ProjectCostModel {
	private String id;
	private String advanceId;//预提费用ID
	private String advanceName;//预提费用名
	private String money;//金额
	private String type;//标识是工程费用还是补款申请
	private String remark;//备注
	private String greatUser;//建单人
	private String isDelete;//
	private String state;
	
	
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getGreatUser() {
		return greatUser;
	}
	public void setGreatUser(String greatUser) {
		this.greatUser = greatUser;
	}
	public String getAdvanceId() {
		return advanceId;
	}
	public void setAdvanceId(String advanceId) {
		this.advanceId = advanceId;
	}
	public String getAdvanceName() {
		return advanceName;
	}
	public void setAdvanceName(String advanceName) {
		this.advanceName = advanceName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
