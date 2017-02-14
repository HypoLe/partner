package com.boco.eoms.partner.net.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:网格
 * </p>
 * <p>
 * Description:网格
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class GrideForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键�
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
	 * 网格名字
	 *
	 */
	private java.lang.String gridName;
   
	public void setGridName(java.lang.String gridName){
		this.gridName= gridName;       
	}
   
	public java.lang.String getGridName(){
		return this.gridName;
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
	 * 合作伙伴名称
	 *
	 */
	private java.lang.String provider;
	/**
	 *
	 * 合作伙伴id
	 *
	 */
	private java.lang.String partnerid;


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
	 * 网格编号
	 *
	 */
	private java.lang.String gridNumber;

	public java.lang.String getGridNumber() {
		return gridNumber;
	}

	public void setGridNumber(java.lang.String gridNumber) {
		this.gridNumber = gridNumber;
	}
	
	/**
	 * 级别
	 * 2010-9-2
	 * benweiwei
	 */
	private String levle;	
	/**
	 * 地域说明
	 * 2010-9-2
	 * benweiwei
	 */
	private String regionExplain;

	public String getLevle() {
		return levle;
	}

	public void setLevle(String levle) {
		this.levle = levle;
	}

	public String getRegionExplain() {
		return regionExplain;
	}

	public void setRegionExplain(String regionExplain) {
		this.regionExplain = regionExplain;
	}	
	/**
	 * 网格运维监督员
	 * 2011-5-25
	 */
	private String gridMonitor;
	/**
	 * 网格运维调度员
	 * 2011-5-25
	 */
	private String gridYardman;

	public String getGridMonitor() {
		return gridMonitor;
	}

	public void setGridMonitor(String gridMonitor) {
		this.gridMonitor = gridMonitor;
	}

	public String getGridYardman() {
		return gridYardman;
	}

	public void setGridYardman(String gridYardman) {
		this.gridYardman = gridYardman;
	}
	
	/**
	 * 自维/代维
	 * 2011-8-10
	 */
	private String maintainType;
	/**
	 * 网格说明
	 * 2010-8-10
	 */
	private String gridExplain;

	public String getMaintainType() {
		return maintainType;
	}

	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}

	public String getGridExplain() {
		return gridExplain;
	}

	public void setGridExplain(String gridExplain) {
		this.gridExplain = gridExplain;
	}

	public java.lang.String getProvider() {
		return provider;
	}

	public void setProvider(java.lang.String provider) {
		this.provider = provider;
	}

	public java.lang.String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(java.lang.String partnerid) {
		this.partnerid = partnerid;
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

}