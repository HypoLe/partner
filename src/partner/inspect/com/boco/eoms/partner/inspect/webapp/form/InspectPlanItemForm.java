package com.boco.eoms.partner.inspect.webapp.form;

import java.io.Serializable;


public class InspectPlanItemForm implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	String id;
	/**
	 * 所属巡检计划id
	 */
	String planId;
	/**
	 * 所属巡检计划资源id
	 */
	String planResId;
	/**
	 * 设备类别
	 */
	String resType;
	/**
	 * 巡检项目
	 */
	String inspectItem;
	/**
	 * 巡检内容
	 */
	String content;
	/**
	 * 输入方式
	 */
	String inputType;
	/**
	 * 默认值
	 */
	String defaultValue;
	/**
	 * 正常值范围
	 */
	String normalRange;
	/**
	 * 关联数据字典
	 */
	String dictId;
	/**
	 * 巡检项巡检结果
	 */
	String itemResult;
	/**
	 * 结果数据字典选项
	 */
	String dictResult;
	
	String dictValue;//手机终端保存离线数据的时候,需要前提查出该字典下的字典ID和字典值  id:1;name:是|id:2;name:否|
	
	String isOverDue;//手机终端保存离线数据的时候,判断该巡检项是否已经过期
	/**
	 * 异常标识
	 */
	String exceptionFlag;
	/**
	 * 保存时间
	 */
	String saveTime;
	/**
	 * 结束时间(计划规定的最后时间限制)
	 */
	String endTime;
	
	private String exceptionContent; //异常内容
	private Integer hasPicture;   //是否有图片（0有图片，1无图片）
	private Integer hasAudio;       //是否有音频（0有，1无）
	private String bigitemName;		  //巡检大项名称
    private String bigitemId;         //巡检大项的ID
    private String deviceInspectFlag;         //巡检大项的ID
    private String inspectTaskLinkId;        
	
    
	private String md5Result;
	private String md5ExceptionContent;
	private String md5ExceptionFlag;
	
	private Integer pictureFlag;      		//是否需要图片（0否、1是）
	private Integer pictureNum;       		//所需图片的数目(默认是0)
	private Integer pictureUploadNum;       //图片的数目(默认是0)
	private Integer pictureUploadFlag;		//图片上传标识（0.未上传完成，1，上传完成）
	
	
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
	public String getDeviceInspectFlag() {
		return deviceInspectFlag;
	}
	public void setDeviceInspectFlag(String deviceInspectFlag) {
		this.deviceInspectFlag = deviceInspectFlag;
	}
	public Integer getHasAudio() {
		return hasAudio;
	}
	public void setHasAudio(Integer hasAudio) {
		this.hasAudio = hasAudio;
	}
	public String getMd5ExceptionContent() {
		return md5ExceptionContent;
	}
	public void setMd5ExceptionContent(String md5ExceptionContent) {
		this.md5ExceptionContent = md5ExceptionContent;
	}
	public String getMd5ExceptionFlag() {
		return md5ExceptionFlag;
	}
	public void setMd5ExceptionFlag(String md5ExceptionFlag) {
		this.md5ExceptionFlag = md5ExceptionFlag;
	}
    
    
	public String getMd5Result() {
		return md5Result;
	}
	public void setMd5Result(String md5Result) {
		this.md5Result = md5Result;
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
	public Integer getHasPicture() {
		return hasPicture;
	}
	public void setHasPicture(Integer hasPicture) {
		this.hasPicture = hasPicture;
	}
	public String getExceptionContent() {
		return exceptionContent;
	}
	public void setExceptionContent(String exceptionContent) {
		this.exceptionContent = exceptionContent;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanResId() {
		return planResId;
	}
	public void setPlanResId(String planResId) {
		this.planResId = planResId;
	}
	
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getNormalRange() {
		return normalRange;
	}
	public void setNormalRange(String normalRange) {
		this.normalRange = normalRange;
	}
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	public String getItemResult() {
		return itemResult;
	}
	public void setItemResult(String itemResult) {
		this.itemResult = itemResult;
	}
	public String getDictResult() {
		return dictResult;
	}
	public void setDictResult(String dictResult) {
		this.dictResult = dictResult;
	}
	public String getExceptionFlag() {
		return exceptionFlag;
	}
	public void setExceptionFlag(String exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
	}
	public String getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}
	public String getInspectItem() {
		return inspectItem;
	}
	public void setInspectItem(String inspectItem) {
		this.inspectItem = inspectItem;
	}
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	public String getIsOverDue() {
		return isOverDue;
	}
	public void setIsOverDue(String isOverDue) {
		this.isOverDue = isOverDue;
	}
	public String getInspectTaskLinkId() {
		return inspectTaskLinkId;
	}
	public void setInspectTaskLinkId(String inspectTaskLinkId) {
		this.inspectTaskLinkId = inspectTaskLinkId;
	}
	
	
	
	
}
