package com.boco.eoms.partner.assess.AssAutoCollection.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:采集结果
 * </p>
 * <p>
 * Description:采集结果
 * </p>
 * <p>
 * Thu Apr 07 14:49:20 CST 2011
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */
public class AssCollectionResult extends BaseObject {

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
	 * 地市
	 *
	 */
	private java.lang.String area;
   
	public void setArea(java.lang.String area){
		this.area= area;       
	}
   
	public java.lang.String getArea(){
		return this.area;
	}

	/**
	 *
	 * 合作伙伴
	 *
	 */
	private java.lang.String partner;
   
	public void setPartner(java.lang.String partner){
		this.partner= partner;       
	}
   
	public java.lang.String getPartner(){
		return this.partner;
	}

	/**
	 *
	 * 时间
	 *
	 */
	private java.lang.String time;
   
	public void setTime(java.lang.String time){
		this.time= time;       
	}
   
	public java.lang.String getTime(){
		return this.time;
	}

	/**
	 *
	 * 结果
	 *
	 */
	private java.lang.String result;
   
	public void setResult(java.lang.String result){
		this.result= result;       
	}
   
	public java.lang.String getResult(){
		return this.result;
	}

	/**
	 *
	 * 对应采集节点(assKpiConfigId)
	 *
	 */
	private java.lang.String treeNodeId;
   
	public void setTreeNodeId(java.lang.String treeNodeId){
		this.treeNodeId= treeNodeId;       
	}
   
	public java.lang.String getTreeNodeId(){
		return this.treeNodeId;
	}

	/**
	 *
	 * 分类
	 *
	 */
	private java.lang.String type;
   
	public void setType(java.lang.String type){
		this.type= type;       
	}
   
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *
	 * 周期
	 *
	 */
	private java.lang.String cycle;
   
	public void setCycle(java.lang.String cycle){
		this.cycle= cycle;       
	}
   
	public java.lang.String getCycle(){
		return this.cycle; 
	}
	
	/**
	 *
	 * 对应kpiId
	 *
	 */
	private java.lang.String kpiId;

	public java.lang.String getKpiId() {
		return kpiId;
	}

	public void setKpiId(java.lang.String kpiId) {
		this.kpiId = kpiId;
	}
	
	/**
	 *
	 * 创建时间
	 *
	 */
	private java.lang.String creatTime;	

	public java.lang.String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(java.lang.String creatTime) {
		this.creatTime = creatTime;
	}
	
	public boolean equals(Object o) {
		if( o instanceof AssCollectionResult ) {
			AssCollectionResult assCollectionResult=(AssCollectionResult)o;
			if (this.id != null || this.id.equals(assCollectionResult.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


}