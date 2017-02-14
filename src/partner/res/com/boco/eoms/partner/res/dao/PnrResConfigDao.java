package com.boco.eoms.partner.res.dao;

import java.util.Map;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.partner.res.model.PnrResConfig;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012 
 * Company:     BOCO
 * @author:     liaojiming 
 * @version:    1.0 
 * Create at:   2012/7/10 
 */

public interface PnrResConfigDao extends CommonGenericDao<PnrResConfig, String> {

	/**
	* 分页取列表
	* @param curPage 当前页
	* @param pageSize 每页显示条数
	* @param whereStr where条件
	* @return map中total为条数,result(list) curPage页的记录
	*/
	public Map getResources(Integer curPage, Integer pageSize,
			final String whereStr);
	
	
	
	
	
	/**
	 * 批量更新
	 * @param c
	 * @param setWhere
	 * @param whereStr
	 */
	
	public void updateAllEntity(Class c,String setWhere,String whereStr);
}
