package com.boco.eoms.partner.deviceAssess.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

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
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class Ftraininfo extends BaseObject {

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
	private Date beginTime;
   
	public void setBeginTime(Date beginTime){
		this.beginTime= beginTime;       
	}
   
	public Date getBeginTime(){
		return this.beginTime;
	}

	/**
	 *
	 * 结束时间
	 *
	 */
	private Date endTime;
   
	public void setEndTime(Date endTime){
		this.endTime= endTime;       
	}
   
	public Date getEndTime(){
		return this.endTime;
	}

	/**
	 *
	 * 培训人数
	 *
	 */
	private java.lang.Integer trainPopulace;
   
	public void setTrainPopulace(java.lang.Integer trainPopulace){
		this.trainPopulace= trainPopulace;       
	}
   
	public java.lang.Integer getTrainPopulace(){
		return this.trainPopulace;
	}

	/**
	 *
	 * 合格人数
	 *
	 */
	private java.lang.Integer eligibPopulace;
   
	public void setEligibPopulace(java.lang.Integer eligibPopulace){
		this.eligibPopulace= eligibPopulace;       
	}
   
	public java.lang.Integer getEligibPopulace(){
		return this.eligibPopulace;
	}

	/**
	 *
	 * 培训合格率
	 *
	 */
	private double trainEligibRate;
   
	public void setTrainEligibRate(double num){
		this.trainEligibRate= num;       
	}
   
	public double getTrainEligibRate(){
		return this.trainEligibRate;
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
	public java.lang.Integer total ;
	/**
	 * 建单时间
	 */	
	private java.util.Date createTime;
	/**统计信息*/
	private	DeviceAssessApprove deviceAssessApprove;
	
	public DeviceAssessApprove getDeviceAssessApprove() {
		return deviceAssessApprove;
	}

	public void setDeviceAssessApprove(DeviceAssessApprove deviceAssessApprove) {
		this.deviceAssessApprove = deviceAssessApprove;
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

	public java.lang.Integer getTotal() {
		return total;
	}

	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public boolean equals(Object o) {
		if( o instanceof Ftraininfo ) {
			Ftraininfo ftraininfo=(Ftraininfo)o;
			if (this.id != null || this.id.equals(ftraininfo.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}