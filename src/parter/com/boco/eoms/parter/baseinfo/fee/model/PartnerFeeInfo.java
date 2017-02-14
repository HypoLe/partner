package com.boco.eoms.parter.baseinfo.fee.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:合作伙伴付费信息
 * </p>
 * <p>
 * Description:合作伙伴付费信息
 * </p>
 * <p>
 * Wed Sep 09 11:22:35 CST 2009
 * </p>
 * 
 * @author lvweihua
 * @version 3.5
 * 
 */
public class PartnerFeeInfo extends BaseObject {

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
	 * 付款费用名称
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
	 * 付款原因
	 *
	 */
	private java.lang.String payCause;
   
	public void setPayCause(java.lang.String payCause){
		this.payCause= payCause;       
	}
   
	public java.lang.String getPayCause(){
		return this.payCause;
	}

	/**
	 *
	 * 费用类型
	 *
	 */
	private java.lang.String feeType;
   
	public void setFeeType(java.lang.String feeType){
		this.feeType= feeType;       
	}
   
	public java.lang.String getFeeType(){
		return this.feeType;
	}

	/**
	 *
	 * 付款时间
	 *
	 */
	private Date payDate;
   
	public void setPayDate(Date payDate){
		this.payDate= payDate;       
	}
   
	public Date getPayDate(){
		return this.payDate;
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
	 * 付款金额
	 *
	 */
	private java.lang.String payFee;
   
	public void setPayFee(java.lang.String payFee){
		this.payFee= payFee;       
	}
   
	public java.lang.String getPayFee(){
		return this.payFee;
	}

	/**
	 *
	 * 付款账号
	 *
	 */
	private java.lang.String payNo;
   
	public void setPayNo(java.lang.String payNo){
		this.payNo= payNo;       
	}
   
	public java.lang.String getPayNo(){
		return this.payNo;
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
	 * 附件名称
	 *
	 */
	private java.lang.String fileName;
   
	public void setFileName(java.lang.String fileName){
		this.fileName= fileName;       
	}
   
	public java.lang.String getFileName(){
		return this.fileName;
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
	 * 是否有付款计划
	 *
	 */
	private java.lang.String isPlan;
   
	public void setIsPlan(java.lang.String isPlan){
		this.isPlan= isPlan;       
	}
   
	public java.lang.String getIsPlan(){
		return this.isPlan;
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
	 * 付款费用信息创建人
	 * 
	 */
	private java.lang.String createUser;
	
	/**
	 * 付款费用信息创建布部门
	 */
	private java.lang.String createDept;
	
	/**
	 * 付款费用信息创建时间
	 */
	private java.util.Date createDate;
	
	/**
	 * 收款单位
	 */
	private java.lang.String collectUnit;
	
	/**
	 * 付款计划id
	 */
	private java.lang.String planId;
	
	public boolean equals(Object o) {
		if( o instanceof PartnerFeeInfo ) {
			PartnerFeeInfo partnerFeeInfo=(PartnerFeeInfo)o;
			if (this.id != null || this.id.equals(partnerFeeInfo.getId())) {
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

	public java.lang.String getPlanId() {
		return planId;
	}

	public void setPlanId(java.lang.String planId) {
		this.planId = planId;
	}
}