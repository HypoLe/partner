package com.boco.eoms.partner.assiEva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.assiEva.model.AssiEvaReportInfo;

public interface IAssiEvaReportInfoDao extends Dao{

	public AssiEvaReportInfo getAssiEvaReportInfo(String id);
	
	public void saveAssiEvaReportInfo(AssiEvaReportInfo assiEvaReportInfo);
	
	public void removeAssiEvaReportInfo(AssiEvaReportInfo assiEvaReportInfo);
	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where);
}
