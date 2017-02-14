package com.boco.eoms.partner.projectCost.model;

import com.boco.eoms.base.webapp.form.BaseForm;


public class ProjectCostForm extends BaseForm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
	
	private String advanceId;//预提费用ID
	private String advanceName;//预提费用名
	private String money;//金额
	private String type;//设备类型
	private String remark;//备注
	private String greatUser;//建单人
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
	public String getGreatUser() {
		return greatUser;
	}
	public void setGreatUser(String greatUser) {
		this.greatUser = greatUser;
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