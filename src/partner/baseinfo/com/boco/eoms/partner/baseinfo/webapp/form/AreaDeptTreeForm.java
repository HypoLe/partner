package com.boco.eoms.partner.baseinfo.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:地域部门树
 * </p>
 * <p>
 * Description:地域部门树
 * </p>
 * <p>
 * Fri Feb 06 11:46:50 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() liujinlong
 * @moudle.getVersion() 3.5
 * 
 */
public class AreaDeptTreeForm extends BaseForm implements java.io.Serializable {

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
	 * 节点名
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
	/**
	 * 节点类型：province省，area地市，factory厂商，user人员，instrument仪器仪表，car车辆管理，oil油机
	 */
    private String nodeType;

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	/**
	 * 节点说明
	 */
	private String nodeRemark;
	
	public String getNodeRemark() {
		return nodeRemark;
	}

	public void setNodeRemark(String nodeRemark) {
		this.nodeRemark = nodeRemark;
	}
	/**
	 * objectId
	 */
	private String objectId; 

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	/**
	 * weight
	 */
	private String weight;
	
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	/**
	 * 在地市一级存合作伙伴名称，此字段用于合作伙伴统计报表
	 */
	private String factoryLists;
	
	public String getFactoryLists() {
		return factoryLists;
	}

	public void setFactoryLists(String factoryLists) {
		this.factoryLists = factoryLists;
	}

	/**
	 * 地域ID与地域信息表（taw_system_area）关联
	 * add by wangjunfeng
	 */
	private String areaId;
	
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	/**
	 * 存放二维码系统中的总公司的ID
	 * 便于统计
	 * add by wangjunfeng
	 * 2010-01-21
	 */
	private String interfaceHeadId ;

	public String getInterfaceHeadId() {
		return interfaceHeadId;
	}

	public void setInterfaceHeadId(String interfaceHeadId) {
		this.interfaceHeadId = interfaceHeadId;
	}
	
	/**
	 * 新增合作伙伴后，是否显示人力信息、仪器仪表、车辆、油机
	 * 显示为0 ， 不显示为1
	 * add by wangjunfeng
	 * 2010-4-8
	 */
	private String isShow;

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	

	

}