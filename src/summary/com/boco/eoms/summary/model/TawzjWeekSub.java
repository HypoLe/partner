package com.boco.eoms.summary.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * @author 龚玉峰
 *
 */
public class TawzjWeekSub extends BaseObject{
	
	private static final long serialVersionUID = 1L;
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private String id;
	
	private String weekid; 
	
	private String datetime;  // 时间
	
	private String week;  // 1，2，3，4，5，6，7
	
	private String work;
	
	private String weekStr;
	 

	public String getWeekStr() {
		return weekStr;
	}

	public void setWeekStr(String weekStr) {
		this.weekStr = weekStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

 
	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getWeekid() {
		return weekid;
	}

	public void setWeekid(String weekid) {
		this.weekid = weekid;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	 
}
