/**
 * DeliveryFeedbackSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.DeliveryFeedback;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.boco.eoms.partner.interfaces.bo.DeliveryRequestConvertBO;

public class DeliveryFeedbackSoapBindingImpl implements com.boco.eoms.partner.interfaces.services.DeliveryFeedback.DeliveryFeedbackPortType{
	private static Log log = LogFactory
	.getLog(DeliveryFeedbackSoapBindingImpl.class);
	public void deliveryFeedbackRequest(
    		javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID,
    		int endStatusCode, java.lang.String endStatusDescription, java.util.Calendar sendTime) 
    throws java.rmi.RemoteException {
//		request:唯一事件标识（EventID）系统标识（SystemID）发送时间（SendTime）任务完成状态码（endStatusCode）
		//任务完成状态描述（endStatusDescription）具体描述任务失败原因
		//response:唯一事件标识（EventID）
		//endStatusCode 1：表示分发任务成功完成 2：表示由于数据消费方内部故障导致分发任务失败 3：表示数据中心提供数据有误导致分发任务失败

    	log.info("收到专业网管发送的分发反馈");
		log.info("接口平台接收到专业网管传递的原始参数为：" + "[唯一事件标识:" + eventID + "] [系统标识:"
				+ systemID + "] [发送时间:" + sendTime.toString() + "] 任务完成状态码:"
				+ endStatusCode + "] "
				+ "] [任务完成状态描述:" + endStatusDescription);
		DeliveryRequestConvertBO bo = new DeliveryRequestConvertBO();
		try {
			eventID.value = "分发反馈结果成功003";
			log.info("分发反馈结果成功" + systemID);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
