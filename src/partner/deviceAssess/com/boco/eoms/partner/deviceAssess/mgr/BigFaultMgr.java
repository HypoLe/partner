package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.partner.deviceAssess.model.BigFault;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;

/**
 * <p>
 * Title:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Description:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Mon Sep 27 13:45:23 CST 2010 
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface BigFaultMgr {
 
	/**
	 *
	 * 取厂家设备重大故障事件信息 列表
	 * @return 返回厂家设备重大故障事件信息列表
	 */
	public List getBigFaults();
    
	/**
	 * 根据主键查询厂家设备重大故障事件信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public BigFault getBigFault(final String id);
    
	/**
	 * 保存厂家设备重大故障事件信息
	 * @param bigFault 厂家设备重大故障事件信息
	 */
	public void saveBigFault(BigFault bigFault);
    
	/**
	 * 根据主键删除厂家设备重大故障事件信息
	 * @param id 主键
	 */
	public void removeBigFault(final String id);
    
	/**
	 * 根据条件分页查询厂家设备重大故障事件信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回厂家设备重大故障事件信息的分页列表
	 */
	public Map getBigFaults(final Integer curPage, final Integer pageSize,
			final String whereStr);

	public void saveDataAndApprove(BigFault bigFault, DeviceAssessApprove daa);

	public ImportResult importFromFile(FormFile formFile, Map params) throws Exception;
			
}