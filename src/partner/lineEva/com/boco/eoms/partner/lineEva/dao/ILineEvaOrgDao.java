package com.boco.eoms.partner.lineEva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.lineEva.model.LineEvaOrg;

public interface ILineEvaOrgDao extends Dao{

	public LineEvaOrg getLineEvaOrg(String id);
	
	public void saveLineEvaOrg(LineEvaOrg lineEvaOrg);
	
	public void removeLineEvaOrg(LineEvaOrg lineEvaOrg);
	
	// 根据模板ID获取组织列表
	public List getOrgsByTempletId(String templateId);

	// 根据模板ID删除相关组织
	public void removeOrgOfTemplate(String templateId);

	// 根据条件获取模板列表
	public List getTempletByOrgId(String where);
	
	// 根据模板ID和动作类型删除相关组织
	public void removeOrgOfTemplateAndActionType(String templateId,String actionType);
}
