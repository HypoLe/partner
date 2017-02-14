package com.boco.eoms.partner.inspect.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanGisMgr;


/**
 * 巡检GIS信息采集轮询
 *
 */
public class InspectPlanGisScheduler implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		saveInspectPlanGis();
		saveInspectPlanGisPnrDept();
	}
	
	/**
	 * 按地市进行统计任务的完成情况
	 */
	public void saveInspectPlanGis() {
		try {
			IInspectPlanGisMgr inspectPlanGisMgr = (IInspectPlanGisMgr) ApplicationContextHolder
					.getInstance().getBean("inspectPlanGisMgr");
			inspectPlanGisMgr.saveInspectPlanGis();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("轮询按地市进行统计任务的完成情况失败");
		}
		
	}
	
	/**
	 * 按代维公司进行统计任务的完成情况
	 */
	public void saveInspectPlanGisPnrDept(){
		try {
			IInspectPlanGisMgr inspectPlanGisMgr = (IInspectPlanGisMgr) ApplicationContextHolder
				.getInstance().getBean("inspectPlanGisMgr");
			inspectPlanGisMgr.saveInspectPlanGisPnrDept();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("轮询按代维公司进行统计任务的完成情况失败");
		}
	} 

}
