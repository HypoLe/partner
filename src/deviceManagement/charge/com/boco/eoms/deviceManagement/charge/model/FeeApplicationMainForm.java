package com.boco.eoms.deviceManagement.charge.model;



import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.base.webapp.form.BaseForm;


public class FeeApplicationMainForm extends BaseForm implements
java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // 主键

	private String feeApplicationUser;// 
	
	private String feeApplicationCall;//  学历
	
	private String feeApplicationDept;//级别
	
	private String feeApplicationArea;//证书	
	
	private String feeApplicationCity;//代维区域
	
	private String feeApplicationGreatTime;//备注
	
	private String feeApplicationType;//人力信息创建时间
	
	private String feeApplicationMoney;//删除标记
	
	private String feeApplicationDiscribe;//人力信息删除时间
	
	private String feeApplicationRemark;//保留字段1
	
	private String feeApplicationAccessory;//保留字段2
	
	private String feeApplicationStatus;//保留字段3
	
	private String deleted;//保留字段4
	
	private String deleteTime;//保留字段4
	
	private String feeApplicationRoleID;//角色ID
	
	private String feeApplicationCompanyName;//代维公司名

	private FormFile importFile;
	
	private String Type;
	
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
	
	private String  operateStatus;//状态
	
	private String subType;//提交动作类型
	
	
	
	private String feeapplicationprovince;//省份，默认为黑龙江

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



	public String getType() {
		return Type;
	}



	public void setType(String type) {
		Type = type;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	

	public String getFeeApplicationUser() {
		return feeApplicationUser;
	}



	public void setFeeApplicationUser(String feeApplicationUser) {
		this.feeApplicationUser = feeApplicationUser;
	}



	public String getFeeApplicationCall() {
		return feeApplicationCall;
	}



	public void setFeeApplicationCall(String feeApplicationCall) {
		this.feeApplicationCall = feeApplicationCall;
	}



	public String getFeeApplicationDept() {
		return feeApplicationDept;
	}



	public void setFeeApplicationDept(String feeApplicationDept) {
		this.feeApplicationDept = feeApplicationDept;
	}



	public String getFeeApplicationArea() {
		return feeApplicationArea;
	}



	public void setFeeApplicationArea(String feeApplicationArea) {
		this.feeApplicationArea = feeApplicationArea;
	}



	public String getFeeApplicationCity() {
		return feeApplicationCity;
	}



	public void setFeeApplicationCity(String feeApplicationCity) {
		this.feeApplicationCity = feeApplicationCity;
	}



	public String getFeeApplicationGreatTime() {
		return feeApplicationGreatTime;
	}



	public void setFeeApplicationGreatTime(String feeApplicationGreatTime) {
		this.feeApplicationGreatTime = feeApplicationGreatTime;
	}



	public String getFeeApplicationType() {
		return feeApplicationType;
	}



	public void setFeeApplicationType(String feeApplicationType) {
		this.feeApplicationType = feeApplicationType;
	}



	public String getFeeApplicationMoney() {
		return feeApplicationMoney;
	}



	public void setFeeApplicationMoney(String feeApplicationMoney) {
		this.feeApplicationMoney = feeApplicationMoney;
	}



	public String getFeeApplicationDiscribe() {
		return feeApplicationDiscribe;
	}



	public void setFeeApplicationDiscribe(String feeApplicationDiscribe) {
		this.feeApplicationDiscribe = feeApplicationDiscribe;
	}



	public String getFeeApplicationRemark() {
		return feeApplicationRemark;
	}



	public void setFeeApplicationRemark(String feeApplicationRemark) {
		this.feeApplicationRemark = feeApplicationRemark;
	}



	public String getFeeApplicationAccessory() {
		return feeApplicationAccessory;
	}



	public void setFeeApplicationAccessory(String feeApplicationAccessory) {
		this.feeApplicationAccessory = feeApplicationAccessory;
	}



	public String getFeeApplicationStatus() {
		return feeApplicationStatus;
	}



	public void setFeeApplicationStatus(String feeApplicationStatus) {
		this.feeApplicationStatus = feeApplicationStatus;
	}



	public String getDeleted() {
		return deleted;
	}



	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}



	public String getDeleteTime() {
		return deleteTime;
	}



	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}



	public String getFeeApplicationRoleID() {
		return feeApplicationRoleID;
	}



	public void setFeeApplicationRoleID(String feeApplicationRoleID) {
		this.feeApplicationRoleID = feeApplicationRoleID;
	}



	public String getFeeApplicationCompanyName() {
		return feeApplicationCompanyName;
	}



	public void setFeeApplicationCompanyName(
			String feeApplicationCompanyName) {
		this.feeApplicationCompanyName = feeApplicationCompanyName;
	}



	public FormFile getImportFile() {
		return importFile;
	}



	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}



	public String getFeeapplicationprovince() {
		return feeapplicationprovince;
	}



	public void setFeeapplicationprovince(String feeapplicationprovince) {
		this.feeapplicationprovince = feeapplicationprovince;
	}



	public String getSubType() {
		return subType;
	}



	public void setSubType(String subType) {
		this.subType = subType;
	}



	public String getOperateStatus() {
		return operateStatus;
	}



	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}



	@Override
	public boolean equals(Object o) {
		if (o instanceof FeeApplicationMainForm) {
			FeeApplicationMainForm a = (FeeApplicationMainForm) o;
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



