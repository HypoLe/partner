package com.boco.eoms.partner.deviceAssess.webapp.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;

/**
 * <p>
 * Title:板件返修事件信息
 * </p>
 * <p>
 * Description:板件返修事件信息
 * </p>
 * <p>
 * Tue Sep 28 15:24:09 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() zhangxuesong
 * @moudle.getVersion() 1.0
 * 
 */
public class RepairinfoForm extends BaseForm implements java.io.Serializable {

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
	private java.lang.String blockNum;
   
	public void setBlockNum(java.lang.String blockNum){
		this.blockNum= blockNum;       
	}
   
	public java.lang.String getBlockNum(){
		return this.blockNum;
	}

	/**
	 *
	 * 送修时间
	 *
	 */
	private java.lang.String repairTime;
   
	public void setRepairTime(java.lang.String repairTime){
		this.repairTime= repairTime;       
	}
   
	public java.lang.String getRepairTime(){
		return this.repairTime;
	}

	/**
	 *
	 * 返回时间
	 *
	 */
	private java.lang.String returnTime;
   
	public void setReturnTime(java.lang.String returnTime){
		this.returnTime= returnTime;       
	}
   
	public java.lang.String getReturnTime(){
		return this.returnTime;
	}

	/**
	 *
	 * 送修时长（天）
	 *
	 */
	private java.lang.String repairLong;
   
	public void setRepairLong(java.lang.String repairLong){
		this.repairLong= repairLong;       
	}
   
	public java.lang.String getRepairLong(){
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