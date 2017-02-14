package com.boco.eoms.task.model;


import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * <a href="BackUp.java.html"><i>View Source</i></a>
 * 
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="taw_system_code"
 */
public class Eoms_Task_User extends BaseObject{
	
	
	private String id ;
	private  String managerid;
	private String managername;
	private String userid;
	private String username;
	
	
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	

}

