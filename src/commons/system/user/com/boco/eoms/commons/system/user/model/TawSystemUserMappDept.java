package com.boco.eoms.commons.system.user.model;

public class TawSystemUserMappDept {
	
	private String id;
	// 4a的部门id
	private String deptId;
	// 4a的部门名称
	private String deptName;
	// 本地的部门id
	private String localDeptId;
	// 本地的部门名称
	private String localDeptName;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getLocalDeptId() {
		return localDeptId;
	}

	public void setLocalDeptId(String localDeptId) {
		this.localDeptId = localDeptId;
	}

	public String getLocalDeptName() {
		return localDeptName;
	}

	public void setLocalDeptName(String localDeptName) {
		this.localDeptName = localDeptName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
