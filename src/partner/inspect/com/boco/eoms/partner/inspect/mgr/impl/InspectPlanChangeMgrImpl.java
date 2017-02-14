package com.boco.eoms.partner.inspect.mgr.impl;


import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectPlanChangeDao;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanChangeMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanChange;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 23, 2012 3:40:49 PM 
 */
public class InspectPlanChangeMgrImpl extends CommonGenericServiceImpl<InspectPlanChange> 
									implements IInspectPlanChangeMgr{
	private IInspectPlanChangeDao inspectPlanChangeDao;

	public IInspectPlanChangeDao getInspectPlanChangeDao() {
		return inspectPlanChangeDao;
	}
	
	/**
	 * 查询变更计划待审批列表
	 * @param hql
	 * @return
	 */
	public PageModel findInspectPlanChangeApproveList(final String hql, final Object[] params, final int offset,final int pagesize){
		return inspectPlanChangeDao.searchPaginated(hql, params, offset, pagesize);
	}

	public void setInspectPlanChangeDao(IInspectPlanChangeDao inspectPlanChangeDao) {
		this.setCommonGenericDao(inspectPlanChangeDao);
		this.inspectPlanChangeDao = inspectPlanChangeDao;
	}
	
	
}
