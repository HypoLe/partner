package com.boco.eoms.partner.inspect.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.inspect.model.InspectPlanItem;

/**
 * Description: 巡检任务主体 Copyright: Copyright (c)2012 Company: BOCO
 * 
 * @author: LEE
 * @version: 1.0 Create at: Jul 9, 2012 10:09:11 AM
 */
public interface IInspectPlanItemMgr extends
		CommonGenericService<InspectPlanItem> {
	public Map<String, List> queryBigItem(String planResId);

	public InspectPlanItem findById(String id);

	/**
	 * 通过条件查找巡检项
	 * 
	 * @param InspectPlanResId
	 *            元任务ID
	 * @param exceptionFlag
	 *            异常标示
	 * @param isHandle
	 *            处理标示
	 * @param pageIndex
	 *            开始行号
	 * @param pageSize
	 *            先生行数
	 * @return
	 */
	public List<InspectPlanItem> findInspectPlanItem(String InspectPlanResId,
			int exceptionFlag, int isHandle, final Integer pageIndex,
			final Integer pageSize);

	/**
	 * 通过条件查找巡检项
	 * 
	 * @param InspectPlanResId
	 *            元任务ID
	 * @param exceptionFlag
	 *            异常标示
	 * @param isHandle
	 *            处理标示
	 * @return
	 */
	public Integer countInspectPlanItem(String InspectPlanResId,
			int exceptionFlag, int isHandle);
}
