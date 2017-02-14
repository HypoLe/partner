package com.boco.eoms.partner.deviceAssess.webapp.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;

/**
 * <p>
 * Title:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Description:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Mon Sep 27 13:45:23 CST 2010 
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class BigFaultForm extends BaseForm implements java.io.Serializable {

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
	 * 重大故障名称
	 *
	 */
	private java.lang.String bigFaultName;
   
	public void setBigFaultName(java.lang.String bigFaultName){
		this.bigFaultName= bigFaultName;       
	}
   
	public java.lang.String getBigFaultName(){
		return this.bigFaultName;
	}

	/**
	 *
	 * 重大故障定义编号
	 *
	 */
	private java.lang.String bigFaultNo;
   
	public void setBigFaultNo(java.lang.String bigFaultNo){
		this.bigFaultNo= bigFaultNo;       
	}
   
	public java.lang.String getBigFaultNo(){
		return this.bigFaultNo;
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
	private java.lang.String beginTime;
   
	public void setBeginTime(java.lang.String beginTime){
		this.beginTime= beginTime;       
	}
   
	public java.lang.String getBeginTime(){
		return this.beginTime;
	}

	/**
	 *
	 * 业务恢复时间
	 *
	 */
	private java.lang.String resumeTime;
   
	public void setResumeTime(java.lang.String resumeTime){
		this.resumeTime= resumeTime;       
	}
   
	public java.lang.String getResumeTime(){
		return this.resumeTime;
	}

	/**
	 *
	 * 故障消除时间
	 *
	 */
	private java.lang.String removeTime;
   
	public void setRemoveTime(java.lang.String removeTime){
		this.removeTime= removeTime;       
	}
   
	public java.lang.String getRemoveTime(){
		return this.removeTime;
	}

	/**
	 *
	 * 满意度
	 *
	 */
	private java.lang.String satisfaction;
   
	public void setSatisfaction(java.lang.String satisfaction){
		this.satisfaction= satisfaction;       
	}
   
	public java.lang.String getSatisfaction(){
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
	private java.lang.String amount;
   
	public void setAmount(java.lang.String amount){
		this.amount= amount;       
	}
   
	public java.lang.String getAmount(){
		return this.amount;
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
	
	private FormFile importFile;

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
}