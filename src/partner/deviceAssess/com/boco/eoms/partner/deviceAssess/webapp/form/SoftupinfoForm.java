package com.boco.eoms.partner.deviceAssess.webapp.form;

import java.util.Date;

import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;

/**
 * <p>
 * Title:软件升级事件信息
 * </p>
 * <p>
 * Description:软件升级事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() zhangxuesong
 * @moudle.getVersion() 1.0
 * 
 */
public class SoftupinfoForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
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
	 * 工单ID号
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
	private java.lang.String createTime;
   
	public void setCreateTime(java.lang.String createTime){
		this.createTime= createTime;       
	}
   
	public java.lang.String getCreateTime(){
		return this.createTime;
	}

	/**
	 *
	 * 归档时间
	 *
	 */
	private java.lang.String pigeonholeTime;
   
	public void setPigeonholeTime(java.lang.String pigeonholeTime){
		this.pigeonholeTime= pigeonholeTime;       
	}
   
	public java.lang.String getPigeonholeTime(){
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
	 * 当前版本
	 *
	 */
	private java.lang.String nonceEdition;
   
	public void setNonceEdition(java.lang.String nonceEdition){
		this.nonceEdition= nonceEdition;       
	}
   
	public java.lang.String getNonceEdition(){
		return this.nonceEdition;
	}

	/**
	 *
	 * 升级版本
	 *
	 */
	private java.lang.String updateEdition;
   
	public void setUpdateEdition(java.lang.String updateEdition){
		this.updateEdition= updateEdition;       
	}
   
	public java.lang.String getUpdateEdition(){
		return this.updateEdition;
	}
//
//	/**
//	 *
//	 * 升级设备数量
//	 *
//	 */
//	private java.lang.String upfixtureAmount;
//   
//	public void setUpfixtureAmount(java.lang.String upfixtureAmount){
//		this.upfixtureAmount= upfixtureAmount;       
//	}
//   
//	public java.lang.String getUpfixtureAmount(){
//		return this.upfixtureAmount;
//	}
//
//	/**
//	 *
//	 * 升级成功数量
//	 *
//	 */
//	private java.lang.String uphitAmount;
//   
//	public void setUphitAmount(java.lang.String uphitAmount){
//		this.uphitAmount= uphitAmount;       
//	}
//   
//	public java.lang.String getUphitAmount(){
//		return this.uphitAmount;
//	}
//
//	/**
//	 *
//	 * 满意度
//	 *
//	 */
//	private java.lang.String satisfaction;
//   
//	public void setSatisfaction(java.lang.String satisfaction){
//		this.satisfaction= satisfaction;       
//	}
//   
//	public java.lang.String getSatisfaction(){
//		return this.satisfaction;
//	}
//
//	/**
//	 *
//	 * 升级成功率
//	 *
//	 */
//	private double uphitRate;
//   
//	public void setUphitRate(double uphitRate){
//		this.uphitRate= uphitRate;       
//	}
//   
//	public double getUphitRate(){
//		return this.uphitRate;
//	}

	/**
	 *
	 * 计数
	 *
	 */
	private java.lang.String takeCountOf;
   
	public void setTakeCountOf(java.lang.String takeCountOf){
		this.takeCountOf= takeCountOf;       
	}
   
	public java.lang.String getTakeCountOf(){
		return this.takeCountOf;
	}
	/** 新加字段： */
	/**未成功的原因*/ 
	private java.lang.String unsucceeReason;
	/**第一次未成功的升级时间*/ 
	private java.lang.String firstUnsucceeTime;
	/**后续升级方案*/ 
	private java.lang.String extendProject;
	/**最终结果*/ 
	private java.lang.String finalResult;
	/**最终升级时间*/ 
	private java.lang.String finalUpTime;
	/**统计标识*/ 
	private java.lang.String  total;
	
	/**附件*/ 
	private java.lang.String accessory;

	public java.lang.String getAccessory() {
		return accessory;
	}

	public void setAccessory(java.lang.String accessory) {
		this.accessory = accessory;
	}

	public java.lang.String getExtendProject() {
		return extendProject;
	}

	public void setExtendProject(java.lang.String extendProject) {
		this.extendProject = extendProject;
	}

	public java.lang.String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(java.lang.String finalResult) {
		this.finalResult = finalResult;
	}

	

	public java.lang.String getFinalUpTime() {
		return finalUpTime;
	}

	public void setFinalUpTime(java.lang.String finalUpTime) {
		this.finalUpTime = finalUpTime;
	}

	public java.lang.String getFirstUnsucceeTime() {
		return firstUnsucceeTime;
	}

	public void setFirstUnsucceeTime(java.lang.String firstUnsucceeTime) {
		this.firstUnsucceeTime = firstUnsucceeTime;
	}

	public java.lang.String getTotal() {
		return total;
	}

	public void setTotal(java.lang.String total) {
		this.total = total;
	}

	public java.lang.String getUnsucceeReason() {
		return unsucceeReason;
	}

	public void setUnsucceeReason(java.lang.String unsucceeReason) {
		this.unsucceeReason = unsucceeReason;
	}
	private DeviceAssessApprove deviceAssessApprove;

	public DeviceAssessApprove getDeviceAssessApprove() {
		return deviceAssessApprove;
	}

	public void setDeviceAssessApprove(DeviceAssessApprove deviceAssessApprove) {
		this.deviceAssessApprove = deviceAssessApprove;
	}
	
}