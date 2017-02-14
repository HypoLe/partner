package com.boco.eoms.partner.assiEva.mgr;

import java.util.List;

import com.boco.eoms.partner.assiEva.dao.IAssiEvaOrgDao;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaReportInfoDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaOrg;
import com.boco.eoms.partner.assiEva.model.AssiEvaReportInfo;

public interface IAssiEvaReportInfoMgr {

	public IAssiEvaReportInfoDao getAssiEvaReportInfoDao();

	public void setAssiEvaReportInfoDao(IAssiEvaReportInfoDao ReportInfoDao);

	public void saveAssiEvaReportInfo(AssiEvaReportInfo assiEvaReportInfo);

	public AssiEvaReportInfo getAssiEvaReportInfo(String id);

	public void removeAssiEvaReportInfo(AssiEvaReportInfo assiEvaReportInfo);
	
	public List getReportInfoByCondition(String conditions);

}
