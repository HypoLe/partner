package com.boco.eoms.partner.baseinfo.model;

import com.boco.eoms.base.model.BaseObject;

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
 * @author benweiwei
 * @version 1.0
 * 
 */
public class Resume extends BaseObject {

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
	private java.util.Date commencementDate;
   
	public void setCommencementDate(java.util.Date commencementDate){
		this.commencementDate= commencementDate;       
	}
   
	public java.util.Date getCommencementDate(){
		return this.commencementDate;
	}

	/**
	 *
	 * 离职时间
	 *
	 */
	private java.util.Date dimissionDate;
   
	public void setDimissionDate(java.util.Date dimissionDate){
		this.dimissionDate= dimissionDate;       
	}
   
	public java.util.Date getDimissionDate(){
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
	private java.util.Date addTime;
   
	public void setAddTime(java.util.Date addTime){
		this.addTime= addTime;       
	}
   
	public java.util.Date getAddTime(){
		return this.addTime;
	}

	/**
	 *
	 * 是否删除
	 *
	 */
	private java.lang.Integer isDel;
   
	public void setIsDel(java.lang.Integer isDel){
		this.isDel= isDel;       
	}
   
	public java.lang.Integer getIsDel(){
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
	private java.util.Date updateTime;
   
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime= updateTime;       
	}
   
	public java.util.Date getUpdateTime(){
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
	private java.util.Date delTime;
   
	public void setDelTime(java.util.Date delTime){
		this.delTime= delTime;       
	}
   
	public java.util.Date getDelTime(){
		return this.delTime;
	}


	public boolean equals(Object o) {
		if( o instanceof Resume ) {
			Resume resume=(Resume)o;
			if (this.id != null || this.id.equals(resume.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}