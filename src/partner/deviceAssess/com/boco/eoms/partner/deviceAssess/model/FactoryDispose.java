package com.boco.eoms.partner.deviceAssess.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:厂家处理设备故障事件信息
 * </p>
 * <p>
 * Description:厂家处理设备故障事件信息
 * </p>
 * <p>
 * Sun Sep 26 15:01:06 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FactoryDispose extends BaseObject {

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
	 * 事件级别
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
	 * 故障开始时间
	 *
	 */
	private java.util.Date beginTime;
   
	public void setBeginTime(java.util.Date beginTime){
		this.beginTime= beginTime;       
	}
   
	public java.util.Date getBeginTime(){
		return this.beginTime;
	}

	/**
	 *
	 * 业务恢复时间
	 *
	 */
	private java.util.Date resumeTime;
   
	public void setResumeTime(java.util.Date resumeTime){
		this.resumeTime= resumeTime;       
	}
   
	public java.util.Date getResumeTime(){
		return this.resumeTime;
	}

	/**
	 *
	 * 故障消除时间
	 *
	 */
	private java.util.Date removeTime;
   
	public void setRemoveTime(java.util.Date removeTime){
		this.removeTime= removeTime;       
	}
   
	public java.util.Date getRemoveTime(){
		return this.removeTime;
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
	 * 业务恢复时长
	 *
	 */
	private java.lang.String resumeLong;
   
	public void setResumeLong(java.lang.String resumeLong){
		this.resumeLong= resumeLong;       
	}
   
	public java.lang.String getResumeLong(){
		return this.resumeLong;
	}

	/**
	 *
	 * 故障恢复时长
	 *
	 */
	private java.lang.String faultLong;
   
	public void setFaultLong(java.lang.String faultLong){
		this.faultLong= faultLong;       
	}
   
	public java.lang.String getFaultLong(){
		return this.faultLong;
	}

	/**
	 *
	 * 计数
	 *
	 */
	private java.lang.Integer amount;
   
	public void setAmount(java.lang.Integer amount){
		this.amount= amount;       
	}
   
	public java.lang.Integer getAmount(){
		return this.amount;
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
	 * 工单主键
	 *
	 */
	private java.lang.String sheetId;	

	public java.lang.String getSheetId() {
		return sheetId;
	}

	public void setSheetId(java.lang.String sheetId) {
		this.sheetId = sheetId;
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
		if( o instanceof FactoryDispose ) {
			FactoryDispose factoryDispose=(FactoryDispose)o;
			if (this.id != null || this.id.equals(factoryDispose.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}