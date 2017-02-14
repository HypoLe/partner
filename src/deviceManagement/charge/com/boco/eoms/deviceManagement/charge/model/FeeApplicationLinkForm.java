package com.boco.eoms.deviceManagement.charge.model;



import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.base.webapp.form.BaseForm;


public class FeeApplicationLinkForm extends BaseForm implements
java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // 主键

	private String feeApplicationID; //费用申请单ID

	private String operateUser; // 操作人（系统登录人）
	
	private String operateDept; // 操作人部门（登录人所在部门）

	private String operateCall; // 操作人联系方式
	
	private String operateRole; // 操作人当前角色

	private String operateTime; // 操作时间
	
	private String operateAccessory; // 附件
	
	private String operateTarget; // 派往对象
	
	private String operateOpinion; // 审批意见	
	
    private String operateRemark; // 备注
	
	private String operateResult; // 审批结果（通过，不通过）
	
	private String operateStatus;
	

	public String getId() {
		return id;
	}










	public void setId(String id) {
		this.id = id;
	}










	public String getFeeApplicationID() {
		return feeApplicationID;
	}










	public void setFeeApplicationID(String feeApplicationID) {
		this.feeApplicationID = feeApplicationID;
	}











	public String getOperateUser() {
		return operateUser;
	}










	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}










	public String getOperateDept() {
		return operateDept;
	}










	public void setOperateDept(String operateDept) {
		this.operateDept = operateDept;
	}










	public String getOperateCall() {
		return operateCall;
	}










	public void setOperateCall(String operateCall) {
		this.operateCall = operateCall;
	}










	public String getOperateRole() {
		return operateRole;
	}










	public void setOperateRole(String operateRole) {
		this.operateRole = operateRole;
	}










	public String getOperateTime() {
		return operateTime;
	}










	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}










	public String getOperateAccessory() {
		return operateAccessory;
	}










	public void setOperateAccessory(String operateAccessory) {
		this.operateAccessory = operateAccessory;
	}










	public String getOperateTarget() {
		return operateTarget;
	}










	public void setOperateTarget(String operateTarget) {
		this.operateTarget = operateTarget;
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










	@Override
	public boolean equals(Object o) {
		if (o instanceof FeeApplicationLinkForm) {
			FeeApplicationLinkForm a = (FeeApplicationLinkForm) o;
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



