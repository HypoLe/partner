package com.boco.eoms.partner.dataSynch.model;

/** 
 * Description: 核查文件中的一行文件内容，一个核查文件中有可能包含多行
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Mar 28, 2012 2:48:26 PM
 */
public class CheckFile {
	private String dataFileName; //数据文件名
	private int dataFileSize; //数据文件大小
	private int dataFileRecordCount; //数据文件包含记录数量
	private String md5Code; //md5码(暂不使用)
	
	private boolean isDataIntegrity; //数据完整性(true完整 false不完整)
	
	public String getDataFileName() {
		return dataFileName;
	}
	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}
	public int getDataFileSize() {
		return dataFileSize;
	}
	public void setDataFileSize(int dataFileSize) {
		this.dataFileSize = dataFileSize;
	}
	public int getDataFileRecordCount() {
		return dataFileRecordCount;
	}
	public void setDataFileRecordCount(int dataFileRecordCount) {
		this.dataFileRecordCount = dataFileRecordCount;
	}
	public String getMd5Code() {
		return md5Code;
	}
	public void setMd5Code(String md5Code) {
		this.md5Code = md5Code;
	}
	public boolean getDataIntegrity() {
		return isDataIntegrity;
	}
	public void setDataIntegrity(boolean isDataIntegrity) {
		this.isDataIntegrity = isDataIntegrity;
	}
}
