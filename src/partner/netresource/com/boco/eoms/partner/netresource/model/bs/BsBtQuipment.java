package com.boco.eoms.partner.netresource.model.bs;

import java.io.Serializable;
import java.util.Date;

/**
 * 基站机房设备
 * @author chenbing
 *

 */
public class BsBtQuipment implements Serializable {
	/**主键*/
	private String id;
	/**基站机房资源ID*/
	private String bsbtId;
	/**基站机房资源名称*/
	private String bsbtName;
	/**设备编号*/
	private String  deviceNumber;
	/**生产商*/
	private String manufacturers;
	/**设备类型*/
	private String  deviceType;
	/**容量*/
	private String  capacity;
	/**所属网络*/
	private String   network;
	/**设备子类*/
	private String  deviceSubclass;
	/**资产归属*/
	private String  assetsAttributable;
	/**资产编号*/
	private String  assetsNumber;
	/**使用日期*/
	private Date    useTime;
	/**软件版本*/
	private String  soft;
	/**巡检资源的名字*/
	private String inspectName;
	/**巡检资源的ID*/
	private String inspectId;

	public BsBtQuipment(String id, String bsbtId, String deviceNumber,
			String network, String deviceSubclass) {
		this.id = id;
		this.bsbtId = bsbtId;
		this.deviceNumber = deviceNumber;
		this.network = network;
		this.deviceSubclass = deviceSubclass;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	
	public String getBsbtId() {
		return bsbtId;
	}
	public void setBsbtId(String bsbtId) {
		this.bsbtId = bsbtId;
	}
	public String getBsbtName() {
		return bsbtName;
	}
	public void setBsbtName(String bsbtName) {
		this.bsbtName = bsbtName;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	public String getManufacturers() {
		return manufacturers;
	}
	public void setManufacturers(String manufacturers) {
		this.manufacturers = manufacturers;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getDeviceSubclass() {
		return deviceSubclass;
	}
	public void setDeviceSubclass(String deviceSubclass) {
		this.deviceSubclass = deviceSubclass;
	}
	public String getAssetsAttributable() {
		return assetsAttributable;
	}
	public void setAssetsAttributable(String assetsAttributable) {
		this.assetsAttributable = assetsAttributable;
	}
	public String getAssetsNumber() {
		return assetsNumber;
	}
	public void setAssetsNumber(String assetsNumber) {
		this.assetsNumber = assetsNumber;
	}
	public Date getUseTime() {
		return useTime;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
	public String getSoft() {
		return soft;
	}
	public void setSoft(String soft) {
		this.soft = soft;
	}
	
	public String getInspectName() {
		return inspectName;
	}
	public void setInspectName(String inspectName) {
		this.inspectName = inspectName;
	}
	public String getInspectId() {
		return inspectId;
	}
	public void setInspectId(String inspectId) {
		this.inspectId = inspectId;
	}
	
	/**
	 * 
	 */
	public BsBtQuipment() {
	
	}
	
	

}
