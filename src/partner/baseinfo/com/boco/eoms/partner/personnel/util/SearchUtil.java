package com.boco.eoms.partner.personnel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import base.dao.Nop3GenericDaoImpl;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.partner.netresource.service.IEomsService;
	/**
 * <p>
 * Title: 分页查询
 * </p>
 * <p>
 * Description: 分页查询
 * </p>
 * <p>
 * Jul 31, 2012 3:45:07 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class SearchUtil<T>{
	/**
	 * 实体Class
	 */
	private Class<T> entryClass;
	/**
	 * 查询结果 别名
	 */
	private String alias;
	/**
	 *  实体结果集 sql语句
	 */
	private String entrySql;
	/**
	 * 总记录数 sql语句
	 */
	private String countSql;
	
	public SearchUtil() {
	}
	public SearchUtil(Class<T> entryClass,String alias,String entrySql,String countSql) {
		this.entryClass = entryClass;
		this.alias = alias;
		this.entrySql = entrySql;
		this.countSql = countSql;
	}
 /**
  * 
  * @param pageIndex 当前页数
  * @return
  */
	public  PageData<T> getDataList(int pageIndex){
		@SuppressWarnings("rawtypes")
//		Nop3GenericDaoImpl nop3Dao = (Nop3GenericDaoImpl) ApplicationContextHolder.getInstance().getBean("nop3GenericDao");
//	 	Session session = nop3Dao.getSessionFactory().openSession();
//	 	final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
//	//	final Integer pageSize =1;
//	 	//取得总记录数
//		SQLQuery queryCount = session.createSQLQuery(this.countSql);
//		queryCount.addScalar("count",Hibernate.INTEGER);
//		final   int  totalCount = Integer.parseInt(queryCount.list().get(0).toString());
//		//查询结果
//		   SQLQuery queryEntry = session.createSQLQuery(this.entrySql);
//		 	queryEntry.addEntity(this.alias,this.entryClass);
//			PageData<T> p = new PageData<T>(pageIndex, pageSize, totalCount);
//			if (totalCount < 1) {
//				p.setList(new ArrayList<T>());
//				return p;
//			}
//			queryEntry.setFirstResult(p.getFirstResult());
//			queryEntry.setMaxResults(p.getPageSize());
//			@SuppressWarnings("unchecked")
//			List<T>  list=queryEntry.list();
//			p.setList(list);
//			session.close();
//			return p;
		IEomsService eomsService = (IEomsService) ApplicationContextHolder.getInstance().getBean("eomsService");
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
	 	Map map = eomsService.getDataList(this.entryClass, this.alias, this.entrySql, this.countSql, pageIndex, pageSize);
	 	
		int totalCount = StaticMethod.nullObject2int(map.get("totalCount"),0);
		PageData pageData= new PageData<T>(pageIndex, pageSize, totalCount);
		pageData.setTotalCount(totalCount);
		if(map.containsKey("list")){
			List list = (List)map.get("list");
			pageData.setList(list);
		}
		return pageData;
	}

}
