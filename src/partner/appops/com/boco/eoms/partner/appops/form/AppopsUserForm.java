package com.boco.eoms.partner.appops.form;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

public class AppopsUserForm extends BaseForm implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	private Date workTime;
	/**工龄*/
	private int workNumber;
	/**入司时间*/
	private Date  companyTime;
	/**司龄*/
	private int  companyNumber;
	/**从事维护工作时间*/
	private Date appopsWorkTime;
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
	
	
	private FormFile importFile;

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
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
	public Date getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}
	public int getWorkNumber() {
		return workNumber;
	}
	public void setWorkNumber(int workNumber) {
		this.workNumber = workNumber;
	}
	public Date getCompanyTime() {
		return companyTime;
	}
	public void setCompanyTime(Date companyTime) {
		this.companyTime = companyTime;
	}
	public int getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(int companyNumber) {
		this.companyNumber = companyNumber;
	}
	public Date getAppopsWorkTime() {
		return appopsWorkTime;
	}
	public void setAppopsWorkTime(Date appopsWorkTime) {
		this.appopsWorkTime = appopsWorkTime;
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
	
	
	
	
}
