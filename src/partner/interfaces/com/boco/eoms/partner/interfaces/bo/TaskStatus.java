/**
 * 
 */
package com.boco.eoms.partner.interfaces.bo;

/**
 * @author new
 * 
 */
public class TaskStatus {

	public final static int completed = 1;// "表示装载任务成功完成";
	public final static int irmserror = 2;// "表示由于数据中心内部故障导致装载任务失败";
	public final static int sourceDataError = 3;// "表示数据提供方提供数据有误导致装载任务失败";
	public final static int noCompleted = 4;// "转载任务未完成";
	public final static int taskNoExist = 5;// "转载任务不存在";

	private String systemID = "";
	private String eventID = "";
	private int statusCode = 4;
	private String statusDescription = "";

	/**
	 * @return the systemID
	 */
	public String getSystemID() {
		return systemID;
	}

	/**
	 * @param systemID
	 *            the systemID to set
	 */
	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	/**
	 * @return the eventID
	 */
	public String getEventID() {
		return eventID;
	}

	/**
	 * @param eventID
	 *            the eventID to set
	 */
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the statusDescription
	 */
	public String getStatusDescription() {
		return statusDescription;
	}

	/**
	 * @param statusDescription
	 *            the statusDescription to set
	 */
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
}
