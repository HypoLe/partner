package com.boco.eoms.deviceManagement.busi.line.model;



import com.boco.eoms.base.model.BaseObject;


public class MaintainReport extends BaseObject {

	private static final long serialVersionUID = 1L;

	private String id; 							// 主键
	
	private String addMan; 					    //提交人
	
	private String maintainNameId;  		    //项目ID

	private String approvalId; 				    //审核ID(备用)
	
	private String approvalUser; 			    //审核人

	private String maintainDate; 				// 实际割接日期
	
	private String useTime; 					// 割接用时
	
	private String peopleNum; 					// 所用人力
	
	private String factualAttenuation;			 // 实际衰耗
	
	private String status;            			 //完成状态,0:未完成,1:完成
	
	private String remark;	
	//备注
	private String addTime;						//备注
	
	/////////////////
	private String remark2;
	private String remark3;
	private String remark4;

	@Override
	public boolean equals(Object o) {
		if (o instanceof MaintainReport) {
			MaintainReport a = (MaintainReport) o;
			if (this.id != null || this.id.equals(a.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddMan() {
		return addMan;
	}

	public void setAddMan(String addMan) {
		this.addMan = addMan;
	}

	public String getMaintainNameId() {
		return maintainNameId;
	}

	public void setMaintainNameId(String maintainNameId) {
		this.maintainNameId = maintainNameId;
	}

	public String getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	public String getMaintainDate() {
		return maintainDate;
	}

	public void setMaintainDate(String maintainDate) {
		this.maintainDate = maintainDate;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getFactualAttenuation() {
		return factualAttenuation;
	}

	public void setFactualAttenuation(String factualAttenuation) {
		this.factualAttenuation = factualAttenuation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}




}



