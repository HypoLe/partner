package com.boco.eoms.partner.baseinfo.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:代维响应速度上报 统计
 * </p>
 * <p>
 * Description:代维响应速度上报 统计
 * </p>
 * @moudle.getAuthor() wangjunfeng
 * @moudle.getVersion() 1.0
 * 
 */
public class ServiceSpeedStatForm  {


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
	 * 月
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
	 * 网络故障响应度
	 *
	 */
	private float webfailure;
   
	public void setWebfailure(float webfailure){
		this.webfailure= webfailure;       
	}
   
	public float getWebfailure(){
		return this.webfailure;
	}

	/**
	 *
	 * 客户投诉处理响应度
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
	 * 对基层业务、服务的响应度
	 *
	 */
	private float toService;
   
	public void setToService(float toService){
		this.toService= toService;       
	}
   
	public float getToService(){
		return this.toService;
	}

	/**
	 *
	 * 表报上报及时率
	 *
	 */
	private float fromReport;
   
	public void setFromReport(float fromReport){
		this.fromReport= fromReport;       
	}
   
	public float getFromReport(){
		return this.fromReport;
	}

	/**
	 *
	 * 表报上报准确率
	 *
	 */
	private float fromPrecision;
   
	public void setFromPrecision(float fromPrecision){
		this.fromPrecision= fromPrecision;       
	}
   
	public float getFromPrecision(){
		return this.fromPrecision;
	}

	/**
	 *
	 * 资料更新准确率
	 *
	 */
	private float datumUpdate;
   
	public void setDatumUpdate(float datumUpdate){
		this.datumUpdate= datumUpdate;       
	}
   
	public float getDatumUpdate(){
		return this.datumUpdate;
	}

	/**
	 *
	 * 应急通信保障响应度
	 *
	 */
	private float commSecurity;
   
	public void setCommSecurity(float commSecurity){
		this.commSecurity= commSecurity;       
	}
   
	public float getCommSecurity(){
		return this.commSecurity;
	}


	/**
	 *
	 * 总分
	 *
	 */
	private float sum;

	public float getSum(){
		sum = webfailure + customerComplaints + toService + fromReport + 
		fromPrecision + datumUpdate + commSecurity;
		return sum;
	}



}