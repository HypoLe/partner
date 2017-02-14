package com.boco.eoms.partner.management.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:管理成本
 * </p>
 * <p>
 * Description:管理成本
 * </p>
 * <p>
 * Wed Mar 28 09:29:15 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public class Management extends BaseObject {
	
	private String createUser;//创建人
    
    private Date createTime;//创建时间
    
    private Integer isDeleted; //删除标记 1：删除 0：未删除
    

    /**
     *
     * 主键
     *
     */
    java.lang.String id;

    public void setId(java.lang.String id){
        this.id= id;       
    }

    public java.lang.String getId(){
        return this.id;
    }

    /**
     *
     * 合作伙伴ID
     *
     */
    java.lang.String partnerId;

    public void setPartnerId(java.lang.String partnerId){
        this.partnerId= partnerId;       
    }

    public java.lang.String getPartnerId(){
        return this.partnerId;
    }

    /**
     *
     * 人员规模
     *
     */
    java.lang.String partnerNum;

    public void setPartnerNum(java.lang.String partnerNum){
        this.partnerNum= partnerNum;       
    }

    public java.lang.String getPartnerNum(){
        return this.partnerNum;
    }

    /**
     *
     * 成本开资费用
     *
     */
    java.lang.String expenseCost;

    public void setExpenseCost(java.lang.String expenseCost){
        this.expenseCost= expenseCost;       
    }

    public java.lang.String getExpenseCost(){
        return this.expenseCost;
    }

    /**
     *
     * 开资统计开始时间
     *
     */
    java.lang.String beginCostTime;

    public void setBeginCostTime(java.lang.String beginCostTime){
        this.beginCostTime= beginCostTime;       
    }

    public java.lang.String getBeginCostTime(){
        return this.beginCostTime;
    }

    /**
     *
     * 开资统计结束时间
     *
     */
    java.lang.String endCostTime;

    public void setEndCostTime(java.lang.String endCostTime){
        this.endCostTime= endCostTime;       
    }

    public java.lang.String getEndCostTime(){
        return this.endCostTime;
    }

    /**
     *
     * 备注
     *
     */
    java.lang.String remark;

    public void setRemark(java.lang.String remark){
        this.remark= remark;       
    }

    public java.lang.String getRemark(){
        return this.remark;
    }


    public boolean equals(Object o) {
        if( o instanceof Management ) {
            Management management=(Management)o;
            if (this.id != null || this.id.equals(management.getId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
}