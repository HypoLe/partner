package com.boco.eoms.partner.lineEva.mgr;

import java.util.List;

import com.boco.eoms.partner.lineEva.dao.ILineEvaOrgDao;
import com.boco.eoms.partner.lineEva.dao.ILineEvaReportInfoDao;
import com.boco.eoms.partner.lineEva.model.LineEvaOrg;
import com.boco.eoms.partner.lineEva.model.LineEvaReportInfo;

public interface ILineEvaReportInfoMgr {

	public ILineEvaReportInfoDao getLineEvaReportInfoDao();

	public void setLineEvaReportInfoDao(ILineEvaReportInfoDao ReportInfoDao);

	public void saveLineEvaReportInfo(LineEvaReportInfo lineEvaReportInfo);

	public LineEvaReportInfo getLineEvaReportInfo(String id);

	public void removeLineEvaReportInfo(LineEvaReportInfo lineEvaReportInfo);
	
	public List getReportInfoByCondition(String conditions);

}
