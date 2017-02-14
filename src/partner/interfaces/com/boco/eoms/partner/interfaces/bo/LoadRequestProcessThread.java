/**
 * 
 */
package com.boco.eoms.partner.interfaces.bo;
/**
 * @author new
 *
 */
public class LoadRequestProcessThread implements Runnable {
	
	private LoadRequestParas loadRequestParas;

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		String systemID = this.getLoadRequestParas().getSystemID();
		String eventid = this.getLoadRequestParas().getEventID();
		int workMode = this.getLoadRequestParas().getWorkMode();
		String feedbackUri = this.getLoadRequestParas().getFeedbackUri();
		String dataInfo = this.getLoadRequestParas().getDataInfo();
		int loadingFlag = this.getLoadRequestParas().getLoadingFlag();
		
		PartnerConvertBO bo = new PartnerConvertBO();
		bo.initBO();
		if (workMode == 1) {
			// 基于文件的批量数据装载
			bo.ConvertXML(systemID,eventid, feedbackUri, loadingFlag);
		} else if (workMode == 0) {
			// 基于消息的批量数据装载
			bo.ConvertMES(systemID,eventid, dataInfo, feedbackUri, loadingFlag);
		}
	}

	/**
	 * @return the loadRequestParas
	 */
	public LoadRequestParas getLoadRequestParas() {
		return loadRequestParas;
	}

	/**
	 * @param loadRequestParas the loadRequestParas to set
	 */
	public void setLoadRequestParas(LoadRequestParas loadRequestParas) {
		this.loadRequestParas = loadRequestParas;
	}
}
