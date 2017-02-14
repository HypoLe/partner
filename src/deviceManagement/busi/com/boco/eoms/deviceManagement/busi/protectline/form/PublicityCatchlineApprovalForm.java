package com.boco.eoms.deviceManagement.busi.protectline.form;



import com.boco.eoms.base.webapp.form.BaseForm;


public class PublicityCatchlineApprovalForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // 主键

	private String approvalUser; //审核人

	private String approvalTime; //审核时间
	
	private String projectNameID; // 审核对应广告ID

	private String approvalContent; // 审核内容
	
	private String approvalType; // 审核状态

	private String remark;//备注
	private String remark2;
	private String remark3;
	private String remark4;

	@Override
	public boolean equals(Object o) {
		if (o instanceof PublicityCatchlineApprovalForm) {
			PublicityCatchlineApprovalForm a = (PublicityCatchlineApprovalForm) o;
			if (this.id != null || this.id.equals(a.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getApprovalContent() {
		return approvalContent;
	}

	public void setApprovalContent(String approvalContent) {
		this.approvalContent = approvalContent;
	}

	public String getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectNameID() {
		return projectNameID;
	}

	public void setProjectNameID(String projectNameID) {
		this.projectNameID = projectNameID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return remark4;
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}



}



