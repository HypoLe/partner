package com.boco.eoms.summary.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:月工作总结
 * </p>
 * <p>
 * Description:true
 * </p>
 * <p>
 * Wed Jun 17 14:24:09 CST 2009
 * </p>
 * 
 * @author hanlu
 * @version 3.5
 * 
 */
public class TawzjMonth extends BaseObject {

	/**
	 * 主键�
	 */
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 所在组
	 */
	private String team;
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	/**
	 *
	 * 月总结日常维护工作
	 *
	 */
	private java.lang.String work;
   
	public void setWork(java.lang.String work){
		this.work= work;       
	}
   
	public java.lang.String getWork(){
		return this.work;
	}

	/**
	 *
	 * 总结专项工作
	 *
	 */
	private java.lang.String specialWork;
   
	public void setSpecialWork(java.lang.String specialWork){
		this.specialWork= specialWork;       
	}
   
	public java.lang.String getSpecialWork(){
		return this.specialWork;
	}
	/**
	 *
	 * 计划专项工作
	 *
	 */
	private java.lang.String planSpecialWork;
  
	public java.lang.String getPlanSpecialWork() {
		return planSpecialWork;
	}

	public void setPlanSpecialWork(java.lang.String planSpecialWork) {
		this.planSpecialWork = planSpecialWork;
	}
	/**
	 *
	 * 遗留问题
	 *
	 */
	private java.lang.String legacy;
   
	public void setLegacy(java.lang.String legacy){
		this.legacy= legacy;       
	}
   
	public java.lang.String getLegacy(){
		return this.legacy;
	}

	/**
	 *
	 * 月计划日常维护工作
	 *
	 */
	private java.lang.String monthWork;
   
	public void setMonthWork(java.lang.String monthWork){
		this.monthWork= monthWork;       
	}
   
	public java.lang.String getMonthWork(){
		return this.monthWork;
	}

	/**
	 *
	 * 填写人
	 *
	 */
	private java.lang.String useId;
   
	public void setUseId(java.lang.String useId){
		this.useId= useId;       
	}
   
	public java.lang.String getUseId(){
		return this.useId;
	}

	/**
	 *
	 * 填写时间
	 *
	 */
	private java.lang.String dateTime;
   
	public void setDateTime(java.lang.String dateTime){
		this.dateTime= dateTime;       
	}
   
	public java.lang.String getDateTime(){
		return this.dateTime;
	}

	/**
	 *
	 * 审批人
	 *
	 */
	private java.lang.String auditer;
   
	public void setAuditer(java.lang.String auditer){
		this.auditer= auditer;       
	}
   
	public java.lang.String getAuditer(){
		return this.auditer;
	}

	/**
	 *
	 * 审批时间
	 *
	 */
	private java.lang.String audTime;
   
	public void setAudTime(java.lang.String audTime){
		this.audTime= audTime;       
	}
   
	public java.lang.String getAudTime(){
		return this.audTime;
	}

	/**
	 *
	 * 审批状态
	 *
	 */
	private java.lang.String status;
   
	public void setStatus(java.lang.String status){
		this.status= status;       
	}
   
	public java.lang.String getStatus(){
		return this.status;
	}
	/**
	 *
	 * 审批状态名字
	 *
	 */
	private java.lang.String statusName;
	/**
	 *
	 * 删除状态
	 *
	 */
	public java.lang.String getStatusName() {
		if("0".equals(status)){
			statusName="待提交";
		}if("1".equals(status)){
			statusName="待审核";
		}if("2".equals(status)){
			statusName="审核成功";
		}if("3".equals(status)){
			statusName="驳回";
		}
		return this.statusName;
	}

	public void setStatusName(java.lang.String statusName) {
		this.statusName = statusName;
	}
	private java.lang.String deleted;
   
	public void setDeleted(java.lang.String deleted){
		this.deleted= deleted;       
	}
   
	public java.lang.String getDeleted(){
		return this.deleted;
	}

/**
 * 总结附件
 */
	private String accessoriesId;
	
	public String getAccessoriesId() {
		return accessoriesId;
	}
	
	public void setAccessoriesId(String accessoriesId) {
		this.accessoriesId = accessoriesId;
	}
	/**
	 * 计划附件
	 */
		private String planAccessoriesId;
		
		public String getPlanAccessoriesId() {
			return planAccessoriesId;
		}
		
		public void setPlanAccessoriesId(String planAccessoriesId) {
			this.planAccessoriesId = planAccessoriesId;
		}
		private String	month;

	public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

	public boolean equals(Object o) {
		if( o instanceof TawzjMonth ) {
			TawzjMonth tawzjMonth=(TawzjMonth)o;
			if (this.id != null || this.id.equals(tawzjMonth.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	



	
}