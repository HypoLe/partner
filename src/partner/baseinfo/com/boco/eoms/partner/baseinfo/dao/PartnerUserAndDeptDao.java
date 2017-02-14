package com.boco.eoms.partner.baseinfo.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndDept;

/**
 * 
 */
public interface PartnerUserAndDeptDao extends Dao {

	public Map getPartnerUserAndDepts(final Integer curPage, final Integer pageSize,
			final String whereStr);  
     
	public void savePartnerUserAndDept(PartnerUserAndDept partnerUserAndDept);
	public Boolean isunique(String userId);
	public PartnerUserAndDept getPartnerUserAndDept(String id) ;
	public void removePartnerUserAndDept(final String id);
	/**
	 * 根据部门iD选人
	 */
	public PartnerUserAndDept getPartnerUserAndDeptByDeptId(String deptId);
}