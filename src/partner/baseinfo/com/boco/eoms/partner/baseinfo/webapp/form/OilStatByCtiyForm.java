package com.boco.eoms.partner.baseinfo.webapp.form;

/**
 * <p>
 * Title:油机按地市统计信息
 * </p>
 * <p>
 * Description:油机按地市统计信息
 * </p>
 * 
 */
public class OilStatByCtiyForm  {

	/**
	 * 地域信息
	 */
	private String areaId;
	/**
	 * 合作伙伴id
	 */
	private String partnerId;
	/**
	 * 合作伙伴中文名
	 */
	private String partnerName;
	/**
	 * 功率id
	 */
	private String power;
	/**
	 * 功率
	 */
	private String powName;
	/**
	 * 应配数
	 */
	private String oilConfigNum;
	/**
	 * 实配总数
	 */
	private String oilSumNum;
	/**
	 * 实配数
	 */
	private String oilNum;
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getOilConfigNum() {
		return oilConfigNum;
	}
	public void setOilConfigNum(String oilConfigNum) {
		this.oilConfigNum = oilConfigNum;
	}
	public String getOilSumNum() {
		return oilSumNum;
	}
	public void setOilSumNum(String oilSumNum) {
		this.oilSumNum = oilSumNum;
	}
	public String getOilNum() {
		return oilNum;
	}
	public void setOilNum(String oilNum) {
		this.oilNum = oilNum;
	}
	public String getPowName() {
		return powName;
	}
	public void setPowName(String powName) {
		this.powName = powName;
	}
}