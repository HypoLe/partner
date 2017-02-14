package com.boco.eoms.partner.inspect.model;

import java.util.Date;

/** 
 * Description: 巡检计划资源(元任务)
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 15, 2012 9:43:43 PM 
 */
public class InspectPlanRes {
	private Long id;              
	private String planId;        //所属计划
	private String templateId;    //巡检模板ID
	private String resCfgId;      //资源配置ID
	private String specialty;     //专业
	private String resType;       //资源类型
	private String resName;       //资源名称
	private String resLevel;      //资源级别
	private String city;          //地市
	private String country;       //区县
	private String eographicalEnvironment;     //地理环境
	private String regionType;    //区域类型
	private String inspectCycle;  //巡检周期
	private Date cycleStartTime;  //巡检周期开始时间
	private Date cycleEndTime;    //巡检周期结束时间
	private Date planStartTime;   //计划巡检开始时间
	private Date planEndTime;     //计划巡检结束时间
	private String executeObject; //执行对象(目前只能为代维小组)
	private String executeDept;   //执行对象部门(代维小组的系统部门)
	private String executeType;   //执行对象类型
	private String inspectUser;   //巡检员
	private String createTime;    //创建时间
	private String creator;       //创建人
	private Integer changeState;  //是否变更中(0否 1是)
	private String planChangeId;  //计划变更ID
	private Integer forceAssign;  //是否强制关联计划(0否 1是)
	private Double resLongitude;  //经度
	private Double resLatitude;   //纬度
	private Double endLongitude;  //终点经度
	private Double endLatitude;   //终点纬度
	private String filePath;      //附件地址
	private Date signTime;        //巡检签到时间
	private Date inspectTime;     //巡检完成时间
	private Date lastInspectTime; //上次巡检完成时间
	private Integer inspectState; //巡检状态(0待完成 1已完成 2超时未关联未完成 3超时已关联未完成)
	private Integer exceptionFlag;//巡检异常标识// 0异常,1正常 -1无设备
	private Float timeOnSite;     //在站时长
	private Integer itemNum;      //巡检项数
	private Integer itemDoneNum;  //已完成巡检项数
	private Integer hasPicture;   //是否有图片（0有图片，1无图片）
	private Double errorDistance; //误差距离
	private Integer startSignTimes;
	private Integer endSignTimes;
	private Integer burstFlag;    //突发标志(0常规 1突发)
	private String inspectPoint;      //所属巡检点
	private String inspectPointId;    //所属巡检点Id
	
	
	/**
	 *  强制提交标识
	 */
	private String forcedSubmit;  // 0 表示正常到达巡检规定时间提交,  1表示强制提交.
	

    /**
     * (旧) 资源查询.apk   新增 条形码 字段
     */
    private String  resBar;


	private Double signLongitude;  //定位经度
	private Double signLatitude;   //定位纬度
	
	private String satisfaction;   //满意度
	private String textRemark;     //文本描述
	private Date qualityInspectTime;   //质检时间
	private String qualityInspectUser; //质检人
	private Integer  signStatus ; //0签到正常,1不在范围之内,2无坐标有照片,3不在范围之内,但有图片,-1PC上填写
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
	/**
	 * 是否处理
	 */
	private Integer isHandle;
	/**
	 * 审核状态
	 * 0待审核
	 * 1审核通过
	 * 2审核未通过
	 * 默认－1
	 */
	private Integer auditState;
	/**
	 * 审核意见
	 */
	private String auditOpinion;

	
    public String getForcedSubmit() {
		return forcedSubmit;
	}

	public void setForcedSubmit(String forcedSubmit) {
		this.forcedSubmit = forcedSubmit;
	}

	public String getResBar(){
        return  resBar;
    }

    public  void setResBar(String resBar){
        this.resBar = resBar;
    }
	public Integer getEndSignTimes() {
		return endSignTimes;
	}
	public Integer getAuditState() {
		return auditState;
	}
	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public void setEndSignTimes(Integer endSignTimes) {
		this.endSignTimes = endSignTimes;
	}
	public Integer getStartSignTimes() {
		return startSignTimes;
	}
	public void setStartSignTimes(Integer startSignTimes) {
		this.startSignTimes = startSignTimes;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getErrorDistance() {
		return errorDistance;
	}
	public void setErrorDistance(Double errorDistance) {
		this.errorDistance = errorDistance;
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
	public Date getCycleStartTime() {
		return cycleStartTime;
	}
	public void setCycleStartTime(Date cycleStartTime) {
		this.cycleStartTime = cycleStartTime;
	}
	public Date getCycleEndTime() {
		return cycleEndTime;
	}
	public void setCycleEndTime(Date cycleEndTime) {
		this.cycleEndTime = cycleEndTime;
	}
	public Date getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}
	public Date getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}
	public String getExecuteObject() {
		return executeObject;
	}
	public void setExecuteObject(String executeObject) {
		this.executeObject = executeObject;
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
	public Integer getChangeState() {
		return changeState;
	}
	public void setChangeState(Integer changeState) {
		this.changeState = changeState;
	}
	public Double getResLongitude() {
		return resLongitude;
	}
	public void setResLongitude(Double resLongitude) {
		this.resLongitude = resLongitude;
	}
	public Double getResLatitude() {
		return resLatitude;
	}
	public void setResLatitude(Double resLatitude) {
		this.resLatitude = resLatitude;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getInspectTime() {
		return inspectTime;
	}
	public void setInspectTime(Date inspectTime) {
		this.inspectTime = inspectTime;
	}
	public Integer getInspectState() {
		return inspectState;
	}
	public void setInspectState(Integer inspectState) {
		this.inspectState = inspectState;
	}
	public Integer getForceAssign() {
		return forceAssign;
	}
	public void setForceAssign(Integer forceAssign) {
		this.forceAssign = forceAssign;
	}
	public String getExecuteDept() {
		return executeDept;
	}
	public void setExecuteDept(String executeDept) {
		this.executeDept = executeDept;
	}
	public Integer getExceptionFlag() {
		return exceptionFlag;
	}
	public void setExceptionFlag(Integer exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
	}
	public Float getTimeOnSite() {
		return timeOnSite;
	}
	public void setTimeOnSite(Float timeOnSite) {
		this.timeOnSite = timeOnSite;
	}
	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public Integer getItemDoneNum() {
		return itemDoneNum;
	}
	public void setItemDoneNum(Integer itemDoneNum) {
		this.itemDoneNum = itemDoneNum;
	}
	public String getPlanChangeId() {
		return planChangeId;
	}
	public void setPlanChangeId(String planChangeId) {
		this.planChangeId = planChangeId;
	}
	public Double getEndLongitude() {
		return endLongitude;
	}
	public void setEndLongitude(Double endLongitude) {
		this.endLongitude = endLongitude;
	}
	public Double getEndLatitude() {
		return endLatitude;
	}
	public void setEndLatitude(Double endLatitude) {
		this.endLatitude = endLatitude;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public Integer getHasPicture() {
		return hasPicture;
	}
	public void setHasPicture(Integer hasPicture) {
		this.hasPicture = hasPicture;
	}
	public Date getLastInspectTime() {
		return lastInspectTime;
	}
	public void setLastInspectTime(Date lastInspectTime) {
		this.lastInspectTime = lastInspectTime;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public Integer getBurstFlag() {
		return burstFlag;
	}
	public void setBurstFlag(Integer burstFlag) {
		this.burstFlag = burstFlag;
	}
	public Integer getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}
	public Double getSignLatitude() {
		return signLatitude;
	}
	public void setSignLatitude(Double signLatitude) {
		this.signLatitude = signLatitude;
	}
	public Double getSignLongitude() {
		return signLongitude;
	}
	public void setSignLongitude(Double signLongitude) {
		this.signLongitude = signLongitude;
	}
	public String getEographicalEnvironment() {
		return eographicalEnvironment;
	}
	public void setEographicalEnvironment(String eographicalEnvironment) {
		this.eographicalEnvironment = eographicalEnvironment;
	}
	public String getRegionType() {
		return regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public String getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	public String getTextRemark() {
		return textRemark;
	}
	public void setTextRemark(String textRemark) {
		this.textRemark = textRemark;
	}
	public Date getQualityInspectTime() {
		return qualityInspectTime;
	}
	public void setQualityInspectTime(Date qualityInspectTime) {
		this.qualityInspectTime = qualityInspectTime;
	}
	public String getQualityInspectUser() {
		return qualityInspectUser;
	}
	public void setQualityInspectUser(String qualityInspectUser) {
		this.qualityInspectUser = qualityInspectUser;
	}
	public String getInspectPoint() {
		return inspectPoint;
	}
	public void setInspectPoint(String inspectPoint) {
		this.inspectPoint = inspectPoint;
	}
	public String getInspectPointId() {
		return inspectPointId;
	}
	public void setInspectPointId(String inspectPointId) {
		this.inspectPointId = inspectPointId;
	}
	public String getTlInspectFlag() {
		return tlInspectFlag;
	}
	public void setTlInspectFlag(String tlInspectFlag) {
		this.tlInspectFlag = tlInspectFlag;
	}
	public String getTlDis() {
		return tlDis;
	}
	public void setTlDis(String tlDis) {
		this.tlDis = tlDis;
	}
	public String getTlWire() {
		return tlWire;
	}
	public void setTlWire(String tlWire) {
		this.tlWire = tlWire;
	}
	public String getTlSystemLevel() {
		return tlSystemLevel;
	}
	public void setTlSystemLevel(String tlSystemLevel) {
		this.tlSystemLevel = tlSystemLevel;
	}
	public String getTlSeg() {
		return tlSeg;
	}
	public void setTlSeg(String tlSeg) {
		this.tlSeg = tlSeg;
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
	public String getTlFiberCount() {
		return tlFiberCount;
	}
	public void setTlFiberCount(String tlFiberCount) {
		this.tlFiberCount = tlFiberCount;
	}
	public String getTlPAName() {
		return tlPAName;
	}
	public void setTlPAName(String tlPAName) {
		this.tlPAName = tlPAName;
	}
	public String getTlPALo() {
		return tlPALo;
	}
	public void setTlPALo(String tlPALo) {
		this.tlPALo = tlPALo;
	}
	public String getTlPALa() {
		return tlPALa;
	}
	public void setTlPALa(String tlPALa) {
		this.tlPALa = tlPALa;
	}
	public String getTlPZName() {
		return tlPZName;
	}
	public void setTlPZName(String tlPZName) {
		this.tlPZName = tlPZName;
	}
	public String getTlPZLo() {
		return tlPZLo;
	}
	public void setTlPZLo(String tlPZLo) {
		this.tlPZLo = tlPZLo;
	}
	public String getTlPZLa() {
		return tlPZLa;
	}
	public void setTlPZLa(String tlPZLa) {
		this.tlPZLa = tlPZLa;
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
	public String getTlRealArrivedRate() {
		return tlRealArrivedRate;
	}
	public void setTlRealArrivedRate(String tlRealArrivedRate) {
		this.tlRealArrivedRate = tlRealArrivedRate;
	}
	public String getTlExecuteType() {
		return tlExecuteType;
	}
	public void setTlExecuteType(String tlExecuteType) {
		this.tlExecuteType = tlExecuteType;
	}
	public String getTlReportInterval() {
		return tlReportInterval;
	}
	public void setTlReportInterval(String tlReportInterval) {
		this.tlReportInterval = tlReportInterval;
	}
	public Integer getIsHandle() {
		return isHandle;
	}
	public void setIsHandle(Integer isHandle) {
		this.isHandle = isHandle;
	}
	
	
}
