package com.boco.eoms.partner.inspect.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.partner.inspect.model.InspectPlanItem;

/**
 * Description: Copyright: Copyright (c)2012 Company: BOCO
 * 
 * @author: LEE
 * @version: 1.0 Create at: Jul 16, 2012 9:51:58 AM
 */
public interface IInspectPlanItemDao extends
		CommonGenericDao<InspectPlanItem, String> {
	public Map<String, List> queryBigItem(String planResId);

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
