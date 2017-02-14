/**
 * LoadingQuerySoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boco.eoms.partner.interfaces.bo.TaskStatus;
import com.boco.eoms.partner.interfaces.dao.PartnerConvertDAO;
import com.boco.eoms.partner.interfaces.bo.LoadingQueryBO;

public class LoadingQuerySoapBindingImpl implements
        com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQueryPortType {
    private static Log log = LogFactory
                             .getLog(LoadingQuerySoapBindingImpl.class);

    public void loadingQueryRequest(javax.xml.rpc.holders.StringHolder eventID,
            java.lang.String systemID, java.util.Calendar sendTime,
            javax.xml.rpc.holders.IntHolder statusCode,
            javax.xml.rpc.holders.StringHolder statusDescription) throws java.rmi.RemoteException {
		// request:唯一事件标识（EventID）系统标识（SystemID）发送时间（SendTime）
		// response:唯一事件标识（EventID）任务完成状态码（StatusCode）任务完成状态描述（StatusDescription）
		log.info("收到专业网管发送的装载查询");
        log.info("接口平台接收到专业网管传递的原始参数为：" + "[唯一事件标识:" + eventID + "] [系统标识:"
                 + systemID + "] [发送时间:" + sendTime.toString());
        PartnerConvertDAO dao = new PartnerConvertDAO(); 
        TaskStatus taskStatus = dao.getTaskStatus(systemID+"_"+eventID);
        log.info("任务状态:"+taskStatus.getStatusCode()+",描述:"+taskStatus.getStatusDescription());
        statusCode.value = taskStatus.getStatusCode();
        statusDescription.value = taskStatus.getStatusDescription();
		try {
			LoadingQueryBO bo = (LoadingQueryBO)dao.selectTask(eventID.value);
			if(bo != null){
				eventID.value =bo.getEventid();
				statusCode.value = new Integer(bo.getStatuscode().toString());
		        statusDescription.value = bo.getStatusdescription();
			}else{
				statusCode.value = 4;
		        statusDescription.value = "数据比较中，转载任务未完成；如果长时间获取不到结果，证明装载任务不成功，请重新装载。";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
