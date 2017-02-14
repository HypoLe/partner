package com.boco.eoms.commons.system.user.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.user.dao.TawSystemUserMappDeptDao;
import com.boco.eoms.commons.system.user.model.TawSystemUserMappDept;

public class TawSystemUserMappDeptDaoHibernate extends BaseDaoHibernate
		implements TawSystemUserMappDeptDao {

	public String getLocalDeptId(String deptId) {
		String queryString = "from TawSystemUserMappDept tawSystemUserMappDept where "
				+ "tawSystemUserMappDept.deptId='" + deptId + "'";
		List list = new ArrayList();
		TawSystemUserMappDept tawSystemUserMappDept = null;
		if (deptId != null && !deptId.equals("")) {
			list = getHibernateTemplate().find(queryString);
			if (list != null && list.size() > 0) {
				tawSystemUserMappDept = (TawSystemUserMappDept) list.get(0);
			}
		}
		return tawSystemUserMappDept == null ? "" : tawSystemUserMappDept
				.getLocalDeptId();
	}

	public String getDeptId(String deptId) {
		String queryString = "from TawSystemUserMappDept tawSystemUserMappDept where "
				+ "tawSystemUserMappDept.localDeptId='" + deptId + "'";
		List list = new ArrayList();
		TawSystemUserMappDept tawSystemUserMappDept = null;
		if (deptId != null && !deptId.equals("")) {
			list = getHibernateTemplate().find(queryString);
			if (list != null && list.size() > 0) {
				tawSystemUserMappDept = (TawSystemUserMappDept) list.get(0);
			}
		}
		return tawSystemUserMappDept == null ? "" : tawSystemUserMappDept
				.getDeptId();
	}

}
