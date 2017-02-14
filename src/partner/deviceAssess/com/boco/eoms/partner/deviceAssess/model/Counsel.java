package com.boco.eoms.partner.deviceAssess.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:咨询服务事件信息表
 * </p>
 * <p>
 * Description:咨询服务事件信息表
 * </p> 
 * <p>
 * Mon Sep 27 15:01:30 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class Counsel extends BaseObject {

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
	 * 转派厂家时间
	 *
	 */
	private java.util.Date changeTime;
   
	public void setChangeTime(java.util.Date changeTime){
		this.changeTime= changeTime;       
	}
   
	public java.util.Date getChangeTime(){
		return this.changeTime;
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
	 * 最终反馈时间
	 *
	 */
	private java.util.Date finallyTime;
   
	public void setFinallyTime(java.util.Date finallyTime){
		this.finallyTime= finallyTime;       
	}
   
	public java.util.Date getFinallyTime(){
		return this.finallyTime;
	}

	/**
	 *
	 * 最终反馈时长
	 *
	 */
	private java.lang.String finallyLong;
   
	public void setFinallyLong(java.lang.String finallyLong){
		this.finallyLong= finallyLong;       
	}
   
	public java.lang.String getFinallyLong(){
		return this.finallyLong;
	}

	/**
	 *
	 * 满意度
	 *
	 */
	private java.lang.Integer satisfaction;
   
	public void setSatisfaction(java.lang.Integer satisfaction){
		this.satisfaction= satisfaction;       
	}
   
	public java.lang.Integer getSatisfaction(){
		return this.satisfaction;
	}

	/**
	 *
	 * 计数
	 *
	 */
	private java.lang.String amount;
   
	public void setAmount(java.lang.String amount){
		this.amount= amount;       
	}
   
	public java.lang.String getAmount(){
		return this.amount;
	}

	/**
	 *
	 * 工单主键
	 *
	 */
	private java.lang.String sheetId;
   
	public void setSheetId(java.lang.String sheetId){
		this.sheetId= sheetId;       
	}
   
	public java.lang.String getSheetId(){
		return this.sheetId;
	}

	public boolean equals(Object o) {
		if( o instanceof Counsel ) {
			Counsel counsel=(Counsel)o;
			if (this.id != null || this.id.equals(counsel.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public int hashCode(){
		return super.hashCode();
	}
	/**
	 *@author huhao
	 *@modify 2011-11-2
	 *根据新需求提出添加咨询提出时间 referTime,统计计数total 提交申请人 approveUser
	 * begin
	 */
	private java.util.Date referTime;
	private java.lang.Integer total;
	private java.lang.String approveUser;
	private DeviceAssessApprove deviceAssessApprove;
	
	public DeviceAssessApprove getDeviceAssessApprove() {
		return deviceAssessApprove;
	}

	public void setDeviceAssessApprove(DeviceAssessApprove deviceAssessApprove) {
		this.deviceAssessApprove = deviceAssessApprove;
	}

	public java.lang.String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(java.lang.String approveUser) {
		this.approveUser = approveUser;
	}

	public java.lang.Integer getTotal() {
		return total;
	}

	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}

	public java.util.Date getReferTime() {
		return referTime;
	}

	public void setReferTime(java.util.Date referTime) {
		this.referTime = referTime;
	}
	/**
	 *@author huhao
	 *@modify 2011-11-2
	 *根据新需求提出添加咨询提出时间 referTime,统计计数total 提交申请人 approveUser
	 * end
	 */

	
}