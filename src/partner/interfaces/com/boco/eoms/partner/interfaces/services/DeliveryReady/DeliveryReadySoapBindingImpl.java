/**
 * DeliveryReadySoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.DeliveryReady;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.boco.eoms.partner.interfaces.services.LoadingFeedback.LoadingFeedbackSoapBindingImpl;

public class DeliveryReadySoapBindingImpl implements com.boco.eoms.partner.interfaces.services.DeliveryReady.DeliveryReadyPortType{
	private static Log log = LogFactory.getLog(LoadingFeedbackSoapBindingImpl.class);
	public java.lang.String deliveryReadyRequest(javax.xml.rpc.holders.StringHolder eventID, 
			java.util.Calendar sendTime, int readyStatusCode, java.lang.String readyStatusDescription, 
			int workMode, int fileFormat, int charSet, int lineSeparator, 
			int fieldSeparator, java.lang.String fieldNameList, java.lang.String xmlSchema, 
			java.lang.String dataInfo, java.lang.String connectionString, 
			java.lang.String path, boolean isCompressed) throws java.rmi.RemoteException {
//    	log.info("path:" + path);
//    	log.info("workmode:" + workMode);
//    	log.info("dataInfo:" + dataInfo);
    	/*[:ftp:ftp://tnms1:tnms123@10.209.2.14:21:]
    	  [:path:/export/home/tnms1/irms200/irmsrtu/tnms-run/irmsfile\IRMS20090519082405718.CHK:]
    	  [:workmode:1:]
    	  [:dataInfo:当前文件格式为固定XML格式:]*/
    	String ftphost = connectionString.substring(connectionString.indexOf("@")+1,connectionString.lastIndexOf(":")); 
		String ftpport = connectionString.substring(connectionString.lastIndexOf(":")+1,connectionString.length()); 
		String tempName = connectionString.substring(connectionString.indexOf("//")+2,connectionString.indexOf("@")); 
		String ftpusername = tempName.substring(0,tempName.indexOf(":"));
		String ftppassword=tempName.substring(tempName.indexOf(":")+1,tempName.length()); 
		String ftpdir = path;
		log.info("(:(:(:(:(:测试WebService调用，打印相关信息，看到此信息即为调用成功:):):):):)" + connectionString);
    	return "success";
    }
}
