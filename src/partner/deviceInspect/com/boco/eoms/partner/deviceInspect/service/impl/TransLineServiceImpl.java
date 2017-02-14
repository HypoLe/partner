package com.boco.eoms.partner.deviceInspect.service.impl;

import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.partner.deviceInspect.dao.ITransLineDao;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectMapping;
import com.boco.eoms.partner.deviceInspect.service.ITransLineService;
import com.boco.eoms.partner.netresource.service.impl.EomsServiceImpl;
import com.googlecode.genericdao.search.SearchResult;

public class TransLineServiceImpl extends EomsServiceImpl  implements ITransLineService{
	private ITransLineDao transLineDao;

	public ITransLineDao getTransLineDao() {
		return transLineDao;
	}

	public void setTransLineDao(ITransLineDao transLineDao) {
		this.transLineDao = transLineDao;
		this.setEomsDao(transLineDao);
	}

	/**
	 * 产生巡检任务（和以前的IInspectPlanMgr中的generateInspectPlanRes一样）
	 * @param cycle 周期
	 * @param city  地市     
	 * @param cycleStartTime 周期开始时间
	 * @param cycleEndTime   周期结束时间   
	 * @param createTime     资源产生日期   
	 */
	public void generateInspectPlanRes(String cycle,String city,
			String cycleStartTime,String cycleEndTime,String createTime) {
		this.transLineDao.generateInspectPlanRes(cycle, city,cycleStartTime,cycleEndTime,createTime);
	}
	
	/**
	 * 产生巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public void generateInspectPlanItem(String city,String createTime){
		this.transLineDao.generateInspectPlanItem(city,createTime);
	}
	
	

	public SearchResult test() {
		this.setPersistentClass(PnrInspectMapping.class);
		EomsSearch eomsSearch = new EomsSearch();
		SearchResult searchResult = this.searchAndCount(eomsSearch);
		return searchResult;
	}
	
	/**
	 * 产生突发巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public String generateBurstInspectPlanRes(String ids) {
		String createTime = this.transLineDao.generateBurstInspectPlanRes(ids);
		return createTime;
	}
	
	/**
	 * 产生突发巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public String generateBurstInspectPlanRes(String ids,String planStartTime,String planEndTime) {
		String createTime = this.transLineDao.generateBurstInspectPlanRes(ids,planStartTime,planEndTime);
		return createTime;
	}

	/**
	 * 产生突发巡检任务
	 * @param ids
	 * @return
	 */
	public void generateBurstInspectPlanItem(String createTime) {
		this.transLineDao.generateBurstInspectPlanItem(createTime);
	}
	
	/**
	 * 生成线路巡检(元任务和巡检项)
	 */
	public void generateBurstTransLine(String ids) {
		String createTime = generateBurstInspectPlanRes(ids);
		generateBurstInspectPlanItem(createTime);
	}
	
	/**
	 * 生成线路巡检(元任务和巡检项)
	 */
	public void generateBurstTransLine(String ids,String planStartTime,String planEndTime) {
		String createTime = generateBurstInspectPlanRes(ids,planStartTime,planEndTime);
		generateBurstInspectPlanItem(createTime);
	}

}
