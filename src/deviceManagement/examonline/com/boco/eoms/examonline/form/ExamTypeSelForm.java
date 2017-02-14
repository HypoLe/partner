package com.boco.eoms.examonline.form;

import org.apache.struts.action.ActionForm;

/** 
 * Description:  
 * Copyright:   Copyright (c)2011 
 * Company:     BOCO 
 * @author:     LEE 
 * @version:    1.0 
 * Create at:   Aug 18, 2011 10:01:48 AM 
 */
public class ExamTypeSelForm extends ActionForm{
	private String id;
	private String specialty;
	private Integer manufacturer;
	private Integer difficulty;
	private Integer radioCount;
	private Integer multipleCount;
	private Integer judgeCount;
	private Integer qaCount;
	private Integer essayCount;
	private String issueId;
	private Integer radioScore;
	private Integer multipleScore;
	private Integer judgeScore;
	private Integer qaScore;
	private Integer essayScore;
	private Integer storeRadioCount;
	private Integer storeMultipleCount;
    private Integer storeJudgeCount;
    private Integer storeQaCount;
    private Integer storeEssayCount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public Integer getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(Integer manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	public Integer getRadioCount() {
		return radioCount;
	}
	public void setRadioCount(Integer radioCount) {
		this.radioCount = radioCount;
	}
	public Integer getMultipleCount() {
		return multipleCount;
	}
	public void setMultipleCount(Integer multipleCount) {
		this.multipleCount = multipleCount;
	}
	public Integer getJudgeCount() {
		return judgeCount;
	}
	public void setJudgeCount(Integer judgeCount) {
		this.judgeCount = judgeCount;
	}
	public Integer getQaCount() {
		return qaCount;
	}
	public void setQaCount(Integer qaCount) {
		this.qaCount = qaCount;
	}
	public Integer getEssayCount() {
		return essayCount;
	}
	public void setEssayCount(Integer essayCount) {
		this.essayCount = essayCount;
	}
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public Integer getRadioScore() {
		return radioScore;
	}
	public void setRadioScore(Integer radioScore) {
		this.radioScore = radioScore;
	}
	public Integer getMultipleScore() {
		return multipleScore;
	}
	public void setMultipleScore(Integer multipleScore) {
		this.multipleScore = multipleScore;
	}
	public Integer getJudgeScore() {
		return judgeScore;
	}
	public void setJudgeScore(Integer judgeScore) {
		this.judgeScore = judgeScore;
	}
	public Integer getQaScore() {
		return qaScore;
	}
	public void setQaScore(Integer qaScore) {
		this.qaScore = qaScore;
	}
	public Integer getEssayScore() {
		return essayScore;
	}
	public void setEssayScore(Integer essayScore) {
		this.essayScore = essayScore;
	}
	public Integer getStoreRadioCount() {
		return storeRadioCount;
	}
	public void setStoreRadioCount(Integer storeRadioCount) {
		this.storeRadioCount = storeRadioCount;
	}
	public Integer getStoreMultipleCount() {
		return storeMultipleCount;
	}
	public void setStoreMultipleCount(Integer storeMultipleCount) {
		this.storeMultipleCount = storeMultipleCount;
	}
	public Integer getStoreJudgeCount() {
		return storeJudgeCount;
	}
	public void setStoreJudgeCount(Integer storeJudgeCount) {
		this.storeJudgeCount = storeJudgeCount;
	}
	public Integer getStoreQaCount() {
		return storeQaCount;
	}
	public void setStoreQaCount(Integer storeQaCount) {
		this.storeQaCount = storeQaCount;
	}
	public Integer getStoreEssayCount() {
		return storeEssayCount;
	}
	public void setStoreEssayCount(Integer storeEssayCount) {
		this.storeEssayCount = storeEssayCount;
	}
}
