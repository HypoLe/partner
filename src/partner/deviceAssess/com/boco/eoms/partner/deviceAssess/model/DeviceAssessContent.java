package com.boco.eoms.partner.deviceAssess.model;

import com.boco.eoms.base.model.BaseObject;

/** 
 * Description: 事件审批意见表 
 * Copyright:   Copyright (c)2011
 * Company:     BOCO
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 2, 2011 2:59:37 PM 
 */
public class DeviceAssessContent extends BaseObject {

	private static final long serialVersionUID = -3668920805081753437L;
	private String id;
	private String approveId; //统一待审批表ID (DeviceAssessApprove_ID)
	private String assessId;  //事件ID (各模块私有UUID)
	private String content;   //意见
	private String remark;    //备注
	private String commitTime;//提交时间
	private String approveUser;//审批人
	private Integer state;    //审批动作（1同意 0驳回）
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getApproveId() {
		return approveId;
	}



	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}




	public String getAssessId() {
		return assessId;
	}



	public void setAssessId(String assessId) {
		this.assessId = assessId;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getCommitTime() {
		return commitTime;
	}



	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}



	public Integer getState() {
		return state;
	}



	public void setState(Integer state) {
		this.state = state;
	}



	@Override
	public boolean equals(Object o) {
		if( o instanceof Counsel ) {
			Counsel counsel=(Counsel)o;
			if (this.id != null || this.id.equals(counsel.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

}
