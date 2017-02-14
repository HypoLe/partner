package com.boco.eoms.partner.quality.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.quality.dao.IPnrQualityAuditDao;
import com.boco.eoms.partner.quality.mgr.IPnrQualityAuditMgr;
import com.boco.eoms.partner.quality.model.PnrQualityAudit;

public class PnrQualityAuditMgrImpl implements IPnrQualityAuditMgr{
	private IPnrQualityAuditDao pnrQualityAuditDao;

	public IPnrQualityAuditDao getPnrQualityAuditDao() {
		return pnrQualityAuditDao;
	}
	public void setPnrQualityAuditDao(IPnrQualityAuditDao pnrQualityAuditDao) {
		this.pnrQualityAuditDao = pnrQualityAuditDao;
	}
	/**
	 * 返回质量管理报告列表 
	 */
	public List getPnrQualityAudit(){
		return pnrQualityAuditDao.getPnrQualityAudit();
	};
	/**
	 * 根据主键ID查询质量管理报告列表
	 *
	 */
	public PnrQualityAudit getPnrQualityAudit(final String id){
		return pnrQualityAuditDao.getPnrQualityAudit(id);
	};
	/**
	 * 保存质量管理报告 
	 */
	public void savePnrQualityAudit(PnrQualityAudit pnrQualityAudit){
		pnrQualityAuditDao.savePnrQualityAudit(pnrQualityAudit);
	};
	/**
	 * 删除质量管理报告
	 */
	public void removePnrQualityAudit(final String id){
		pnrQualityAuditDao.removePnrQualityAudit(id);
	};
	/**
	 * 分页查询质量管理报告列表 
	 */
	public Map getPnrQualityAudits(final Integer curPage, final Integer pageSize,
			final String whereStr){
		return pnrQualityAuditDao.getPnrQualityAudits(curPage, pageSize, whereStr);
	};
	/**
	 * 根据条件查询质量管理报告列表 
	 */
	public List getPnrQualityAudits(final String where){
		return pnrQualityAuditDao.getPnrQualityAudits(where);
	};
	
}
