package com.boco.activiti.partner.process.po.scene;

import java.util.List;


public class SceneDetailModel {
	//主场景val值
	private String mainSceneValue;
	
	//主场景中文名
	private String mainSceneName;
	
	//主场景val值
	private String childSceneValue;
	
	//主场景中文名
	private String childSceneName;
	
	//处理措施
	private String treatmentMeasures;
	
	//单位
	private String 	totalUnit;
	
	//单位数（针对特殊情况）
	private String unitNum;
	
	//老单位数（针对特殊情况）
	private String oldUnitNum;
	
	//材料
	private List<MaterialModel> materialList;
	
	//数量
	private String 	num;
	//老数量
	private String oldNum;
	//定额数量
	private String quotaNum;
	//定额老数量
	private String oldQuotaNum;
	
	//单位
	private String 	unit;
	
	//单价
	private String 	price;
	//老单价
	private String oldPrice;
	//初始化价格
	private String initialPrice;
	
	//总额
	private String 	total;
	//材料行--标识
	private String itemNo;

	//是否利旧
	private List<UtilizeModel> utilizeList;

	public List<UtilizeModel> getUtilizeList() {
		return utilizeList;
	}

	public void setUtilizeList(List<UtilizeModel> utilizeList) {
		this.utilizeList = utilizeList;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getMainSceneValue() {
		return mainSceneValue;
	}

	public void setMainSceneValue(String mainSceneValue) {
		this.mainSceneValue = mainSceneValue;
	}

	public String getMainSceneName() {
		return mainSceneName;
	}

	public void setMainSceneName(String mainSceneName) {
		this.mainSceneName = mainSceneName;
	}

	public String getChildSceneValue() {
		return childSceneValue;
	}

	public void setChildSceneValue(String childSceneValue) {
		this.childSceneValue = childSceneValue;
	}

	public String getChildSceneName() {
		return childSceneName;
	}

	public void setChildSceneName(String childSceneName) {
		this.childSceneName = childSceneName;
	}

	public String getTreatmentMeasures() {
		return treatmentMeasures;
	}

	public void setTreatmentMeasures(String treatmentMeasures) {
		this.treatmentMeasures = treatmentMeasures;
	}

	public String getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(String totalUnit) {
		this.totalUnit = totalUnit;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getOldNum() {
		return oldNum;
	}

	public void setOldNum(String oldNum) {
		this.oldNum = oldNum;
	}

	public String getQuotaNum() {
		return quotaNum;
	}

	public void setQuotaNum(String quotaNum) {
		this.quotaNum = quotaNum;
	}

	public String getOldQuotaNum() {
		return oldQuotaNum;
	}

	public void setOldQuotaNum(String oldQuotaNum) {
		this.oldQuotaNum = oldQuotaNum;
	}

	public String getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}

	public String getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(String initialPrice) {
		this.initialPrice = initialPrice;
	}

	public List<MaterialModel> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<MaterialModel> materialList) {
		this.materialList = materialList;
	}

	public String getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}

	public String getOldUnitNum() {
		return oldUnitNum;
	}

	public void setOldUnitNum(String oldUnitNum) {
		this.oldUnitNum = oldUnitNum;
	}
	
}
