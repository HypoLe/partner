package com.boco.eoms.examonline.model;

import java.sql.Timestamp;

/** 
 * Description: 考试结果
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Feb 21, 2012 3:46:45 PM 
 */
public class ExamResult {
	private String id;
	private String issueId;
	private String title = "";
	private Timestamp startTime;
	private Timestamp endTime;
	private String tester;   //身份证号（代维上岗） 或者用户名（移动）
	private Integer totalCount; //总题数
	private Integer score;
	private Integer assignType; //0 待指定 1合格 2不合格  
	private Integer examType; // 1移动 2代维上岗
	
	private String testerName; //考生中文名
	private String userId;  //合格考试系统用户号
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public String getTester() {
		return tester;
	}
	public void setTester(String tester) {
		this.tester = tester;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getAssignType() {
		return assignType;
	}
	public void setAssignType(Integer assignType) {
		this.assignType = assignType;
	}
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public Integer getExamType() {
		return examType;
	}
	public void setExamType(Integer examType) {
		this.examType = examType;
	}
	public String getTesterName() {
		return testerName;
	}
	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
