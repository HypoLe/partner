package com.boco.eoms.netresource.point.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:标点信息管理
 * </p>
 * <p>
 * Description:标点信息管理
 * </p>
 * <p>
 * Thu Feb 16 18:22:16 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public class PointsForm extends BaseForm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
	

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
     * 标点名称
     *
     */
    java.lang.String pointName;

    public void setPointName(java.lang.String pointName){
        this.pointName= pointName;       
    }

    public java.lang.String getPointName(){
        return this.pointName;
    }

    /**
     *
     * 标点编号
     *
     */
    java.lang.String pointNo;

    public void setPointNo(java.lang.String pointNo){
        this.pointNo= pointNo;       
    }

    public java.lang.String getPointNo(){
        return this.pointNo;
    }

    /**
     * 所属地市
     */
    private String region;
    
    /**
     *
     * 所属区县
     *
     */
    java.lang.String city;

    public void setCity(java.lang.String city){
        this.city= city;       
    }

    public java.lang.String getCity(){
        return this.city;
    }

    /**
     *
     * 所属网格
     *
     */
    java.lang.String grid;

    public void setGrid(java.lang.String grid){
        this.grid= grid;       
    }

    public java.lang.String getGrid(){
        return this.grid;
    }

    /**
     *
     * 所属线路
     *
     */
    java.lang.String line;

    public void setLine(java.lang.String line){
        this.line= line;       
    }

    public java.lang.String getLine(){
        return this.line;
    }

    /**
     *
     * 专业类型
     *
     */
    java.lang.String specialtyType;

    public void setSpecialtyType(java.lang.String specialtyType){
        this.specialtyType= specialtyType;       
    }

    public java.lang.String getSpecialtyType(){
        return this.specialtyType;
    }


    /**
     *
     * 承载业务
     *
     */
    java.lang.String loadwork;

    public void setLoadwork(java.lang.String loadwork){
        this.loadwork= loadwork;       
    }

    public java.lang.String getLoadwork(){
        return this.loadwork;
    }

    /**
     *
     * 所属集团客户
     *
     */
    java.lang.String groupUser;

    public void setGroupUser(java.lang.String groupUser){
        this.groupUser= groupUser;       
    }

    public java.lang.String getGroupUser(){
        return this.groupUser;
    }

    /**
     *
     * 所属集团客户编码
     *
     */
    java.lang.String groupUserNo;

    public void setGroupUserNo(java.lang.String groupUserNo){
        this.groupUserNo= groupUserNo;       
    }

    public java.lang.String getGroupUserNo(){
        return this.groupUserNo;
    }

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * 
	 * 删除标记  0：未删除 1：删除
	 * 
	 */
	private String isdeleted;
	
	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}	
	
	/**
	 * 
	 * 创建时间
	 * 
	 */
	private String createTime;
	
	/**
	 * 
	 * 创建人
	 * 
	 */
	private String creator;
	
	/**
	 * 
	 * 误差范围
	 * 
	 */
	private String errorScope;
	
	public String getErrorScope() {
		return errorScope;
	}

	public void setErrorScope(String errorScope) {
		this.errorScope = errorScope;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	/**
	 * 合作伙伴
	 */
	private String partner;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}
	

}