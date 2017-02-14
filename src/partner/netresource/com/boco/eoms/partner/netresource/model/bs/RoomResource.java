package com.boco.eoms.partner.netresource.model.bs;

import java.io.Serializable;

/**
 * 室分资源
 * @author chenbing
 *
 */
public class RoomResource implements Serializable{
	/**主键*/
	private String id;
	/**室分编号*/
	private String reNumber;
	/**室分名称*/
	private String reName;
	/**地市*/
	private String city;
	/**地区*/
	private String country;
	/**巡检专业*/
	private String specialty;
	/**资源类别*/
	private String resType;
	/**资源级别*/
	private String resLevel;
	/**室分地址*/
	private String  address;
	/**经度*/
	private double  longitude;
	/**纬度*/
	private double  latitude;
	/**2G信源情况*/
	private String  source2g;
	/**LAC*/
	private String  lac;
	/**CI*/
	private String  ci;	
	/**BCCH*/
	private String  bcch;
	/**3G信源情况*/
	private String  source3g;
	/**PSC*/
	private String  psc;
	/**干放安装位置*/
	private String  location;
	/**2G干放型号*/
	private String  model2g;
	/**2G干放数量*/
	private String  number2g;
	/**3G干放型号*/
	private String  model3g;
	/**3G干放数量*/
	private String  number3g;
	/**业主联系人*/
	private String  contact;
	/**联系电话*/
	private String  contactTel;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getReNumber() {
		return reNumber;
	}
	public void setReNumber(String reNumber) {
		this.reNumber = reNumber;
	}
	public String getReName() {
		return reName;
	}
	public void setReName(String reName) {
		this.reName = reName;
	}
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getSource2g() {
		return source2g;
	}
	public void setSource2g(String source2g) {
		this.source2g = source2g;
	}
	
	public String getLac() {
		return lac;
	}
	public void setLac(String lac) {
		this.lac = lac;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getBcch() {
		return bcch;
	}
	public void setBcch(String bcch) {
		this.bcch = bcch;
	}
	public String getSource3g() {
		return source3g;
	}
	public void setSource3g(String source3g) {
		this.source3g = source3g;
	}
	public String getPsc() {
		return psc;
	}
	public void setPsc(String psc) {
		this.psc = psc;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getModel2g() {
		return model2g;
	}
	public void setModel2g(String model2g) {
		this.model2g = model2g;
	}
	public String getNumber2g() {
		return number2g;
	}
	public void setNumber2g(String number2g) {
		this.number2g = number2g;
	}
	public String getModel3g() {
		return model3g;
	}
	public void setModel3g(String model3g) {
		this.model3g = model3g;
	}
	public String getNumber3g() {
		return number3g;
	}
	public void setNumber3g(String number3g) {
		this.number3g = number3g;
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
	public RoomResource() {
		
	}
	
	
	
}
