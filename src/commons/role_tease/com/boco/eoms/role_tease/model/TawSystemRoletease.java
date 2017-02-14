package com.boco.eoms.role_tease.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:角色梳理
 * </p>
 * <p>
 * Description:角色梳理
 * </p>
 * <p>
 * Tue Aug 04 11:38:53 CST 2009
 * </p>
 * 
 * @author fengshaohong
 * @version 3.5
 * 
 */
public class TawSystemRoletease extends BaseObject {

	/**
	 * 锟斤拷锟�
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
	 * 流程id
	 *
	 */
	private java.lang.String workflowId;
   
	public void setWorkflowId(java.lang.String workflowId){
		this.workflowId= workflowId;       
	}
   
	public java.lang.String getWorkflowId(){
		return this.workflowId;
	}

	/**
	 *
	 * 流程名称
	 *
	 */
	private java.lang.String workflowName;
   
	public void setWorkflowName(java.lang.String workflowName){
		this.workflowName= workflowName;       
	}
   
	public java.lang.String getWorkflowName(){
		return this.workflowName;
	}

	/**
	 *
	 * 角色id
	 *
	 */
	private java.lang.String roleId;
   
	public void setRoleId(java.lang.String roleId){
		this.roleId= roleId;       
	}
   
	public java.lang.String getRoleId(){
		return this.roleId;
	}

	/**
	 *
	 * 角色名称
	 *
	 */
	private java.lang.String roleName;
   
	public void setRoleName(java.lang.String roleName){
		this.roleName= roleName;       
	}
   
	public java.lang.String getRoleName(){
		return this.roleName;
	}

	/**
	 *
	 * 子角色id
	 *
	 */
	private java.lang.String subRole_id;
   
	public void setSubRole_id(java.lang.String subRole_id){
		this.subRole_id= subRole_id;       
	}
   
	public java.lang.String getSubRole_id(){
		return this.subRole_id;
	}

	/**
	 *
	 * 子角色名称
	 *
	 */
	private java.lang.String subRoleName;
   
	public void setSubRoleName(java.lang.String subRoleName){
		this.subRoleName= subRoleName;       
	}
   
	public java.lang.String getSubRoleName(){
		return this.subRoleName;
	}

	/**
	 *
	 * 工作模板
	 *
	 */
	private java.lang.String workTemple;
   
	public void setWorkTemple(java.lang.String workTemple){
		this.workTemple= workTemple;       
	}
   
	public java.lang.String getWorkTemple(){
		return this.workTemple;
	}

	/**
	 *
	 * 时限要求
	 *
	 */
	private java.lang.String timeLimite;
   
	public void setTimeLimite(java.lang.String timeLimite){
		this.timeLimite= timeLimite;       
	}
   
	public java.lang.String getTimeLimite(){
		return this.timeLimite;
	}

	/**
	 *
	 * 技术要求
	 *
	 */
	private java.lang.String skillRequire;
   
	public void setSkillRequire(java.lang.String skillRequire){
		this.skillRequire= skillRequire;       
	}
   
	public java.lang.String getSkillRequire(){
		return this.skillRequire;
	}

	/**
	 *
	 * 考核指标
	 *
	 */
	private java.lang.String checkLine;
   
	public void setCheckLine(java.lang.String checkLine){
		this.checkLine= checkLine;       
	}
   
	public java.lang.String getCheckLine(){
		return this.checkLine;
	}

	/**
	 *
	 * 部门id
	 *
	 */
	private java.lang.String deptId;
   
	public void setDeptId(java.lang.String deptId){
		this.deptId= deptId;       
	}
   
	public java.lang.String getDeptId(){
		return this.deptId;
	}

	/**
	 *
	 * 部门名称
	 *
	 */
	private java.lang.String deptName;
   
	public void setDeptName(java.lang.String deptName){
		this.deptName= deptName;       
	}
   
	public java.lang.String getDeptName(){
		return this.deptName;
	}

	/**
	 *
	 * 用户id
	 *
	 */
	private java.lang.String userId;
   
	public void setUserId(java.lang.String userId){
		this.userId= userId;       
	}
   
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *
	 * 用户名称
	 *
	 */
	private java.lang.String userName;
   
	public void setUserName(java.lang.String userName){
		this.userName= userName;       
	}
   
	public java.lang.String getUserName(){
		return this.userName;
	}


	public boolean equals(Object o) {
		if( o instanceof TawSystemRoletease ) {
			TawSystemRoletease tawSystemRoletease=(TawSystemRoletease)o;
			if (this.id != null || this.id.equals(tawSystemRoletease.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}