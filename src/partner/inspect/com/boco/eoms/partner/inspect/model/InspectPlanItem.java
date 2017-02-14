package com.boco.eoms.partner.inspect.model;

import java.io.Serializable;
import java.util.Date;

import java.sql.Timestamp;

/**
 * 
 * Description: 巡检项 Company: boco
 * 
 * @author: lee
 * @version: 1.0 Create at: Jun 22, 2012 3:31:36 PM
 */
public class InspectPlanItem implements Serializable {
	private static final long serialVersionUID = 1L;
	Integer id;
	/**
	 * 所属巡检计划id
	 */
	String planId;
	/**
	 * 所属巡检计划资源id
	 */
	Long planResId;
	/**
	 * 资源类别
	 */
	String resType;

	/**
	 * 巡检项
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
	 * 周期
	 */
	String inspectCycle;
	/**
	 * 巡检项巡检结果
	 */
	String itemResult;
	/**
	 * 结果数据字典选项
	 */
	String dictResult;
	/**
	 * 保存时间
	 */
	Date saveTime;
	/**
	 * 异常标识
	 */
	Integer exceptionFlag; // 0异常,1正常 -1无设备
	/**
	 * 所属资源所在地市（以便该表能够按地市进行分片提高查询效率）
	 */
	private String city;

	private Integer hasPicture; // 是否有图片（0有图片，1无图片）
	private Integer hasAudio; // 是否有音频（0有，1无）
	private String exceptionContent; // 异常内容
	private String templateItemId; // 巡检模板项ID
	private String bigitemName; // 巡检大项名称
	private String bigitemId; // 巡检大项的ID

	private String inspectTaskLinkId; // PnrInspectTaskLink中的主键id
	private Integer deviceInspectFlag; // 设备巡检项标识

	private Integer pictureFlag; // 是否需要图片（0否、1是）
	private Integer pictureNum; // 所需图片的数目(默认是0)
	private Integer pictureUploadNum; // 图片的数目(默认是0)
	private Integer pictureUploadFlag; // 图片上传标识（0.未上传完成，1，上传完成）
	/**
	 * 是否处理
	 */
	private Integer isHandle;
	/**
	 * 时间处理
	 */
	private Timestamp handleTimestamp;
	/**
	 * 处理人员ID
	 */
	private String handleUser;

	public Integer getHasAudio() {
		return hasAudio;
	}

	public void setHasAudio(Integer hasAudio) {
		this.hasAudio = hasAudio;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public Long getPlanResId() {
		return planResId;
	}

	public void setPlanResId(Long planResId) {
		this.planResId = planResId;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getInspectItem() {
		return inspectItem;
	}

	public void setInspectItem(String inspectItem) {
		this.inspectItem = inspectItem;
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

	public String getInspectCycle() {
		return inspectCycle;
	}

	public void setInspectCycle(String inspectCycle) {
		this.inspectCycle = inspectCycle;
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

	public Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	public Integer getExceptionFlag() {
		return exceptionFlag;
	}

	public void setExceptionFlag(Integer exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
	}

	public String getExceptionContent() {
		return exceptionContent;
	}

	public void setExceptionContent(String exceptionContent) {
		this.exceptionContent = exceptionContent;
	}

	public String getTemplateItemId() {
		return templateItemId;
	}

	public void setTemplateItemId(String templateItemId) {
		this.templateItemId = templateItemId;
	}

	public Integer getHasPicture() {
		return hasPicture;
	}

	public void setHasPicture(Integer hasPicture) {
		this.hasPicture = hasPicture;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getInspectTaskLinkId() {
		return inspectTaskLinkId;
	}

	public void setInspectTaskLinkId(String inspectTaskLinkId) {
		this.inspectTaskLinkId = inspectTaskLinkId;
	}

	public Integer getDeviceInspectFlag() {
		return deviceInspectFlag;
	}

	public void setDeviceInspectFlag(Integer deviceInspectFlag) {
		this.deviceInspectFlag = deviceInspectFlag;
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

	public Integer getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(Integer isHandle) {
		this.isHandle = isHandle;
	}

	public Timestamp getHandleTimestamp() {
		return handleTimestamp;
	}

	public void setHandleTimestamp(Timestamp handleTimestamp) {
		this.handleTimestamp = handleTimestamp;
	}

	public String getHandleUser() {
		return handleUser;
	}

	public void setHandleUser(String handleUser) {
		this.handleUser = handleUser;
	}

}
