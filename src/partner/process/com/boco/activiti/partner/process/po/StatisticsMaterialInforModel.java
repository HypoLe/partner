package com.boco.activiti.partner.process.po;

/*
 * 
 	* @author WangJun
 	* @ClassName: StatisticsMaterialInforModel
 	* @Version 版本 
 	* @ModifiedBy 修改人
 	* @Copyright 公司名称
 	* @date May 9, 2016 10:29:48 AM
 	* @description 审批位置统计材料信息
 */
public class StatisticsMaterialInforModel {
	//材料金额和
	private String materialAmountSum; 
	
	//电杆数量和（棵）
	private String  poleNumSum;
	
	//光缆数量和（米）
	private String opticalCableNumSum;
	
	//接头盒数量和（套）
	private String jointBoxNumSum;

	public String getMaterialAmountSum() {
		return materialAmountSum;
	}

	public void setMaterialAmountSum(String materialAmountSum) {
		this.materialAmountSum = materialAmountSum;
	}

	public String getPoleNumSum() {
		return poleNumSum;
	}

	public void setPoleNumSum(String poleNumSum) {
		this.poleNumSum = poleNumSum;
	}

	public String getOpticalCableNumSum() {
		return opticalCableNumSum;
	}

	public void setOpticalCableNumSum(String opticalCableNumSum) {
		this.opticalCableNumSum = opticalCableNumSum;
	}

	public String getJointBoxNumSum() {
		return jointBoxNumSum;
	}

	public void setJointBoxNumSum(String jointBoxNumSum) {
		this.jointBoxNumSum = jointBoxNumSum;
	}
}
