package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.Softupinfo;

/**
 * <p>
 * Title:软件升级事件信息
 * </p>
 * <p>
 * Description:软件升级事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p>
 * 
 * @author zhangxuesong 
 * @version 1.0
 * 
 */
 public interface SoftupinfoMgr {
 
	/**
	 *
	 * 取软件升级事件信息 列表
	 * @return 返回软件升级事件信息列表
	 */
	public List getSoftupinfos();
    
	/**
	 * 根据主键查询软件升级事件信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public Softupinfo getSoftupinfo(final String id);
    
	/**
	 * 保存软件升级事件信息
	 * @param softupinfo 软件升级事件信息
	 */
	public void saveSoftupinfo(Softupinfo softupinfo);
    
	/**
	 * 根据主键删除软件升级事件信息
	 * @param id 主键
	 */
	public void removeSoftupinfo(final String id);
    
	/**
	 * 根据条件分页查询软件升级事件信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回软件升级事件信息的分页列表
	 */
	public Map getSoftupinfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
		
	 public Softupinfo getSoftupinfos(final String id) ;
	 
	 public void saveDataAndApprove(Softupinfo softupinfo,
				DeviceAssessApprove daa);
}