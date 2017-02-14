package com.boco.eoms.partner.netresource.model.group;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--集客家客--彩信息表
 * 创建人： zhangkeqi
 * 创建时间：2013-03-06 17:37:39
 */
public class IrmsGroupMms extends EomsModel{

	/**主键*/
	private String id;
		
	/**客户名称*/
	private String customerName;
		
	/**与【T1.1-客户信息表】的【客户编号】关联*/
	private String relatedInstance;
		
	/**短信/彩信*/
	private String mmsServiceNumTy;
		
	/**用于计费唯一标识EC所分配的企业代码*/
	private String mmsEnterpriseCod;
		
	/**彩信服务号码*/
	private String mmsServiceNum;
		
	/**全国、全省（如果是直辖市范围填写全省）、地市*/
	private String mmsServiceScope;
		
	/**服务地市名称*/
	private String serviceDistrict;
		
	/**描述接口的协议类型：SOAP、HTTP*/
	private String protocalType;
		
	/**提供彩信业务的具体业务名称*/
	private String mmsBusiName;
		
	/**业务名称对应的代码*/
	private String mmsBusiCode;
		
	/**用于上行到客户的url*/
	private String mmsUpUrl;
		
	/**1—表示只允许业务上行；
2—表示只允许业务下行；
3—表示上下行均允许*/
	private String mmsBusiFlowLimi;
		
	/**枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
	private String tranAccessWay;
		
	/**选项：
1：移动数据（互联网)专线接入；
2：其它运营商互联网接入
3：移动传输专线接入
若选择2，则关联产品实例标识可为空*/
	private String businessHostingWay;
		
	/**若业务承载方式选择为2，此字段可以不填写*/
	private String relatedProductInstance;
		
	/**与《短信网关－系统信息》的【所属短信网关ID】的《短信网关－基础信息》的【所在机房】关联*/
	private String relatedAgRoom;
		
	/**与“短彩专业”的【彩信中兴－系统信息】的【业务系统名称】关联*/
	private String relatedAgName;
		
	/**与“短彩专业”的【彩信中兴－系统信息】的【公网互联IP地址】关联；*/
	private String relatedGatewayIp;
		
	/**若业务承载方式选择为1时，与相关产品实例标识关联的【客户业务应用IP地址】关联；
若业务承载方式选择为2时，手工填写，无跨专业关联*/
	private String relatedServerIp;
		
	/**port number*/
	private String servicePort;
		
	/**业务登录帐号*/
	private String loginName;
		
	/**与“传送网资源”（内线逻辑资源）的【传输电路】的【电路路名称】关联*/
	private String relatedCircuitName;
		
	/**与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
	private String relatedOcName;
		
	/**光纤接入客户的日期:yyyy-MM-dd*/
	private String customerAccessDate;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**产品实例标识ID*/
	private String relatedInstanceId;
		
	/**接入网关名称ID*/
	private String relatedAgNameId;
		
	/**网关IP地址ID*/
	private String relatedGatewayIpId;
		
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
	public String getMmsServiceNumTy() {
		return this.mmsServiceNumTy;
	}
	
	public void setMmsServiceNumTy(String mmsServiceNumTy) {
		this.mmsServiceNumTy = mmsServiceNumTy;
	}
	public String getMmsEnterpriseCod() {
		return this.mmsEnterpriseCod;
	}
	
	public void setMmsEnterpriseCod(String mmsEnterpriseCod) {
		this.mmsEnterpriseCod = mmsEnterpriseCod;
	}
	public String getMmsServiceNum() {
		return this.mmsServiceNum;
	}
	
	public void setMmsServiceNum(String mmsServiceNum) {
		this.mmsServiceNum = mmsServiceNum;
	}
	public String getMmsServiceScope() {
		return this.mmsServiceScope;
	}
	
	public void setMmsServiceScope(String mmsServiceScope) {
		this.mmsServiceScope = mmsServiceScope;
	}
	public String getServiceDistrict() {
		return this.serviceDistrict;
	}
	
	public void setServiceDistrict(String serviceDistrict) {
		this.serviceDistrict = serviceDistrict;
	}
	public String getProtocalType() {
		return this.protocalType;
	}
	
	public void setProtocalType(String protocalType) {
		this.protocalType = protocalType;
	}
	public String getMmsBusiName() {
		return this.mmsBusiName;
	}
	
	public void setMmsBusiName(String mmsBusiName) {
		this.mmsBusiName = mmsBusiName;
	}
	public String getMmsBusiCode() {
		return this.mmsBusiCode;
	}
	
	public void setMmsBusiCode(String mmsBusiCode) {
		this.mmsBusiCode = mmsBusiCode;
	}
	public String getMmsUpUrl() {
		return this.mmsUpUrl;
	}
	
	public void setMmsUpUrl(String mmsUpUrl) {
		this.mmsUpUrl = mmsUpUrl;
	}
	public String getMmsBusiFlowLimi() {
		return this.mmsBusiFlowLimi;
	}
	
	public void setMmsBusiFlowLimi(String mmsBusiFlowLimi) {
		this.mmsBusiFlowLimi = mmsBusiFlowLimi;
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
	public String getRelatedProductInstance() {
		return this.relatedProductInstance;
	}
	
	public void setRelatedProductInstance(String relatedProductInstance) {
		this.relatedProductInstance = relatedProductInstance;
	}
	public String getRelatedAgRoom() {
		return this.relatedAgRoom;
	}
	
	public void setRelatedAgRoom(String relatedAgRoom) {
		this.relatedAgRoom = relatedAgRoom;
	}
	public String getRelatedAgName() {
		return this.relatedAgName;
	}
	
	public void setRelatedAgName(String relatedAgName) {
		this.relatedAgName = relatedAgName;
	}
	public String getRelatedGatewayIp() {
		return this.relatedGatewayIp;
	}
	
	public void setRelatedGatewayIp(String relatedGatewayIp) {
		this.relatedGatewayIp = relatedGatewayIp;
	}
	public String getRelatedServerIp() {
		return this.relatedServerIp;
	}
	
	public void setRelatedServerIp(String relatedServerIp) {
		this.relatedServerIp = relatedServerIp;
	}
	public String getServicePort() {
		return this.servicePort;
	}
	
	public void setServicePort(String servicePort) {
		this.servicePort = servicePort;
	}
	public String getLoginName() {
		return this.loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
	public String getRelatedAgNameId() {
		return this.relatedAgNameId;
	}
	
	public void setRelatedAgNameId(String relatedAgNameId) {
		this.relatedAgNameId = relatedAgNameId;
	}
	public String getRelatedGatewayIpId() {
		return this.relatedGatewayIpId;
	}
	
	public void setRelatedGatewayIpId(String relatedGatewayIpId) {
		this.relatedGatewayIpId = relatedGatewayIpId;
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
