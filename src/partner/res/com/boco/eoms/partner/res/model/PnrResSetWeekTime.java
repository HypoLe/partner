package com.boco.eoms.partner.res.model;

import java.io.Serializable;

/**
 * 一个临时的对象
 * @author chenbing
 *
 */
public class PnrResSetWeekTime implements Serializable {
	

	private String cdictId;
	private String weekname;
	private String resLevel;
	private String resType;
	private String specialty;
	
	
	public String getCdictId() {
		return cdictId;
	}


	public void setCdictId(String cdictId) {
		this.cdictId = cdictId;
	}


	public String getWeekname() {
		return weekname;
	}


	public void setWeekname(String weekname) {
		this.weekname = weekname;
	}


	public String getResLevel() {
		return resLevel;
	}


	public void setResLevel(String resLevel) {
		this.resLevel = resLevel;
	}


	public String getResType() {
		return resType;
	}


	public void setResType(String resType) {
		this.resType = resType;
	}


	public String getSpecialty() {
		return specialty;
	}


	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}


	public PnrResSetWeekTime() {
		
	}
	
}
