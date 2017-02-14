package com.boco.activiti.partner.process.model;

import java.sql.Clob;
import java.util.Date;

public class PnrAndroidWorkOderPhotoFile {

	
	
    /** 主键 */
	protected String id;
	
	/** 照片Id */
	protected String picture_id;

	/** 文件Base64数据 */
	private Clob fileData;
	
	
	/** 工单类型 */
	private String id_type;

	  private String path;
	  private String imgPath;
	  private Date createTime;
	  private String latitude;
	  private String longitude;
	/** 记录系统时间 */
	private Date systemTime;
	/** 2：图片路径改用imgPath*/
	
	private Integer state;
	
	private String userId;
	
	private String distance;
	
	//照片类型
	private String photoType;
	//照片类型 --二级
	private String photoSubType;
	
	
	
	public String getPhotoType() {
		return photoType;
	}


	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}


	public String getPhotoSubType() {
		return photoSubType;
	}


	public void setPhotoSubType(String photoSubType) {
		this.photoSubType = photoSubType;
	}


	public String getDistance() {
		return distance;
	}


	public void setDistance(String distance) {
		this.distance = distance;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPicture_id() {
		return picture_id;
	}


	public void setPicture_id(String picture_id) {
		this.picture_id = picture_id;
	}


	public Clob getFileData() {
		return fileData;
	}


	public void setFileData(Clob fileData) {
		this.fileData = fileData;
	}


	public String getId_type() {
		return id_type;
	}


	public void setId_type(String id_type) {
		this.id_type = id_type;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public Date getSystemTime() {
		return systemTime;
	}


	public void setSystemTime(Date systemTime) {
		this.systemTime = systemTime;
	}


	public String getImgPath() {
		return imgPath;
	}


	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
