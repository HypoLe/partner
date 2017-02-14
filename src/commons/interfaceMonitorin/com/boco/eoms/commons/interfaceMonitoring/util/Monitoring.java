package com.boco.eoms.commons.interfaceMonitoring.util;

import org.apache.axis.AxisFault;
import org.apache.axis.Handler;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.SOAPPart;
import org.apache.axis.handlers.BasicHandler;

import com.boco.eoms.commons.interfaceMonitoring.model.InterfaceMonitoring;

public class Monitoring extends BasicHandler {

	public void invoke(MessageContext msgContext) throws AxisFault {
		// TODO Auto-generated method stub
		try {
			
	
			Handler serviceHandler = msgContext.getService();
			
			 System.out.println("msgContext.getTransportName():"+msgContext.getTransportName());
			Message message = msgContext.getRequestMessage();
			String soap;
			System.out.println("messageContext.getPastPivot():"+msgContext.getPastPivot());
			if(msgContext.getPastPivot()){
				message = msgContext.getRequestMessage();
				 soap =((SOAPPart)message.getSOAPPart()).getAsString();
				 System.out.println("soap:"+soap);
				message = msgContext.getResponseMessage();
				 soap =message.getSOAPPartAsString();
				 System.out.println("Requestsoap:"+soap); 
				 
			}
		
			InterfaceMonitoring interfaceMonitoring =new InterfaceMonitoring();
			
			interfaceMonitoring.setInterFaceType(msgContext.getTransportName());
			interfaceMonitoring.setInterFaceMethod(msgContext.getTransportName());
//			interfaceMonitoring.setText(soap);
			interfaceMonitoring.setSuccess("ok");
			//InterfaceMonitoringDaoHibernate interfaceMonitoringDaoHibernate=new InterfaceMonitoringDaoHibernate();
			
//			System.out.println(result);
		} catch (Exception e) {
		e.printStackTrace();
		}
	}

}
