package com.boco.eoms.eva.mgr;

import java.util.List;

import com.boco.eoms.eva.dao.IEvaOrgDao;
import com.boco.eoms.eva.dao.IEvaReportInfoDao;
import com.boco.eoms.eva.model.EvaOrg;
import com.boco.eoms.eva.model.EvaReportInfo;

public interface IEvaReportInfoMgr {

	public IEvaReportInfoDao getEvaReportInfoDao();

	public void setEvaReportInfoDao(IEvaReportInfoDao ReportInfoDao);

	public void saveEvaReportInfo(EvaReportInfo evaReportInfo);

	public EvaReportInfo getEvaReportInfo(String id);

	public void removeEvaReportInfo(EvaReportInfo evaReportInfo);
	
	public List getReportInfoByCondition(String conditions);

	public List getReportInfoByTaskId(String taskId);
	//得到所有没有执行的考核任务
	public List getUnEvaReport(final String time, final String dept);
}
