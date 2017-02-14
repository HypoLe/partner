package com.boco.eoms.partner.inspect.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectPlanGisPnrDao;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanGisPnrMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanGisPnr;

public class InspectPlanGisPnrMgrImpl extends CommonGenericServiceImpl<InspectPlanGisPnr> implements IInspectPlanGisPnrMgr {

	private IInspectPlanGisPnrDao inspectPlanGisPnrDao;

	public IInspectPlanGisPnrDao getInspectPlanGisPnrDao() {
		return inspectPlanGisPnrDao;
	}

	public void setInspectPlanGisPnrDao(IInspectPlanGisPnrDao inspectPlanGisPnrDao) {
		this.setCommonGenericDao(inspectPlanGisPnrDao);
		this.inspectPlanGisPnrDao = inspectPlanGisPnrDao;
	}
}
