package com.boco.eoms.partner.inspect.mgr;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.inspect.model.InspectPlanGis;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     liaojiming
 * @version:    1.0 
 */
public interface IInspectPlanGisMgr extends CommonGenericService<InspectPlanGis> {

	/**
	 * 按地市保存任务的完成数
	 */
	public void saveInspectPlanGis();
	
	/**
	 * 按部门保存任务的完成数
	 */
	public void saveInspectPlanGisPnrDept();
	
	/**
	 * 查询当前地市下任务的完成数
	 * @param city
	 * @return
	 */
	public List findInspectplanGis(String city);
}
