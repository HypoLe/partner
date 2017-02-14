package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 交接箱核查信息
 * 
 * @author Jun
 *
 */

public class JunctionBoxCheckInfor implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private Double fiberNodeId;//交接箱id
	
	private String fiberNodeNo;//交接箱编号
	
	private Double	gpsX;//交接箱X坐标
	
	private Double	gpsY;//交接箱Y坐标
	
	private String	insertMan;//采集人
	
	private String	insertDate;//采集时间
	
	private String	posiChange;//位置是否发生变化
	
	private String modulChange;//模块数是否发生变化
	
	private String changeMemo;//变化内容
	
	private Date createTime;//入库时间
	
	private String id;//主键

	public Double getFiberNodeId() {
		return fiberNodeId;
	}

	public void setFiberNodeId(Double fiberNodeId) {
		this.fiberNodeId = fiberNodeId;
	}

	public String getFiberNodeNo() {
		return fiberNodeNo;
	}

	public void setFiberNodeNo(String fiberNodeNo) {
		this.fiberNodeNo = fiberNodeNo;
	}

	public Double getGpsX() {
		return gpsX;
	}

	public void setGpsX(Double gpsX) {
		this.gpsX = gpsX;
	}

	public Double getGpsY() {
		return gpsY;
	}

	public void setGpsY(Double gpsY) {
		this.gpsY = gpsY;
	}

	public String getInsertMan() {
		return insertMan;
	}

	public void setInsertMan(String insertMan) {
		this.insertMan = insertMan;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getPosiChange() {
		return posiChange;
	}

	public void setPosiChange(String posiChange) {
		this.posiChange = posiChange;
	}

	public String getModulChange() {
		return modulChange;
	}

	public void setModulChange(String modulChange) {
		this.modulChange = modulChange;
	}

	public String getChangeMemo() {
		return changeMemo;
	}

	public void setChangeMemo(String changeMemo) {
		this.changeMemo = changeMemo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
