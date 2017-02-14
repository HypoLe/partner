package com.boco.activiti.partner.process.model;

import java.io.Serializable;

public class MasteRelTaskItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String sceneName;
	private String sceneNo;
	private String copyName;
	private String copyNo;
	private String unitName;
	private String unitId;
	private String craftName;
	private String craftId;
	private String modelName;
	private String modelId;
	private String norm;
	private String costPerSale;
	private String costPerNoSale;
	private String discount;
	private String costWork;
	private String mainType;
	private String mainPrice;
	private String auxiliaryType;
	private String auxiliaryPrice;
	private String materialSafeCost;
	private String materialCost;
	private String totalCost;
	private String soilName;
	private String soilId;
	private String secondCostPer;
	private String costNoMaterial;
	private String pipeName;
	private String pipeId;
	private String landformName;
	private String landformId;
	private String costPerNoStandard;
	private String itemNo;
	private String processInstanceId;
	private String num;
	private String costPerNoQuota;
	private String childSceneId;
	
	private String costPer;
	private String materialPer;
	
	private String mainHid;
	private String assistHid;
	private String quotavalueHid;
	private String informainHid;
	private String inforassistHid;
	private String totalcostmainHid;
	private String totalcostassistHid;
	
	//其他费用（手工填写） 描述
	private String otherCostDescription;
	// 唯一编码，区分同一个定额的不同数据
	private String uniqueId;
	//排序
	private Integer seq; 
	//(手填)场景名称
	private String customSceneName;
	
	//环节类型
	private String linkType;
	
	public String getChildSceneId() {
		return childSceneId;
	}
	public void setChildSceneId(String childSceneId) {
		this.childSceneId = childSceneId;
	}
	public String getMainHid() {
		return mainHid;
	}
	public void setMainHid(String mainHid) {
		this.mainHid = mainHid;
	}
	public String getAssistHid() {
		return assistHid;
	}
	public void setAssistHid(String assistHid) {
		this.assistHid = assistHid;
	}
	public String getQuotavalueHid() {
		return quotavalueHid;
	}
	public void setQuotavalueHid(String quotavalueHid) {
		this.quotavalueHid = quotavalueHid;
	}
	public String getInformainHid() {
		return informainHid;
	}
	public void setInformainHid(String informainHid) {
		this.informainHid = informainHid;
	}
	public String getInforassistHid() {
		return inforassistHid;
	}
	public void setInforassistHid(String inforassistHid) {
		this.inforassistHid = inforassistHid;
	}
	public String getTotalcostmainHid() {
		return totalcostmainHid;
	}
	public void setTotalcostmainHid(String totalcostmainHid) {
		this.totalcostmainHid = totalcostmainHid;
	}
	public String getTotalcostassistHid() {
		return totalcostassistHid;
	}
	public void setTotalcostassistHid(String totalcostassistHid) {
		this.totalcostassistHid = totalcostassistHid;
	}
	public String getCostNoMaterial() {
		return costNoMaterial;
	}
	public void setCostNoMaterial(String costNoMaterial) {
		this.costNoMaterial = costNoMaterial;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSceneName() {
		return sceneName;
	}
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}
	public String getSceneNo() {
		return sceneNo;
	}
	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}
	public String getCopyName() {
		return copyName;
	}
	public void setCopyName(String copyName) {
		this.copyName = copyName;
	}
	public String getCopyNo() {
		return copyNo;
	}
	public void setCopyNo(String copyNo) {
		this.copyNo = copyNo;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getCraftName() {
		return craftName;
	}
	public void setCraftName(String craftName) {
		this.craftName = craftName;
	}
	public String getCraftId() {
		return craftId;
	}
	public void setCraftId(String craftId) {
		this.craftId = craftId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getNorm() {
		return norm;
	}
	public void setNorm(String norm) {
		this.norm = norm;
	}
	public String getCostPerSale() {
		return costPerSale;
	}
	public void setCostPerSale(String costPerSale) {
		this.costPerSale = costPerSale;
	}
	public String getCostPerNoSale() {
		return costPerNoSale;
	}
	public void setCostPerNoSale(String costPerNoSale) {
		this.costPerNoSale = costPerNoSale;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	

	public String getCostWork() {
		return costWork;
	}
	public void setCostWork(String costWork) {
		this.costWork = costWork;
	}
	public String getMainType() {
		return mainType;
	}
	public void setMainType(String mainType) {
		this.mainType = mainType;
	}
	public String getMainPrice() {
		return mainPrice;
	}
	public void setMainPrice(String mainPrice) {
		this.mainPrice = mainPrice;
	}
	public String getAuxiliaryType() {
		return auxiliaryType;
	}
	public void setAuxiliaryType(String auxiliaryType) {
		this.auxiliaryType = auxiliaryType;
	}
	public String getAuxiliaryPrice() {
		return auxiliaryPrice;
	}
	public void setAuxiliaryPrice(String auxiliaryPrice) {
		this.auxiliaryPrice = auxiliaryPrice;
	}
	public String getMaterialSafeCost() {
		return materialSafeCost;
	}
	public void setMaterialSafeCost(String materialSafeCost) {
		this.materialSafeCost = materialSafeCost;
	}
	public String getMaterialCost() {
		return materialCost;
	}
	public void setMaterialCost(String materialCost) {
		this.materialCost = materialCost;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public String getSoilName() {
		return soilName;
	}
	public void setSoilName(String soilName) {
		this.soilName = soilName;
	}
	public String getSoilId() {
		return soilId;
	}
	public void setSoilId(String soilId) {
		this.soilId = soilId;
	}
	public String getSecondCostPer() {
		return secondCostPer;
	}
	public void setSecondCostPer(String secondCostPer) {
		this.secondCostPer = secondCostPer;
	}
	
	public String getPipeName() {
		return pipeName;
	}
	public void setPipeName(String pipeName) {
		this.pipeName = pipeName;
	}
	public String getPipeId() {
		return pipeId;
	}
	public void setPipeId(String pipeId) {
		this.pipeId = pipeId;
	}
	public String getLandformName() {
		return landformName;
	}
	public void setLandformName(String landformName) {
		this.landformName = landformName;
	}
	public String getLandformId() {
		return landformId;
	}
	public void setLandformId(String landformId) {
		this.landformId = landformId;
	}
	public String getCostPerNoStandard() {
		return costPerNoStandard;
	}
	public void setCostPerNoStandard(String costPerNoStandard) {
		this.costPerNoStandard = costPerNoStandard;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getCostPerNoQuota() {
		return costPerNoQuota;
	}
	public void setCostPerNoQuota(String costPerNoQuota) {
		this.costPerNoQuota = costPerNoQuota;
	}
	
	public String getCostPer() {
		return costPer;
	}
	public void setCostPer(String costPer) {
		this.costPer = costPer;
	}
	public String getMaterialPer() {
		return materialPer;
	}
	public void setMaterialPer(String materialPer) {
		this.materialPer = materialPer;
	}
	
	public MasteRelTaskItem() {
	}
	public String getOtherCostDescription() {
		return otherCostDescription;
	}
	public void setOtherCostDescription(String otherCostDescription) {
		this.otherCostDescription = otherCostDescription;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getCustomSceneName() {
		return customSceneName;
	}
	public void setCustomSceneName(String customSceneName) {
		this.customSceneName = customSceneName;
	}
	public String getLinkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	
	
	
}
