package com.boco.eoms.parter.baseinfo.fee.model;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.user.dao.TawSystemUserDao;

/**
 * <p>
 * Title:费用管理审核步骤
 * </p>
 * <p>
 * Description:费用管理审核步骤
 * </p>
 * <p>
 * Mon May 04 14:42:01 CST 2009
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class PartnerFeeAudit extends BaseObject {

	/**
	 * 主键id
	 */
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 计划或付费信息主键id
	 */
	private String feeId;
	
	public String getFeeId() {
		return feeId;
	}

	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}

	/**
	 * 审核信息分类
	 */
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	/**
	 *
	 * 创建时间
	 *
	 */
	private java.util.Date createTime;
  
	public void setCreateTime(java.util.Date createTime){
		this.createTime= createTime;       
	}
  
	public java.util.Date getCreateTime(){
		return this.createTime;
	}
	
	/**
	 *
	 * 操作时间
	 *
	 */
	private java.util.Date operateTime;
   
	public void setOperateTime(java.util.Date operateTime){
		this.operateTime= operateTime;       
	}
   
	public java.util.Date getOperateTime(){
		return this.operateTime;
	}

	/**
	 *
	 * 操作人类型
	 *
	 */
	private java.lang.String operateType;
   
	public void setOperateType(java.lang.String operateType){
		this.operateType= operateType;       
	}
   
	public java.lang.String getOperateType(){
		return this.operateType;
	}

	/**
	 *
	 * 操作人
	 *
	 */
	private java.lang.String operateId;
   
	public void setOperateId(java.lang.String operateId){
		this.operateId= operateId;       
	}
   
	public java.lang.String getOperateId(){
		return this.operateId;
	}


	/**
	 *
	 * 下一步操作者类型
	 *
	 */
	private java.lang.String toOrgType;
   
	public void setToOrgType(java.lang.String toOrgType){
		this.toOrgType= toOrgType;       
	}
   
	public java.lang.String getToOrgType(){
		return this.toOrgType;
	}

	/**
	 *
	 * 下一步操作者
	 *
	 */
	private java.lang.String toOrgId;
   
	public void setToOrgId(java.lang.String toOrgId){
		this.toOrgId= toOrgId;       
	}
   
	public java.lang.String getToOrgId(){
		return this.toOrgId;
	}

	/**
	 *
	 * 审核结果
	 *
	 */
	private java.lang.String auditResult;
   
	public void setAuditResult(java.lang.String auditResult){
		this.auditResult= auditResult;       
	}
   
	public java.lang.String getAuditResult(){
		return this.auditResult;
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
	 * 状态:1-未完成,2-完成
	 *
	 */
	private java.lang.String state;
   
	public void setState(java.lang.String state){
		this.state= state;       
	}
   
	public java.lang.String getState(){
		return this.state;
	}

	public boolean equals(Object o) {
		if( o instanceof PartnerFeeAudit ) {
			PartnerFeeAudit partnerFeeAudit=(PartnerFeeAudit)o;
			if (this.id != null || this.id.equals(partnerFeeAudit.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}