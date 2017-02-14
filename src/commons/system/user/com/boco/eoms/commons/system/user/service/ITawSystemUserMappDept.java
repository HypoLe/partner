package com.boco.eoms.commons.system.user.service;

import com.boco.eoms.base.service.Manager;

public interface ITawSystemUserMappDept extends Manager {

	/**
	 * 通过4a传过来的deptid得到本地deptid
	 * 
	 * @param deptId
	 *            参数deptid
	 * @return 返回本地的部门id
	 */
	public String getLocalDeptId(String deptId);

	/**
	 * 通过本地deptid得到4a的deptid
	 * 
	 * @param deptId
	 *            参数deptid
	 * @return 返回4a的部门id
	 */
	public String getDeptId(String deptId);

}
