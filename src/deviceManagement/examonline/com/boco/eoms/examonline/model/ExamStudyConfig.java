package com.boco.eoms.examonline.model;

public class ExamStudyConfig {
  public ExamStudyConfig() {
  }
  String id = "";
  String userId = "";
  String selValue = "";
  String selName = "";




  public String getSelName() {
    return selName;
  }

  public void setSelValue(String selValue) {
    this.selValue = selValue;
  }



  public void setSelName(String selName) {
    this.selName = selName;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSelValue() {
    return selValue;
  }

  public String getUserId() {
    return userId;
  }

  public String getId() {
    return id;
  }

}
