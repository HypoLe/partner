package com.boco.eoms.partner.eva.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.form.BaseForm;

public class PnrEvaKpiForm extends BaseForm implements java.io.Serializable {

	/**
	 * 主键
	 */
	protected String id;

	/**
	 * 指标名称
	 */
	protected String kpiName;

	/**
	 * 指标对应节点ID
	 */
	protected String nodeId;

	/**
	 * 周期
	 */
	protected String cycle;

	/**
	 * 阀值
	 */
	protected float threshold;

	/**
	 * 规则组ID
	 */
	protected String ruleGroupId;

	/**
	 * 创建人
	 */
	protected String creator;

	/**
	 * 创建时间
	 */
	protected String createTime;

	/**
	 * 备注
	 */
	protected String remark;

	/**
	 * 删除标志
	 */
	protected String deleted;
	
	/**
	 * 考核算法
	 */
	protected String algorithm;

	/**
	 * 指标的权重，为了页面显示用，数据库中并不存在此字段
	 */
	protected float weight;
	
	/**
	 * 地域信息，为设置tree表地域信息,数据库中并不存在此字段
	 */
	private String area;
	
	/**
	 *标准值 
	 */
	private float defaultValue ;
	
	public float getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(float defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
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

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
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

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
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

	public float getThreshold() {
		return threshold;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	/**
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		id = "";
		kpiName = "";
		nodeId = "";
		cycle = "";
		threshold = 0;
		ruleGroupId = "";
		creator = "";
		createTime = "";
		remark = "";
		deleted = "";
		weight = 0;
		algorithm = "";
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

}
