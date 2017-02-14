package com.boco.eoms.partner.dataSynch.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.boco.eoms.base.util.InterfaceDataMonitor;

/** 
 * Description: 数据申请接口(属地化运维<客户端>调用综合资源<服务端>)
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 25, 2012 11:33:38 AM
 */
public class DeliveryRequestRequestService {
	
	/**
	 * 申请数据
	 * @param eventID  唯一事件标识
	 * @param systemID 系统标识
	 * @param deliveryTime 发送时间
	 * @param filter 取数过滤条件串
	 * @param dataReadyRequestUri 数据就绪请求URI,如：http://10.210.17.65:8080/eoms/services/deliveryReadyRequestService?wsdl
	 * @throws Exception
	 */
	public void deliveryRequestRequest(String eventID,String systemID,String deliveryTime,
					String filter,String dataReadyRequestUri) {
		//创建接口日志
		InterfaceDataMonitor monitor = new InterfaceDataMonitor();
		String result = "0";
		Map<String,Object> monitorMap = new HashMap<String,Object> ();
		monitorMap.put("eventID", eventID);
		monitorMap.put("systemID", systemID);
		monitorMap.put("deliveryTime", deliveryTime);
		monitorMap.put("filter", filter);
		monitorMap.put("dataReadyRequestUri", dataReadyRequestUri);
		monitorMap.put("sheetkey", eventID);
		
		try{
			Calendar sendTime = new GregorianCalendar();
			org.apache.ws.axis2.services.DeliveryRequest.DeliveryRequestSOAPport_httpSoapBindingStub binding;
		    binding = (org.apache.ws.axis2.services.DeliveryRequest.DeliveryRequestSOAPport_httpSoapBindingStub)
		    new org.apache.ws.axis2.services.DeliveryRequest.DeliveryRequestLocator().getDeliveryRequestSOAPport_http();
		    binding.deliveryRequestRequest(new javax.xml.rpc.holders.StringHolder(eventID), 
		        	systemID, sendTime, filter, dataReadyRequestUri);
		}catch(Exception err){
			err.printStackTrace();
			result =err.getMessage();
		}finally{
			monitor.saveMonitorBySeq(monitorMap, result,  "网络资源数据同步", "deliveryRequestRequest");
		}
	}
}
