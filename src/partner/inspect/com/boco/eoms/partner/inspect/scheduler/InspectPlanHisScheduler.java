package com.boco.eoms.partner.inspect.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/** 
 * Description: 轮询将巡检计划、巡检资源、巡检项数据搬移到历史表
 * 重要：该轮询需要配置到所有轮询之后
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 2, 2012 9:13:51 AM 
 */
public class InspectPlanHisScheduler implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	}
	
	

}
