package com.boco.eoms.task.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

public class EomsTaskForm  extends BaseForm{

	

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected  String managerid;
	protected String managername;
	protected String userid;
	protected String username;
	

	/**
	 * 用户排序id
	 */
	protected int orderid;
	
	public String getManagerid() {
		return managerid;
	}
	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}
	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	
	
}
