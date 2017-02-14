package com.boco.eoms.examonline.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class ExamConfig {
	private String companyId="";
	private String deptId="";
	private String testers="";
	private int testerCount=0;
  private String issueId = "";
  private String title = "";
  private Timestamp startTime;
  private Timestamp endTime;
  private String issueUser = "";
  private String typeSel = "";
  private String tester = "";
  private boolean assignment;
  private Timestamp issueStarttime;
  private Timestamp issueEndtime;
  private Integer markFlag; //0或者为空 无需阅卷 ; 1待阅卷 ; 2阅卷完成
  private Integer mark; //该套试卷总分
  private Integer examType; // 1移动 2代维上岗
 
  //------------与数据库无关的属性--------------------
  private int testedCount;  //已经参考人数
  private int notestedCount;  //未参考人数
  private String typesel_fk;



public String getTypesel_fk() {
	return typesel_fk;
}

public void setTypesel_fk(String typesel_fk) {
	this.typesel_fk = typesel_fk;
}

public Timestamp getEndTime() {
    return endTime;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public String getIssueId() {
    return issueId;
  }

  public String getIssueUser() {
    return issueUser;
  }

  public void setTester(String tester) {
    this.tester = tester;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public void setIssueId(String issueId) {
    this.issueId = issueId;
  }

  public void setIssueUser(String issueUser) {
    this.issueUser = issueUser;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTypeSel(String typeSel) {
    this.typeSel = typeSel;
  }

  public void setIssueEndtime(Timestamp issueEndtime) {
    this.issueEndtime = issueEndtime;
  }

  public void setIssueStarttime(Timestamp issueStarttime) {
    this.issueStarttime = issueStarttime;
  }

  public String getTester() {
    return tester;
  }

  public String getTitle() {
    return title;
  }

  public String getTypeSel() {
    return typeSel;
  }

  public Timestamp getIssueEndtime() {
    return issueEndtime;
  }

  public Timestamp getIssueStarttime() {
    return issueStarttime;
  }

public String getCompanyId() {
	return companyId;
}

public void setCompanyId(String companyId) {
	this.companyId = companyId;
}

public String getDeptId() {
	return deptId;
}

public void setDeptId(String deptId) {
	this.deptId = deptId;
}

public int getTesterCount() {
	return testerCount;
}

public void setTesterCount(int testerCount) {
	this.testerCount = testerCount;
}

public boolean isAssignment() {
	return assignment;
}

public void setAssignment(boolean assignment) {
	this.assignment = assignment;
}

public String getTesters() {
	return testers;
}

public void setTesters(String testers) {
	this.testers = testers;
}

public int getTestedCount() {
	return testedCount;
}

public void setTestedCount(int testedCount) {
	this.testedCount = testedCount;
}

public int getNotestedCount() {
	return notestedCount;
}

public void setNotestedCount(int notestedCount) {
	this.notestedCount = notestedCount;
}

public Integer getMarkFlag() {
	return markFlag;
}

public void setMarkFlag(Integer markFlag) {
	this.markFlag = markFlag;
}

public Integer getMark() {
	return mark;
}

public void setMark(Integer mark) {
	this.mark = mark;
}

public Integer getExamType() {
	return examType;
}

public void setExamType(Integer examType) {
	this.examType = examType;
}



}
