package com.boco.eoms.partner.assess.AssFlow.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:后评估步骤列表信息
 * </p>
 * <p>
 * Description:后评估步骤列表信息
 * </p>
 * <p>
 * Fri Sep 10 13:54:56 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() 戴志刚
 * @moudle.getVersion() 1.0
 * 
 */
public class AssOperateStepForm extends BaseForm implements java.io.Serializable {

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
	 * 考核main表id
	 *
	 */
	private java.lang.String reportId;
   
	public void setReportId(java.lang.String reportId){
		this.reportId= reportId;       
	}
   
	public java.lang.String getReportId(){
		return this.reportId;
	}
	
	/**
	 *
	 * 评估表名
	 *
	 */
	private java.lang.String taskName;
	
	public java.lang.String getTaskName() {
		return taskName;
	}

	public void setTaskName(java.lang.String taskName) {
		this.taskName = taskName;
	}
	/**
	 *
	 * 任务执行者
	 *
	 */
	private java.lang.String operateOrgId;
   
	public void setOperateOrgId(java.lang.String operateOrgId){
		this.operateOrgId= operateOrgId;       
	}
   
	public java.lang.String getOperateOrgId(){
		return this.operateOrgId;
	}

	/**
	 *
	 * 任务执行者类型
	 *
	 */
	private java.lang.String operateOrgType;
   
	public void setOperateOrgType(java.lang.String operateOrgType){
		this.operateOrgType= operateOrgType;       
	}
   
	public java.lang.String getOperateOrgType(){
		return this.operateOrgType;
	}

	/**
	 *
	 * 实际任务执行人
	 *
	 */
	private java.lang.String operateUser;
   
	public void setOperateUser(java.lang.String operateUser){
		this.operateUser= operateUser;       
	}
   
	public java.lang.String getOperateUser(){
		return this.operateUser;
	}

	/**
	 *
	 * 操作时间
	 *
	 */
	private java.lang.String operateTime;
   
	public void setOperateTime(java.lang.String operateTime){
		this.operateTime= operateTime;       
	}
   
	public java.lang.String getOperateTime(){
		return this.operateTime;
	}

	/**
	 *
	 * 任务执行人联系方式
	 *
	 */
	private java.lang.String operateUserContact;
   
	public void setOperateUserContact(java.lang.String operateUserContact){
		this.operateUserContact= operateUserContact;       
	}
   
	public java.lang.String getOperateUserContact(){
		return this.operateUserContact;
	}

	/**
	 *
	 * 操作意见
	 *
	 */
	private java.lang.String operateOpinion;
   
	public void setOperateOpinion(java.lang.String operateOpinion){
		this.operateOpinion= operateOpinion;       
	}
   
	public java.lang.String getOperateOpinion(){
		return this.operateOpinion;
	}

	/**
	 *
	 * 操作阶段
	 *
	 */
	private java.lang.String state;
   
	public void setState(java.lang.String state){
		this.state= state;       
	}
   
	public java.lang.String getState(){
		return this.state;
	}
	/**
	 *
	 * 操作阶段名称
	 *
	 */
	private java.lang.String stateName;
  
	public void setStateName(java.lang.String stateName){
		this.stateName= stateName;       
	}
  
	public java.lang.String getStateName(){
		return this.stateName;
	}
	/**
	 *
	 * 状态
	 *
	 */
	private java.lang.String operateFlag;
   
	public void setOperateFlag(java.lang.String operateFlag){
		this.operateFlag= operateFlag;       
	}
   
	public java.lang.String getOperateFlag(){
		return this.operateFlag;
	}


	protected String accessoriesId;
	
	public String getAccessoriesId() {
		return accessoriesId;
	}
	
	public void setAccessoriesId(String accessoriesId) {
		this.accessoriesId = accessoriesId;
	}
	//main表对应信息

	/**
	 *
	 * 对应地域id
	 *
	 */
	private java.lang.String area;
  
	public void setArea(java.lang.String area){
		this.area= area;       
	}
  
	public java.lang.String getArea(){
		return this.area;
	}
	
	/**
	 *
	 * 对应周期
	 *
	 */
	private java.lang.String time;
 
	public void setTime(java.lang.String time){
		this.time= time;       
	}
 
	public java.lang.String getTime(){
		return this.time;
	}
	
	/**
	 *
	 * 对应模板id
	 *
	 */
	private java.lang.String templateId;
   
	public void setTemplateId(java.lang.String templateId){
		this.templateId= templateId;       
	}
   
	public java.lang.String getTemplateId(){
		return this.templateId;
	}

	/**
	 *
	 * 考核人
	 *
	 */
	private java.lang.String checkUserId;
   
	public void setCheckUserId(java.lang.String checkUserId){
		this.checkUserId= checkUserId;       
	}
   
	public java.lang.String getCheckUserId(){
		return this.checkUserId;
	}

	/**
	 *
	 * 地市公司部门id
	 *
	 */
	private java.lang.String deptId;
   
	public void setDeptId(java.lang.String deptId){
		this.deptId= deptId;       
	}
   
	public java.lang.String getDeptId(){
		return this.deptId;
	}

	/**
	 *
	 * 合作伙伴id
	 *
	 */
	private java.lang.String partnerId;
   
	public void setPartnerId(java.lang.String partnerId){
		this.partnerId= partnerId;       
	}
   
	public java.lang.String getPartnerId(){
		return this.partnerId;
	}

	/**
	 *
	 * 合作伙伴名称
	 *
	 */
	private java.lang.String partnerName;
   
	public void setPartnerName(java.lang.String partnerName){
		this.partnerName= partnerName;       
	}
   
	public java.lang.String getPartnerName(){
		return this.partnerName;
	}

	/**
	 *
	 * 总公司合作伙伴
	 *
	 */
	private java.lang.String bigPartnerId;
   
	public void setBigPartnerId(java.lang.String bigPartnerId){
		this.bigPartnerId= bigPartnerId;       
	}
   
	public java.lang.String getBigPartnerId(){
		return this.bigPartnerId;
	}

	/**
	 *
	 * 创建时间
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
	 * 考核主体表识
	 *
	 */
	private java.lang.String checkType;
	
	public java.lang.String getCheckType() {
		return checkType;
	}

	public void setCheckType(java.lang.String checkType) {
		this.checkType = checkType;
	}
	
	
	/**
	 * 标包号
	 * add by:WangJunfeng
	 * 2010-11-29
	 */
	private java.lang.String lotNum;

	public java.lang.String getLotNum() {
		return lotNum;
	}

	public void setLotNum(java.lang.String lotNum) {
		this.lotNum = lotNum;
	}

	public boolean equals(Object o) {
		return this == o;
	}

}