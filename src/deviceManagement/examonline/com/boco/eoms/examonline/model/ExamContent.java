package com.boco.eoms.examonline.model;

import java.sql.Timestamp;

/**
 * 考生答题情况表
 * @author lee
 *
 */
public class ExamContent {
  public ExamContent() {
  }

  private String id = "";
  private String userId = "";
  private String subId = "";
  private String issueId = "study";
  private int mark = 0;
  private Timestamp submitTime;
  private String answer = "";
  private int right;
  private int total;
  private String options = "";
  private int tag=0;

  public String getId() {
    return id;
  }

  public String getIssueId() {
    return issueId;
  }

  public Timestamp getSubmitTime() {
    return submitTime;
  }

  public String getAnswer() {
    return answer;
  }

  public String getUserId() {
    return userId;
  }

  public int getMark() {
    return mark;
  }

  public int getRight() {
    return right;
  }

  public void setSubId(String subId) {
    this.subId = subId;
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

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setMark(int mark) {
    this.mark = mark;
  }

  public void setRight(int right) {
    this.right = right;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public void setOptions(String options) {
    this.options = options;
  }

  public void setTag(int tag) {
    this.tag = tag;
  }

  public String getSubId() {
    return subId;
  }

  public int getTotal() {
    return total;
  }

  public String getOptions() {
    return options;
  }

  public int getTag() {
    return tag;
  }

}
