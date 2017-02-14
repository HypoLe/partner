package com.boco.eoms.partner.net.model;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.partner.excel.model.IParExcelDeal;

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
 * @author benweiwei
 * @version 1.0
 * 
 */
public class Gride extends BaseObject implements IParExcelDeal{

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
	private java.util.Date addTime;
   
	public void setAddTime(java.util.Date addTime){
		this.addTime= addTime;       
	}
   
	public java.util.Date getAddTime(){
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
	private java.util.Date updateTime;
   
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime= updateTime;       
	}
   
	public java.util.Date getUpdateTime(){
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
	private java.util.Date delTime;
   
	public void setDelTime(java.util.Date delTime){
		this.delTime= delTime;       
	}
   
	public java.util.Date getDelTime(){
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


	public boolean equals(Object o) {
		if( o instanceof Gride ) {
			Gride grid=(Gride)o;
			if (this.id != null || this.id.equals(grid.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
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
	 * 所属合作伙伴的id（非节点）
	 * 2010-4-7
	 * bww
	 */
	private String partnerid;
	/**
	 * 所属大合作伙伴的id（非节点）
	 * 2010-4-7
	 * bww
	 */
	private String bigpartnerid;


	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getBigpartnerid() {
		return bigpartnerid;
	}

	public void setBigpartnerid(String bigpartnerid) {
		this.bigpartnerid = bigpartnerid;
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
	/**
	 *
	 * 站点经度
	 *
	 */
	private java.lang.String longitude;
  
	public void setLongitude(java.lang.String longitude){
		this.longitude= longitude;       
	}
  
	public java.lang.String getLongitude(){
		return this.longitude;
	}

	/**
	 *
	 * 站点纬度
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