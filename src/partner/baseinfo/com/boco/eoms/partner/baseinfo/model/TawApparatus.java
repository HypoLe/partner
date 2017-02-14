package com.boco.eoms.partner.baseinfo.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:仪器仪表
 * </p>
 * <p>
 * Description:仪器仪表
 * </p>
 * <p>
 * Wed Feb 04 16:31:56 CST 2009
 * </p>
 * 
 * @author fengshaohong
 * @version 3.5
 * 
 */
public class TawApparatus extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 429081959453526868L;
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
	 * 仪器ID
	 *
	 */
	private String apparatusId;
	public String getApparatusId() {
		return apparatusId;
	}

	public void setApparatusId(String apparatusId) {
		this.apparatusId = apparatusId;
	}
	/**
	 *
	 * 仪器名称
	 *
	 */
	private java.lang.String apparatusName;
   
	public void setApparatusName(java.lang.String apparatusName){
		this.apparatusName= apparatusName;       
	}
   
	public java.lang.String getApparatusName(){
		return this.apparatusName;
	}
	/**
	 *
	 * 添加人
	 *
	 */
	private java.lang.String addMan;
	public java.lang.String getAddMan() {
		return addMan;
	}

	public void setAddMan(java.lang.String addMan) {
		this.addMan = addMan;
	}

	/**
	 *
	 * 添加时间
	 *
	 */
	private java.util.Date addTime;
	public java.util.Date getAddTime() {
		return addTime;
	}

	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	/**
	 *
	 * 添加人联系方式
	 *
	 */
	private java.lang.String connectType;
	public java.lang.String getConnectType() {
		return connectType;
	}

	public void setConnectType(java.lang.String connectType) {
		this.connectType = connectType;
	}
	/**
	 *
	 * 生产厂家
	 *
	 */
	private java.lang.String factory;
   
	public void setFactory(java.lang.String factory){
		this.factory= factory;       
	}
   
	public java.lang.String getFactory(){
		return this.factory;
	}

	/**
	 *
	 * 所属公司
	 *
	 */
	private java.lang.String dept_id;
   
	public void setDept_id(java.lang.String dept_id){
		this.dept_id= dept_id;       
	}
   
	public java.lang.String getDept_id(){
		return this.dept_id;
	}

	/**
	 *
	 * 所属地市
	 *
	 */
	private java.lang.String area_id;
   
	public void setArea_id(java.lang.String area_id){
		this.area_id= area_id;       
	}
   
	public java.lang.String getArea_id(){
		return this.area_id;
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
	 * 型号
	 *
	 */
	private java.lang.String model;
   
	public void setModel(java.lang.String model){
		this.model= model;       
	}
   
	public java.lang.String getModel(){
		return this.model;
	}

	/**
	 *
	 * 运行状态
	 *
	 */
	private java.lang.String state;
   
	public void setState(java.lang.String state){
		this.state= state;       
	}
   
	public java.lang.String getState(){
		return this.state;
	}

	/**
	 *
	 * 存放地点
	 *
	 */
	private java.lang.String storage;
   
	public void setStorage(java.lang.String storage){
		this.storage= storage;       
	}
   
	public java.lang.String getStorage(){
		return this.storage;
	}

	/**
	 *
	 * 备注
	 *
	 */
	private java.lang.String remark;
   
	public void setRemark(java.lang.String remark){
		this.remark= remark;       
	}
   
	public java.lang.String getRemark(){
		return this.remark;
	}
	/**
	 * 删除标志位
	 * 2009-9-21
	 * liujinlong
	 */
	private String deleted;

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	
	/**
	 * 新增字段
	 * 2009-12-22
	 * wangjunfeng
	 */
	//所属县区
	private java.lang.String city;
	   
	public void setCity(java.lang.String city){
		this.city= city;       
	}
   
	public java.lang.String getCity(){
		return this.city;
	}

	//所属驻点
	private java.lang.String station;
	   
	public void setStation(java.lang.String station){
		this.station= station;       
	}
   
	public java.lang.String getStation(){
		return this.station;
	}

	
	//维护专业 service_professional
	private java.lang.String serviceProfessional;
	   
	public void setServiceProfessional(java.lang.String serviceProfessional){
		this.serviceProfessional= serviceProfessional;       
	}
   
	public java.lang.String getServiceProfessional(){
		return this.serviceProfessional;
	}
	
	//序列号 no
	private java.lang.String no;
	   
	public void setNo(java.lang.String no){
		this.no= no;       
	}
   
	public java.lang.String getNo(){
		return this.no;
	}
	
	//序列号缩略图 no_thumbnail
	private java.lang.String noThumbnail;
	   
	public void setNoThumbnail(java.lang.String noThumbnail){
		this.noThumbnail= noThumbnail;       
	}
   
	public java.lang.String getNoThumbnail(){
		return this.noThumbnail;
	}
	
	//用途 use
	private java.lang.String use;
	   
	public void setUse(java.lang.String use){
		this.use= use;       
	}
   
	public java.lang.String getUse(){
		return this.use;
	}
	
	//是否可以分配
	private java.lang.String  whether_allocate  ;
	
	public void setWhether_allocate(java.lang.String whether_allocate){
		this.whether_allocate = whether_allocate ;
	}
	
	public java.lang.String getWhether_allocate(){
		return this.whether_allocate ;
	}

	//价格 price
	private java.lang.String price;
	   
	public void setPrice(java.lang.String price){
		this.price= price;       
	}
   
	public java.lang.String getPrice(){
		return this.price;
	}

	//仪器仪表缩略图 thumbnail
	private java.lang.String thumbnail;
	   
	public void setThumbnail(java.lang.String thumbnail){
		this.thumbnail= thumbnail;       
	}
   
	public java.lang.String getThumbnail(){
		return this.thumbnail;
	}

	//二维码 dimensional_code
	private java.lang.String dimensionalCode;
	   
	public void setDimensionalCode(java.lang.String dimensionalCode){
		this.dimensionalCode= dimensionalCode;       
	}
   
	public java.lang.String getDimensionalCode(){
		return this.dimensionalCode;
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
	/**
	 * 所属地市的areaid
	 * 2010-4-7
	 * bww
	 */
	private String areaidtrue;
	
	private String whetherallocate;
	


	public String getWhetherallocate() {
		return whetherallocate;
	}

	public void setWhetherallocate(String whetherallocate) {
		this.whetherallocate = whetherallocate;
	}

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

	public String getAreaidtrue() {
		return areaidtrue;
	}

	public void setAreaidtrue(String areaidtrue) {
		this.areaidtrue = areaidtrue;
	}
	
	
	public boolean equals(Object o) {
		if( o instanceof TawApparatus ) {
			TawApparatus tawApparatus=(TawApparatus)o;
			if (this.id != null || this.id.equals(tawApparatus.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	
}