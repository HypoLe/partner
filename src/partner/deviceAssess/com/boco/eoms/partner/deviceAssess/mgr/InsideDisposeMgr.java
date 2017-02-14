package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;

/**
 * <p>
 * Title:移动内部处理的设备故障事件信息
 * </p>
 * <p>
 * Description:移动内部处理的设备故障事件信息
 * </p>
 * <p>
 * Sun Sep 26 09:32:29 CST 2010
 * </p> 
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface InsideDisposeMgr {
 
	/**
	 *
	 * 取移动内部处理的设备故障事件信息 列表
	 * @return 返回移动内部处理的设备故障事件信息列表
	 */
	public List getInsideDisposes();
    
	/**
	 * 根据主键查询移动内部处理的设备故障事件信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public InsideDispose getInsideDispose(final String id);
    
	/**
	 * 保存移动内部处理的设备故障事件信息
	 * @param insideDispose 移动内部处理的设备故障事件信息
	 */
	public void saveInsideDispose(InsideDispose insideDispose);
    
	/**
	 * 根据主键删除移动内部处理的设备故障事件信息
	 * @param id 主键
	 */
	public void removeInsideDispose(final String id);
    
	/**
	 * 根据条件分页查询移动内部处理的设备故障事件信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回移动内部处理的设备故障事件信息的分页列表
	 */
	public Map getInsideDisposes(final Integer curPage, final Integer pageSize,
			final String whereStr);
	/**
	 * 将工单数据存入本地数据库中
	 */	
	public void eomsSheetToInsideDispose() ;

	/**
	 * 同时保存设备故障事件信息和审批信息
	 * @param insideDispose
	 * @param daa
	 */
	public void saveDataAndApprove(InsideDispose insideDispose,
			DeviceAssessApprove daa);

	/**
	 * 导入
	 * @param formFile
	 * @param params 
	 * @return
	 */
	public ImportResult importFromFile(FormFile formFile, Map params)throws Exception;
}