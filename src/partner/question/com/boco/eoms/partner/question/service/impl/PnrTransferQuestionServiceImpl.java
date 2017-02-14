package com.boco.eoms.partner.question.service.impl;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.question.dao.IPnrTransferQuestionDao;
import com.boco.eoms.partner.question.dao.IPnrTransferQuestionJDBCDao;
import com.boco.eoms.partner.question.model.PnrTransferQuestion;
import com.boco.eoms.partner.question.service.IPnrTransferQuestionService;

/**
 
 */
public class PnrTransferQuestionServiceImpl extends CommonGenericServiceImpl<PnrTransferQuestion> implements IPnrTransferQuestionService {

    private IPnrTransferQuestionDao pnrTransferQuestionDao;
    
    private IPnrTransferQuestionJDBCDao pnrTransferQuestionDaoJDBC;
    
    
	public IPnrTransferQuestionDao getPnrTransferQuestionDao() {
		return pnrTransferQuestionDao;
	}
	public void setPnrTransferQuestionDao(
			IPnrTransferQuestionDao pnrTransferQuestionDao) {
		this.pnrTransferQuestionDao = pnrTransferQuestionDao;
		this.setCommonGenericDao(pnrTransferQuestionDao);
	}
	public IPnrTransferQuestionJDBCDao getPnrTransferQuestionDaoJDBC() {
		return pnrTransferQuestionDaoJDBC;
	}
	public void setPnrTransferQuestionDaoJDBC(
			IPnrTransferQuestionJDBCDao pnrTransferQuestionDaoJDBC) {
		this.pnrTransferQuestionDaoJDBC = pnrTransferQuestionDaoJDBC;
	}
    
	public List<PnrTransferQuestion> getQuestionNumber(PnrTransferQuestion pnrTransferQuestion,int firstIndex,int lastIndex){
		
		
		return pnrTransferQuestionDaoJDBC.getQuestionNumber(pnrTransferQuestion,firstIndex,lastIndex);
	}
	
	public PnrTransferQuestion  getQuestionInformationByQuestionNumber(String questionNumber){
		return pnrTransferQuestionDaoJDBC.getQuestionInformationByQuestionNumber(questionNumber);
	}
	public List<PnrTransferQuestion> getQuestionList(PnrTransferQuestion pnrTransferQuestion,int firstIndex,int lastIndex){
		return pnrTransferQuestionDaoJDBC.selQuestionList(pnrTransferQuestion,firstIndex,lastIndex);
	}
	public PnrTransferQuestion getOneQuestion(String numner){
		return pnrTransferQuestionDaoJDBC.getOneQuestion(numner);
	}
	public boolean ChangeQuestionInformation(String questionNumber,String questionState,String questionLevel){
		 return pnrTransferQuestionDaoJDBC.ChangeQuestionInformation(questionNumber, questionState, questionLevel);
		
	}
}
