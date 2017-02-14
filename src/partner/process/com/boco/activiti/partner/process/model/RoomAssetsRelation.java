package com.boco.activiti.partner.process.model;

import java.io.Serializable;

/**
 * 机房资产和流程关联表
 * 
 * @author WANGJUN
 * 
 */
public class RoomAssetsRelation implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private String id;
	// 流程号
	private String processInstanceId;
	// 资产ID
	private String roomAssetsId;
	// 退网使用方向
	private String exitDirection;
	// 排序
	private Integer orderCode;

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

	public String getRoomAssetsId() {
		return roomAssetsId;
	}

	public void setRoomAssetsId(String roomAssetsId) {
		this.roomAssetsId = roomAssetsId;
	}

	public String getExitDirection() {
		return exitDirection;
	}

	public void setExitDirection(String exitDirection) {
		this.exitDirection = exitDirection;
	}

	public Integer getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}

}
