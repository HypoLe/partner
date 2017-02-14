package com.boco.eoms.partner.question.dao;

import java.util.List;

import com.boco.eoms.partner.question.model.PnrTransferQuestion;
import com.boco.eoms.partner.question.model.PnrTransferQuestionState;


public interface IPnrTransferQuestionStateJDBCDao {
    
	public List<PnrTransferQuestionState> selectPnrTransferQuestionStateByQuestionNumber(String number);
}
