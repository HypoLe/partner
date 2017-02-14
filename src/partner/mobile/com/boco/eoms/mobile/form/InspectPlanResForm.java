package com.boco.eoms.mobile.form;

/** 
 * Description: 巡检主体 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     LEE 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 5:22:14 PM 
 */
public class InspectPlanResForm {
	private Long id;              
	private String planId;        //所属计划
	private String resCfgId;      //资源配置ID
	private String specialty;     //专业
	private String specialtyName;     //专业
	private String resType;       //资源类型
	private String resTypeName;       //资源类型
	private String resName;       //资源名称
	private String resLevel;      //资源级别
	private String resLevelName;      //资源级别
	private String city;          //地市
	private String cityName;          //地市
	private String country;       //区县
	private String countryName;       //区县
	private String inspectCycle;  //巡检周期
	private String cycleStartTime;  //巡检周期开始时间
	private String cycleEndTime;    //巡检周期结束时间
	private String planStartTime;   //计划巡检开始时间
	private String planEndTime;     //计划巡检结束时间
	private String executeObject; //执行对象
	private String executeDept;   //执行对象部门
	private String executeDeptName;   //执行对象部门
	private String executeType;   //执行对象类型
	private String inspectUser;   //巡检员
	private String createTime;    //创建时间
	private String creator;       //创建人
	private String changeState;  //是否变更中(0否 1是)
	private String planChangeId;  //计划变更ID
	private String forceAssign;  //是否强制关联计划(0否 1是)
	private String resLongitude;  //经度
	private String resLatitude;   //纬度
	private String filePath;      //附件地址
	private String inspectTime;     //巡检完成时间
	private String inspectState; //巡检状态(0待完成 1已完成)
	private String exceptionFlag;//巡检异常标识(0异常 1正常)
	private String timeOnSite;     //在站时长
	private String itemNum;      //巡检项数
	private String itemDoneNum;  //已完成巡检项数
	
	private String signLongitude;  //定位经度
	private String signLatitude;   //定位纬度
	private String lastInspectTime;
	private String signTime;        //巡检签到时间
	private String burstFlag;       //突发标志(0常规 1突发)
	
	
	
	
	
	
	/*以下字段用于线路巡检独有 begin tl(transLine简写)*/
	/**线路巡检标识 1为线路巡检 为0为以前的数据 在数据库中增加默认约束 用些字段区分是否线路巡检资源类型*/
	private String tlInspectFlag;
	private String tlDis;
	private String tlWire;
	private String tlSystemLevel;
	private String tlSeg;
	private String tlLayType;
	private String tlLength;
	private String tlFiberCount;
	private String tlPAName;
	private String tlPALo;
	private String tlPALa;
	private String tlPZName;
	private String tlPZLo;
	private String tlPZLa;
	
	private String arrivedRateId;
	private String locationCycleId;
	/**标准到点率*/
	private String tlArrivedRate;
	/**实际到点率 是否完成要根据此来判断 即更改inspectState该字段的标准要用这到这个字段*/
	private String tlRealArrivedRate;
	/**巡检执行方式*/
	private String tlExecuteType;
	/**自动上报位置信息时间间隔*/
	private String tlReportInterval;
	/*以下字段用于线路巡检独有 end*/
	
	
	
	
	public String getLastInspectTime() {
		return lastInspectTime;
	}
	public void setLastInspectTime(String lastInspectTime) {
		this.lastInspectTime = lastInspectTime;
	}
	public String getSignLatitude() {
		return signLatitude;
	}
	public void setSignLatitude(String signLatitude) {
		this.signLatitude = signLatitude;
	}
	public String getSignLongitude() {
		return signLongitude;
	}
	public void setSignLongitude(String signLongitude) {
		this.signLongitude = signLongitude;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getResCfgId() {
		return resCfgId;
	}
	public void setResCfgId(String resCfgId) {
		this.resCfgId = resCfgId;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResLevel() {
		return resLevel;
	}
	public void setResLevel(String resLevel) {
		this.resLevel = resLevel;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getInspectCycle() {
		return inspectCycle;
	}
	public void setInspectCycle(String inspectCycle) {
		this.inspectCycle = inspectCycle;
	}
	public String getCycleStartTime() {
		return cycleStartTime;
	}
	public void setCycleStartTime(String cycleStartTime) {
		this.cycleStartTime = cycleStartTime;
	}
	public String getCycleEndTime() {
		return cycleEndTime;
	}
	public void setCycleEndTime(String cycleEndTime) {
		this.cycleEndTime = cycleEndTime;
	}
	public String getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime;
	}
	public String getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(String planEndTime) {
		this.planEndTime = planEndTime;
	}
	public String getExecuteObject() {
		return executeObject;
	}
	public void setExecuteObject(String executeObject) {
		this.executeObject = executeObject;
	}
	public String getExecuteDept() {
		return executeDept;
	}
	public void setExecuteDept(String executeDept) {
		this.executeDept = executeDept;
	}
	public String getExecuteType() {
		return executeType;
	}
	public void setExecuteType(String executeType) {
		this.executeType = executeType;
	}
	public String getInspectUser() {
		return inspectUser;
	}
	public void setInspectUser(String inspectUser) {
		this.inspectUser = inspectUser;
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
	public String getChangeState() {
		return changeState;
	}
	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}
	public String getPlanChangeId() {
		return planChangeId;
	}
	public void setPlanChangeId(String planChangeId) {
		this.planChangeId = planChangeId;
	}
	public String getForceAssign() {
		return forceAssign;
	}
	public void setForceAssign(String forceAssign) {
		this.forceAssign = forceAssign;
	}
	public String getResLongitude() {
		return resLongitude;
	}
	public void setResLongitude(String resLongitude) {
		this.resLongitude = resLongitude;
	}
	public String getResLatitude() {
		return resLatitude;
	}
	public void setResLatitude(String resLatitude) {
		this.resLatitude = resLatitude;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getInspectTime() {
		return inspectTime;
	}
	public void setInspectTime(String inspectTime) {
		this.inspectTime = inspectTime;
	}
	public String getInspectState() {
		return inspectState;
	}
	public void setInspectState(String inspectState) {
		this.inspectState = inspectState;
	}
	public String getExceptionFlag() {
		return exceptionFlag;
	}
	public void setExceptionFlag(String exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
	}
	public String getTimeOnSite() {
		return timeOnSite;
	}
	public void setTimeOnSite(String timeOnSite) {
		this.timeOnSite = timeOnSite;
	}
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}
	public String getItemDoneNum() {
		return itemDoneNum;
	}
	public void setItemDoneNum(String itemDoneNum) {
		this.itemDoneNum = itemDoneNum;
	}
	public String getArrivedRateId() {
		return arrivedRateId;
	}
	public void setArrivedRateId(String arrivedRateId) {
		this.arrivedRateId = arrivedRateId;
	}
	public String getLocationCycleId() {
		return locationCycleId;
	}
	public void setLocationCycleId(String locationCycleId) {
		this.locationCycleId = locationCycleId;
	}
	public String getTlArrivedRate() {
		return tlArrivedRate;
	}
	public void setTlArrivedRate(String tlArrivedRate) {
		this.tlArrivedRate = tlArrivedRate;
	}
	public String getTlDis() {
		return tlDis;
	}
	public void setTlDis(String tlDis) {
		this.tlDis = tlDis;
	}
	public String getTlExecuteType() {
		return tlExecuteType;
	}
	public void setTlExecuteType(String tlExecuteType) {
		this.tlExecuteType = tlExecuteType;
	}
	public String getTlFiberCount() {
		return tlFiberCount;
	}
	public void setTlFiberCount(String tlFiberCount) {
		this.tlFiberCount = tlFiberCount;
	}
	public String getTlInspectFlag() {
		return tlInspectFlag;
	}
	public void setTlInspectFlag(String tlInspectFlag) {
		this.tlInspectFlag = tlInspectFlag;
	}
	public String getTlLayType() {
		return tlLayType;
	}
	public void setTlLayType(String tlLayType) {
		this.tlLayType = tlLayType;
	}
	public String getTlLength() {
		return tlLength;
	}
	public void setTlLength(String tlLength) {
		this.tlLength = tlLength;
	}
	public String getTlPALa() {
		return tlPALa;
	}
	public void setTlPALa(String tlPALa) {
		this.tlPALa = tlPALa;
	}
	public String getTlPALo() {
		return tlPALo;
	}
	public void setTlPALo(String tlPALo) {
		this.tlPALo = tlPALo;
	}
	public String getTlPAName() {
		return tlPAName;
	}
	public void setTlPAName(String tlPAName) {
		this.tlPAName = tlPAName;
	}
	public String getTlPZLa() {
		return tlPZLa;
	}
	public void setTlPZLa(String tlPZLa) {
		this.tlPZLa = tlPZLa;
	}
	public String getTlPZLo() {
		return tlPZLo;
	}
	public void setTlPZLo(String tlPZLo) {
		this.tlPZLo = tlPZLo;
	}
	public String getTlPZName() {
		return tlPZName;
	}
	public void setTlPZName(String tlPZName) {
		this.tlPZName = tlPZName;
	}
	public String getTlRealArrivedRate() {
		return tlRealArrivedRate;
	}
	public void setTlRealArrivedRate(String tlRealArrivedRate) {
		this.tlRealArrivedRate = tlRealArrivedRate;
	}
	public String getTlReportInterval() {
		return tlReportInterval;
	}
	public void setTlReportInterval(String tlReportInterval) {
		this.tlReportInterval = tlReportInterval;
	}
	public String getTlSeg() {
		return tlSeg;
	}
	public void setTlSeg(String tlSeg) {
		this.tlSeg = tlSeg;
	}
	public String getTlSystemLevel() {
		return tlSystemLevel;
	}
	public void setTlSystemLevel(String tlSystemLevel) {
		this.tlSystemLevel = tlSystemLevel;
	}
	public String getTlWire() {
		return tlWire;
	}
	public void setTlWire(String tlWire) {
		this.tlWire = tlWire;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getExecuteDeptName() {
		return executeDeptName;
	}
	public void setExecuteDeptName(String executeDeptName) {
		this.executeDeptName = executeDeptName;
	}
	public String getResLevelName() {
		return resLevelName;
	}
	public void setResLevelName(String resLevelName) {
		this.resLevelName = resLevelName;
	}
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	public String getBurstFlag() {
		return burstFlag;
	}
	public void setBurstFlag(String burstFlag) {
		this.burstFlag = burstFlag;
	}
	
}
