package com.boco.eoms.partner.deviceAssess.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:板件返修事件信息
 * </p>
 * <p>
 * Description:板件返修事件信息
 * </p>
 * 
 * @version 1.0
 * 
 */
public class Repairinfo extends BaseObject {

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
	 * 板件主键
	 *
	 */
	private java.lang.String sheetId;
   
	public void setSheetId(java.lang.String sheetId){
		this.sheetId= sheetId;       
	}
   
	public java.lang.String getSheetId(){
		return this.sheetId;
	}

	/**
	 *
	 * 工单号
	 *
	 */
	private java.lang.String sheetNum;
   
	public void setSheetNum(java.lang.String sheetNum){
		this.sheetNum= sheetNum;       
	}
   
	public java.lang.String getSheetNum(){
		return this.sheetNum;
	}

	/**
	 *
	 * 建单时间
	 *
	 */
	private java.util.Date createTime;
   
	public void setCreateTime(java.util.Date createTime){
		this.createTime= createTime;       
	}
   
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *
	 * 归档时间
	 *
	 */
	private java.util.Date pigeonholeTime;
   
	public void setPigeonholeTime(java.util.Date pigeonholeTime){
		this.pigeonholeTime= pigeonholeTime;       
	}
   
	public java.util.Date getPigeonholeTime(){
		return this.pigeonholeTime;
	}

	/**
	 *
	 * 事件名称
	 *
	 */
	private java.lang.String affairName;
   
	public void setAffairName(java.lang.String affairName){
		this.affairName= affairName;       
	}
   
	public java.lang.String getAffairName(){
		return this.affairName;
	}

	/**
	 *
	 * 级别
	 *
	 */
	private java.lang.String affairLevel;
   
	public void setAffairLevel(java.lang.String affairLevel){
		this.affairLevel= affairLevel;       
	}
   
	public java.lang.String getAffairLevel(){
		return this.affairLevel;
	}

	/**
	 *
	 * 发生省份
	 *
	 */
	private java.lang.String province;
   
	public void setProvince(java.lang.String province){
		this.province= province;       
	}
   
	public java.lang.String getProvince(){
		return this.province;
	}

	/**
	 *
	 * 发生地市
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
	 * 厂家
	 *
	 */
	private java.lang.String factory;
   
	public void setFactory(java.lang.String factory){
		this.factory= factory;       
	}
   
	public java.lang.String getFactory(){
		return this.factory;
	}

	/**
	 *
	 * 专业
	 *
	 */
	private java.lang.String speciality;
   
	public void setSpeciality(java.lang.String speciality){
		this.speciality= speciality;       
	}
   
	public java.lang.String getSpeciality(){
		return this.speciality;
	}

	/**
	 *
	 * 设备类型
	 *
	 */
	private java.lang.String equipmentType;
   
	public void setEquipmentType(java.lang.String equipmentType){
		this.equipmentType= equipmentType;       
	}
   
	public java.lang.String getEquipmentType(){
		return this.equipmentType;
	}

	/**
	 *
	 * 设备名称
	 *
	 */
	private java.lang.String equipmentName;
   
	public void setEquipmentName(java.lang.String equipmentName){
		this.equipmentName= equipmentName;       
	}
   
	public java.lang.String getEquipmentName(){
		return this.equipmentName;
	}

	/**
	 *
	 * 设备版本
	 *
	 */
	private java.lang.String equipmentVersion;
   
	public void setEquipmentVersion(java.lang.String equipmentVersion){
		this.equipmentVersion= equipmentVersion;       
	}
   
	public java.lang.String getEquipmentVersion(){
		return this.equipmentVersion;
	}

	/**
	 *
	 * 板块数量
	 *
	 */
	private java.lang.Integer blockNum;
   
	public void setBlockNum(java.lang.Integer blockNum){
		this.blockNum= blockNum;       
	}
   
	public java.lang.Integer getBlockNum(){
		return this.blockNum;
	}

	/**
	 *
	 * 送修时间
	 *
	 */
	private java.util.Date repairTime;
   
	public void setRepairTime(java.util.Date repairTime){
		this.repairTime= repairTime;       
	}
   
	public java.util.Date getRepairTime(){
		return this.repairTime;
	}

	/**
	 *
	 * 返回时间
	 *
	 */
	private java.util.Date returnTime;
   
	public void setReturnTime(java.util.Date returnTime){
		this.returnTime= returnTime;       
	}
   
	public java.util.Date getReturnTime(){
		return this.returnTime;
	}

	/**
	 *
	 * 送修时长（天）
	 *
	 */
	private java.lang.Integer repairLong;
   
	public void setRepairLong(java.lang.Integer repairLong){
		this.repairLong= repairLong;       
	}
   
	public java.lang.Integer getRepairLong(){
		return this.repairLong;
	}
	/**
	 *
	 * 送修时长（小时）
	 *
	 */
	
	
	
    private java.lang.String repairLongHour;
  
	
	public java.lang.String getRepairLongHour() {
		return repairLongHour;
	}

	public void setRepairLongHour(java.lang.String repairLongHour) {
		this.repairLongHour = repairLongHour;
	}
	/**
	 *
	 * 计数
	 *
	 */
	private java.lang.Integer takeCountOf;
   
	public void setTakeCountOf(java.lang.Integer takeCountOf){
		this.takeCountOf= takeCountOf;       
	}
   
	public java.lang.Integer getTakeCountOf(){
		return this.takeCountOf;
	}

	private java.lang.Integer total;
	
	public java.lang.Integer getTotal() {
		return total;
	}

	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}
	
	private DeviceAssessApprove deviceAssessApprove;

	public DeviceAssessApprove getDeviceAssessApprove() {
		return deviceAssessApprove;
	}

	public void setDeviceAssessApprove(DeviceAssessApprove deviceAssessApprove) {
		this.deviceAssessApprove = deviceAssessApprove;
	}

	public boolean equals(Object o) {
		if( o instanceof Repairinfo ) {
			Repairinfo repairinfo=(Repairinfo)o;
			if (this.id != null || this.id.equals(repairinfo.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}