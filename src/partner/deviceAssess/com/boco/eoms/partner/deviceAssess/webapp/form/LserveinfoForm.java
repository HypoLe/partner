package com.boco.eoms.partner.deviceAssess.webapp.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;

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
 * @moudle.getAuthor() zhangxuesong
 * @moudle.getVersion() 1.0
 * 
 */
public class LserveinfoForm extends BaseForm implements java.io.Serializable {

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
	private java.lang.String createTime;
   
	public void setCreateTime(java.lang.String createTime){
		this.createTime= createTime;       
	}
   
	public java.lang.String getCreateTime(){
		return this.createTime;
	}

	/**
	 *
	 * 转派厂家时间
	 *
	 */
	private java.lang.String turnFactoryTime;
   
	public void setTurnFactoryTime(java.lang.String turnFactoryTime){
		this.turnFactoryTime= turnFactoryTime;       
	}
   
	public java.lang.String getTurnFactoryTime(){
		return this.turnFactoryTime;
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
	 * 服务人天数
	 *
	 */
	private java.lang.String servePopu;
   
	public void setServePopu(java.lang.String servePopu){
		this.servePopu= servePopu;       
	}
   
	public java.lang.String getServePopu(){
		return this.servePopu;
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
	private java.lang.String total;
	
	private java.lang.String satisfactionReason;
	
	private java.lang.String  deviceType;

	public java.lang.String getSatisfactionReason() {
		return satisfactionReason;
	}

	public void setSatisfactionReason(java.lang.String satisfactionReason) {
		this.satisfactionReason = satisfactionReason;
	}

	public java.lang.String getTotal() {
		return total;
	}

	public void setTotal(java.lang.String total) {
		this.total = total;
	}

	public java.lang.String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(java.lang.String deviceType) {
		this.deviceType = deviceType;
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