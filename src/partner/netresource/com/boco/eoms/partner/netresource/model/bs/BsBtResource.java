package com.boco.eoms.partner.netresource.model.bs;

import java.io.Serializable;
import java.util.Date;


/**
 * 类说明：基站机房--BTSSITE
 * 创建人： chenbing
 * 创建时间：2013-06-28 
 */
public class BsBtResource implements Serializable{

	/**主键*/
	private String id;
	/**基站机房名称*/
	private String bsbtName;
	/**
	 * 所属地市ID
	 */
	private String city;
	/**所属区域ID*/
	private String country;
	/**巡检专业*/
	private String specialty;
	/**资源类别*/
	private String resType;
	/**资源级别*/
	private String resLevel;
	/**所在局所*/
	private String bureau;
	/**二期机房名*/
	private String twoBsbtName;
	/**机房别名*/
	private String anotherBsbtName;
	/**机房类型*/
	private String bsbtType;
	/**机房级别*/
	private String bsbtLevel;
	/**机房位置*/
	private String bsbtAddress;
	/**所在楼层*/
	private String foor;
	/**序号*/
	private String no;
	/**资产归属*/
	private String assetsAttributable;
	/**联系人*/
	private String contact;
	/**联系电话*/
	private String contactTel;
	/**所属97局所*/
	private String is97bureau;
	/**用户名称*/
	private String userName;
	/**备注*/
	private String remark;
	/**录入人*/
	private String entryPeople;
	/**录入日期*/
	private Date	entryTime;
	/**修改人*/
	private String modifyPeople;
	/**修改日期*/
	private Date   modifyTime;
	/**长*/
	private double  length;
	/**宽*/
	private double  width;
	/**高*/
	private double  height;
	/**机房分类*/
	private String bsbtClassify;
	/**经度*/
	private double  longitude;
	/**纬度*/
	private double  latitude;
	/**集团可用面积*/
	private double  usableArea;
	/**集团已用面积*/
	private double  usedArea;
	/**集团机房归属*/
	private String  bsbtVested;
	/**集团是否共享*/
	private String  isShare;
	/**集团共享运营商*/
	private String  shareOperators;
	/**机房大类*/
	private String  bsbtCategories;
	/**机房小类*/
	private String  bsbtSmallClass;
	/**集团机房类型*/
	private String  groupBsbtTypes;
	/**集团机房承重*/
	private double  groupRoombear;
	/**集团走线方式*/
	private String  alignmentMethod;
	/**集团单双走线架*/
	private String  chutes;
	/**集团消防系统*/
	private String  fireSystem;
	/**集团有无地板*/
	private String  isNoun;
	/**集团有无监控*/
	private String  isMonitor;
	/**集团租用到期时间*/
	private Date    maturityTime;
	/**集团房屋类型*/
	private String  roomType;
	/**集团维护方式*/
	private String  maintenanceMode;
	/**集团维护单位*/
	private String  maintenanceUnit;
	/**集团包机人*/
	private String  charter;
	/**集团第三方维护单位*/
	private String  threeMainUnit;
	/**集团续保截至日期*/
	private Date    renewalTime;
	/**集团维保类型*/
	private String  maintenanceType;
	/**集团已购买维保累计年限*/
	private String  maintenanceYear;
	/**集团室外放置点*/
	private String  outDoor;	
	/**标准地址*/
	private String  place;
	
	
	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getSpecialty() {
		return specialty;
	}


	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getResType() {
		return resType;
	}


	public void setResType(String resType) {
		this.resType = resType;
	}


	public String getResLevel() {
		return resLevel;
	}


	public void setResLevel(String resLevel) {
		this.resLevel = resLevel;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getBsbtName() {
		return bsbtName;
	}


	public void setBsbtName(String bsbtName) {
		this.bsbtName = bsbtName;
	}
	

	public String getBureau() {
		return bureau;
	}


	public void setBureau(String bureau) {
		this.bureau = bureau;
	}


	public String getTwoBsbtName() {
		return twoBsbtName;
	}


	public void setTwoBsbtName(String twoBsbtName) {
		this.twoBsbtName = twoBsbtName;
	}


	public String getAnotherBsbtName() {
		return anotherBsbtName;
	}


	public void setAnotherBsbtName(String anotherBsbtName) {
		this.anotherBsbtName = anotherBsbtName;
	}


	public String getBsbtType() {
		return bsbtType;
	}


	public void setBsbtType(String bsbtType) {
		this.bsbtType = bsbtType;
	}


	public String getBsbtLevel() {
		return bsbtLevel;
	}


	public void setBsbtLevel(String bsbtLevel) {
		this.bsbtLevel = bsbtLevel;
	}


	public String getBsbtAddress() {
		return bsbtAddress;
	}


	public void setBsbtAddress(String bsbtAddress) {
		this.bsbtAddress = bsbtAddress;
	}


	public String getFoor() {
		return foor;
	}


	public void setFoor(String foor) {
		this.foor = foor;
	}


	public String getNo() {
		return no;
	}


	public void setNo(String no) {
		this.no = no;
	}


	public String getAssetsAttributable() {
		return assetsAttributable;
	}


	public void setAssetsAttributable(String assetsAttributable) {
		this.assetsAttributable = assetsAttributable;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public String getContactTel() {
		return contactTel;
	}


	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}


	public String getIs97bureau() {
		return is97bureau;
	}


	public void setIs97bureau(String is97bureau) {
		this.is97bureau = is97bureau;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getEntryPeople() {
		return entryPeople;
	}


	public void setEntryPeople(String entryPeople) {
		this.entryPeople = entryPeople;
	}


	public Date getEntryTime() {
		return entryTime;
	}


	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}


	public String getModifyPeople() {
		return modifyPeople;
	}


	public void setModifyPeople(String modifyPeople) {
		this.modifyPeople = modifyPeople;
	}


	public Date getModifyTime() {
		return modifyTime;
	}


	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	public double getLength() {
		return length;
	}


	public void setLength(double length) {
		this.length = length;
	}


	public double getWidth() {
		return width;
	}


	public void setWidth(double width) {
		this.width = width;
	}


	public double getHeight() {
		return height;
	}


	public void setHeight(double height) {
		this.height = height;
	}


	public String getBsbtClassify() {
		return bsbtClassify;
	}


	public void setBsbtClassify(String bsbtClassify) {
		this.bsbtClassify = bsbtClassify;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getUsableArea() {
		return usableArea;
	}


	public void setUsableArea(double usableArea) {
		this.usableArea = usableArea;
	}


	public double getUsedArea() {
		return usedArea;
	}


	public void setUsedArea(double usedArea) {
		this.usedArea = usedArea;
	}


	public String getBsbtVested() {
		return bsbtVested;
	}


	public void setBsbtVested(String bsbtVested) {
		this.bsbtVested = bsbtVested;
	}


	public String getIsShare() {
		return isShare;
	}


	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}


	public String getShareOperators() {
		return shareOperators;
	}


	public void setShareOperators(String shareOperators) {
		this.shareOperators = shareOperators;
	}


	public String getBsbtCategories() {
		return bsbtCategories;
	}


	public void setBsbtCategories(String bsbtCategories) {
		this.bsbtCategories = bsbtCategories;
	}


	public String getBsbtSmallClass() {
		return bsbtSmallClass;
	}


	public void setBsbtSmallClass(String bsbtSmallClass) {
		this.bsbtSmallClass = bsbtSmallClass;
	}


	public String getGroupBsbtTypes() {
		return groupBsbtTypes;
	}


	public void setGroupBsbtTypes(String groupBsbtTypes) {
		this.groupBsbtTypes = groupBsbtTypes;
	}


	public double getGroupRoombear() {
		return groupRoombear;
	}


	public void setGroupRoombear(double groupRoombear) {
		this.groupRoombear = groupRoombear;
	}


	public String getAlignmentMethod() {
		return alignmentMethod;
	}


	public void setAlignmentMethod(String alignmentMethod) {
		this.alignmentMethod = alignmentMethod;
	}


	public String getChutes() {
		return chutes;
	}


	public void setChutes(String chutes) {
		this.chutes = chutes;
	}


	public String getFireSystem() {
		return fireSystem;
	}


	public void setFireSystem(String fireSystem) {
		this.fireSystem = fireSystem;
	}


	public String getIsNoun() {
		return isNoun;
	}


	public void setIsNoun(String isNoun) {
		this.isNoun = isNoun;
	}


	public String getIsMonitor() {
		return isMonitor;
	}


	public void setIsMonitor(String isMonitor) {
		this.isMonitor = isMonitor;
	}


	public Date getMaturityTime() {
		return maturityTime;
	}


	public void setMaturityTime(Date maturityTime) {
		this.maturityTime = maturityTime;
	}


	public String getRoomType() {
		return roomType;
	}


	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}


	public String getMaintenanceMode() {
		return maintenanceMode;
	}


	public void setMaintenanceMode(String maintenanceMode) {
		this.maintenanceMode = maintenanceMode;
	}


	public String getMaintenanceUnit() {
		return maintenanceUnit;
	}


	public void setMaintenanceUnit(String maintenanceUnit) {
		this.maintenanceUnit = maintenanceUnit;
	}


	public String getCharter() {
		return charter;
	}


	public void setCharter(String charter) {
		this.charter = charter;
	}


	public String getThreeMainUnit() {
		return threeMainUnit;
	}


	public void setThreeMainUnit(String threeMainUnit) {
		this.threeMainUnit = threeMainUnit;
	}


	public Date getRenewalTime() {
		return renewalTime;
	}


	public void setRenewalTime(Date renewalTime) {
		this.renewalTime = renewalTime;
	}


	public String getMaintenanceType() {
		return maintenanceType;
	}


	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}


	public String getMaintenanceYear() {
		return maintenanceYear;
	}


	public void setMaintenanceYear(String maintenanceYear) {
		this.maintenanceYear = maintenanceYear;
	}


	public String getOutDoor() {
		return outDoor;
	}


	public void setOutDoor(String outDoor) {
		this.outDoor = outDoor;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public BsBtResource() {
		
	}
	
	
	
	
}
