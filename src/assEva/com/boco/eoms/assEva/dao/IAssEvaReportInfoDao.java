package com.boco.eoms.assEva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.assEva.model.AssEvaReportInfo;

public interface IAssEvaReportInfoDao extends Dao{

	public AssEvaReportInfo getAssEvaReportInfo(String id);
	
	public void saveAssEvaReportInfo(AssEvaReportInfo assEvaReportInfo);
	
	public void removeAssEvaReportInfo(AssEvaReportInfo assEvaReportInfo);
	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where);
}
