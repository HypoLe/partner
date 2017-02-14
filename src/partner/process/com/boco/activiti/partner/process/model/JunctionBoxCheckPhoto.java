package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 交接箱核查信息
 * 
 * @author Jun
 * 
 */

public class JunctionBoxCheckPhoto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;// 主键

	private String filePath;// 文件路径

	private Double fiberNodeId;// 交接箱id

	private String fiberNodeNo;// 交接箱编号

	private String longitude;// 经度

	private String latitude;// 纬度

	private Date photoTime;// 照片时间

	private String userId;// 上传人

	private Date createTime;// 创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

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

	public Date getPhotoTime() {
		return photoTime;
	}

	public void setPhotoTime(Date photoTime) {
		this.photoTime = photoTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
