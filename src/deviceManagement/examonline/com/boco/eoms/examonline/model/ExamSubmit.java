package com.boco.eoms.examonline.model;
import java.sql.Timestamp;

public class ExamSubmit {
  public ExamSubmit() {
  }

  String id;
  String issueId;
  String userId;
  Timestamp submitTime;
//  private Integer markFlag; //0或者为空 无需阅卷 ; 1待阅卷 ; 2阅卷完成
  
  public String getId() {
    return id;
  }

  public String getIssueId() {
    return issueId;
  }

  public Timestamp getSubmitTime() {
    return submitTime;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setIssueId(String issueId) {
    this.issueId = issueId;
  }

  public void setSubmitTime(Timestamp submitTime) {
    this.submitTime = submitTime;
  }

  public String getUserId() {
    return userId;
  }
//  public Integer getMarkFlag() {
//		return markFlag;
//	}
//
//	public void setMarkFlag(Integer markFlag) {
//		this.markFlag = markFlag;
//	}

}
