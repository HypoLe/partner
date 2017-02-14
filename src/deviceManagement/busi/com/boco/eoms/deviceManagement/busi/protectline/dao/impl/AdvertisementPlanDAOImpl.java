package com.boco.eoms.deviceManagement.busi.protectline.dao.impl;



import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;


import com.boco.eoms.deviceManagement.busi.protectline.dao.AdvertisementPlanDAO;
import com.boco.eoms.deviceManagement.busi.protectline.model.AdvertisementPlan;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;


public class AdvertisementPlanDAOImpl extends GenericDAOImpl<AdvertisementPlan, String> implements
		AdvertisementPlanDAO {

	public List state() {
		// TODO Auto-generated method stub
		Session session = getSession();
		String sql = "select city , sum(generalstone) sum1,sum(detectstone) sum2,sum(generalstone)+sum(detectstone) sumall from dm_advertisementplan_info where status=2 group by city";
		SQLQuery query = session.createSQLQuery(sql);
		List list = query.list();
		return list;
	}

	

}
