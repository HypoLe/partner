package com.boco.eoms.partner.baseinfo.model;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.partner.excel.model.IParExcelDeal;

/**
 * <p>
 * Title:仪器仪表配置
 * </p>
 * <p>
 * Description:仪器仪表配置
 * </p>
 * <p>
 * Mon Dec 14 14:07:13 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class InstrumentConfig extends BaseObject  implements IParExcelDeal {

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
	 * 用途
	 *
	 */
	private java.lang.String use;
   
	public void setUse(java.lang.String use){
		this.use= use;       
	}
   
	public java.lang.String getUse(){
		return this.use;
	}

	/**
	 *
	 * 类型
	 *
	 */
	private java.lang.String type;
   
	public void setType(java.lang.String type){
		this.type= type;       
	}
   
	public java.lang.String getType(){
		return this.type;
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
		if( o instanceof InstrumentConfig ) {
			InstrumentConfig instrumentConfig=(InstrumentConfig)o;
			if (this.id != null || this.id.equals(instrumentConfig.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}