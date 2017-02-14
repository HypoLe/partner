package com.boco.eoms.partner.quality.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.quality.dao.IPnrQualityMainDao;
import com.boco.eoms.partner.quality.mgr.IPnrQualityMainMgr;
import com.boco.eoms.partner.quality.model.PnrQualityMain;

public class PnrQualityMainMgrImpl implements IPnrQualityMainMgr{
	private IPnrQualityMainDao pnrQualityMainDao;

	public IPnrQualityMainDao getPnrQualityMainDao() {
		return pnrQualityMainDao;
	}
	public void setPnrQualityMainDao(IPnrQualityMainDao pnrQualityMainDao) {
		this.pnrQualityMainDao = pnrQualityMainDao;
	}
	/**
	 * 返回质量管理报告列表 
	 */
	public List getPnrQualityMain(){
		return pnrQualityMainDao.getPnrQualityMain();
	};
	/**
	 * 根据主键ID查询质量管理报告列表
	 *
	 */
	public PnrQualityMain getPnrQualityMain(final String id){
		return pnrQualityMainDao.getPnrQualityMain(id);
	};
	/**
	 * 保存质量管理报告 
	 */
	public void savePnrQualityMain(PnrQualityMain pnrQualityMain){
		pnrQualityMainDao.savePnrQualityMain(pnrQualityMain);
	};
	/**
	 * 删除质量管理报告
	 */
	public void removePnrQualityMain(final String id){
		pnrQualityMainDao.removePnrQualityMain(id);
	};
	/**
	 * 分页查询质量管理报告列表 
	 */
	public Map getPnrQualityMains(final Integer curPage, final Integer pageSize,
			final String whereStr){
		return pnrQualityMainDao.getPnrQualityMains(curPage, pageSize, whereStr);
	};
	/**
	 * 根据条件查询质量管理报告列表 
	 */
	public List getPnrQualityMains(final String where){
		return pnrQualityMainDao.getPnrQualityMains(where);
	};
	
}
