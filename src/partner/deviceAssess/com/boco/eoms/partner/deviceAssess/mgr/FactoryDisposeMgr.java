package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.FactoryDispose;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;
import com.boco.eoms.partner.deviceAssess.webapp.form.FactoryDisposeForm;

/**
 * <p>
 * Title:厂家处理设备故障事件信息
 * </p>
 * <p>
 * Description:厂家处理设备故障事件信息
 * </p>
 * <p> 
 * Sun Sep 26 15:01:06 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface FactoryDisposeMgr {
 
	/**
	 *
	 * 取厂家处理设备故障事件信息 列表
	 * @return 返回厂家处理设备故障事件信息列表
	 */
	public List getFactoryDisposes();
    
	/**
	 * 根据主键查询厂家处理设备故障事件信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public FactoryDispose getFactoryDispose(final String id);
    
	/**
	 * 保存厂家处理设备故障事件信息
	 * @param factoryDispose 厂家处理设备故障事件信息
	 */
	public void saveFactoryDispose(FactoryDispose factoryDispose);
    
	/**
	 * 根据主键删除厂家处理设备故障事件信息
	 * @param id 主键
	 */
	public void removeFactoryDispose(final String id);
    
	/**
	 * 根据条件分页查询厂家处理设备故障事件信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回厂家处理设备故障事件信息的分页列表
	 */
	public Map getFactoryDisposes(final Integer curPage, final Integer pageSize,
			final String whereStr);

	public ImportResult importFromFile(FormFile formFile, Map params) throws Exception;

	public void saveDataAndApprove(FactoryDispose factoryDispose,
			DeviceAssessApprove daa);
			
}