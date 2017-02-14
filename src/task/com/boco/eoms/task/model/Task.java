package com.boco.eoms.task.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:任务
 * </p>
 * <p>
 * Description:任务
 * </p>
 * <p>
 * Sun Jul 05 17:19:32 CST 2009
 * </p>
 * 
 * @author qiuzi
 * @version 3.5
 * 
 */
public class Task extends BaseObject {

	/**
	 * 自动生成序列号
	 */
	private static final long serialVersionUID = -8784259605140685367L;

	/**
	 * 主键�
	 */
	private String taskId;

	/**
	 * 任务编号
	 */
	private String id;

	/**
	 * 父任务编号
	 */
	private String parentTaskId;

	/**
	 * 后续任务Id
	 */
	private String nextId;

	/**
	 * 任务名称
	 */
	private java.lang.String name;

	/**
	 * 
	 * 起草人
	 * 
	 */
	private java.lang.String drafter;

	/**
	 * 
	 * 起草时间
	 * 
	 */
	private java.lang.String draftTime;

	/**
	 * 
	 * 任务开始时间
	 * 
	 */
	private java.lang.String startTime;

	/**
	 * 
	 * 规定任务结束时间
	 * 
	 */
	private java.lang.String endTime;

	/**
	 * 
	 * 任务内容
	 * 
	 */
	private java.lang.String content;

	/**
	 * 任务负责人
	 */
	private String principal;

	/**
	 * 任务完成进度
	 */
	private String progress;

	/**
	 * 及时完成百分比
	 */
	private String inTimeProgress;

	/**
	 * 
	 * 完成情况描述
	 * 
	 */
	private java.lang.String remark;

	/**
	 * 是否短信提醒
	 */
	private String remind;

	/**
	 * 附件
	 */
	private String accessories;

	/**
	 * 是否叶子任务
	 */
	private String leaf;

	/**
	 * 删除标志
	 */
	private String deleted;

	/**
	 * 是否相对顶级任务，只在生成任务XML串时做判断用，不存入数据库
	 */
	private boolean isTopTask;

	public String getAccessories() {
		return accessories;
	}

	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}

	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	public boolean getIsTopTask() {
		return isTopTask;
	}

	public void setIsTopTask(boolean isTopTask) {
		this.isTopTask = isTopTask;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getNextId() {
		return nextId;
	}

	public void setNextId(String nextId) {
		this.nextId = nextId;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getDrafter() {
		return drafter;
	}

	public void setDrafter(java.lang.String drafter) {
		this.drafter = drafter;
	}

	public java.lang.String getDraftTime() {
		return draftTime;
	}

	public void setDraftTime(java.lang.String draftTime) {
		this.draftTime = draftTime;
	}

	public java.lang.String getStartTime() {
		return startTime;
	}

	public void setStartTime(java.lang.String startTime) {
		this.startTime = startTime;
	}

	public java.lang.String getEndTime() {
		return endTime;
	}

	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime;
	}

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getInTimeProgress() {
		return inTimeProgress;
	}

	public void setInTimeProgress(String inTimeProgress) {
		this.inTimeProgress = inTimeProgress;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public boolean equals(Object o) {
		if (o instanceof Task) {
			Task task = (Task) o;
			if (this.taskId != null && this.taskId.equals(task.getTaskId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
}