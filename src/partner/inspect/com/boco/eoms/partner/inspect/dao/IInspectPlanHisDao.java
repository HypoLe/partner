package com.boco.eoms.partner.inspect.dao;


import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 10, 2012 3:30:58 PM 
 */
public interface IInspectPlanHisDao extends Dao{
	/**
	 * 查询巡检计划历史列表
	 */
    public PageModel findInspectPlanMainHisList(String whereSql, int offset, int pagesize);
    
    public InspectPlanMain getInspectPlanMainHis(String id);
    
    public PageModel findInspectPlanResHisList(String whereSql, int offset, int pagesize);
}
