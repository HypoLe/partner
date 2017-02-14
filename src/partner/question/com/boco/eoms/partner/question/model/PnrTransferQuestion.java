package com.boco.eoms.partner.question.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 问题反馈
 * @author wangyue
 *
 */
public class PnrTransferQuestion implements Serializable{
	
	private static final long serialVersionUID = 2992558806285652792L;
	
	/**主键id*/
	private String id;
	/**提出问题的人*/
	private String questioner;
	/**问题联系人*/
	private String linkman;
	/**联系电话*/
	private String linkmanPhone;
	/**问题详情*/
	private String questionContent;
	/**提出问题时间*/
	private Date raiseTime;
	/**问题状态*/
	private String questionState;
	/**问题等级*/
	private String questionLevel;
	/**问题编号*/
	private String questionNumber;
	/**不存入数据库，在其他地方用*/
	private String useTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestioner() {
		return questioner;
	}
	public void setQuestioner(String questioner) {
		this.questioner = questioner;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkmanPhone() {
		return linkmanPhone;
	}
	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	
	public Date getRaiseTime() {
		return raiseTime;
	}
	public void setRaiseTime(Date raiseTime) {
		this.raiseTime = raiseTime;
	}
	public String getQuestionState() {
		return questionState;
	}
	public void setQuestionState(String questionState) {
		this.questionState = questionState;
	}
	public String getQuestionLevel() {
		return questionLevel;
	}
	public void setQuestionLevel(String questionLevel) {
		this.questionLevel = questionLevel;
	}
	public String getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	
	
	
	
}
