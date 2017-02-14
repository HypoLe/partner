package com.boco.eoms.deviceManagement.busi.protectline.form;



import com.boco.eoms.base.webapp.form.BaseForm;


public class MediaPublicityForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // 主键

	private String creatUser; //填报人

	private String creatTime; // 填报时间
	
	private String creatDept; // 所属部门

	private String approvalUser; // 提交审核人
	
	private String mediaName; // 项目名称
	
	private String mediaContent;// 媒体宣传内容

	private String mediaStyle; // 媒体宣传形式
	
	private String mediaTime; // 媒体宣传时间
	
	private String mediaAssess; // 宣传效果评估
	
	private String  approvalType;//审核状态
	
	private String  deleted;//删除标示
	
	private String  modifyUser;//修改人
	
	private String  modifyTime;//修改时间
	
	private String  modifyDept;//修改人部门
	
	private String remark; // 备注
	
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	
	
	
	

	
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






	public String getId() {
		return id;
	}






	public void setId(String id) {
		this.id = id;
	}






	public String getMediaAssess() {
		return mediaAssess;
	}






	public void setMediaAssess(String mediaAssess) {
		this.mediaAssess = mediaAssess;
	}






	public String getMediaContent() {
		return mediaContent;
	}






	public void setMediaContent(String mediaContent) {
		this.mediaContent = mediaContent;
	}






	public String getMediaName() {
		return mediaName;
	}






	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}






	public String getMediaStyle() {
		return mediaStyle;
	}






	public void setMediaStyle(String mediaStyle) {
		this.mediaStyle = mediaStyle;
	}






	public String getMediaTime() {
		return mediaTime;
	}






	public void setMediaTime(String mediaTime) {
		this.mediaTime = mediaTime;
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






	public String getRemark() {
		return remark;
	}






	public void setRemark(String remark) {
		this.remark = remark;
	}






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
		if (o instanceof MediaPublicityForm) {
			MediaPublicityForm a = (MediaPublicityForm) o;
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



