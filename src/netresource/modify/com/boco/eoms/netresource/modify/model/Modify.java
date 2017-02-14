package com.boco.eoms.netresource.modify.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:资源变更管理
 * </p>
 * <p>
 * Description:资源变更管理
 * </p>
 * <p>
 * Tue Feb 21 11:40:03 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public class Modify extends BaseObject {

    /**
     *
     * 主键
     *
     */
	private java.lang.String id;

    public void setId(java.lang.String id){
        this.id= id;       
    }

    public java.lang.String getId(){
        return this.id;
    }

    /**
     *
     * 申请人
     *
     */
    private java.lang.String applyUser;

    public void setApplyUser(java.lang.String applyUser){
        this.applyUser= applyUser;       
    }

    public java.lang.String getApplyUser(){
        return this.applyUser;
    }

    /**
     *
     * 申请人部门
     *
     */
    private java.lang.String applyDept;

    public void setApplyDept(java.lang.String applyDept){
        this.applyDept= applyDept;       
    }

    public java.lang.String getApplyDept(){
        return this.applyDept;
    }

    /**
     *
     * 申请时间
     *
     */
    private Date applyTime;

    public void setApplyTime(Date applyTime){
        this.applyTime= applyTime;       
    }

    public Date getApplyTime(){
        return this.applyTime;
    }

    /**
     *
     * 申请描述
     *
     */
    private java.lang.String description;

    public void setDescription(java.lang.String description){
        this.description= description;       
    }

    public java.lang.String getDescription(){
        return this.description;
    }

    /**
     *
     * 审批人
     *
     */
    private java.lang.String approveUser;

    public void setApproveUser(java.lang.String approveUser){
        this.approveUser= approveUser;       
    }

    public java.lang.String getApproveUser(){
        return this.approveUser;
    }

    /**
     *
     * 受理时间
     *
     */
    private Date acceptTime;

    public void setAcceptTime(Date acceptTime){
        this.acceptTime= acceptTime;       
    }

    public Date getAcceptTime(){
        return this.acceptTime;
    }

    /**
     *
     * 审批时间
     *
     */
    private Date approveTime;

    public void setApproveTime(Date approveTime){
        this.approveTime= approveTime;       
    }

    public Date getApproveTime(){
        return this.approveTime;
    }

    /**
     *
     * 审批状态
     *
     */
    private String approveStatus;

    public void setApproveStatus(String approveStatus){
        this.approveStatus= approveStatus;       
    }

    public String getApproveStatus(){
        return this.approveStatus;
    }

    /**
     *
     * 误差范围
     *
     */
    private java.lang.String errorScope;

    public void setErrorScope(java.lang.String errorScope){
        this.errorScope= errorScope;       
    }

    public java.lang.String getErrorScope(){
        return this.errorScope;
    }

    /**
     *
     * 变更类型
     *
     */
    private String modifyType;

    public void setModifyType(String modifyType){
        this.modifyType= modifyType;       
    }

    public String getModifyType(){
        return this.modifyType;
    }

    /**
     *
     * 资源类型
     *
     */
    private String resourceType;

    public void setResourceType(String resourceType){
        this.resourceType= resourceType;       
    }

    public String getResourceType(){
        return this.resourceType;
    }

    /**
     *
     * 变更资源ID
     *
     */
    private java.lang.String resourceId;

    public void setResourceId(java.lang.String resourceId){
        this.resourceId= resourceId;       
    }

    public java.lang.String getResourceId(){
        return this.resourceId;
    }

    /**
     *
     * 删除标记
     *
     */
    private Integer isDeleted;

    public void setIsDeleted(Integer isDeleted){
        this.isDeleted= isDeleted;       
    }

    public Integer getIsDeleted(){
        return this.isDeleted;
    }


    public boolean equals(Object o) {
        if( o instanceof Modify ) {
            Modify modify=(Modify)o;
            if (this.id != null || this.id.equals(modify.getId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}