package com.boco.eoms.commons.system.user.service.impl;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.commons.system.user.dao.TawSystemUserMappDeptDao;
import com.boco.eoms.commons.system.user.service.ITawSystemUserMappDept;

public class TawSystemUserMappDeptImpl extends BaseManager implements
		ITawSystemUserMappDept {

	private TawSystemUserMappDeptDao tawSystemUserMappDeptDao = null;

	public String getLocalDeptId(String deptId) {
		return tawSystemUserMappDeptDao.getLocalDeptId(deptId);
	}

	public TawSystemUserMappDeptDao getTawSystemUserMappDeptDao() {
		return tawSystemUserMappDeptDao;
	}

	public void setTawSystemUserMappDeptDao(
			TawSystemUserMappDeptDao tawSystemUserMappDeptDao) {
		this.tawSystemUserMappDeptDao = tawSystemUserMappDeptDao;
	}

	public String getDeptId(String deptId) {
		return tawSystemUserMappDeptDao.getDeptId(deptId);
	}

}
