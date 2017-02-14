package com.boco.eoms.partner.tranEva.mgr;

import java.util.List;

import com.boco.eoms.partner.tranEva.dao.ITranEvaOrgDao;
import com.boco.eoms.partner.tranEva.dao.ITranEvaReportInfoDao;
import com.boco.eoms.partner.tranEva.model.TranEvaOrg;
import com.boco.eoms.partner.tranEva.model.TranEvaReportInfo;

public interface ITranEvaReportInfoMgr {

	public ITranEvaReportInfoDao getTranEvaReportInfoDao();

	public void setTranEvaReportInfoDao(ITranEvaReportInfoDao ReportInfoDao);

	public void saveTranEvaReportInfo(TranEvaReportInfo tranEvaReportInfo);

	public TranEvaReportInfo getTranEvaReportInfo(String id);

	public void removeTranEvaReportInfo(TranEvaReportInfo tranEvaReportInfo);
	
	public List getReportInfoByCondition(String conditions);

}
