package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 传输局工单与运维商城的接口，运维商城填单时，回传过来的单据明细。
 * @author Administrator
 *
 */
public class OperationReturnResultDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	//工单类型
	private String ticketType;
	//故障单号
	private String faultOrderId;
	//商品名称
	private String goodName;
	//商品规格
	private String goodStandard;
	//商品单价
	private String goodPrice;
	//商品数量
	private String goodNumber;
	//总金额
	private String totalMoney;
	
	private Date insertTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getFaultOrderId() {
		return faultOrderId;
	}

	public void setFaultOrderId(String faultOrderId) {
		this.faultOrderId = faultOrderId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodStandard() {
		return goodStandard;
	}

	public void setGoodStandard(String goodStandard) {
		this.goodStandard = goodStandard;
	}

	public String getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}

	public String getGoodNumber() {
		return goodNumber;
	}

	public void setGoodNumber(String goodNumber) {
		this.goodNumber = goodNumber;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public OperationReturnResultDetail() {
		
	}
	
		
}
