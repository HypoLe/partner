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
 	* @description 公客场景模板统计
 */
public class MaleSceneStatisticsModel implements Serializable {
	private static final long serialVersionUID = 1L;

	//总金额
	private String totalAmount;
	
	//电杆数
	private String poleNum;

	//接头盒数
	private String jointBoxNum;
	
	//光缆长度
	private String opticalCableLength;
	
	public MaleSceneStatisticsModel(){
		
	}

	public MaleSceneStatisticsModel(String totalAmount, String poleNum,
			String jointBoxNum, String opticalCableLength) {
		super();
		this.totalAmount = totalAmount;
		this.poleNum = poleNum;
		this.jointBoxNum = jointBoxNum;
		this.opticalCableLength = opticalCableLength;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPoleNum() {
		return poleNum;
	}

	public void setPoleNum(String poleNum) {
		this.poleNum = poleNum;
	}

	public String getJointBoxNum() {
		return jointBoxNum;
	}

	public void setJointBoxNum(String jointBoxNum) {
		this.jointBoxNum = jointBoxNum;
	}

	public String getOpticalCableLength() {
		return opticalCableLength;
	}

	public void setOpticalCableLength(String opticalCableLength) {
		this.opticalCableLength = opticalCableLength;
	}
}

