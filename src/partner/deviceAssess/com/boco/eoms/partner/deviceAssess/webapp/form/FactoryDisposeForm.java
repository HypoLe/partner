package com.boco.eoms.partner.deviceAssess.webapp.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;

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
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class FactoryDisposeForm extends BaseForm implements java.io.Serializable {

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
	private java.lang.String changeTime;
   
	public void setChangeTime(java.lang.String changeTime){
		this.changeTime= changeTime;       
	}
   
	public java.lang.String getChangeTime(){
		return this.changeTime;
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