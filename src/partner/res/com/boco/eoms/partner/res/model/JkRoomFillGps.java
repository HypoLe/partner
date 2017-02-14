package com.boco.eoms.partner.res.model;

import java.util.Date;

/**
 * 	给资源系统回传数据的表
 * 	对应表jk_room_fill_gps
 	* @author WangJun
 	* @ClassName: PnrResourceInventoryRoom
 	* @date Nov 27, 2015 2:39:54 PM
 	* @description 
 */
public class JkRoomFillGps {
	
	private String id;//主键
	private String roomId;//机房流水号
	private String roomName;//机房名称
	private String gpsX;//机房X坐标
	private String gpsY;//机房Y坐标
	private String insertMan;//采集人
	private Date insertDate;//采集时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getGpsX() {
		return gpsX;
	}
	public void setGpsX(String gpsX) {
		this.gpsX = gpsX;
	}
	public String getGpsY() {
		return gpsY;
	}
	public void setGpsY(String gpsY) {
		this.gpsY = gpsY;
	}
	public String getInsertMan() {
		return insertMan;
	}
	public void setInsertMan(String insertMan) {
		this.insertMan = insertMan;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	

}
