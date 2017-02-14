package com.boco.eoms.partner.assiEva.mgr;

import com.boco.eoms.partner.assiEva.dao.IAssiEvaEntityRelDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaEntityRel;

public interface IAssiEvaEntityRelMgr {

	public IAssiEvaEntityRelDao getAssiEvaEntityDao();
	
	public void setAssiEvaEntityDao(IAssiEvaEntityRelDao entityDao);
	
	public AssiEvaEntityRel getAssiEvaEntityRel(String id);
	
	public void saveAssiEvaEntityRel(AssiEvaEntityRel assiEvaEntityRel);

	public AssiEvaEntityRel getAssiEvaEntityRelByTemplateId(String templateId);
}
