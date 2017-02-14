package com.boco.eoms.partner.lineEva.dao;
import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.lineEva.model.LineEvaEntityRel;

public interface ILineEvaEntityRelDao extends Dao {
	
	public LineEvaEntityRel getLineEvaEntityRel(String id);
	
	public void saveLineEvaEntityRel(LineEvaEntityRel lineEvaEntityRel);

	public LineEvaEntityRel getLineEvaEntityRelByTemplateId(String templateId);
}
