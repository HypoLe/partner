package com.boco.eoms.partner.netresource.model.bs;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--基站设备及配套--CELL
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:49:19
 */
public class IrmsBsCell extends EomsModel{

	/**主键*/
	private String id;
		
	/**按照集团要求，OMC中网元名称要与综合资管命名保持一致，此项命名参照集团规范*/
	private String userlabelCm;
		
	/**中文名称*/
	private String labelCn;
		
	/**小区码CI*/
	private String ci;
		
	/**位置区码LAC*/
	private String lac;
		
	/**1800、900/1800、900，标识清楚即可*/
	private String gsmdcsindicator;
		
	/**关联属性，关联到【BTSSITE】表网元名称*/
	private String relatedBtssiteCu;
		
	/**枚举{宏蜂窝,微蜂窝}*/
	private String cellType;
		
	/**枚举{现网、工程、空载、退网}*/
	private String status;
		
	/**枚举{省内,省际}*/
	private String boundcellType;
		
	/** 枚举{是,否}*/
	private String ifRepeator;
		
	/**支持EGPRS*/
	private String isEnegprs;
		
	/**车站,大中专院校,党政机关,高级写字楼,高速公路,会展中心,机场,居民小区,旅游景点,其它,商业区,体育场馆,铁路,星级酒店,重点企业 供参考*/
	private String coverFiled;
		
	/**TRX数量*/
	private String trxCount;
		
	/**开通时间*/
	private String openTime;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属BTS ID*/
	private String relatedBtssiteCuId;
		

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
	public String getCi() {
		return this.ci;
	}
	
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getLac() {
		return this.lac;
	}
	
	public void setLac(String lac) {
		this.lac = lac;
	}
	public String getGsmdcsindicator() {
		return this.gsmdcsindicator;
	}
	
	public void setGsmdcsindicator(String gsmdcsindicator) {
		this.gsmdcsindicator = gsmdcsindicator;
	}
	public String getRelatedBtssiteCu() {
		return this.relatedBtssiteCu;
	}
	
	public void setRelatedBtssiteCu(String relatedBtssiteCu) {
		this.relatedBtssiteCu = relatedBtssiteCu;
	}
	public String getCellType() {
		return this.cellType;
	}
	
	public void setCellType(String cellType) {
		this.cellType = cellType;
	}
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBoundcellType() {
		return this.boundcellType;
	}
	
	public void setBoundcellType(String boundcellType) {
		this.boundcellType = boundcellType;
	}
	public String getIfRepeator() {
		return this.ifRepeator;
	}
	
	public void setIfRepeator(String ifRepeator) {
		this.ifRepeator = ifRepeator;
	}
	public String getIsEnegprs() {
		return this.isEnegprs;
	}
	
	public void setIsEnegprs(String isEnegprs) {
		this.isEnegprs = isEnegprs;
	}
	public String getCoverFiled() {
		return this.coverFiled;
	}
	
	public void setCoverFiled(String coverFiled) {
		this.coverFiled = coverFiled;
	}
	public String getTrxCount() {
		return this.trxCount;
	}
	
	public void setTrxCount(String trxCount) {
		this.trxCount = trxCount;
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
	public String getRelatedBtssiteCuId() {
		return this.relatedBtssiteCuId;
	}
	
	public void setRelatedBtssiteCuId(String relatedBtssiteCuId) {
		this.relatedBtssiteCuId = relatedBtssiteCuId;
	}
	
}
