package com.boco.eoms.partner.deviceAssess.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.deviceAssess.dao.AssessIndicatorDao;
import com.boco.eoms.partner.deviceAssess.mgr.AssessIndicatorService;
import com.boco.eoms.partner.deviceAssess.model.AssessIndicator;

public class AssessIndicatorServiceImpl extends CommonGenericServiceImpl<AssessIndicator>
  implements AssessIndicatorService{
	private AssessIndicatorDao assessIndicatorDao;

	public AssessIndicatorDao getAssessIndicatorDao() {
		return assessIndicatorDao;
	}

	public void setAssessIndicatorDao(AssessIndicatorDao assessIndicatorDao) {
		this.assessIndicatorDao = assessIndicatorDao;
		this.setCommonGenericDao(assessIndicatorDao);
	}
	
	
}
