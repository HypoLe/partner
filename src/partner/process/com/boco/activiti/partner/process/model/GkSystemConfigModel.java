package com.boco.activiti.partner.process.model;

import java.io.Serializable;

/**
 * 与公客系统的接口之间的配置信息
 * @author chenbing 
 *
 */
public class GkSystemConfigModel implements Serializable{
//	公客系统的访问地址
	private  String gkInterfaceUrl;
//	山东巡检初始化调度人
	private  String troubleInitiator;
//	状态接口
	private  String statusMethod;
//	回单接口
	private  String receiptMethod;
//  公客提供的统一方法
	private  String unifyMethod;	
//  公客接口工单访问地址
	private String gkInterfaceMaleGuestUrl;
//  公客接口--状态接收
	private String maleGuestStatusMethod;
//  公客接口--工单回单	
	private String maleGuestReceiptMethod;
//	公客工单接口统一方法
	private String maleGuestUnifyMethod;
//  预检预修工单访问地址	
	private String transferInterfaceUrl;
//  预检预修--归档提示	
	private String transferInterfaceOverMethod;
//  预检预修工单统一方法	
	private String transferInterfaceUnifyMethod;
	
	public String getUnifyMethod() {
		return unifyMethod;
	}
	public void setUnifyMethod(String unifyMethod) {
		this.unifyMethod = unifyMethod;
	}
	public String getGkInterfaceUrl() {
		return gkInterfaceUrl;
	}
	public void setGkInterfaceUrl(String gkInterfaceUrl) {
		this.gkInterfaceUrl = gkInterfaceUrl;
	}
	public String getTroubleInitiator() {
		return troubleInitiator;
	}
	public void setTroubleInitiator(String troubleInitiator) {
		this.troubleInitiator = troubleInitiator;
	}
	
	public String getStatusMethod() {
		return statusMethod;
	}
	public void setStatusMethod(String statusMethod) {
		this.statusMethod = statusMethod;
	}
	public String getReceiptMethod() {
		return receiptMethod;
	}
	public void setReceiptMethod(String receiptMethod) {
		this.receiptMethod = receiptMethod;
	}
	public GkSystemConfigModel() {
		
	}
	public String getGkInterfaceMaleGuestUrl() {
		return gkInterfaceMaleGuestUrl;
	}
	public void setGkInterfaceMaleGuestUrl(String gkInterfaceMaleGuestUrl) {
		this.gkInterfaceMaleGuestUrl = gkInterfaceMaleGuestUrl;
	}
	public String getMaleGuestStatusMethod() {
		return maleGuestStatusMethod;
	}
	public void setMaleGuestStatusMethod(String maleGuestStatusMethod) {
		this.maleGuestStatusMethod = maleGuestStatusMethod;
	}
	public String getMaleGuestReceiptMethod() {
		return maleGuestReceiptMethod;
	}
	public void setMaleGuestReceiptMethod(String maleGuestReceiptMethod) {
		this.maleGuestReceiptMethod = maleGuestReceiptMethod;
	}
	public String getMaleGuestUnifyMethod() {
		return maleGuestUnifyMethod;
	}
	public void setMaleGuestUnifyMethod(String maleGuestUnifyMethod) {
		this.maleGuestUnifyMethod = maleGuestUnifyMethod;
	}
	public String getTransferInterfaceUrl() {
		return transferInterfaceUrl;
	}
	public void setTransferInterfaceUrl(String transferInterfaceUrl) {
		this.transferInterfaceUrl = transferInterfaceUrl;
	}
	public String getTransferInterfaceOverMethod() {
		return transferInterfaceOverMethod;
	}
	public void setTransferInterfaceOverMethod(String transferInterfaceOverMethod) {
		this.transferInterfaceOverMethod = transferInterfaceOverMethod;
	}
	public String getTransferInterfaceUnifyMethod() {
		return transferInterfaceUnifyMethod;
	}
	public void setTransferInterfaceUnifyMethod(String transferInterfaceUnifyMethod) {
		this.transferInterfaceUnifyMethod = transferInterfaceUnifyMethod;
	}
	
	
}
