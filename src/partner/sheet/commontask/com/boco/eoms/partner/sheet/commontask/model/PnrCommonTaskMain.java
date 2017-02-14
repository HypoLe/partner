package com.boco.eoms.partner.sheet.commontask.model;

import com.boco.eoms.sheet.base.model.BaseMain;

/** 
 * Description: 通用任务工单MAIN
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 23, 2012 3:26:12 PM 
 */
 public class PnrCommonTaskMain extends BaseMain {
	
	private static final long serialVersionUID = 1L;

	/** 专业类型 */
	private String mainSpecialty;
	/** 任务类型 */
	private String mainTaskType;
	/** 地市 */
	private String mainCity;
	/** 区县 */
	private String mainCountry;
	/** 资源ID */
	private String mainResId;
	/** 资源编码 */
	private String mainResCode;
	/** 资源名称 */
	private String mainResName;
	/** 代维任务地点 */
	private String mainLocation;
	/** 回复是否需要附件 0否1是 */
	private String mainFileNeeded;
	/** 代维任务描述 */
	private String mainRemark;
	/** 派往对象 */
	private String mainSendObject;
	/** 单人最长总耗时(最晚完成时间-派单时间) */
	private Long mainSingleTotalTime;
	/** 多人总耗时(完成时间-派单时间) */
	private Long mainMultiTotalTime;
	/** 超时时间(完成时间-完成时限) */
	private Long mainTimeOut;
	/** 是否超时 */
	private Integer mainOverTimeFlag;
	/** 归档回退次数 */
	private Integer mainBackTime;
	
	/** 是否计费 */
	private String mainIfcharging;
	
	/** 区域类型 */
	private String mainRegiontype; 
	
	/** 地理环境 */
	private String mainEnvironment;
	
	public String getMainSpecialty() {
		return mainSpecialty;
	}
	public void setMainSpecialty(String mainSpecialty) {
		this.mainSpecialty = mainSpecialty;
	}
	public String getMainTaskType() {
		return mainTaskType;
	}
	public void setMainTaskType(String mainTaskType) {
		this.mainTaskType = mainTaskType;
	}
	public String getMainCity() {
		return mainCity;
	}
	public void setMainCity(String mainCity) {
		this.mainCity = mainCity;
	}
	public String getMainCountry() {
		return mainCountry;
	}
	public void setMainCountry(String mainCountry) {
		this.mainCountry = mainCountry;
	}
	public String getMainResId() {
		return mainResId;
	}
	public void setMainResId(String mainResId) {
		this.mainResId = mainResId;
	}
	public String getMainResCode() {
		return mainResCode;
	}
	public void setMainResCode(String mainResCode) {
		this.mainResCode = mainResCode;
	}
	public String getMainResName() {
		return mainResName;
	}
	public void setMainResName(String mainResName) {
		this.mainResName = mainResName;
	}
	public String getMainLocation() {
		return mainLocation;
	}
	public void setMainLocation(String mainLocation) {
		this.mainLocation = mainLocation;
	}
	public String getMainFileNeeded() {
		return mainFileNeeded;
	}
	public void setMainFileNeeded(String mainFileNeeded) {
		this.mainFileNeeded = mainFileNeeded;
	}
	public String getMainRemark() {
		return mainRemark;
	}
	public void setMainRemark(String mainRemark) {
		this.mainRemark = mainRemark;
	}
	public String getMainSendObject() {
		return mainSendObject;
	}
	public void setMainSendObject(String mainSendObject) {
		this.mainSendObject = mainSendObject;
	}
	public Long getMainSingleTotalTime() {
		return mainSingleTotalTime;
	}
	public void setMainSingleTotalTime(Long mainSingleTotalTime) {
		this.mainSingleTotalTime = mainSingleTotalTime;
	}
	public Long getMainMultiTotalTime() {
		return mainMultiTotalTime;
	}
	public void setMainMultiTotalTime(Long mainMultiTotalTime) {
		this.mainMultiTotalTime = mainMultiTotalTime;
	}
	public Long getMainTimeOut() {
		return mainTimeOut;
	}
	public void setMainTimeOut(Long mainTimeOut) {
		this.mainTimeOut = mainTimeOut;
	}
	public Integer getMainOverTimeFlag() {
		return mainOverTimeFlag;
	}
	public void setMainOverTimeFlag(Integer mainOverTimeFlag) {
		this.mainOverTimeFlag = mainOverTimeFlag;
	}
	public Integer getMainBackTime() {
		return mainBackTime;
	}
	public void setMainBackTime(Integer mainBackTime) {
		this.mainBackTime = mainBackTime;
	}
	public String getMainIfcharging() {
		return mainIfcharging;
	}
	public void setMainIfcharging(String mainIfcharging) {
		this.mainIfcharging = mainIfcharging;
	}
	public String getMainRegiontype() {
		return mainRegiontype;
	}
	public void setMainRegiontype(String mainRegiontype) {
		this.mainRegiontype = mainRegiontype;
	}
	public String getMainEnvironment() {
		return mainEnvironment;
	}
	public void setMainEnvironment(String mainEnvironment) {
		this.mainEnvironment = mainEnvironment;
	}
	
	
 }