package com.boco.eoms.materials.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.materials.dao.MatePermissionDao;
import com.boco.eoms.materials.model.MatePermission;

public class MatePermissionDaoHibernate extends BaseDaoHibernate implements MatePermissionDao {

	public String getPermission(String userid) {
		String permisson = "";
		List list = getHibernateTemplate().find("from MatePermission where user_id='"+userid+"'");
		if(list.size()>0){
			MatePermission mp =(MatePermission) list.get(0);
			permisson = mp.getPermission();
		}
		
		return permisson;
	}

}
