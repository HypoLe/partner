package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 地市预算金额
 */

public class PnrReviewResults implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private String id;
	// 工单号
	private String processInstanceId;
	// 回填环节
	// cityManageExamineAgency：市运维主管待办;cityManageExamineToReply:市运维主管待回复;provinceLineExamineAgency:省线路主管待办;provinceLineExamineToReply:省线路主管待回复;waitingCallInterface:等待接口调用;
	private String backfillLink;
	// 会审结果 YES:合格; NO：不合格;
	private String reviewResult;
	// 专家意见
	private String expertOpinion;
	// 会审时间
	private Date reviewTime;
	// 工单流程类型
	private String themeInterface;
	// 处理标志 0:处理中; 1:已处理;
	private String handleMark;
	// 导入人
	private String importUser;
	// 唯一标识
	private String uniqueMark;
	// 导入时间
	private Date importTime;
	// 是否同意实施
	private String isAgreeStriking;

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

	public String getBackfillLink() {
		return backfillLink;
	}

	public void setBackfillLink(String backfillLink) {
		this.backfillLink = backfillLink;
	}

	public String getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(String reviewResult) {
		this.reviewResult = reviewResult;
	}

	public String getExpertOpinion() {
		return expertOpinion;
	}

	public void setExpertOpinion(String expertOpinion) {
		this.expertOpinion = expertOpinion;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getThemeInterface() {
		return themeInterface;
	}

	public void setThemeInterface(String themeInterface) {
		this.themeInterface = themeInterface;
	}

	public String getHandleMark() {
		return handleMark;
	}

	public void setHandleMark(String handleMark) {
		this.handleMark = handleMark;
	}

	public String getImportUser() {
		return importUser;
	}

	public void setImportUser(String importUser) {
		this.importUser = importUser;
	}

	public String getUniqueMark() {
		return uniqueMark;
	}

	public void setUniqueMark(String uniqueMark) {
		this.uniqueMark = uniqueMark;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public String getIsAgreeStriking() {
		return isAgreeStriking;
	}

	public void setIsAgreeStriking(String isAgreeStriking) {
		this.isAgreeStriking = isAgreeStriking;
	}

}
