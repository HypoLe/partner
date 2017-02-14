package com.boco.eoms.partner.inspect.dao.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.inspect.dao.IInspectPlanResDao;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;

import com.boco.eoms.partner.res.model.PnrResConfig;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 16, 2012 10:45:22 AM 
 */
public class InspectPlanResDaoHibernate extends BaseDaoHibernate implements IInspectPlanResDao{
	
	@SuppressWarnings("unchecked")
	public Map findInspectPlanResList(final Integer curPage, final Integer pageSize,final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InspectPlanRes res";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;
				int total = ((Integer) session.createQuery(queryCountStr).iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
//				if(0!=total){
//				query.setFirstResult(0);
//				query.setMaxResults(total);
//				}
				List<InspectPlanRes> result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
	
	/**
	 * 资源查询   config表  2013-08-30
	 */
	@SuppressWarnings("unchecked")
	public Map findResourceList(final Integer curPage,final Integer pageSize,
		final String whereStr) {
		// TODO Auto-generated method stub
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrResConfig  res ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;
				int total = ((Integer) session.createQuery(queryCountStr).iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
//				if(0!=total){
//				query.setFirstResult(0);
//				query.setMaxResults(total);
//				}
				List<PnrResConfig> result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> queryInspectPlanRes(Map<String, String> queryMap,final Integer curPage, final Integer pageSize) {
		final StringBuilder builder = new StringBuilder();
		builder.append(" where ");
		if(null != queryMap){
			Set<Map.Entry<String, String>> set = queryMap.entrySet();
	        for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
	            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
	            builder.append(entry.getKey()).append(" = '").append(entry.getValue()).append("' ");
	            if(it.hasNext()){
	            	builder.append("  and  ");
	            }
	        }
			
		}
		System.out.println("whereStr     "+builder.toString());
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InspectPlanRes res";
				if (builder.toString() != null && builder.toString().length() > 0)
					queryStr += builder.toString();
				String queryCountStr = "select count(*) " + queryStr;
				int total = ((Integer) session.createQuery(queryCountStr).iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 查询巡检计划已经关联的巡检资源数量
	 * @param planId
	 * @return
	 */
	public Integer getPlanResAssignCount(String planId){
		String hql = "select count(*) from InspectPlanRes where planId='"+planId+"'";
		return (Integer)getHibernateTemplate().find(hql).get(0);
	}
	public Integer getPlanResCountByHql(String hql){
		return (Integer)getHibernateTemplate().find(hql).get(0);
	}
	public Integer getExceptionResCount(String planId) {
		String hql = "select count(*) from InspectPlanRes where planId='"+planId+"' and (exceptionFlag ='0'  or  exceptionFlag='-1')";
		return (Integer)getHibernateTemplate().find(hql).get(0);
	}
	/**
	 * 查询巡检资源与变更资源列表
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map findPlanResWithChangeList(final Integer curPage, final Integer pageSize,final String whereStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				HibernateCallback callback = new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						String queryStr = "from InspectPlanRes res";
						if (whereStr != null && whereStr.length() > 0)
							queryStr += whereStr;
						String queryCountStr = "select count(*) " + queryStr;
						int total = ((Integer) session.createQuery(queryCountStr).iterate().next()).intValue();
						queryStr += " order by burstFlag desc,changeState desc,resName desc";
						Query query = session.createQuery(queryStr);
						query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
						query.setMaxResults(pageSize.intValue());
						List result = query.list();
						HashMap map = new HashMap();
						map.put("total", new Integer(total));
						map.put("result", result);
						return map;
					}
				};
				return (Map) getHibernateTemplate().execute(callback);
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 获取巡检资源与变更情况
	 * @param resId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPlanResWithChange(String resId){
		String hql = "select res,chg from InspectPlanRes res,InspectPlanResChange chg where res.id=chg.planResId and res.id='"+resId+"'";
		return super.find(hql);
	}

	/**
	 * 查询已完成的巡检资源
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Integer> queryTotalAndHasDoneNum(String plan_id){
		Map<String,Integer> map = new HashMap<String, Integer>();
		Session session = getSession();
		String currentDate  =  StaticMethod.getCurrentDateTime();
		//计划完成数量
		String planCount = "select count(*) as planCount from pnr_inspect_plan_res where   plan_id='"+plan_id+"' and plan_end_time<'"+currentDate+"'";
		
		Query planCountQuery = session.createSQLQuery(planCount).addScalar("planCount",Hibernate.INTEGER);
		
		List planCountList = planCountQuery.list();
		if(!planCountList.isEmpty()&&planCountList.size()>0){
			map.put("planCount", Integer.parseInt(StaticMethod.nullObject2int(planCountList.get(0))+""));
		}else{
			map.put("planCount", 0);
		}
		return map;
	}

	public Map<String, Integer> queryHasDoneNum(String plan_id) {
		Map<String,Integer> map = new HashMap<String, Integer>();
		Session session = getSession();
		String currentDate  =  StaticMethod.getCurrentDateTime();
		//计划完成数量
		String resHasCheckCount = "select count(*) as planCount from pnr_inspect_plan_res where   plan_id='"+plan_id+"' and inspect_State = 1";
		
		Query resHasCheckCountQuery = session.createSQLQuery(resHasCheckCount).addScalar("planCount",Hibernate.INTEGER);
		
		List planCountList = resHasCheckCountQuery.list();
		if(!planCountList.isEmpty()&&planCountList.size()>0){
			map.put("hasCheckDoneNum", Integer.parseInt(StaticMethod.nullObject2int(planCountList.get(0))+""));
		}else{
			map.put("hasCheckDoneNum", 0);
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map getInspectPlanMainDetailList(final Integer curPage, final Integer pageSize,final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String selectSql="select res ";
				String queryStr = "from InspectPlanRes res ,InspectPlanMain main";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;
				int total = ((Integer) session.createQuery(queryCountStr).iterate().next()).intValue();
				selectSql+=queryStr;
				Query query = session.createQuery(selectSql);
				query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
//				if(0!=total){
//				query.setFirstResult(0);
//				query.setMaxResults(total);
//				}
				List<InspectPlanRes> result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
}
