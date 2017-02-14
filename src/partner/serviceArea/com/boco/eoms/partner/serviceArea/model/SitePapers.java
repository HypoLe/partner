package com.boco.eoms.partner.serviceArea.model;

import com.boco.eoms.base.model.BaseObject;

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
 * @author benweiwei
 * @version 1.0
 * 
 */
public class SitePapers extends BaseObject {

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
	private java.util.Date startTime;
   
	public void setStartTime(java.util.Date startTime){
		this.startTime= startTime;       
	}
   
	public java.util.Date getStartTime(){
		return this.startTime;
	}

	/**
	 *
	 * 失效时间
	 *
	 */
	private java.util.Date endTime;
   
	public void setEndTime(java.util.Date endTime){
		this.endTime= endTime;       
	}
   
	public java.util.Date getEndTime(){
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
	private java.util.Date addTime;
   
	public void setAddTime(java.util.Date addTime){
		this.addTime= addTime;       
	}
   
	public java.util.Date getAddTime(){
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
	private java.util.Date delTime;
   
	public void setDelTime(java.util.Date delTime){
		this.delTime= delTime;       
	}
   
	public java.util.Date getDelTime(){
		return this.delTime;
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
	

	public boolean equals(Object o) {
		if( o instanceof SitePapers ) {
			SitePapers sitePapers=(SitePapers)o;
			if (this.id != null || this.id.equals(sitePapers.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}