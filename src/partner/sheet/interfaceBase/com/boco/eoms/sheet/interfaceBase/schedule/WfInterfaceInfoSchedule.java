package com.boco.eoms.sheet.interfaceBase.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.sheet.interfaceBase.bo.WfInterfaceInfoBo;

public class WfInterfaceInfoSchedule implements Job {

	private WfInterfaceInfoBo wfBo = WfInterfaceInfoBo.getInstance();

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		wfBo.sendInfo();
	}

	public static void main(String args[]) {
		WfInterfaceInfoBo.getInstance().sendInfo();
	}

}
