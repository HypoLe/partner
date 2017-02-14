package com.boco.eoms.partner.baseinfo.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:合作伙伴资质
 * </p>
 * <p>
 * Description:合作伙伴资质
 * </p>
 * <p>
 * Fri Dec 18 14:11:52 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class Aptitude extends BaseObject {

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
	 * 合作伙伴名称
	 *
	 */
	private java.lang.String providerName;
   
	public void setProviderName(java.lang.String providerName){
		this.providerName= providerName;       
	}
   
	public java.lang.String getProviderName(){
		return this.providerName;
	}

	/**
	 *
	 * 服务资质名称
	 *
	 */
	private java.lang.String aptitudeName;
   
	public void setAptitudeName(java.lang.String aptitudeName){
		this.aptitudeName= aptitudeName;       
	}
   
	public java.lang.String getAptitudeName(){
		return this.aptitudeName;
	}

	/**
	 *
	 * 资质等级（甲级、乙级、丙级）
	 *
	 */
	private java.lang.String aptitudeLevle;
   
	public void setAptitudeLevle(java.lang.String aptitudeLevle){
		this.aptitudeLevle= aptitudeLevle;       
	}
   
	public java.lang.String getAptitudeLevle(){
		return this.aptitudeLevle;
	}

	/**
	 *
	 * 资质生效时间
	 *
	 */
	private java.util.Date aptitudeStartTime;
   
	public void setAptitudeStartTime(java.util.Date aptitudeStartTime){
		this.aptitudeStartTime= aptitudeStartTime;       
	}
   
	public java.util.Date getAptitudeStartTime(){
		return this.aptitudeStartTime;
	}

	/**
	 *
	 * 资质失效时间
	 *
	 */
	private java.util.Date aptitudeEndTime;
   
	public void setAptitudeEndTime(java.util.Date aptitudeEndTime){
		this.aptitudeEndTime= aptitudeEndTime;       
	}
   
	public java.util.Date getAptitudeEndTime(){
		return this.aptitudeEndTime;
	}

	/**
	 *
	 * 服务资质说明
	 *
	 */
	private java.lang.String aptitudeDesc;
   
	public void setAptitudeDesc(java.lang.String aptitudeDesc){
		this.aptitudeDesc= aptitudeDesc;       
	}
   
	public java.lang.String getAptitudeDesc(){
		return this.aptitudeDesc;
	}

	/**
	 *
	 * 资质附件
	 *
	 */
	private java.lang.String aptitudeAccessory;
   
	public void setAptitudeAccessory(java.lang.String aptitudeAccessory){
		this.aptitudeAccessory= aptitudeAccessory;       
	}
   
	public java.lang.String getAptitudeAccessory(){
		return this.aptitudeAccessory;
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
	/**
	 *
	 * 合作伙伴主键
	 *
	 */
	private java.lang.String proId;

	public java.lang.String getProId() {
		return proId;
	}

	public void setProId(java.lang.String proId) {
		this.proId = proId;
	}
	
	public boolean equals(Object o) {
		if( o instanceof Aptitude ) {
			Aptitude aptitude=(Aptitude)o;
			if (this.id != null || this.id.equals(aptitude.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


}