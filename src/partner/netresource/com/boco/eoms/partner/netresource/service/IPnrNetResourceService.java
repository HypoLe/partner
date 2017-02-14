package com.boco.eoms.partner.netresource.service;

import java.util.List;
import java.util.Map;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 22, 2013 1:55:13 AM
 */
public interface IPnrNetResourceService {
	/**
	 * 同步网络资源到巡检资源
	 * @param synchResultId irms_datasynch_result表id
	 * @param model 网络资源类型
	 */
	public void synchNetResToResConfig(String synchResultId,String model);
	
	/**
	 * 查询数据同步结果
	 * @param id
	 * @return
	 */
	public Map<String,Object> getDatasynchResult(String id);
	
	/**
	 * 网络资源同步统计
	 */
	public List netResourceCount(Integer curPage, Integer pageSize);
}
