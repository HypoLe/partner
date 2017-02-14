/**
 * 
 */
package com.boco.eoms.partner.interfaces.bo;

/**
 * @author new
 *
 */
public class LoadRequestParas {

	private String systemID;
	private String eventID;
	private int workMode;
	String dataInfo;
	String feedbackUri;
	int loadingFlag;
	
	
	/**
	 * @return the systemID
	 */
	public String getSystemID() {
		return systemID;
	}

	/**
	 * @param systemID the systemID to set
	 */
	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	/**
	 * @return the dataInfo
	 */
	public String getDataInfo() {
		return dataInfo;
	}

	/**
	 * @param dataInfo the dataInfo to set
	 */
	public void setDataInfo(String dataInfo) {
		this.dataInfo = dataInfo;
	}

	/**
	 * @return the feedbackUri
	 */
	public String getFeedbackUri() {
		return feedbackUri;
	}

	/**
	 * @param feedbackUri the feedbackUri to set
	 */
	public void setFeedbackUri(String feedbackUri) {
		this.feedbackUri = feedbackUri;
	}

	/**
	 * @return the loadingFlag
	 */
	public int getLoadingFlag() {
		return loadingFlag;
	}

	/**
	 * @param loadingFlag the loadingFlag to set
	 */
	public void setLoadingFlag(int loadingFlag) {
		this.loadingFlag = loadingFlag;
	}

	/**
	 * @return the eventID
	 */
	public String getEventID() {
		return eventID;
	}

	/**
	 * @param eventID the eventID to set
	 */
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	/**
	 * @return the workMode
	 */
	public int getWorkMode() {
		return workMode;
	}

	/**
	 * @param workMode the workMode to set
	 */
	public void setWorkMode(int workMode) {
		this.workMode = workMode;
	}
	
	
	
}
