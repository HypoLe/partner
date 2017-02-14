package com.boco.eoms.partner.assess.AssReport.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:操作确认费用信息
 * </p>
 * <p>
 * Description:操作确认费用信息
 * </p>
 * <p>
 * Date:Dec 2, 2010 6:19:40 AM
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */
public class AssConfirm extends BaseObject {

	private String id;

	private String taskId;
	
	private String time;
	
	private String userId;
	
	private String deductMoney;
	
	private String realMoney;
	
	private String provinceMoney;
	
	public boolean equals(Object o) {
		return this == o;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeductMoney() {
		return deductMoney;
	}

	public void setDeductMoney(String deductMoney) {
		this.deductMoney = deductMoney;
	}

	public String getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(String realMoney) {
		this.realMoney = realMoney;
	}

	public String getProvinceMoney() {
		return provinceMoney;
	}

	public void setProvinceMoney(String provinceMoney) {
		this.provinceMoney = provinceMoney;
	}


}
