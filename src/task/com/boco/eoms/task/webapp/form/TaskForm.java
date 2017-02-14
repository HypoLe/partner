package com.boco.eoms.task.webapp.form;

import java.io.Serializable;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:任务表单类
 * </p>
 * <p>
 * Description:任务表单类
 * </p>
 * <p>
 * Sun Jul 05 17:19:32 CST 2009
 * </p>
 * 
 * @author qiuzi
 * @version 3.5
 * 
 */
public class TaskForm extends BaseForm implements Serializable {

	/**
	 * 自动生成序列号
	 */
	private static final long serialVersionUID = 3957292958290727244L;

	/**
	 * 主键�
	 */
	protected String taskId;

	/**
	 * 任务编号
	 */
	protected String id;

	/**
	 * 父任务编号
	 */
	protected String parentTaskId;

	/**
	 * 后续任务Id
	 */
	protected String nextId;

	/**
	 * 任务名称
	 */
	protected java.lang.String name;

	/**
	 * 
	 * 起草人
	 * 
	 */
	protected java.lang.String drafter;

	/**
	 * 
	 * 起草时间
	 * 
	 */
	protected java.lang.String draftTime;

	/**
	 * 
	 * 任务开始时间
	 * 
	 */
	protected java.lang.String startTime;

	/**
	 * 
	 * 规定任务结束时间
	 * 
	 */
	protected java.lang.String endTime;

	/**
	 * 
	 * 任务内容
	 * 
	 */
	protected java.lang.String content;

	/**
	 * 任务负责人
	 */
	protected String principal;

	/**
	 * 任务完成进度
	 */
	protected String progress;

	/**
	 * 是否及时完成
	 */
	protected String isInTime;

	/**
	 * 是否叶子任务
	 */
	protected String leaf;

	/**
	 * 删除标志
	 */
	protected String deleted;

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

	public String getIsInTime() {
		return isInTime;
	}

	public void setIsInTime(String isInTime) {
		this.isInTime = isInTime;
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

}