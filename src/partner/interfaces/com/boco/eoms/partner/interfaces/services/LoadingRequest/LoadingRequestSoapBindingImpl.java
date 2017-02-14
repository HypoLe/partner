/**
 * LoadingRequestSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boco.eoms.partner.interfaces.bo.LoadRequestParas;
import com.boco.eoms.partner.interfaces.bo.LoadRequestProcessThread;



public class LoadingRequestSoapBindingImpl implements
		com.boco.eoms.partner.interfaces.services.LoadingRequest.LoadingRequestPortType {
	private static Log log = LogFactory.getLog(LoadingRequestSoapBindingImpl.class);

	public void loadingRequestRequest(
			javax.xml.rpc.holders.StringHolder eventID,
			java.lang.String systemID, java.util.Calendar sendTime,
			java.lang.String feedbackUri, int loadingFlag, int workMode,
			int fileFormat, int charSet, java.lang.String lineSeparator,
			java.lang.String fieldSeparator, java.lang.String fieldNameList,
			java.lang.String xmlSchema, java.lang.String dataInfo)
			throws java.rmi.RemoteException {
//		 request:唯一事件标识（EventID）系统标识（SystemID）发送时间（SendTime）反馈URI（FeedbackUri）增量/全量标志（LoadingFlag）
		// 			工作方式（WorkMode）文件格式（FileFormat）建议字符编码集（CharSet）行分割符（LineSeparator）
		// 			字段分割符（FieldSeparator）字段名列表（FieldNameList）XmlSchema（XmlSchema）数据信息（DataInfo）
		// response:唯一事件标识（EventID）
		log.info("收到专业网管发送的装载请求");
		log.error("接口平台接收到专业网管传递的原始参数为：" + "[唯一事件标识:" + eventID.value
				+ "] [系统标识:" + systemID + "] [发送时间:"
				+ sendTime.getTime().toString() + "] [反馈URI:" + feedbackUri
				+ "] [增量/全量标志:" + loadingFlag + "] [工作方式:" + workMode
				+ "] [文件格式:" + fileFormat + "] [建议字符编码集:" + charSet
				+ "] [行分割符:" + lineSeparator + "] [字段分割符:" + fieldSeparator
				+ "] [字段名列表:" + fieldNameList + "] [XmlSchema:" + xmlSchema
				+ "] [数据信息（长度）:" + dataInfo.length());
		
		LoadRequestParas loadRequestParas = new LoadRequestParas();
		loadRequestParas.setSystemID(systemID);
		loadRequestParas.setEventID(eventID.value);
		loadRequestParas.setWorkMode(workMode);
		loadRequestParas.setDataInfo(dataInfo);
		loadRequestParas.setFeedbackUri(feedbackUri);
		loadRequestParas.setLoadingFlag(loadingFlag);
		
		LoadRequestProcessThread loadThread = new LoadRequestProcessThread();
		loadThread.setLoadRequestParas(loadRequestParas);
		Thread thread = new Thread(loadThread);
		thread.setName("loadRequestProcessThread-"+eventID.value);
		log.info("请求处理开始，线程名："+thread.getName());
		thread.start();
	}

}
