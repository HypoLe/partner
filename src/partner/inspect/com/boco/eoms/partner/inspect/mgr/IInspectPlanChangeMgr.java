package com.boco.eoms.partner.inspect.mgr;


import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.inspect.model.InspectPlanChange;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 23, 2012 3:40:30 PM 
 */
public interface IInspectPlanChangeMgr extends CommonGenericService<InspectPlanChange>{
	
	/**
	 * 查询变更计划待审批列表
	 * @param hql
	 * @return
	 */
	public PageModel findInspectPlanChangeApproveList(final String hql, final Object[] params, final int offset,final int pagesize);
}
