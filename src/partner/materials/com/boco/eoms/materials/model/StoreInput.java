package com.boco.eoms.materials.model;

import java.util.Date;





/**
 * 入库单表model类
 * 
 * @author wanghongliang
 * 
 */
public class StoreInput {
	private String id;
	/**
	 * 单号
	 */
	private String storeNum;
	/**
	 * 原始单号
	 */
	private String storeOriginalNum;
	/**
	 * 仓库名称
	 */
	private String storeSname;
	/**
	 * 仓库主键ID
	 */
	private String storeSid;
	/**
	 * 领料人
	 */
	private String storeRequisitioner;
	/**
	 * 开单日期
	 */
	private Date storeBillingDate;
	/**
	 * 制单日期
	 */
	private Date storeMakeBillDate;
	/**
	 * 制单人
	 */
	private String storeMakeBillPeople;
	/**
	 * 审核状态
	 */
	private String storeExamineStatus;
	/**
	 * 审核人
	 */
	private String storeExaminePeople;
	/**
	 * 往来单位
	 */
	private String storeCompany;
	/**
	 * 最后修改人
	 */
	private String storeLastModifyPeople;
	/**
	 * 最后修改日
	 */
	private Date storeLastModifyDate;
	/**
	 * 单据类型
	 */
	private String storeBillType;
	/**
	 * 部门
	 */
	private String storeDepartment;
	/**
	 * 往来单位主键ID
	 */
	private String storeCompanyId;
	/**
	 * 备注
	 */
	private String storeRemark;

	private int deleteFlag;
	private String auditSuggestion;
	
	public String getAuditSuggestion() {
		return auditSuggestion;
	}

	public void setAuditSuggestion(String auditSuggestion) {
		this.auditSuggestion = auditSuggestion;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(String storeNum) {
		this.storeNum = storeNum;
	}

	public String getStoreOriginalNum() {
		return storeOriginalNum;
	}

	public void setStoreOriginalNum(String storeOriginalNum) {
		this.storeOriginalNum = storeOriginalNum;
	}

	public String getStoreSname() {
		return storeSname;
	}

	public void setStoreSname(String storeSname) {
		this.storeSname = storeSname;
	}

	public String getStoreSid() {
		return storeSid;
	}

	public void setStoreSid(String storeSid) {
		this.storeSid = storeSid;
	}

	public String getStoreRequisitioner() {
		return storeRequisitioner;
	}

	public void setStoreRequisitioner(String storeRequisitioner) {
		this.storeRequisitioner = storeRequisitioner;
	}

	public Date getStoreBillingDate() {
		return storeBillingDate;
	}

	public void setStoreBillingDate(Date storeBillingDate) {
		this.storeBillingDate = storeBillingDate;
	}

	public Date getStoreMakeBillDate() {
		return storeMakeBillDate;
	}

	public void setStoreMakeBillDate(Date storeMakeBillDate) {
		this.storeMakeBillDate = storeMakeBillDate;
	}

	public String getStoreMakeBillPeople() {
		return storeMakeBillPeople;
	}

	public void setStoreMakeBillPeople(String storeMakeBillPeople) {
		this.storeMakeBillPeople = storeMakeBillPeople;
	}

	public String getStoreExamineStatus() {
		return storeExamineStatus;
	}

	public void setStoreExamineStatus(String storeExamineStatus) {
		this.storeExamineStatus = storeExamineStatus;
	}

	public String getStoreExaminePeople() {
		return storeExaminePeople;
	}

	public void setStoreExaminePeople(String storeExaminePeople) {
		this.storeExaminePeople = storeExaminePeople;
	}

	public String getStoreCompany() {
		return storeCompany;
	}

	public void setStoreCompany(String storeCompany) {
		this.storeCompany = storeCompany;
	}

	public String getStoreLastModifyPeople() {
		return storeLastModifyPeople;
	}

	public void setStoreLastModifyPeople(String storeLastModifyPeople) {
		this.storeLastModifyPeople = storeLastModifyPeople;
	}

	public Date getStoreLastModifyDate() {
		return storeLastModifyDate;
	}

	public void setStoreLastModifyDate(Date storeLastModifyDate) {
		this.storeLastModifyDate = storeLastModifyDate;
	}

	public String getStoreBillType() {
		return storeBillType;
	}

	public void setStoreBillType(String storeBillType) {
		this.storeBillType = storeBillType;
	}

	public String getStoreDepartment() {
		return storeDepartment;
	}

	public void setStoreDepartment(String storeDepartment) {
		this.storeDepartment = storeDepartment;
	}

	public String getStoreCompanyId() {
		return storeCompanyId;
	}

	public void setStoreCompanyId(String storeCompanyId) {
		this.storeCompanyId = storeCompanyId;
	}

	public String getStoreRemark() {
		return storeRemark;
	}

	public void setStoreRemark(String storeRemark) {
		this.storeRemark = storeRemark;
	}
}
