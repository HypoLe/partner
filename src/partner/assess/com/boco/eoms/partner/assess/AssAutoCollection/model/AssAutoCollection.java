package com.boco.eoms.partner.assess.AssAutoCollection.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:kpi自动采集
 * </p>
 * <p>
 * Description:kpi自动采集
 * </p>
 * <p>
 * Tue Mar 29 17:39:54 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class AssAutoCollection extends BaseObject {
	
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
		if( o instanceof AssAutoCollection ) {
			AssAutoCollection assAutoCollection=(AssAutoCollection)o;
			if (this.id != null || this.id.equals(assAutoCollection.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}