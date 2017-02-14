package com.boco.eoms.partner.baseinfo.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:油机配置
 * </p>
 * <p>
 * Description:合作伙伴资源配置管理 油机配置
 * </p>
 * <p>
 * Sun Dec 13 21:42:25 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() wangjunfeng
 * @moudle.getVersion() 1.0
 * 
 */
public class OilEngineConfigureForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
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
	private java.lang.String isDel;
   
	public void setIsDel(java.lang.String isDel){
		this.isDel= isDel;       
	}
   
	public java.lang.String getIsDel(){
		return this.isDel;
	}

	/**
	 *
	 * 删除用户名
	 *
	 */
	private java.lang.String userNameDel;
   
	public void setUserNameDel(java.lang.String userNameDel){
		this.userNameDel= userNameDel;       
	}
   
	public java.lang.String getUserNameDel(){
		return this.userNameDel;
	}

	/**
	 *
	 * 删除时间
	 *
	 */
	private java.lang.String timeDel;
   
	public void setTimeDel(java.lang.String timeDel){
		this.timeDel= timeDel;       
	}
   
	public java.lang.String getTimeDel(){
		return this.timeDel;
	}

}