/**
 * 
 */
package com.boco.eoms.partner.interfaces.bo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boco.eoms.partner.interfaces.services.LoadingFeedback.LoadingFeedbackLocator;
import com.boco.eoms.partner.interfaces.services.LoadingFeedback.LoadingFeedbackPortType;

/**
 * @author mooker
 * 
 */
public class PartnerWebserviceBO {
	private static Log log = LogFactory.getLog(PartnerWebserviceBO.class);

	LoadingFeedbackPortType port = null;

	public PartnerWebserviceBO() {
	}

	public LoadingFeedbackPortType getPort(String url) {
		LoadingFeedbackLocator service = new LoadingFeedbackLocator();
		try {
			port = service.getLoadingFeedbackSOAPport_http(new URL(url));
		} catch (MalformedURLException e) {
			log.error("java.net.MalformedURLException: " + e);
			return null;
		} catch (ServiceException e) {
			log.error("javax.xml.rpc.ServiceException: " + e);
			return null;
		}
		return port;
	}

	public void feedback(String eventid, int endstatuscode,
			String endstatusdescription, String url) {
		String result = "";
		try {
			StringHolder eventID = new StringHolder();
			eventID.value = eventid;
			int endStatusCode = endstatuscode;
			String endStatusDescription = endstatusdescription;
			Calendar sendTime = new GregorianCalendar();
			log.info("准备调用专业网管装载反馈接口，原始值为：[唯一事件标识：" + eventid + "][发送时间："
					+ sendTime.getTime().toString() + "][任务完成状态码："
					+ endstatuscode + "][任务完成状态描述：" + endstatusdescription + "]");
			//kyx 修改
			//SOAP
			this.getPort(url);
			result = port.loadingFeedbackRequest(eventID, endStatusCode, endStatusDescription, sendTime);
			//锐易特 RES
//			LoadingFeedbackProxy.doLoadingFeedback(url,eventID, sendTime, endStatusCode, endStatusDescription);
			log.info("专业网管处理装载反馈返回值为：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
