package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.Lserveinfo;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;

/**
 * <p>
 * Title:现场服务事件信息
 * </p>
 * <p>
 * Description:现场服务事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p>
 * 
 * @author zhangxuesong
 * @version 1.0 
 * 
 */
 public interface LserveinfoMgr {
 
	/**
	 *
	 * 取现场服务事件信息 列表
	 * @return 返回现场服务事件信息列表
	 */
	public List getLserveinfos(); 
    
	/**
	 * 根据主键查询现场服务事件信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public Lserveinfo getLserveinfo(final String id);
    
	/**
	 * 保存现场服务事件信息
	 * @param lserveinfo 现场服务事件信息
	 */
	public void saveLserveinfo(Lserveinfo lserveinfo);
    
	/**
	 * 根据主键删除现场服务事件信息
	 * @param id 主键
	 */
	public void removeLserveinfo(final String id);
    
	/**
	 * 根据条件分页查询现场服务事件信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回现场服务事件信息的分页列表
	 */
	 public Map getLserveinfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
	 
	 public void saveDataAndApprove(Lserveinfo lserveinfo,DeviceAssessApprove daa);	
	 
	 public ImportResult importFromFile(FormFile formFile,final Map params) throws Exception;
}