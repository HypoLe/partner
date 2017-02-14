package com.boco.eoms.partner.res.model;

/** 
 * Description:  传输线路
 * Copyright:   Copyright (c)2012 
 * Company:     BOCO
 * @author:     liaojiming 
 * @version:    1.0 
 * Create at:   2012/7/16 
 */

public class PnrResLine {

	private String id;
	private String endLongitude;            //终点经度
	private String endLatitude;             //终点纬度
	private String tubeSide ;				  //管程/杆程
	private String singleRouting ;		      //单路由光缆皮长
	private String likeRouting ;             //同路由光缆皮长
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getTubeSide() {
		return tubeSide;
	}
	public void setTubeSide(String tubeSide) {
		this.tubeSide = tubeSide;
	}
	public String getSingleRouting() {
		return singleRouting;
	}
	public void setSingleRouting(String singleRouting) {
		this.singleRouting = singleRouting;
	}
	public String getLikeRouting() {
		return likeRouting;
	}
	public void setLikeRouting(String likeRouting) {
		this.likeRouting = likeRouting;
	}
	
	
	
}