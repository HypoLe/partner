package com.boco.activiti.partner.process.model;

import java.io.Serializable;

/**
 * olt-bbu机房信息基础表
 * 
 * @author Jun
 * 
 */
public class PnrOltBbuRoom implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 主键
	private String id;
	// olt OLT机房优化清单；bbu BBU机房优化清单；oltbbu OLT、BBU共站址机房；
	private String themeInterface;
	// 序号
	private String serialNumber;
	// 机房地市
	private String jfCity;
	// 机房区县
	private String jfCountry;
	// 设备位置 局址名称
	private String jfAddressName;
	// 机房内OLT数量
	private String oltNumber;
	// 总用户数
	private String totalUserNumber;
	// 语音用户
	private String voiceUser;
	// 宽带用户
	private String broadbandUser;
	// IPTV用户
	private String iptvUser;
	// 机房内有无BBU
	private String isNoBbu;
	// 机房内BBU数量
	private String bbuNumber;
	// 是否需要杆路投资
	private String isNeedRodInvestment;
	// 是否需要光缆投资
	private String isNeedCableInvestment;
	// 新建杆路长度（1000M以内）
	private String newBuiltRodLength;
	// 新建杆路投资
	private String newBuiltRodInvestment;
	// 原光缆路由简述（如A-B-C-D）
	private String originalCableRoute;
	// 新光缆路由简述（如A-E-F-D）
	private String newCableRoute;
	// 新建段落（如E-F）
	private String newParagraph;
	// 新建线路路由简图（插入新建E-F段路由图）
	private String newLineRoutingDiagram;
	// 光缆布放芯数
	private String cableClothCoreNumber;
	// 光缆布放长度（5KM以内）
	private String cableClothLength;
	// 光缆投资估算
	private String cableInvestment;
	// 线路总投资估算
	private String lineTotalInvestment;
	// 设备板卡需求
	private String boardDemand;
	// 设备光模块需求
	private String lightModuleDemand;
	// 传输设备板卡需求
	private String transBoardDemand;
	// 传输设备光模块需求
	private String transLightModuleDemand;
	// 设备类投资估算
	private String equipmentInvestment;
	// 备注
	private String jfRemark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getThemeInterface() {
		return themeInterface;
	}

	public void setThemeInterface(String themeInterface) {
		this.themeInterface = themeInterface;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getJfCity() {
		return jfCity;
	}

	public void setJfCity(String jfCity) {
		this.jfCity = jfCity;
	}

	public String getJfCountry() {
		return jfCountry;
	}

	public void setJfCountry(String jfCountry) {
		this.jfCountry = jfCountry;
	}

	public String getJfAddressName() {
		return jfAddressName;
	}

	public void setJfAddressName(String jfAddressName) {
		this.jfAddressName = jfAddressName;
	}

	public String getOltNumber() {
		return oltNumber;
	}

	public void setOltNumber(String oltNumber) {
		this.oltNumber = oltNumber;
	}

	public String getTotalUserNumber() {
		return totalUserNumber;
	}

	public void setTotalUserNumber(String totalUserNumber) {
		this.totalUserNumber = totalUserNumber;
	}

	public String getVoiceUser() {
		return voiceUser;
	}

	public void setVoiceUser(String voiceUser) {
		this.voiceUser = voiceUser;
	}

	public String getBroadbandUser() {
		return broadbandUser;
	}

	public void setBroadbandUser(String broadbandUser) {
		this.broadbandUser = broadbandUser;
	}

	public String getIptvUser() {
		return iptvUser;
	}

	public void setIptvUser(String iptvUser) {
		this.iptvUser = iptvUser;
	}

	public String getIsNoBbu() {
		return isNoBbu;
	}

	public void setIsNoBbu(String isNoBbu) {
		this.isNoBbu = isNoBbu;
	}

	public String getBbuNumber() {
		return bbuNumber;
	}

	public void setBbuNumber(String bbuNumber) {
		this.bbuNumber = bbuNumber;
	}

	public String getIsNeedRodInvestment() {
		return isNeedRodInvestment;
	}

	public void setIsNeedRodInvestment(String isNeedRodInvestment) {
		this.isNeedRodInvestment = isNeedRodInvestment;
	}

	public String getIsNeedCableInvestment() {
		return isNeedCableInvestment;
	}

	public void setIsNeedCableInvestment(String isNeedCableInvestment) {
		this.isNeedCableInvestment = isNeedCableInvestment;
	}

	public String getNewBuiltRodLength() {
		return newBuiltRodLength;
	}

	public void setNewBuiltRodLength(String newBuiltRodLength) {
		this.newBuiltRodLength = newBuiltRodLength;
	}

	public String getNewBuiltRodInvestment() {
		return newBuiltRodInvestment;
	}

	public void setNewBuiltRodInvestment(String newBuiltRodInvestment) {
		this.newBuiltRodInvestment = newBuiltRodInvestment;
	}

	public String getOriginalCableRoute() {
		return originalCableRoute;
	}

	public void setOriginalCableRoute(String originalCableRoute) {
		this.originalCableRoute = originalCableRoute;
	}

	public String getNewCableRoute() {
		return newCableRoute;
	}

	public void setNewCableRoute(String newCableRoute) {
		this.newCableRoute = newCableRoute;
	}

	public String getNewParagraph() {
		return newParagraph;
	}

	public void setNewParagraph(String newParagraph) {
		this.newParagraph = newParagraph;
	}

	public String getNewLineRoutingDiagram() {
		return newLineRoutingDiagram;
	}

	public void setNewLineRoutingDiagram(String newLineRoutingDiagram) {
		this.newLineRoutingDiagram = newLineRoutingDiagram;
	}

	public String getCableClothCoreNumber() {
		return cableClothCoreNumber;
	}

	public void setCableClothCoreNumber(String cableClothCoreNumber) {
		this.cableClothCoreNumber = cableClothCoreNumber;
	}

	public String getCableClothLength() {
		return cableClothLength;
	}

	public void setCableClothLength(String cableClothLength) {
		this.cableClothLength = cableClothLength;
	}

	public String getCableInvestment() {
		return cableInvestment;
	}

	public void setCableInvestment(String cableInvestment) {
		this.cableInvestment = cableInvestment;
	}

	public String getLineTotalInvestment() {
		return lineTotalInvestment;
	}

	public void setLineTotalInvestment(String lineTotalInvestment) {
		this.lineTotalInvestment = lineTotalInvestment;
	}

	public String getBoardDemand() {
		return boardDemand;
	}

	public void setBoardDemand(String boardDemand) {
		this.boardDemand = boardDemand;
	}

	public String getLightModuleDemand() {
		return lightModuleDemand;
	}

	public void setLightModuleDemand(String lightModuleDemand) {
		this.lightModuleDemand = lightModuleDemand;
	}

	public String getTransBoardDemand() {
		return transBoardDemand;
	}

	public void setTransBoardDemand(String transBoardDemand) {
		this.transBoardDemand = transBoardDemand;
	}

	public String getTransLightModuleDemand() {
		return transLightModuleDemand;
	}

	public void setTransLightModuleDemand(String transLightModuleDemand) {
		this.transLightModuleDemand = transLightModuleDemand;
	}

	public String getEquipmentInvestment() {
		return equipmentInvestment;
	}

	public void setEquipmentInvestment(String equipmentInvestment) {
		this.equipmentInvestment = equipmentInvestment;
	}

	public String getJfRemark() {
		return jfRemark;
	}

	public void setJfRemark(String jfRemark) {
		this.jfRemark = jfRemark;
	}

}
