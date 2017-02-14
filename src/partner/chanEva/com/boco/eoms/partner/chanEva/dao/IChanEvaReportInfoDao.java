package com.boco.eoms.partner.chanEva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.chanEva.model.ChanEvaReportInfo;

public interface IChanEvaReportInfoDao extends Dao{

	public ChanEvaReportInfo getChanEvaReportInfo(String id);
	
	public void saveChanEvaReportInfo(ChanEvaReportInfo chanEvaReportInfo);
	
	public void removeChanEvaReportInfo(ChanEvaReportInfo chanEvaReportInfo);
	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where);
}
