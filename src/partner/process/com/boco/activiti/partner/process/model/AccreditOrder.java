package com.boco.activiti.partner.process.model;

import java.util.Date;

public class AccreditOrder {
	/**表的主键*/
	private String id;
	/**授权记录id*/
	private String accreditId;
	/**派发工单号*/
	private String orderId;
	/**工单主题*/
	private String zhuTi;
	/**派发时间*/
	private Date payoutTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccreditId() {
		return accreditId;
	}
	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getPayoutTime() {
		return payoutTime;
	}
	public void setPayoutTime(Date payoutTime) {
		this.payoutTime = payoutTime;
	}
	public String getZhuTi() {
		return zhuTi;
	}
	public void setZhuTi(String zhuTi) {
		this.zhuTi = zhuTi;
	}
	
	
}
