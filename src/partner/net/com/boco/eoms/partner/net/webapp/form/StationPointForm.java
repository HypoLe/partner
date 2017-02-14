package com.boco.eoms.partner.net.webapp.form;

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
public class StationPointForm extends BaseForm implements java.io.Serializable {

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
	private java.lang.String siteNo;
   
	public void setSiteNo(java.lang.String siteNo){
		this.siteNo= siteNo;       
	}
   
	public java.lang.String getSiteNo(){
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

	/**
	 *
	 * 2g/3g是否共享
	 *
	 */
	private java.lang.String gshare;

	public java.lang.String getGshare() {
		return gshare;
	}

	public void setGshare(java.lang.String gshare) {
		this.gshare = gshare;
	}	
	/**
	 * 基站名称2
	 * 2010-9-2
	 * benweiwei
	 */
	private java.lang.String siteNameTwo;
	/**
	 * 2G基站站号2
	 * 2010-9-2
	 * benweiwei
	 */
	private java.lang.String siteNoTwo;
	/**
	 * 3G基站站号
	 * 2010-9-2
	 * benweiwei
	 */
	private java.lang.String siteNoThreeG;

	public java.lang.String getSiteNameTwo() {
		return siteNameTwo;
	}

	public void setSiteNameTwo(java.lang.String siteNameTwo) {
		this.siteNameTwo = siteNameTwo;
	}

	public java.lang.String getSiteNoTwo() {
		return siteNoTwo;
	}

	public void setSiteNoTwo(java.lang.String siteNoTwo) {
		this.siteNoTwo = siteNoTwo;
	}

	public java.lang.String getSiteNoThreeG() {
		return siteNoThreeG;
	}

	public void setSiteNoThreeG(java.lang.String siteNoThreeG) {
		this.siteNoThreeG = siteNoThreeG;
	}	

	/**
	 *
	 * 站点客户经理
	 *
	 */
	private java.lang.String siteManager;

	public java.lang.String getSiteManager() {
		return siteManager;
	}

	public void setSiteManager(java.lang.String siteManager) {
		this.siteManager = siteManager;
	}
	
	/**
	 *
	 * 站点客户经理联系方式
	 *
	 */
	private java.lang.String siteManagerTele;

	public java.lang.String getSiteManagerTele() {
		return siteManagerTele;
	}

	public void setSiteManagerTele(java.lang.String siteManagerTele) {
		this.siteManagerTele = siteManagerTele;
	}

	/**
	 *
	 * 站点联系人
	 *
	 */
	private java.lang.String siteLinkman;

	public java.lang.String getSiteLinkman() {
		return siteLinkman;
	}

	public void setSiteLinkman(java.lang.String siteLinkman) {
		this.siteLinkman = siteLinkman;
	}
	
	/**
	 *
	 * 站点联系人联系方式
	 *
	 */
	private java.lang.String siteLinkmanTele;

	public java.lang.String getSiteLinkmanTele() {
		return siteLinkmanTele;
	}

	public void setSiteLinkmanTele(java.lang.String siteLinkmanTele) {
		this.siteLinkmanTele = siteLinkmanTele;
	}

	/**
	 *
	 * 站址
	 *
	 */
	private java.lang.String address;

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	/**
	 *
	 * 专业类型
	 *
	 */
	private java.lang.String specialtyType;

	public java.lang.String getSpecialtyType() {
		return specialtyType;
	}

	public void setSpecialtyType(java.lang.String specialtyType) {
		this.specialtyType = specialtyType;
	}
	
	/**
	 * 所属公司名称
	 */
	private java.lang.String partnername;
	
	public java.lang.String getPartnername() {
		return partnername;
	}

	public void setPartnername(java.lang.String partnername) {
		this.partnername = partnername;
	}
	/**
	 * 网格编号
	 */
	private java.lang.String gridNumber;
	
	public java.lang.String getGridNumber() {
		return gridNumber;
	}

	public void setGridNumber(java.lang.String gridNumber) {
		this.gridNumber = gridNumber;
	}
	
	private String partnerid;
	
	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

}