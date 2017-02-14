package com.boco.eoms.deviceManagement.busi.protectline.model;



import com.boco.eoms.base.model.BaseObject;


public class PublicizeArticle extends BaseObject {

	private static final long serialVersionUID = 1L;

	private String id; // 主键

	private String creatUser; //填报人

	private String creatTime; // 填报时间
	
	private String creatDept; // 所属部门

	private String approvalUser; // 提交审核人
	
	private String projectName; // 项目名称

	private String publicizeArticlePlace; // 宣传单/宣传品发放地点
	
	private String publicizeArticlePlanAmount; // 计划发放宣传单/宣传品数量（份）
	
	private String publicizeArticleActualAmount; // 实际发放宣传单/宣传品数量（份）
	
	private String publicizeArticleType; // 宣传品种类名称（份）*
	
	private String finishTime; // 完成时间	
	
    private String incompleteCause; // 未完成原因
    
	private String  approvalType;//审核状态
	
	private String remark; // 备注
	
	private String  deleted;//删除标示
	
	private String  modifyUser;//修改人
	
	private String  modifyTime;//修改时间
	
	private String  modifyDept;//修改人部门
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	public String getRemark1() {
		return remark1;
	}







	public void setRemark1(String remark1) {
		this.remark1 = remark1;
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







	@Override
	public boolean equals(Object o) {
		if (o instanceof PublicizeArticle) {
			PublicizeArticle a = (PublicizeArticle) o;
			if (this.id != null || this.id.equals(a.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}











	public String getPublicizeArticlePlace() {
		return publicizeArticlePlace;
	}







	public void setPublicizeArticlePlace(String publicizeArticlePlace) {
		this.publicizeArticlePlace = publicizeArticlePlace;
	}







	public String getPublicizeArticlePlanAmount() {
		return publicizeArticlePlanAmount;
	}







	public void setPublicizeArticlePlanAmount(String publicizeArticlePlanAmount) {
		this.publicizeArticlePlanAmount = publicizeArticlePlanAmount;
	}







	public String getPublicizeArticleActualAmount() {
		return publicizeArticleActualAmount;
	}







	public void setPublicizeArticleActualAmount(String publicizeArticleActualAmount) {
		this.publicizeArticleActualAmount = publicizeArticleActualAmount;
	}







	public String getPublicizeArticleType() {
		return publicizeArticleType;
	}







	public void setPublicizeArticleType(String publicizeArticleType) {
		this.publicizeArticleType = publicizeArticleType;
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






	public String getcreatDept() {
		return creatDept;
	}







	public void setcreatDept(String creatDept) {
		this.creatDept = creatDept;
	}







	public String getCreatUser() {
		return creatUser;
	}







	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}







	public String getDeleted() {
		return deleted;
	}







	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}












	public String getFinishTime() {
		return finishTime;
	}







	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}







	public String getId() {
		return id;
	}







	public void setId(String id) {
		this.id = id;
	}







	public String getIncompleteCause() {
		return incompleteCause;
	}







	public void setIncompleteCause(String incompleteCause) {
		this.incompleteCause = incompleteCause;
	}







	public String getModifyDept() {
		return modifyDept;
	}







	public void setModifyDept(String modifyDept) {
		this.modifyDept = modifyDept;
	}







	public String getModifyTime() {
		return modifyTime;
	}







	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}







	public String getModifyUser() {
		return modifyUser;
	}







	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}







	public String getProjectName() {
		return projectName;
	}







	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}







	public String getRemark() {
		return remark;
	}







	public void setRemark(String remark) {
		this.remark = remark;
	}







	public String getCreatDept() {
		return creatDept;
	}







	public void setCreatDept(String creatDept) {
		this.creatDept = creatDept;
	}














	public String getCreatTime() {
		return creatTime;
	}







	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

}



