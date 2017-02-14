package com.boco.activiti.partner.process.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boco.activiti.partner.process.dao.IRepairFaultDao;
import com.boco.activiti.partner.process.model.FaultType;
import com.boco.activiti.partner.process.model.SchemeRateRejectModel;
import com.boco.activiti.partner.process.service.IRepairFaultService;
import com.boco.eoms.deviceManagement.common.pojo.CommonSearch;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class RepairFaultServiceImpl extends CommonGenericServiceImpl<FaultType> implements IRepairFaultService{
	private IRepairFaultDao repairFaultDao;
	/**
	 * 地市故障类型统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	@Override
	public Map<String,Object> faultTypeListPage(String startTime,String endTime) {
		Map<String,Object> map=repairFaultDao.faultTypeListPage(startTime,endTime);
		return map;
	}
	/**
	 * 区县故障类型统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	@Override
	public Map<String,Object> repairFaultqxList(String county,String startTime,String endTime) {
		Map<String,Object> map=repairFaultDao.repairFaultqxList(county, startTime,endTime);
		return map;
	}
	/**
	 * 地市非故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	@Override
	public Map<String,Object> nonFaultcsList(String startTime,String endTime) {
		Map<String,Object> map=repairFaultDao.nonFaultcsList(startTime,endTime);
		return map;
	}
	/**
	 * 区县非故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	@Override
	public Map<String,Object> nonFaultqxList(String county,String startTime,String endTime) {
		Map<String,Object> map=repairFaultDao.nonFaultqxList(county, startTime,endTime);
		return map;
	}
	@Override
	public int count(ISearch search) {
		return 0;
	}

	@Override
	public List excelExportToProcess(Search search, String userId,
			String deptId, String queryFlag, String processKey, String flag,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FaultType find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FaultType[] find(String... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FaultType> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map getDataList(Class<FaultType> entryClass, String alias,
			String entrySql, String countSql, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filter getFilterFromExample(FaultType example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filter getFilterFromExample(FaultType example,
			ExampleOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FaultType getReference(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FaultType[] getReferences(String... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAttached(FaultType entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void refresh(FaultType... entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean remove(FaultType entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void remove(FaultType... entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeByIds(String... ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean save(FaultType entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean[] save(FaultType... entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FaultType> search(ISearch search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResult<FaultType> searchAndCount(ISearch search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <RT> SearchResult<RT> searchAndCount(CommonSearch commonSearch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Search searchPrivFilter(Search search, String userId, String deptId,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FaultType searchUnique(ISearch search) {
		// TODO Auto-generated method stub
		return null;
	}
	public IRepairFaultDao getRepairFaultDao() {
		return repairFaultDao;
	}
	public void setRepairFaultDao(IRepairFaultDao repairFaultDao) {
		this.repairFaultDao = repairFaultDao;
	}

	
	
}
