package com.boco.eoms.parter.baseinfo.fee.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:合作伙伴付款计划
 * </p>
 * <p>
 * Description:合作伙伴付款计划
 * </p>
 * <p>
 * Tue Sep 08 09:38:34 CST 2009
 * </p>
 * 
 * @author lvweihua
 * @version 3.5
 * 
 */
public class PartnerFeePlan extends BaseObject {

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
	 * 付款计划名称
	 *
	 */
	private java.lang.String name;
   
	public void setName(java.lang.String name){
		this.name= name;       
	}
   
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *
	 * 付款单位
	 *
	 */
	private java.lang.String payUnit;
   
	public void setPayUnit(java.lang.String payUnit){
		this.payUnit= payUnit;       
	}
   
	public java.lang.String getPayUnit(){
		return this.payUnit;
	}
	
	/**
	 *
	 * 付款单位名称
	 *
	 */
	private java.lang.String payUnitName;
	   
	public void setPayUnitName(java.lang.String payUnitName){
		this.payUnitName= payUnitName;       
	}
   
	public java.lang.String getPayUnitName(){
		return this.payUnitName;
	}
	
	
	/**
	 *
	 * 计划付款时间
	 *
	 */
	private Date planPayDate;
   
	public void setPlanPayDate(Date planPayDate){
		this.planPayDate= planPayDate;       
	}
   
	public Date getPlanPayDate(){
		return this.planPayDate;
	}

	/**
	 *
	 * 实际付款时间
	 *
	 */
	private Date factPayDate;
   
	public void setFactPayDate(Date factPayDate){
		this.factPayDate= factPayDate;       
	}
   
	public Date getFactPayDate(){
		return this.factPayDate;
	}

	/**
	 *
	 * 计划付款金额
	 *
	 */
	private java.lang.String planPayFee;
   
	public void setPlanPayFee(java.lang.String planPayFee){
		this.planPayFee= planPayFee;       
	}
   
	public java.lang.String getPlanPayFee(){
		return this.planPayFee;
	}

	/**
	 *
	 * 实际付款金额
	 *
	 */
	private java.lang.String factPayFee;
   
	public void setFactPayFee(java.lang.String factPayFee){
		this.factPayFee= factPayFee;       
	}
   
	public java.lang.String getFactPayFee(){
		return this.factPayFee;
	}

	/**
	 *
	 * 付款方式
	 *
	 */
	private java.lang.String payWay;
   
	public void setPayWay(java.lang.String payWay){
		this.payWay= payWay;       
	}
   
	public java.lang.String getPayWay(){
		return this.payWay;
	}

	/**
	 *
	 * 付款人
	 *
	 */
	private java.lang.String payUser;
   
	public void setPayUser(java.lang.String payUser){
		this.payUser= payUser;       
	}
   
	public java.lang.String getPayUser(){
		return this.payUser;
	}

	/**
	 *
	 * 付款状态
	 *
	 */
	private java.lang.String payStatus;
   
	public void setPayStatus(java.lang.String payStatus){
		this.payStatus= payStatus;       
	}
   
	public java.lang.String getPayStatus(){
		return this.payStatus;
	}

	/**
	 *
	 * 备注
	 *
	 */
	private java.lang.String remark;
   
	public void setRemark(java.lang.String remark){
		this.remark= remark;       
	}
   
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *
	 * 付款阶段
	 *
	 */
	private java.lang.String payStage;
   
	public void setPayStage(java.lang.String payStage){
		this.payStage= payStage;       
	}
   
	public java.lang.String getPayStage(){
		return this.payStage;
	}

	/**
	 *
	 * 合同编号
	 *
	 */
	private java.lang.String compactNo;
   
	public void setCompactNo(java.lang.String compactNo){
		this.compactNo= compactNo;       
	}
   
	public java.lang.String getCompactNo(){
		return this.compactNo;
	}

	/**
	 *
	 * 合同名称
	 *
	 */
	private java.lang.String compactName;
   
	public void setCompactName(java.lang.String compactName){
		this.compactName= compactName;       
	}
   
	public java.lang.String getCompactName(){
		return this.compactName;
	}
	
	/**
	 * 
	 * 付款计划创建人
	 * 
	 */
	private java.lang.String createUser;
	
	/**
	 * 付款计划创建布部门
	 */
	private java.lang.String createDept;
	
	/**
	 * 付款计划创建时间
	 */
	private java.util.Date createDate;
	
	/**
	 * 收款单位
	 */
	private java.lang.String collectUnit;
	
	
	/**
	 *
	 * 收款单位名称
	 *
	 */
	private java.lang.String collectUnitName;
	
	/**
	 *
	 * 计划状态
	 *
	 */
	private java.lang.String payState;
	   
	public void setCollectUnitName(java.lang.String collectUnitName){
		this.collectUnitName= collectUnitName;       
	}
   
	public java.lang.String getCollectUnitName(){
		return this.collectUnitName;
	}
	
	public boolean equals(Object o) {
		if( o instanceof PartnerFeePlan ) {
			PartnerFeePlan partnerFeePlan=(PartnerFeePlan)o;
			if (this.id != null || this.id.equals(partnerFeePlan.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public java.lang.String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(java.lang.String createUser) {
		this.createUser = createUser;
	}

	public java.lang.String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(java.lang.String createDept) {
		this.createDept = createDept;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.String getCollectUnit() {
		return collectUnit;
	}

	public void setCollectUnit(java.lang.String collectUnit) {
		this.collectUnit = collectUnit;
	}

	public java.lang.String getPayState() {
		return payState;
	}

	public void setPayState(java.lang.String payState) {
		this.payState = payState;
	}

	/**
	 *
	 * 合同Id
	 *
	 */
	private java.lang.String contentId;

	public java.lang.String getContentId() {
		return contentId;
	}

	public void setContentId(java.lang.String contentId) {
		this.contentId = contentId;
	}
}