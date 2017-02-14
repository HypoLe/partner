package com.boco.eoms.partner.netresource.model.group;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--集客家客--短信信息表
 * 创建人： zhangkeqi
 * 创建时间：2013-03-06 17:37:39
 */
public class IrmsGroupSms extends EomsModel{

	/**主键*/
	private String id;
		
	/**客户名称*/
	private String customerName;
		
	/**与【T1.1-客户信息表】的【客户编号】关联*/
	private String relatedInstance;
		
	/**用于计费唯一标识EC所分配的企业代码*/
	private String smsEnterpriseCod;
		
	/**短信/彩信/短彩信/WAP*/
	private String smsServiceNumType;
		
	/**短信服务号码*/
	private String smsServiceNum;
		
	/**短信规则为：字符+数字，十位字符长度*/
	private String smsBusiCode;
		
	/**全国、全省（如果是直辖市范围填写全省）、地市*/
	private String smsServiceScope;
		
	/**服务地市*/
	private String serviceDistrict;
		
	/**描述接口的协议类型：CMPP2.0、CMPP3.0、CMPPE*/
	private String smsProtocolType;
		
	/**枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
	private String tranAccessWay;
		
	/**选项：
1：移动数据（互联网)专线接入；
2：其它运营商互联网接入
3：移动传输专线接入
若选择2，则关联产品实例标识可为空*/
	private String businessHostingWay;
		
	/**若业务承载方式选择为2，此字段可以不填写*/
	private String productInstanceId;
		
	/**与《短信网关－系统信息》的【所属短信网关ID】的《短信网关－基础信息》的【所在机房】关联*/
	private String accessGatewayRoom;
		
	/**与“短彩专业”的【短信网关－系统信息】的【业务系统名称】关联*/
	private String accessGateway;
		
	/**互联网接入时，与“短彩专业”的【短信网关－系统信息】的【公网互联IP地址】关联；
传输专线接入时，与“短彩专业”的【短信网关－系统信息】的【私网互联IP地址】关联；*/
	private String gatewayIp;
		
	/**若业务承载方式选择为1时，与相关产品实例标识关联的【客户业务应用IP地址】关联；
若业务承载方式选择为2时，手工填写，无跨专业关联*/
	private String enterpriseServerIp;
		
	/**提供服务端口号*/
	private String servicePortNo;
		
	/**登录网关用户名*/
	private String gatewayLoginName;
		
	/**与“传送网资源”（内线逻辑资源）的【传输电路】的【电路路名称】关联*/
	private String relatedCircuitName;
		
	/**与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
	private String relatedOcName;
		
	/**光纤接入客户的日期*/
	private String customerAccessDate;
		
	/**创建时间*/
	private String createTime;
		
	/**备注*/
	private String remark;
		
	/**产品实例标识ID*/
	private String relatedInstanceId;
		
	/**接入网关名称ID*/
	private String accessGatewayId;
		
	/**网关IP地址ID*/
	private String gatewayIpId;
		
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
	public String getSmsEnterpriseCod() {
		return this.smsEnterpriseCod;
	}
	
	public void setSmsEnterpriseCod(String smsEnterpriseCod) {
		this.smsEnterpriseCod = smsEnterpriseCod;
	}
	public String getSmsServiceNumType() {
		return this.smsServiceNumType;
	}
	
	public void setSmsServiceNumType(String smsServiceNumType) {
		this.smsServiceNumType = smsServiceNumType;
	}
	public String getSmsServiceNum() {
		return this.smsServiceNum;
	}
	
	public void setSmsServiceNum(String smsServiceNum) {
		this.smsServiceNum = smsServiceNum;
	}
	public String getSmsBusiCode() {
		return this.smsBusiCode;
	}
	
	public void setSmsBusiCode(String smsBusiCode) {
		this.smsBusiCode = smsBusiCode;
	}
	public String getSmsServiceScope() {
		return this.smsServiceScope;
	}
	
	public void setSmsServiceScope(String smsServiceScope) {
		this.smsServiceScope = smsServiceScope;
	}
	public String getServiceDistrict() {
		return this.serviceDistrict;
	}
	
	public void setServiceDistrict(String serviceDistrict) {
		this.serviceDistrict = serviceDistrict;
	}
	public String getSmsProtocolType() {
		return this.smsProtocolType;
	}
	
	public void setSmsProtocolType(String smsProtocolType) {
		this.smsProtocolType = smsProtocolType;
	}
	public String getTranAccessWay() {
		return this.tranAccessWay;
	}
	
	public void setTranAccessWay(String tranAccessWay) {
		this.tranAccessWay = tranAccessWay;
	}
	public String getBusinessHostingWay() {
		return this.businessHostingWay;
	}
	
	public void setBusinessHostingWay(String businessHostingWay) {
		this.businessHostingWay = businessHostingWay;
	}
	public String getProductInstanceId() {
		return this.productInstanceId;
	}
	
	public void setProductInstanceId(String productInstanceId) {
		this.productInstanceId = productInstanceId;
	}
	public String getAccessGatewayRoom() {
		return this.accessGatewayRoom;
	}
	
	public void setAccessGatewayRoom(String accessGatewayRoom) {
		this.accessGatewayRoom = accessGatewayRoom;
	}
	public String getAccessGateway() {
		return this.accessGateway;
	}
	
	public void setAccessGateway(String accessGateway) {
		this.accessGateway = accessGateway;
	}
	public String getGatewayIp() {
		return this.gatewayIp;
	}
	
	public void setGatewayIp(String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}
	public String getEnterpriseServerIp() {
		return this.enterpriseServerIp;
	}
	
	public void setEnterpriseServerIp(String enterpriseServerIp) {
		this.enterpriseServerIp = enterpriseServerIp;
	}
	public String getServicePortNo() {
		return this.servicePortNo;
	}
	
	public void setServicePortNo(String servicePortNo) {
		this.servicePortNo = servicePortNo;
	}
	public String getGatewayLoginName() {
		return this.gatewayLoginName;
	}
	
	public void setGatewayLoginName(String gatewayLoginName) {
		this.gatewayLoginName = gatewayLoginName;
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
	public String getAccessGatewayId() {
		return this.accessGatewayId;
	}
	
	public void setAccessGatewayId(String accessGatewayId) {
		this.accessGatewayId = accessGatewayId;
	}
	public String getGatewayIpId() {
		return this.gatewayIpId;
	}
	
	public void setGatewayIpId(String gatewayIpId) {
		this.gatewayIpId = gatewayIpId;
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
