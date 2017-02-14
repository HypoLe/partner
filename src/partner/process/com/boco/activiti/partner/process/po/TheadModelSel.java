package com.boco.activiti.partner.process.po;

import java.io.Serializable;

/**
 * 
 * 场景模板动态设置下拉值，获取折扣率等
 *
 */
public class TheadModelSel implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String proEnglishName ;
	private String proValue ;
	private String itemNo;
	private String uniqueId;//每一行的唯一标识（为了区分相同定额数据）
	
	
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getProEnglishName() {
		return proEnglishName;
	}
	public void setProEnglishName(String proEnglishName) {
		this.proEnglishName = proEnglishName;
	}
	public String getProValue() {
		return proValue;
	}
	public void setProValue(String proValue) {
		this.proValue = proValue;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	
}
