package com.boco.eoms.partner.lineEva.mgr;

import com.boco.eoms.partner.lineEva.dao.ILineEvaEntityRelDao;
import com.boco.eoms.partner.lineEva.model.LineEvaEntityRel;

public interface ILineEvaEntityRelMgr {

	public ILineEvaEntityRelDao getLineEvaEntityDao();
	
	public void setLineEvaEntityDao(ILineEvaEntityRelDao entityDao);
	
	public LineEvaEntityRel getLineEvaEntityRel(String id);
	
	public void saveLineEvaEntityRel(LineEvaEntityRel lineEvaEntityRel);

	public LineEvaEntityRel getLineEvaEntityRelByTemplateId(String templateId);
}
