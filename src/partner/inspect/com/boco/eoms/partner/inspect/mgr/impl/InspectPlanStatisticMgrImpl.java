package com.boco.eoms.partner.inspect.mgr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.eoms.partner.inspect.dao.IInspectPlanStatisticDaoJdbc;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanStatisticMgr;
import com.boco.eoms.partner.inspect.model.InspectStatisticArea;
import com.boco.eoms.partner.inspect.model.InspectStatisticPartner;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromNew;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromSpecialty;

/** 
 * Description: 巡检统计 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 1, 2012 3:51:01 PM 
 */
public class InspectPlanStatisticMgrImpl implements IInspectPlanStatisticMgr {
	private IInspectPlanStatisticDaoJdbc inspectPlanStatisticDaoJdbc;
	
	/**
	 * 统计当前月地市巡检情况
	 * @return
	 */
	public List<InspectStatisticArea> findInspectPlanStatisticCity(){
		return inspectPlanStatisticDaoJdbc.findInspectPlanStatisticCity();
	}
	
	/**
	 * 统计历史地市巡检情况
	 * @param year
	 * @param month
	 * @return
	 */
	public List<InspectStatisticArea> findInspectPlanStatisticCityHis(String year,String month){
		return inspectPlanStatisticDaoJdbc.findInspectPlanStatisticCityHis(year, month);
	}
	
	/**
	 * 统计历史地市巡检情况 改造
	 * @param year
	 * @param month
	 * @return
	 */
	public List<InspectPlanResCountFromNew> findInspectPlanStatisticCityHisNew(boolean isNowMonth,String countType,
			String areaId,String year,String month,String newMonth){
		return inspectPlanStatisticDaoJdbc.findInspectPlanStatisticCityHisNew(isNowMonth,countType,areaId,year, month,newMonth);
	}
	
//	@Override
	public Map<String,Object> findInspectPlanStatisticSpecialtyNew(String inspectState,String year ,String month ,String countType,
			String areaId ,String specialty,String person,int firstIndex,int lastIndex) {
		
		Map<String,Object> map = new HashMap<String,Object>();
    	List<InspectPlanResCountFromSpecialty> r = new ArrayList<InspectPlanResCountFromSpecialty>();
    	List<InspectPlanResCountFromSpecialty> rlist = new ArrayList<InspectPlanResCountFromSpecialty>();
        r = inspectPlanStatisticDaoJdbc.findInspectPlanStatisticSpecialtyNew(inspectState,year , month ,countType,areaId,specialty,person);
        
//      分页获取数据
        int size = r.size();
        int maxSize =0;
        int begin = firstIndex;
        //当取的值，为最大时
        if(size>=lastIndex){
        	maxSize =lastIndex;
        }else{
        	maxSize=size;
        }
       
        for(int index=begin;index<maxSize;index++){
    	   rlist.add(r.get(index));
        } 
        map.put("size",size);
       	        
        map.put("list",rlist);
        
        
        return map;
	}
	/**
	 * 统计当前月区县巡检情况
	 * @param city 地市
	 * @return
	 */
	public List<InspectStatisticArea> findInspectPlanStatisticCountry(String city){
		return inspectPlanStatisticDaoJdbc.findInspectPlanStatisticCountry(city);
	}
	
	/**
	 * 统计历史区县巡检情况
	 * @param year
	 * @param month
	 * @param city 地市
	 * @return
	 */
	public List<InspectStatisticArea> findInspectPlanStatisticCountryHis(String year,String month,String city){
		return inspectPlanStatisticDaoJdbc.findInspectPlanStatisticCountryHis(year, month,city);
	}

	public IInspectPlanStatisticDaoJdbc getInspectPlanStatisticDaoJdbc() {
		return inspectPlanStatisticDaoJdbc;
	}

	public void setInspectPlanStatisticDaoJdbc(
			IInspectPlanStatisticDaoJdbc inspectPlanStatisticDaoJdbc) {
		this.inspectPlanStatisticDaoJdbc = inspectPlanStatisticDaoJdbc;
	}
	
	/**
	 * 删除当前地市区县表的所有统计数据
	 */
	public void deleteStatisticAreaData(){
		inspectPlanStatisticDaoJdbc.deleteStatisticAreaData();
	}
	
	/**
	 * 移动当前地市区县表的统计数据到历史表
	 */
	public void moveStatisticAreaDataToHis(){
		inspectPlanStatisticDaoJdbc.moveStatisticAreaDataToHis();
	}
	/**
	 * 按地市采集数据
	 * @param list
	 * @param year
	 * @param month
	 */
	public void saveStatisticCityData(final List<Map<String,String>> list,String year,String month){
		inspectPlanStatisticDaoJdbc.saveStatisticCityData(list, year, month);
	}
	
	/**
	 * 按区县采集数据
	 * @param list
	 * @param year
	 * @param month
	 */
	public void saveStatisticCountryData(List<Map<String,String>> list,String year,String month,String city){
		inspectPlanStatisticDaoJdbc.saveStatisticCountryData(list, year, month, city);
	}

	/**
	 * 统计当月总代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticHeadPnr(){
		return inspectPlanStatisticDaoJdbc.findInspectPlanStatisticHeadPnr();
	}
	
	/**
	 * 统计历史总代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticHeadPnrHis(String year,String month){
		return inspectPlanStatisticDaoJdbc.findInspectPlanStatisticHeadPnrHis(year,month);
	}

	/**
	 * 统计当月地市代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticCityPnr(String hearPartnerId){
		return inspectPlanStatisticDaoJdbc.findInspectPlanStatisticCityPnr(hearPartnerId);
	}
	
	/**
	 * 统计历史地市代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticCityPnrHis(String hearPartnerId,String year,String month){
		return inspectPlanStatisticDaoJdbc.findInspectPlanStatisticCityPnrHis(hearPartnerId,year, month);
	}
	
	/**
	 * 统计当月区县代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticCountryPnr(String cityPartnerId){
		return inspectPlanStatisticDaoJdbc.findInspectPlanStatisticCountryPnr(cityPartnerId);
	}
	
	/**
	 * 统计历史区县代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticCountryPnrHis(String cityPartnerId,String year,String month){
		return inspectPlanStatisticDaoJdbc.findInspectPlanStatisticCountryPnrHis(cityPartnerId,year, month);
	}
	
	/**
	 * 删除当前总、地市、区县表的所有统计数据
	 */
	public void deleteStatisticPartnerData(){
		inspectPlanStatisticDaoJdbc.deleteStatisticPartnerData();
	}
	
	/**
	 * 移动当前总、地市、区县表的统计数据到历史表
	 */
	public void moveStatisticPartnerDataToHis(){
		inspectPlanStatisticDaoJdbc.moveStatisticPartnerDataToHis();
	}
	/**
	 * 采集所有总公司巡检数据
	 * @param year
	 * @param month
	 */
	public void saveStatisticHearPartnerData(final List<Map<String,String>> list,String year,String month){
		inspectPlanStatisticDaoJdbc.saveStatisticHearPartnerData(list, year, month);
	}
	
	/**
	 * 采集所有地市公司巡检数据
	 * @param partnerList
	 * @param year
	 * @param month
	 * @param parentPartnerId
	 */
	public void saveStatisticCityPartnerData(List<Map<String,String>> partnerList,String year,String month,String parentPartnerId){
		inspectPlanStatisticDaoJdbc.saveStatisticCityPartnerData(partnerList, year, month,parentPartnerId);
	}
	/**
	 * 采集所有区县公司巡检数据
	 * @param partnerList
	 * @param year
	 * @param month
	 * @param parentPartnerId
	 */
	public void saveStatisticCountryPartnerData(List<Map<String,String>> partnerList,String year,String month,String parentPartnerId){
		inspectPlanStatisticDaoJdbc.saveStatisticCountryPartnerData(partnerList, year, month,parentPartnerId);
	}
	
	/**
	 * 查询所有总代维公司ID
	 * @return
	 */
	public List<Map<String,String>> findAllHeadPartnerId(){
		return inspectPlanStatisticDaoJdbc.findAllHeadPartnerId();
	}
	
	/**
	 * 查询所有子代维公司
	 * @param parentPartnerId 上级代维公司ID
	 * @return
	 */
	public List<Map<String,String>> findAllSubPartner(String parentPartnerId){
		return inspectPlanStatisticDaoJdbc.findAllSubPartner(parentPartnerId);
	}
}
