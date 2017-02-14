package com.boco.eoms.partner.dataSynch.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.partner.dataSynch.model.EventMapping;
import com.boco.eoms.partner.dataSynch.util.DataParserUtils;

public class IrmsWlanRepeaterScheduler implements Job {
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String eventKey = "PNR_IrmsWlanRepeater";
		DataParserUtils util = new DataParserUtils();
		EventMapping em = new EventMapping();
		String eventID = em.eventKey2EventIDMap.get(eventKey);
		util.deliveryRequestRequest(eventID);
	}
}