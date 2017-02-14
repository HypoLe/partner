package com.boco.eoms.partner.assess.AssFlow.model;
/**
 * <p>
 * Title:流程流转配置对应类
 * </p>
 * <p>
 * Description:流程流转配置对应类
 * </p>
 * @author 戴志刚
 * 
 */
public class AssFlow {
	/**
	 * 步骤id
	 */
	private String id;
	/**
	 * 节点执行角色名称
	 */
	private String roleName;
	/**
	 * 执行角色Id
	 */
	private String operater;
	/**
	 * 节点状态描述
	 */
	private String description;
	/**
	 * 驳回节点集合
	 */
	private String rejectCell;
	/**
	 * 下级节点
	 */
	private String gotoCell;
	
	/**
	 * 跳转页面类型
	 */
	private String pageType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRejectCell() {
		return rejectCell;
	}
	public void setRejectCell(String rejectCell) {
		this.rejectCell = rejectCell;
	}
	public String getGotoCell() {
		return gotoCell;
	}
	public void setGotoCell(String gotoCell) {
		this.gotoCell = gotoCell;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	
}
