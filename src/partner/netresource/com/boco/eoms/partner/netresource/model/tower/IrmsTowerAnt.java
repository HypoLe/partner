package com.boco.eoms.partner.netresource.model.tower;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--铁塔及天馈--天线
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:53:04
 */
public class IrmsTowerAnt extends EomsModel{

	/**主键*/
	private String id;
		
	/****小区（小区网元命名）天线*/
	private String labelCn;
		
	/**自动计算（电下倾+机械下倾）*/
	private String sumAngle;
		
	/**关联属性，关联到【CELL】、【UTRANCELL】表网元名称，存在一根天线关联多个小区情况，用逗号分隔*/
	private String relatedCellid;
		
	/**国信,京信,广东通宇等*/
	private String vendor;
		
	/**相对于地面的高度*/
	private String highCube;
		
	/**枚举{单频单极化、单频双极化,单频双极化电调,双频双极化,双频双极化电调}省公司根据需求自行改变*/
	private String antType;
		
	/**方位角*/
	private String maxazimuthValue;
		
	/**电调下倾角*/
	private String adjustAngleEle;
		
	/**机械下倾角*/
	private String adjustAngleMac;
		
	/**安装时间:yyyy-mm-dd*/
	private String installTime;
		
	/**统一填天线所在平台，如1代表第一平台，2代表第2平台；桅杆如果不能区分天面情况填0*/
	private String tianMian;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属小区ID*/
	private String relatedCellidId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getLabelCn() {
		return this.labelCn;
	}
	
	public void setLabelCn(String labelCn) {
		this.labelCn = labelCn;
	}
	public String getSumAngle() {
		return this.sumAngle;
	}
	
	public void setSumAngle(String sumAngle) {
		this.sumAngle = sumAngle;
	}
	public String getRelatedCellid() {
		return this.relatedCellid;
	}
	
	public void setRelatedCellid(String relatedCellid) {
		this.relatedCellid = relatedCellid;
	}
	public String getVendor() {
		return this.vendor;
	}
	
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getHighCube() {
		return this.highCube;
	}
	
	public void setHighCube(String highCube) {
		this.highCube = highCube;
	}
	public String getAntType() {
		return this.antType;
	}
	
	public void setAntType(String antType) {
		this.antType = antType;
	}
	public String getMaxazimuthValue() {
		return this.maxazimuthValue;
	}
	
	public void setMaxazimuthValue(String maxazimuthValue) {
		this.maxazimuthValue = maxazimuthValue;
	}
	public String getAdjustAngleEle() {
		return this.adjustAngleEle;
	}
	
	public void setAdjustAngleEle(String adjustAngleEle) {
		this.adjustAngleEle = adjustAngleEle;
	}
	public String getAdjustAngleMac() {
		return this.adjustAngleMac;
	}
	
	public void setAdjustAngleMac(String adjustAngleMac) {
		this.adjustAngleMac = adjustAngleMac;
	}
	public String getInstallTime() {
		return this.installTime;
	}
	
	public void setInstallTime(String installTime) {
		this.installTime = installTime;
	}
	public String getTianMian() {
		return this.tianMian;
	}
	
	public void setTianMian(String tianMian) {
		this.tianMian = tianMian;
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
	public String getRelatedCellidId() {
		return this.relatedCellidId;
	}
	
	public void setRelatedCellidId(String relatedCellidId) {
		this.relatedCellidId = relatedCellidId;
	}
	
}
