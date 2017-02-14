package com.boco.eoms.eva.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.form.BaseForm;

public class EvaKpiForm extends BaseForm implements java.io.Serializable {

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
	protected Float threshold;

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
	protected Float weight;
	
	/**
	 * 指标的考核开始时间
	 */
	private String evaEndTime;
	
	/**
	 * 指标的考核截止时间
	 */
	private String evaStartTime;
	private String evaSource;
	public String getEvaSource() {
		return evaSource;
	}
	public void setEvaSource(String evaSource) {
		this.evaSource = evaSource;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
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

	public Float getThreshold() {
		return threshold;
	}

	public void setThreshold(Float threshold) {
		this.threshold = threshold;
	}
	
	/**
	 *
	 * 工作任务执行人
	 *
	 */	
	private java.lang.String toOrgUser;

	public java.lang.String getToOrgUser() {
		return toOrgUser;
	}

	public void setToOrgUser(java.lang.String toOrgUser) {
		this.toOrgUser = toOrgUser;
	}
	
	/**
	 *
	 * 算法分类
	 *
	 */	
	private java.lang.String algorithmType;
	
	public java.lang.String getAlgorithmType() {
		return algorithmType;
	}

	public void setAlgorithmType(java.lang.String algorithmType) {
		this.algorithmType = algorithmType;
	}

	/**
	 *
	 * 对应考核指标详细算法(按算法分类)
	 *
	 */	
	private java.lang.String workEvaContentByType;	

	public java.lang.String getWorkEvaContentByType() {
//		0是直接得分
		if("1".equals(this.algorithmType)) {
			String[] scopeValue = this.algorithm.split("-");
			String[] scope = scopeValue[0].split(",");
			StringBuffer retWorkEvaContent = new StringBuffer();
			retWorkEvaContent.append("100%~");
			retWorkEvaContent.append(scope[1]);
			retWorkEvaContent.append("% 得满分 ,");
			retWorkEvaContent.append(scope[1]);
			retWorkEvaContent.append("%~0% 不得分");
			return retWorkEvaContent.toString();			
		} else if("2".equals(this.algorithmType)) {
			String[] scopeValue = this.algorithm.split("-");
			String[] scope = scopeValue[0].split(",");
			String[] value = scopeValue[1].split(",");
			StringBuffer retWorkEvaContent = new StringBuffer();
			for(int k = 0 ; scope.length>k+1 ; k++){
				retWorkEvaContent.append(scope[k]);
				retWorkEvaContent.append("%~");
				retWorkEvaContent.append(scope[k+1]);
				retWorkEvaContent.append("  得总分的");
				retWorkEvaContent.append(value[k]);
				retWorkEvaContent.append("%  ;");
			}
			return retWorkEvaContent.toString();
		}
		return this.algorithm;
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
		threshold = null;
		ruleGroupId = "";
		creator = "";
		createTime = "";
		remark = "";
		deleted = "";
		weight = null;
		algorithm = "";
		evaEndTime ="";
		evaEndTime ="";
		algorithmType ="";
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getEvaEndTime() {
		return evaEndTime;
	}

	public void setEvaEndTime(String evaEndTime) {
		this.evaEndTime = evaEndTime;
	}

	public String getEvaStartTime() {
		return evaStartTime;
	}

	public void setEvaStartTime(String evaStartTime) {
		this.evaStartTime = evaStartTime;
	}

}
