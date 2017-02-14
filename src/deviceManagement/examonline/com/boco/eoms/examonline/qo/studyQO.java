package com.boco.eoms.examonline.qo;

public class studyQO {

  public studyQO() {
  }
  int total = 0;
  int mark = 0;
  int right = 0;
  int part = 0;
  double rate = 0;
  String userId = "";
  String userName = "";
  String issueId = "";


  public int getPart() {
    return part;
  }

  public String getUserId() {
    return userId;
  }

  public int getMark() {
    return mark;
  }

  public int getTotal() {
    return total;
  }

  public int getRight() {
    return right;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPart(int part) {
    this.part = part;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setMark(int mark) {
    this.mark = mark;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public void setRight(int right) {
    this.right = right;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  public void setIssueId(String issueId) {
    this.issueId = issueId;
  }

  public String getUserName() {
    return userName;
  }

  public double getRate() {
    return rate;
  }

  public String getIssueId() {
    return issueId;
  }

}
