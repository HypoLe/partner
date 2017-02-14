package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 机房资产表
 * 
 * @author WANGJUN
 * 
 */
/**
 * @author Administrator
 *
 */
public class RoomAssets implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//资产标签号
	private String assetNumbers;
	//资产名称
	private String assetName;
	//资产类别
	private String assetsSort;
	//设备型号
	private String modelVersion;
	//资产启用日期
	private Date assetsStartTime;
	//资产使用月数（月）
	private Integer assetsMonthNum;
	//原值
	private String originalValue;
	//累计折旧
	private String accumulatedDepreciation;
	//资产净值
	private String assetsNet;
	//累计减值准备
	private String assetsDevalue;
	//地市
	private String city;
	//机房名称
	private String roomName;
	//机房类型
	private String roomType;
	//退网使用方向
	private String direction;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssetNumbers() {
		return assetNumbers;
	}

	public void setAssetNumbers(String assetNumbers) {
		this.assetNumbers = assetNumbers;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetsSort() {
		return assetsSort;
	}

	public void setAssetsSort(String assetsSort) {
		this.assetsSort = assetsSort;
	}

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

	public Date getAssetsStartTime() {
		return assetsStartTime;
	}

	public void setAssetsStartTime(Date assetsStartTime) {
		this.assetsStartTime = assetsStartTime;
	}

	public Integer getAssetsMonthNum() {
		return assetsMonthNum;
	}

	public void setAssetsMonthNum(Integer assetsMonthNum) {
		this.assetsMonthNum = assetsMonthNum;
	}

	public String getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}

	public String getAccumulatedDepreciation() {
		return accumulatedDepreciation;
	}

	public void setAccumulatedDepreciation(String accumulatedDepreciation) {
		this.accumulatedDepreciation = accumulatedDepreciation;
	}

	public String getAssetsNet() {
		return assetsNet;
	}

	public void setAssetsNet(String assetsNet) {
		this.assetsNet = assetsNet;
	}

	public String getAssetsDevalue() {
		return assetsDevalue;
	}

	public void setAssetsDevalue(String assetsDevalue) {
		this.assetsDevalue = assetsDevalue;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}


}
