/**
 * 
 */
package com.boco.eoms.partner.interfaces.bo;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boco.eoms.partner.interfaces.dataprovider.proxy.DeliveryReadyProxy;
import com.boco.eoms.partner.interfaces.services.DeliveryReady.DeliveryReadyLocator;
import com.boco.eoms.partner.interfaces.services.DeliveryReady.DeliveryReadyPortType;

/**
 * @author kyx
 * 
 */
public class DeliveryReadyWebserviceBO{
	private static Log log = LogFactory.getLog(DeliveryReadyWebserviceBO.class);
	private static DeliveryReadyWebserviceBO Instance = new DeliveryReadyWebserviceBO();
	DeliveryReadyPortType port = null;
	
	public static DeliveryReadyWebserviceBO getInstance() {
    	return Instance;
    }
	 public DeliveryReadyPortType getPort(String url) {
	    	DeliveryReadyLocator service = new DeliveryReadyLocator();
	    	try {
				port = service.getDeliveryReadySOAPport_http(new URL(url));
			} catch (MalformedURLException e) {
				log.error("java.net.MalformedURLException: " + e);
				return null;
			} catch (ServiceException e) {
				log.error("javax.xml.rpc.ServiceException: " + e);
				return null;
			}
			return port;
	    }
	
	public String deliveryReady(
    		StringHolder eventID,Calendar sendTime, int readyStatusCode,
    		String readyStatusDescription, int workMode, int fileFormat, int charSet,
    		int lineSeparator, int fieldSeparator, String fieldNameList,String xmlSchema, 
    		String dataInfo,String connectionString,String path, boolean isCompressed,
    		String dataReadyRequestUri,String systemID
    		) {
    	String result = null;
		try {
			log.info("获取专业网管提供的服务");
			log.info("deliveryReadyRequest 调用URL为：" + dataReadyRequestUri);
			//soap
			this.getPort(dataReadyRequestUri);
			result = port.deliveryReadyRequest(eventID, sendTime, readyStatusCode, readyStatusDescription, workMode, fileFormat, charSet, lineSeparator, fieldSeparator, fieldNameList, xmlSchema, dataInfo, connectionString, path, isCompressed);
			//res esb
//			result =  DeliveryReadyProxy.doDeliveryReady(dataReadyRequestUri,eventID, sendTime, charSet, 
//					                                     workMode, fileFormat, connectionString, dataInfo, 
//					                                     readyStatusDescription, fieldNameList, fieldSeparator, 
//					                                     isCompressed, lineSeparator, path, readyStatusCode, 
//					                                     xmlSchema,systemID);
//					
			log.info("专业网管处理分发就绪返回值为：" + result);
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
}
