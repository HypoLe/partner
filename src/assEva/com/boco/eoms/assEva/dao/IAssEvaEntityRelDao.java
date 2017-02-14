package com.boco.eoms.assEva.dao;
import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.assEva.model.AssEvaEntityRel;

public interface IAssEvaEntityRelDao extends Dao {
	
	public AssEvaEntityRel getAssEvaEntityRel(String id);
	
	public void saveAssEvaEntityRel(AssEvaEntityRel assEvaEntityRel);

	public AssEvaEntityRel getAssEvaEntityRelByTemplateId(String templateId);
}
