package com.boco.eoms.partner.baseinfo.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:驻点
 * </p>
 * <p>
 * Description:驻点
 * </p>
 * <p>
 * Fri Dec 18 09:31:48 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class Station extends BaseObject {

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
	 * 驻点名称
	 *
	 */
	private java.lang.String stationName;
   
	public void setStationName(java.lang.String stationName){
		this.stationName= stationName;       
	}
   
	public java.lang.String getStationName(){
		return this.stationName;
	}

	/**
	 *
	 * 所在地市
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
	 * 所在县市
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
	 * 所属合作伙伴
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
	 * 维护专业
	 *
	 */
	private java.lang.String serviceProfessional;
   
	public void setServiceProfessional(java.lang.String serviceProfessional){
		this.serviceProfessional= serviceProfessional;       
	}
   
	public java.lang.String getServiceProfessional(){
		return this.serviceProfessional;
	}

	/**
	 *
	 * 经度
	 *
	 */
	private java.lang.String longitude;
   
	public void setLongitude(java.lang.String longitude){
		this.longitude= longitude;       
	}
   
	public java.lang.String getLongitude(){
		return this.longitude;
	}

	/**
	 *
	 * 纬度
	 *
	 */
	private java.lang.String latitude;
   
	public void setLatitude(java.lang.String latitude){
		this.latitude= latitude;       
	}
   
	public java.lang.String getLatitude(){
		return this.latitude;
	}

	/**
	 *
	 * 地址
	 *
	 */
	private java.lang.String address;
   
	public void setAddress(java.lang.String address){
		this.address= address;       
	}
   
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *
	 * 面积
	 *
	 */
	private java.lang.String area;
   
	public void setArea(java.lang.String area){
		this.area= area;       
	}
   
	public java.lang.String getArea(){
		return this.area;
	}

	/**
	 *
	 * 设立时间
	 *
	 */
	private java.util.Date setTime;
   
	public void setSetTime(java.util.Date setTime){
		this.setTime= setTime;       
	}
   
	public java.util.Date getSetTime(){
		return this.setTime;
	}

	/**
	 *
	 * 附件
	 *
	 */
	private java.lang.String accessory;
   
	public void setAccessory(java.lang.String accessory){
		this.accessory= accessory;       
	}
   
	public java.lang.String getAccessory(){
		return this.accessory;
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

	/**
	 * 所属合作伙伴的id（非节点）
	 * 2010-4-7
	 * bww
	 */
	private String partnerid;
	
	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	/**
	 * 所属大合作伙伴的id（非节点）
	 * 2010-4-7
	 * bww
	 */
	private String bigpartnerid;
	
	public String getBigpartnerid() {
		return bigpartnerid;
	}

	public void setBigpartnerid(String bigpartnerid) {
		this.bigpartnerid = bigpartnerid;
	}
	public boolean equals(Object o) {
		if( o instanceof Station ) {
			Station station=(Station)o;
			if (this.id != null || this.id.equals(station.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}