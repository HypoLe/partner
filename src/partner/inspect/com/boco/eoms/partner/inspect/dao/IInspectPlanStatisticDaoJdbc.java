package com.boco.eoms.partner.inspect.dao;

import java.util.List;
import java.util.Map;

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
 * Create at:   Aug 1, 2012 3:49:33 PM 
 */
public interface IInspectPlanStatisticDaoJdbc {
	
	/**
	 * 统计当前月地市巡检情况
	 * @return
	 */
	public List<InspectStatisticArea> findInspectPlanStatisticCity();
	
	/**
	 * 统计历史地市巡检情况
	 * @param year
	 * @param month
	 * @return
	 */
	public List<InspectStatisticArea> findInspectPlanStatisticCityHis(String year,String month);
	
	/**
	 * 统计历史地市巡检情况 改造
	 * @param year
	 * @param month
	 * @return
	 */
	public List<InspectPlanResCountFromNew> findInspectPlanStatisticCityHisNew(boolean isNowMonth,String countType,
			String areaId,String year,String month,String newMonth);
	
	/**
	 * 统计巡检
	 * 
	 * @param countType
	 *            统计类型
	 * @return
	 */
	public List<InspectPlanResCountFromSpecialty> findInspectPlanStatisticSpecialtyNew(String inspectState,String year , String month ,String countType,
			String areaId,String specialty,String person);
	/**
	 * 统计当前月区县巡检情况
	 * @param city 地市
	 * @return
	 */
	public List<InspectStatisticArea> findInspectPlanStatisticCountry(String city);
	
	/**
	 * 统计历史区县巡检情况
	 * @param year
	 * @param month
	 * @param city 地市
	 * @return
	 */
	public List<InspectStatisticArea> findInspectPlanStatisticCountryHis(String year,String month,String city);

	/**
	 * 删除当前地市区县表的所有统计数据
	 */
	public void deleteStatisticAreaData();
	
	/**
	 * 移动当前地市区县表的统计数据到历史表
	 */
	public void moveStatisticAreaDataToHis();
	/**
	 * 按地市采集数据
	 * @param list
	 * @param year
	 * @param month
	 */
	public void saveStatisticCityData(List<Map<String,String>> list,String year,String month);
	/**
	 * 按区县采集数据
	 * @param list
	 * @param year
	 * @param month
	 */
	public void saveStatisticCountryData(List<Map<String,String>> list,String year,String month,String city);
	
	/**
	 * 统计当月总代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticHeadPnr();
	
	/**
	 * 统计历史总代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticHeadPnrHis(String year,String month);

	/**
	 * 统计当月地市代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticCityPnr(String headPartnerId);
	
	/**
	 * 统计历史地市代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticCityPnrHis(String headPartnerId,String year,String month);
	
	/**
	 * 统计当月区县代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticCountryPnr(String cityPartnerId);
	
	/**
	 * 统计历史区县代维公司巡检情况
	 * @return
	 */
	public List<InspectStatisticPartner> findInspectPlanStatisticCountryPnrHis(String cityPartnerId,String year,String month);
	
	/**
	 * 删除当前总、地市、区县表的所有统计数据
	 */
	public void deleteStatisticPartnerData();
	
	/**
	 * 移动当前总、地市、区县表的统计数据到历史表
	 */
	public void moveStatisticPartnerDataToHis();
	/**
	 * 采集所有总公司巡检数据
	 * @param year
	 * @param month
	 */
	public void saveStatisticHearPartnerData(final List<Map<String,String>> list,String year,String month);
	
	/**
	 * 采集所有地市公司巡检数据
	 * @param partnerList
	 * @param year
	 * @param month
	 * @param parentPartnerId
	 */
	public void saveStatisticCityPartnerData(final List<Map<String,String>> partnerList,
			String year,String month,String parentPartnerId);
	/**
	 * 采集所有区县公司巡检数据
	 * @param partnerList
	 * @param year
	 * @param month
	 * @param parentPartnerId
	 */
	public void saveStatisticCountryPartnerData(final List<Map<String,String>> partnerList,
			String year,String month,String parentPartnerId);
	
	/**
	 * 查询所有总代维公司ID
	 * @return
	 */
	public List<Map<String,String>> findAllHeadPartnerId();
	/**
	 * 查询所有子代维公司
	 * @param parentPartnerId 上级代维公司ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> findAllSubPartner(String parentPartnerId);
}
