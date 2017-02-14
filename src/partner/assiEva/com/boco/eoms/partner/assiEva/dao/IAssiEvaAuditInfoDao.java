package com.boco.eoms.partner.assiEva.dao;

import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.assiEva.model.AssiEvaAuditInfo;

public interface IAssiEvaAuditInfoDao extends Dao{

	public AssiEvaAuditInfo getAssiEvaAuditInfo(String id);
	
	public void saveAssiEvaAuditInfo(AssiEvaAuditInfo assiEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
