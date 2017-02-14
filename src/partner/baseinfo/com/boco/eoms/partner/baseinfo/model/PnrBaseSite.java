package com.boco.eoms.partner.baseinfo.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:合作伙伴站址信息管理
 * </p>
 * <p>
 * Description:站址信息管理
 * </p>
 * <p>
 * Wed Mar 24 14:01:56 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrBaseSite extends BaseObject {

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
	 * 资源名称
	 *
	 */
	private java.lang.String name;
   
	public void setName(java.lang.String name){
		this.name= name;       
	}
   
	public java.lang.String getName(){
		return this.name;
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
	private java.util.Date updateTime;
   
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime= updateTime;       
	}
   
	public java.util.Date getUpdateTime(){
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
	private java.util.Date delTime;
   
	public void setDelTime(java.util.Date delTime){
		this.delTime= delTime;       
	}
   
	public java.util.Date getDelTime(){
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
	 *
	 * 资源编码
	 *
	 */
	private java.lang.String code;
   
	public void setCode(java.lang.String code){
		this.code= code;       
	}
   
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *
	 * 状态
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
	 * 软件版本
	 *
	 */
	private java.lang.String softVersion;
   
	public void setSoftVersion(java.lang.String softVersion){
		this.softVersion= softVersion;       
	}
   
	public java.lang.String getSoftVersion(){
		return this.softVersion;
	}

	/**
	 *
	 * 硬件平台
	 *
	 */
	private java.lang.String hardwareFlat;
   
	public void setHardwareFlat(java.lang.String hardwareFlat){
		this.hardwareFlat= hardwareFlat;       
	}
   
	public java.lang.String getHardwareFlat(){
		return this.hardwareFlat;
	}

	/**
	 *
	 * 硬件版本
	 *
	 */
	private java.lang.String hardwareVersion;
   
	public void setHardwareVersion(java.lang.String hardwareVersion){
		this.hardwareVersion= hardwareVersion;       
	}
   
	public java.lang.String getHardwareVersion(){
		return this.hardwareVersion;
	}

	/**
	 *
	 * 基站类型
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
	 * 基站标识
	 *
	 */
	private java.lang.String siteNO;
   
	public void setSiteNO(java.lang.String siteNO){
		this.siteNO= siteNO;       
	}
   
	public java.lang.String getSiteNO(){
		return this.siteNO;
	}

	/**
	 *
	 * 基站用途
	 *
	 */
	private java.lang.String siteUse;
   
	public void setSiteUse(java.lang.String siteUse){
		this.siteUse= siteUse;       
	}
   
	public java.lang.String getSiteUse(){
		return this.siteUse;
	}

	/**
	 *
	 * Vip标识
	 *
	 */
	private java.lang.String vipNO;
   
	public void setVipNO(java.lang.String vipNO){
		this.vipNO= vipNO;       
	}
   
	public java.lang.String getVipNO(){
		return this.vipNO;
	}

	/**
	 *
	 * 村通标识
	 *
	 */
	private java.lang.String connectCode;
   
	public void setConnectCode(java.lang.String connectCode){
		this.connectCode= connectCode;       
	}
   
	public java.lang.String getConnectCode(){
		return this.connectCode;
	}

	/**
	 *
	 * 频段
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
	 * 设备提供商名称
	 *
	 */
	private java.lang.String equipmentFactury;
   
	public void setEquipmentFactury(java.lang.String equipmentFactury){
		this.equipmentFactury= equipmentFactury;       
	}
   
	public java.lang.String getEquipmentFactury(){
		return this.equipmentFactury;
	}

	/**
	 *
	 * 传输设备厂家
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
	 * 出厂日期
	 *
	 */
	private java.util.Date productDate;
   
	public void setProductDate(java.util.Date productDate){
		this.productDate= productDate;       
	}
   
	public java.util.Date getProductDate(){
		return this.productDate;
	}

	/**
	 *
	 * 出厂序列号
	 *
	 */
	private java.lang.String productNO;
   
	public void setProductNO(java.lang.String productNO){
		this.productNO= productNO;       
	}
   
	public java.lang.String getProductNO(){
		return this.productNO;
	}

	/**
	 *
	 * 站名
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
	 * 站址号
	 *
	 */
	private java.lang.String addressNO;
   
	public void setAddressNO(java.lang.String addressNO){
		this.addressNO= addressNO;       
	}
   
	public java.lang.String getAddressNO(){
		return this.addressNO;
	}

	/**
	 *
	 * 所属地市
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
	 * 所属县区
	 *
	 */
	private java.lang.String town;
   
	public void setTown(java.lang.String town){
		this.town= town;       
	}
   
	public java.lang.String getTown(){
		return this.town;
	}

	/**
	 *
	 * 详细地址
	 *
	 */
	private java.lang.String address;
   
	public void setAddress(java.lang.String address){
		this.address= address;       
	}
   
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *
	 * 归属BSC
	 *
	 */
	private java.lang.String bsc;
   
	public void setBsc(java.lang.String bsc){
		this.bsc= bsc;       
	}
   
	public java.lang.String getBsc(){
		return this.bsc;
	}

	/**
	 *
	 * 基站分类
	 *
	 */
	private java.lang.String siteSort;
   
	public void setSiteSort(java.lang.String siteSort){
		this.siteSort= siteSort;       
	}
   
	public java.lang.String getSiteSort(){
		return this.siteSort;
	}

	/**
	 *
	 * 经度
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
	 * 纬度
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
	 * 海拔（米）
	 *
	 */
	private java.lang.String altitude;
   
	public void setAltitude(java.lang.String altitude){
		this.altitude= altitude;       
	}
   
	public java.lang.String getAltitude(){
		return this.altitude;
	}

	/**
	 *
	 * 管理此网元的OMC
	 *
	 */
	private java.lang.String omc;
   
	public void setOmc(java.lang.String omc){
		this.omc= omc;       
	}
   
	public java.lang.String getOmc(){
		return this.omc;
	}

	/**
	 *
	 * 边界站详细描述
	 *
	 */
	private java.lang.String borderDescription;
   
	public void setBorderDescription(java.lang.String borderDescription){
		this.borderDescription= borderDescription;       
	}
   
	public java.lang.String getBorderDescription(){
		return this.borderDescription;
	}

	/**
	 *
	 * 覆盖区域标识
	 *
	 */
	private java.lang.String coverArea;
   
	public void setCoverArea(java.lang.String coverArea){
		this.coverArea= coverArea;       
	}
   
	public java.lang.String getCoverArea(){
		return this.coverArea;
	}

	/**
	 *
	 * 覆盖类型
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
	 * 室内覆盖
	 *
	 */
	private java.lang.String courtCover;
   
	public void setCourtCover(java.lang.String courtCover){
		this.courtCover= courtCover;       
	}
   
	public java.lang.String getCourtCover(){
		return this.courtCover;
	}

	/**
	 *
	 * 地理特征
	 *
	 */
	private java.lang.String geographyCharacter;
   
	public void setGeographyCharacter(java.lang.String geographyCharacter){
		this.geographyCharacter= geographyCharacter;       
	}
   
	public java.lang.String getGeographyCharacter(){
		return this.geographyCharacter;
	}

	/**
	 *
	 * 区域类别
	 *
	 */
	private java.lang.String areaType;
   
	public void setAreaType(java.lang.String areaType){
		this.areaType= areaType;       
	}
   
	public java.lang.String getAreaType(){
		return this.areaType;
	}

	/**
	 *
	 * 是否已监控
	 *
	 */
	private java.lang.String isMonitor;
   
	public void setIsMonitor(java.lang.String isMonitor){
		this.isMonitor= isMonitor;       
	}
   
	public java.lang.String getIsMonitor(){
		return this.isMonitor;
	}

	/**
	 *
	 * 小区数
	 *
	 */
	private java.lang.String courtNum;
   
	public void setCourtNum(java.lang.String courtNum){
		this.courtNum= courtNum;       
	}
   
	public java.lang.String getCourtNum(){
		return this.courtNum;
	}

	/**
	 *
	 * 载频数
	 *
	 */
	private java.lang.String carrierFrequencyNum;
   
	public void setCarrierFrequencyNum(java.lang.String carrierFrequencyNum){
		this.carrierFrequencyNum= carrierFrequencyNum;       
	}
   
	public java.lang.String getCarrierFrequencyNum(){
		return this.carrierFrequencyNum;
	}

	/**
	 *
	 * 入网时间
	 *
	 */
	private java.util.Date networkDate;
   
	public void setNetworkDate(java.util.Date networkDate){
		this.networkDate= networkDate;       
	}
   
	public java.util.Date getNetworkDate(){
		return this.networkDate;
	}

	/**
	 *
	 * 基站赴途时长（小时）
	 *
	 */
	private java.lang.String timeForArrive;
   
	public void setTimeForArrive(java.lang.String timeForArrive){
		this.timeForArrive= timeForArrive;       
	}
   
	public java.lang.String getTimeForArrive(){
		return this.timeForArrive;
	}

	/**
	 *
	 * 供电所电话
	 *
	 */
	private java.lang.String powerProviderTel;
   
	public void setPowerProviderTel(java.lang.String powerProviderTel){
		this.powerProviderTel= powerProviderTel;       
	}
   
	public java.lang.String getPowerProviderTel(){
		return this.powerProviderTel;
	}

	/**
	 *
	 * 交（直流站）
	 *
	 */
	private java.lang.String acOrDc;
   
	public void setAcOrDc(java.lang.String acOrDc){
		this.acOrDc= acOrDc;       
	}
   
	public java.lang.String getAcOrDc(){
		return this.acOrDc;
	}

	/**
	 *
	 * 传输类型
	 *
	 */
	private java.lang.String transfersType;
   
	public void setTransfersType(java.lang.String transfersType){
		this.transfersType= transfersType;       
	}
   
	public java.lang.String getTransfersType(){
		return this.transfersType;
	}

	/**
	 *
	 * 动力是否共站
	 *
	 */
	private java.lang.String togetherSite;
   
	public void setTogetherSite(java.lang.String togetherSite){
		this.togetherSite= togetherSite;       
	}
   
	public java.lang.String getTogetherSite(){
		return this.togetherSite;
	}

	/**
	 *
	 * 动力共站信息
	 *
	 */
	private java.lang.String togetherSiteInf;
   
	public void setTogetherSiteInf(java.lang.String togetherSiteInf){
		this.togetherSiteInf= togetherSiteInf;       
	}
   
	public java.lang.String getTogetherSiteInf(){
		return this.togetherSiteInf;
	}

	/**
	 *
	 * 无委证号
	 *
	 */
	private java.lang.String noncommissionNO;
   
	public void setNoncommissionNO(java.lang.String noncommissionNO){
		this.noncommissionNO= noncommissionNO;       
	}
   
	public java.lang.String getNoncommissionNO(){
		return this.noncommissionNO;
	}

	/**
	 *
	 * 建站时间
	 *
	 */
	private java.util.Date setDate;
   
	public void setSetDate(java.util.Date setDate){
		this.setDate= setDate;       
	}
   
	public java.util.Date getSetDate(){
		return this.setDate;
	}

	/**
	 *
	 * 是否代维
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
	 * 代维厂家
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
	 * 市电引入情况
	 *
	 */
	private java.lang.String powerState;
   
	public void setPowerState(java.lang.String powerState){
		this.powerState= powerState;       
	}
   
	public java.lang.String getPowerState(){
		return this.powerState;
	}

	/**
	 *
	 * 门锁情况
	 *
	 */
	private java.lang.String lockState;
   
	public void setLockState(java.lang.String lockState){
		this.lockState= lockState;       
	}
   
	public java.lang.String getLockState(){
		return this.lockState;
	}

	/**
	 *
	 * 维护责任人
	 *
	 */
	private java.lang.String dutyPerson;
   
	public void setDutyPerson(java.lang.String dutyPerson){
		this.dutyPerson= dutyPerson;       
	}
   
	public java.lang.String getDutyPerson(){
		return this.dutyPerson;
	}

	/**
	 *
	 * 维护责任人电话
	 *
	 */
	private java.lang.String dutyPersonTel;
   
	public void setDutyPersonTel(java.lang.String dutyPersonTel){
		this.dutyPersonTel= dutyPersonTel;       
	}
   
	public java.lang.String getDutyPersonTel(){
		return this.dutyPersonTel;
	}

	/**
	 *
	 * 进出基站情况
	 *
	 */
	private java.lang.String inOrOut;
   
	public void setInOrOut(java.lang.String inOrOut){
		this.inOrOut= inOrOut;       
	}
   
	public java.lang.String getInOrOut(){
		return this.inOrOut;
	}

	/**
	 *
	 * 钥匙保管人及电话
	 *
	 */
	private java.lang.String keyKeeper;
   
	public void setKeyKeeper(java.lang.String keyKeeper){
		this.keyKeeper= keyKeeper;       
	}
   
	public java.lang.String getKeyKeeper(){
		return this.keyKeeper;
	}

	/**
	 *
	 * 钥匙存放位置
	 *
	 */
	private java.lang.String keyLeave;
   
	public void setKeyLeave(java.lang.String keyLeave){
		this.keyLeave= keyLeave;       
	}
   
	public java.lang.String getKeyLeave(){
		return this.keyLeave;
	}

	/**
	 *
	 * 机房产权
	 *
	 */
	private java.lang.String roomRight;
   
	public void setRoomRight(java.lang.String roomRight){
		this.roomRight= roomRight;       
	}
   
	public java.lang.String getRoomRight(){
		return this.roomRight;
	}

	/**
	 *
	 * 房屋结构
	 *
	 */
	private java.lang.String houseFrame;
   
	public void setHouseFrame(java.lang.String houseFrame){
		this.houseFrame= houseFrame;       
	}
   
	public java.lang.String getHouseFrame(){
		return this.houseFrame;
	}

	/**
	 *
	 * 房屋属性
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
	 * 房屋面积（平方米）
	 *
	 */
	private java.lang.String houseArea;
   
	public void setHouseArea(java.lang.String houseArea){
		this.houseArea= houseArea;       
	}
   
	public java.lang.String getHouseArea(){
		return this.houseArea;
	}

	/**
	 *
	 * 每月房租（元）
	 *
	 */
	private java.lang.String rent;
   
	public void setRent(java.lang.String rent){
		this.rent= rent;       
	}
   
	public java.lang.String getRent(){
		return this.rent;
	}

	/**
	 *
	 * 业主
	 *
	 */
	private java.lang.String owner;
   
	public void setOwner(java.lang.String owner){
		this.owner= owner;       
	}
   
	public java.lang.String getOwner(){
		return this.owner;
	}

	/**
	 *
	 * 业主身份证号
	 *
	 */
	private java.lang.String ownerIDcard;
   
	public void setOwnerIDcard(java.lang.String ownerIDcard){
		this.ownerIDcard= ownerIDcard;       
	}
   
	public java.lang.String getOwnerIDcard(){
		return this.ownerIDcard;
	}

	/**
	 *
	 * 业主联系方式
	 *
	 */
	private java.lang.String ownerTel;
   
	public void setOwnerTel(java.lang.String ownerTel){
		this.ownerTel= ownerTel;       
	}
   
	public java.lang.String getOwnerTel(){
		return this.ownerTel;
	}

	/**
	 *
	 * 房产证号
	 *
	 */
	private java.lang.String roomNO;
   
	public void setRoomNO(java.lang.String roomNO){
		this.roomNO= roomNO;       
	}
   
	public java.lang.String getRoomNO(){
		return this.roomNO;
	}

	/**
	 *
	 * 土地证号
	 *
	 */
	private java.lang.String groundCardNO;
   
	public void setGroundCardNO(java.lang.String groundCardNO){
		this.groundCardNO= groundCardNO;       
	}
   
	public java.lang.String getGroundCardNO(){
		return this.groundCardNO;
	}

	/**
	 *
	 * 租赁合同号
	 *
	 */
	private java.lang.String tenancyNO;
   
	public void setTenancyNO(java.lang.String tenancyNO){
		this.tenancyNO= tenancyNO;       
	}
   
	public java.lang.String getTenancyNO(){
		return this.tenancyNO;
	}

	/**
	 *
	 * 基站端传输设备类型
	 *
	 */
	private java.lang.String transEquipmentType;
   
	public void setTransEquipmentType(java.lang.String transEquipmentType){
		this.transEquipmentType= transEquipmentType;       
	}
   
	public java.lang.String getTransEquipmentType(){
		return this.transEquipmentType;
	}

	/**
	 *
	 * 传输归属
	 *
	 */
	private java.lang.String transfersBelong;
   
	public void setTransfersBelong(java.lang.String transfersBelong){
		this.transfersBelong= transfersBelong;       
	}
   
	public java.lang.String getTransfersBelong(){
		return this.transfersBelong;
	}

	/**
	 *
	 * 传输端口号
	 *
	 */
	private java.lang.String transfersPort;
   
	public void setTransfersPort(java.lang.String transfersPort){
		this.transfersPort= transfersPort;       
	}
   
	public java.lang.String getTransfersPort(){
		return this.transfersPort;
	}

	/**
	 *
	 * 光缆线路类型
	 *
	 */
	private java.lang.String fiberType;
   
	public void setFiberType(java.lang.String fiberType){
		this.fiberType= fiberType;       
	}
   
	public java.lang.String getFiberType(){
		return this.fiberType;
	}

	/**
	 *
	 * 是否传输节点
	 *
	 */
	private java.lang.String isTransNode;
   
	public void setIsTransNode(java.lang.String isTransNode){
		this.isTransNode= isTransNode;       
	}
   
	public java.lang.String getIsTransNode(){
		return this.isTransNode;
	}

	/**
	 *
	 * 描述
	 *
	 */
	private java.lang.String description;
   
	public void setDescription(java.lang.String description){
		this.description= description;       
	}
   
	public java.lang.String getDescription(){
		return this.description;
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


	public boolean equals(Object o) {
		if( o instanceof PnrBaseSite ) {
			PnrBaseSite pnrBaseSite=(PnrBaseSite)o;
			if (this.id != null || this.id.equals(pnrBaseSite.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}