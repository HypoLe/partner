package com.boco.eoms.role_tease.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:角色说明
 * </p>
 * <p>
 * Description:角色说明
 * </p>
 * <p>
 * Fri Aug 07 11:13:20 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() fengshaohong
 * @moudle.getVersion() 3.5
 * 
 */
public class TawSystemRoleDescForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
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
	 * 角色编号
	 *
	 */
	private long roleId;
   
	public void setRoleId(long roleId){
		this.roleId= roleId;       
	}
   
	public long getRoleId(){
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
	 * dictCode
	 *
	 */
	private java.lang.String dictCode;
   
	public void setDictCode(java.lang.String dictCode){
		this.dictCode= dictCode;       
	}
   
	public java.lang.String getDictCode(){
		return this.dictCode;
	}

	/**
	 *
	 * 流程编号
	 *
	 */
	private long workflowFlag;
   
	public void setWorkflowFlag(long workflowFlag){
		this.workflowFlag= workflowFlag;       
	}
   
	public long getWorkflowFlag(){
		return this.workflowFlag;
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
	 * 模板编号
	 *
	 */
	private java.lang.String templateCode;
   
	public void setTemplateCode(java.lang.String templateCode){
		this.templateCode= templateCode;       
	}
   
	public java.lang.String getTemplateCode(){
		return this.templateCode;
	}

	/**
	 *
	 * 模板名称
	 *
	 */
	private java.lang.String templateName;
   
	public void setTemplateName(java.lang.String templateName){
		this.templateName= templateName;       
	}
   
	public java.lang.String getTemplateName(){
		return this.templateName;
	}

	/**
	 *
	 * 时间期限
	 *
	 */
	private java.lang.String timeLimit;
   
	public void setTimeLimit(java.lang.String timeLimit){
		this.timeLimit= timeLimit;       
	}
   
	public java.lang.String getTimeLimit(){
		return this.timeLimit;
	}

	/**
	 *
	 * 技能要求
	 *
	 */
	private java.lang.String skill;
   
	public void setSkill(java.lang.String skill){
		this.skill= skill;       
	}
   
	public java.lang.String getSkill(){
		return this.skill;
	}

	/**
	 *
	 * 考核
	 *
	 */
	private java.lang.String kpi;
   
	public void setKpi(java.lang.String kpi){
		this.kpi= kpi;       
	}
   
	public java.lang.String getKpi(){
		return this.kpi;
	}

	/**
	 *
	 * 顺序编号
	 *
	 */
	private long orderFlag;
   
	public void setOrderFlag(long orderFlag){
		this.orderFlag= orderFlag;       
	}
   
	public long getOrderFlag(){
		return this.orderFlag;
	}

	/**
	 *
	 * 备注
	 *
	 */
	private java.lang.String notes;
   
	public void setNotes(java.lang.String notes){
		this.notes= notes;       
	}
   
	public java.lang.String getNotes(){
		return this.notes;
	}


}