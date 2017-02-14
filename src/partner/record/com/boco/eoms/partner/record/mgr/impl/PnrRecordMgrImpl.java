package com.boco.eoms.partner.record.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.record.dao.IPnrRecordDao;
import com.boco.eoms.partner.record.mgr.IPnrRecordMgr;
import com.boco.eoms.partner.record.model.PnrRecord;

public class PnrRecordMgrImpl implements IPnrRecordMgr{
	private IPnrRecordDao pnrRecordDao;

	public IPnrRecordDao getPnrRecordDao() {
		return pnrRecordDao;
	}
	public void setPnrRecordDao(IPnrRecordDao pnrRecordDao) {
		this.pnrRecordDao = pnrRecordDao;
	}
	/**
	 * 返回质量管理报告列表 
	 */
	public List getPnrRecord(){
		return pnrRecordDao.getPnrRecord();
	};
	/**
	 * 根据主键ID查询质量管理报告列表
	 *
	 */
	public PnrRecord getPnrRecord(final String id){
		return pnrRecordDao.getPnrRecord(id);
	};
	/**
	 * 保存质量管理报告 
	 */
	public void savePnrRecord(PnrRecord pnrRecord){
		pnrRecordDao.savePnrRecord(pnrRecord);
	};
	/**
	 * 删除质量管理报告
	 */
	public void removePnrRecord(final String id){
		pnrRecordDao.removePnrRecord(id);
	};
	/**
	 * 分页查询质量管理报告列表 
	 */
	public Map getPnrRecords(final Integer curPage, final Integer pageSize,
			final String whereStr){
		return pnrRecordDao.getPnrRecords(curPage, pageSize, whereStr);
	};
	/**
	 * 根据条件查询质量管理报告列表 
	 */
	public List getPnrRecords(final String where){
		return pnrRecordDao.getPnrRecords(where);
	};
	
}
