package com.boco.eoms.partner.netresource.model.bs;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--基站设备及配套--UTRANCELL
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:49:19
 */
public class IrmsBsUtrancell extends EomsModel{

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
		
	/**关联属性，关联到【NODEB】表网元名称*/
	private String relatedNodeb;
		
	/**服务器编码（SAC）*/
	private String sac;
		
	/**自动采集*/
	private String trxNbr;
		
	/** 枚举{宏蜂窝,微蜂窝}*/
	private String cellType;
		
	/** 枚举{现网、工程、空载、退网}*/
	private String status;
		
	/** 枚举{省内,省际}*/
	private String boundcellType;
		
	/** 车站,大中专院校,党政机关,高级写字楼,高速公路,会展中心,机场,居民小区,旅游景点,其它,商业区,体育场馆,铁路,星级酒店,重点企业 供参考*/
	private String coverFiled;
		
	/**开通时间*/
	private String setupTime;
		
	/**创建时间*/
	private String createTime;
		
	/**备注*/
	private String remark;
		
	/**所属NODEB ID*/
	private String relatedNodebId;
		

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
	public String getRelatedNodeb() {
		return this.relatedNodeb;
	}
	
	public void setRelatedNodeb(String relatedNodeb) {
		this.relatedNodeb = relatedNodeb;
	}
	public String getSac() {
		return this.sac;
	}
	
	public void setSac(String sac) {
		this.sac = sac;
	}
	public String getTrxNbr() {
		return this.trxNbr;
	}
	
	public void setTrxNbr(String trxNbr) {
		this.trxNbr = trxNbr;
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
	public String getCoverFiled() {
		return this.coverFiled;
	}
	
	public void setCoverFiled(String coverFiled) {
		this.coverFiled = coverFiled;
	}
	public String getSetupTime() {
		return this.setupTime;
	}
	
	public void setSetupTime(String setupTime) {
		this.setupTime = setupTime;
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
	public String getRelatedNodebId() {
		return this.relatedNodebId;
	}
	
	public void setRelatedNodebId(String relatedNodebId) {
		this.relatedNodebId = relatedNodebId;
	}
	
}
