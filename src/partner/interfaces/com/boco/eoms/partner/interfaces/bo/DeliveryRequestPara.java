/**
 * 
 */
package com.boco.eoms.partner.interfaces.bo;

/**
 * @author new
 *
 */
public class DeliveryRequestPara {
	java.lang.String eventID;
	java.lang.String systemID;
	java.util.Calendar sendTime; 
	java.lang.String filter;
	java.lang.String dataReadyRequestUri;
	 
	/**
	 * @return the eventID
	 * 
	 * 
	 */
	 
	public java.lang.String getEventID() {
		return eventID;
	}
	/**
	 * @param eventID the eventID to set
	 */
	public void setEventID(java.lang.String eventID) {
		this.eventID = eventID;
	}
	/**
	 * @return the systemID
	 */
	public java.lang.String getSystemID() {
		return systemID;
	}
	/**
	 * @param systemID the systemID to set
	 */
	public void setSystemID(java.lang.String systemID) {
		this.systemID = systemID;
	}
	/**
	 * @return the sendTime
	 */
	public java.util.Calendar getSendTime() {
		return sendTime;
	}
	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(java.util.Calendar sendTime) {
		this.sendTime = sendTime;
	}
	/**
	 * @return the filter
	 */
	public java.lang.String getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(java.lang.String filter) {
		this.filter = filter;
	}
	/**
	 * @return the dataReadyRequestUri
	 */
	public java.lang.String getDataReadyRequestUri() {
		return dataReadyRequestUri;
	}
	/**
	 * @param dataReadyRequestUri the dataReadyRequestUri to set
	 */
	public void setDataReadyRequestUri(java.lang.String dataReadyRequestUri) {
		this.dataReadyRequestUri = dataReadyRequestUri;
	}
	
	
}
