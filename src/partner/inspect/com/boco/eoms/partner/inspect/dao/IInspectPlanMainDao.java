package com.boco.eoms.partner.inspect.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;

/** 
 * Description: 巡检主体 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 9:58:17 AM 
 */
public interface IInspectPlanMainDao extends CommonGenericDao<InspectPlanMain, String>{
	@SuppressWarnings("unchecked")
	public Map getUserCheckInfo(String deptId);
	
	/**
     * 查询所有的计划
     * @param offset
     * @param pagesize
     * @param whereStr
     * @return
     */
    public List<Object> getAllPlanMain(int offset,int pagesize,String whereStr);
}
