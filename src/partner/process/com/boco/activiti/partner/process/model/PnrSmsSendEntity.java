package com.boco.activiti.partner.process.model;

import java.io.Serializable;

public class PnrSmsSendEntity implements Serializable{
	
	private String mobile;
	private String context;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public PnrSmsSendEntity() {
		
	}
	
	

}
