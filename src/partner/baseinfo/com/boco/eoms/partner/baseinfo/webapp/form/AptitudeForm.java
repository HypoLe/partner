package com.boco.eoms.partner.baseinfo.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

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
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class AptitudeForm extends BaseForm implements java.io.Serializable {

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
	private java.lang.String aptitudeStartTime;
   
	public void setAptitudeStartTime(java.lang.String aptitudeStartTime){
		this.aptitudeStartTime= aptitudeStartTime;       
	}
   
	public java.lang.String getAptitudeStartTime(){
		return this.aptitudeStartTime;
	}

	/**
	 *
	 * 资质失效时间
	 *
	 */
	private java.lang.String aptitudeEndTime;
   
	public void setAptitudeEndTime(java.lang.String aptitudeEndTime){
		this.aptitudeEndTime= aptitudeEndTime;       
	}
   
	public java.lang.String getAptitudeEndTime(){
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

}