package com.boco.eoms.partner.serviceArea.webapp.form;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.commons.system.dict.service.ID2NameService;

/**
 * <p>
 * Title:驻点信息管理
 * </p>
 * <p>
 * Description:驻点信息管理
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @moudle.getVersion() 1.0
 * 
 */
public class ResidentSiteForm extends BaseForm implements java.io.Serializable {

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

	/**
	 *
	 * 驻点名称
	 *
	 */
	private java.lang.String residentSiteName;
   
	/**
	 *
	 * 驻点编号
	 *
	 */
	private java.lang.String residentSiteNo;

	/**
	 *
	 * 所在地市
	 *
	 */
	private java.lang.String city;

	/**
	 *
	 * 所在县区
	 *
	 */
	private java.lang.String country;
	
	/**
	 * 代维公司
	 */
	private java.lang.String monitorCompany;
	/**
	 * 所属合作伙伴的id（非节点）
	 */
	private String partnerId;
	
	/**
	 * 所属大合作伙伴的id（非节点）
	 */
	private String bigPartnerId;
	
	/**
	 * 驻点地址
	 */
	private String address;
	
	/**
	 *
	 * 人员配置数
	 *
	 */
	private java.lang.Integer personNum;
	
	/**
	 *
	 * 车辆配置数
	 *
	 */
	private java.lang.Integer carNum;
	
	/**
	 *
	 * 固定电话
	 *
	 */
	private java.lang.String telNum;
	
	/**
	 *
	 * 移动电话
	 *
	 */
	private java.lang.String mobileTelNum;

	/**
	 *
	 * 负责人
	 *
	 */
	private java.lang.String dutyMan;
	

	/**
	 *
	 * 负责人名称
	 *
	 */
	private java.lang.String dutyManName;

	/**
	 *
	 * 新增时间
	 *
	 */
	private java.lang.String addTime;
   
	/**
	 *
	 * 新增人
	 *
	 */
	private java.lang.String addUser;
   
	/**
	 *
	 * 修改时间
	 *
	 */
	private java.lang.String updateTime;
   
	/**
	 *
	 * 修改人
	 *
	 */
	private java.lang.String updateUser;
   
	/**
	 *
	 * 删除时间
	 *
	 */
	private java.lang.String delTime;

	/**
	 *
	 * 删除人
	 *
	 */
	private java.lang.String delUser;

	/**
	 *
	 * 是否删除
	 *
	 */
	private java.lang.String isDel;
	

	public java.lang.String getResidentSiteName() {
		return residentSiteName;
	}

	public void setResidentSiteName(java.lang.String residentSiteName) {
		this.residentSiteName = residentSiteName;
	}

	public java.lang.String getResidentSiteNo() {
		return residentSiteNo;
	}

	public void setResidentSiteNo(java.lang.String residentSiteNo) {
		this.residentSiteNo = residentSiteNo;
	}

	public java.lang.String getCountry() {
		return country;
	}

	public void setCountry(java.lang.String country) {
		this.country = country;
	}

	public java.lang.String getMonitorCompany() {
		return monitorCompany;
	}

	public void setMonitorCompany(java.lang.String monitorCompany) {
		this.monitorCompany = monitorCompany;
	}

	public java.lang.String getCity() {
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getBigPartnerId() {
		return bigPartnerId;
	}

	public void setBigPartnerId(String bigPartnerId) {
		this.bigPartnerId = bigPartnerId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public java.lang.Integer getPersonNum() {
		return personNum;
	}

	public void setPersonNum(java.lang.Integer personNum) {
		this.personNum = personNum;
	}

	public java.lang.Integer getCarNum() {
		return carNum;
	}

	public void setCarNum(java.lang.Integer carNum) {
		this.carNum = carNum;
	}

	public java.lang.String getTelNum() {
		return telNum;
	}

	public void setTelNum(java.lang.String telNum) {
		this.telNum = telNum;
	}

	public java.lang.String getMobileTelNum() {
		return mobileTelNum;
	}

	public void setMobileTelNum(java.lang.String mobileTelNum) {
		this.mobileTelNum = mobileTelNum;
	}

	public java.lang.String getDutyMan() {
		return dutyMan;
	}

	public void setDutyMan(java.lang.String dutyMan) {
		this.dutyMan = dutyMan;
	}

	public java.lang.String getAddTime() {
		return addTime;
	}

	public void setAddTime(java.lang.String addTime) {
		this.addTime = addTime;
	}

	public java.lang.String getAddUser() {
		return addUser;
	}

	public void setAddUser(java.lang.String addUser) {
		this.addUser = addUser;
	}

	public java.lang.String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.lang.String updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(java.lang.String updateUser) {
		this.updateUser = updateUser;
	}

	public java.lang.String getDelTime() {
		return delTime;
	}

	public void setDelTime(java.lang.String delTime) {
		this.delTime = delTime;
	}

	public java.lang.String getDelUser() {
		return delUser;
	}

	public void setDelUser(java.lang.String delUser) {
		this.delUser = delUser;
	}

	public java.lang.String getIsDel() {
		return isDel;
	}

	public void setIsDel(java.lang.String isDel) {
		this.isDel = isDel;
	}

	public java.lang.String getDutyManName() {
		ID2NameService id2NameMgr = (ID2NameService)ApplicationContextHolder
					.getInstance().getBean("id2nameService");
		return id2NameMgr.id2Name(dutyMan, "tawSystemUserDao");
	}

	public void setDutyManName(java.lang.String dutyManName) {
		this.dutyManName = dutyManName;
	}
	

}