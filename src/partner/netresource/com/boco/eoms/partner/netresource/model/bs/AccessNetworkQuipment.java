package com.boco.eoms.partner.netresource.model.bs;

import java.io.Serializable;
import java.util.Date;

/**
 * 接入网机房设备
 * @author chenbing
 *

 */
public class AccessNetworkQuipment implements Serializable {
	/**主键*/
	private String id;
	/**所属网络*/
	private String   network;
	/**设备类别*/
	private String  deviceType;
	/**设备名称*/
	private String  anqName;
	/**所属接入网机房名称*/
	private String  anrName;
	/**接入网机房ID*/
	private String anrId;
	/**设备编号*/
	private String  deviceNumber;

	/**巡检资源的名字*/
	private String inspectName;
	/**巡检资源的ID*/
	private String inspectId;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	

	public String getAnrName() {
		return anrName;
	}
	public void setAnrName(String anrName) {
		this.anrName = anrName;
	}
	public String getAnrId() {
		return anrId;
	}
	public void setAnrId(String anrId) {
		this.anrId = anrId;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
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
	
	public String getAnqName() {
		return anqName;
	}
	public void setAnqName(String anqName) {
		this.anqName = anqName;
	}
	
	/**
	 * 
	 */
	public AccessNetworkQuipment() {
	
	}
	public AccessNetworkQuipment(String id, String network, String deviceType,
			String anrId, String deviceNumber) {
		this.id = id;
		this.network = network;
		this.deviceType = deviceType;
		this.anrId = anrId;
		this.deviceNumber = deviceNumber;
	}
	

}
