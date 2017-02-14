package com.boco.eoms.summary.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

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
 * @moudle.getAuthor() hanlu
 * @moudle.getVersion() 3.5
 * 
 */
public class TawzjMonthForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
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
	 * 日常维护工作
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
	 * 审批人名称
	 *
	 */
	private java.lang.String auditerName;
	public java.lang.String getAuditerName() {
		return auditerName;
	}

	public void setAuditerName(java.lang.String auditerName) {
		this.auditerName = auditerName;
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
	 * 删除状态
	 *
	 */
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

	protected String accessoriesId;
	
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
		private String year;
		

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}
		private String month;
		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		

}