package com.boco.eoms.partner.lineEva.dao;

import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.lineEva.model.LineEvaAuditInfo;

public interface ILineEvaAuditInfoDao extends Dao{

	public LineEvaAuditInfo getLineEvaAuditInfo(String id);
	
	public void saveLineEvaAuditInfo(LineEvaAuditInfo lineEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
