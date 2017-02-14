package com.boco.eoms.partner.baseinfo.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.PartnerUserAndDept;


/**

 */
 public interface PartnerUserAndDeptMgr {
 	
	 public Map getPartnerUserAndDepts(final Integer curPage, final Integer pageSize,
				final String whereStr);
	 /**
		 * 判断人力地域表userId是否唯一
		 * 
		 *      
		 */
		public Boolean isunique(final String userId);
		public void savePartnerUserAndDept(PartnerUserAndDept partnerUserAndDept);
		public PartnerUserAndDept getPartnerUserAndDept(final String id);
		public void removePartnerUserAndDept(final String id);
		/**
		 * 根据部门iD选人
		 */
		public PartnerUserAndDept getPartnerUserAndDeptByDeptId(String deptId);
}