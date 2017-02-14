package com.boco.eoms.partner.question.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 问题反馈
 * @author wangyue
 *
 */
public class PnrTransferQuestionState implements Serializable{
	
	private static final long serialVersionUID = 2992558806285652792L;
	
	/**主键id*/
	private String id;
	/**问题状态改变时的时间*/
	private Date changeTime;
	/**问题状态*/
	private String quesitonState;
	/**问题编号*/
	private String questionNumber;
	/**不存入数据库,用于其他*/
	private String useTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public String getQuesitonState() {
		return quesitonState;
	}
	public void setQuesitonState(String quesitonState) {
		this.quesitonState = quesitonState;
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
