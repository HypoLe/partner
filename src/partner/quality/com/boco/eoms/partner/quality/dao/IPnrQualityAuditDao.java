package com.boco.eoms.partner.quality.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.quality.model.PnrQualityAudit;

public interface IPnrQualityAuditDao {

	/**
	 * 返回质量管理报告列表 
	 */
	public List getPnrQualityAudit();
	/**
	 * 根据主键ID查询质量管理报告列表
	 *
	 */
	public PnrQualityAudit getPnrQualityAudit(final String id);
	/**
	 * 保存质量管理报告 
	 */
	public void savePnrQualityAudit(PnrQualityAudit pnrQualityAudit);
	/**
	 * 删除质量管理报告
	 */
	public void removePnrQualityAudit(final String id);
	/**
	 * 分页查询质量管理报告列表 
	 */
	public Map getPnrQualityAudits(final Integer curPage, final Integer pageSize,
			final String whereStr);
	/**
	 * 根据条件查询质量管理报告列表 
	 */
	public List getPnrQualityAudits(final String where);
	
}
