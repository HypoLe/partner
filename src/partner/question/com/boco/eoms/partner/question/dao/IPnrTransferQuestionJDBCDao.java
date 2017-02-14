package com.boco.eoms.partner.question.dao;

import java.util.List;

import com.boco.eoms.partner.question.model.PnrTransferQuestion;


public interface IPnrTransferQuestionJDBCDao {
    
	/**
	 * 根据问题相关内容,查询问题编号
	 * @return
	 */
	public List<PnrTransferQuestion> getQuestionNumber(PnrTransferQuestion pnrTransferQuestion,int firstIndex,int lastIndex);
	/**
	 * 根据问题编号，查询问题进度
	 * @return
	 */
	public PnrTransferQuestion getQuestionInformationByQuestionNumber(String questionNumber);
	/**
	 * 获得问题集合
	 * @return
	 */
	public List<PnrTransferQuestion> selQuestionList(PnrTransferQuestion pnrTransferQuestion,int firstIndex,int lastIndex);
	/**
	 * 获得问题的详细信息
	 * @param numner
	 * @return
	 */
	public PnrTransferQuestion getOneQuestion(String numner);
	/**
	 * 管理员修改问题信息
	 * @param questionNumber
	 * @param questionState
	 * @param questionLevel
	 */
	public boolean ChangeQuestionInformation(String questionNumber,String questionState,String questionLevel);
}
