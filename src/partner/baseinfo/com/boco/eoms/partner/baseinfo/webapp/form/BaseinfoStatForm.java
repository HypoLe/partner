package com.boco.eoms.partner.baseinfo.webapp.form;

/**
 * <p>
 * Title:合作伙伴基本信息统计
 * </p>
 * <p>
 * Description:合作伙伴基本信息统计
 * </p>
 * <p>
 * </p>
 * 
 * @moudle.getAuthor() 戴志刚
 * @moudle.getVersion() 2.0
 * 
 */
public class BaseinfoStatForm {
	
	/**
	 *
	 * 地域id
	 *
	 */
	private String areaId;
	/**
	 *
	 * 合作伙伴数量
	 *
	 */
	private String partnerNum;
	/**
	 *
	 * 合作伙伴id
	 *
	 */
	private String partnerId;

	/**
	 *
	 * 合作伙伴名称
	 *
	 */
	private String partnerName;

	/**
	 *
	 * 专业id
	 *
	 */
	private String professionId;

	/**
	 *
	 * 专业名称
	 *
	 */
	private String professionName;

	/**
	 *
	 * 人员数量
	 *
	 */
	private String userNum;
	
	/**
	 *
	 * 人员应配数量
	 *
	 */
	private String userConfig;

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getPartnerNum() {
		return partnerNum;
	}

	public void setPartnerNum(String partnerNum) {
		this.partnerNum = partnerNum;
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

	public String getProfessionId() {
		return professionId;
	}

	public void setProfessionId(String professionId) {
		this.professionId = professionId;
	}

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getUserConfig() {
		return userConfig;
	}

	public void setUserConfig(String userConfig) {
		this.userConfig = userConfig;
	}
}
