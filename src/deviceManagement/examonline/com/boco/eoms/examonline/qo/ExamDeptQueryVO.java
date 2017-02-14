package com.boco.eoms.examonline.qo;

/**
 * 按部门查询考试成绩VO
 *
 */
public class ExamDeptQueryVO {
	private int testerCountAll; //应该参考人数
	private int testerCount;//实际参考人数
	private int notesterCount; //缺考人数
	private String averagePoint; //平均分
	private String companyName;  //单位
	private String issueId;      //
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public int getTesterCountAll() {
		return testerCountAll;
	}
	public void setTesterCountAll(int testerCountAll) {
		this.testerCountAll = testerCountAll;
	}
	public int getTesterCount() {
		return testerCount;
	}
	public void setTesterCount(int testerCount) {
		this.testerCount = testerCount;
	}
	public int getNotesterCount() {
		return notesterCount;
	}
	public void setNotesterCount(int notesterCount) {
		this.notesterCount = notesterCount;
	}
	public String getAveragePoint() {
		return averagePoint;
	}
	public void setAveragePoint(String averagePoint) {
		this.averagePoint = averagePoint;
	}
}
