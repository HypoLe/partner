package com.boco.eoms.partner.property.model;

/**
 * 类说明：网络资源--空间资源--电费统计
 * 创建人： fengguanping
 * 创建时间：2012-09-28 10:36:39
 */
public class PnrElectricityBillsCount {

	/**主键id*/
	private String id;
		
	/**省份*/
	private String province;
		
	/**地市*/
	private String city;
		
	/**区县*/
	private String district;
		
	/**所属物业点*/
	private String site;
	
	/**所属合同id*/
	private String relatedAgreementid;
		
	/**费用产生年*/
	private String timeYear;
		
	/**1月份费用*/
	private double januaryBills;
		
	/**2月份费用*/
	private double februaryBills;
		
	/**3月份费用*/
	private double marchBills;
		
	/**4月份费用*/
	private double aprilBills;
		
	/**5月份费用*/
	private double mayBills;
		
	/**6月份费用*/
	private double juneBills;
		
	/**7月份费用*/
	private double julyBills;
		
	/**8月份费用*/
	private double augustBills;
		
	/**9月份费用*/
	private double septemberBills;
		
	/**10月份费用*/
	private double octoberBills;
		
	/**11月份费用*/
	private double novemberBills;
		
	/**12月份费用*/
	private double decemberBills;
		
	/**一年费用共计*/
	private double totalBills;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getProvince() {
		return this.province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return this.district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getSite() {
		return this.site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	public String getTimeYear() {
		return this.timeYear;
	}
	
	public void setTimeYear(String timeYear) {
		this.timeYear = timeYear;
	}
	public double getJanuaryBills() {
		return this.januaryBills;
	}
	
	public void setJanuaryBills(double januaryBills) {
		this.januaryBills = januaryBills;
	}
	public double getFebruaryBills() {
		return this.februaryBills;
	}
	
	public void setFebruaryBills(double februaryBills) {
		this.februaryBills = februaryBills;
	}
	public double getMarchBills() {
		return this.marchBills;
	}
	
	public void setMarchBills(double marchBills) {
		this.marchBills = marchBills;
	}
	public double getAprilBills() {
		return this.aprilBills;
	}
	
	public void setAprilBills(double aprilBills) {
		this.aprilBills = aprilBills;
	}
	public double getMayBills() {
		return this.mayBills;
	}
	
	public void setMayBills(double mayBills) {
		this.mayBills = mayBills;
	}
	public double getJuneBills() {
		return this.juneBills;
	}
	
	public void setJuneBills(double juneBills) {
		this.juneBills = juneBills;
	}
	public double getJulyBills() {
		return this.julyBills;
	}
	
	public void setJulyBills(double julyBills) {
		this.julyBills = julyBills;
	}
	public double getAugustBills() {
		return this.augustBills;
	}
	
	public void setAugustBills(double augustBills) {
		this.augustBills = augustBills;
	}
	public double getSeptemberBills() {
		return this.septemberBills;
	}
	
	public void setSeptemberBills(double septemberBills) {
		this.septemberBills = septemberBills;
	}
	public double getOctoberBills() {
		return this.octoberBills;
	}
	
	public void setOctoberBills(double octoberBills) {
		this.octoberBills = octoberBills;
	}
	public double getNovemberBills() {
		return this.novemberBills;
	}
	
	public void setNovemberBills(double novemberBills) {
		this.novemberBills = novemberBills;
	}
	public double getDecemberBills() {
		return this.decemberBills;
	}
	
	public void setDecemberBills(double decemberBills) {
		this.decemberBills = decemberBills;
	}
	public double getTotalBills() {
		return this.totalBills;
	}
	
	public void setTotalBills(double totalBills) {
		this.totalBills=totalBills;
	}

	public String getRelatedAgreementid() {
		return relatedAgreementid;
	}

	public void setRelatedAgreementid(String relatedAgreementid) {
		this.relatedAgreementid = relatedAgreementid;
	}
	
}
