package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.Repairinfo;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;

/**
 * <p>
 * Title:板件返修事件信息
 * </p>
 * <p>
 * Description:板件返修事件信息
 * </p>
 * <p>
 * Tue Sep 28 15:24:09 CST 2010
 * </p> 
 * 
 * @author zhangxuesong
 * @version 1.0
 * 
 */
 public interface RepairinfoMgr {
 
	/**
	 *
	 * 取板件返修事件信息 列表
	 * @return 返回板件返修事件信息列表
	 */
	public List getRepairinfos();
    
	/**
	 * 根据主键查询板件返修事件信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public Repairinfo getRepairinfo(final String id);
    
	/**
	 * 保存板件返修事件信息
	 * @param repairinfo 板件返修事件信息
	 */
	public void saveRepairinfo(Repairinfo repairinfo);
    
	/**
	 * 根据主键删除板件返修事件信息
	 * @param id 主键
	 */
	public void removeRepairinfo(final String id);
    
	/**
	 * 根据条件分页查询板件返修事件信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回板件返修事件信息的分页列表
	 */
	
	public Repairinfo getRepairinfos(final String id);
	 
	public Map getRepairinfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
	public void saveDataAndApprove(Repairinfo repairinfo,
			DeviceAssessApprove daa);
	public ImportResult importFromFile(FormFile formFile,final Map params) throws Exception;
}