package com.boco.eoms.partner.assess.AssAlgorithm.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:指标打分实例
 * </p>
 * <p>
 * Description:指标打分实例
 * </p>
 * <p>
 * Tue Nov 16 09:08:10 CST 2010
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssSelectedInstance extends BaseObject {
	
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
	 * 对应report的Id
	 *
	 */
	private java.lang.String reportId;
   
	public void setReportId(java.lang.String reportId){
		this.reportId= reportId;       
	}
   
	public java.lang.String getReportId(){
		return this.reportId;
	}
	/**
	 *
	 * 对应config的Id
	 *
	 */
	private java.lang.String configId;
  
	public void setConfigId(java.lang.String configId){
		this.configId= configId;       
	}
  
	public java.lang.String getConfigId(){
		return this.configId;
	}
	
	/**
	 *
	 * 对应config的节点id
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
	 * 对应于所属指标的权重
	 *
	 */
	private float weight;
   
	public void setWeight(float weight){
		this.weight= weight;       
	}
   
	public float getWeight(){
		return this.weight;
	}
	
	/**
	 *
	 * 对应task的节点id
	 *
	 */
	private java.lang.String taskId;
	
	public java.lang.String getTaskId() {
		return taskId;
	}

	public void setTaskId(java.lang.String taskId) {
		this.taskId = taskId;
	}
	
	/**
	 *
	 * 对应partner的节点id
	 *
	 */
	private java.lang.String partnerId;

	public java.lang.String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(java.lang.String partnerId) {
		this.partnerId = partnerId;
	}
	
	/**
	 *
	 * 对应time
	 *
	 */
	private java.lang.String time;

	public java.lang.String getTime() {
		return time;
	}

	public void setTime(java.lang.String time) {
		this.time = time;
	}
	/**
	 *
	 * 对应areaId
	 *
	 */
	private java.lang.String areaId;
	
	public java.lang.String getAreaId() {
		return areaId;
	}

	public void setAreaId(java.lang.String areaId) {
		this.areaId = areaId;
	}

	public boolean equals(Object o) {
		return this == o;
	}

	public int hashCode() {
		return 0;
	}


}