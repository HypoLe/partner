package com.boco.eoms.partner.res.model;

import java.util.Date;

/**
 * 	保存插入成功的条数的日志表
 * 	对应表 pnr_resource_success_log
 	* @author WangJun
 	* @ClassName: PnrResourceInventoryRoom
 	* @date Nov 27, 2015 2:39:54 PM
 	* @description 
 */
public class PnrResourceSuccessLog {
	private String id;
	private String userId;
	private Date insertTime;
	private String cityNames;
	private String insertSuccessNum;
	private String zyglQueryNum;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getCityNames() {
		return cityNames;
	}
	public void setCityNames(String cityNames) {
		this.cityNames = cityNames;
	}
	public String getInsertSuccessNum() {
		return insertSuccessNum;
	}
	public void setInsertSuccessNum(String insertSuccessNum) {
		this.insertSuccessNum = insertSuccessNum;
	}
	public String getZyglQueryNum() {
		return zyglQueryNum;
	}
	public void setZyglQueryNum(String zyglQueryNum) {
		this.zyglQueryNum = zyglQueryNum;
	}
}
