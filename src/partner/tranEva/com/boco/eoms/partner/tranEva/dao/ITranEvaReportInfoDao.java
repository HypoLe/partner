package com.boco.eoms.partner.tranEva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.tranEva.model.TranEvaReportInfo;

public interface ITranEvaReportInfoDao extends Dao{

	public TranEvaReportInfo getTranEvaReportInfo(String id);
	
	public void saveTranEvaReportInfo(TranEvaReportInfo tranEvaReportInfo);
	
	public void removeTranEvaReportInfo(TranEvaReportInfo tranEvaReportInfo);
	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where);
}
