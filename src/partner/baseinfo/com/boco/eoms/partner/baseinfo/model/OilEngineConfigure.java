package com.boco.eoms.partner.baseinfo.model;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.partner.excel.model.IParExcelDeal;

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
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public class OilEngineConfigure extends BaseObject  implements IParExcelDeal {

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
	private java.lang.Integer disposeNo;
   
	public void setDisposeNo(java.lang.Integer disposeNo){
		this.disposeNo= disposeNo;       
	}
   
	public java.lang.Integer getDisposeNo(){
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
	private java.util.Date timeDel;
   
	public void setTimeDel(java.util.Date timeDel){
		this.timeDel= timeDel;       
	}
   
	public java.util.Date getTimeDel(){
		return this.timeDel;
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
		if( o instanceof OilEngineConfigure ) {
			OilEngineConfigure oilEngineConfigure=(OilEngineConfigure)o;
			if (this.id != null || this.id.equals(oilEngineConfigure.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}