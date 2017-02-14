package com.boco.eoms.partner.serviceArea.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:基站证件信息
 * </p>
 * <p>
 * Description:基站证件信息
 * </p>
 * <p>
 * Wed Nov 18 11:29:29 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class SitePapersForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键�
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
	 * 基站名称
	 *
	 */
	private java.lang.String siteName;
   
	public void setSiteName(java.lang.String siteName){
		this.siteName= siteName;       
	}
   
	public java.lang.String getSiteName(){
		return this.siteName;
	}

	/**
	 *
	 * 证件号
	 *
	 */
	private java.lang.String papersNo;
   
	public void setPapersNo(java.lang.String papersNo){
		this.papersNo= papersNo;       
	}
   
	public java.lang.String getPapersNo(){
		return this.papersNo;
	}

	/**
	 *
	 * 生效时间
	 *
	 */
	private java.lang.String startTime;
   
	public void setStartTime(java.lang.String startTime){
		this.startTime= startTime;       
	}
   
	public java.lang.String getStartTime(){
		return this.startTime;
	}

	/**
	 *
	 * 失效时间
	 *
	 */
	private java.lang.String endTime;
   
	public void setEndTime(java.lang.String endTime){
		this.endTime= endTime;       
	}
   
	public java.lang.String getEndTime(){
		return this.endTime;
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
	 * 基站站号
	 */
	private java.lang.Integer siteNo;
	
	public java.lang.Integer getSiteNo() {
		return siteNo;
	}

	public void setSiteNo(java.lang.Integer siteNo) {
		this.siteNo = siteNo;
	}
	/**
	 *
	 *  基站ID   
	 *  关联用
	 *
	 */
	private java.lang.String idSite;
  
	public void setIdSite(java.lang.String idSite){
		this.idSite= idSite;       
	}
  
	public java.lang.String getIdSite(){
		return this.idSite;
	}


}