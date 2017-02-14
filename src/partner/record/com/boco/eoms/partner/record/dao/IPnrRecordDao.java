package com.boco.eoms.partner.record.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.record.model.PnrRecord;

public interface IPnrRecordDao {

	/**
	 * 返回质量管理报告列表 
	 */
	public List getPnrRecord();
	/**
	 * 根据主键ID查询质量管理报告列表
	 *
	 */
	public PnrRecord getPnrRecord(final String id);
	/**
	 * 保存质量管理报告 
	 */
	public void savePnrRecord(PnrRecord pnrRecord);
	/**
	 * 删除质量管理报告
	 */
	public void removePnrRecord(final String id);
	/**
	 * 分页查询质量管理报告列表 
	 */
	public Map getPnrRecords(final Integer curPage, final Integer pageSize,
			final String whereStr);
	/**
	 * 根据条件查询质量管理报告列表 
	 */
	public List getPnrRecords(final String where);
	
}
