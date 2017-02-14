package com.boco.eoms.partner.baseinfo.webapp.form;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:车辆管理
 * </p>
 * <p>
 * Description:车辆管理
 * </p>
 * <p>
 * Thu Feb 05 13:54:40 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() fengshaohong
 * @moudle.getVersion() 3.5
 * 
 */
public class TawPartnerCarForm extends BaseForm implements java.io.Serializable {

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

	private FormFile accessoryName; // 批量录入文件

	public FormFile getAccessoryName() {
		return accessoryName;
	}

	public void setAccessoryName(FormFile accessoryName) {
		this.accessoryName = accessoryName;
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
	 * 车牌号
	 * 
	 */
	private java.lang.String car_number;

	public void setCar_number(java.lang.String car_number) {
		this.car_number = car_number;
	}

	public java.lang.String getCar_number() {
		return this.car_number;
	}

	/**
	 * 
	 * 所属公司
	 * 
	 */
	private java.lang.String dept_id;

	public void setDept_id(java.lang.String dept_id) {
		this.dept_id = dept_id;
	}

	public java.lang.String getDept_id() {
		return this.dept_id;
	}

	/**
	 * 
	 * 所属地市
	 * 
	 */
	private java.lang.String area_id;

	public void setArea_id(java.lang.String area_id) {
		this.area_id = area_id;
	}

	public java.lang.String getArea_id() {
		return this.area_id;
	}

	/**
	 * 购买时间
	 */
	private java.lang.String purchaseTime;

	public java.lang.String getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(java.lang.String purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	/**
	 * 车辆驱动
	 */
	private java.lang.String driveType;

	public java.lang.String getDriveType() {
		return driveType;
	}

	public void setDriveType(java.lang.String driveType) {
		this.driveType = driveType;
	}

	/**
	 * 
	 * 开始使用时间
	 * 
	 */
	private java.lang.String start_time;

	public void setStart_time(java.lang.String start_time) {
		this.start_time = start_time;
	}

	public java.lang.String getStart_time() {
		return this.start_time;
	}

	/**
	 * 
	 * 停止使用时间
	 * 
	 */
	private java.lang.String endTime;

	public java.lang.String getEndTime() {
		return endTime;
	}

	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 
	 * 所属片区
	 * 
	 */
	private java.lang.String piece;

	public void setPiece(java.lang.String piece) {
		this.piece = piece;
	}

	public java.lang.String getPiece() {
		return this.piece;
	}

	/**
	 * 基站还是线路
	 */
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * 
	 * 所属片区的数组形式
	 * 
	 */
	private java.lang.String[] arrayPiece;

	public void setArrayPiece(java.lang.String[] arrayPiece) {
		this.arrayPiece = arrayPiece;
	}

	public java.lang.String[] getArrayPiece() {
		return arrayPiece;
	}

	/**
	 * 
	 * 全部所属片区的数组形式
	 * 
	 */
	private java.lang.String[] arrayPieceAll;

	public java.lang.String[] getArrayPieceAll() {
		return arrayPieceAll;
	}

	public void setArrayPieceAll(java.lang.String[] arrayPieceAll) {
		this.arrayPieceAll = arrayPieceAll;
	}

	/**
	 * 
	 * 备注
	 * 
	 */
	private java.lang.String remark;

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getRemark() {
		return this.remark;
	}

	/**
	 * 删除标志位 2009-9-21 liujinlong
	 */
	private String deleted;

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	/**
	 * 添加字段 2009-12-22 wangjunfeng
	 */

	// 车辆品牌BRAND
	private java.lang.String brand;

	public void setBrand(java.lang.String brand) {
		this.brand = brand;
	}

	public java.lang.String getBrand() {
		return this.brand;
	}

	// 行驶证号DRIVING_LICENSE
	private java.lang.String drivingLicense;

	public void setDrivingLicense(java.lang.String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public java.lang.String getDrivingLicense() {
		return this.drivingLicense;
	}

	// 生产厂家FACTORY
	private java.lang.String factory;

	public void setFactory(java.lang.String factory) {
		this.factory = factory;
	}

	public java.lang.String getFactory() {
		return this.factory;
	}

	// 型号规格 MODEL
	private java.lang.String model;

	public void setModel(java.lang.String model) {
		this.model = model;
	}

	public java.lang.String getModel() {
		return this.model;
	}

	// 发动机号 ENGINE_NO
	private java.lang.String engineNo;

	public void setEngineNo(java.lang.String engineNo) {
		this.engineNo = engineNo;
	}

	public java.lang.String getEngineNo() {
		return this.engineNo;
	}

	// 排气量 air_displacement
	private java.lang.String airDisplacement;

	public void setAirDisplacement(java.lang.String airDisplacement) {
		this.airDisplacement = airDisplacement;
	}

	public java.lang.String getAirDisplacement() {
		return this.airDisplacement;
	}

	// 年检日期 annual_check_data
	private java.lang.String annualCheckData;

	public void setAnnualCheckData(java.lang.String annualCheckData) {
		this.annualCheckData = annualCheckData;
	}

	public java.lang.String getAnnualCheckData() {
		return this.annualCheckData;
	}

	// 所属驻点 station
	private java.lang.String station;

	public void setStation(java.lang.String station) {
		this.station = station;
	}

	public java.lang.String getStation() {
		return this.station;
	}

	// 维护专业 service_professional
	private java.lang.String serviceProfessional;

	public void setServiceProfessional(java.lang.String serviceProfessional) {
		this.serviceProfessional = serviceProfessional;
	}

	public java.lang.String getServiceProfessional() {
		return this.serviceProfessional;
	}

	// 开始使用里程数 start_use_milemeter
	private java.lang.String startUseMilemeter;

	public void setStartUseMilemeter(java.lang.String startUseMilemeter) {
		this.startUseMilemeter = startUseMilemeter;
	}

	public java.lang.String getStartUseMilemeter() {
		return this.startUseMilemeter;
	}

	// 所有性质 ownership_type
	private java.lang.String ownershipType;

	public void setOwnershipType(java.lang.String ownershipType) {
		this.ownershipType = ownershipType;
	}

	public java.lang.String getOwnershipType() {
		return this.ownershipType;
	}

	// 用途 use
	private java.lang.String use;

	public void setUse(java.lang.String use) {
		this.use = use;
	}

	public java.lang.String getUse() {
		return this.use;
	}

	// 二维码 dimensional_code
	private java.lang.String dimensionalCode;

	public void setDimensionalCode(java.lang.String dimensionalCode) {
		this.dimensionalCode = dimensionalCode;
	}

	public java.lang.String getDimensionalCode() {
		return this.dimensionalCode;
	}

	// 车辆缩略图 thumbnail
	private java.lang.String thumbnail;

	public void setThumbnail(java.lang.String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public java.lang.String getThumbnail() {
		return this.thumbnail;
	}

	// 行驶证缩略图 driving_license _thumbnail
	private java.lang.String drivingLicenseThumbnail;

	public void setDrivingLicenseThumbnail(
			java.lang.String drivingLicenseThumbnail) {
		this.drivingLicenseThumbnail = drivingLicenseThumbnail;
	}

	public java.lang.String getDrivingLicenseThumbnail() {
		return this.drivingLicenseThumbnail;
	}

	/**
	 * 上传的车辆缩略图文件对象
	 */
	private FormFile thumbnailFile;

	public FormFile getThumbnailFile() {
		return thumbnailFile;
	}

	public void setThumbnailFile(FormFile thumbnailFile) {
		this.thumbnailFile = thumbnailFile;
	}

	public java.lang.String city;

	public java.lang.String getCity() {
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}

	/**
	 * 所属合作伙伴的id（非节点） 2010-4-7 bww
	 */
	private String partnerid;
	/**
	 * 所属大合作伙伴的id（非节点） 2010-4-7 bww
	 */
	private String bigpartnerid;
	/**
	 * 所属地市的areaid 2010-4-7 bww
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
	 * 所属代维公司名称
	 */
	private String carBelongCompany;
	/**
	 * 车辆状态
	 */
	private String carState;
	/**
	 * 是否可调配
	 */
	private String isPrepare;
	/**
	 * 车型
	 */
	private String carForm;
	/**
	 * 车型
	 */
	private String formInterfaceHeadId;
	/**
	 * 出厂日期
	 */
	private String carOutDate;

	public String getCarBelongCompany() {
		return carBelongCompany;
	}

	public void setCarBelongCompany(String carBelongCompany) {
		this.carBelongCompany = carBelongCompany;
	}

	public String getCarState() {
		return carState;
	}

	public void setCarState(String carState) {
		this.carState = carState;
	}

	public String getIsPrepare() {
		return isPrepare;
	}

	public void setIsPrepare(String isPrepare) {
		this.isPrepare = isPrepare;
	}

	public String getCarForm() {
		return carForm;
	}

	public void setCarForm(String carForm) {
		this.carForm = carForm;
	}

	public String getCarOutDate() {
		return carOutDate;
	}

	public void setCarOutDate(String carOutDate) {
		this.carOutDate = carOutDate;
	}

	public String getFormInterfaceHeadId() {
		return formInterfaceHeadId;
	}

	public void setFormInterfaceHeadId(String formInterfaceHeadId) {
		this.formInterfaceHeadId = formInterfaceHeadId;
	}

	private String formInterfaceHeadIdXls;
	private String partneridXls;

	public String getFormInterfaceHeadIdXls() {
		return formInterfaceHeadIdXls;
	}

	public void setFormInterfaceHeadIdXls(String formInterfaceHeadIdXls) {
		this.formInterfaceHeadIdXls = formInterfaceHeadIdXls;
	}

	public String getPartneridXls() {
		return partneridXls;
	}

	public void setPartneridXls(String partneridXls) {
		this.partneridXls = partneridXls;
	}

	
}