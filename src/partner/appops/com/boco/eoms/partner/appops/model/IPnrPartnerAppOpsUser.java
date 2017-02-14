package com.boco.eoms.partner.appops.model;

import java.util.Date;

/**
 * 
  * @author wangyue
  * @ClassName: IPnrPartnerAppOpsUser
  * @Copyright 亿阳信通
  * @date Sep 24, 2014 4:02:51 PM
  * @description 运维人员实体类
 */
public class IPnrPartnerAppOpsUser {
	private String id;
	/**分公司*/
	private String company;
	/**员工编号*/
	private String userNumber;
	/**姓名*/
	private String userName;
	/**性别*/
	private String userSex;
	/**出生年份*/
	private String userBorth;
	/**年龄*/
	private int userAge;
	/**电话*/
	private String telephone;
	/**手机*/
	private String phone;
	/**邮箱*/
	private String email;
	/**参加工作时间*/
	private String workTime;
	/**工龄*/
	private int workNumber;
	/**入司时间*/
	private String  companyTime;
	/**司龄*/
	private int  companyNumber;
	/**从事维护工作时间*/
	private String appopsWorkTime;
	/**从事维护时限*/
	private int appopsNumber;
	/**最高学历*/
	private String highestDegree;
	/**最高学位毕业院校*/
	private String school;
	/**第一学位*/
	private String firstDegree;
	/**第一学位专业*/
	private String firstSpecialty;
	/**第二学位*/
	private String secondDegree;
	/**第二学位专业*/
	private String secondSpecialty;
	/**其它证书*/
	private String certificate;
	/**员工类别*/
	private String  workSort;
	/**职称*/
	private String jobTitle;
	/**用工类别*/
	private String useSort;
	/**岗位职级*/
	private String jobLevel;
	/**管理层级*/
	private String managerLevel;
	/**所在部门*/
	private String dept;
	/**所在班组*/
	private String team;
	/**专业类别*/
	private String specialty;
	/**工作岗位--专职*/
	private String operatingPostZ;
	/**工作岗位--兼职*/
	private String operatingPostJ;
	/**工作描述*/
	private String workDescribe;
	/**删除标志*/
	private String isDelete;
	/**地区ID--根据所选的班组*/
	private String areaId;
	/**工作岗位--兼职-字符串*/
	private String operatingPostJName;
	/**所在部门名称*/
	private String deptName;
	/**所在班组名称*/
	private String teamName;
	/**组织编码*(所在部门编码)---新增*/
	private String deptCode;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserBorth() {
		return userBorth;
	}
	public void setUserBorth(String userBorth) {
		this.userBorth = userBorth;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getWorkNumber() {
		return workNumber;
	}
	public void setWorkNumber(int workNumber) {
		this.workNumber = workNumber;
	}
	
	public int getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(int companyNumber) {
		this.companyNumber = companyNumber;
	}
	
	public int getAppopsNumber() {
		return appopsNumber;
	}
	public void setAppopsNumber(int appopsNumber) {
		this.appopsNumber = appopsNumber;
	}
	public String getHighestDegree() {
		return highestDegree;
	}
	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getFirstDegree() {
		return firstDegree;
	}
	public void setFirstDegree(String firstDegree) {
		this.firstDegree = firstDegree;
	}
	public String getFirstSpecialty() {
		return firstSpecialty;
	}
	public void setFirstSpecialty(String firstSpecialty) {
		this.firstSpecialty = firstSpecialty;
	}
	public String getSecondDegree() {
		return secondDegree;
	}
	public void setSecondDegree(String secondDegree) {
		this.secondDegree = secondDegree;
	}
	public String getSecondSpecialty() {
		return secondSpecialty;
	}
	public void setSecondSpecialty(String secondSpecialty) {
		this.secondSpecialty = secondSpecialty;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getWorkSort() {
		return workSort;
	}
	public void setWorkSort(String workSort) {
		this.workSort = workSort;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getUseSort() {
		return useSort;
	}
	public void setUseSort(String useSort) {
		this.useSort = useSort;
	}
	public String getJobLevel() {
		return jobLevel;
	}
	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	public String getManagerLevel() {
		return managerLevel;
	}
	public void setManagerLevel(String managerLevel) {
		this.managerLevel = managerLevel;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getOperatingPostZ() {
		return operatingPostZ;
	}
	public void setOperatingPostZ(String operatingPostZ) {
		this.operatingPostZ = operatingPostZ;
	}
	public String getOperatingPostJ() {
		return operatingPostJ;
	}
	public void setOperatingPostJ(String operatingPostJ) {
		this.operatingPostJ = operatingPostJ;
	}
	public String getWorkDescribe() {
		return workDescribe;
	}
	public void setWorkDescribe(String workDescribe) {
		this.workDescribe = workDescribe;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getCompanyTime() {
		return companyTime;
	}
	public void setCompanyTime(String companyTime) {
		this.companyTime = companyTime;
	}
	public String getAppopsWorkTime() {
		return appopsWorkTime;
	}
	public void setAppopsWorkTime(String appopsWorkTime) {
		this.appopsWorkTime = appopsWorkTime;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getOperatingPostJName() {
		return operatingPostJName;
	}
	public void setOperatingPostJName(String operatingPostJName) {
		this.operatingPostJName = operatingPostJName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
		
}
