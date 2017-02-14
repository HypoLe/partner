package base.model;

import java.util.Date;

/**
 * 基础信息抽象模型
 * 
 * @author Steve
 * 
 */
public abstract class Nop3GenericMain {
	/*
	 * 这里定义程序Main Model中使用的字段常量，对于某些常量，应该达成命名规范。
	 */
	private String id;// 主键，一般为UUID 32位

	private String status;// 流程状态，该状态可以理解为：“横切面”状态

	private String verticalStatus;// 该状态可以理解为：“纵切面”状态

	private String taskOwnerType;// 任务所有者类型，角色，部门，人等任意类型

	private String taskOwner;// 任务所有者

	private String createUserId;// 记录创建者Id

	private Date createDate;// 记录创建时间

	private int yearFlag;// 年标记

	private int monthFlag;// 月标记

	private int dayFlag;// 天标记

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVerticalStatus() {
		return verticalStatus;
	}

	public void setVerticalStatus(String verticalStatus) {
		this.verticalStatus = verticalStatus;
	}

	public String getTaskOwnerType() {
		return taskOwnerType;
	}

	public void setTaskOwnerType(String taskOwnerType) {
		this.taskOwnerType = taskOwnerType;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getDayFlag() {
		return dayFlag;
	}

	public void setDayFlag(int dayFlag) {
		this.dayFlag = dayFlag;
	}

	public int getMonthFlag() {
		return monthFlag;
	}

	public void setMonthFlag(int monthFlag) {
		this.monthFlag = monthFlag;
	}

	public int getYearFlag() {
		return yearFlag;
	}

	public void setYearFlag(int yearFlag) {
		this.yearFlag = yearFlag;
	}

}
