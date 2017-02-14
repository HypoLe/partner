package com.boco.eoms.partner.res.mgr;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.partner.res.model.PnrResourceSuccessLog;
import com.boco.eoms.partner.res.util.excelimport.ImportResult;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */ 
public interface PnrResConfigMgr extends CommonGenericService<PnrResConfig> {

	/**
	 * 分页查询
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	public Map getResources(Integer curPage, Integer pageSize,
			final String whereStr);
	/**
	 * 资源导入
	 * @param formFile
	 * @param province
	 * @param specialty
	 * @param creatorId
	 * @return
	 * @throws Exception
	 */
	public ImportResult importFromFile( FormFile formFile,String province,String specialty,String creatorId) throws Exception;  //资源的导入

	
	/**
	 * 资源导出
	 * @param specialty
	 * @param country
	 * @param toClient
	 */
	public void excelExport(String specialty,List<String> country,OutputStream toClient);
	
	/**
	 * 资源批量更新
	 * @param c
	 * @param setWhere
	 * @param whereStr
	 */
	public void updateAllEntity(Class c,String setWhere, String whereStr);
	
	/**
	 * 查询未分配周期和执行对象的资源数
	 */
	public List findUnAssignCycleAndExecuteObject(String whereSql);
	/**
	 * 通过资源的ID找到巡检资源
	 * @param subResId
	 * @return List
	 */
	public List<PnrResConfig> getPnrResConfigByResId(String subResId);
	
	/**
	 * 查询资源总条数
	 */
	public Map<String,Object> totalNumber(Map<String,String> param);
	
	/**
	 * 	 同步资源清查数据
	 	 * @author Wangjun
	 	 * @title: collectResourceInventoryData
	 	 * @date Nov 24, 2015 2:11:17 PM
	 	 * @param param
	 	 * @return Map<String,Object>
	 */
	public Map<String,Object> collectResourceInventoryData(Map<String,String> param);
	
	/**
	 *	往插入成功的条数的日志表中保存数据
	 	 * @author WangJun
	 	 * @title: savePnrResourceSuccessLog
	 	 * @date Jan 6, 2016 4:44:47 PM
	 	 * @param pnrResourceSuccessLog void
	 */
	public void savePnrResourceSuccessLog(PnrResourceSuccessLog pnrResourceSuccessLog);
	
}
