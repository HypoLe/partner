package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.deviceAssess.model.FacilityNum;

/**
 * <p>
 * Title:设备量信息
 * </p>
 * <p>
 * Description:设备量信息
 * </p>
 * <p>
 * Wed Sep 29 11:28:40 CST 2010
 * </p> 
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface FacilityNumMgr {
 
	/**
	 *
	 * 取设备量信息 列表
	 * @return 返回设备量信息列表
	 */
	public List getFacilityNums();
    
	/**
	 * 根据主键查询设备量信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public FacilityNum getFacilityNum(final String id);
    
	/**
	 * 保存设备量信息
	 * @param facilityNum 设备量信息
	 */
	public void saveFacilityNum(FacilityNum facilityNum);
    
	/**
	 * 根据主键删除设备量信息
	 * @param id 主键
	 */
	public void removeFacilityNum(final String id);
    
	/**
	 * 根据条件分页查询设备量信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回设备量信息的分页列表
	 */
	public Map getFacilityNums(final Integer curPage, final Integer pageSize,
			final String whereStr);
			
}