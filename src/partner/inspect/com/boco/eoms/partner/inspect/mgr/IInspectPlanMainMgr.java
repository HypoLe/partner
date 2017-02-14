package com.boco.eoms.partner.inspect.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;

/** 
 * Description: 巡检任务主体 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 10:09:11 AM 
 */
public interface IInspectPlanMainMgr extends CommonGenericService<InspectPlanMain>{
	/**
	 * 查询巡检计划待审批列表
	 */
    public PageModel findInspectPlanMainApproveList(final String hql, final Object[] params, final int offset,final int pagesize);
    
    /**
     * lee
     * 查询用户的巡检计划和巡检资源
     * @param deptId
     * @return
     */
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
