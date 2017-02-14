package com.boco.eoms.partner.question.service;


import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.question.model.PnrTransferQuestionState;


public interface IPnrTransferQuestionStateService extends CommonGenericService<PnrTransferQuestionState> {
	
	/**
	 * 根据问题编号,查询相应问题的状态
	 * @return
	 */
	public List<PnrTransferQuestionState> selectPnrTransferQuestionStateByQuestionNumber(String number);
}
