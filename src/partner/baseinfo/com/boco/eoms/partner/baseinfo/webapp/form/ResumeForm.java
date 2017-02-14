package com.boco.eoms.partner.baseinfo.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:工作简历
 * </p>
 * <p>
 * Description:工作简历
 * </p>
 * <p>
 * Fri Dec 18 16:50:43 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class ResumeForm extends BaseForm implements java.io.Serializable {

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
	 * 人员名称
	 *
	 */
	private java.lang.String personnelName;
   
	public void setPersonnelName(java.lang.String personnelName){
		this.personnelName= personnelName;       
	}
   
	public java.lang.String getPersonnelName(){
		return this.personnelName;
	}

	/**
	 *
	 * 身份证号
	 *
	 */
	private java.lang.String idCardNo;
   
	public void setIdCardNo(java.lang.String idCardNo){
		this.idCardNo= idCardNo;       
	}
   
	public java.lang.String getIdCardNo(){
		return this.idCardNo;
	}

	/**
	 *
	 * 所在公司
	 *
	 */
	private java.lang.String provider;
   
	public void setProvider(java.lang.String provider){
		this.provider= provider;       
	}
   
	public java.lang.String getProvider(){
		return this.provider;
	}

	/**
	 *
	 * 工作性质
	 *
	 */
	private java.lang.String post;
   
	public void setPost(java.lang.String post){
		this.post= post;       
	}
   
	public java.lang.String getPost(){
		return this.post;
	}

	/**
	 *
	 * 入职时间
	 *
	 */
	private java.lang.String commencementDate;
   
	public void setCommencementDate(java.lang.String commencementDate){
		this.commencementDate= commencementDate;       
	}
   
	public java.lang.String getCommencementDate(){
		return this.commencementDate;
	}

	/**
	 *
	 * 离职时间
	 *
	 */
	private java.lang.String dimissionDate;
   
	public void setDimissionDate(java.lang.String dimissionDate){
		this.dimissionDate= dimissionDate;       
	}
   
	public java.lang.String getDimissionDate(){
		return this.dimissionDate;
	}

	/**
	 *
	 * 在职状态
	 *
	 */
	private java.lang.String state;
   
	public void setState(java.lang.String state){
		this.state= state;       
	}
   
	public java.lang.String getState(){
		return this.state;
	}

	/**
	 *
	 * 备注
	 *
	 */
	private java.lang.String remark;
   
	public void setRemark(java.lang.String remark){
		this.remark= remark;       
	}
   
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *
	 * 新增人
	 *
	 */
	private java.lang.String addUser;
   
	public void setAddUser(java.lang.String addUser){
		this.addUser= addUser;       
	}
   
	public java.lang.String getAddUser(){
		return this.addUser;
	}

	/**
	 *
	 * 新增时间
	 *
	 */
	private java.lang.String addTime;
   
	public void setAddTime(java.lang.String addTime){
		this.addTime= addTime;       
	}
   
	public java.lang.String getAddTime(){
		return this.addTime;
	}

	/**
	 *
	 * 是否删除
	 *
	 */
	private java.lang.String isDel;
   
	public void setIsDel(java.lang.String isDel){
		this.isDel= isDel;       
	}
   
	public java.lang.String getIsDel(){
		return this.isDel;
	}

	/**
	 *
	 * 修改人
	 *
	 */
	private java.lang.String updateUser;
   
	public void setUpdateUser(java.lang.String updateUser){
		this.updateUser= updateUser;       
	}
   
	public java.lang.String getUpdateUser(){
		return this.updateUser;
	}

	/**
	 *
	 * 修改时间
	 *
	 */
	private java.lang.String updateTime;
   
	public void setUpdateTime(java.lang.String updateTime){
		this.updateTime= updateTime;       
	}
   
	public java.lang.String getUpdateTime(){
		return this.updateTime;
	}

	/**
	 *
	 * 删除人
	 *
	 */
	private java.lang.String delUser;
   
	public void setDelUser(java.lang.String delUser){
		this.delUser= delUser;       
	}
   
	public java.lang.String getDelUser(){
		return this.delUser;
	}

	/**
	 *
	 * 删除时间
	 *
	 */
	private java.lang.String delTime;
   
	public void setDelTime(java.lang.String delTime){
		this.delTime= delTime;       
	}
   
	public java.lang.String getDelTime(){
		return this.delTime;
	}


}