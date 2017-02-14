package com.boco.eoms.partner.res.model;

/** 
 * Description:  直放站 
 * Copyright:   Copyright (c)2012 
 * Company:     BOCO
 * @author:     liaojiming 
 * @version:    1.0 
 * Create at:   2012/7/16 
 */

public class PnrResRepeaters {

	private String id;
	private String address;                     //站址
	private Integer propertyType;               //产权类型
	private String sharing;						//共建共享
	private String netType;                     //网络类型
	private String openTime;					//2G开通年月
	private String TDOpenTime;			    	//TD开通年月
	private String adjustmentMessage;			//搬迁与配置调整情况
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(Integer propertyType) {
		this.propertyType = propertyType;
	}
	public String getSharing() {
		return sharing;
	}
	public void setSharing(String sharing) {
		this.sharing = sharing;
	}
	public String getNetType() {
		return netType;
	}
	public void setNetType(String netType) {
		this.netType = netType;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getTDOpenTime() {
		return TDOpenTime;
	}
	public void setTDOpenTime(String openTime) {
		TDOpenTime = openTime;
	}
	public String getAdjustmentMessage() {
		return adjustmentMessage;
	}
	public void setAdjustmentMessage(String adjustmentMessage) {
		this.adjustmentMessage = adjustmentMessage;
	}
}
