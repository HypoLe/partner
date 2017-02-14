package com.boco.eoms.partner.tranEva.dao;
import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.tranEva.model.TranEvaEntityRel;

public interface ITranEvaEntityRelDao extends Dao {
	
	public TranEvaEntityRel getTranEvaEntityRel(String id);
	
	public void saveTranEvaEntityRel(TranEvaEntityRel tranEvaEntityRel);

	public TranEvaEntityRel getTranEvaEntityRelByTemplateId(String templateId);
}
