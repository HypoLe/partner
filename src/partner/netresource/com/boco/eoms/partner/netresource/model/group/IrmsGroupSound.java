package com.boco.eoms.partner.netresource.model.group;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--集客家客--语音专线信息表
 * 创建人： zhangkeqi
 * 创建时间：2013-03-06 17:37:39
 */
public class IrmsGroupSound extends EomsModel{

	/**主键*/
	private String id;
		
	/**客户名称*/
	private String customerName;
		
	/**与【T1.1-客户信息表】的【客户编号】关联*/
	private String relatedInstance;
		
	/**语音接入码*/
	private String codeSection;
		
	/**选项：本地、省内、省际、国际*/
	private String incomingCallPermissions;
		
	/**PRI/NO.7/NO.1/MFC/DTMF*/
	private String signalingPattern;
		
	/**中继接入速率*/
	private String businessBw;
		
	/**N×2M*/
	private String relayNum;
		
	/**枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
	private String tranAccessWay;
		
	/**客户语音接入地址*/
	private String customerBusAddr;
		
	/**与【客户端设备信息表】的【客户端客户设备名称】关联*/
	private String relatedSdName;
		
	/**与【客户端设备信息表】的【客户端客户设备端口编号】关联*/
	private String relatedSdPort;
		
	/**SDH、PDH接入时，与“传送网”（外线资源）的【光路】的【末端传输或业务设备名称】关联*/
	private String relatedAdName;
		
	/**SDH、PDH接入时，与“传送网”（外线资源）的【光路】的【末端传输或业务端口名称】关联*/
	private String relatedAdPort;
		
	/**经过互联网接入语音专线时与“数据网”的SW/SR的【面板信息】的【设备名称】关联*/
	private String relatedLanName;
		
	/**经过互联网接入语音专线时与“数据网”的SW/SR的【面板信息】的【端口名称】关联*/
	private String relatedLanPort;
		
	/**汇聚语音交换机（PBX/IP PBX/SIP GW/TDM交换机等）设备名称*/
	private String convergedName;
		
	/**汇聚语音交换机（PBX/IP PBX/SIP GW/TDM交换机等）设备的端口名称*/
	private String convergedPort;
		
	/**如果接入TDM设备，与“核心网资源”专业的《端口》的【所属机房】关联*/
	private String relatedOrName;
		
	/**如果接入TDM设备,与“核心网资源”专业的【端口】的【网元名称】关联*/
	private String relatedOrDevice;
		
	/**如果接入TDM设备，
与“核心网资源”专业的【端口】的【端口名称】关联*/
	private String relatedOrDevicePort;
		
	/**与“传送网资源”（内线逻辑资源）的【传输电路】的【电路路名称】关联*/
	private String relatedCircuitName;
		
	/**与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
	private String relatedOcName;
		
	/**IP语音专线接入时，与“数据网”的【逻辑端口信息】的【IP地址】关联*/
	private String clientBusAppIp;
		
	/**接入客户日期:yyyy-MM-dd*/
	private String customerAcsDate;
		
	/**创建时间*/
	private String createTime;
		
	/**备注*/
	private String remark;
		
	/**产品实例标识ID*/
	private String relatedInstanceId;
		
	/**客户端客户设备名称ID*/
	private String relatedSdNameId;
		
	/**客户端客户设备端口编号ID*/
	private String relatedSdPortId;
		
	/**移动接入设备名ID*/
	private String relatedAdNameId;
		
	/**移动接入设备端口编号ID*/
	private String relatedAdPortId;
		
	/**城域网设备名称ID*/
	private String relatedLanNameaId;
		
	/**城域网设备端口编号ID*/
	private String relatedLanPortId;
		
	/**局端设备名称ID*/
	private String relatedOrDeviceId;
		
	/**局端设备端口ID*/
	private String relatedOrDevicePortId;
		
	/**电路名称ID*/
	private String relatedCircuitNameId;
		
	/**光路名称ID*/
	private String relatedOcNameId;
		
	/**客户业务应用IP地址（公网/私网）ID*/
	private String clientBusAppIpId;
		

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
	public String getCodeSection() {
		return this.codeSection;
	}
	
	public void setCodeSection(String codeSection) {
		this.codeSection = codeSection;
	}
	public String getIncomingCallPermissions() {
		return this.incomingCallPermissions;
	}
	
	public void setIncomingCallPermissions(String incomingCallPermissions) {
		this.incomingCallPermissions = incomingCallPermissions;
	}
	public String getSignalingPattern() {
		return this.signalingPattern;
	}
	
	public void setSignalingPattern(String signalingPattern) {
		this.signalingPattern = signalingPattern;
	}
	public String getBusinessBw() {
		return this.businessBw;
	}
	
	public void setBusinessBw(String businessBw) {
		this.businessBw = businessBw;
	}
	public String getRelayNum() {
		return this.relayNum;
	}
	
	public void setRelayNum(String relayNum) {
		this.relayNum = relayNum;
	}
	public String getTranAccessWay() {
		return this.tranAccessWay;
	}
	
	public void setTranAccessWay(String tranAccessWay) {
		this.tranAccessWay = tranAccessWay;
	}
	public String getCustomerBusAddr() {
		return this.customerBusAddr;
	}
	
	public void setCustomerBusAddr(String customerBusAddr) {
		this.customerBusAddr = customerBusAddr;
	}
	public String getRelatedSdName() {
		return this.relatedSdName;
	}
	
	public void setRelatedSdName(String relatedSdName) {
		this.relatedSdName = relatedSdName;
	}
	public String getRelatedSdPort() {
		return this.relatedSdPort;
	}
	
	public void setRelatedSdPort(String relatedSdPort) {
		this.relatedSdPort = relatedSdPort;
	}
	public String getRelatedAdName() {
		return this.relatedAdName;
	}
	
	public void setRelatedAdName(String relatedAdName) {
		this.relatedAdName = relatedAdName;
	}
	public String getRelatedAdPort() {
		return this.relatedAdPort;
	}
	
	public void setRelatedAdPort(String relatedAdPort) {
		this.relatedAdPort = relatedAdPort;
	}
	public String getRelatedLanName() {
		return this.relatedLanName;
	}
	
	public void setRelatedLanName(String relatedLanName) {
		this.relatedLanName = relatedLanName;
	}
	public String getRelatedLanPort() {
		return this.relatedLanPort;
	}
	
	public void setRelatedLanPort(String relatedLanPort) {
		this.relatedLanPort = relatedLanPort;
	}
	public String getConvergedName() {
		return this.convergedName;
	}
	
	public void setConvergedName(String convergedName) {
		this.convergedName = convergedName;
	}
	public String getConvergedPort() {
		return this.convergedPort;
	}
	
	public void setConvergedPort(String convergedPort) {
		this.convergedPort = convergedPort;
	}
	public String getRelatedOrName() {
		return this.relatedOrName;
	}
	
	public void setRelatedOrName(String relatedOrName) {
		this.relatedOrName = relatedOrName;
	}
	public String getRelatedOrDevice() {
		return this.relatedOrDevice;
	}
	
	public void setRelatedOrDevice(String relatedOrDevice) {
		this.relatedOrDevice = relatedOrDevice;
	}
	public String getRelatedOrDevicePort() {
		return this.relatedOrDevicePort;
	}
	
	public void setRelatedOrDevicePort(String relatedOrDevicePort) {
		this.relatedOrDevicePort = relatedOrDevicePort;
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
	public String getClientBusAppIp() {
		return this.clientBusAppIp;
	}
	
	public void setClientBusAppIp(String clientBusAppIp) {
		this.clientBusAppIp = clientBusAppIp;
	}
	public String getCustomerAcsDate() {
		return this.customerAcsDate;
	}
	
	public void setCustomerAcsDate(String customerAcsDate) {
		this.customerAcsDate = customerAcsDate;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRelatedInstanceId() {
		return this.relatedInstanceId;
	}
	
	public void setRelatedInstanceId(String relatedInstanceId) {
		this.relatedInstanceId = relatedInstanceId;
	}
	public String getRelatedSdNameId() {
		return this.relatedSdNameId;
	}
	
	public void setRelatedSdNameId(String relatedSdNameId) {
		this.relatedSdNameId = relatedSdNameId;
	}
	public String getRelatedSdPortId() {
		return this.relatedSdPortId;
	}
	
	public void setRelatedSdPortId(String relatedSdPortId) {
		this.relatedSdPortId = relatedSdPortId;
	}
	public String getRelatedAdNameId() {
		return this.relatedAdNameId;
	}
	
	public void setRelatedAdNameId(String relatedAdNameId) {
		this.relatedAdNameId = relatedAdNameId;
	}
	public String getRelatedAdPortId() {
		return this.relatedAdPortId;
	}
	
	public void setRelatedAdPortId(String relatedAdPortId) {
		this.relatedAdPortId = relatedAdPortId;
	}
	public String getRelatedLanNameaId() {
		return this.relatedLanNameaId;
	}
	
	public void setRelatedLanNameaId(String relatedLanNameaId) {
		this.relatedLanNameaId = relatedLanNameaId;
	}
	public String getRelatedLanPortId() {
		return this.relatedLanPortId;
	}
	
	public void setRelatedLanPortId(String relatedLanPortId) {
		this.relatedLanPortId = relatedLanPortId;
	}
	public String getRelatedOrDeviceId() {
		return this.relatedOrDeviceId;
	}
	
	public void setRelatedOrDeviceId(String relatedOrDeviceId) {
		this.relatedOrDeviceId = relatedOrDeviceId;
	}
	public String getRelatedOrDevicePortId() {
		return this.relatedOrDevicePortId;
	}
	
	public void setRelatedOrDevicePortId(String relatedOrDevicePortId) {
		this.relatedOrDevicePortId = relatedOrDevicePortId;
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
	public String getClientBusAppIpId() {
		return this.clientBusAppIpId;
	}
	
	public void setClientBusAppIpId(String clientBusAppIpId) {
		this.clientBusAppIpId = clientBusAppIpId;
	}
	
}
