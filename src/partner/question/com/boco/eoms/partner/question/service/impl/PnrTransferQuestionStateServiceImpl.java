package com.boco.eoms.partner.question.service.impl;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.question.dao.IPnrTransferQuestionStateDao;
import com.boco.eoms.partner.question.dao.IPnrTransferQuestionStateJDBCDao;
import com.boco.eoms.partner.question.model.PnrTransferQuestionState;
import com.boco.eoms.partner.question.service.IPnrTransferQuestionStateService;

/**
 
 */
public class PnrTransferQuestionStateServiceImpl extends CommonGenericServiceImpl<PnrTransferQuestionState> implements IPnrTransferQuestionStateService {

    private IPnrTransferQuestionStateDao pnrTransferQuestionStateDao;
    
    private IPnrTransferQuestionStateJDBCDao pnrTransferQuestionStateDaoJDBC;

	public IPnrTransferQuestionStateDao getPnrTransferQuestionStateDao() {
		return pnrTransferQuestionStateDao;
	}

	public void setPnrTransferQuestionStateDao(
			IPnrTransferQuestionStateDao pnrTransferQuestionStateDao) {
		this.pnrTransferQuestionStateDao = pnrTransferQuestionStateDao;
		this.setCommonGenericDao(pnrTransferQuestionStateDao);
	}

	public IPnrTransferQuestionStateJDBCDao getPnrTransferQuestionStateDaoJDBC() {
		return pnrTransferQuestionStateDaoJDBC;
	}

	public void setPnrTransferQuestionStateDaoJDBC(
			IPnrTransferQuestionStateJDBCDao pnrTransferQuestionStateDaoJDBC) {
		this.pnrTransferQuestionStateDaoJDBC = pnrTransferQuestionStateDaoJDBC;
	}
    
	public List<PnrTransferQuestionState> selectPnrTransferQuestionStateByQuestionNumber(String number){
		return pnrTransferQuestionStateDaoJDBC.selectPnrTransferQuestionStateByQuestionNumber(number);
	}


}
