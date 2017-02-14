package com.boco.eoms.parter.baseinfo.fee.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeUnit;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeeUnitMgr;
import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeeUnitDao;

/**
 * <p>
 * Title:合作伙伴费用单位
 * </p>
 * <p>
 * Description:合作伙伴费用单位
 * </p>
 * <p>
 * Tue Sep 08 09:38:34 CST 2009
 * </p>
 * 
 * @author lvweihua
 * @version 3.5
 * 
 */
public class PartnerFeeUnitMgrImpl implements PartnerFeeUnitMgr {
 
	private PartnerFeeUnitDao  partnerFeeUnitDao;
 	
	public PartnerFeeUnitDao getPartnerFeeUnitDao() {
		return this.partnerFeeUnitDao;
	}
 	
	public void setPartnerFeeUnitDao(PartnerFeeUnitDao partnerFeeUnitDao) {
		this.partnerFeeUnitDao = partnerFeeUnitDao;
	}
 	
    public List getPartnerFeeUnits() {
    	return partnerFeeUnitDao.getPartnerFeeUnits();
    }
    
    public PartnerFeeUnit getPartnerFeeUnit(final String id) {
    	return partnerFeeUnitDao.getPartnerFeeUnit(id);
    }
    
    public void savePartnerFeeUnit(PartnerFeeUnit partnerFeeUnit) {
    	partnerFeeUnitDao.savePartnerFeeUnit(partnerFeeUnit);
    }
    
    public void removePartnerFeeUnit(final String id) {
    	partnerFeeUnitDao.removePartnerFeeUnit(id);
    }
    
    public Map getPartnerFeeUnits(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return partnerFeeUnitDao.getPartnerFeeUnits(curPage, pageSize, whereStr);
	}
	
}