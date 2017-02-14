package com.boco.eoms.partner.netresource.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.netresource.dao.IPnrNetResourceDaoJdbc;
import com.boco.eoms.partner.netresource.service.IPnrNetResourceService;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 22, 2013 1:56:30 AM
 */
public class PnrNetResourceServiceImpl implements IPnrNetResourceService {
	private IPnrNetResourceDaoJdbc pnrNetResourceDaoJdbc;

	/**
	 * 同步网络资源到巡检资源
	 * @param synchResultId irms_datasynch_result表id
	 * @param model 网络资源类型
	 */
	public void synchNetResToResConfig(String synchResultId,String model) {
		pnrNetResourceDaoJdbc.synchNetResToResConfig(synchResultId,model);
	}
	
	/**
	 * 查询数据同步结果
	 * @param id
	 * @return
	 */
	public Map<String,Object> getDatasynchResult(String id){
		return pnrNetResourceDaoJdbc.getDatasynchResult(id);
	}
	
	/**
	 * 网络资源同步统计
	 */
	public List netResourceCount(Integer curPage, Integer pageSize){
		return pnrNetResourceDaoJdbc.netResourceCount(curPage,pageSize);
	}
	
	public IPnrNetResourceDaoJdbc getPnrNetResourceDaoJdbc() {
		return pnrNetResourceDaoJdbc;
	}

	public void setPnrNetResourceDaoJdbc(
			IPnrNetResourceDaoJdbc pnrNetResourceDaoJdbc) {
		this.pnrNetResourceDaoJdbc = pnrNetResourceDaoJdbc;
	}

}
