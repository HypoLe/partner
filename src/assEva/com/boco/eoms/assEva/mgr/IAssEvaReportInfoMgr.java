package com.boco.eoms.assEva.mgr;

import java.util.List;

import com.boco.eoms.assEva.dao.IAssEvaOrgDao;
import com.boco.eoms.assEva.dao.IAssEvaReportInfoDao;
import com.boco.eoms.assEva.model.AssEvaOrg;
import com.boco.eoms.assEva.model.AssEvaReportInfo;

public interface IAssEvaReportInfoMgr {

	public IAssEvaReportInfoDao getAssEvaReportInfoDao();

	public void setAssEvaReportInfoDao(IAssEvaReportInfoDao ReportInfoDao);

	public void saveAssEvaReportInfo(AssEvaReportInfo assEvaReportInfo);

	public AssEvaReportInfo getAssEvaReportInfo(String id);

	public void removeAssEvaReportInfo(AssEvaReportInfo assEvaReportInfo);
	
	public List getReportInfoByCondition(String conditions);

}
