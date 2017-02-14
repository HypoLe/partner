package com.boco.eoms.partner.assiEva.dao;
import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.assiEva.model.AssiEvaEntityRel;

public interface IAssiEvaEntityRelDao extends Dao {
	
	public AssiEvaEntityRel getAssiEvaEntityRel(String id);
	
	public void saveAssiEvaEntityRel(AssiEvaEntityRel assiEvaEntityRel);

	public AssiEvaEntityRel getAssiEvaEntityRelByTemplateId(String templateId);
}
