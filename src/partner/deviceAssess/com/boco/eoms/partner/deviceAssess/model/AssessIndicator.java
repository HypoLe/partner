package com.boco.eoms.partner.deviceAssess.model;

public class AssessIndicator {
	private String id;
	private String indicatorType;//指标分类
	private String indicatorName;//指标名字
	private String configid;//Access_config表主键
	private Float typeScore;//指标分类得分
	private Float nameScor;//指标名字得分
	
	
	public String getConfigid() {
		return configid;
	}
	public void setConfigid(String configid) {
		this.configid = configid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndicatorName() {
		return indicatorName;
	}
	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}
	public String getIndicatorType() {
		return indicatorType;
	}
	public void setIndicatorType(String indicatorType) {
		this.indicatorType = indicatorType;
	}
	public Float getNameScor() {
		return nameScor;
	}
	public void setNameScor(Float nameScor) {
		this.nameScor = nameScor;
	}
	public Float getTypeScore() {
		return typeScore;
	}
	public void setTypeScore(Float typeScore) {
		this.typeScore = typeScore;
	}

	
	
}
