package com.boco.eoms.partner.serviceArea.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:站点信息管理
 * </p>
 * <p>
 * Description:站点信息管理
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class SiteForm extends BaseForm implements java.io.Serializable {

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
	 * 基站名称
	 *
	 */
	private java.lang.String siteName;
   
	public void setSiteName(java.lang.String siteName){
		this.siteName= siteName;       
	}
   
	public java.lang.String getSiteName(){
		return this.siteName;
	}

	/**
	 *
	 * 基站站号
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
	 * 基站站型
	 *
	 */
	private java.lang.String siteType;
   
	public void setSiteType(java.lang.String siteType){
		this.siteType= siteType;       
	}
   
	public java.lang.String getSiteType(){
		return this.siteType;
	}

	/**
	 *
	 * 基站频段
	 *
	 */
	private java.lang.String frequencyBand;
   
	public void setFrequencyBand(java.lang.String frequencyBand){
		this.frequencyBand= frequencyBand;       
	}
   
	public java.lang.String getFrequencyBand(){
		return this.frequencyBand;
	}

	/**
	 *
	 * 设备生产厂家
	 *
	 */
	private java.lang.String vendor;
   
	public void setVendor(java.lang.String vendor){
		this.vendor= vendor;       
	}
   
	public java.lang.String getVendor(){
		return this.vendor;
	}

	/**
	 *
	 * 维护合作伙伴
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
	 * 覆盖性质
	 *
	 */
	private java.lang.String coverType;
   
	public void setCoverType(java.lang.String coverType){
		this.coverType= coverType;       
	}
   
	public java.lang.String getCoverType(){
		return this.coverType;
	}

	/**
	 *
	 * 基站经度
	 *
	 */

	private java.lang.String  longitude;
   
	public void setLongitude(java.lang.String longitude){
		this.longitude= longitude;       
	}
   
	public java.lang.String getLongitude(){
		return this.longitude;
	}

	/**
	 *
	 * 基站纬度
	 *
	 */
	private java.lang.String latitude;
   
	public void setLatitude(java.lang.String latitude){
		this.latitude= latitude;       
	}
   
	public java.lang.String getLatitude(){
		return this.latitude;
	}

	/**
	 *
	 * 蜂窝类型
	 *
	 */
	private java.lang.String cellulType;
   
	public void setCellulType(java.lang.String cellulType){
		this.cellulType= cellulType;       
	}
   
	public java.lang.String getCellulType(){
		return this.cellulType;
	}

	/**
	 *
	 * 基站等级
	 *
	 */
	private java.lang.String siteLevle;
   
	public void setSiteLevle(java.lang.String siteLevle){
		this.siteLevle= siteLevle;       
	}
   
	public java.lang.String getSiteLevle(){
		return this.siteLevle;
	}

	/**
	 *
	 * 机房类型
	 *
	 */
	private java.lang.String roomType;
   
	public void setRoomType(java.lang.String roomType){
		this.roomType= roomType;       
	}
   
	public java.lang.String getRoomType(){
		return this.roomType;
	}

	/**
	 *
	 * 自维/代维
	 *
	 */
	private java.lang.String maintainType;
   
	public void setMaintainType(java.lang.String maintainType){
		this.maintainType= maintainType;       
	}
   
	public java.lang.String getMaintainType(){
		return this.maintainType;
	}

	/**
	 *
	 * 所在地市
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
	 * 所在县区
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
	 * 建设时间
	 *
	 */
	private java.lang.String setDate;
   
	public void setSetDate(java.lang.String setDate){
		this.setDate= setDate;       
	}
   
	public java.lang.String getSetDate(){
		return this.setDate;
	}

	/**
	 *
	 * 入网时间
	 *
	 */
	private java.lang.String networkDate;
   
	public void setNetworkDate(java.lang.String networkDate){
		this.networkDate= networkDate;       
	}
   
	public java.lang.String getNetworkDate(){
		return this.networkDate;
	}

	/**
	 *
	 * 新增时间
	 *
	 */
	private java.lang.String addTime;
   
	public void setAddTime(java.lang.String addTime){
		this.addTime= addTime;       
	}
   
	public java.lang.String getAddTime(){
		return this.addTime;
	}

	/**
	 *
	 * 新增人
	 *
	 */
	private java.lang.String addUser;
   
	public void setAddUser(java.lang.String addUser){
		this.addUser= addUser;       
	}
   
	public java.lang.String getAddUser(){
		return this.addUser;
	}

	/**
	 *
	 * 修改时间
	 *
	 */
	private java.lang.String updateTime;
   
	public void setUpdateTime(java.lang.String updateTime){
		this.updateTime= updateTime;       
	}
   
	public java.lang.String getUpdateTime(){
		return this.updateTime;
	}

	/**
	 *
	 * 修改人
	 *
	 */
	private java.lang.String updateUser;
   
	public void setUpdateUser(java.lang.String updateUser){
		this.updateUser= updateUser;       
	}
   
	public java.lang.String getUpdateUser(){
		return this.updateUser;
	}

	/**
	 *
	 * 删除时间
	 *
	 */
	private java.lang.String delTime;
   
	public void setDelTime(java.lang.String delTime){
		this.delTime= delTime;       
	}
   
	public java.lang.String getDelTime(){
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
	private java.lang.String isDel;
   
	public void setIsDel(java.lang.String isDel){
		this.isDel= isDel;       
	}
   
	public java.lang.String getIsDel(){
		return this.isDel;
	}
	
	/**
	 * 接口
	 * 字段标识作为同步但未处理的数据
	 * add by:wangjunfeng
	 */
	private java.lang.String unconfig;

	public java.lang.String getUnconfig() {
		return this.unconfig;
	}

	public void setUnconfig(java.lang.String unconfig) {
		this.unconfig = unconfig;
	}
	


}