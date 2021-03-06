/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Nov 23, 2010 2:02:35 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssTree extends BaseObject {

	/* (non-Javadoc)
	 * @see com.boco.eoms.base.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return this == o;
	}


	/**
	 * 主键
	 */
	private String id;

	/**
	 * 节点Id（按规则生成）
	 */
	private String nodeId;

	/**
	 * 节点名称
	 */
	private String nodeName;

	/**
	 * 节点类型（指标类型、指标、模板等）
	 */
	private String nodeType;

	/**
	 * 节点备注
	 */
	private String nodeRemark;

	/**
	 * 父节点Id
	 */
	private String parentNodeId;

	/**
	 * 是否叶节点
	 */
	private String leaf;

	/**
	 * 树节点对应的对象Id（模板、指标等）
	 */
	private String objectId;
	
	/**
	 * 指标节点对应的权值，模板节点默认为100
	 */
	private Float weight;

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeRemark() {
		return nodeRemark;
	}

	public void setNodeRemark(String nodeRemark) {
		this.nodeRemark = nodeRemark;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	/**
	 * 是否是汇总节点（Y是,N不是）
	 */
	private String isTotal;

	public String getIsTotal() {
		return isTotal;
	}

	public void setIsTotal(String isTotal) {
		this.isTotal = isTotal;
	}	
	/**
	 * 汇总类型（avg算术平均,weightAvg加权平均）
	 */
	private String oneTotal;

	public String getOneTotal() {
		return oneTotal;
	}

	public void setOneTotal(String oneTotal) {
		this.oneTotal = oneTotal;
	}	
}
