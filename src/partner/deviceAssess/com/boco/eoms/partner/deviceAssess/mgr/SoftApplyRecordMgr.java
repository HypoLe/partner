package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.SoftApplyRecord;

/**
 * <p>
 * Title:软件申请问题记录
 * </p>
 * <p>
 * Description:软件申请问题记录
 * </p>
 * <p>
 * Sun Sep 26 09:32:29 CST 2011
 * </p> 
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface SoftApplyRecordMgr {
 
	/**
	 *
	 * 取软件申请问题记录 列表
	 * @return 返回软件申请问题记录列表
	 */
	public List getSoftApplyRecords();
    
	/**
	 * 根据主键查询软件申请问题记录
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public SoftApplyRecord getSoftApplyRecord(final String id);
    
	/**
	 * 保存软件申请问题记录
	 * @param SoftApplyRecord 软件申请问题记录
	 */
	public void saveSoftApplyRecord(SoftApplyRecord softApplyRecord);
    
	/**
	 * 根据主键删除软件申请问题记录
	 * @param id 主键
	 */
	public void removeSoftApplyRecord(final String id);
    
	/**
	 * 根据条件分页查询软件申请问题记录
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回软件申请问题记录分页列表
	 */
	public Map getSoftApplyRecords(final Integer curPage, final Integer pageSize,
			final String whereStr);

	/**
	 * 同时保存软件申请问题记录和审批信息
	 * @param SoftApplyRecord
	 * @param daa
	 */
	public void saveDataAndApprove(SoftApplyRecord softApplyRecord,
			DeviceAssessApprove daa);
	
	/**
	 * 根据条件分页查询软件申请问题记录
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param hql 查询语句
	 * @return 返回软件申请问题记录分页列表
	 */
    public Map getSoftApplyRecordsWithHQL(final Integer curPage, final Integer pageSize,
    		final String hql);
}