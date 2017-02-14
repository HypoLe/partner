package com.boco.eoms.partner.deviceAssess.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:peventinfo
 * </p>
 * <p>
 * Description:peventinfo
 * </p>
 * <p>
 * Sat Sep 25 10:35:07 CST 2010
 * </p>
 * 
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class Peventinfo extends BaseObject {

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
	 * 工程号
	 *
	 */
	private java.lang.String proNum;
	public java.lang.String getProNum() {
		return proNum;
	}

	public void setProNum(java.lang.String proNum) {
		this.proNum = proNum;
	}


	/**
	 *
	 * 工程名称
	 *
	 */
	private java.lang.String proName;
	public java.lang.String getProName() {
		return proName;
	}

	public void setProName(java.lang.String proName) {
		this.proName = proName;
	}

	/**
	 *
	 * 级别
	 *
	 */
	private java.lang.String proLevel;
	public java.lang.String getProLevel() {
		return proLevel;
	}

	public void setProLevel(java.lang.String proLevel) {
		this.proLevel = proLevel;
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
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}


	/**
	 *
	 * 结束时间
	 *
	 */
	private Date endTime;
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	private java.lang.Integer takeOf;
	public java.lang.Integer getTakeOf() {
		return takeOf;
	}

	public void setTakeOf(java.lang.Integer takeOf) {
		this.takeOf = takeOf;
	}



	/**
	 *
	 * 工程ID
	 *
	 */
	private java.lang.String proId;
	
	public java.lang.String getProId() {
		return proId;
	}

	public void setProId(java.lang.String proId) {
		this.proId = proId;
	}

	public boolean equals(Object o) {
		if( o instanceof Peventinfo ) {
			Peventinfo peventinfo=(Peventinfo)o;
			if (this.id != null || this.id.equals(peventinfo.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
/**
 * @author huhao
 * modify 2011-11-3
 * 根据新需求增加，省份area，满意度打分原因satisfactionCase，统计新计数total,提交审批人approveUser,建单时间creatTime
 * begin
 */	
	private java.lang.String satisfactionCase;
	private java.lang.String area;
	private java.lang.Integer total;    
	private java.lang.String approveUser;
	private DeviceAssessApprove deviceAssessApprove;
	private Date creatTime;
	
	

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public java.lang.String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(java.lang.String approveUser) {
		this.approveUser = approveUser;
	}

	public DeviceAssessApprove getDeviceAssessApprove() {
		return deviceAssessApprove;
	}

	public void setDeviceAssessApprove(DeviceAssessApprove deviceAssessApprove) {
		this.deviceAssessApprove = deviceAssessApprove;
	}

	public java.lang.Integer getTotal() {
		return total;
	}

	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}

	public java.lang.String getArea() {
		return area;
	}

	public void setArea(java.lang.String area) {
		this.area = area;
	}

	public java.lang.String getSatisfactionCase() {
		return satisfactionCase;
	}

	public void setSatisfactionCase(java.lang.String satisfactionCase) {
		this.satisfactionCase = satisfactionCase;
	}
	/**
	 * @author huhao
	 * modify 2011-11-3
	 * 根据新需求增加，省份area，满意度打分原因satisfactionCase，统计新计数total,提交审批人approveUser
	 * end
	 */	
	
}