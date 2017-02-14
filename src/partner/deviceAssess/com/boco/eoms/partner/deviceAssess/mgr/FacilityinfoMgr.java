package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.Facilityinfo;

/**
 * <p>
 * Title:厂家设备问题事件信息
 * </p>
 * <p>
 * Description:厂家设备问题事件信息
 * </p>
 * <p>
 * Tue Sep 28 15:24:09 CST 2010
 * </p>
 * 
 * @author zhangxuesong 
 * @version 1.0
 * 
 */
 public interface FacilityinfoMgr {
 
	/**
	 *
	 * 取厂家设备问题事件信息 列表
	 * @return 返回厂家设备问题事件信息列表
	 */
	public List getFacilityinfos();
    
	/**
	 * 根据主键查询厂家设备问题事件信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public Facilityinfo getFacilityinfo(final String id);
    
	/**
	 * 保存厂家设备问题事件信息
	 * @param facilityinfo 厂家设备问题事件信息
	 */
	public void saveFacilityinfo(Facilityinfo facilityinfo);
    
	/**
	 * 根据主键删除厂家设备问题事件信息
	 * @param id 主键
	 */
	public void removeFacilityinfo(final String id);
    
	/**
	 * 根据条件分页查询厂家设备问题事件信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回厂家设备问题事件信息的分页列表
	 */
	public Map getFacilityinfos(final Integer curPage, final Integer pageSize,
			final String whereStr);

	public void saveDataAndApprove(Facilityinfo facilityinfo,
			DeviceAssessApprove daa);
			
}