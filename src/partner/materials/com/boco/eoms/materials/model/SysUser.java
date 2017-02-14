package com.boco.eoms.materials.model;

/**
 * 用户表
 * 
 * @author wanghongliang
 * 
 */
public class SysUser {
	private String id;
	private String username;
	private String password;
	/**
	 * 权限
	 */
	private String privilege;
	private String usersRemark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUsersRemark() {
		return usersRemark;
	}

	public void setUsersRemark(String usersRemark) {
		this.usersRemark = usersRemark;
	}

	
}
