package com.boco.eoms.partner.assess.AssFee.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:考核线路信息树
 * </p>
 * <p>
 * Description:考核线路信息树
 * </p>
 * <p>
 * Tue Nov 23 08:36:47 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class FeeTreeForm extends BaseForm implements java.io.Serializable {

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
	 * 节点名称
	 *
	 */
	private java.lang.String nodeName;
   
	public void setNodeName(java.lang.String nodeName){
		this.nodeName= nodeName;       
	}
   
	public java.lang.String getNodeName(){
		return this.nodeName;
	}

	/**
	 *
	 * 节点备注
	 *
	 */
	private java.lang.String nodeRemark;
   
	public void setNodeRemark(java.lang.String nodeRemark){
		this.nodeRemark= nodeRemark;       
	}
   
	public java.lang.String getNodeRemark(){
		return this.nodeRemark;
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
	 * 节点Id（按规则生成）
	 */
	private String nodeId;
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
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
	
	public boolean equals(Object o) {
		return this == o;
	}
}