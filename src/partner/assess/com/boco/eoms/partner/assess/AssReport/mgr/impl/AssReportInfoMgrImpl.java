/**
 * 
 */
package com.boco.eoms.partner.assess.AssReport.mgr.impl;

import java.util.Iterator;
import java.util.List;
import com.boco.eoms.partner.assess.AssReport.dao.IAssReportInfoDao;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssReportInfoMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;

/**
 * <p>
 * Title:考核报表信息业务方法基类
 * </p>
 * <p>
 * Description:考核报表信息业务方法基类
 * </p>
 * <p>
 * Date:Dec 2, 2010 6:19:40 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssReportInfoMgrImpl implements IAssReportInfoMgr {


	private IAssReportInfoDao reportInfoDao;
 
	public IAssReportInfoDao getAssReportInfoDao() {
		return reportInfoDao;
	}

	public void setAssReportInfoDao(IAssReportInfoDao reportInfoDao) {
		this.reportInfoDao = reportInfoDao;
	}

	public void saveAssReportInfo(AssReportInfo assReportInfo) {
		reportInfoDao.saveAssReportInfo(assReportInfo);
	}
	
	public AssReportInfo getReportInfoByTimeAndPartner(String taskId,String areaId,
			String timeType,String time, String partnerId,String publicFlag){
			List reportList = reportInfoDao.getReportInfoByTimeAndPartner(taskId,areaId,
				timeType,time, partnerId,publicFlag);
			Iterator it = reportList.iterator();
			AssReportInfo assReportInfo = new AssReportInfo();
			if (it.hasNext()) {
				assReportInfo = (AssReportInfo) it.next();
			}
		return assReportInfo;
	}

	public AssReportInfo getAssReportInfo(String id) {
		return reportInfoDao.getAssReportInfo(id);
	}

	public AssReportInfo getReportInfoByTreeNodeId(String treeNodeId,String areaId,
			String timeType,String time, String partnerId,String publicFlag){
			List reportList = reportInfoDao.getReportInfoByTreeNodeId(treeNodeId,areaId,
				timeType,time, partnerId,publicFlag);
			AssReportInfo assReportInfo = new AssReportInfo();
			if(reportList!=null&&reportList.size()>0){
				 assReportInfo = (AssReportInfo)reportList.get(0);
			}
		return assReportInfo;
	}	
}
