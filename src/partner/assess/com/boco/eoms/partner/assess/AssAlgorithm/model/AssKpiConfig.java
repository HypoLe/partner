package com.boco.eoms.partner.assess.AssAlgorithm.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:指标打分配置
 * </p>
 * <p>
 * Description:指标打分配置
 * </p>
 * <p>
 * Tue Nov 16 09:08:10 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class AssKpiConfig extends BaseObject {
	
	/**
	 * 主键
	 */
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 *
	 * 对应kpi的Id
	 *
	 */
	private java.lang.String kpiId;
   
	public void setKpiId(java.lang.String kpiId){
		this.kpiId= kpiId;       
	}
   
	public java.lang.String getKpiId(){
		return this.kpiId;
	}

	/**
	 *
	 * 对应节点id
	 *
	 */
	private java.lang.String nodeId;
   
	public void setNodeId(java.lang.String nodeId){
		this.nodeId= nodeId;       
	}
   
	public java.lang.String getNodeId(){
		return this.nodeId;
	}

	/**
	 *
	 * 节点类型
	 *
	 */
	private java.lang.String nodeType;
   
	public void setNodeType(java.lang.String nodeType){
		this.nodeType= nodeType;       
	}
   
	public java.lang.String getNodeType(){
		return this.nodeType;
	}

	/**
	 *
	 * 值域最小值
	 *
	 */
	private java.lang.Float minValue;

	public java.lang.Float getMinValue() {
		return minValue;
	}

	public void setMinValue(java.lang.Float minValue) {
		this.minValue = minValue;
	}

	/**
	 *
	 * 值域最大值
	 *
	 */
	private java.lang.Float maxValue;
   
	public void setMaxValue(java.lang.Float maxValue){
		this.maxValue= maxValue;       
	}
   
	public java.lang.Float getMaxValue(){
		return this.maxValue;
	}

	/**
	 *
	 * 值域判断标识
	 *
	 */
	private java.lang.String valueConfig;
   
	public void setValueConfig(java.lang.String valueConfig){
		this.valueConfig= valueConfig;       
	}
   
	public java.lang.String getValueConfig(){
		return this.valueConfig;
	}

	/**
	 *
	 * 对应于所属指标的权重
	 *
	 */
	private java.lang.Float weight;
   
	public void setWeight(java.lang.Float weight){
		this.weight= weight;       
	}
   
	public java.lang.Float getWeight(){
		return this.weight;
	}

	/**
	 *
	 * 备注
	 *
	 */
	private java.lang.String remark;
   
	public void setRemark(java.lang.String remark){
		this.remark= remark;       
	}
   
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *
	 * 设备数量关联值域范围
	 *
	 */
	private java.lang.String numRelation;
   
	public void setNumRelation(java.lang.String numRelation){
		this.numRelation= numRelation;       
	}
   
	public java.lang.String getNumRelation(){
		return this.numRelation;
	}

	/**
	 *
	 * 设备数量判断标识
	 *
	 */
	private java.lang.String numConfig;

	public java.lang.String getNumConfig() {
		return numConfig;
	}

	public void setNumConfig(java.lang.String numConfig) {
		this.numConfig = numConfig;
	}

	/**
	 *
	 * 指标得分的算法
	 *
	 */
	private java.lang.String algorithm;
   
	public void setAlgorithm(java.lang.String algorithm){
		this.algorithm= algorithm;       
	}
   
	public java.lang.String getAlgorithm(){
		return this.algorithm;
	}

	
	/**
	 * 父节点Id
	 */
	private String parentNodeId;
	
	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	
	/**
	 * 是否叶节点
	 */
	private String leaf;
	
	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
	
	/**
	 * 是否采集
	 */
	private String isCollection;	
	
	public String getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(String isCollection) {
		this.isCollection = isCollection;
	}

	/**
	 * 采集类型
	 */
	private String collectionType;	
	
	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * 采集配置
	 */
	private String collectionConfig;	
	
	public String getCollectionConfig() {
		return collectionConfig;
	}

	public void setCollectionConfig(String collectionConfig) {
		this.collectionConfig = collectionConfig;
	}
	
	public boolean equals(Object o) {
		if( o instanceof AssKpiConfig ) {
			AssKpiConfig assKpiConfig=(AssKpiConfig)o;
			if (this.id != null && this.id.equals(assKpiConfig.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public int hashCode() {
		return 0;
	}

}