/**
 * DeliveryRequestSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.DeliveryRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.boco.eoms.partner.interfaces.bo.DeliveryRequestPara;
import com.boco.eoms.partner.interfaces.bo.DeliveryRequestProcessThread;

public class DeliveryRequestSoapBindingImpl implements
		com.boco.eoms.partner.interfaces.services.DeliveryRequest.DeliveryRequestPortType {
	private static Log log = LogFactory
			.getLog(DeliveryRequestSoapBindingImpl.class);

	public void deliveryRequestRequest(
			javax.xml.rpc.holders.StringHolder eventID,
			java.lang.String systemID, java.util.Calendar sendTime,
			java.lang.String filter, java.lang.String dataReadyRequestUri)
			throws java.rmi.RemoteException {
//		 request:唯一事件标识（EventID）系统标识（SystemID）发送时间（SendTime）
		// 数据消费方服务端URI（dataReadyRequestUri） 用以指定所取数据的范围(filter)
		// response:唯一事件标识（EventID）
//		log.info("收到专业网管发送的分发请求");
		log.info("接口平台接收到专业网管分发请求传递的原始参数为：" + "[唯一事件标识:" + eventID.value + "] [系统标识:"
				+ systemID + "] [发送时间:" + sendTime.toString() + "] [反馈URI:"
				+ dataReadyRequestUri + "] " + "] [用以指定所取数据的范围:" + filter);
		DeliveryRequestProcessThread DeliveryRequestProcess = new DeliveryRequestProcessThread();
		DeliveryRequestPara para = new DeliveryRequestPara();
		para.setDataReadyRequestUri(dataReadyRequestUri);
		para.setEventID(eventID.value);
		para.setFilter(filter);
		para.setSendTime(sendTime);
		para.setSystemID(systemID);
		DeliveryRequestProcess.setPara(para);

		Thread thread = new Thread(DeliveryRequestProcess);
		thread.start();
		log.info("DeliveryRequestProcessThread start");
	}
}