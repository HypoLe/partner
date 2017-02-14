package com.boco.eoms.netresource.asset.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:资产信息管理
 * </p>
 * <p>
 * Description:资产信息管理
 * </p>
 * <p>
 * Thu Mar 08 09:48:46 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public class Asset extends BaseObject {

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
     * 创建人
     *
     */
    java.lang.String createUser;

    public void setCreateUser(java.lang.String createUser){
        this.createUser= createUser;       
    }

    public java.lang.String getCreateUser(){
        return this.createUser;
    }

    /**
     *
     * 创建时间
     *
     */
    java.util.Date createTime;

    public void setCreateTime(java.util.Date createTime){
        this.createTime= createTime;       
    }

    public java.util.Date getCreateTime(){
        return this.createTime;
    }

    /**
     *
     * 资产名称
     *
     */
    java.lang.String assetName;

    public void setAssetName(java.lang.String assetName){
        this.assetName= assetName;       
    }

    public java.lang.String getAssetName(){
        return this.assetName;
    }

    /**
     *
     * 资产类型
     *
     */
    java.lang.String assetType;

    public void setAssetType(java.lang.String assetType){
        this.assetType= assetType;       
    }

    public java.lang.String getAssetType(){
        return this.assetType;
    }

    /**
     *
     * 资产规格型号
     *
     */
    java.lang.String assetModel;

    public void setAssetModel(java.lang.String assetModel){
        this.assetModel= assetModel;       
    }

    public java.lang.String getAssetModel(){
        return this.assetModel;
    }

    /**
     *
     * 资产启用时间
     *
     */
    private String assetUseTime;

    public void setAssetUseTime(String assetUseTime){
        this.assetUseTime= assetUseTime;       
    }

    public String getAssetUseTime(){
        return this.assetUseTime;
    }

    /**
     *
     * 资产条形码
     *
     */
    java.lang.String assetBarCode;

    public void setAssetBarCode(java.lang.String assetBarCode){
        this.assetBarCode= assetBarCode;       
    }

    public java.lang.String getAssetBarCode(){
        return this.assetBarCode;
    }

    /**
     *
     * 资产地点标签
     *
     */
    java.lang.String assetSitusTag;

    public void setAssetSitusTag(java.lang.String assetSitusTag){
        this.assetSitusTag= assetSitusTag;       
    }

    public java.lang.String getAssetSitusTag(){
        return this.assetSitusTag;
    }

    /**
     *
     * 分布物理位置
     *
     */
    java.lang.String assetLocation;

    public void setAssetLocation(java.lang.String assetLocation){
        this.assetLocation= assetLocation;       
    }

    public java.lang.String getAssetLocation(){
        return this.assetLocation;
    }

    /**
     *
     * 资产备注
     *
     */
    java.lang.String assetRemark;

    public void setAssetRemark(java.lang.String assetRemark){
        this.assetRemark= assetRemark;       
    }

    public java.lang.String getAssetRemark(){
        return this.assetRemark;
    }

    /**
     *
     * 删除标记
     *
     */
    Integer isDeleted;

    public void setIsDeleted(Integer isDeleted){
        this.isDeleted= isDeleted;       
    }

    public Integer getIsDeleted(){
        return this.isDeleted;
    }

	@Override
	public boolean equals(Object o) {
		return false;
	}

}