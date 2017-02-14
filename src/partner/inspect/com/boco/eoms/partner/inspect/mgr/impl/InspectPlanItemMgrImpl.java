package com.boco.eoms.partner.inspect.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectPlanItemDao;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanItemMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanItem;

/**
 * Description: Copyright: Copyright (c)2012 Company: BOCO
 * 
 * @author: lee
 * @version: 1.0 Create at: Jul 9, 2012 10:09:39 AM
 */
public class InspectPlanItemMgrImpl extends
		CommonGenericServiceImpl<InspectPlanItem> implements
		IInspectPlanItemMgr {

	private IInspectPlanItemDao inspectPlanItemDao;

	public IInspectPlanItemDao getInspectPlanItemDao() {
		return inspectPlanItemDao;
	}

	public void setInspectPlanItemDao(IInspectPlanItemDao inspectPlanItemDao) {
		this.setCommonGenericDao(inspectPlanItemDao);
		this.inspectPlanItemDao = inspectPlanItemDao;
	}

	public Map<String, List> queryBigItem(String planResId) {
		return inspectPlanItemDao.queryBigItem(planResId);
	}

	public InspectPlanItem findById(String id) {
		List<InspectPlanItem> inspectPlanItems = this.inspectPlanItemDao
				.findByHql("from InspectPlanItem where id = " + id);
		return inspectPlanItems.isEmpty() ? null : inspectPlanItems.get(0);
	}

	@Override
	public List<InspectPlanItem> findInspectPlanItem(String InspectPlanResId,
			int exceptionFlag, int isHandle, Integer pageIndex, Integer pageSize) {
		return inspectPlanItemDao.findInspectPlanItem(InspectPlanResId,
				exceptionFlag, isHandle, pageIndex, pageSize);
	}
	@Override
	public Integer countInspectPlanItem(String InspectPlanResId,
			int exceptionFlag, int isHandle) {
		return inspectPlanItemDao.countInspectPlanItem(InspectPlanResId,
				exceptionFlag, isHandle);
	}
}
