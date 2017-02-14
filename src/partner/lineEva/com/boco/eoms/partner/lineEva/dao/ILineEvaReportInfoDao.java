package com.boco.eoms.partner.lineEva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.lineEva.model.LineEvaReportInfo;

public interface ILineEvaReportInfoDao extends Dao{

	public LineEvaReportInfo getLineEvaReportInfo(String id);
	
	public void saveLineEvaReportInfo(LineEvaReportInfo lineEvaReportInfo);
	
	public void removeLineEvaReportInfo(LineEvaReportInfo lineEvaReportInfo);
	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where);
}
