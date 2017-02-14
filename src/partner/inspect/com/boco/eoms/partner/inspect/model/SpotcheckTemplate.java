package com.boco.eoms.partner.inspect.model;

/** 
 * Description: 巡检抽检模板 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 23, 2012 10:00:23 AM 
 */
public class SpotcheckTemplate {
	private String id;
	private String templateId;        //巡检模板
	private String bigitemName;       //巡检项归类描述
	private String markRule;          //评分规则
	private Float score;              //总分
	private Integer itemNum;
	private Integer selectItemNum;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getBigitemName() {
		return bigitemName;
	}
	public void setBigitemName(String bigitemName) {
		this.bigitemName = bigitemName;
	}
	public String getMarkRule() {
		return markRule;
	}
	public void setMarkRule(String markRule) {
		this.markRule = markRule;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public Integer getSelectItemNum() {
		return selectItemNum;
	}
	public void setSelectItemNum(Integer selectItemNum) {
		this.selectItemNum = selectItemNum;
	}
	
	
}
