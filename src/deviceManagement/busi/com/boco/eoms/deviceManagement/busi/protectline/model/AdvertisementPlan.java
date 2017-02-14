package com.boco.eoms.deviceManagement.busi.protectline.model;

public class AdvertisementPlan {
	
	private String id;
	private String createUserId;
	private String createTime;
	private String passTime;
	private String reviewer;
	private String city;
	private String status;
	private Integer generalStone;
	private Integer detectStone;
	private Integer advertisement;
	private String nextOper;
	private String remarks;

	public AdvertisementPlan() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	

	

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setNextOper(String nextOper) {
		this.nextOper = nextOper;
	}

	public String getNextOper() {
		return nextOper;
	}

	

	public Integer getGeneralStone() {
		return generalStone;
	}

	public void setGeneralStone(Integer generalStone) {
		this.generalStone = generalStone;
	}

	public Integer getDetectStone() {
		return detectStone;
	}

	public void setDetectStone(Integer detectStone) {
		this.detectStone = detectStone;
	}

	public Integer getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Integer advertisement) {
		this.advertisement = advertisement;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

	public String getPassTime() {
		return passTime;
	}
	
	

}
