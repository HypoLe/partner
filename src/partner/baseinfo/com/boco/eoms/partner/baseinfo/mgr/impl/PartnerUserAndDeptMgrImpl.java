package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.dao.PartnerUserAndDeptDao;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndDept;

/**

 * 
 */
public class PartnerUserAndDeptMgrImpl implements PartnerUserAndDeptMgr {
 
	private PartnerUserAndDeptDao  partnerUserAndDeptDao;
 	
	public PartnerUserAndDeptDao getPartnerUserAndDeptDao() {
		return this.partnerUserAndDeptDao;
	}
 	
	public void setPartnerUserAndDeptDao(PartnerUserAndDeptDao partnerUserAndDeptDao) {
		this.partnerUserAndDeptDao = partnerUserAndDeptDao;
	}
	
	 public Map getPartnerUserAndDepts(final Integer curPage, final Integer pageSize,
				final String whereStr) {
			return partnerUserAndDeptDao.getPartnerUserAndDepts(curPage, pageSize, whereStr);
		}

	@Override
	public PartnerUserAndDept getPartnerUserAndDept(String id) {
		// TODO Auto-generated method stub
    	return partnerUserAndDeptDao.getPartnerUserAndDept(id);

	}

	@Override
	public Boolean isunique(String userId) {
		// TODO Auto-generated method stub
		return partnerUserAndDeptDao.isunique(userId);
	}

	@Override
	public void savePartnerUserAndDept(PartnerUserAndDept partnerUserAndDept) {
		// TODO Auto-generated method stub
    	partnerUserAndDeptDao.savePartnerUserAndDept(partnerUserAndDept);
	}
	@Override 
	public void removePartnerUserAndDept(final String id) {
	    	partnerUserAndDeptDao.removePartnerUserAndDept(id);
	}

	@Override
	public PartnerUserAndDept getPartnerUserAndDeptByDeptId(
			String deptId) {
		// TODO Auto-generated method stub
		return partnerUserAndDeptDao.getPartnerUserAndDeptByDeptId(deptId);
	}
}
