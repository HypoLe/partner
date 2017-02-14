package com.boco.eoms.partner.inspect.dao.hibernate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.inspect.model.PnrInspectTrack;
import com.boco.eoms.partner.inspect.util.InspectUtils;

/** 
 * Description:  巡检轨迹DAO实现
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Lee 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 10:07:43 AM 
 */
public class PnrInspectTrackDaoHibernate extends  CommonGenericDaoImpl<com.boco.eoms.partner.inspect.model.PnrInspectTrack, String> implements com.boco.eoms.partner.inspect.dao.IPnrInspectTrackDao{

	public String getTimeOnSite(String res_id) {
//		Query query = this.getSession().createQuery("from PnrInspectTrack ");
//		this.getSession().createSQLQuery(arg0)
		String sql = "select id,plan_res_id,sign_time,sign_longitude,sign_latitude,sign_state,error_range,status from pnr_inspect_track where substr(sign_time,1,10)=extend(today,year to day) and plan_res_id ='"+res_id+"' order by sign_Time asc";
		Session session = this.getSession();
		Query query = 
			session
			.createSQLQuery(sql).addScalar("id", Hibernate.STRING)
			.addScalar("plan_res_id", Hibernate.STRING).addScalar("sign_time", Hibernate.STRING)
			.addScalar("sign_longitude", Hibernate.STRING).addScalar("sign_latitude", Hibernate.STRING)
			.addScalar("sign_state", Hibernate.STRING).addScalar("error_range", Hibernate.STRING).addScalar("status", Hibernate.STRING)
			;
		List<PnrInspectTrack> returnTrackList  = maping(query.list());
//		session.close();
		String firstSignTime = "";
		String lastSignTime = "";
		if(null != returnTrackList&& !returnTrackList.isEmpty()){//没有定位记录
			if(1 == returnTrackList.size()){//如果只有一次签到记录
				return "0";
			}
			//returnTrackList.size()-1最后一次打点记录returnTrackList.size()-2最后一次签到记录,正常情况
			if("0".equals(returnTrackList.get(returnTrackList.size()-1).getStatus())&&"1".equals(returnTrackList.get(returnTrackList.size()-2).getStatus())){//如果有,则为正常签到签退
				lastSignTime = returnTrackList.get(returnTrackList.size()-1).getSignTime();
				firstSignTime = returnTrackList.get(returnTrackList.size()-2).getSignTime();
			}else{
				return "0";
			}
			if(InspectUtils.isCurrentDay(StaticMethod.getCurrentDateTime(),lastSignTime)&&InspectUtils.isCurrentDay(StaticMethod.getCurrentDateTime(),firstSignTime)){//判断是否是同一天打点如果是,则计算时间差
				return InspectUtils.getTimeDifference(firstSignTime,lastSignTime)+"";
			}else{
				return "0";
			}
			
		}
		return "0";
	}
	
	public List<PnrInspectTrack> maping(List qeryList){
		PnrInspectTrack inspectTrack;
		List<PnrInspectTrack> returnList = new ArrayList<PnrInspectTrack>();
		if(null == qeryList || qeryList.isEmpty()){
			return returnList;
		}
		for(int i = 0 ;i<qeryList.size();i++){
			inspectTrack = new PnrInspectTrack();
			Object[] obj = (Object[]) qeryList.get(i);
			inspectTrack.setId(obj[0]+"");
			inspectTrack.setPlanResId(obj[1]+"");
			inspectTrack.setSignTime(obj[2]+"");
			inspectTrack.setSignLongitude(obj[3]+"");
			inspectTrack.setSignLatitude(obj[4]+"");
			inspectTrack.setSignState(obj[5]+"");
			inspectTrack.setErrorRange(Double.parseDouble(obj[6]+""));
			inspectTrack.setStatus(obj[7]+"");
			returnList.add(inspectTrack);
		}
			return returnList;
	}
	
	public List getLineByLocation(String sql) {
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.list();
	}
}