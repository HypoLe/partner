package com.boco.eoms.partner.deviceInspect.scheduler;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;

/**
 * 
 	* @author WangJun
 	* @ClassName: ResourceInventoryDataScheduler
 	* @Version 1.0
 	* @ModifiedBy WangJun
 	* @Copyright 亿阳
 	* @date Nov 27, 2015 9:49:09 AM
 	* @description 用来从资源数据库采集资源清查数据，插入到现场维护系统的资源资源管理表及备份表
 */
public class ResourceInventoryDataScheduler implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
		Map<String,String> param =new HashMap<String,String>();
		param.put("userId", "admin");//定时任务时创建人默认为admin
		param.put("limitedNumber", "10000");//限制条数，默认10000条
		param.put("cityIds","1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17");//地市ids，默认17个地市
		param.put("cityNames", "济南,青岛,烟台,潍坊,淄博,威海,济宁,泰安,德州,临沂,枣庄,菏泽,聊城,滨州,东营,日照,莱芜");//地市names，默认17个地市
		Map<String, Object> resultMap = pnrResConfiMgr.collectResourceInventoryData(param);
		int total = (Integer)resultMap.get("total");
		System.out.println("从资源库采集资源清查数据完毕！共入库成功"+total+"条数据！");
	}
	
}
