package com.boco.eoms.mobile.model;

import java.io.Serializable;

/**
 * 

CREATE TABLE informix.pnr_android_allstatistics ( 
    id                	NVARCHAR(32) NOT NULL,
    statisticsName          	VARCHAR(32),
    statisticsType       	VARCHAR(32),
    currentScore        	int,
    previousScore          	int,
    addDate      	DATETIME YEAR to SECOND,
    addUser    	VARCHAR(32),
    PRIMARY KEY(id)
)
LOCK MODE PAGE
GO

 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LEE
 * @version:    1.0
 * Create at:   2012-12-15 下午02:30:32
 */

public class AllStatistics implements Serializable{
	private static final long serialVersionUID = 1L;
	String id;//统计ID
	String statisticsName;//统计名称:包换对代维公司的统计,费用统计或其它统计
	String statisticsType;//用来区分统计类别公司考核  type=company;
	int  currentScore;//当月数据
	int previousScore;//上月数据
	String addDate;//添加时间
	String addUser;//添加人
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public int getCurrentScore() {
		return currentScore;
	}
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPreviousScore() {
		return previousScore;
	}
	public void setPreviousScore(int previousScore) {
		this.previousScore = previousScore;
	}
	public String getStatisticsName() {
		return statisticsName;
	}
	public void setStatisticsName(String statisticsName) {
		this.statisticsName = statisticsName;
	}
	public String getStatisticsType() {
		return statisticsType;
	}
	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}
	
}
