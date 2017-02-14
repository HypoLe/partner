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
public class OilStatByStateForm  {

	private String partnerId;
	/**
	 * 合作伙伴中文名
	 */
	private String partnerName;
	
	/**
	 * 实配数
	 */
	private String oilNum;
	
	/**
	 * 油机性质
	 */
	private String kind;	
	
	/**
	 * 油机状态
	 */
	private String state;

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
	public String getOilNum() {
		return oilNum;
	}
	public void setOilNum(String oilNum) {
		this.oilNum = oilNum;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}