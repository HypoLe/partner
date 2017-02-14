package com.boco.eoms.sheet.base.model;

/** 
 * Description: 工单查询相关表
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 28, 2013 10:59:27 AM
 */
public class PnrSheetQuery {
	private String id;              //ID
	private String mainId;          //MAIN表ID
	private String provincePnrDept; //省代维公司部门
	private String cityPnrDept;     //地市代维公司部门 
	private String countryPnrDept;  //区县代维公司部门
	private String groupPnrDept;    //小组代维公司部门
	private String sheetType;       //工单种类
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	public String getProvincePnrDept() {
		return provincePnrDept;
	}
	public void setProvincePnrDept(String provincePnrDept) {
		this.provincePnrDept = provincePnrDept;
	}
	public String getCityPnrDept() {
		return cityPnrDept;
	}
	public void setCityPnrDept(String cityPnrDept) {
		this.cityPnrDept = cityPnrDept;
	}
	public String getCountryPnrDept() {
		return countryPnrDept;
	}
	public void setCountryPnrDept(String countryPnrDept) {
		this.countryPnrDept = countryPnrDept;
	}
	public String getGroupPnrDept() {
		return groupPnrDept;
	}
	public void setGroupPnrDept(String groupPnrDept) {
		this.groupPnrDept = groupPnrDept;
	}
	public String getSheetType() {
		return sheetType;
	}
	public void setSheetType(String sheetType) {
		this.sheetType = sheetType;
	}
	
}
