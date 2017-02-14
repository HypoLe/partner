package com.boco.activiti.partner.process.po;

import java.io.Serializable;

public class MaterialModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id ;
	private String sortNum;//序号
	private String name;//名称
	private String standard;//规格程式
	private String unit;//单位
	private double num;//数量
	private double perPrice;//单价
	private double totalPrice;//合价
	private String itemNo;//定额编码
	private String type;//1:主材；0：辅材
	
	private double originalPerPrice;//基准单价
	private double originalNum;//基准数量
	private String isWhetherOld;//是否利旧
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSortNum() {
		return sortNum;
	}
	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getNum() {
		return num;
	}
	public void setNum(double num) {
		this.num = num;
	}
	public double getPerPrice() {
		return perPrice;
	}
	public void setPerPrice(double perPrice) {
		this.perPrice = perPrice;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MaterialModel() {
	}
	public double getOriginalPerPrice() {
		return originalPerPrice;
	}
	public void setOriginalPerPrice(double originalPerPrice) {
		this.originalPerPrice = originalPerPrice;
	}
	public double getOriginalNum() {
		return originalNum;
	}
	public void setOriginalNum(double originalNum) {
		this.originalNum = originalNum;
	}
	public String getIsWhetherOld() {
		return isWhetherOld;
	}
	public void setIsWhetherOld(String isWhetherOld) {
		this.isWhetherOld = isWhetherOld;
	}
	
	

}
