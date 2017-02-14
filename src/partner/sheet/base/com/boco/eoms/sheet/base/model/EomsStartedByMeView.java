package com.boco.eoms.sheet.base.model;

import java.util.Date;

public class EomsStartedByMeView {
	 /**
     * 主键
     */
    private String id;
 
    /**
     * 工单id
     */
    private String sheetId;

    /**
     * 工单标题
     */
    private String title;

    /**
     * 接收时限
     */
    private Date sheetAcceptLimit;

    /**
     * 完成时限
     */
    private Date sheetCompleteLimit;


    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 发送组织类型，用户，部门，角色
     */
    private String  sendOrgType;

    /**
     * 发送人id
     */
    private String sendUserId;

    /**
     * 发送部门id,注：由于dept的主键为int故这里使用Integer类型，建议以后改为统一string
     */
    private String sendDeptId;

    /**
     * 发送角色ID,注：由于role的主键为int故这里使用Integer类型，建议以后改为统一string
     */
    private String sendRoleId;
    
    /**
     * 发送人联系方式
     */
    private String sendContact;

    /**
     * 结束时间
     */
    private Date endTime;


    /**
     * 工单状态，未结束,已归档,已结束未归档
     */
    private Integer status = new Integer(0);


    /**
     * 结束工单用户id
     */
    private String endUserId;

    /**
     * 结束工单用户部门
     */
    private String endDeptId;

    /**
     * 结束工单用户角色
     */
    private String endRoleId;
    
    /**
     * 工单派往部门
     */
    private String toDeptId;

    /**
     * 工单有效否
     */
    private Integer deleted;

    /**
     * wps的流程实例号
     */
    private String piid;

    /**
     * 当A流程触发B流程时，A流程的工单名
     */
    private String parentSheetName;

    /**
     * 当A流程触发B流程时，A流程的工单流水号
     */
    private String parentSheetId;
    
    
    /**
     * 流程关联Key
     */
    private String correlationKey;
    
    /**
     * 父流程关联Key
     */
    private String parentCorrelation;
    
    /**
     * @see 调用类型。
     * @see 异步调用:asynchronism,同步:synchronization
     * @author yyk
     * @date 2008-11-26
     */
    private String invokeMode;
    
    private String sheetType;
    
    private String sheetTypeName;

	public String getCorrelationKey() {
		return correlationKey;
	}

	public void setCorrelationKey(String correlationKey) {
		this.correlationKey = correlationKey;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getEndDeptId() {
		return endDeptId;
	}

	public void setEndDeptId(String endDeptId) {
		this.endDeptId = endDeptId;
	}

	public String getEndRoleId() {
		return endRoleId;
	}

	public void setEndRoleId(String endRoleId) {
		this.endRoleId = endRoleId;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEndUserId() {
		return endUserId;
	}

	public void setEndUserId(String endUserId) {
		this.endUserId = endUserId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInvokeMode() {
		return invokeMode;
	}

	public void setInvokeMode(String invokeMode) {
		this.invokeMode = invokeMode;
	}

	public String getParentCorrelation() {
		return parentCorrelation;
	}

	public void setParentCorrelation(String parentCorrelation) {
		this.parentCorrelation = parentCorrelation;
	}

	public String getParentSheetId() {
		return parentSheetId;
	}

	public void setParentSheetId(String parentSheetId) {
		this.parentSheetId = parentSheetId;
	}

	public String getParentSheetName() {
		return parentSheetName;
	}

	public void setParentSheetName(String parentSheetName) {
		this.parentSheetName = parentSheetName;
	}

	public String getPiid() {
		return piid;
	}

	public void setPiid(String piid) {
		this.piid = piid;
	}

	public String getSendContact() {
		return sendContact;
	}

	public void setSendContact(String sendContact) {
		this.sendContact = sendContact;
	}

	public String getSendDeptId() {
		return sendDeptId;
	}

	public void setSendDeptId(String sendDeptId) {
		this.sendDeptId = sendDeptId;
	}

	public String getSendOrgType() {
		return sendOrgType;
	}

	public void setSendOrgType(String sendOrgType) {
		this.sendOrgType = sendOrgType;
	}

	public String getSendRoleId() {
		return sendRoleId;
	}

	public void setSendRoleId(String sendRoleId) {
		this.sendRoleId = sendRoleId;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public Date getSheetAcceptLimit() {
		return sheetAcceptLimit;
	}

	public void setSheetAcceptLimit(Date sheetAcceptLimit) {
		this.sheetAcceptLimit = sheetAcceptLimit;
	}

	public Date getSheetCompleteLimit() {
		return sheetCompleteLimit;
	}

	public void setSheetCompleteLimit(Date sheetCompleteLimit) {
		this.sheetCompleteLimit = sheetCompleteLimit;
	}

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	public String getSheetType() {
		return sheetType;
	}

	public void setSheetType(String sheetType) {
		this.sheetType = sheetType;
	}

	public String getSheetTypeName() {
		return sheetTypeName;
	}

	public void setSheetTypeName(String sheetTypeName) {
		this.sheetTypeName = sheetTypeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getToDeptId() {
		return toDeptId;
	}

	public void setToDeptId(String toDeptId) {
		this.toDeptId = toDeptId;
	}

	
    
    
}
