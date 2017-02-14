package com.boco.eoms.partner.dataSynch.util;

/** 
 * Description: 数据同步配置类
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Mar 28, 2012 4:57:51 PM
 */
public class DataSynchConfig {
	private String filePath; //属地化端数据文件以及核查文件保存路径
	private Integer batchListSize;  //批量插入数据条数
	private boolean synchToMainDev; //是否往设备主表同步
	private boolean tmpTableCountQuery;//临时表总数据条数查询开关
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getBatchListSize() {
		return batchListSize;
	}
	public void setBatchListSize(Integer batchListSize) {
		this.batchListSize = batchListSize;
	}
	public boolean isSynchToMainDev() {
		return synchToMainDev;
	}
	public void setSynchToMainDev(boolean synchToMainDev) {
		this.synchToMainDev = synchToMainDev;
	}
	public boolean isTmpTableCountQuery() {
		return tmpTableCountQuery;
	}
	public void setTmpTableCountQuery(boolean tmpTableCountQuery) {
		this.tmpTableCountQuery = tmpTableCountQuery;
	}
	
	
}
