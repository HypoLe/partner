package com.boco.eoms.partner.baseinfo.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:合作伙伴服务评价
 * </p>
 * <p>
 * Description:合作伙伴服务评价
 * </p>
 * <p>
 * Tue Apr 27 16:50:24 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class PartnerEvaluation extends BaseObject {

	/**
	 * id
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
	 * 合同id
	 *
	 */
	private java.lang.String contractId;
   
	public void setContractId(java.lang.String contractId){
		this.contractId= contractId;       
	}
   
	public java.lang.String getContractId(){
		return this.contractId;
	}

	/**
	 *
	 * 费用计划id
	 *
	 */
	private java.lang.String feePlanId;
   
	public void setFeePlanId(java.lang.String feePlanId){
		this.feePlanId= feePlanId;       
	}
   
	public java.lang.String getFeePlanId(){
		return this.feePlanId;
	}

	/**
	 *
	 * 服务评价时间
	 *
	 */
	private java.util.Date evaTime;
   
	public void setEvaTime(java.util.Date evaTime){
		this.evaTime= evaTime;       
	}
   
	public java.util.Date getEvaTime(){
		return this.evaTime;
	}

	/**
	 *
	 * 服务评价结果
	 *
	 */
	private java.lang.String evaResult;
   
	public void setEvaResult(java.lang.String evaResult){
		this.evaResult= evaResult;       
	}
   
	public java.lang.String getEvaResult(){
		return this.evaResult;
	}

	/**
	 *
	 * 服务评价人
	 *
	 */
	private java.lang.String evaUser;
   
	public void setEvaUser(java.lang.String evaUser){
		this.evaUser= evaUser;       
	}
   
	public java.lang.String getEvaUser(){
		return this.evaUser;
	}

	/**
	 *
	 * 不满意原因说明
	 *
	 */
	private java.lang.String unContent;
   
	public void setUnContent(java.lang.String unContent){
		this.unContent= unContent;       
	}
   
	public java.lang.String getUnContent(){
		return this.unContent;
	}

	/**
	 *
	 * 改进建议
	 *
	 */
	private java.lang.String propose;
   
	public void setPropose(java.lang.String propose){
		this.propose= propose;       
	}
   
	public java.lang.String getPropose(){
		return this.propose;
	}

	/**
	 *
	 * 是否删除
	 *
	 */
	private java.lang.String isDel;
   
	public void setIsDel(java.lang.String isDel){
		this.isDel= isDel;       
	}
   
	public java.lang.String getIsDel(){
		return this.isDel;
	}

	/**
	 *
	 * 评价人部门
	 *
	 */
	private java.lang.String evaDept;
   
	public void setEvaDept(java.lang.String evaDept){
		this.evaDept= evaDept;       
	}
   
	public java.lang.String getEvaDept(){
		return this.evaDept;
	}


	public boolean equals(Object o) {
		if( o instanceof PartnerEvaluation ) {
			PartnerEvaluation partnerEvaluation=(PartnerEvaluation)o;
			if (this.id != null || this.id.equals(partnerEvaluation.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}