package com.boco.eoms.partner.serviceArea.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:网格
 * </p>
 * <p>
 * Description:网格
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class GridForm extends BaseForm implements java.io.Serializable {

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
	 * 网格名字
	 *
	 */
	private java.lang.String gridName;
   
	public void setGridName(java.lang.String gridName){
		this.gridName= gridName;       
	}
   
	public java.lang.String getGridName(){
		return this.gridName;
	}

	/**
	 *
	 * 所属地市
	 *
	 */
	private java.lang.String region;
   
	public void setRegion(java.lang.String region){
		this.region= region;       
	}
   
	public java.lang.String getRegion(){
		return this.region;
	}

	/**
	 *
	 * 所属县区
	 *
	 */
	private java.lang.String city;
   
	public void setCity(java.lang.String city){
		this.city= city;       
	}
   
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *
	 * 合作伙伴
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


}