package com.boco.eoms.partner.assess.AssFee.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:光缆线路付费信息
 * </p>
 * <p>
 * Description:光缆线路付费信息
 * </p>
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class FeeDetailForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
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
	 * 皮长
	 *
	 */
	private java.lang.String number;
   
	public void setNumber(java.lang.String number){
		this.number= number;       
	}
   
	public java.lang.String getNumber(){
		return this.number;
	}

	/**
	 *
	 * 单价
	 *
	 */
	private java.lang.String price;
   
	public void setPrice(java.lang.String price){
		this.price= price;       
	}
   
	public java.lang.String getPrice(){
		return this.price;
	}

	/**
	 *
	 * 合计
	 *
	 */
	private java.lang.String total;
   
	public void setTotal(java.lang.String total){
		this.total= total;       
	}
   
	public java.lang.String getTotal(){
		return this.total;
	}

	/**
	 *
	 * 计算结果id
	 *
	 */
	private java.lang.String resultId;
   
	public void setResultId(java.lang.String resultId){
		this.resultId= resultId;       
	}
   
	public java.lang.String getResultId(){
		return this.resultId;
	}

	/**
	 *
	 * 节点id
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
	 * 地市
	 *
	 */
	private java.lang.String areaId;
   
	public void setAreaId(java.lang.String areaId){
		this.areaId= areaId;       
	}
   
	public java.lang.String getAreaId(){
		return this.areaId;
	}

	/**
	 *
	 * 年
	 *
	 */
	private java.lang.String year;
   
	public void setYear(java.lang.String year){
		this.year= year;       
	}
   
	public java.lang.String getYear(){
		return this.year;
	}

	/**
	 *
	 * 类型（标识维护量合计）
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
	 * 删除标志位
	 *
	 */
	private java.lang.String deleted;
   
	public void setDeleted(java.lang.String deleted){
		this.deleted= deleted;       
	}
   
	public java.lang.String getDeleted(){
		return this.deleted;
	}

	/**
	 *
	 * 单位
	 *
	 */
	private java.lang.String unit;
  
	public void setUnit(java.lang.String unit){
		this.unit= unit;       
	}
  
	public java.lang.String getUnit(){
		return this.unit;
	}	


	/**
	 *
	 * 父节点
	 *
	 */
	private java.lang.String parentNodeId;

	public java.lang.String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(java.lang.String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	
	public boolean equals(Object o) {
		return this == o;
	}
}