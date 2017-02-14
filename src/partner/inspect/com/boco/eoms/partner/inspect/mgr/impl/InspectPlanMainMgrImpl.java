package com.boco.eoms.partner.inspect.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectPlanMainDao;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;

/** 
 * Description: 巡检任务主体
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 k
 * Create at:   Jul 9, 2012 10:09:39 AM 
 */
public class InspectPlanMainMgrImpl extends CommonGenericServiceImpl<InspectPlanMain> 
							implements IInspectPlanMainMgr {
	private IInspectPlanMainDao inspectPlanMainDao;
	
	/**
	 * 查询巡检计划待审批列表
	 */
    public PageModel findInspectPlanMainApproveList(final String hql, final Object[] params, final int offset,final int pagesize){
    	return inspectPlanMainDao.searchPaginated(hql, params, offset, pagesize);
    }
	
    @SuppressWarnings("unchecked")
	public Map getUserCheckInfo(String deptId){
    	return inspectPlanMainDao.getUserCheckInfo(deptId);
    }

	public IInspectPlanMainDao getInspectPlanMainDao() {
		return inspectPlanMainDao;
	}

	public void setInspectPlanMainDao(IInspectPlanMainDao inspectPlanMainDao) {
		this.setCommonGenericDao(inspectPlanMainDao);
		this.inspectPlanMainDao = inspectPlanMainDao;
	}
	/**
     * 查询所有的计划
     * @param offset
     * @param pagesize
     * @param whereStr
     * @return
     */
    public List<Object> getAllPlanMain(int offset,int pagesize,String whereStr){
    	return inspectPlanMainDao.getAllPlanMain(offset, pagesize, whereStr);
    }
}
