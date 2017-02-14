package com.boco.eoms.partner.inspect.model;

import com.boco.eoms.base.model.BaseObject;


/**
 * 
 * Description:  巡检模板子项
 * Copyright:   Copyright (c)2009 
 * Company:     boco 
 * @author:     lee 
 * @version:    1.0 
 * Create at:   Jul 11, 2012 3:31:36 PM
 */

public class InspectTemplateItem extends BaseObject {

	private static final long serialVersionUID = 1L;

	/**
     *
     * 模版项主键
     *
     */
    java.lang.String id;


    /**
     *
     * 模版id
     *
     */
    java.lang.String templateId;


    /**
     *
     * 巡检项目
     *
     */
    java.lang.String inspectItem;

    /**
     *
     * 检查内容
     *
     */
    java.lang.String inspectContent;

    /**
     *
     * 输入方式
     *
     */
    java.lang.String inputType;

    /**
     *
     * 默认值
     *
     */
    java.lang.String defaultValue;
    /**
     *
     * 正常值范围
     *
     */
    java.lang.String normalRange;
    /**
     *
     * 关联数据字典
     *
     */
    java.lang.String dictId;
    /**
     *
     * 周期（参考）
     *
     */
    java.lang.Integer cycle;
    /**
     *
     * 添加人
     *
     */
    java.lang.String addUser;
    /**
     *
     * 添加时间
     *
     */
    java.lang.String addTime;
    /**
     *
     * 资源类别
     *
     */
    java.lang.String resType;
    private Integer status;           		//是否可用(0否 1是)
    
    private String bigitemName;		  		//模板大项名称
    private String bigitemId;         		//模板大项的ID
    private Integer orderNo;          		//排序号
    
    private String inspectMappingId;        //网络设备Id
    private Integer deviceInspectFlag;      //网络设备标识;1.网络设备,0.通用设备
    
	private Integer pictureFlag;      		//是否需要图片（0否、1是）
	private Integer pictureNum;       		//所需图片的数目(默认是0)
	private Integer pictureUploadNum;       //图片的数目(默认是0)
	private Integer pictureUploadFlag;		//图片上传标识（0.未上传完成，1，上传完成）

	public java.lang.String getResType() {
		return resType;
	}
	public void setResType(java.lang.String resType) {
		this.resType = resType;
	}
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(java.lang.String templateId) {
		this.templateId = templateId;
	}
	public java.lang.String getInspectItem() {
		return inspectItem;
	}
	public void setInspectItem(java.lang.String inspectItem) {
		this.inspectItem = inspectItem;
	}
	public java.lang.String getInspectContent() {
		return inspectContent;
	}
	public void setInspectContent(java.lang.String inspectContent) {
		this.inspectContent = inspectContent;
	}
	public java.lang.String getInputType() {
		return inputType;
	}
	public void setInputType(java.lang.String inputType) {
		this.inputType = inputType;
	}
	public java.lang.String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(java.lang.String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public java.lang.String getNormalRange() {
		return normalRange;
	}
	public void setNormalRange(java.lang.String normalRange) {
		this.normalRange = normalRange;
	}
	public java.lang.String getDictId() {
		return dictId;
	}
	public void setDictId(java.lang.String dictId) {
		this.dictId = dictId;
	}
	public java.lang.Integer getCycle() {
		return cycle;
	}
	public void setCycle(java.lang.Integer cycle) {
		this.cycle = cycle;
	}
	public java.lang.String getAddUser() {
		return addUser;
	}
	public void setAddUser(java.lang.String addUser) {
		this.addUser = addUser;
	}
	public java.lang.String getAddTime() {
		return addTime;
	}
	public void setAddTime(java.lang.String addTime) {
		this.addTime = addTime;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public boolean equals(Object o) {
        if( o instanceof InspectTemplateItem ) {
        	InspectTemplateItem inspectTemplateItem=(InspectTemplateItem)o;
            if (this.id != null || this.id.equals(inspectTemplateItem.getId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
	public String getBigitemName() {
		return bigitemName;
	}
	public void setBigitemName(String bigitemName) {
		this.bigitemName = bigitemName;
	}
	public String getBigitemId() {
		return bigitemId;
	}
	public void setBigitemId(String bigitemId) {
		this.bigitemId = bigitemId;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public String getInspectMappingId() {
		return inspectMappingId;
	}
	public void setInspectMappingId(String inspectMappingId) {
		this.inspectMappingId = inspectMappingId;
	}
	public Integer getDeviceInspectFlag() {
		return deviceInspectFlag;
	}
	public void setDeviceInspectFlag(Integer deviceInspectFlag) {
		this.deviceInspectFlag = deviceInspectFlag;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getPictureFlag() {
		return pictureFlag;
	}
	public void setPictureFlag(Integer pictureFlag) {
		this.pictureFlag = pictureFlag;
	}
	public Integer getPictureNum() {
		return pictureNum;
	}
	public void setPictureNum(Integer pictureNum) {
		this.pictureNum = pictureNum;
	}
	public Integer getPictureUploadNum() {
		return pictureUploadNum;
	}
	public void setPictureUploadNum(Integer pictureUploadNum) {
		this.pictureUploadNum = pictureUploadNum;
	}
	public Integer getPictureUploadFlag() {
		return pictureUploadFlag;
	}
	public void setPictureUploadFlag(Integer pictureUploadFlag) {
		this.pictureUploadFlag = pictureUploadFlag;
	}
	
}