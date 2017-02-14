package com.boco.eoms.partner.deviceAssess.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;

/**
 * <p>
 * Title:pnr_deviceassess_ftrain_info
 * </p>
 * <p>
 * Description:pnr_deviceassess_ftrain_info
 * </p>
 * <p>
 * Sun Sep 26 09:11:03 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() zhangxuesong
 * @moudle.getVersion() 1.0
 * 
 */
public class FtraininfoForm extends BaseForm implements java.io.Serializable {

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
	 * 厂家培训事件信息ID
	 *
	 */
	private java.lang.String trainId;
   
	public void setTrainId(java.lang.String trainId){
		this.trainId= trainId;       
	}
   
	public java.lang.String getTrainId(){
		return this.trainId;
	}

	/**
	 *
	 * 事件名称
	 *
	 */
	private java.lang.String eventName;
   
	public void setEventName(java.lang.String eventName){
		this.eventName= eventName;       
	}
   
	public java.lang.String getEventName(){
		return this.eventName;
	}

	/**
	 *
	 * 级别
	 *
	 */
	private java.lang.String trainLevel;
   
	public void setTrainLevel(java.lang.String trainLevel){
		this.trainLevel= trainLevel;       
	}
   
	public java.lang.String getTrainLevel(){
		return this.trainLevel;
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
	 * 开始时间
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
	 * 结束时间
	 *
	 */
	private java.lang.String endTime;
   
	public void setEndTime(java.lang.String endTime){
		this.endTime= endTime;       
	}
   
	public java.lang.String getEndTime(){
		return this.endTime;
	}

	/**
	 *
	 * 培训人数
	 *
	 */
	private java.lang.String trainPopulace;
   
	public void setTrainPopulace(java.lang.String trainPopulace){
		this.trainPopulace= trainPopulace;       
	}
   
	public java.lang.String getTrainPopulace(){
		return this.trainPopulace;
	}

	/**
	 *
	 * 合格人数
	 *
	 */
	private java.lang.String eligibPopulace;
   
	public void setEligibPopulace(java.lang.String eligibPopulace){
		this.eligibPopulace= eligibPopulace;       
	}
   
	public java.lang.String getEligibPopulace(){
		return this.eligibPopulace;
	}

	/**
	 *
	 * 培训合格率
	 *
	 */
	private java.lang.String trainEligibRate;
   
	public void setTrainEligibRate(java.lang.String trainEligibRate){
		this.trainEligibRate= trainEligibRate;       
	}
   
	public java.lang.String getTrainEligibRate(){
		return this.trainEligibRate;
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
	/**
	 * 新增字段
	 */
	/**
	 * 省份
	 */	
	private java.lang.String province;
	   
	/**
	 * 满意度打分原因
	 */	
	private java.lang.String satisfactionReason;
	/**
	 * 统计标识
	 */	
	public java.lang.String  total ;
	/**
	 * 建单时间
	 */	
	private java.lang.String  createTime;

	

	public java.lang.String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getProvince() {
		return province;
	}

	public void setProvince(java.lang.String province) {
		this.province = province;
	}

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
	private DeviceAssessApprove deviceAssessApprove;

	public DeviceAssessApprove getDeviceAssessApprove() {
		return deviceAssessApprove;
	}

	public void setDeviceAssessApprove(DeviceAssessApprove deviceAssessApprove) {
		this.deviceAssessApprove = deviceAssessApprove;
	}
	
}