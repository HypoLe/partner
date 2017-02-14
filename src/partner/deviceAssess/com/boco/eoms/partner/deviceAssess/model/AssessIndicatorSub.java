package com.boco.eoms.partner.deviceAssess.model;

public class AssessIndicatorSub {
	private String id;//主键
	private String indicatorName;//指标名
	private String score;//得分，使用拼串存储，例如A=12;B=8
	private String arithmetic;//得分算法，指标计算公式，例如A+(min(xxx)/self*B)
	private String indicatorid;//Access_indicator表主键
	public String getArithmetic() {
		return arithmetic;
	}
	public void setArithmetic(String arithmetic) {
		this.arithmetic = arithmetic;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndicatorid() {
		return indicatorid;
	}
	public void setIndicatorid(String indicatorid) {
		this.indicatorid = indicatorid;
	}

	public String getIndicatorName() {
		return indicatorName;
	}
	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	
	
}
