package com.boco.activiti.partner.process.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单统计钻取后的工单列表model
 */
public class WorkOrderStatisticsDrillModel implements Serializable {
//  地市
	private String city;
//  工单流水号
    private String processInstanceId;
//  工单主题id
    private String themeId;
//  工单主题
    private String theme;
//  派单时间
    private Date createDate;
    
//  创建人
    private String initiator;
//  标记 1：故障工单 2：任务工单
    private Integer flag;
//  超时 1：超时 0：未超
    private Integer isOverTime;
//  所属部门
    private String deptId;

    
	public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	
	public Integer getIsOverTime() {
		return isOverTime;
	}

	public void setIsOverTime(Integer isOverTime) {
		this.isOverTime = isOverTime;
	}
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public WorkOrderStatisticsDrillModel() {
    	
    }
    
}
