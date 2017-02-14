package com.boco.eoms.partner.eva.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;


import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:考核树，用来维持树形结构
 * </p>
 * <p>
 * Description:考核树，用来维持树形结构
 * </p>
 * <p>
 * Date:2008-11-20 上午11:02:55
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */

public class PnrEvaTree extends BaseObject {

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
	private float weight;	
	
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

	
	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
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

	public boolean equals(Object o) {
		return false;
	}

	public String toString() {
		return null;
	}

	public int hashCode() {
		return 0;
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
