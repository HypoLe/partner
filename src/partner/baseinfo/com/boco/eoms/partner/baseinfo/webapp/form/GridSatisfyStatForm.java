package com.boco.eoms.partner.baseinfo.webapp.form;

/**
 * <p>
 * Title:网格综合满意度统计
 * </p>
 * <p>
 * Description:网格综合满意度统计
 * </p>
 * <p>
 * Tue Nov 10 11:43:15 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class GridSatisfyStatForm {

	/**
	 *
	 * 月份
	 *
	 */
	private java.lang.String month;
   
	public void setMonth(java.lang.String month){
		this.month= month;       
	}
   
	public java.lang.String getMonth(){
		return this.month;
	}

	/**
	 *
	 * 年份
	 *
	 */
	private java.lang.String year;
  
	public void setYear(java.lang.String year){
		this.year= year;       
	}
  
	public java.lang.String getYear(){
		return this.year;
	}
	
	/**
	 *
	 * 地市
	 *
	 */
	private java.lang.String region;
   
	public void setRegion(java.lang.String region){
		this.region= region;       
	}
   
	public java.lang.String getRegion(){
		return this.region;
	}

	/**
	 *
	 * 县区
	 *
	 */
	private java.lang.String city;
  
	public void setCity(java.lang.String city){
		this.city= city;       
	}
  
	public java.lang.String getCity(){
		return this.city;
	}
	
	

	/**
	 *
	 * 网格
	 *
	 */
	private java.lang.String grid;
   
	public void setGrid(java.lang.String grid){
		this.grid= grid;       
	}
   
	public java.lang.String getGrid(){
		return this.grid;
	}

	/**
	 *
	 * 合作伙伴
	 *
	 */
	private java.lang.String provider;
   
	public void setProvider(java.lang.String provider){
		this.provider= provider;       
	}
   
	public java.lang.String getProvider(){
		return this.provider;
	}

	/**
	 *
	 * 综合评价
	 *
	 */
	private float synRating;
   
	public void setSynRating(float synRating){
		this.synRating= synRating;       
	}
   
	public float getSynRating(){
		return this.synRating;
	}

	/**
	 *
	 * 与业主关系维系满意度
	 *
	 */
	private float tieMaintenance;
   
	public void setTieMaintenance(float tieMaintenance){
		this.tieMaintenance= tieMaintenance;       
	}
   
	public float getTieMaintenance(){
		return this.tieMaintenance;
	}

	/**
	 *
	 * 故障处理满意度
	 *
	 */
	private float faultDispose;
   
	public void setFaultDispose(float faultDispose){
		this.faultDispose= faultDispose;       
	}
   
	public float getFaultDispose(){
		return this.faultDispose;
	}

	/**
	 *
	 * 语音网络质量满意度
	 *
	 */
	private float phonicsQuality;
   
	public void setPhonicsQuality(float phonicsQuality){
		this.phonicsQuality= phonicsQuality;       
	}
   
	public float getPhonicsQuality(){
		return this.phonicsQuality;
	}

	/**
	 *
	 * 营业厅满意度
	 *
	 */
	private float businessLobby;
   
	public void setBusinessLobby(float businessLobby){
		this.businessLobby= businessLobby;       
	}
   
	public float getBusinessLobby(){
		return this.businessLobby;
	}

	/**
	 *
	 * 投诉客户满意度
	 *
	 */
	private float customerComplaints;
   
	public void setCustomerComplaints(float customerComplaints){
		this.customerComplaints= customerComplaints;       
	}
   
	public float getCustomerComplaints(){
		return this.customerComplaints;
	}

	/**
	 *
	 * 价值客户满意度
	 *
	 */
	private float valueCustomer;
   
	public void setValueCustomer(float valueCustomer){
		this.valueCustomer= valueCustomer;       
	}
   
	public float getValueCustomer(){
		return this.valueCustomer;
	}

	/**
	 *
	 * 集团客户满意度
	 *
	 */
	private float corporateCustomer;
   
	public void setCorporateCustomer(float corporateCustomer){
		this.corporateCustomer= corporateCustomer;       
	}
   
	public float getCorporateCustomer(){
		return this.corporateCustomer;
	}

	/**
	 *
	 * 主动接受基层公司管理、调遣和检查
	 *
	 */
	private float companyAct;
   
	public void setCompanyAct(float companyAct){
		this.companyAct= companyAct;       
	}
   
	public float getCompanyAct(){
		return this.companyAct;
	}

	/**
	 *
	 * 维护人员技术能力、储备及流失情况
	 *
	 */
	private float personnelStatus;
   
	public void setPersonnelStatus(float personnelStatus){
		this.personnelStatus= personnelStatus;       
	}
   
	public float getPersonnelStatus(){
		return this.personnelStatus;
	}

	/**
	 *
	 * 仪器、仪表到位率及完好率情况
	 *
	 */
	private float instrumentStatus;
   
	public void setInstrumentStatus(float instrumentStatus){
		this.instrumentStatus= instrumentStatus;       
	}
   
	public float getInstrumentStatus(){
		return this.instrumentStatus;
	}

	/**
	 *
	 * 管理水平
	 *
	 */
	private float managementAbility;
   
	public void setManagementAbility(float managementAbility){
		this.managementAbility= managementAbility;       
	}
   
	public float getManagementAbility(){
		return this.managementAbility;
	}


	/**
	 *
	 * 总分
	 *
	 */
	private float sum;

	public float getSum(){
		sum = synRating + tieMaintenance + faultDispose + phonicsQuality
				+ businessLobby + customerComplaints + valueCustomer
				+ corporateCustomer + companyAct + personnelStatus
				+ instrumentStatus + managementAbility;
		return sum;
	}

}