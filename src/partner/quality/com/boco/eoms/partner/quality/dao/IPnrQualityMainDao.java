package com.boco.eoms.partner.quality.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.quality.model.PnrQualityMain;

public interface IPnrQualityMainDao {

	/**
	 * 返回质量管理报告列表 
	 */
	public List getPnrQualityMain();
	/**
	 * 根据主键ID查询质量管理报告列表
	 *
	 */
	public PnrQualityMain getPnrQualityMain(final String id);
	/**
	 * 保存质量管理报告 
	 */
	public void savePnrQualityMain(PnrQualityMain pnrQualityMain);
	/**
	 * 删除质量管理报告
	 */
	public void removePnrQualityMain(final String id);
	/**
	 * 分页查询质量管理报告列表 
	 */
	public Map getPnrQualityMains(final Integer curPage, final Integer pageSize,
			final String whereStr);
	/**
	 * 根据条件查询质量管理报告列表 
	 */
	public List getPnrQualityMains(final String where);
	
}
