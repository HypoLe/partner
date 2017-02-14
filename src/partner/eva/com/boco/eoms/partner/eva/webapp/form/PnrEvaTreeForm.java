package com.boco.eoms.partner.eva.webapp.form;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.webapp.form.BaseForm;

public class PnrEvaTreeForm extends BaseForm implements java.io.Serializable {

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
	
	/**
	 * 考核层面省，地市，县
	 */
	private String orgType;
	
	/**
	 * 是否锁定：开放，锁定,只对权重控制
	 */
	private String isLock;	
	
	/**
	 * 节点状态：待审核，有效，激活
	 */
	private String state;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	
	/**
	 * 记录节点制定者的地域信息
	 */
	private String creatArea;
	
	/**
	 * 创建人
	 */
	private String createUser;
	

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

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreatArea() {
		return creatArea;
	}

	public void setCreatArea(String creatArea) {
		this.creatArea = creatArea;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

}
