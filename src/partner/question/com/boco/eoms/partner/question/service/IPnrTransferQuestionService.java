package com.boco.eoms.partner.question.service;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.question.model.PnrTransferQuestion;


public interface IPnrTransferQuestionService extends CommonGenericService<PnrTransferQuestion> {
	
	/**
	 * 找回问题编码
	 * @param pnrTransferQuestion
	 * @return
	 */
	public List<PnrTransferQuestion> getQuestionNumber(PnrTransferQuestion pnrTransferQuestion,int firstIndex,int lastIndex);
	/**
	 * 根据问题编号,查询问题进度 
	 * @return
	 */
	public PnrTransferQuestion  getQuestionInformationByQuestionNumber(String questionNumber);
	/**
	 * 查询问题集合
	 * @return
	 */
	public List<PnrTransferQuestion> getQuestionList(PnrTransferQuestion pnrTransferQuestion,int firstIndex,int lastIndex);
	/**
	 * 查询问题详细信息
	 * @param numner
	 * @return
	 */
	public PnrTransferQuestion getOneQuestion(String numner);
	
	/**
	 * 管理员更改问题信息
	 * @param questionNumber 问题编号
	 * @param questionState 问题状态
	 * @param questionLevel 问题级别
	 */
	public boolean ChangeQuestionInformation(String questionNumber,String questionState,String questionLevel);
}
