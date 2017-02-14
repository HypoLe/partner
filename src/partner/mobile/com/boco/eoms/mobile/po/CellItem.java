package com.boco.eoms.mobile.po;

/**
 * 室分 小区
 * 
 * @author Jun
 * 
 */
public class CellItem {

	/*
	 * 基站名称
	 */
	private String siteName;

	/*
	 * 基站id
	 */
	private String siteId;

	/*
	 * 网元编码
	 */
	private String neCode;

	/*
	 * 小区名称
	 */
	private String cellName;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getNeCode() {
		return neCode;
	}

	public void setNeCode(String neCode) {
		this.neCode = neCode;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
}
