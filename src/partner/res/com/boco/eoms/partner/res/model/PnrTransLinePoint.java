package com.boco.eoms.partner.res.model;

import java.util.Date;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012 
 * Company:     BOCO
 * @author:     zhangkeqi 
 * @version:    1.0 
 * Create at:   2013/4/15
 */

public class PnrTransLinePoint {

	private String id;
	
	private String tlpDis;
	private String tlpWire;
	private String tlpSeg;
	private String tlpPAName;
	private String tlpPALo;
	private String tlpPALa;
	private String tlpPZName;
	private String tlpPZLo;
	private String tlpPZLa;
	
	/*属性资源Id*/
	private String resCfgId;
	/*光缆点类别*/
	private String tlpType;
	/*光缆点顺序号*/
	private String tlpSortNum;
	/*资源分配情况 1:已分配 0或空或NULL：未分配 (用此来设置必到点)*/
	private String isMustArrive;
	/*映射到代维后的地市*/
	private String city;		      //地市
	/*映射到代维后的区县*/
	private String country;           //区县
	
	/*敷设点来源 0:正常情况，即本来就是段之间的点;1:光缆段的起点新增;2：光缆段的终点新增*/
	private String tlpSource;
	
	/**巡检点类型与网络资源设备类型映射id（PnrInspectMapping中的id）*/
	private String inspectMappingId;
	
	public String getInspectMappingId() {
		return inspectMappingId;
	}
	public void setInspectMappingId(String inspectMappingId) {
		this.inspectMappingId = inspectMappingId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTlpDis() {
		return tlpDis;
	}
	public void setTlpDis(String tlpDis) {
		this.tlpDis = tlpDis;
	}
	public String getTlpWire() {
		return tlpWire;
	}
	public void setTlpWire(String tlpWire) {
		this.tlpWire = tlpWire;
	}
	public String getTlpSeg() {
		return tlpSeg;
	}
	public void setTlpSeg(String tlpSeg) {
		this.tlpSeg = tlpSeg;
	}
	public String getTlpPALo() {
		return tlpPALo;
	}
	public void setTlpPALo(String tlpPALo) {
		this.tlpPALo = tlpPALo;
	}
	public String getTlpPALa() {
		return tlpPALa;
	}
	public void setTlpPALa(String tlpPALa) {
		this.tlpPALa = tlpPALa;
	}
	public String getTlpPAName() {
		return tlpPAName;
	}
	public void setTlpPAName(String tlpPAName) {
		this.tlpPAName = tlpPAName;
	}
	public String getTlpPZName() {
		return tlpPZName;
	}
	public void setTlpPZName(String tlpPZName) {
		this.tlpPZName = tlpPZName;
	}
	public String getTlpPZLo() {
		return tlpPZLo;
	}
	public void setTlpPZLo(String tlpPZLo) {
		this.tlpPZLo = tlpPZLo;
	}
	public String getTlpPZLa() {
		return tlpPZLa;
	}
	public void setTlpPZLa(String tlpPZLa) {
		this.tlpPZLa = tlpPZLa;
	}
	public String getResCfgId() {
		return resCfgId;
	}
	public void setResCfgId(String resCfgId) {
		this.resCfgId = resCfgId;
	}
	public String getTlpType() {
		return tlpType;
	}
	public void setTlpType(String tlpType) {
		this.tlpType = tlpType;
	}
	public String getTlpSortNum() {
		return tlpSortNum;
	}
	public void setTlpSortNum(String tlpSortNum) {
		this.tlpSortNum = tlpSortNum;
	}
	public String getIsMustArrive() {
		return isMustArrive;
	}
	public void setIsMustArrive(String isMustArrive) {
		this.isMustArrive = isMustArrive;
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
	public String getTlpSource() {
		return tlpSource;
	}
	public void setTlpSource(String tlpSource) {
		this.tlpSource = tlpSource;
	}
}
