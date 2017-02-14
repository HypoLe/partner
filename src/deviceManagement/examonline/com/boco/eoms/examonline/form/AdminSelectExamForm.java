package com.boco.eoms.examonline.form;

import org.apache.struts.action.ActionForm;

public class AdminSelectExamForm extends ActionForm{
	private String starttime;
	private String endtime;
	private String provtype;
	private String company;
	private String specialty;
	
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getProvtype() {
		return provtype;
	}
	public void setProvtype(String provtype) {
		this.provtype = provtype;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
}
