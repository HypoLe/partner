/**
 * LoadingFeedbackSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingFeedback;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoadingFeedbackSoapBindingImpl implements com.boco.eoms.partner.interfaces.services.LoadingFeedback.LoadingFeedbackPortType{
	private static Log log = LogFactory.getLog(LoadingFeedbackSoapBindingImpl.class);
    public java.lang.String loadingFeedbackRequest(javax.xml.rpc.holders.StringHolder eventID, int endStatusCode, java.lang.String endStatusDescription, java.util.Calendar sendTime) throws java.rmi.RemoteException {
    	log.info(":):):):):)测试WebService调用，打印相关信息，看到此信息即为调用成功:):):):):)");
        return "专业网管处理装载反馈成功";
    }

}
