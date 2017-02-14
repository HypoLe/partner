package com.boco.eoms.partner.netresource.model.bs;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--基站设备及配套--BTSSITE
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:49:18
 */
public class IrmsBsBtssite extends EomsModel{

	/**主键*/
	private String id;
		
	/**OMC中网元名称按照集团要求，OMC中网元名称要与综合资管命名保持一致，此项命名参照集团规范*/
	private String userlabelCm;
		
	/**中文名称*/
	private String labelCn;
		
	/**所属bsc关联属性，关联到【BSC】表网元名称*/
	private String relatedBsc;
		
	/**1800、900/1800、900，标识清楚即可*/
	private String freBand;
		
	/**光传输、微波、卫星*/
	private String transmode;
		
	/**50年一遇防洪基站,冰雪,测试站,常规站,地震,地震＋冰雪,地震＋洪水,地震＋洪水＋冰雪,地震＋洪水＋台风,地震＋洪水＋台风＋冰雪,地震＋台风,地震＋台风＋冰雪,洪水,洪水＋冰雪,洪水＋台风,洪水＋台风＋冰雪,台风,台风＋冰雪,应急通信车,应急站 */
	private String btssiteCrisisType;
		
	/**非VIP,一级VIP,二级VIP,三级VIP,非基站机房,VVIP类型,超级VIP基站*/
	private String vipType;
		
	/**所属关联属性，关联到【空间资源】-【机房】表-【机房名称】,对于无机房的基站，请在空间资源里加入该基站虚拟机房名称，名称中可通过扩展虚拟字段进行区分。*/
	private String relatedRoom;
		
	/**枚举{宏蜂窝,微蜂窝}*/
	private String beehiveType;
		
	/**基站编号*/
	private String btssiteNo;
		
	/**BTS3900*/
	private String model;
		
	/**故障受理单位手工维护，如基站班、网优中心故障处理组等*/
	private String failAccUnit;
		
	/**江苏海讯科技有限公司,京信通信系统(广州)有限公司等等*/
	private String manageCompany;
		
	/**{现网、工程、空载、退网}*/
	private String status;
		
	/**诺西、新邮通、阿尔卡特、摩托罗拉、中兴、华为、日海等*/
	private String relatedVendor;
		
	/**手工维护*/
	private String softVersion;
		
	/**手工维护*/
	private String openTime;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属BSC ID*/
	private String relatedBscId;
		
	/**所属机房ID*/
	private String relatedRoomId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getUserlabelCm() {
		return this.userlabelCm;
	}
	
	public void setUserlabelCm(String userlabelCm) {
		this.userlabelCm = userlabelCm;
	}
	public String getLabelCn() {
		return this.labelCn;
	}
	
	public void setLabelCn(String labelCn) {
		this.labelCn = labelCn;
	}
	public String getRelatedBsc() {
		return this.relatedBsc;
	}
	
	public void setRelatedBsc(String relatedBsc) {
		this.relatedBsc = relatedBsc;
	}
	public String getFreBand() {
		return this.freBand;
	}
	
	public void setFreBand(String freBand) {
		this.freBand = freBand;
	}
	public String getTransmode() {
		return this.transmode;
	}
	
	public void setTransmode(String transmode) {
		this.transmode = transmode;
	}
	public String getBtssiteCrisisType() {
		return this.btssiteCrisisType;
	}
	
	public void setBtssiteCrisisType(String btssiteCrisisType) {
		this.btssiteCrisisType = btssiteCrisisType;
	}
	public String getVipType() {
		return this.vipType;
	}
	
	public void setVipType(String vipType) {
		this.vipType = vipType;
	}
	public String getRelatedRoom() {
		return this.relatedRoom;
	}
	
	public void setRelatedRoom(String relatedRoom) {
		this.relatedRoom = relatedRoom;
	}
	public String getBeehiveType() {
		return this.beehiveType;
	}
	
	public void setBeehiveType(String beehiveType) {
		this.beehiveType = beehiveType;
	}
	public String getBtssiteNo() {
		return this.btssiteNo;
	}
	
	public void setBtssiteNo(String btssiteNo) {
		this.btssiteNo = btssiteNo;
	}
	public String getModel() {
		return this.model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	public String getFailAccUnit() {
		return this.failAccUnit;
	}
	
	public void setFailAccUnit(String failAccUnit) {
		this.failAccUnit = failAccUnit;
	}
	public String getManageCompany() {
		return this.manageCompany;
	}
	
	public void setManageCompany(String manageCompany) {
		this.manageCompany = manageCompany;
	}
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRelatedVendor() {
		return this.relatedVendor;
	}
	
	public void setRelatedVendor(String relatedVendor) {
		this.relatedVendor = relatedVendor;
	}
	public String getSoftVersion() {
		return this.softVersion;
	}
	
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
	public String getOpenTime() {
		return this.openTime;
	}
	
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
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
	public String getRelatedBscId() {
		return this.relatedBscId;
	}
	
	public void setRelatedBscId(String relatedBscId) {
		this.relatedBscId = relatedBscId;
	}
	public String getRelatedRoomId() {
		return this.relatedRoomId;
	}
	
	public void setRelatedRoomId(String relatedRoomId) {
		this.relatedRoomId = relatedRoomId;
	}
	
}
