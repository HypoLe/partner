package com.boco.eoms.deviceManagement.busi.protectline.form;

import com.boco.eoms.base.webapp.form.BaseForm;

public class WarningBoardOperateForm  extends BaseForm implements
java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private String id; // 主键
	
	private String warningBoardId;//警示牌ID
	
	private String operateTime;//审核时间
	
    private String operateUserId; // 审核人id
	
	private String operateResult;//审核结果
	
	private String operateOpinion;//审核意见
	
	private String operateRemark;//备注
	
	private String operateStatus; // 协议状态
		
    private String operateTarget;//派往对象id
		





	public String getId() {
		return id;
	}






	public void setId(String id) {
		this.id = id;
	}






	public String getOperateOpinion() {
		return operateOpinion;
	}






	public void setOperateOpinion(String operateOpinion) {
		this.operateOpinion = operateOpinion;
	}






	public String getOperateRemark() {
		return operateRemark;
	}






	public void setOperateRemark(String operateRemark) {
		this.operateRemark = operateRemark;
	}






	public String getOperateResult() {
		return operateResult;
	}






	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}






	public String getOperateStatus() {
		return operateStatus;
	}






	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}






	public String getOperateTarget() {
		return operateTarget;
	}






	public void setOperateTarget(String operateTarget) {
		this.operateTarget = operateTarget;
	}






	public String getOperateTime() {
		return operateTime;
	}






	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}






	public String getOperateUserId() {
		return operateUserId;
	}






	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}






	





	public String getWarningBoardId() {
		return warningBoardId;
	}






	public void setWarningBoardId(String warningBoardId) {
		this.warningBoardId = warningBoardId;
	}






	@Override
	public boolean equals(Object o) {
		if (o instanceof WarningBoardOperateForm) {
			WarningBoardOperateForm a = (WarningBoardOperateForm) o;
			if (this.id != null || this.id.equals(a.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	
	

}
