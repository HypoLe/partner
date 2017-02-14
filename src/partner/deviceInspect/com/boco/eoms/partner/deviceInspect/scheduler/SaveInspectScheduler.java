package com.boco.eoms.partner.deviceInspect.scheduler;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.partner.deviceInspect.dao.IDeviceInspectDao;
import com.boco.eoms.partner.deviceInspect.service.IDeviceInspectService;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMgr;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.google.common.primitives.Ints;

/** 
 * Description: 巡检计划保存上月未完成的任务
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     chenbing
 * @version:    1.0 
 * Create at:   2014-09-21 21:25
 */
public class SaveInspectScheduler implements Job{
	/**
	 * 添加：
	 * 
	 * 1、 巡检结束时间与巡检开始时间差---1个月；

       2、 未完成，或完成时间在；
       
       情况分析：
          bug点：
          1、 执行的时候，是计划已经移到下一个计划中；
       
       ******最好在巡检轮询开始之前处理。*******

       删除：

       1、id,plan_id 重复的只是留一个。
           
	 */		
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LocalDate now = new LocalDate();
	//首先先判断表中是否存在该数据；
	//不存在插入
		
	}
	
}
