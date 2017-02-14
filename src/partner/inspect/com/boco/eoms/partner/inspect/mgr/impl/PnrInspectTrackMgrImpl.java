package com.boco.eoms.partner.inspect.mgr.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.jdbc.object.SqlQuery;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectTemplateDao;
import com.boco.eoms.partner.inspect.dao.IPnrInspectTrackDao;
import com.boco.eoms.partner.inspect.mgr.IInspectTemplateMgr;
import com.boco.eoms.partner.inspect.mgr.IPnrInspectTrackMgr;
import com.boco.eoms.partner.inspect.model.InspectTemplate;
import com.boco.eoms.partner.inspect.model.PnrInspectTrack;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;



/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     lee 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 10:09:39 AM 
 */

/**
 * 该 spring 的配置文件在applicationContext-all.xml的最后
 */
public class PnrInspectTrackMgrImpl extends CommonGenericServiceImpl<PnrInspectTrack> 
							implements IPnrInspectTrackMgr {

	private IPnrInspectTrackDao inspectTrackDao;
	public IPnrInspectTrackDao getInspectTrackDao() {
		return inspectTrackDao;
	}
	public void setInspectTrackDao(IPnrInspectTrackDao inspectTrackDao) {
		this.inspectTrackDao = inspectTrackDao;
		this.setCommonGenericDao(inspectTrackDao);
	}
	public String getTimeOnSite(String res_id) {
		return inspectTrackDao.getTimeOnSite(res_id);
	}
//	public String getTimeOnSite(IPnrInspectTrackMgr inspectTrackMgr, String res_id) {
//		Search search = new Search();
//		search.addFilterEqual("planResId", res_id);
//		search.addSortAsc("signTime");//注意,要按时间排序,从小到大,取出第一次定位时间
//		SearchResult<PnrInspectTrack> returnSearch = inspectTrackMgr.searchAndCount(search);
//		List<PnrInspectTrack> returnTrackList = returnSearch.getResult();
//		String firstSignTime = "";
//		String lastSignTime = "";
//		float returnTimeOnSite = 0f;
//		if(null != returnTrackList&& !returnTrackList.isEmpty()){//没有定位记录
//			firstSignTime = returnTrackList.get(0).getSignTime();//第一次定位时间,也是签到时间
//			if(1 == returnTrackList.size()){//如果只有一次签到记录
//				return "";
//			}
//			boolean hasEndTimeOnSite = false;//是否有最后一次签退记录
//			for(int i = 0;i<returnTrackList.size();i++){
//				if("0".equals(returnTrackList.get(i).getStatus())){//如果有,则跳出
//					hasEndTimeOnSite = true;
//					break;
//				}
//				if(!hasEndTimeOnSite&& i == returnTrackList.size()-1){//如果遍历到最后还是没有签退记录,则直接返回
//					return "";
//				}
//			}
//			
//			
//			
//			
//			
//			if("0".equals(returnTrackList.get(returnTrackList.size()-1).getStatus())){//最后一次定位时间,但不一定是签到时间,此时需要判断,取出status=0的那条数据,如果最后一定正好是签退时间
//				for(int i = 0 ;i<returnTrackList.size();i++){
//					if("1".equals(returnTrackList.get(i).getStatus())){
//						firstSignTime = returnTrackList.get(i).getSignTime();
//					}if("1".equals(returnTrackList.get(i).getStatus())&&"0".equals(returnTrackList.get(i+1).getStatus())){//正常情况,有签到和签退
//						
//					}
//					
//					
//				}
//				
//				
//				
//				lastSignTime = returnTrackList.get(returnTrackList.size()-1).getSignTime();
//			}else{//如果不是签退时间,则对定位时间进行遍历,取出最后一次status 0 的时间
//				if(1 == returnTrackList.size()){//如果该用户只签到了一次,即只有一条定位记录,则直接返回,该用户没有签退
//					lastSignTime = "";
//				}else{
//					boolean isEndSign = false;//是否是最后一次标示
//					for(int i = 0;i<returnTrackList.size()-1;i++){//因为第一次判断时为false,此时可以直接去掉最后一条数据,少遍历一次
//						if(isEndSign){
//							break;
//						}
//						if("0".equals(returnTrackList.get(i).getStatus())){
//							isEndSign = true;
//							lastSignTime = returnTrackList.get(i).getSignTime();
//						}
//					}
//				}
//			}
//		}
//		
//		return "";
//	}
	public List getLineByLocation(String sql) {
		return inspectTrackDao.getLineByLocation(sql);
	}
	
	
}
