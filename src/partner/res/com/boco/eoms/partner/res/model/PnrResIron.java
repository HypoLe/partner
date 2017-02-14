package com.boco.eoms.partner.res.model;

/** 
 * Description:  铁搭
 * Copyright:   Copyright (c)2012 
 * Company:     BOCO
 * @author:     liaojiming 
 * @version:    1.0 
 * Create at:   2012/7/16 
 */

public class PnrResIron {

	private String id;
	private String ironType;             //型号
	private String indoorNumber;         //室内机编号
	private String outdoorNumber;        //室外机编号
	private String phaseNumber ;		  //相数
	private String power ;				  //功率
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIronType() {
		return ironType;
	}
	public void setIronType(String ironType) {
		this.ironType = ironType;
	}
	public String getIndoorNumber() {
		return indoorNumber;
	}
	public void setIndoorNumber(String indoorNumber) {
		this.indoorNumber = indoorNumber;
	}
	public String getOutdoorNumber() {
		return outdoorNumber;
	}
	public void setOutdoorNumber(String outdoorNumber) {
		this.outdoorNumber = outdoorNumber;
	}
	public String getPhaseNumber() {
		return phaseNumber;
	}
	public void setPhaseNumber(String phaseNumber) {
		this.phaseNumber = phaseNumber;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	
	
}
