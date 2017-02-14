package com.boco.activiti.partner.process.po;

import java.io.Serializable;

public class PrecheckRoleModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**市公司*/
	private String cityCompany;
	/**省线路维护中心*/
	private String provinceLineExamine;	
	/**省线运维部*/
	private String provinceManageExamine;
	/**省线路分管总经理*/
	private String provinceLineConductor;
	/**省运维分管总经理*/
	private String provinceManageConductor;
	
	/**是否干线处的金额*/
	private String firstMoneyLimitDicId;
	/**派单审批之后的金额*/
	private String secondMoneyLimitDicId;
	/**第三处的金额限制*/
	private String threeMoneyLimtDicId;
	/**最后的金额限制*/
	private String fourMoneyLimtDicId;
	/**干线预检预修--第一处金额限制*/
	private String arteryFirstDicId;
	/**干线预检预修--第二处金额限制*/
	private String arteryLastDicId;
	// 手机端巡检图片存放的远端windows共享地址
	private String imgInspectPublicUrl;
	// 手机工单相关的图片存放的远端windows共享地址
	private String imgWorksheetPublicUrl;
	
	//机房拆除-省公司运维部主管
	private String roomDemolitionProvinceManage ;
	
	// 交接箱核查的图片存放的远端windows共享地址
	private String imgJunctionBoxCheckPublicUrl;
	
	//干线 省线路主管
	private String arteryProvinceLineExamine;
	
	//干线 省线路总经理
	private String arteryProvinceLineConductor;
	
	//网络资源巡检众筹 设备专业 综合调度环节 调度人
	private String equipmentDispatcher;
	
	//网络资源巡检众筹 线路专业 综合调度环节 调度人
	private String circuitDispatcher;

	
	public String getCityCompany() {
		return cityCompany;
	}
	public void setCityCompany(String cityCompany) {
		this.cityCompany = cityCompany;
	}
	public String getProvinceLineExamine() {
		return provinceLineExamine;
	}
	public void setProvinceLineExamine(String provinceLineExamine) {
		this.provinceLineExamine = provinceLineExamine;
	}
	public String getProvinceManageExamine() {
		return provinceManageExamine;
	}
	public void setProvinceManageExamine(String provinceManageExamine) {
		this.provinceManageExamine = provinceManageExamine;
	}
	public String getFirstMoneyLimitDicId() {
		return firstMoneyLimitDicId;
	}
	public void setFirstMoneyLimitDicId(String firstMoneyLimitDicId) {
		this.firstMoneyLimitDicId = firstMoneyLimitDicId;
	}
	public String getSecondMoneyLimitDicId() {
		return secondMoneyLimitDicId;
	}
	public void setSecondMoneyLimitDicId(String secondMoneyLimitDicId) {
		this.secondMoneyLimitDicId = secondMoneyLimitDicId;
	}
	public String getThreeMoneyLimtDicId() {
		return threeMoneyLimtDicId;
	}
	public void setThreeMoneyLimtDicId(String threeMoneyLimtDicId) {
		this.threeMoneyLimtDicId = threeMoneyLimtDicId;
	}
	public String getFourMoneyLimtDicId() {
		return fourMoneyLimtDicId;
	}
	public void setFourMoneyLimtDicId(String fourMoneyLimtDicId) {
		this.fourMoneyLimtDicId = fourMoneyLimtDicId;
	}
	public String getProvinceLineConductor() {
		return provinceLineConductor;
	}
	public void setProvinceLineConductor(String provinceLineConductor) {
		this.provinceLineConductor = provinceLineConductor;
	}
	public String getProvinceManageConductor() {
		return provinceManageConductor;
	}
	public void setProvinceManageConductor(String provinceManageConductor) {
		this.provinceManageConductor = provinceManageConductor;
	}
	public String getArteryFirstDicId() {
		return arteryFirstDicId;
	}
	public void setArteryFirstDicId(String arteryFirstDicId) {
		this.arteryFirstDicId = arteryFirstDicId;
	}
	public String getArteryLastDicId() {
		return arteryLastDicId;
	}
	public void setArteryLastDicId(String arteryLastDicId) {
		this.arteryLastDicId = arteryLastDicId;
	}
	public String getImgInspectPublicUrl() {
		return imgInspectPublicUrl;
	}
	public void setImgInspectPublicUrl(String imgInspectPublicUrl) {
		this.imgInspectPublicUrl = imgInspectPublicUrl;
	}
	public String getImgWorksheetPublicUrl() {
		return imgWorksheetPublicUrl;
	}
	public void setImgWorksheetPublicUrl(String imgWorksheetPublicUrl) {
		this.imgWorksheetPublicUrl = imgWorksheetPublicUrl;
	}
	public String getRoomDemolitionProvinceManage() {
		return roomDemolitionProvinceManage;
	}
	public void setRoomDemolitionProvinceManage(String roomDemolitionProvinceManage) {
		this.roomDemolitionProvinceManage = roomDemolitionProvinceManage;
	}
	public String getImgJunctionBoxCheckPublicUrl() {
		return imgJunctionBoxCheckPublicUrl;
	}
	public void setImgJunctionBoxCheckPublicUrl(String imgJunctionBoxCheckPublicUrl) {
		this.imgJunctionBoxCheckPublicUrl = imgJunctionBoxCheckPublicUrl;
	}
	public String getArteryProvinceLineExamine() {
		return arteryProvinceLineExamine;
	}
	public void setArteryProvinceLineExamine(String arteryProvinceLineExamine) {
		this.arteryProvinceLineExamine = arteryProvinceLineExamine;
	}
	public String getArteryProvinceLineConductor() {
		return arteryProvinceLineConductor;
	}
	public void setArteryProvinceLineConductor(String arteryProvinceLineConductor) {
		this.arteryProvinceLineConductor = arteryProvinceLineConductor;
	}
	public String getEquipmentDispatcher() {
		return equipmentDispatcher;
	}
	public void setEquipmentDispatcher(String equipmentDispatcher) {
		this.equipmentDispatcher = equipmentDispatcher;
	}
	public String getCircuitDispatcher() {
		return circuitDispatcher;
	}
	public void setCircuitDispatcher(String circuitDispatcher) {
		this.circuitDispatcher = circuitDispatcher;
	}
}
