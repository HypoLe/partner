package com.boco.eoms.partner.netresource.model.group;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--集客家客--GPRS信息表
 * 创建人： zhangkeqi
 * 创建时间：2013-03-06 17:37:39
 */
public class IrmsGroupGprs extends EomsModel{

	/**主键*/
	private String id;
		
	/**客户名称*/
	private String customerName;
		
	/**与【T1.1-客户信息表】的【客户编号】关联*/
	private String relatedProInstanceId;
		
	/**APN名称*/
	private String apnName;
		
	/**与“核心网资源”专业的【APN】的【APN ID】关联*/
	private String relatedApnId;
		
	/**为企业服务器接入GGSN时开通的专线速率*/
	private String businessBw;
		
	/**全国，全省（如果是直辖市范围填写全省），本地*/
	private String businessOpenLimi;
		
	/**选项：
1：移动数据（互联网)专线接入；
2：其它运营商互联网接入
3：移动传输专线接入
若选择2，则关联产品实例标识可为空*/
	private String businessHostingWay;
		
	/**若业务承载方式选择为2，此字段可以不填写*/
	private String relativeProInstanceId;
		
	/**描述给客户的IP地址分配方式（静态/动态）*/
	private String ipAllocationMode;
		
	/**格式：设备名称-IP地址数量。例如：ZZGGSN03BNK-254;*/
	private String useIpNum;
		
	/**是/否
若选择是，则第二个GGSN的相关字段需要填写*/
	private String isBothGgsn;
		
	/**枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
	private String tranAccessWay;
		
	/**若业务承载方式选择为1时，与相关产品实例标识关联的【客户业务应用IP地址】关联；
若业务承载方式选择为2时，手工填写，无跨专业关联*/
	private String enterpriseService;
		
	/**与“GPRS”的【GPRS系统（物理资源）－GGSN】的【设备名称】关联*/
	private String labelCn1;
		
	/**第一个的GGSN GRE选择的地址*/
	private String greAddressName1;
		
	/**GGSN与客户路由器之间的隧道IP信息*/
	private String greEnterpriseAdd1;
		
	/**GRE的名称*/
	private String greName1;
		
	/**隧道key，手工填写*/
	private String greTunnrlKey1;
		
	/**与“GPRS”的【GPRS系统（物理资源）－GGSN】的【设备名称】关联*/
	private String labelCn2;
		
	/**GRE的GGSN本端地址，双GGSN时填写*/
	private String greAddressName2;
		
	/**GGSN企业端地址，根据上面的“GRE的企业端地址”在双GGSN时手工填写*/
	private String greEnterpriseAdd2;
		
	/**GRE的名称*/
	private String greName2;
		
	/**隧道key，双GGSN时手工填写*/
	private String greTunnrlKey2;
		
	/**与“GPRS”的【GPRS系统（逻辑资源）－IP地址信息】的【IP地址】关联*/
	private String apnAddressPool;
		
	/**是，否*/
	private String isUseRadius;
		
	/**RADIUS服务器地址*/
	private String radiusIpAdd;
		
	/**是，否*/
	private String isTerminalVisits;
		
	/**与“传送网资源”（内线逻辑资源）的【传输电路】的【电路路名称】关联*/
	private String circuitName;
		
	/**与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
	private String opticalCircuitName;
		
	/**光纤接入客户的日期：yyyy-MM-dd*/
	private String customerAccessDate;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**产品实例标识ID*/
	private String relatedProInstanceIdId;
		
	/**APN号码ID*/
	private String relatedApnIdId;
		
	/**企业服务器IP地址ID*/
	private String enterpriseServiceId;
		
	/**第一个GGSN的名称ID*/
	private String labelCn1Id;
		
	/**第二个GGSN的名称ID*/
	private String labelCn2Id;
		
	/**终端IP地址池ID*/
	private String apnAddressPoolId;
		
	/**电路名称ID*/
	private String circuitNameId;
		
	/**光路名称ID*/
	private String opticalCircuitNameId;
		

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
	public String getRelatedProInstanceId() {
		return this.relatedProInstanceId;
	}
	
	public void setRelatedProInstanceId(String relatedProInstanceId) {
		this.relatedProInstanceId = relatedProInstanceId;
	}
	public String getApnName() {
		return this.apnName;
	}
	
	public void setApnName(String apnName) {
		this.apnName = apnName;
	}
	public String getRelatedApnId() {
		return this.relatedApnId;
	}
	
	public void setRelatedApnId(String relatedApnId) {
		this.relatedApnId = relatedApnId;
	}
	public String getBusinessBw() {
		return this.businessBw;
	}
	
	public void setBusinessBw(String businessBw) {
		this.businessBw = businessBw;
	}
	public String getBusinessOpenLimi() {
		return this.businessOpenLimi;
	}
	
	public void setBusinessOpenLimi(String businessOpenLimi) {
		this.businessOpenLimi = businessOpenLimi;
	}
	public String getBusinessHostingWay() {
		return this.businessHostingWay;
	}
	
	public void setBusinessHostingWay(String businessHostingWay) {
		this.businessHostingWay = businessHostingWay;
	}
	public String getRelativeProInstanceId() {
		return this.relativeProInstanceId;
	}
	
	public void setRelativeProInstanceId(String relativeProInstanceId) {
		this.relativeProInstanceId = relativeProInstanceId;
	}
	public String getIpAllocationMode() {
		return this.ipAllocationMode;
	}
	
	public void setIpAllocationMode(String ipAllocationMode) {
		this.ipAllocationMode = ipAllocationMode;
	}
	public String getUseIpNum() {
		return this.useIpNum;
	}
	
	public void setUseIpNum(String useIpNum) {
		this.useIpNum = useIpNum;
	}
	public String getIsBothGgsn() {
		return this.isBothGgsn;
	}
	
	public void setIsBothGgsn(String isBothGgsn) {
		this.isBothGgsn = isBothGgsn;
	}
	public String getTranAccessWay() {
		return this.tranAccessWay;
	}
	
	public void setTranAccessWay(String tranAccessWay) {
		this.tranAccessWay = tranAccessWay;
	}
	public String getEnterpriseService() {
		return this.enterpriseService;
	}
	
	public void setEnterpriseService(String enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	public String getLabelCn1() {
		return this.labelCn1;
	}
	
	public void setLabelCn1(String labelCn1) {
		this.labelCn1 = labelCn1;
	}
	public String getGreAddressName1() {
		return this.greAddressName1;
	}
	
	public void setGreAddressName1(String greAddressName1) {
		this.greAddressName1 = greAddressName1;
	}
	public String getGreEnterpriseAdd1() {
		return this.greEnterpriseAdd1;
	}
	
	public void setGreEnterpriseAdd1(String greEnterpriseAdd1) {
		this.greEnterpriseAdd1 = greEnterpriseAdd1;
	}
	public String getGreName1() {
		return this.greName1;
	}
	
	public void setGreName1(String greName1) {
		this.greName1 = greName1;
	}
	public String getGreTunnrlKey1() {
		return this.greTunnrlKey1;
	}
	
	public void setGreTunnrlKey1(String greTunnrlKey1) {
		this.greTunnrlKey1 = greTunnrlKey1;
	}
	public String getLabelCn2() {
		return this.labelCn2;
	}
	
	public void setLabelCn2(String labelCn2) {
		this.labelCn2 = labelCn2;
	}
	public String getGreAddressName2() {
		return this.greAddressName2;
	}
	
	public void setGreAddressName2(String greAddressName2) {
		this.greAddressName2 = greAddressName2;
	}
	public String getGreEnterpriseAdd2() {
		return this.greEnterpriseAdd2;
	}
	
	public void setGreEnterpriseAdd2(String greEnterpriseAdd2) {
		this.greEnterpriseAdd2 = greEnterpriseAdd2;
	}
	public String getGreName2() {
		return this.greName2;
	}
	
	public void setGreName2(String greName2) {
		this.greName2 = greName2;
	}
	public String getGreTunnrlKey2() {
		return this.greTunnrlKey2;
	}
	
	public void setGreTunnrlKey2(String greTunnrlKey2) {
		this.greTunnrlKey2 = greTunnrlKey2;
	}
	public String getApnAddressPool() {
		return this.apnAddressPool;
	}
	
	public void setApnAddressPool(String apnAddressPool) {
		this.apnAddressPool = apnAddressPool;
	}
	public String getIsUseRadius() {
		return this.isUseRadius;
	}
	
	public void setIsUseRadius(String isUseRadius) {
		this.isUseRadius = isUseRadius;
	}
	public String getRadiusIpAdd() {
		return this.radiusIpAdd;
	}
	
	public void setRadiusIpAdd(String radiusIpAdd) {
		this.radiusIpAdd = radiusIpAdd;
	}
	public String getIsTerminalVisits() {
		return this.isTerminalVisits;
	}
	
	public void setIsTerminalVisits(String isTerminalVisits) {
		this.isTerminalVisits = isTerminalVisits;
	}
	public String getCircuitName() {
		return this.circuitName;
	}
	
	public void setCircuitName(String circuitName) {
		this.circuitName = circuitName;
	}
	public String getOpticalCircuitName() {
		return this.opticalCircuitName;
	}
	
	public void setOpticalCircuitName(String opticalCircuitName) {
		this.opticalCircuitName = opticalCircuitName;
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
	public String getRelatedProInstanceIdId() {
		return this.relatedProInstanceIdId;
	}
	
	public void setRelatedProInstanceIdId(String relatedProInstanceIdId) {
		this.relatedProInstanceIdId = relatedProInstanceIdId;
	}
	public String getRelatedApnIdId() {
		return this.relatedApnIdId;
	}
	
	public void setRelatedApnIdId(String relatedApnIdId) {
		this.relatedApnIdId = relatedApnIdId;
	}
	public String getEnterpriseServiceId() {
		return this.enterpriseServiceId;
	}
	
	public void setEnterpriseServiceId(String enterpriseServiceId) {
		this.enterpriseServiceId = enterpriseServiceId;
	}
	public String getLabelCn1Id() {
		return this.labelCn1Id;
	}
	
	public void setLabelCn1Id(String labelCn1Id) {
		this.labelCn1Id = labelCn1Id;
	}
	public String getLabelCn2Id() {
		return this.labelCn2Id;
	}
	
	public void setLabelCn2Id(String labelCn2Id) {
		this.labelCn2Id = labelCn2Id;
	}
	public String getApnAddressPoolId() {
		return this.apnAddressPoolId;
	}
	
	public void setApnAddressPoolId(String apnAddressPoolId) {
		this.apnAddressPoolId = apnAddressPoolId;
	}
	public String getCircuitNameId() {
		return this.circuitNameId;
	}
	
	public void setCircuitNameId(String circuitNameId) {
		this.circuitNameId = circuitNameId;
	}
	public String getOpticalCircuitNameId() {
		return this.opticalCircuitNameId;
	}
	
	public void setOpticalCircuitNameId(String opticalCircuitNameId) {
		this.opticalCircuitNameId = opticalCircuitNameId;
	}
	
}
