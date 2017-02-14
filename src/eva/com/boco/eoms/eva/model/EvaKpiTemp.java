package com.boco.eoms.eva.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:指标(临时)
 * </p>
 * <p>
 * Description:指标（临时）
 * </p>
 * <p>
 * Date:2010-07-10
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */

public class EvaKpiTemp extends BaseObject {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 模板主键
	 */
	private String evaTemplateId;

	/**
	 * 指标名称
	 */
	private String kpiName;

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
	private Float evaScore;
	
	/**
	 * 指标的权重，为了页面显示用，数据库中并不存在此字段
	 */
	private Float weight;
	
	/**
	 * 指标的考核开始时间
	 */
	private String evaEndTime;
	
	/**
	 * 指标的考核截止时间
	 */
	private String evaStartTime;
	/**
	 *
	 * 考核来源
	 *
	 */		
	private String evaSource;

	/**
	 *
	 * 工作任务执行人
	 *
	 */	
	private java.lang.String toOrgUser;
	
	/**
	 *
	 * 工作任务执行人名称
	 *
	 */	
	private java.lang.String toOrgUserName;	
	
	/**
	 *
	 * 算法分类
	 *
	 */	
	private java.lang.String algorithmType;	
	
	/**
	 *
	 * 协议的工作任务id
	 *
	 */	
	private java.lang.String agreementWorkId;	
	
	/**
	 *
	 * 对应考核指标详细算法(按算法分类)
	 *
	 */	
	private java.lang.String evaContentByType;	

	public java.lang.String getEvaContentByType() {
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
				retWorkEvaContent.append("%  得总分的");
				retWorkEvaContent.append(value[k]);
				retWorkEvaContent.append("%  ;");
			}
			return retWorkEvaContent.toString();
		}
		return this.algorithm;
	}	
	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getEvaScore() {
		return evaScore;
	}

	public void setEvaScore(Float evaScore) {
		this.evaScore = evaScore;
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
		return false;
	}

	public String toString() {
		return null;
	}

	public int hashCode() {
		return id.hashCode();
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getEvaStartTime() {
		return evaStartTime;
	}

	public void setEvaStartTime(String evaStartTime) {
		this.evaStartTime = evaStartTime;
	}

	public String getEvaEndTime() {
		return evaEndTime;
	}
	/**
	 *
	 * 指标考核开始时间（String）
	 *
	 */
	private java.lang.String evaStartTimeStr;		

	public java.lang.String getEvaStartTimeStr() {
		int s = this.evaStartTime.indexOf(" ");
		evaStartTimeStr = this.evaStartTime.substring(0, s);
		return evaStartTimeStr;
	}	
	
	public void setEvaEndTime(String evaEndTime) {
		this.evaEndTime = evaEndTime;
	}

	public String getEvaSource() {
		return evaSource;
	}
	/**
	 *
	 * 指标考核结束时间（String）  
	 *
	 */
	private java.lang.String evaEndTimeStr;		

	public java.lang.String getEvaEndTimeStr() {
		int s = this.evaEndTime.indexOf(" ");
		evaEndTimeStr = this.evaEndTime.substring(0, s);
		return evaEndTimeStr;
	}
	public void setEvaSource(String evaSource) {
		this.evaSource = evaSource;
	}

	public java.lang.String getToOrgUser() {
		return toOrgUser;
	}

	public void setToOrgUser(java.lang.String toOrgUser) {
		this.toOrgUser = toOrgUser;
	}

	public java.lang.String getAlgorithmType() {
		return algorithmType;
	}

	public void setAlgorithmType(java.lang.String algorithmType) {
		this.algorithmType = algorithmType;
	}
	public String getEvaTemplateId() {
		return evaTemplateId;
	}
	public void setEvaTemplateId(String evaTemplateId) {
		this.evaTemplateId = evaTemplateId;
	}
	public java.lang.String getToOrgUserName() {
		return toOrgUserName;
	}
	public void setToOrgUserName(java.lang.String toOrgUserName) {
		this.toOrgUserName = toOrgUserName;
	}
	public java.lang.String getAgreementWorkId() {
		return agreementWorkId;
	}
	public void setAgreementWorkId(java.lang.String agreementWorkId) {
		this.agreementWorkId = agreementWorkId;
	}
}
