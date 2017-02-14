package com.boco.eoms.arcGis.model;

import java.util.Date;

/**
 * add by chenruoke
 * 实时经纬度表 用来呈现轨迹
 */
public class Trajectory {
	private String id;
	private String imis;
	private String phoneNum;
	private String carNum;
	private String type;
	private String x;
	private String y;
	private String state;		//状态，在用(执行任务)或空闲
	private String ifOnLine;	//是否在线 0：在线，1：离线
	private Date insertTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImis() {
		return imis;
	}
	public void setImis(String imis) {
		this.imis = imis;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIfOnLine() {
		return ifOnLine;
	}
	public void setIfOnLine(String ifOnLine) {
		this.ifOnLine = ifOnLine;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}	
	
	
}
