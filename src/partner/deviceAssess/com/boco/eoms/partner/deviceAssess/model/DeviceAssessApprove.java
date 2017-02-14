package com.boco.eoms.partner.deviceAssess.model;

import com.boco.eoms.base.model.BaseObject;

/** 
 * Description: 事件统一待审批 
 * Copyright:   Copyright (c)2011
 * Company:     BOCO
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 2, 2011 11:17:41 AM
 */
public class DeviceAssessApprove extends BaseObject {

	private static final long serialVersionUID = 1L;
	private String id;
	private String assessType; //事件类型
	private String assessId;   //事件ID (各模块私有UUID)
	private String sheetNum;   //工单号
	private String name;       //名称
	private String commitTime; //提交时间
	private String approveUser;//审批人
	private String className;  //实体类名
	private String modifyUrl;  //修改URL链接
	private String detailUrl;  //详细查看URL链接
	private Integer state;    //审批状态（0驳回 1通过 2待审批）
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getAssessType() {
		return assessType;
	}

	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}

	public String getAssessId() {
		return assessId;
	}

	public void setAssessId(String assessId) {
		this.assessId = assessId;
	}

	public String getSheetNum() {
		return sheetNum;
	}

	public void setSheetNum(String sheetNum) {
		this.sheetNum = sheetNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getModifyUrl() {
		return modifyUrl;
	}

	public void setModifyUrl(String modifyUrl) {
		this.modifyUrl = modifyUrl;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

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
}
