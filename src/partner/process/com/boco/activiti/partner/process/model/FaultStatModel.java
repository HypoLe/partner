package com.boco.activiti.partner.process.model;

import java.io.Serializable;

public class FaultStatModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;				
	private String city;				//地市
	private String equipmentNum;		//光缆线路设备量（百皮长公里）
	private String faultSheetNum;		//报障工单数
	private String imputationConfirmNum;	//归集确认故障数
	private String faultRate; 			//故障率（次/百皮长公里）
	private String averageFaultLast;	//平均故障历时
	private String maleGuestsLast;		//公客平均派单时长
	private String lateSheetNum;		//超时工单数量
	private String faultRepairRate;		//故障修复及时率
	private String faultRebuildNum;		//故障重修数
	private String faultRebuildRate;	//故障重修率
	private String materialMoney;		//材料金额
	private String guzhanglishi; //故障历时
	private String gkPaidanshichang; //公客派单时长
    private String gkGuijiquerenNum; //公客归集确认数
    
    private String chaoshigongdanshu;//超时工单数
    private String gkSxnxfNum;//时限内修复故障数
    private String guzhangxiufujishilv;//故障修复及时率
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEquipmentNum() {
		return equipmentNum;
	}
	public void setEquipmentNum(String equipmentNum) {
		this.equipmentNum = equipmentNum;
	}
	public String getFaultSheetNum() {
		return faultSheetNum;
	}
	public void setFaultSheetNum(String faultSheetNum) {
		this.faultSheetNum = faultSheetNum;
	}
	public String getImputationConfirmNum() {
		return imputationConfirmNum;
	}
	public void setImputationConfirmNum(String imputationConfirmNum) {
		this.imputationConfirmNum = imputationConfirmNum;
	}
	public String getFaultRate() {
		return faultRate;
	}
	public void setFaultRate(String faultRate) {
		this.faultRate = faultRate;
	}
	public String getAverageFaultLast() {
		return averageFaultLast;
	}
	public void setAverageFaultLast(String averageFaultLast) {
		this.averageFaultLast = averageFaultLast;
	}
	public String getMaleGuestsLast() {
		return maleGuestsLast;
	}
	public void setMaleGuestsLast(String maleGuestsLast) {
		this.maleGuestsLast = maleGuestsLast;
	}
	public String getLateSheetNum() {
		return lateSheetNum;
	}
	public void setLateSheetNum(String lateSheetNum) {
		this.lateSheetNum = lateSheetNum;
	}
	public String getFaultRepairRate() {
		return faultRepairRate;
	}
	public void setFaultRepairRate(String faultRepairRate) {
		this.faultRepairRate = faultRepairRate;
	}
	public String getFaultRebuildNum() {
		return faultRebuildNum;
	}
	public void setFaultRebuildNum(String faultRebuildNum) {
		this.faultRebuildNum = faultRebuildNum;
	}
	public String getFaultRebuildRate() {
		return faultRebuildRate;
	}
	public void setFaultRebuildRate(String faultRebuildRate) {
		this.faultRebuildRate = faultRebuildRate;
	}
	public String getMaterialMoney() {
		return materialMoney;
	}
	public void setMaterialMoney(String materialMoney) {
		this.materialMoney = materialMoney;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGuzhanglishi() {
		return guzhanglishi;
	}
	public void setGuzhanglishi(String guzhanglishi) {
		this.guzhanglishi = guzhanglishi;
	}
	public String getGkPaidanshichang() {
		return gkPaidanshichang;
	}
	public void setGkPaidanshichang(String gkPaidanshichang) {
		this.gkPaidanshichang = gkPaidanshichang;
	}
	public String getGkGuijiquerenNum() {
		return gkGuijiquerenNum;
	}
	public void setGkGuijiquerenNum(String gkGuijiquerenNum) {
		this.gkGuijiquerenNum = gkGuijiquerenNum;
	}
	public String getChaoshigongdanshu() {
		return chaoshigongdanshu;
	}
	public void setChaoshigongdanshu(String chaoshigongdanshu) {
		this.chaoshigongdanshu = chaoshigongdanshu;
	}
	public String getGkSxnxfNum() {
		return gkSxnxfNum;
	}
	public void setGkSxnxfNum(String gkSxnxfNum) {
		this.gkSxnxfNum = gkSxnxfNum;
	}
	public String getGuzhangxiufujishilv() {
		return guzhangxiufujishilv;
	}
	public void setGuzhangxiufujishilv(String guzhangxiufujishilv) {
		this.guzhangxiufujishilv = guzhangxiufujishilv;
	}
}
