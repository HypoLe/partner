package com.boco.eoms.duty.model;

 
public class TawRmAddonsTable  {
	private String id; // 标识

	private String name; // 中文名

	private String remark; // 备注

	private String model; // 模块

	private String url;// 地址

	private String roomId; // 机房名称

	// private String deptId; // 名称

	private String creatUser; // 创建人

	private String creatTime; // 创建时间
	private String roomName ; 
	private String deleted;

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getId() {

		return id;
	}

	public void setId(String id) {
		this.id = id;

	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getCreatUser() {
		return creatUser;
	}

	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

/*	
	CREATE TABLE informix.taw_rm_addons(
	id                  serial NOT NULL PRIMARY KEY ,
	name                VARCHAR(255),
	remark              VARCHAR(255),
	model               VARCHAR(50) ,
	url            		VARCHAR(255) ,
	roomId              VARCHAR(50) ,
	creatUser           VARCHAR(50) ,
	creatTime           VARCHAR(50) ,
	deleted             VARCHAR(10) 
	
)*/
}
