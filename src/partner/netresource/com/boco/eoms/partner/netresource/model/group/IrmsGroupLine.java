package com.boco.eoms.partner.netresource.model.group;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--集客家客--数据(互联网)专线信息表
 * 创建人： zhangkeqi
 * 创建时间：2013-03-06 17:37:38
 */
public class IrmsGroupLine extends EomsModel{

	/**主键*/
	private String id;
		
	/**客户名称*/
	private String customerName;
		
	/**与【T1.1-客户信息表】的【客户编号】关联*/
	private String relatedInstance;
		
	/**客户接入互联网的地址。*/
	private String customerBusiAddr;
		
	/**客户要求的速率*/
	private String businessBw;
		
	/**枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
	private String tranAccessWay;
		
	/**与【客户端设备信息表】的【客户端客户设备名称】关联*/
	private String relatedCdName;
		
	/**与【客户端设备信息表】的【客户端客户设备端口编号】关联*/
	private String relatedCdPort;
		
	/**SDH、PDH接入时，与“传送网”（外线资源）的【光路】的【末端传输或业务设备名称】关联
采用PON接入时，接入点设备一般是ONU（FTTB）/olt（FTTH），与“传送网（内线逻辑资源）”的【ONU】或【OLT】的【设备名称】关联*/
	private String relatedAdName;
		
	/**SDH、PDH接入时，与“传送网”（外线资源）的【光路】的【末端传输或业务端口名称】关联
采用PON接入时，接入点设备一般是ONU（FTTB）/olt（FTTH），与“传送网（内线逻辑资源）”的【ONU】或【OLT】的【端口名称】关联*/
	private String relatedAdPortNo;
		
	/**与“数据网”的【VLAN信息－单层】的【Vlan ID】关联*/
	private String relatedAdVlan;
		
	/**与“数据网”的【vlan信息－双层】的【外层vlan ID关联】*/
	private String relatedAdSlan;
		
	/**与“数据网”的【vlan信息－双层】的【内层vlan ID关联】*/
	private String relatedAdClan;
		
	/**互联网用途的业务接入时，根据局端设备SW或SR等的名称，与“数据网”的【基础信息】的【机房】关联
GPRS用途的业务接入时，与“GPRS”的【GPRS系统（物理资源）－GGSN】的【设备所在机房】关联
短信和彩信等其它业务用途的接入时，与相关设备网元的【基础信息】的【机房】关联*/
	private String relatedOsrName;
		
	/**互联网用途的业务接入时，与“数据网”的SW或SR等的【面板信息】的【设备名称】关联
GPRS用途的业务接入时，与“GPRS”的【GPRS系统（物理资源）－端口】的【所在设备】关联
短信和彩信等其它业务用途的接入时，与相关设备网元的【面板信息】的【设备名称】关联*/
	private String relatedOsdName;
		
	/**互联网用途的业务接入时，与“数据网”的SW或SR等【面板信息】的【端口名称】关联
GPRS用途的业务接入时，与“GPRS”的【GPRS系统（物理资源）－端口】的【端口名称】关联
短信和彩信用途的业务接入时，与“短彩信网”的【端口名称】关联*/
	private String relatedOsdPort;
		
	/**互联网用途的业务接入时，与“数据网”的【逻辑端口信息】的【IP地址】关联
GPRS用途的业务接入时，与“GPRS”的【GPRS系统（逻辑资源）－IP地址信息】的【IP地址】关联
短信和彩信等其它业务用途的接入时，与相关设备网元的【逻辑端口信息】的【IP地址】关联*/
	private String relatedClientIp;
		
	/**互联网用途的业务接入时，与“数据网”的【逻辑端口信息】的【IP地址】关联
GPRS用途的业务接入时，与“GPRS”的【GPRS系统（逻辑资源）－IP地址信息】的【IP地址】关联
短信和彩信等其它业务用途的接入时，与相关设备网元的【逻辑端口信息】的【IP地址】关联*/
	private String relatedConnectIp;
		
	/**与“传送网资源”（内线逻辑资源）的【传输电路】的【电路路名称】关联*/
	private String relatedCircuitName;
		
	/**与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
	private String relatedOcName;
		
	/**光纤接入客户的日期*/
	private String customerAccessDate;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/***/
	private String relatedInstanceId;
		
	/***/
	private String relatedCdNameId;
		
	/**客户端客户设备端口编号ID*/
	private String relatedCdPortId;
		
	/**移动接入设备名称ID*/
	private String relatedAdNameId;
		
	/**移动接入设备端口编号ID*/
	private String relatedAdPortNoId;
		
	/**接入点设备VLAN编号ID*/
	private String relatedAdVlanId;
		
	/**接入点设备SLAN编号ID*/
	private String relatedAdSlanId;
		
	/**接入点设备CLAN编号ID*/
	private String relatedAdClanId;
		
	/**局端设备名称ID*/
	private String relatedOsdNameId;
		
	/**局端设备端口ID*/
	private String relatedOsdPortId;
		
	/**客户终端IP地址（公网/私网）ID*/
	private String relatedClientIpId;
		
	/**设备互联IP地址ID*/
	private String relatedConnectIpId;
		
	/**电路名称ID*/
	private String relatedCircuitNameId;
		
	/**光路名称ID*/
	private String relatedOcNameId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getRelatedInstance() {
		return this.relatedInstance;
	}
	
	public void setRelatedInstance(String relatedInstance) {
		this.relatedInstance = relatedInstance;
	}
	public String getCustomerBusiAddr() {
		return this.customerBusiAddr;
	}
	
	public void setCustomerBusiAddr(String customerBusiAddr) {
		this.customerBusiAddr = customerBusiAddr;
	}
	public String getBusinessBw() {
		return this.businessBw;
	}
	
	public void setBusinessBw(String businessBw) {
		this.businessBw = businessBw;
	}
	public String getTranAccessWay() {
		return this.tranAccessWay;
	}
	
	public void setTranAccessWay(String tranAccessWay) {
		this.tranAccessWay = tranAccessWay;
	}
	public String getRelatedCdName() {
		return this.relatedCdName;
	}
	
	public void setRelatedCdName(String relatedCdName) {
		this.relatedCdName = relatedCdName;
	}
	public String getRelatedCdPort() {
		return this.relatedCdPort;
	}
	
	public void setRelatedCdPort(String relatedCdPort) {
		this.relatedCdPort = relatedCdPort;
	}
	public String getRelatedAdName() {
		return this.relatedAdName;
	}
	
	public void setRelatedAdName(String relatedAdName) {
		this.relatedAdName = relatedAdName;
	}
	public String getRelatedAdPortNo() {
		return this.relatedAdPortNo;
	}
	
	public void setRelatedAdPortNo(String relatedAdPortNo) {
		this.relatedAdPortNo = relatedAdPortNo;
	}
	public String getRelatedAdVlan() {
		return this.relatedAdVlan;
	}
	
	public void setRelatedAdVlan(String relatedAdVlan) {
		this.relatedAdVlan = relatedAdVlan;
	}
	public String getRelatedAdSlan() {
		return this.relatedAdSlan;
	}
	
	public void setRelatedAdSlan(String relatedAdSlan) {
		this.relatedAdSlan = relatedAdSlan;
	}
	public String getRelatedAdClan() {
		return this.relatedAdClan;
	}
	
	public void setRelatedAdClan(String relatedAdClan) {
		this.relatedAdClan = relatedAdClan;
	}
	public String getRelatedOsrName() {
		return this.relatedOsrName;
	}
	
	public void setRelatedOsrName(String relatedOsrName) {
		this.relatedOsrName = relatedOsrName;
	}
	public String getRelatedOsdName() {
		return this.relatedOsdName;
	}
	
	public void setRelatedOsdName(String relatedOsdName) {
		this.relatedOsdName = relatedOsdName;
	}
	public String getRelatedOsdPort() {
		return this.relatedOsdPort;
	}
	
	public void setRelatedOsdPort(String relatedOsdPort) {
		this.relatedOsdPort = relatedOsdPort;
	}
	public String getRelatedClientIp() {
		return this.relatedClientIp;
	}
	
	public void setRelatedClientIp(String relatedClientIp) {
		this.relatedClientIp = relatedClientIp;
	}
	public String getRelatedConnectIp() {
		return this.relatedConnectIp;
	}
	
	public void setRelatedConnectIp(String relatedConnectIp) {
		this.relatedConnectIp = relatedConnectIp;
	}
	public String getRelatedCircuitName() {
		return this.relatedCircuitName;
	}
	
	public void setRelatedCircuitName(String relatedCircuitName) {
		this.relatedCircuitName = relatedCircuitName;
	}
	public String getRelatedOcName() {
		return this.relatedOcName;
	}
	
	public void setRelatedOcName(String relatedOcName) {
		this.relatedOcName = relatedOcName;
	}
	public String getCustomerAccessDate() {
		return this.customerAccessDate;
	}
	
	public void setCustomerAccessDate(String customerAccessDate) {
		this.customerAccessDate = customerAccessDate;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRelatedInstanceId() {
		return this.relatedInstanceId;
	}
	
	public void setRelatedInstanceId(String relatedInstanceId) {
		this.relatedInstanceId = relatedInstanceId;
	}
	public String getRelatedCdNameId() {
		return this.relatedCdNameId;
	}
	
	public void setRelatedCdNameId(String relatedCdNameId) {
		this.relatedCdNameId = relatedCdNameId;
	}
	public String getRelatedCdPortId() {
		return this.relatedCdPortId;
	}
	
	public void setRelatedCdPortId(String relatedCdPortId) {
		this.relatedCdPortId = relatedCdPortId;
	}
	public String getRelatedAdNameId() {
		return this.relatedAdNameId;
	}
	
	public void setRelatedAdNameId(String relatedAdNameId) {
		this.relatedAdNameId = relatedAdNameId;
	}
	public String getRelatedAdPortNoId() {
		return this.relatedAdPortNoId;
	}
	
	public void setRelatedAdPortNoId(String relatedAdPortNoId) {
		this.relatedAdPortNoId = relatedAdPortNoId;
	}
	public String getRelatedAdVlanId() {
		return this.relatedAdVlanId;
	}
	
	public void setRelatedAdVlanId(String relatedAdVlanId) {
		this.relatedAdVlanId = relatedAdVlanId;
	}
	public String getRelatedAdSlanId() {
		return this.relatedAdSlanId;
	}
	
	public void setRelatedAdSlanId(String relatedAdSlanId) {
		this.relatedAdSlanId = relatedAdSlanId;
	}
	public String getRelatedAdClanId() {
		return this.relatedAdClanId;
	}
	
	public void setRelatedAdClanId(String relatedAdClanId) {
		this.relatedAdClanId = relatedAdClanId;
	}
	public String getRelatedOsdNameId() {
		return this.relatedOsdNameId;
	}
	
	public void setRelatedOsdNameId(String relatedOsdNameId) {
		this.relatedOsdNameId = relatedOsdNameId;
	}
	public String getRelatedOsdPortId() {
		return this.relatedOsdPortId;
	}
	
	public void setRelatedOsdPortId(String relatedOsdPortId) {
		this.relatedOsdPortId = relatedOsdPortId;
	}
	public String getRelatedClientIpId() {
		return this.relatedClientIpId;
	}
	
	public void setRelatedClientIpId(String relatedClientIpId) {
		this.relatedClientIpId = relatedClientIpId;
	}
	public String getRelatedConnectIpId() {
		return this.relatedConnectIpId;
	}
	
	public void setRelatedConnectIpId(String relatedConnectIpId) {
		this.relatedConnectIpId = relatedConnectIpId;
	}
	public String getRelatedCircuitNameId() {
		return this.relatedCircuitNameId;
	}
	
	public void setRelatedCircuitNameId(String relatedCircuitNameId) {
		this.relatedCircuitNameId = relatedCircuitNameId;
	}
	public String getRelatedOcNameId() {
		return this.relatedOcNameId;
	}
	
	public void setRelatedOcNameId(String relatedOcNameId) {
		this.relatedOcNameId = relatedOcNameId;
	}
	
}
