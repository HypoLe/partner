package com.boco.eoms.partner.deviceAssess.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:现场服务事件信息
 * </p>
 * <p>
 * Description:现场服务事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p>
 * 
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class Lserveinfo extends BaseObject {

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
	 * 工单id号
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
	private Date createTime;
   
	public void setCreateTime(Date createTime){
		this.createTime= createTime;       
	}
   
	public Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *
	 * 转派厂家时间
	 *
	 */
	private Date turnFactoryTime;
   
	public void setTurnFactoryTime(Date turnFactoryTime){
		this.turnFactoryTime= turnFactoryTime;       
	}
   
	public Date getTurnFactoryTime(){
		return this.turnFactoryTime;
	}

	/**
	 *
	 * 归档时间
	 *
	 */
	private Date pigeonholeTime;
   
	public void setPigeonholeTime(Date pigeonholeTime){
		this.pigeonholeTime= pigeonholeTime;       
	}
   
	public Date getPigeonholeTime(){
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
	 * 服务人天数
	 *
	 */
	private java.lang.Integer servePopu;
   
	public void setServePopu(java.lang.Integer servePopu){
		this.servePopu= servePopu;       
	}
   
	public java.lang.Integer getServePopu(){
		return this.servePopu;
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
	private java.lang.Integer takeCountOf;
   
	public void setTakeCountOf(java.lang.Integer takeCountOf){
		this.takeCountOf= takeCountOf;       
	}
   
	public java.lang.Integer getTakeCountOf(){
		return this.takeCountOf;
	}

	
	/**
	 *
	 * 满意度打分原因
	 *
	 */
	private java.lang.String satisfactionReason;
	/**
	 *
	 * 计数
	 *
	 */
	private java.lang.Integer total;
	
	private java.lang.String  deviceType;
	

	public java.lang.String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(java.lang.String deviceType) {
		this.deviceType = deviceType;
	}

	public java.lang.String getSatisfactionReason() {
		return satisfactionReason;
	}

	public void setSatisfactionReason(java.lang.String satisfactionReason) {
		this.satisfactionReason = satisfactionReason;
	}

	public java.lang.Integer getTotal() {
		return total;
	}

	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}
    private	DeviceAssessApprove deviceAssessApprove;
	
	public DeviceAssessApprove getDeviceAssessApprove() {
		return deviceAssessApprove;
	}

	public void setDeviceAssessApprove(DeviceAssessApprove deviceAssessApprove) {
		this.deviceAssessApprove = deviceAssessApprove;
	}

	public boolean equals(Object o) {
		if( o instanceof Lserveinfo ) {
			Lserveinfo lserveinfo=(Lserveinfo)o;
			if (this.id != null || this.id.equals(lserveinfo.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}