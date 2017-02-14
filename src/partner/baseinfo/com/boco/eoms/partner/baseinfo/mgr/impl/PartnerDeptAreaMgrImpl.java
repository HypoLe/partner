package com.boco.eoms.partner.baseinfo.mgr.impl;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.boco.eoms.partner.baseinfo.dao.PartnerDeptAreaDao;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;


/**
 * <p>
 * Title:����
 * </p>
 * <p>
 * Description:����
 * </p>
 * <p>
 * Mon Feb 09 10:57:12 CST 2009
 * </p>
 * 
 * @author leefeng
 * @version 3.5
 * 
 */
public class PartnerDeptAreaMgrImpl extends PartnerDeptMgrImpl {
	private PartnerDeptAreaDao partnerDeptAreaDao;

	public PartnerDeptAreaDao getPartnerDeptAreaDao() {
		return partnerDeptAreaDao;
	}

	public void setPartnerDeptAreaDao(PartnerDeptAreaDao partnerDeptAreaDao) {
		this.partnerDeptAreaDao = partnerDeptAreaDao;
	}
	
	public String deptIdToName(String id, String beanId){
		String areaName=partnerDeptAreaDao.deptIdToName(id);
		if(areaName!=null&&(!("").equals(areaName))){
		return areaName;
		}else{
			return "";
		}
	}
}