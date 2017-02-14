package com.boco.eoms.assEva.mgr;

import com.boco.eoms.assEva.dao.IAssEvaEntityRelDao;
import com.boco.eoms.assEva.model.AssEvaEntityRel;

public interface IAssEvaEntityRelMgr {

	public IAssEvaEntityRelDao getAssEvaEntityDao();
	
	public void setAssEvaEntityDao(IAssEvaEntityRelDao entityDao);
	
	public AssEvaEntityRel getAssEvaEntityRel(String id);
	
	public void saveAssEvaEntityRel(AssEvaEntityRel assEvaEntityRel);

	public AssEvaEntityRel getAssEvaEntityRelByTemplateId(String templateId);
}
