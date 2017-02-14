package com.boco.eoms.partner.assess.AssTree.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.webapp.form.BaseForm;

public class AssTreeForm extends BaseForm implements java.io.Serializable {

	/**
	 * 主键
	 */
	protected String id;

	/**
	 * 节点Id（按规则生成）
	 */
	protected String nodeId;

	/**
	 * 节点名称
	 */
	protected String nodeName;

	/**
	 * 节点类型（指标类型、指标、模板等）
	 */
	protected String nodeType;

	/**
	 * 节点备注
	 */
	protected String nodeRemark;

	/**
	 * 父节点Id
	 */
	protected String parentNodeId;

	/**
	 * 是否叶节点
	 */
	protected String leaf;

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

	/**
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// reset any boolean data types to false

	}
	/**
	 * 是否是汇总节点（Y是,N不是）
	 */
	protected String isTotal;

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
	
	public boolean equals(Object o) {
		return this == o;
	}

}
