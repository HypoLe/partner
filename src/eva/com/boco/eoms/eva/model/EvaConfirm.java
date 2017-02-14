package com.boco.eoms.eva.model;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.base.util.StaticMethod;

/**
 * <p>
 * Title:考核确认信息表
 * </p>
 * <p>
 * Description:考核确认信息表
 * </p>
 * <p>
 * 2010-7-10 
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */
public class EvaConfirm extends BaseObject {

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
	 * 考核模板id
	 */
	private String evaTemplateId;

	public String getEvaTemplateId() {
		return evaTemplateId;
	}

	public void setEvaTemplateId(String evaTemplateId) {
		this.evaTemplateId = evaTemplateId;
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
	 * 操作时间
	 *
	 */
	private java.lang.String operateTimeStr;

	public java.lang.String getOperateTimeString() {
		return operateTimeStr = StaticMethod.date2String(operateTime);
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
	 * 状态:1-确认,2-未确认
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
	 * 确认意见
	 *
	 */
	private java.lang.String agrcomment;
  
	public java.lang.String getAgrcomment() {
		return agrcomment;
	}

	public void setAgrcomment(java.lang.String agrcomment) {
		this.agrcomment = agrcomment;
	}	
	/**
	 *
	 * 确认结果
	 *
	 */
	private java.lang.String confirmResult;
	
	public java.lang.String getConfirmResult() {
		return confirmResult;
	}

	public void setConfirmResult(java.lang.String confirmResult) {
		this.confirmResult = confirmResult;
	}

	public boolean equals(Object o) {
		if( o instanceof EvaConfirm ) {
			EvaConfirm pnrAgreementAudit=(EvaConfirm)o;
			if (this.id != null || this.id.equals(pnrAgreementAudit.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}