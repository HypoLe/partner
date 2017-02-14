package com.boco.eoms.partner.baseinfo.webapp.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:fϢ
 * </p>
 * <p>
 * Description:fϢ
 * </p>
 * <p>
 * Tue Feb 10 17:33:14 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() liujinlong
 * @moudle.getVersion() 3.5
 * 
 */
public class PartnerUserForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
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
	 * 人员姓名
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
	 * 厂商（部门）ID
	 *
	 */
	private java.lang.String deptId;
   
	public void setDeptId(java.lang.String deptId){
		this.deptId= deptId;       
	}
   
	public java.lang.String getDeptId(){
		return this.deptId;
	}

	/**
	 *
	 * 厂商名
	 *
	 */
	private java.lang.String deptName;
   
	public void setDeptName(java.lang.String deptName){
		this.deptName= deptName;       
	}
   
	public java.lang.String getDeptName(){
		return this.deptName;
	}
	/**
	 * 地市nodeId
	 */
	private String areaId;

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	/**
	 * 所属地市
	 */
	private String areaName ;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	/**
	 * 身份证号码
	 */
	private String personCardNo;

	public String getPersonCardNo() {
		return personCardNo;
	}

	public void setPersonCardNo(String personCardNo) {
		this.personCardNo = personCardNo;
	}
	/**
	 *
	 * 服务地市范围
	 *
	 */
	private java.lang.String areaNames;
   
	public void setAreaNames(java.lang.String areaNames){
		this.areaNames= areaNames;       
	}
   
	public java.lang.String getAreaNames(){
		return this.areaNames;
	}

	/**
	 *
	 * 用户ID
	 *
	 */
	private java.lang.String userId;
   
	public void setUserId(java.lang.String userId){
		this.userId= userId;       
	}
   
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *
	 * 性别
	 *
	 */
	private java.lang.String sex;
   
	public void setSex(java.lang.String sex){
		this.sex= sex;       
	}
   
	public java.lang.String getSex(){
		return this.sex;
	}

	/**
	 *
	 */
	private java.lang.String photo;
   
	public void setPhoto(java.lang.String photo){
		this.photo= photo;       
	}
   
	public java.lang.String getPhoto(){
		return this.photo;
	}

	/**
	 *
	 */
	private java.lang.String birthdey;
   
	public void setBirthdey(java.lang.String birthdey){
		this.birthdey= birthdey;       
	}
   
	public java.lang.String getBirthdey(){
		return this.birthdey;
	}

	/**
	 *
	 */
	private java.lang.String diploma;
   
	public void setDiploma(java.lang.String diploma){
		this.diploma= diploma;       
	}
   
	public java.lang.String getDiploma(){
		return this.diploma;
	}

	/**
	 *
	 * μӹʱ
	 *
	 */
	private java.lang.String workTime;
   
	public void setWorkTime(java.lang.String workTime){
		this.workTime= workTime;       
	}
   
	public java.lang.String getWorkTime(){
		return this.workTime;
	}

	/**
	 *
	 * μӱλʱ
	 *
	 */
	private java.lang.String deptWorkTime;
   
	public void setDeptWorkTime(java.lang.String deptWorkTime){
		this.deptWorkTime= deptWorkTime;       
	}
   
	public java.lang.String getDeptWorkTime(){
		return this.deptWorkTime;
	}

	/**
	 *
	 */
	private java.lang.String licenseType;
   
	public void setLicenseType(java.lang.String licenseType){
		this.licenseType= licenseType;       
	}
   
	public java.lang.String getLicenseType(){
		return this.licenseType;
	}

	/**
	 *
	 */
	private java.lang.String licenseNo;
   
	public void setLicenseNo(java.lang.String licenseNo){
		this.licenseNo= licenseNo;       
	}
   
	public java.lang.String getLicenseNo(){
		return this.licenseNo;
	}

	/**
	 *岗位
	 */
	private java.lang.String post;
   
	public void setPost(java.lang.String post){
		this.post= post;       
	}
   
	public java.lang.String getPost(){
		return this.post;
	}

	/**
	 *在岗状态
	 */
	private java.lang.String postState;
   
	public void setPostState(java.lang.String postState){
		this.postState= postState;       
	}
   
	public java.lang.String getPostState(){
		return this.postState;
	}
	/**
	 * 经度
	 */
	private String longtitude;
	/**
	 * 纬度
	 */
	private String latitude;	
	
	
	/**
	 *联系手机
	 */
	private java.lang.String phone;
   
	public void setPhone(java.lang.String phone){
		this.phone= phone;       
	}
   
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *emil
	 */
	private java.lang.String email;
   
	public void setEmail(java.lang.String email){
		this.email= email;       
	}
   
	public java.lang.String getEmail(){
		return this.email;
	}
	/**
	 * 对应树节点nodeId
	 */
	private String treeNodeId;
	
	public String getTreeNodeId() {
		return treeNodeId;
	}

	public void setTreeNodeId(String treeNodeId) {
		this.treeNodeId = treeNodeId;
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
	 *
	 * 附件存在文件夹下的名字
	 *
	 */
	private java.lang.String accessory;
 
	public void setAccessory(java.lang.String accessory){
		this.accessory= accessory;       
	}
 
	public java.lang.String getAccessory(){
		return this.accessory;
	}
	/**
	 * 附件上传
	 */
    private FormFile accessoryName; //批量录入文件
    
	public FormFile getAccessoryName() {
		return accessoryName;
	}
	public void setAccessoryName(FormFile accessoryName) {
		this.accessoryName = accessoryName;
	}


	/**
	 * 二维码
	 * 2009-9-21
	 * bww
	 */
	private String dimensionalCode;

	/**
	 * 上岗证级别
	 * 2009-9-21
	 * bww
	 */
	private String workLicenseLevel;
	/**
	 * 上岗证专业
	 * 2009-9-21
	 * bww
	 */
	private String workLicenseMajor;
	/**
	 * 项目名称
	 * 2009-9-21
	 * bww
	 */
	private String projectName;
	/**
	 * 代维程度
	 * 2009-9-21
	 * bww
	 */
	private String maintainLevel;
	/**
	 * 项目属性
	 * 2009-9-21
	 * bww
	 */
	private String projectProperty;
	/**
	 * 所属驻点
	 * 2009-9-21
	 * bww
	 */
	private String station;
	/**
	 * 维护专业
	 * 2009-9-21
	 * bww
	 */
	private String serviceProfessional;
	/**
	 * 职责
	 * 2009-9-21
	 * bww
	 */
	private String responsibility;
	/**
	 * 是否骨干员工
	 * 2009-9-21
	 * bww
	 */
	private String isbackbone;
	/**
	 * 备注
	 * 2009-9-21
	 * bww
	 */
	private String remark;
	/**
	 * 移动电话
	 * 2009-9-21
	 * bww
	 */
	private String mobilePhone;

	/**
	 * 所属县区
	 * 2009-9-21
	 * bww
	 */
	private String city;
	public String getDimensionalCode() {
		return dimensionalCode;
	}

	public void setDimensionalCode(String dimensionalCode) {
		this.dimensionalCode = dimensionalCode;
	}

	public String getWorkLicenseLevel() {
		return workLicenseLevel;
	}

	public void setWorkLicenseLevel(String workLicenseLevel) {
		this.workLicenseLevel = workLicenseLevel;
	}

	public String getWorkLicenseMajor() {
		return workLicenseMajor;
	}

	public void setWorkLicenseMajor(String workLicenseMajor) {
		this.workLicenseMajor = workLicenseMajor;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getMaintainLevel() {
		return maintainLevel;
	}

	public void setMaintainLevel(String maintainLevel) {
		this.maintainLevel = maintainLevel;
	}

	public String getProjectProperty() {
		return projectProperty;
	}

	public void setProjectProperty(String projectProperty) {
		this.projectProperty = projectProperty;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getServiceProfessional() {
		return serviceProfessional;
	}

	public void setServiceProfessional(String serviceProfessional) {
		this.serviceProfessional = serviceProfessional;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getIsbackbone() {
		return isbackbone;
	}

	public void setIsbackbone(String isbackbone) {
		this.isbackbone = isbackbone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	private String partnerid;

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	
	private FormFile importFile;

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
	/**
	 * 是否为管理员ismanager
	 * 2011-11-1
	 * @author 胡浩
	 * 是管理员为1  ，否则为0
	 */
	private String ismanager;

	public String getIsmanager() {
		return ismanager;
	}

	public void setIsmanager(String ismanager) {
		this.ismanager = ismanager;
	}
	/**
	 * 密码userPassword
	 * 2011-12-1
	 * @author heminxi
	 * 默认为123456
	 */
	private String userPassword;

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	private String userNo;//员工编码
	private String nativePlace;//籍贯
	private String nationality;//民族
	private String age;//年龄
	private String groupPhone;//集团短号
	private String graduateSchool;//毕业院校
	private String learnSpecialty;//所学专业
	private String ifWor;//离职原因
	private String blacklist;//黑名单标识    是、否   系统默认为否
	private String identificationAccessory;//身份证扫描件
	/*为了在后续统计功能中统计到某月的信息而添加的值 add by 丰光平 begin*/
	private String saveTime;//保存时间，人员加入系统的时间
	private long saveTimeLongValue;//保存时间的值；
	private String saveTimeYear;//保存时间的年份值
	private String saveTimeMonth;//保存时间的月份值
	private String updateTime;//更新时间，人员发生更新时的时间
	private String leavaTime;//离开单位时间；
	private long leavaTimeLongValue;//离开时间值
	private String leavaTimeYear;//离开单位时间的年值
	private String leavaTimeMonth;//离开单位时间的月份值;
	private String leavaReason;//离开单位原因；
	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public long getSaveTimeLongValue() {
		return saveTimeLongValue;
	}

	public void setSaveTimeLongValue(long saveTimeLongValue) {
		this.saveTimeLongValue = saveTimeLongValue;
	}

	public String getSaveTimeYear() {
		return saveTimeYear;
	}

	public void setSaveTimeYear(String saveTimeYear) {
		this.saveTimeYear = saveTimeYear;
	}

	public String getSaveTimeMonth() {
		return saveTimeMonth;
	}

	public void setSaveTimeMonth(String saveTimeMonth) {
		this.saveTimeMonth = saveTimeMonth;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getLeavaTime() {
		return leavaTime;
	}

	public void setLeavaTime(String leavaTime) {
		this.leavaTime = leavaTime;
	}

	public long getLeavaTimeLongValue() {
		return leavaTimeLongValue;
	}

	public void setLeavaTimeLongValue(long leavaTimeLongValue) {
		this.leavaTimeLongValue = leavaTimeLongValue;
	}

	public String getLeavaTimeYear() {
		return leavaTimeYear;
	}

	public void setLeavaTimeYear(String leavaTimeYear) {
		this.leavaTimeYear = leavaTimeYear;
	}

	public String getLeavaTimeMonth() {
		return leavaTimeMonth;
	}

	public void setLeavaTimeMonth(String leavaTimeMonth) {
		this.leavaTimeMonth = leavaTimeMonth;
	}

	public String getLeavaReason() {
		return leavaReason;
	}

	public void setLeavaReason(String leavaReason) {
		this.leavaReason = leavaReason;
	}

	/*为了在后续统计功能中统计到某月的信息而添加的值 add by 丰光平 end*/
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGroupPhone() {
		return groupPhone;
	}

	public void setGroupPhone(String groupPhone) {
		this.groupPhone = groupPhone;
	}

	public String getGraduateSchool() {
		return graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public String getLearnSpecialty() {
		return learnSpecialty;
	}

	public void setLearnSpecialty(String learnSpecialty) {
		this.learnSpecialty = learnSpecialty;
	}

	public String getIfWor() {
		return ifWor;
	}

	public void setIfWor(String ifWor) {
		this.ifWor = ifWor;
	}

	public String getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	public String getIdentificationAccessory() {
		return identificationAccessory;
	}

	public void setIdentificationAccessory(String identificationAccessory) {
		this.identificationAccessory = identificationAccessory;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}