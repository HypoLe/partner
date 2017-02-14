package com.boco.eoms.partner.dataSynch.model;

import java.util.Date;

/** 
 * Description: 同步异常数据记录
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Mar 28, 2012 6:22:28 PM
 */
public class SynchExceptionRecord {
	private String id;
	private String cuid;
	private String dataType;  //数据类型
	private String exceptionField;  //异常字段
	private String exceptionReason; //异常原因
	private String exceptionFileName; //异常文件名称
	private String createTime;  //异常创建时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCuid() {
		return cuid;
	}
	public void setCuid(String cuid) {
		this.cuid = cuid;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getExceptionReason() {
		return exceptionReason;
	}
	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getExceptionField() {
		return exceptionField;
	}
	public void setExceptionField(String exceptionField) {
		this.exceptionField = exceptionField;
	}
	public String getExceptionFileName() {
		return exceptionFileName;
	}
	public void setExceptionFileName(String exceptionFileName) {
		this.exceptionFileName = exceptionFileName;
	}
	
}
