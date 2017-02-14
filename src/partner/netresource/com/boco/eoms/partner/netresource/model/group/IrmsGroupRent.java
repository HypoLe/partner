package com.boco.eoms.partner.netresource.model.group;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--集客家客--出租专线信息表
 * 创建人： zhangkeqi
 * 创建时间：2013-03-06 17:37:39
 */
public class IrmsGroupRent extends EomsModel{

	/**主键*/
	private String id;
		
	/**签订合同的客户名称*/
	private String customerName;
		
	/**开通电路的集团客户名称*/
	private String pointaCustomerName;
		
	/**与【T1.1-客户信息表】的【客户编号】关联*/
	private String relatedInstance;
		
	/**枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
	private String tranAccessWay;
		
	/**客户申请的速率*/
	private String businessBw;
		
	/**A端传输接入的地址信息*/
	private String pointaCustomerAddr;
		
	/**与【客户端设备资源录入模板-来自集团总部】的【客户端客户设备端口类型】关联*/
	private String relatedPointa;
		
	/**与【客户端设备资源录入模板-来自集团总部】的【客户端客户设备端口编号】关联*/
	private String relatedPointaPort;
		
	/**A端客户设备ODF架端口*/
	private String pointaDdfodfPort;
		
	/**SDH、PDH、裸光纤接入时，与“传送网资源”（内线逻辑资源）的【传输网元】的【传输网元名称】关联*/
	private String relatedArDevice;
		
	/**SDH、PDH、裸光纤接入时，与“传送网资源”（内线物理资源）的【光口】的【传输端口名称】关联*/
	private String relatedArDevicePort;
		
	/**SDH、PDH、裸光纤接入时，与“传送网资源”（内线物理资源）的【光口】的【传输侧ODF端子名称】关联*/
	private String relatedArdOdfPort;
		
	/**与“传送网资源”（内线逻辑资源）的【传输电路】的【本端传输网元端口】关联*/
	private String relatedCircuitName;
		
	/**与“传送网资源”（内线逻辑资源）的【传输电路】的【本端传输网元端口】关联*/
	private String relatedCircuitType;
		
	/**与“传送网资源”（内线逻辑资源）的【传输电路】的【电路级别】关联*/
	private String relatedCircuitLevel;
		
	/**根据电路的要求情况分为：新增、停闭、调整*/
	private String circuitRequirement;
		
	/**描述该电路的保护方式（单链、双路由、双路由双节点、环保护）*/
	private String circuitProtectWay;
		
	/**业务使用方局向，可以为业务设备或者业务站点*/
	private String officeRedirection;
		
	/**开通电路的集团客户名称*/
	private String pointzCustomerName;
		
	/**Z端传输接入的地址信息（如果是传输专线，则Z端必须填写，如果只是负责接入语音或互联网，则Z端地址可以不填）*/
	private String pointzCustomerAddr;
		
	/**与【客户端设备资源录入模板-来自集团总部】的【客户端客户设备端口类型】关联*/
	private String relatedPointzName;
		
	/**与【客户端设备资源录入模板-来自集团总部】的【客户端客户设备端口编号】关联*/
	private String relatedPointzPort;
		
	/**Z端客户设备ODF架端口*/
	private String pointzDdfodfPort;
		
	/**SDH、PDH、裸光纤接入时，与“传送网资源”（内线逻辑资源）的【传输网元】的【传输网元名称】关联*/
	private String relatedPointzArName;
		
	/**SDH、PDH、裸光纤接入时，与“传送网资源”（内线物理资源）的【光口】的【传输端口名称】关联*/
	private String relatedPointzArPort;
		
	/**SDH、PDH、裸光纤接入时，与“传送网资源”（内线物理资源）的【光口】的【传输侧ODF端子名称】关联*/
	private String relatedPointzArpOdf;
		
	/**与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
	private String relatedOcName;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**产品实例标识ID*/
	private String relatedInstanceId;
		
	/**A 端业务设备名称ID*/
	private String relatedPointaId;
		
	/**A 端业务设备端口ID*/
	private String relatedPointaPortId;
		
	/**A端接入机房设备名称ID*/
	private String relatedArDeviceId;
		
	/**A端接入机房设备端口ID*/
	private String relatedArDevicePortId;
		
	/**A端接入机房设备ODF端口ID*/
	private String relatedArdOdfPortId;
		
	/**电路名称ID*/
	private String relatedCircuitNameId;
		
	/**电路接口类型ID*/
	private String relatedCircuitTypeId;
		
	/**电路级别ID*/
	private String relatedCircuitLevelId;
		
	/**Z端业务设备名称ID*/
	private String relatedPointzNameId;
		
	/**Z端业务设备端口ID*/
	private String relatedPointzPortId;
		
	/**Z端接入机房设备名称ID*/
	private String relatedPointzArNameId;
		
	/**Z端接入机房设备端口ID*/
	private String relatedPointzArptId;
		
	/**Z端接入机房设备ODF端口ID*/
	private String relatedPointzArpOdfId;
		
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
	public String getPointaCustomerName() {
		return this.pointaCustomerName;
	}
	
	public void setPointaCustomerName(String pointaCustomerName) {
		this.pointaCustomerName = pointaCustomerName;
	}
	public String getRelatedInstance() {
		return this.relatedInstance;
	}
	
	public void setRelatedInstance(String relatedInstance) {
		this.relatedInstance = relatedInstance;
	}
	public String getTranAccessWay() {
		return this.tranAccessWay;
	}
	
	public void setTranAccessWay(String tranAccessWay) {
		this.tranAccessWay = tranAccessWay;
	}
	public String getBusinessBw() {
		return this.businessBw;
	}
	
	public void setBusinessBw(String businessBw) {
		this.businessBw = businessBw;
	}
	public String getPointaCustomerAddr() {
		return this.pointaCustomerAddr;
	}
	
	public void setPointaCustomerAddr(String pointaCustomerAddr) {
		this.pointaCustomerAddr = pointaCustomerAddr;
	}
	public String getRelatedPointa() {
		return this.relatedPointa;
	}
	
	public void setRelatedPointa(String relatedPointa) {
		this.relatedPointa = relatedPointa;
	}
	public String getRelatedPointaPort() {
		return this.relatedPointaPort;
	}
	
	public void setRelatedPointaPort(String relatedPointaPort) {
		this.relatedPointaPort = relatedPointaPort;
	}
	public String getPointaDdfodfPort() {
		return this.pointaDdfodfPort;
	}
	
	public void setPointaDdfodfPort(String pointaDdfodfPort) {
		this.pointaDdfodfPort = pointaDdfodfPort;
	}
	public String getRelatedArDevice() {
		return this.relatedArDevice;
	}
	
	public void setRelatedArDevice(String relatedArDevice) {
		this.relatedArDevice = relatedArDevice;
	}
	public String getRelatedArDevicePort() {
		return this.relatedArDevicePort;
	}
	
	public void setRelatedArDevicePort(String relatedArDevicePort) {
		this.relatedArDevicePort = relatedArDevicePort;
	}
	public String getRelatedArdOdfPort() {
		return this.relatedArdOdfPort;
	}
	
	public void setRelatedArdOdfPort(String relatedArdOdfPort) {
		this.relatedArdOdfPort = relatedArdOdfPort;
	}
	public String getRelatedCircuitName() {
		return this.relatedCircuitName;
	}
	
	public void setRelatedCircuitName(String relatedCircuitName) {
		this.relatedCircuitName = relatedCircuitName;
	}
	public String getRelatedCircuitType() {
		return this.relatedCircuitType;
	}
	
	public void setRelatedCircuitType(String relatedCircuitType) {
		this.relatedCircuitType = relatedCircuitType;
	}
	public String getRelatedCircuitLevel() {
		return this.relatedCircuitLevel;
	}
	
	public void setRelatedCircuitLevel(String relatedCircuitLevel) {
		this.relatedCircuitLevel = relatedCircuitLevel;
	}
	public String getCircuitRequirement() {
		return this.circuitRequirement;
	}
	
	public void setCircuitRequirement(String circuitRequirement) {
		this.circuitRequirement = circuitRequirement;
	}
	public String getCircuitProtectWay() {
		return this.circuitProtectWay;
	}
	
	public void setCircuitProtectWay(String circuitProtectWay) {
		this.circuitProtectWay = circuitProtectWay;
	}
	public String getOfficeRedirection() {
		return this.officeRedirection;
	}
	
	public void setOfficeRedirection(String officeRedirection) {
		this.officeRedirection = officeRedirection;
	}
	public String getPointzCustomerName() {
		return this.pointzCustomerName;
	}
	
	public void setPointzCustomerName(String pointzCustomerName) {
		this.pointzCustomerName = pointzCustomerName;
	}
	public String getPointzCustomerAddr() {
		return this.pointzCustomerAddr;
	}
	
	public void setPointzCustomerAddr(String pointzCustomerAddr) {
		this.pointzCustomerAddr = pointzCustomerAddr;
	}
	public String getRelatedPointzName() {
		return this.relatedPointzName;
	}
	
	public void setRelatedPointzName(String relatedPointzName) {
		this.relatedPointzName = relatedPointzName;
	}
	public String getRelatedPointzPort() {
		return this.relatedPointzPort;
	}
	
	public void setRelatedPointzPort(String relatedPointzPort) {
		this.relatedPointzPort = relatedPointzPort;
	}
	public String getPointzDdfodfPort() {
		return this.pointzDdfodfPort;
	}
	
	public void setPointzDdfodfPort(String pointzDdfodfPort) {
		this.pointzDdfodfPort = pointzDdfodfPort;
	}
	public String getRelatedPointzArName() {
		return this.relatedPointzArName;
	}
	
	public void setRelatedPointzArName(String relatedPointzArName) {
		this.relatedPointzArName = relatedPointzArName;
	}
	public String getRelatedPointzArPort() {
		return this.relatedPointzArPort;
	}
	
	public void setRelatedPointzArPort(String relatedPointzArPort) {
		this.relatedPointzArPort = relatedPointzArPort;
	}
	public String getRelatedPointzArpOdf() {
		return this.relatedPointzArpOdf;
	}
	
	public void setRelatedPointzArpOdf(String relatedPointzArpOdf) {
		this.relatedPointzArpOdf = relatedPointzArpOdf;
	}
	public String getRelatedOcName() {
		return this.relatedOcName;
	}
	
	public void setRelatedOcName(String relatedOcName) {
		this.relatedOcName = relatedOcName;
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
	public String getRelatedPointaId() {
		return this.relatedPointaId;
	}
	
	public void setRelatedPointaId(String relatedPointaId) {
		this.relatedPointaId = relatedPointaId;
	}
	public String getRelatedPointaPortId() {
		return this.relatedPointaPortId;
	}
	
	public void setRelatedPointaPortId(String relatedPointaPortId) {
		this.relatedPointaPortId = relatedPointaPortId;
	}
	public String getRelatedArDeviceId() {
		return this.relatedArDeviceId;
	}
	
	public void setRelatedArDeviceId(String relatedArDeviceId) {
		this.relatedArDeviceId = relatedArDeviceId;
	}
	public String getRelatedArDevicePortId() {
		return this.relatedArDevicePortId;
	}
	
	public void setRelatedArDevicePortId(String relatedArDevicePortId) {
		this.relatedArDevicePortId = relatedArDevicePortId;
	}
	public String getRelatedArdOdfPortId() {
		return this.relatedArdOdfPortId;
	}
	
	public void setRelatedArdOdfPortId(String relatedArdOdfPortId) {
		this.relatedArdOdfPortId = relatedArdOdfPortId;
	}
	public String getRelatedCircuitNameId() {
		return this.relatedCircuitNameId;
	}
	
	public void setRelatedCircuitNameId(String relatedCircuitNameId) {
		this.relatedCircuitNameId = relatedCircuitNameId;
	}
	public String getRelatedCircuitTypeId() {
		return this.relatedCircuitTypeId;
	}
	
	public void setRelatedCircuitTypeId(String relatedCircuitTypeId) {
		this.relatedCircuitTypeId = relatedCircuitTypeId;
	}
	public String getRelatedCircuitLevelId() {
		return this.relatedCircuitLevelId;
	}
	
	public void setRelatedCircuitLevelId(String relatedCircuitLevelId) {
		this.relatedCircuitLevelId = relatedCircuitLevelId;
	}
	public String getRelatedPointzNameId() {
		return this.relatedPointzNameId;
	}
	
	public void setRelatedPointzNameId(String relatedPointzNameId) {
		this.relatedPointzNameId = relatedPointzNameId;
	}
	public String getRelatedPointzPortId() {
		return this.relatedPointzPortId;
	}
	
	public void setRelatedPointzPortId(String relatedPointzPortId) {
		this.relatedPointzPortId = relatedPointzPortId;
	}
	public String getRelatedPointzArNameId() {
		return this.relatedPointzArNameId;
	}
	
	public void setRelatedPointzArNameId(String relatedPointzArNameId) {
		this.relatedPointzArNameId = relatedPointzArNameId;
	}
	public String getRelatedPointzArptId() {
		return this.relatedPointzArptId;
	}
	
	public void setRelatedPointzArptId(String relatedPointzArptId) {
		this.relatedPointzArptId = relatedPointzArptId;
	}
	public String getRelatedPointzArpOdfId() {
		return this.relatedPointzArpOdfId;
	}
	
	public void setRelatedPointzArpOdfId(String relatedPointzArpOdfId) {
		this.relatedPointzArpOdfId = relatedPointzArpOdfId;
	}
	public String getRelatedOcNameId() {
		return this.relatedOcNameId;
	}
	
	public void setRelatedOcNameId(String relatedOcNameId) {
		this.relatedOcNameId = relatedOcNameId;
	}
	
}
