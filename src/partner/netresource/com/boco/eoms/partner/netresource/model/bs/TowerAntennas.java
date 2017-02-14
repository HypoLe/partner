package com.boco.eoms.partner.netresource.model.bs;

import java.io.Serializable;
import java.util.Date;


/**
 * 类说明：天塔及天馈--TowerAntennas
 * 创建人： chenbing
 * 创建时间：2013-11-19 
 */
public class TowerAntennas implements Serializable{

	/**主键*/
	private String id;
	/**基站名称（常用名称）*/
	private String anotherTaName;
	/**资源管理系统中的基站名称*/
	private String taName;
	/**所属地市ID*/
	private String city;
	/**所属区域ID*/
	private String country;
	/**巡检专业*/
	private String specialty;
	/**资源类别*/
	private String resType;
	/**资源级别*/
	private String resLevel;
	/**产权*/
	private String equity;
	/**铁塔类型*/
	private String towerType;
	/**天馈线类型*/
	private String antennaType;
	/**铁塔高度（米）*/
	private String towerHeight;
	/**经度*/
	private double  longitude;
	/**纬度*/
	private double  latitude;
	/**塔上RRU数量（套）*/
	private double  rruNumber;
	/**塔上直放站数量（套）*/
	private double  repeaterNumber;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAnotherTaName() {
		return anotherTaName;
	}


	public void setAnotherTaName(String anotherTaName) {
		this.anotherTaName = anotherTaName;
	}


	public String getTaName() {
		return taName;
	}


	public void setTaName(String taName) {
		this.taName = taName;
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


	public String getEquity() {
		return equity;
	}


	public void setEquity(String equity) {
		this.equity = equity;
	}


	public String getTowerType() {
		return towerType;
	}


	public void setTowerType(String towerType) {
		this.towerType = towerType;
	}


	public String getAntennaType() {
		return antennaType;
	}


	public void setAntennaType(String antennaType) {
		this.antennaType = antennaType;
	}


	public String getTowerHeight() {
		return towerHeight;
	}


	public void setTowerHeight(String towerHeight) {
		this.towerHeight = towerHeight;
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


	public double getRruNumber() {
		return rruNumber;
	}


	public void setRruNumber(double rruNumber) {
		this.rruNumber = rruNumber;
	}


	public double getRepeaterNumber() {
		return repeaterNumber;
	}


	public void setRepeaterNumber(double repeaterNumber) {
		this.repeaterNumber = repeaterNumber;
	}


	public TowerAntennas(String id, String taName) {
		this.id = id;
		this.taName = taName;
	}


	public TowerAntennas() {
		
	}
	
	
	
	
}
