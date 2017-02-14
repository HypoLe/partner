package com.boco.eoms.partner.eva.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.eva.model.PnrEvaAuditInfo;

public interface IPnrEvaAuditInfoDao extends Dao{
	/**
	 * 根据主键id查询模板的审核信息
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public PnrEvaAuditInfo getPnrEvaAuditInfo(String id);
	/**
	 * 根据模板id查询模板的审核信息
	 * 
	 * @param templateId
	 *            模板主键
	 * @return
	 */
	public List getPnrEvaAudit(final String templateId);
	/**
	 * 保存模板的审核信息
	 * 
	 * @param PnrEvaAuditInfo
	 *            模板的审核信息
	 */
	public void savePnrEvaAuditInfo(PnrEvaAuditInfo evaAuditInfo);
	/**
	 * 根据查询条件查询模板的审核信息
	 * 
	 * @param curPage
	 *            当前页
	 *            
	 * @param pageSize
	 *            每页显示条数
	 *                
	 * @param whereStr
	 *            查询条件
	 * @return
	 */
	public Map getAuditInfoByCondition(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
