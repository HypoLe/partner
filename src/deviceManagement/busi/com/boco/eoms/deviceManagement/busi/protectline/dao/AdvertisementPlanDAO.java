package com.boco.eoms.deviceManagement.busi.protectline.dao;

import java.util.List;

import com.boco.eoms.deviceManagement.busi.protectline.model.AdvertisementPlan;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface AdvertisementPlanDAO extends GenericDAO<AdvertisementPlan, String> {
	
	
	public List state();

}
