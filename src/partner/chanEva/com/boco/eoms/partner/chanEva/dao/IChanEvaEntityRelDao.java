package com.boco.eoms.partner.chanEva.dao;
import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.chanEva.model.ChanEvaEntityRel;

public interface IChanEvaEntityRelDao extends Dao {
	
	public ChanEvaEntityRel getChanEvaEntityRel(String id);
	
	public void saveChanEvaEntityRel(ChanEvaEntityRel chanEvaEntityRel);

	public ChanEvaEntityRel getChanEvaEntityRelByTemplateId(String templateId);
}
