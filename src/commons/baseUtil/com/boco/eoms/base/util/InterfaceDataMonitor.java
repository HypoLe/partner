package com.boco.eoms.base.util;

import java.util.Iterator;
import java.util.Map;

import com.boco.eoms.commons.interfaceMonitoring.mgr.InterfaceMonitoringMgr;
import com.boco.eoms.commons.interfaceMonitoring.model.InterfaceMonitoring;
import com.boco.eoms.commons.interfaceMonitoring.util.InterfaceMonitoringTools;
import com.boco.eoms.commons.loging.BocoLog;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   May 2, 2012 9:29:21 AM
 */
public class InterfaceDataMonitor {
	/**
	 * 保存接口日志(以队列的方式)
	 * @param map	参数信息
	 * @param result 调用结果
	 * @param serviceName	服务名
	 * @param methodName	方法名
	 */
	public void saveMonitorBySeq(Map map,String result,String serviceName,String methodName){
		try{
			InterfaceMonitoringTools interfaceMonitoringTools =new InterfaceMonitoringTools();
			interfaceMonitoringTools.interfaceMonitoringLlog(map, result,serviceName,methodName);
		}catch(Exception e){
			BocoLog.info(this, e.getMessage());
			BocoLog.info(this, "保存接口日志失败:save interfaceMonitor faulter");
		}
	}

	/**
	 * 保存接口日志
	 */
	public void saveMonitor(Map map,String result,String serviceName,String methodName){
		InterfaceMonitoring interfaceMonitoring = new InterfaceMonitoring();
		String text="";
	    for(Iterator it = map.entrySet().iterator(); it.hasNext();){
	    	Object obj = it.next();
	    	if(!"".equals(StaticMethod.nullObject2String(obj))){
	    		if(obj.toString().length()>1000){
	    			text+=obj.toString().substring(0,30)+"...更多"+"&#13";
	    			
	    		}else{
	    			text+=obj.toString()+"&#13";	
	    		}
	    	}
            System.out.println(obj);
        } 
	    System.out.println(text);
	    interfaceMonitoring.setInterFaceType(serviceName);
	    interfaceMonitoring.setMethod(serviceName);
	    String serSupplier=StaticMethod.nullObject2String(map.get("serSupplier"));
	    interfaceMonitoring.setSerSupplier(serSupplier);
	    interfaceMonitoring.setSerCaller(StaticMethod.nullObject2String(map.get("serCaller")));
	    interfaceMonitoring.setSheetKey(StaticMethod.nullObject2String(map.get("sheetkey")));
	    interfaceMonitoring.setCallingSide(StaticMethod.nullObject2String(map.get("serCaller")));
	    interfaceMonitoring.setProvider(serSupplier);
//		    interfaceMonitoring.set
		if(serSupplier.equals("EOMS")){
			interfaceMonitoring.setCallDirection("Horizontal");	
		}
		else{
		interfaceMonitoring.setCallDirection("Longitudinal");
		}
		interfaceMonitoring.setSuccess(result);
		interfaceMonitoring.setExceptionLog(result);
		interfaceMonitoring.setInterFaceMethod("Web Service");
	    interfaceMonitoring.setCallingTime(StaticMethod.getLocalString());
	    interfaceMonitoring.setText(text);
	    
	    InterfaceMonitoringMgr mgr = (InterfaceMonitoringMgr) ApplicationContextHolder
				.getInstance().getBean("InterfaceMonitoringMgr");
	    mgr.saveInterfaceMonitoring(interfaceMonitoring);
	}
}
