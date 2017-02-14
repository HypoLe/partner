package com.boco.eoms.partner.interfaces.bo;

public class LoadingQueryBO {
	private String eventid;
	private Long statuscode;
	private String statusdescription;
	private String cuid;
	public String getCuid() {
		return cuid;
	}
	public void setCuid(String cuid) {
		this.cuid = cuid;
	}
	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	public Long getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(Long statuscode) {
		this.statuscode = statuscode;
	}
	public String getStatusdescription() {
		return statusdescription;
	}
	public void setStatusdescription(String statusdescription) {
		this.statusdescription = statusdescription;
	}
}
