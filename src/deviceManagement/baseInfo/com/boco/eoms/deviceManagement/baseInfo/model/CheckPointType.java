package com.boco.eoms.deviceManagement.baseInfo.model;

import java.util.HashMap;
import java.util.Map;


public class CheckPointType{
	private static final long serialVersionUID = 1L;

	private Map<String,String> cpType = new HashMap<String,String>();

	public Map<String, String> getCpType() {
		return cpType;
	}

	public void setCpType(Map<String, String> cpType) {
		this.cpType = cpType;
	}
}