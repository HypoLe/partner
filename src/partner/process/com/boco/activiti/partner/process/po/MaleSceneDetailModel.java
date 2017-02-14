package com.boco.activiti.partner.process.po;

import java.io.Serializable;

/**
 * 
 	* @author WangJun
 	* @ClassName: MaleSceneDetailModel
 	* @Version 版本 
 	* @ModifiedBy 修改人
 	* @Copyright 公司名称
 	* @date May 10, 2016 11:33:57 AM
 	* @description 公客场景明细
 */
public class MaleSceneDetailModel implements Serializable {
	private static final long serialVersionUID = 1L;

	//主场景名
	private String sceneName;
	
	//子场景名
	private String copyName;
	
	//处理措施
	private String dispose;

	//总单位
	private String totalUnit;
	
	//材料名
	private String materialName;
	
	//数量
	private String num;
	
	//单位
	private String unit;
	
	//单价
	private String price;
	
	//总额
	private String totalAmount;
	
	//是否利旧
	private String isUtilize;

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getCopyName() {
		return copyName;
	}

	public void setCopyName(String copyName) {
		this.copyName = copyName;
	}

	public String getDispose() {
		return dispose;
	}

	public void setDispose(String dispose) {
		this.dispose = dispose;
	}

	public String getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(String totalUnit) {
		this.totalUnit = totalUnit;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getIsUtilize() {
		return isUtilize;
	}

	public void setIsUtilize(String isUtilize) {
		this.isUtilize = isUtilize;
	}
}

