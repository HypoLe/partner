package com.boco.eoms.partner.chanEva.mgr;

import com.boco.eoms.partner.chanEva.dao.IChanEvaEntityRelDao;
import com.boco.eoms.partner.chanEva.model.ChanEvaEntityRel;

public interface IChanEvaEntityRelMgr {

	public IChanEvaEntityRelDao getChanEvaEntityDao();
	
	public void setChanEvaEntityDao(IChanEvaEntityRelDao entityDao);
	
	public ChanEvaEntityRel getChanEvaEntityRel(String id);
	
	public void saveChanEvaEntityRel(ChanEvaEntityRel chanEvaEntityRel);

	public ChanEvaEntityRel getChanEvaEntityRelByTemplateId(String templateId);
}
