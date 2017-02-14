package com.boco.eoms.partner.inspect.model;

import com.boco.eoms.base.model.BaseObject;

/** 
 * Description: 巡检轨迹
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     lee 
 * @version:    1.0 
 * Create at:   Jul 15, 2012 9:43:43 PM 
 */
public class PnrInspectTrack extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String planResId;
	String signTime;
	String signLongitude;
	String signLatitude;
	String signState;//0强制到站 ,1正常到站
	double errorRange;//误差距离
	String status; //0离站,1到站中,2巡检中
	
	private Integer StartSignTimes;//签到打点次数
	private Integer endSignTimes;//签退打点次数
	
	private String inspectUser;  //巡检人员
	private String carNumber;    //车牌号
	
	public Integer getEndSignTimes() {
		return endSignTimes;
	}



	public void setEndSignTimes(Integer endSignTimes) {
		this.endSignTimes = endSignTimes;
	}



	public Integer getStartSignTimes() {
		return StartSignTimes;
	}



	public void setStartSignTimes(Integer startSignTimes) {
		StartSignTimes = startSignTimes;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getPlanResId() {
		return planResId;
	}



	public void setPlanResId(String planResId) {
		this.planResId = planResId;
	}



	public String getSignTime() {
		return signTime;
	}



	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}



	public String getSignLongitude() {
		return signLongitude;
	}



	public void setSignLongitude(String signLongitude) {
		this.signLongitude = signLongitude;
	}



	public String getSignLatitude() {
		return signLatitude;
	}



	public void setSignLatitude(String signLatitude) {
		this.signLatitude = signLatitude;
	}



	public String getSignState() {
		return signState;
	}



	public void setSignState(String signState) {
		this.signState = signState;
	}






	



	public double getErrorRange() {
		return errorRange;
	}



	public void setErrorRange(double errorRange) {
		this.errorRange = errorRange;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	@Override
	public boolean equals(Object o) {
		  if( o instanceof PnrInspectTrack ) {
			  PnrInspectTrack inspectTrack=(PnrInspectTrack)o;
	            if (this.id != null || this.id.equals(inspectTrack.getId())) {
	                return true;
	            } else {
	                return false;
	            }
	        } else {
	            return false;
	        }
	}



	public String getInspectUser() {
		return inspectUser;
	}



	public void setInspectUser(String inspectUser) {
		this.inspectUser = inspectUser;
	}



	public String getCarNumber() {
		return carNumber;
	}



	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	
	
	
}
