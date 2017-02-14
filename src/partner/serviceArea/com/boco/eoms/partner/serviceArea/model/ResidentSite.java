package com.boco.eoms.partner.serviceArea.model;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.partner.excel.model.IParExcelDeal;

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
 * @author benweiwei
 * @version 1.0
 * 
 */
public class ResidentSite extends BaseObject implements IParExcelDeal{

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
	private String residentSiteName;
   
	/**
	 *
	 * 驻点编号
	 *
	 */
	private String residentSiteNo;

	/**
	 *
	 * 所在地市
	 *
	 */
	private String city;

	/**
	 *
	 * 所在县区
	 *
	 */
	private String country;
	
	/**
	 * 代维公司
	 */
	private String monitorCompany;
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
	private java.lang.String personNum;
	
	/**
	 *
	 * 车辆配置数
	 *
	 */
	private java.lang.String carNum;
	
	/**
	 *
	 * 固定电话
	 *
	 */
	private String telNum;
	
	/**
	 *
	 * 移动电话
	 *
	 */
	private String mobileTelNum;

	/**
	 *
	 * 负责人
	 *
	 */
	private String dutyMan;
	

	/**
	 *
	 * 新增时间
	 *
	 */
	private String createTime;
   
	/**
	 *
	 * 新增人
	 *
	 */
	private String createUser;
   
	/**
	 *
	 * 删除时间
	 *
	 */
	private String delTime;

	/**
	 *
	 * 删除人
	 *
	 */
	private String delUser;

	/**
	 *
	 * 是否删除
	 *
	 */
	private String isDel;
	

	public String getResidentSiteName() {
		return residentSiteName;
	}

	public void setResidentSiteName(String residentSiteName) {
		this.residentSiteName = residentSiteName;
	}

	public String getResidentSiteNo() {
		return residentSiteNo;
	}

	public void setResidentSiteNo(String residentSiteNo) {
		this.residentSiteNo = residentSiteNo;
	}


	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMonitorCompany() {
		return monitorCompany;
	}

	public void setMonitorCompany(String monitorCompany) {
		this.monitorCompany = monitorCompany;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
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


	public java.lang.String getPersonNum() {
		return personNum;
	}

	public void setPersonNum(java.lang.String personNum) {
		this.personNum = personNum;
	}

	public java.lang.String getCarNum() {
		return carNum;
	}

	public void setCarNum(java.lang.String carNum) {
		this.carNum = carNum;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getMobileTelNum() {
		return mobileTelNum;
	}

	public void setMobileTelNum(String mobileTelNum) {
		this.mobileTelNum = mobileTelNum;
	}

	public String getDutyMan() {
		return dutyMan;
	}

	public void setDutyMan(String dutyMan) {
		this.dutyMan = dutyMan;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getDelTime() {
		return delTime;
	}

	public void setDelTime(String delTime) {
		this.delTime = delTime;
	}

	public String getDelUser() {
		return delUser;
	}

	public void setDelUser(String delUser) {
		this.delUser = delUser;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public boolean equals(Object o) {
		if( o instanceof Site ) {
			Site site=(Site)o;
			if (this.id != null || this.id.equals(site.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}