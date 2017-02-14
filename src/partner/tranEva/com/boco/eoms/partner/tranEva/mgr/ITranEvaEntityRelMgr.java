package com.boco.eoms.partner.tranEva.mgr;

import com.boco.eoms.partner.tranEva.dao.ITranEvaEntityRelDao;
import com.boco.eoms.partner.tranEva.model.TranEvaEntityRel;

public interface ITranEvaEntityRelMgr {

	public ITranEvaEntityRelDao getTranEvaEntityDao();
	
	public void setTranEvaEntityDao(ITranEvaEntityRelDao entityDao);
	
	public TranEvaEntityRel getTranEvaEntityRel(String id);
	
	public void saveTranEvaEntityRel(TranEvaEntityRel tranEvaEntityRel);

	public TranEvaEntityRel getTranEvaEntityRelByTemplateId(String templateId);
}
