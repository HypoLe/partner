package com.boco.eoms.partner.chanEva.mgr;

import java.util.List;

import com.boco.eoms.partner.chanEva.dao.IChanEvaOrgDao;
import com.boco.eoms.partner.chanEva.dao.IChanEvaReportInfoDao;
import com.boco.eoms.partner.chanEva.model.ChanEvaOrg;
import com.boco.eoms.partner.chanEva.model.ChanEvaReportInfo;

public interface IChanEvaReportInfoMgr {

	public IChanEvaReportInfoDao getChanEvaReportInfoDao();

	public void setChanEvaReportInfoDao(IChanEvaReportInfoDao ReportInfoDao);

	public void saveChanEvaReportInfo(ChanEvaReportInfo chanEvaReportInfo);

	public ChanEvaReportInfo getChanEvaReportInfo(String id);

	public void removeChanEvaReportInfo(ChanEvaReportInfo chanEvaReportInfo);
	
	public List getReportInfoByCondition(String conditions);

}
