package com.boco.eoms.partner.dataSynch.scheduler.tower;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.partner.dataSynch.model.EventMapping;
import com.boco.eoms.partner.dataSynch.util.DataParserUtils;
 
 /**
 * 类说明：轮循任务-铁塔及天馈-铁塔
 * 创建人： zhangkeqi
 * 创建时间：2012-12-01 18:05:58
 */
public class IrmsTowerTowerScheduler implements Job {
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String eventKey = "PNR_IrmsTowerTower";
		DataParserUtils util = new DataParserUtils();
		EventMapping em = new EventMapping();
		String eventID = em.eventKey2EventIDMap.get(eventKey);
		util.deliveryRequestRequest(eventID);
	}
}