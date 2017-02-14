package com.boco.eoms.partner.inspect.scheduler;

import org.joda.time.LocalDate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMgr;

/**
 * 巡检统计轮询类
 * @author Administrator
 *
 */
public class InspectPlanMainScheduler implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.updateInspectPlanResDoneNum();
		this.saveInspectTemplateHis();
	}
	
	/**
	 * 更新巡检计划的计划完成数
	 */
	public void updateInspectPlanResDoneNum(){
		IInspectPlanMgr inspectPlanMgr = (IInspectPlanMgr)ApplicationContextHolder
				.getInstance().getBean("inspectPlanMgr");
		inspectPlanMgr.updateInspectPlanResDoneNum();
	}
	
	public void saveInspectTemplateHis(){
		IInspectPlanMgr inspectPlanMgr = (IInspectPlanMgr)ApplicationContextHolder
		.getInstance().getBean("inspectPlanMgr");
		LocalDate date = new LocalDate();
		String month = date.getMonthOfYear()+"";
		String year = date.getYear()+"";
		int day = date.getDayOfMonth();
		try {
			if(day==1){  //每月的第一天执行
				inspectPlanMgr.saveInspectTemplateHis(year, month);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(year+"年"+month+"月的巡检模板历史记录生成失败");
		}
	}

}
