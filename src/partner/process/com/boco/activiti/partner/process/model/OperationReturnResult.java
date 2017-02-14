package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 传输局工单与运维商城的接口，运维商城填单时，回传过来的单据。
 * @author Administrator
 *
 */
public class OperationReturnResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	//工单类型
	private String ticketType;
	//工单号
	private String processInstanceId;
	//订单号
	private String orderId;
	//回填完成时间
	private Date backfillTime;
	//订单总金额
	private String orderPrice;
	
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
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public Date getBackfillTime() {
		return backfillTime;
	}
	public void setBackfillTime(Date backfillTime) {
		this.backfillTime = backfillTime;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
	
}
