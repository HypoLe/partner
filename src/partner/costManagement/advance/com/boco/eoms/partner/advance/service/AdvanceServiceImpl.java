package com.boco.eoms.partner.advance.service;

import base.service.Nop3GenericServiceImpl;

import com.boco.eoms.partner.advance.dao.AdvanceDao;
import com.boco.eoms.partner.advance.model.AdvanceModel;

public class AdvanceServiceImpl extends Nop3GenericServiceImpl<AdvanceModel> implements AdvanceService {
	private AdvanceDao  advanceDao;

	public AdvanceDao getAdvanceDao() {
		return advanceDao;
	}

	public void setAdvanceDao(AdvanceDao advanceDao) {
		this.advanceDao = advanceDao;
		this.setNop3GenericDao(advanceDao);
	}

	
	


	
	
}
	



