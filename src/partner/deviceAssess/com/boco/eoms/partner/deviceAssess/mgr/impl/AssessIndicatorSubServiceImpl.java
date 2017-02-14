package com.boco.eoms.partner.deviceAssess.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.deviceAssess.dao.AssessIndicatorSubDao;
import com.boco.eoms.partner.deviceAssess.mgr.AssessIndicatorSubService;
import com.boco.eoms.partner.deviceAssess.model.AssessIndicatorSub;

public class AssessIndicatorSubServiceImpl extends CommonGenericServiceImpl<AssessIndicatorSub>
 implements AssessIndicatorSubService{
	private AssessIndicatorSubDao assessIndicatorSubDao;

	public AssessIndicatorSubDao getAssessIndicatorSubDao() {
		return assessIndicatorSubDao;
	}

	public void setAssessIndicatorSubDao(AssessIndicatorSubDao assessIndicatorSubDao) {
		this.assessIndicatorSubDao = assessIndicatorSubDao;
		this.setCommonGenericDao(assessIndicatorSubDao);
	}
	
}
