package com.boco.eoms.commons.system.user.dao;

import com.boco.eoms.base.dao.Dao;

public interface TawSystemUserMappDeptDao extends Dao{

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
