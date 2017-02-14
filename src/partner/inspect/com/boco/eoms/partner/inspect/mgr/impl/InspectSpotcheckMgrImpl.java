package com.boco.eoms.partner.inspect.mgr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.partner.inspect.dao.IInspectSpotcheckDaoJdbc;
import com.boco.eoms.partner.inspect.dao.ISpotcheckItemDao;
import com.boco.eoms.partner.inspect.dao.ISpotcheckResDao;
import com.boco.eoms.partner.inspect.dao.ISpotcheckTemplateDao;
import com.boco.eoms.partner.inspect.dao.ISpotcheckTemplateItemDao;
import com.boco.eoms.partner.inspect.mgr.IInspectSpotcheckMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.SpotcheckItem;
import com.boco.eoms.partner.inspect.model.SpotcheckRes;
import com.boco.eoms.partner.inspect.model.SpotcheckTemplate;
import com.boco.eoms.partner.inspect.model.SpotcheckTemplateItem;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 23, 2012 12:48:25 PM 
 */
public class InspectSpotcheckMgrImpl implements IInspectSpotcheckMgr{
	private ISpotcheckTemplateDao spotcheckTemplateDao;
	private ISpotcheckTemplateItemDao spotcheckTemplateItemDao;
	private ISpotcheckResDao spotcheckResDao;
	private ISpotcheckItemDao spotcheckItemDao;
	private IInspectSpotcheckDaoJdbc inspectSpotcheckDaoJdbc;
	
	public List<Map<String,Object>> findSpotcheckTemplateList(String inspectTemplateId,String year,String month){
		return inspectSpotcheckDaoJdbc.findSpotcheckTemplateList(inspectTemplateId,year,month);
	}
	
	/**
	 * 根据ID获取巡检抽检
	 * @param id
	 * @return
	 */
	public SpotcheckTemplate getSpotcheckTemplate(String id){
		return spotcheckTemplateDao.find(id);
	}
	
	/**
	 * 保存抽检模板
	 * @param spotcheckTemplate
	 * @param itemIdList
	 */
	public void saveSpotcheckTemplate(SpotcheckTemplate spotcheckTemplate,List<String> itemIdList){
		
		if(StringUtils.isEmpty(spotcheckTemplate.getId())){//新增
			String spotcheckTemplateId = "";
			try {
				spotcheckTemplateId = UUIDHexGenerator.getInstance().getID();
			} catch (Exception e) {
				e.printStackTrace();
			}
			spotcheckTemplate.setId(spotcheckTemplateId);
		}else{//修改
			this.deleteSpotcheckTemplateItem(spotcheckTemplate.getId());
		}
		spotcheckTemplateDao.save(spotcheckTemplate);
		for (String inspectTemplateItemId : itemIdList) {
			SpotcheckTemplateItem spotcheckTemplateItem = new SpotcheckTemplateItem();
			spotcheckTemplateItem.setInspectTemplateItemId(inspectTemplateItemId);
			spotcheckTemplateItem.setSpotcheckTemplateId(spotcheckTemplate.getId());
			spotcheckTemplateItemDao.save(spotcheckTemplateItem);
		}
	}
	
	public String saveSpotcheckTemplate(SpotcheckTemplate spotcheckTemplate){
		spotcheckTemplateDao.save(spotcheckTemplate);
		String id=spotcheckTemplate.getId();
		return id;
	}
	
	/**
	 * 查询抽检模板已关联以及未关联的巡检项
	 * @param inspectTemplateId
	 * @param spotcheckTemplateId
	 * @return
	 */
	public List<Map<String,String>> findInspectPlanItemList(String inspectTemplateId,String spotcheckTemplateId){
		return inspectSpotcheckDaoJdbc.findInspectPlanItemList(inspectTemplateId, spotcheckTemplateId);
	}
	
	/**
	 * 查询出当前模板下的所有模板大项
	 * @param inspectTemplateId
	 * @return
	 */
	public List<String> findInspectTemplateAllBigItem(String inspectTemplateId,String year,String month){
		
		return inspectSpotcheckDaoJdbc.findInspectTemplateAllBigItem(inspectTemplateId,year,month);
	}
	
	/**
	 * 查询所有的抽检模板项
	 * @param templateBigItemId
	 * @param inspectTemplateId
	 * @return
	 */
	public List getAllInspectTemplateItem(String templateBigItemId,String inspectTemplateId,String year,String month){
		
		return inspectSpotcheckDaoJdbc.getAllInspectTemplateItem(templateBigItemId,inspectTemplateId,year,month);
	}
	
	/**
	 * 执行sql
	 * @param sql
	 */
	public void excuteSql(String sql){
		inspectSpotcheckDaoJdbc.excuteSql(sql);
	}
	
	/**
	 * 删除抽检模板
	 * @param spotcheckTemplateId
	 */
	public void deleteSpotcheckTemplate(String spotcheckTemplateId){
		spotcheckTemplateDao.removeById(spotcheckTemplateId);
		this.deleteSpotcheckTemplateItem(spotcheckTemplateId);
	}
	
	/**
	 * 删除抽检模板关联的抽检模板项
	 * @param spotcheckTemplateId
	 */
	public void deleteSpotcheckTemplateItem(String spotcheckTemplateId){
		String hql = "delete from SpotcheckTemplateItem where spotcheckTemplateId='"+spotcheckTemplateId+"'";
		spotcheckTemplateDao.bulkUpdateByHql(hql);
	}
	
	/**
	 * 查询抽检模板可分配的分数
	 * @param inspectTemplateId
	 * @param spotcheckTemplateId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Float getSpotcheckTemplateUsableScore(String inspectTemplateId,String spotcheckTemplateId){
		String hql = "select sum(score) from SpotcheckTemplate where templateId='"+inspectTemplateId+"' ";
		if(!StringUtils.isEmpty(spotcheckTemplateId)){
			hql += " and id<>'"+spotcheckTemplateId+"'";
		}
		List scoreList = spotcheckTemplateDao.findByHql(hql);
		if(scoreList.get(0) != null){
			return 100 - Float.valueOf(scoreList.get(0).toString());
		}
		return 100f;
	}
	
	/**
	 * 巡检资源抽检列表
	 * @param planId
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public PageModel findInspectPlanResSpotcheckList(String planId,int offset,int pagesize,InspectPlanRes res){
		return inspectSpotcheckDaoJdbc.findInspectPlanResSpotcheckList(planId, offset, pagesize,res);
	}
	/**
	 * 巡检资源抽检列表
	 * @param planId
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public PageModel findInspectPlanResSpotcheckListMobile(String planId,int offset,int pagesize,InspectPlanRes res,HashMap<String, String> queryMap){
		return inspectSpotcheckDaoJdbc.findInspectPlanResSpotcheckListMobile(planId, offset, pagesize,res,queryMap);
	}
	
	/**
	 * 巡检项抽检列表
	 * @param inspectTemplateId
	 * @param inspectPlanResId
	 * @return
	 */
	public List<Map<String,Object>> findInspectPlanItemSpotcheckList(String inspectTemplateId,String inspectPlanResId){
		return inspectSpotcheckDaoJdbc.findInspectPlanItemSpotcheckList(inspectTemplateId, inspectPlanResId);
	}
	
	/**
	 * 保存巡检抽检资源
	 * @param spotcheckRes
	 */
	public void saveSpotcheckRes(SpotcheckRes spotcheckRes){
		spotcheckResDao.save(spotcheckRes);
	}
	
	/**
	 * 保存巡检抽检资源项
	 * @param spotcheckItem
	 */
	public void saveSpotcheckItem(SpotcheckItem spotcheckItem){
		spotcheckItemDao.save(spotcheckItem);
	}
	
	/**
	 * 获取巡检抽检资源
	 * @param spotcheckRes
	 */
	public SpotcheckRes getSpotcheckRes(String id){
		return spotcheckResDao.find(id);
	}
	
	/**
	 * 获取巡检抽检资源项
	 * @param spotcheckItem
	 */
	public SpotcheckItem getSpotcheckItem(String id){
		return spotcheckItemDao.find(id);
	}
	
	/**
	 * 获得模板历史表
	 * @param year
	 * @param month
	 * @return
	 */
	public List getInspectTemplateHisList(String year,String month,int offset,int pagesize,String whereStr){
		return inspectSpotcheckDaoJdbc.getInspectTemplateHisList(year, month,offset,pagesize,whereStr);
	}
	
	public ISpotcheckTemplateDao getSpotcheckTemplateDao() {
		return spotcheckTemplateDao;
	}
	public void setSpotcheckTemplateDao(ISpotcheckTemplateDao spotcheckTemplateDao) {
		this.spotcheckTemplateDao = spotcheckTemplateDao;
	}
	public ISpotcheckTemplateItemDao getSpotcheckTemplateItemDao() {
		return spotcheckTemplateItemDao;
	}
	public void setSpotcheckTemplateItemDao(
			ISpotcheckTemplateItemDao spotcheckTemplateItemDao) {
		this.spotcheckTemplateItemDao = spotcheckTemplateItemDao;
	}
	public IInspectSpotcheckDaoJdbc getInspectSpotcheckDaoJdbc() {
		return inspectSpotcheckDaoJdbc;
	}
	public void setInspectSpotcheckDaoJdbc(
			IInspectSpotcheckDaoJdbc inspectSpotcheckDaoJdbc) {
		this.inspectSpotcheckDaoJdbc = inspectSpotcheckDaoJdbc;
	}

	public ISpotcheckResDao getSpotcheckResDao() {
		return spotcheckResDao;
	}

	public void setSpotcheckResDao(ISpotcheckResDao spotcheckResDao) {
		this.spotcheckResDao = spotcheckResDao;
	}

	public ISpotcheckItemDao getSpotcheckItemDao() {
		return spotcheckItemDao;
	}

	public void setSpotcheckItemDao(ISpotcheckItemDao spotcheckItemDao) {
		this.spotcheckItemDao = spotcheckItemDao;
	}
	
	
}
