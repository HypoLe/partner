package com.boco.eoms.partner.inspect.mgr.impl;


import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.partner.inspect.dao.IInspectPlanHisDao;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanHisMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 10, 2012 11:47:38 AM 
 */
public class InspectPlanHisMgrImpl implements IInspectPlanHisMgr {
	private IInspectPlanHisDao inspectPlanHisDao;
	
	/**
	 * 查询巡检计划历史列表
	 */
    public PageModel findInspectPlanMainHisList(String whereSql, int offset, int pagesize){
    	return inspectPlanHisDao.findInspectPlanMainHisList(whereSql, offset, pagesize);
    }
    
    public InspectPlanMain getInspectPlanMainHis(String id){
    	return inspectPlanHisDao.getInspectPlanMainHis(id);
    }
    
    public PageModel findInspectPlanResHisList(String whereSql, int offset, int pagesize){
    	return inspectPlanHisDao.findInspectPlanResHisList(whereSql, offset, pagesize);
    }

	public IInspectPlanHisDao getInspectPlanHisDao() {
		return inspectPlanHisDao;
	}

	public void setInspectPlanHisDao(IInspectPlanHisDao inspectPlanHisDao) {
		this.inspectPlanHisDao = inspectPlanHisDao;
	}
    
    
}
