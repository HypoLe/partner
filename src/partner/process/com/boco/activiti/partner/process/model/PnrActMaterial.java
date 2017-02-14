package com.boco.activiti.partner.process.model;

import java.io.Serializable;

/**
 * 拆除机房材料表
 * 
 * @author WANGJUN
 * 
 */
public class PnrActMaterial implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//序号
	private String serialNum;
	//材料名称
	private String materialName;
	//规格型号
	private String specifications;
	//单价
	private String unitPrice;
	//数量
	private String materialNum;
	//金额
	private String amount;
	//工单流程id
	private String processInstanceId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getMaterialNum() {
		return materialNum;
	}
	public void setMaterialNum(String materialNum) {
		this.materialNum = materialNum;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	
}
