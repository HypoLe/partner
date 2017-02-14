package com.boco.eoms.partner.assess.AssTree.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title: 指标
 * </p>
 * <p>
 * Description:指标
 * </p>
 * <p>
 * Date:Nov 23, 2010 2:02:35 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */

public class AssKpi extends BaseObject {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 指标名称
	 */
	private String kpiName;
	
	/**
	 * 指标类型
	 */
	private String kpiType;

	/**
	 * 周期
	 */
	private String cycle;

	/**
	 * 阀值
	 */
	private Float threshold;

	/**
	 * 规则组ID
	 */
	private String ruleGroupId;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 删除标志
	 */ 
	private String deleted;
	
	/**
	 * 考核算法
	 */
	private String algorithm;

	/**
	 * 指标的考核分数，为了页面显示用，数据库中并不存在此字段
	 */
	private Float assScore;
	
	/**
	 * 指标的权重，为了页面显示用，数据库中并不存在此字段
	 */
	private Float weight;
	
	/**
	 * 单项汇总类型
	 */
	private String oneTotal;
	
	/**
	 * 是否采集
	 */
	private String isCollection;	

	/**
	 * 是否引用
	 */
	private String isQuote;
	
	public String getIsQuote() {
		return isQuote;
	}

	public void setIsQuote(String isQuote) {
		this.isQuote = isQuote;
	}

	public Float getWeight() {
		return weight;
	}

	public String getOneTotal() {
		return oneTotal;
	}

	public void setOneTotal(String oneTotal) {
		this.oneTotal = oneTotal;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getAssScore() {
		return assScore;
	}

	public void setAssScore(Float assScore) {
		this.assScore = assScore;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRuleGroupId() {
		return ruleGroupId;
	}

	public void setRuleGroupId(String ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}

	public Float getThreshold() {
		return threshold;
	}

	public void setThreshold(Float threshold) {
		this.threshold = threshold;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public boolean equals(Object o) {
		return this == o;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getKpiType() {
		return kpiType;
	}

	public void setKpiType(String kpiType) {
		this.kpiType = kpiType;
	}

	public String getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(String isCollection) {
		this.isCollection = isCollection;
	}

}
