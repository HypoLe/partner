package com.boco.eoms.deviceManagement.busi.line.model;



import com.boco.eoms.base.model.BaseObject;


public class MaintainFinish extends BaseObject {

	private static final long serialVersionUID = 1L;

	private String id; 							// 主键
	
	private String addMan; 					    //提交人
	
	private String maintainNameId;  		    //项目ID
	
	private String maintainPlace;  		    	//割接地点
	
	private String maintainReportId;  		    //审核ID

	private String finishResult;            	//验收结果
	
	private String finishRemark;				 //备注
	
	private String addTime;						//添加时间	
	
	private String startTime;					//开始验收时间	 
	
	private String endTime;						//结束验收时间	 	
	/////////////////
	private String remark1;
	private String remark2;
	private String remark3;
	
	

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

	@Override
	public boolean equals(Object o) {
		if (o instanceof MaintainFinish) {
			MaintainFinish a = (MaintainFinish) o;
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

	public String getMaintainReportId() {
		return maintainReportId;
	}

	public void setMaintainReportId(String maintainReportId) {
		this.maintainReportId = maintainReportId;
	}

	public String getFinishResult() {
		return finishResult;
	}

	public void setFinishResult(String finishResult) {
		this.finishResult = finishResult;
	}

	public String getFinishRemark() {
		return finishRemark;
	}

	public void setFinishRemark(String finishRemark) {
		this.finishRemark = finishRemark;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMaintainPlace() {
		return maintainPlace;
	}

	public void setMaintainPlace(String maintainPlace) {
		this.maintainPlace = maintainPlace;
	}




}



