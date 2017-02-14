package com.boco.eoms.partner.netresource.util;

/** 
 * Description: 网络资源同步到巡检资源配置
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 25, 2013 10:06:17 AM
 */
public class PnrNetResourceConfig {
	private String specialty; //专业
	private String resType; //资源类型
	private String wholeSql; //全量sql
	
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getWholeSql() {
		return wholeSql;
	}
	public void setWholeSql(String wholeSql) {
		this.wholeSql = wholeSql;
	}
	
	
	
}
