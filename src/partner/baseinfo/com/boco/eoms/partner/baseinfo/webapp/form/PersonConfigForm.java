package com.boco.eoms.partner.baseinfo.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:人员配置
 * </p>
 * <p>
 * Description:人员配置
 * </p>
 * <p>
 * Tue Dec 08 15:14:23 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class PersonConfigForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 锟斤拷锟�
	 */
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 *
	 * 所属地市
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
	 * 所属县区
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
	 * 所属公司
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
	 * 维护专业
	 *
	 */
	private java.lang.String serviceProfessional;
   
	public void setServiceProfessional(java.lang.String serviceProfessional){
		this.serviceProfessional= serviceProfessional;       
	}
   
	public java.lang.String getServiceProfessional(){
		return this.serviceProfessional;
	}

	/**
	 *
	 * 职责
	 *
	 */
	private java.lang.String responsibility;
   
	public void setResponsibility(java.lang.String responsibility){
		this.responsibility= responsibility;       
	}
   
	public java.lang.String getResponsibility(){
		return this.responsibility;
	}

	/**
	 *
	 * 基站数
	 *
	 */
	private java.lang.String siteNo;
   
	public void setSiteNo(java.lang.String siteNo){
		this.siteNo= siteNo;       
	}
   
	public java.lang.String getSiteNo(){
		return this.siteNo;
	}

	/**
	 *
	 * 应配数量
	 *
	 */
	private java.lang.String disposeNo;
   
	public void setDisposeNo(java.lang.String disposeNo){
		this.disposeNo= disposeNo;       
	}
   
	public java.lang.String getDisposeNo(){
		return this.disposeNo;
	}

	/**
	 *
	 * 是否删除
	 *
	 */
	private java.lang.Integer isDel;
  
	public void setIsDel(java.lang.Integer isDel){
		this.isDel= isDel;       
	}
  
	public java.lang.Integer getIsDel(){
		return this.isDel;
	}


}