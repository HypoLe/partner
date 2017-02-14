package com.boco.eoms.partner.res.model;

/** 
 * Description:  集客家客
 * Copyright:   Copyright (c)2012 
 * Company:     BOCO
 * @author:     liaojiming 
 * @version:    1.0 
 * Create at:   2012/7/16 
 */

public class PnrResJieke {

	private String id;
	private String clientNumber ;         //客户数
	private String clientType ;		   //业务类型
	private String stationName ;		   //所属站点名称
	private String stationNumber ;	       //所属站点编号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStationNumber() {
		return stationNumber;
	}
	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}
	
	
}
