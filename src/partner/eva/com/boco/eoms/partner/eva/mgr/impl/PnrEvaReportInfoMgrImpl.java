package com.boco.eoms.partner.eva.mgr.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaReportYearStaticFrom;
import com.boco.eoms.partner.eva.dao.IPnrEvaReportInfoDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaReportInfoMgr;
import com.boco.eoms.partner.eva.model.PnrEvaReportInfo;

public class PnrEvaReportInfoMgrImpl implements IPnrEvaReportInfoMgr {



	private IPnrEvaReportInfoDao pnrEvaReportInfoDao;
 
	public IPnrEvaReportInfoDao getPnrEvaReportInfoDao() {
		return pnrEvaReportInfoDao;
	}

	public void setPnrEvaReportInfoDao(IPnrEvaReportInfoDao pnrEvaReportInfoDao) {
		this.pnrEvaReportInfoDao = pnrEvaReportInfoDao;
	}

	public void savePnrEvaReportInfo(PnrEvaReportInfo evaReportInfo) {
		pnrEvaReportInfoDao.savePnrEvaReportInfo(evaReportInfo);
	}

	public PnrEvaReportInfo getPnrEvaReportInfo(String id) {
		return pnrEvaReportInfoDao.getPnrEvaReportInfo(id);
	}

	public void removePnrEvaReportInfo(PnrEvaReportInfo evaReportInfo) {
		pnrEvaReportInfoDao.removePnrEvaReportInfo(evaReportInfo);
	}

	public List getReportInfoByCondition(String conditions) {
		return pnrEvaReportInfoDao.getReportInfoByCondition(conditions);
	}
	
	public List getReportInfoByTimeAndPartner(String templateId,String area,
			String timeType,String time, String partnerId,String publicFlag){
		return pnrEvaReportInfoDao.getReportInfoByTimeAndPartner(templateId,area,
				timeType,time, partnerId,publicFlag);
	}
	public Map listReportInfoForPage(final Integer curPage,final Integer pageSize,
			final String whereStr) {
		return pnrEvaReportInfoDao.listReportInfoForPage(curPage, pageSize, whereStr);
	}
	
	
	public List getReportInfosByTimeAndAllPartner(String templateId,String area,
			String timeType,String time, String publicFlag){
		return pnrEvaReportInfoDao.getReportInfosByTimeAndAllPartner(templateId,area,
				timeType,time, publicFlag);
	}
	public List getReportYearStaticsByTime(String belongNode,String startTime,String endTime,String publicFlag){
		//查询出汇总分数
		return  pnrEvaReportInfoDao.getReportYearStaticsByTime(belongNode,startTime,endTime,publicFlag);

	}
	public List getReportMouthStaticsByTime(String belongNode,String area,String time,String publicFlag){
		//查询出汇总分数
		return  pnrEvaReportInfoDao.getReportMouthStaticsByTime(belongNode,area,time,publicFlag);

	}
	public List getReportsByTime(String belongNode,String area,String time,String publicFlag){
		//查询出汇总分数
		return  pnrEvaReportInfoDao.getReportsByTime(belongNode,area,time,publicFlag);

	}
}
