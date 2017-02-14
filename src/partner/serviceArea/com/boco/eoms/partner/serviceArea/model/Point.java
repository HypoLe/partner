package com.boco.eoms.partner.serviceArea.model;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.partner.excel.model.IParExcelDeal;

/**
 * <p>
 * Title:数据点
 * </p>
 * <p>
 * Description:服务资源配置 数据点
 * </p>
 * <p>
 * Mon Nov 23 11:36:10 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public class Point extends BaseObject implements IParExcelDeal{

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
	 * 数据点名称
	 *
	 */
	private java.lang.String pointName;
   
	public void setPointName(java.lang.String pointName){
		this.pointName= pointName;       
	}
   
	public java.lang.String getPointName(){
		return this.pointName;
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
	 * 所属网格
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
	 * 新增人员账号
	 *
	 */
	private java.lang.String userNameNew;
  
	public void setUserNameNew(java.lang.String userNameNew){
		this.userNameNew= userNameNew;       
	}
  
	public java.lang.String getUserNameNew(){
		return this.userNameNew;
	}

	/**
	 *
	 * 修改用户账号
	 *
	 */
	private java.lang.String userNameModify;
  
	public void setUserNameModify(java.lang.String userNameModify){
		this.userNameModify= userNameModify;       
	}
  
	public java.lang.String getUserNameModify(){
		return this.userNameModify;
	}

	/**
	 *
	 * 删除用户账号
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
	 * 新增时间
	 *
	 */
	private java.util.Date timeNew;
  
	public void setTimeNew(java.util.Date timeNew){
		this.timeNew= timeNew;       
	}
  
	public java.util.Date getTimeNew(){
		return this.timeNew;
	}

	/**
	 *
	 * 修改时间
	 *
	 */
	private java.util.Date timeModify;
  
	public void setTimeModify(java.util.Date timeModify){
		this.timeModify= timeModify;       
	}
  
	public java.util.Date getTimeModify(){
		return this.timeModify;
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
	
	/**
	 * 网格的id
	 * 2010-4-7
	 * bww
	 */
	private String gridid;
	
	public String getGridid() {
		return gridid;
	}

	public void setGridid(String gridid) {
		this.gridid = gridid;
	}

	
	public boolean equals(Object o) {
		if( o instanceof Point ) {
			Point point=(Point)o;
			if (this.id != null || this.id.equals(point.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}