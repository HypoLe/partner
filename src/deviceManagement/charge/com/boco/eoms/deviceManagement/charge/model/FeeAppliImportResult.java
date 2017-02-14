package com.boco.eoms.deviceManagement.charge.model;



public class FeeAppliImportResult {
	
	
	private String id;
	
	private String resultCode;
	
	private String restultMsg = "";
	
	private int importCount;
	
	public static final byte successCode = 0;
	
	public static final byte failCode = 1;

	public FeeAppliImportResult() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public int getImportCount() {
		return importCount;
	}

	public void setImportCount(int importCount) {
		this.importCount = importCount;
	}

	public String getRestultMsg() {
		return restultMsg;
	}

	public void setRestultMsg(String restultMsg) {
		this.restultMsg = restultMsg;
	}
}
