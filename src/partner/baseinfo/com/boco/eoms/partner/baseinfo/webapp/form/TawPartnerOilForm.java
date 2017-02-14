package com.boco.eoms.partner.baseinfo.webapp.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:油机信息
 * </p>
 * <p>
 * Description:油机信息
 * </p>
 * <p>
 * Thu Feb 05 13:56:15 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() fengshaohong
 * @moudle.getVersion() 3.5
 * 
 */
public class TawPartnerOilForm extends BaseForm implements java.io.Serializable {

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
private FormFile accessoryName; //批量录入文件
    
	public FormFile getAccessoryName() {
		return accessoryName;
	}
	public void setAccessoryName(FormFile accessoryName) {
		this.accessoryName = accessoryName;
	}
	/**
	 *
	 * 油机编号
	 *
	 */
	private java.lang.String oil_number;
   
	public void setOil_number(java.lang.String oil_number){
		this.oil_number= oil_number;       
	}
   
	public java.lang.String getOil_number(){
		return this.oil_number;
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
	private java.lang.String addTime;
	public java.lang.String getAddTime() {
		return addTime;
	}

	public void setAddTime(java.lang.String addTime) {
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
	 * 油机厂家
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
	 * 油机型号
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
	 * 功率
	 *
	 */
	private java.lang.String power;
   
	public void setPower(java.lang.String power){
		this.power= power;       
	}
   
	public java.lang.String getPower(){
		return this.power;
	}

	/**
	 *
	 * 性质
	 *
	 */
	private java.lang.String kind;
   
	public void setKind(java.lang.String kind){
		this.kind= kind;       
	}
   
	public java.lang.String getKind(){
		return this.kind;
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
	 * 储备地点
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
     * 存放地点 
     *
     */
	private String savePlace;
	
	public String getSavePlace() {
		return savePlace;
	}

	public void setSavePlace(String savePlace) {
		this.savePlace = savePlace;
	}
	
	/**
	 * 
	 * 启动方式
	 *
	 */
	private String startWay;
	

	public String getStartWay() {
		return startWay;
	}

	public void setStartWay(String startWay) {
		this.startWay = startWay;
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
	 * 添加字段
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
	
	//归属驻点
	private java.lang.String station;
	   
	public void setStation(java.lang.String station){
		this.station= station;       
	}
   
	public java.lang.String getStation(){
		return this.station;
	}
	
	//维护专业
	private java.lang.String serviceProfessional;
	   
	public void setServiceProfessional(java.lang.String serviceProfessional){
		this.serviceProfessional= serviceProfessional;       
	}
   
	public java.lang.String getServiceProfessional(){
		return this.serviceProfessional;
	}
	
	//二维码 DIMENSIONAL_CODE
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
    /**
     * 导入excel
     * 2012-02-17
     * @author munanyang
     */
	private FormFile importFile;

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
}