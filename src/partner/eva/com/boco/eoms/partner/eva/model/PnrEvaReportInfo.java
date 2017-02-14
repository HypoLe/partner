package com.boco.eoms.partner.eva.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:考核报表信息，用来记录考核报表情况与总值
 * </p>
 * <p>
 * Description:考核报表信息，用来记录考核报表情况与总值
 * </p>
 * <p>
 * Date:2009-2-11 上午11:02:55
 * </p>
 * 
 * @author 王思轩
 * @version 3.5.1
 * 
 */

public class PnrEvaReportInfo extends BaseObject {

	private String id;
	private String taskId;
	private String time;
	private String timeType;
	private String partnerId;
	private String partnerName;
	private String isPublish;
	private String createTime;
	private String totalWeight;
	private float totalScore;
	private String area;
	private String state;
	private String assessUserId;//评定人
	private String assessDeptId;//评定部门
	/**
	 * 保存节点的id值,用于历史数据的查询
	 */
	private String belongNode ;
	/**
	 * 任务名称，作显示用，数据库中不存在该字段
	 * 贾智会 2009-11-12
	 */
	private String taskName ;
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
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

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PnrEvaReportInfo other = (PnrEvaReportInfo) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isPublish == null) {
			if (other.isPublish != null)
				return false;
		} else if (!isPublish.equals(other.isPublish))
			return false;
		if (partnerId == null) {
			if (other.partnerId != null)
				return false;
		} else if (!partnerId.equals(other.partnerId))
			return false;
		if (partnerName == null) {
			if (other.partnerName != null)
				return false;
		} else if (!partnerName.equals(other.partnerName))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (timeType == null) {
			if (other.timeType != null)
				return false;
		} else if (!timeType.equals(other.timeType))
			return false;
		if (Float.floatToIntBits(totalScore) != Float
				.floatToIntBits(other.totalScore))
			return false;
		if (totalWeight == null) {
			if (other.totalWeight != null)
				return false;
		} else if (!totalWeight.equals(other.totalWeight))
			return false;
		return true;
	}

	public String toString() {
		return null;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isPublish == null) ? 0 : isPublish.hashCode());
		result = prime * result
				+ ((partnerId == null) ? 0 : partnerId.hashCode());
		result = prime * result
				+ ((partnerName == null) ? 0 : partnerName.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime * result
				+ ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result
				+ ((timeType == null) ? 0 : timeType.hashCode());
		result = prime * result + Float.floatToIntBits(totalScore);
		result = prime * result
				+ ((totalWeight == null) ? 0 : totalWeight.hashCode());
		return result;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBelongNode() {
		return belongNode;
	}

	public void setBelongNode(String belongNode) {
		this.belongNode = belongNode;
	}

	public String getAssessUserId() {
		return assessUserId;
	}

	public void setAssessUserId(String assessUserId) {
		this.assessUserId = assessUserId;
	}

	public String getAssessDeptId() {
		return assessDeptId;
	}

	public void setAssessDeptId(String assessDeptId) {
		this.assessDeptId = assessDeptId;
	}

}
