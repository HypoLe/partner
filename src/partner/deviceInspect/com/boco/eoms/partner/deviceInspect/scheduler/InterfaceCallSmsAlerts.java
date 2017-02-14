package com.boco.eoms.partner.deviceInspect.scheduler;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.activiti.partner.process.dao.IPnrTransferNewPrecheckJDBCDao;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.partner.deviceInspect.dao.IDeviceInspectDao;
import com.boco.eoms.partner.deviceInspect.service.IDeviceInspectService;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMgr;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.google.common.primitives.Ints;

/** 
 * Description: 自省公司运维部分管总经理批通过开始超过40天，工单还处于接口调用状态，则给工单发起人发短信提醒.
 * Copyright:   Copyright (c)2015
 * Company:     BOCO 
 * @author:     chujingang
 * @version:    1.0 
 * Create at:   Jul 8, 2015 3:06:23 PM 
 */
public class InterfaceCallSmsAlerts implements Job{
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("进入InterfaceCallSmsAlerts处理类了....");
		IPnrTransferNewPrecheckJDBCDao dao=(IPnrTransferNewPrecheckJDBCDao)ApplicationContextHolder.getInstance().getBean("pnrTransferNewPrecheckDao");
		List<TaskModel> l=dao.InterfaceCallSmsAlertsList();
		for(int i=0;i<l.size();i++){
			//发短信
			String messagePerson = l.get(i).getInitiator();//取出派单人账号
			String msg = "工单号：" + "  " + l.get(i).getProcessInstanceId()
					+ "," + "工单类型：" + l.get(i).getWorkorderTypeName() + "," + "工单主题："
					+ l.get(i).getTheme() + "," + "省公司审批通过已40天，请尽快提运维商城流程" + "。";
			System.out.println(msg);
			CommonUtils.sendMsg(msg, messagePerson);
		}	
		
	}

	
	
	
}
