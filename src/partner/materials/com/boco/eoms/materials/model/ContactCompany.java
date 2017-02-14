package com.boco.eoms.materials.model;

/**
 * 往来单位model类
 * 
 * @author wanghongliang
 * 
 */
public class ContactCompany {

	private String id;
	/**
	 * 公司名称
	 */
	private String name;
	/**
	 * 公司编码
	 */
	private String encode;
	/**
	 * 单位分类
	 */
	private String category;
	/**
	 * 类别名称
	 */
	private String categoryName;
	/**
	 * 联系人
	 */
	private String contactPerson;
	/**
	 * 联系地址
	 */
	private String address;
	/**
	 * 手机号码
	 */
	private Integer phoneNumber;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 简介
	 */
	private String intro;
	/**
	 * 办公电话
	 */
	private String officePhone;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

}
