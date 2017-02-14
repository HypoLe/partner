package com.boco.eoms.mobile.po;

/**
 * 光交箱经过缆情況
 * 
 * @author whl
 * @version 20161011
 */
public class GjxCableOpticalCableItem {
	
	private String gjxId ;
	/**
	 * 光缆名称
	 */
	private String ofiberName;
	/**
	 * 光缆标号
	 */
	private String coreSequence;
	
	/**
	 * 纤芯数
	 */
	private String fportNum;
	
	/**
	 * 状态
	 */
	private String coreStatus;
	
	public String getGjxId() {
		return gjxId;
	}
	public void setGjxId(String gjxId) {
		this.gjxId = gjxId;
	}
	public String getOfiberName() {
		return ofiberName;
	}
	public void setOfiberName(String ofiberName) {
		this.ofiberName = ofiberName;
	}
	public String getCoreSequence() {
		return coreSequence;
	}
	public void setCoreSequence(String coreSequence) {
		this.coreSequence = coreSequence;
	}
	public String getFportNum() {
		return fportNum;
	}
	public void setFportNum(String fportNum) {
		this.fportNum = fportNum;
	}
	public String getCoreStatus() {
		return coreStatus;
	}
	public void setCoreStatus(String coreStatus) {
		this.coreStatus = coreStatus;
	}
}
