package com.boco.eoms.examonline.qo;

public class ExamTesterQueryVO implements Comparable<ExamTesterQueryVO>{   

	private String name;
	private String companyName;
	private String point;
	private String examTime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getExamTime() {
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	public int compareTo(ExamTesterQueryVO o) {
		return Integer.valueOf(o.getPoint()).compareTo(Integer.valueOf(this.getPoint()));
	}  
}
