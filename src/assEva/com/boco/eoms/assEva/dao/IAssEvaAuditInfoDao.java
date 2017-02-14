package com.boco.eoms.assEva.dao;

import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.assEva.model.AssEvaAuditInfo;

public interface IAssEvaAuditInfoDao extends Dao{

	public AssEvaAuditInfo getAssEvaAuditInfo(String id);
	
	public void saveAssEvaAuditInfo(AssEvaAuditInfo assEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
