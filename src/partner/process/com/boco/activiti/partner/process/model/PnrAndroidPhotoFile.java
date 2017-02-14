package com.boco.activiti.partner.process.model;

import java.sql.Clob;
import java.util.Date;

public class PnrAndroidPhotoFile {

	/** 主键 */
	protected String id;
	/** 照片Id */
	protected String picture_id;

	/** 文件Base64数据 */
	private Clob fileData;

	/** 上传时间 */
	private String createTime;

	/** 经度 */
	private String longitude;

	/** 纬度 */
	private String latitude;

	/** 故障地点 */
	private String faultLocation;

	/** 故障描述 */
	private String faultDescription;
	/** 处理人 */
	private String userId;
	/** 处理人所在的组织 */
	private String deptId;
	/** 处理人所在的地市 */
	private String city;
	/**是否被选过  1 代表已选状态 0 或空代表未选状态*/
	private String state;
	/**图片地址*/
	private String path;
	//照片类型
	private String photoType;
	//照片类型 --二级
	private String photoSubType;
	
	//服务器时间 20160510添加
	private Date systemTime;

	public String getPhotoSubType() {
		return photoSubType;
	}

	public void setPhotoSubType(String photoSubType) {
		this.photoSubType = photoSubType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Clob getFileData() {
		return fileData;
	}

	public void setFileData(Clob fileData) {
		this.fileData = fileData;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPicture_id() {
		return picture_id;
	}

	public void setPicture_id(String picture_id) {
		this.picture_id = picture_id;
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPhotoType() {
		return photoType;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}

	public Date getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Date systemTime) {
		this.systemTime = systemTime;
	}
}
