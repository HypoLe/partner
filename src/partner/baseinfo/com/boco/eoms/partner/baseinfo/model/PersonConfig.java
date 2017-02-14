package com.boco.eoms.partner.baseinfo.model;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.partner.excel.model.IParExcelDeal;

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
 * @author benweiwei
 * @version 1.0
 * 
 */
public class PersonConfig extends BaseObject implements IParExcelDeal{

	/**
	 * 主键
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
	private java.lang.Integer siteNo;
   
	public void setSiteNo(java.lang.Integer siteNo){
		this.siteNo= siteNo;       
	}
   
	public java.lang.Integer getSiteNo(){
		return this.siteNo;
	}

	/**
	 *
	 * 应配数量
	 *
	 */
	private java.lang.Integer disposeNo;
   
	public void setDisposeNo(java.lang.Integer disposeNo){
		this.disposeNo= disposeNo;       
	}
   
	public java.lang.Integer getDisposeNo(){
		return this.disposeNo;
	}
	/**
	 *
	 * 删除时间
	 *
	 */
	private java.util.Date delTime;
  
	public void setDelTime(java.util.Date delTime){
		this.delTime= delTime;       
	}
  
	public java.util.Date getDelTime(){
		return this.delTime;
	}

	/**
	 *
	 * 删除人
	 *
	 */
	private java.lang.String delUser;
  
	public void setDelUser(java.lang.String delUser){
		this.delUser= delUser;       
	}
  
	public java.lang.String getDelUser(){
		return this.delUser;
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
	/**
	 * 所属合作伙伴的id（非节点）
	 * 2010-4-7
	 * bww
	 */
	private String partnerid;
	
	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	/**
	 * 所属大合作伙伴的id（非节点）
	 * 2010-4-7
	 * bww
	 */
	private String bigpartnerid;
	
	public String getBigpartnerid() {
		return bigpartnerid;
	}

	public void setBigpartnerid(String bigpartnerid) {
		this.bigpartnerid = bigpartnerid;
	}
	public boolean equals(Object o) {
		if( o instanceof PersonConfig ) {
			PersonConfig personConfig=(PersonConfig)o;
			if (this.id != null || this.id.equals(personConfig.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}