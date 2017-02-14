package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 个人状态
 * @author wangyue
 *
 */
public class WorkerState implements Serializable{
	//表的主键
	private String id;
	//登录员工的id
	private String workerId;
	//授权人的id
	private String accredit;
	//请假时间
	private Date beginTime;
	//回复工作时间
	private Date endTime;
	//个人状态：工作状态、请假状态
	private String state;
	//授权标志：0代表没有请假，1代表请假中
	private String flag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public String getAccredit() {
		return accredit;
	}
	public void setAccredit(String accredit) {
		this.accredit = accredit;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}
