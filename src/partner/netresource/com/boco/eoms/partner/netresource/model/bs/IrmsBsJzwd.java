package com.boco.eoms.partner.netresource.model.bs;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--基站设备及配套--机楼基站外电 创建人： zhangkeqi 创建时间：2012-11-27 18:49:18
 */
public class IrmsBsJzwd extends EomsModel {

	/** 主键 */
	private String id;

	/** 填写所属省份 */
	private String relatedProvince;

	/** 填写所属省份地市 */
	private String relatedCity;

	/** 填写所属省份区县 */
	private String relatedCounty;

	/** 关联到【空间资源】-【站点表】-【站点名称】 */
	private String relatedSite;

	/** 填写所属机楼（基站）名称 */
	private String relatedSiteName;

	/** 填写所属机楼（基站）编号 */
	private String relatedSiteNo;

	/** 枚举值：自建、购买、租用、共建、共享 */
	private String property;

	/**
	 * 枚举值：通信机楼,综合机楼，非通信机楼， 超级基站，VVIP基站，VIP基站，普通基站
	 */
	private String importantLevel;

	/** 机楼、基站的详细地址信息 */
	private String roomAddr;

	/** 市电一来自的变电站名称，节点基站不必填 */
	private String oneSubstation;

	/** 市电二来自的变电站名称 */
	private String twoSubstation;

	/** 移动公司该设备的负责人 */
	private String preserver;

	/** 向供电部门申请最终确定的容量 */
	private String utilityCpacity;

	/** 枚举值：地埋、架空 */
	private String utilityMode;

	/** 线径大小 */
	private String line;

	/** 线缆长度 */
	private String cablesLength;

	/** 枚举值：转供、直供 */
	private String electKind;

	/** 转供单位及联系人 */
	private String turnInfo;

	/** 机房绝对标高指机房地面以海平面为基准的标高(也称海拔标高)，一般使用珠江高程。如缺乏绝对标高资料，则用相对标高，即以机房室外地面为基准的标高，备注中说明。单位：m */
	private String height;

	/** 基站机房的经度。填至度、分、秒。格式：XXX：XX:XXE */
	private String longitude;

	/** 基站机房的纬度。填至度、分、秒。格式：XXX：XX:XXN */
	private String latitude;

	/** 供电合同编号 */
	private String electContract;

	/** 变电站的联系电话 */
	private String substationTel;

	/** 基站机房的业主联系人。 */
	private String ownerLinkman;

	/** 基站机房的保安或者物业管理联系人。 */
	private String queryLinkman;

	/** 基站机房的业主联系电话。 */
	private String ownerLinktel;

	/** 基站机房的保安或者物业管理联系电话。 */
	private String queryLinktel;

	/** 动力代维 */
	private String powerMaint;

	/** 传输代维 */
	private String transMaint;

	/** 环境代维 */
	private String environmentMaint;

	/** 消防代维 */
	private String fireMaint;

	/** 空调代维 */
	private String airConditionMain;

	/** 创建时间 */
	private String createTime;

	/** 备注 */
	private String remark;

	/** 站点ID */
	private String relatedSiteId;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRelatedProvince() {
		return this.relatedProvince;
	}

	public void setRelatedProvince(String relatedProvince) {
		this.relatedProvince = relatedProvince;
	}

	public String getRelatedCity() {
		return this.relatedCity;
	}

	public void setRelatedCity(String relatedCity) {
		this.relatedCity = relatedCity;
	}

	public String getRelatedCounty() {
		return this.relatedCounty;
	}

	public void setRelatedCounty(String relatedCounty) {
		this.relatedCounty = relatedCounty;
	}

	public String getRelatedSite() {
		return this.relatedSite;
	}

	public void setRelatedSite(String relatedSite) {
		this.relatedSite = relatedSite;
	}

	public String getRelatedSiteName() {
		return this.relatedSiteName;
	}

	public void setRelatedSiteName(String relatedSiteName) {
		this.relatedSiteName = relatedSiteName;
	}

	public String getRelatedSiteNo() {
		return this.relatedSiteNo;
	}

	public void setRelatedSiteNo(String relatedSiteNo) {
		this.relatedSiteNo = relatedSiteNo;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getImportantLevel() {
		return this.importantLevel;
	}

	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}

	public String getRoomAddr() {
		return this.roomAddr;
	}

	public void setRoomAddr(String roomAddr) {
		this.roomAddr = roomAddr;
	}

	public String getOneSubstation() {
		return this.oneSubstation;
	}

	public void setOneSubstation(String oneSubstation) {
		this.oneSubstation = oneSubstation;
	}

	public String getTwoSubstation() {
		return this.twoSubstation;
	}

	public void setTwoSubstation(String twoSubstation) {
		this.twoSubstation = twoSubstation;
	}

	public String getPreserver() {
		return this.preserver;
	}

	public void setPreserver(String preserver) {
		this.preserver = preserver;
	}

	public String getUtilityCpacity() {
		return this.utilityCpacity;
	}

	public void setUtilityCpacity(String utilityCpacity) {
		this.utilityCpacity = utilityCpacity;
	}

	public String getUtilityMode() {
		return this.utilityMode;
	}

	public void setUtilityMode(String utilityMode) {
		this.utilityMode = utilityMode;
	}

	public String getLine() {
		return this.line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getCablesLength() {
		return this.cablesLength;
	}

	public void setCablesLength(String cablesLength) {
		this.cablesLength = cablesLength;
	}

	public String getElectKind() {
		return this.electKind;
	}

	public void setElectKind(String electKind) {
		this.electKind = electKind;
	}

	public String getTurnInfo() {
		return this.turnInfo;
	}

	public void setTurnInfo(String turnInfo) {
		this.turnInfo = turnInfo;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getElectContract() {
		return this.electContract;
	}

	public void setElectContract(String electContract) {
		this.electContract = electContract;
	}

	public String getSubstationTel() {
		return this.substationTel;
	}

	public void setSubstationTel(String substationTel) {
		this.substationTel = substationTel;
	}

	public String getOwnerLinkman() {
		return this.ownerLinkman;
	}

	public void setOwnerLinkman(String ownerLinkman) {
		this.ownerLinkman = ownerLinkman;
	}

	public String getQueryLinkman() {
		return this.queryLinkman;
	}

	public void setQueryLinkman(String queryLinkman) {
		this.queryLinkman = queryLinkman;
	}

	public String getOwnerLinktel() {
		return this.ownerLinktel;
	}

	public void setOwnerLinktel(String ownerLinktel) {
		this.ownerLinktel = ownerLinktel;
	}

	public String getQueryLinktel() {
		return this.queryLinktel;
	}

	public void setQueryLinktel(String queryLinktel) {
		this.queryLinktel = queryLinktel;
	}

	public String getPowerMaint() {
		return this.powerMaint;
	}

	public void setPowerMaint(String powerMaint) {
		this.powerMaint = powerMaint;
	}

	public String getTransMaint() {
		return this.transMaint;
	}

	public void setTransMaint(String transMaint) {
		this.transMaint = transMaint;
	}

	public String getEnvironmentMaint() {
		return this.environmentMaint;
	}

	public void setEnvironmentMaint(String environmentMaint) {
		this.environmentMaint = environmentMaint;
	}

	public String getFireMaint() {
		return this.fireMaint;
	}

	public void setFireMaint(String fireMaint) {
		this.fireMaint = fireMaint;
	}

	public String getAirConditionMain() {
		return this.airConditionMain;
	}

	public void setAirConditionMain(String airConditionMain) {
		this.airConditionMain = airConditionMain;
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

	public String getRelatedSiteId() {
		return this.relatedSiteId;
	}

	public void setRelatedSiteId(String relatedSiteId) {
		this.relatedSiteId = relatedSiteId;
	}

}
