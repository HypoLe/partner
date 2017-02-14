package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 网络资源巡检众筹 照片MODEL
 * @author WANGJUN
 * 
 */
public class NetResInspectPhoto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//工单流程id
	private String processInstanceId;
	//照片Id
	private String pictureId;
	//照片创建时间
	private Date createTime;
	//经度
	private String longitude;
	//纬度
	private String latitude;
	//故障地点
	private String faultLocation;
	//故障描述
	private String faultDescription;
	//拍照人
	private String userId;
	//图片地址
	private String path;
	//环节标识
	private String linkFlag;
	//新建时的照片的唯一标识
	private String reportedUniqueFlag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
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
	public String getFaultLocation() {
		return faultLocation;
	}
	public void setFaultLocation(String faultLocation) {
		this.faultLocation = faultLocation;
	}
	public String getFaultDescription() {
		return faultDescription;
	}
	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getLinkFlag() {
		return linkFlag;
	}
	public void setLinkFlag(String linkFlag) {
		this.linkFlag = linkFlag;
	}
	public String getReportedUniqueFlag() {
		return reportedUniqueFlag;
	}
	public void setReportedUniqueFlag(String reportedUniqueFlag) {
		this.reportedUniqueFlag = reportedUniqueFlag;
	}
}
