package com.boco.eoms.partner.inspect.dao.hibernate;


import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.inspect.dao.IInspectPlanHisDao;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 10, 2012 3:33:16 PM 
 */
public class InspectPlanHisDaoHibernate extends BaseDaoHibernate implements IInspectPlanHisDao {

	/**
	 * 查询巡检计划历史列表
	 */
    @SuppressWarnings("unchecked")
	public PageModel findInspectPlanMainHisList(String whereSql, int offset, int pagesize){
    	Session session = getSession();
    	String selectSql = "select * from pnr_inspect_plan_main_his ";
    	String sql = selectSql + whereSql + "order by create_time desc";
    	SQLQuery query = session.createSQLQuery(sql).addEntity(InspectPlanMain.class);
    	query.setFirstResult(offset);
		query.setMaxResults(pagesize);
    	List<InspectPlanMain> list = query.list();
    	
    	int total = 0;
    	String countSql = "select count(*) as mainHisCount from pnr_inspect_plan_main_his " + whereSql;
    	SQLQuery countQuery = session.createSQLQuery(countSql).addScalar("mainHisCount",Hibernate.INTEGER);
    	List countList = countQuery.list();
    	if(countList.size() > 0){
    		total = StaticMethod.nullObject2int(countList.get(0));
    	}
    	
    	PageModel pageModel = new PageModel();
    	pageModel.setDatas(list);
    	pageModel.setTotal(total);
    	return pageModel;
    }
    
    @SuppressWarnings("unchecked")
	public InspectPlanMain getInspectPlanMainHis(String id){
    	Session session = getSession();
    	String sql = "select * from pnr_inspect_plan_main_his where id='"+id+"'";
    	SQLQuery query = session.createSQLQuery(sql).addEntity(InspectPlanMain.class);
    	List<InspectPlanMain> inspectPlanMainHisList = query.list();
    	if(inspectPlanMainHisList.size() > 0){
    		return inspectPlanMainHisList.get(0);
    	}
    	return null;
    }
    
    @SuppressWarnings("unchecked")
	public PageModel findInspectPlanResHisList(String whereSql, int offset, int pagesize){
    	Session session = getSession();
    	String selectSql = "select * from pnr_inspect_plan_res_his ";
    	String sql = selectSql + whereSql + "order by inspect_State desc";
    	SQLQuery query = session.createSQLQuery(sql).addEntity(InspectPlanRes.class);
    	query.setFirstResult(offset);
		query.setMaxResults(pagesize);
    	List<InspectPlanRes> list = query.list();
    	
    	int total = 0;
    	String countSql = "select count(*) as resHisCount from pnr_inspect_plan_res_his " + whereSql;
    	SQLQuery countQuery = session.createSQLQuery(countSql).addScalar("resHisCount",Hibernate.INTEGER);
    	List countList = countQuery.list();
    	if(countList.size() > 0){
    		total = StaticMethod.nullObject2int(countList.get(0));
    	}
    	
    	PageModel pageModel = new PageModel();
    	pageModel.setDatas(list);
    	pageModel.setTotal(total);
    	return pageModel;
    }
    
}
