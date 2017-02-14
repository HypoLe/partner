package com.boco.eoms.partner.netresource.util;

import java.io.Serializable;

public class ImportResult implements Serializable {
	

	private String id;
	
	private String resultCode;
	
	private String restultMsg = "";
	
	private int importCount;
	private int errorCount;//错误的条数 2013-07-23
	
	public static final byte successCode = 0;
	
	public static final byte failCode = 1;

	public ImportResult() {
		
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

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public String getRestultMsg() {
		return restultMsg;
	}

	public void setRestultMsg(String restultMsg) {
		this.restultMsg = restultMsg;
	}
}
