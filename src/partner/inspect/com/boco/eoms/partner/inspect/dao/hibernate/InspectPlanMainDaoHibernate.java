package com.boco.eoms.partner.inspect.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.inspect.dao.IInspectPlanMainDao;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 10:07:43 AM 
 */
public class InspectPlanMainDaoHibernate extends  CommonGenericDaoImpl<InspectPlanMain, String> implements IInspectPlanMainDao{
	/**
	 * lee
	 * 查询该用户所在小组的计划数量
	 */
	@SuppressWarnings("unchecked")
	public Map getUserCheckInfo(String deptId){
		Session session = getSession();
		SQLQuery queryAllPlan,queryNotCompletePlan = null;
		ArrayList<Integer> returnNotCompletePlanList = null,returnAllPlanList=null;
		HashMap<String, Integer> returnMap = new HashMap<String, Integer>();
		if("admin".equals(deptId)){
			queryAllPlan = session.createSQLQuery("select count(*) as num from pnr_inspect_plan_main").addScalar("num",Hibernate.INTEGER);
			returnAllPlanList = (ArrayList<Integer>) queryAllPlan.list();
			returnMap.put("planNum", returnAllPlanList.get(0));
			returnMap.put("planNotCompleteNum", 0);
			return returnMap;
		}else{
			queryAllPlan = session.createSQLQuery("select count(*) as num from pnr_inspect_plan_main where dept_mag_id like '"+deptId+"%' ").addScalar("num",Hibernate.INTEGER);
			queryNotCompletePlan = session.createSQLQuery("select count(*) as num from pnr_inspect_plan_main where dept_mag_id like '"+deptId+"%' and res_num <> res_Done_Num").addScalar("num",Hibernate.INTEGER);
			
			returnNotCompletePlanList = (ArrayList<Integer>) queryNotCompletePlan.list();
			returnAllPlanList = (ArrayList<Integer>) queryAllPlan.list();
		}
		if(null != returnAllPlanList && !returnAllPlanList.isEmpty()&&null != returnNotCompletePlanList && !returnNotCompletePlanList.isEmpty()){
			returnMap.put("planNum", returnAllPlanList.get(0));
			returnMap.put("planNotCompleteNum", returnNotCompletePlanList.get(0));
		}else{
			returnMap.put("planNum", 0);
			returnMap.put("planNotCompleteNum", 0);
		}
		return returnMap;
	}
	
	/**
     * 查询所有的计划
     * @param offset
     * @param pagesize
     * @param whereStr
     * @return
     */
    public List<Object> getAllPlanMain(int offset,int pagesize,String whereStr){
    	List list1 = new ArrayList();
    	List list2 = new ArrayList();
    	String sql1 = "select count(*) from InspectPlanMain "+whereStr;
    	String sql2 = "from InspectPlanMain "+whereStr;
    	Integer total = Integer.parseInt(this.getSession().createQuery(sql1).uniqueResult().toString());
    	list1 = this.getSession().createQuery(sql2).setFirstResult(offset).setMaxResults(pagesize).list();
    	list2.add(total);
    	list2.add(list1);
    	return list2;
    }
	
}
