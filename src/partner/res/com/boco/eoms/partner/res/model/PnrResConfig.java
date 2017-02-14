package com.boco.eoms.partner.res.model;

import java.util.Date;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012 
 * Company:     BOCO
 * @author:     liaojiming 
 * @version:    1.0 
 * Create at:   2012/7/10 
 */

public class PnrResConfig {

	private String id;
	private String executeObject;     //执行对象
	private String executeType;       //执行对象类型
	private String specialty;         //资源专业类型
	private String resName;			  //资源名称
	private String resType;			  //资源类别
	private String resLevel;		  //资源级别
	private String resLongitude;      //资源经度
	private String resLatitude;	      //资源纬度
	private String subResTable;       //资源子表明
	private String subResId;		  //资源子表ID
	private String inspectCycle;      //巡检周期
	private String city;		      //地市
	private String country;           //区县
	private String executeDept;       //执行部门
	private String creator;           //制定人
	private String createTime;        //制定日期
	private String street;     		  //街道
	private String companyName;       //单位名称
	private String contactName;		  //联系人名称
	private String phone;             //联系电话
	private String remarks;  		  //备注
	private String serviceRegion;	  //服务区域
	private String endLongitude;      //终点经度
	private String endLatitude;       //终点纬度
	private String eographicalEnvironment;     //地理环境
	private String regionType;        //区域类型
	private Date lastInspectTime;     //上次巡检完成时间
	private String inspectPoint;      //所属巡检点
	private String inspectPointId;    //所属巡检点Id
	private String resBar;  //资源条码  2013-09-02
	private String chargePerson;  //增加一个维护人员与站点的关系 2014-01-07
	
	
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
	/**到点率*/
	private String tlArrivedRate;
	/**巡检执行方式*/
	private String tlExecuteType;
	/**自动上报位置信息时间间隔*/
	private String tlReportInterval;
	
	/**操作记录字段 用于记录用户是否已经执行过某些动作*/
	private Integer executeRecord;
	/*以下字段用于线路巡检独有 end*/
	
	
	public Integer getExecuteRecord() {
		return executeRecord;
	}
	public void setExecuteRecord(Integer executeRecord) {
		this.executeRecord = executeRecord;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegionType() {
		return regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
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
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getResLevel() {
		return resLevel;
	}
	public void setResLevel(String resLevel) {
		this.resLevel = resLevel;
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
	public String getSubResTable() {
		return subResTable;
	}
	public void setSubResTable(String subResTable) {
		this.subResTable = subResTable;
	}
	public String getSubResId() {
		return subResId;
	}
	public void setSubResId(String subResId) {
		this.subResId = subResId;
	}
	public String getInspectCycle() {
		return inspectCycle;
	}
	public void setInspectCycle(String inspectCycle) {
		this.inspectCycle = inspectCycle;
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
	public String getExecuteDept() {
		return executeDept;
	}
	public void setExecuteDept(String executeDept) {
		this.executeDept = executeDept;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getServiceRegion() {
		return serviceRegion;
	}
	public void setServiceRegion(String serviceRegion) {
		this.serviceRegion = serviceRegion;
	}
	public String getEndLongitude() {
		return endLongitude;
	}
	public void setEndLongitude(String endLongitude) {
		this.endLongitude = endLongitude;
	}
	public String getEndLatitude() {
		return endLatitude;
	}
	public void setEndLatitude(String endLatitude) {
		this.endLatitude = endLatitude;
	}
	public Date getLastInspectTime() {
		return lastInspectTime;
	}
	public void setLastInspectTime(Date lastInspectTime) {
		this.lastInspectTime = lastInspectTime;
	}
	public String getEographicalEnvironment() {
		return eographicalEnvironment;
	}
	public void setEographicalEnvironment(String eographicalEnvironment) {
		this.eographicalEnvironment = eographicalEnvironment;
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
	public String getTlInspectFlag() {
		return tlInspectFlag;
	}
	public void setTlInspectFlag(String tlInspectFlag) {
		this.tlInspectFlag = tlInspectFlag;
	}
	public String getTlLength() {
		return tlLength;
	}
	public void setTlLength(String tlLength) {
		this.tlLength = tlLength;
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
	public PnrResConfig(String id, String resName,String subResTable, String subResId) {
		this.id = id;
		this.resName = resName;
		this.subResTable = subResTable;
		this.subResId = subResId;
	}

	public String getResBar() {
		return resBar;
	}
	public void setResBar(String resBar) {
		this.resBar = resBar;
	}
	
	public String getChargePerson() {
		return chargePerson;
	}
	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}
	public PnrResConfig() {
		
	}
	
	
}
