package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.partner.deviceAssess.model.Counsel;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;

/**
 * <p>
 * Title:咨询服务事件信息表
 * </p>
 * <p>
 * Description:咨询服务事件信息表
 * </p>
 * <p> 
 * Mon Sep 27 15:01:30 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface CounselMgr {
 
	/**
	 *
	 * 取咨询服务事件信息表 列表
	 * @return 返回咨询服务事件信息表列表
	 */
	public List getCounsels();
    
	/**
	 * 根据主键查询咨询服务事件信息表
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public Counsel getCounsel(final String id);
    
	/**
	 * 保存咨询服务事件信息表
	 * @param counsel 咨询服务事件信息表
	 */
	public void saveCounsel(Counsel counsel);
	
	/**
	 * 同时保存设备故障事件信息和审批信息
	 * @param insideDispose
	 * @param daa
	 */
	public void saveDataAndApprove(Counsel counsel,
			DeviceAssessApprove daa);
    
	/**
	 * 根据主键删除咨询服务事件信息表
	 * @param id 主键
	 */
	public void removeCounsel(final String id);
    
	/**
	 * 根据条件分页查询咨询服务事件信息表
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回咨询服务事件信息表的分页列表
	 */
	public Map getCounsels(final Integer curPage, final Integer pageSize,
			final String whereStr);
	/**
	 * 导入
	 * @param formFile
	 * @param params 
	 * @return
	 */
	public ImportResult importFromFile(FormFile formFile, Map params)throws Exception;
			
}