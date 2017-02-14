/**
 * 接口日志管理类
 */
package com.boco.eoms.sheet.interfaceBase.util;

import java.util.Map;

//import com.boco.eoms.commons.interfaceMonitoring.util.InterfaceMonitoringTools;
import com.boco.eoms.commons.loging.BocoLog;

public class InterfaceDataMonitor {
	/**
	 * 保存接口日志
	 * @param map	参数信息
	 * @param result 调用结果
	 * @param serviceName	服务名
	 * @param methodName	方法名
	 */
	public void saveMonitor(Map map,String result,String serviceName,String methodName){
		try{
			//InterfaceMonitoringTools interfaceMonitoringTools =new InterfaceMonitoringTools();
			//interfaceMonitoringTools.interfaceMonitoringLlog(map, result,serviceName,methodName);
		}catch(Exception e){
			BocoLog.info(this, e.getMessage());
			BocoLog.info(this, "保存接口日志失败:save interfaceMonitor faulter");
		}
	}
}
