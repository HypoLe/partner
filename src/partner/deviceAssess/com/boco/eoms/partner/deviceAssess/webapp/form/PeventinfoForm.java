package com.boco.eoms.partner.deviceAssess.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;

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
 * @moudle.getAuthor() zhangxuesong
 * @moudle.getVersion() 1.0
 * 
 */
public class PeventinfoForm extends BaseForm implements java.io.Serializable {

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
	 * 工程号
	 *
	 */
	private java.lang.String proNum;
   
	public void setProNum(java.lang.String proNum){
		this.proNum= proNum;       
	}
   
	public java.lang.String getProNum(){
		return this.proNum;
	}

	/**
	 *
	 * 工程名称
	 *
	 */
	private java.lang.String proName;
   
	public void setProName(java.lang.String proName){
		this.proName= proName;       
	}
   
	public java.lang.String getProName(){
		return this.proName;
	}

	/**
	 *
	 * 级别
	 *
	 */
	private java.lang.String proLevel;
   
	public void setProLevel(java.lang.String proLevel) {
		this.proLevel = proLevel;
	}

	public java.lang.String getProLevel() {
		return proLevel;
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
	private String beginTime;
   
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 *
	 * 结束时间
	 *
	 */
	private String endTime;
   
	public void setEndTime(String endTime){
		this.endTime= endTime;       
	}
   
	public String getEndTime(){
		return this.endTime;
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
	private java.lang.String takeOf;
   
	public void setTakeOf(java.lang.String takeOf){
		this.takeOf= takeOf;       
	}
   
	public java.lang.String getTakeOf(){
		return this.takeOf;
	}

	/**
	 *
	 * 工程ID
	 *
	 */
	private java.lang.String proId;
   
	public void setProId(java.lang.String proId){
		this.proId= proId;       
	}
   
	public java.lang.String getProId(){
		return this.proId;
	}

	/**
	 * @author huhao
	 * modify 2011-11-3
	 * 根据新需求增加，省份area，满意度打分原因satisfactionCase，统计新计数total,提交审批人approveUser
	 * begin
	 */	
		private java.lang.String satisfactionCase;
		private java.lang.String area;
		private java.lang.Integer total;
		private DeviceAssessApprove deviceAssessApprove;
		private java.lang.String approveUser;
		private java.lang.String creatTime;
		
		public java.lang.String getCreatTime() {
			return creatTime;
		}

		public void setCreatTime(java.lang.String creatTime) {
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