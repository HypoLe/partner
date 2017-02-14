package com.boco.eoms.partner.serviceArea.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.partner.serviceArea.dao.ILineDao;
import com.boco.eoms.partner.serviceArea.model.Line;

/**
 * <p>
 * Title:线路 dao的hibernate实现
 * </p>
 * <p>
 * Description:服务资源配置 线路
 * </p>
 * <p>
 * Fri Nov 13 10:10:56 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public class LineDaoHibernate extends BaseDaoHibernate implements ILineDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.serviceareamgr.LineDao#getLines()
	 *      
	 */
	public List getLines() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Line line";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}

    /**
	 * 
	 * @see com.boco.eoms.partner.maintenance.CheckDao#getChecks()
	 *      
	 */
	public List listCityOfArea(final String areaId,final String len) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "from TawSystemArea t  where t.areaid like '" + areaId + "%' and length(areaid)="+ len +" ";
				
				List list = getHibernateTemplate().find(hql);
				return list;

			}
		};
		return (List) getHibernateTemplate().execute(callback);		

		
	}

    /**
	 * 
	 * @see com.boco.eoms.partner.maintenance.CheckDao#getChecks()
	 *      
	 */
	public List listCountryOfCity(final String cityId,final String len) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "from TawSystemArea t  where t.areaid like '" + cityId + "%' and length(areaid)="+ len +" ";

				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
		
	}
	
	//二级联动菜单 加载合作伙伴 列表 (根据地市、县区)
	public List listProviderOfCity(final String region,final String city) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
/*				//String hql = "from AreaDeptTree a  where  a.parentNodeId = (select nodeId from AreaDeptTree where  node_type = 'area' and areaId = '" + cityId + "') ";
				String hql = "from AreaDeptTree a  where  a.parentNodeId = (select nodeId from AreaDeptTree where  node_type = 'area' and areaId = '" + cityId + "') ";
				List list = getHibernateTemplate().find(hql);
				return list;
*/				
				
				String queryStr=" select name,id,interface_head_id from pnr_dept t where area_id='"+ region +"' and city='"+ city +"' ";
		
				SQLQuery countQuery = session.createSQLQuery(queryStr);
				
				return countQuery.list();
			
			}
		};
		return (List) getHibernateTemplate().execute(callback);		

	}

	
	//二级联动菜单 加载合作伙伴 列表 (根据地市)
	public List listProviderByRegion(final String region) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				
				String queryStr=" select name from pnr_dept t where area_id='"+ region +"' ";
		
				SQLQuery countQuery = session.createSQLQuery(queryStr);
				
				return countQuery.list();
			
			}
		};
		return (List) getHibernateTemplate().execute(callback);		

	}


	
	/**
	 *
	 * @see com.boco.eoms.partner.serviceareamgr.LineDao#getLine(java.lang.String)
	 *
	 */
	public Line getLine(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Line line where line.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Line) result.iterator().next();
				} else {
					return new Line();
				}
			}
		};
		return (Line) getHibernateTemplate().execute(callback);
	}
	
	
	   /**
	 * 判断线路是否重复
	 * @see com.boco.eoms.partner.servicearea.IPointDao#getPoints()
	 *      
	 */
	public List getLineName(final String lineName) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				
				String queryStr = "from Line line where line.lineName = '"+lineName+"'";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.serviceareamgr.LineDao#saveLines(com.boco.eoms.partner.serviceareamgr.Line)
	 *      
	 */
	public void saveLine(final Line line) {
		
		if ((line.getId() == null) || (line.getId().equals("")))
			getHibernateTemplate().save(line);
		
		else
			getHibernateTemplate().saveOrUpdate(line);
	}

	
	
	/**
	 * 
	 * 判断段落名称是否已经存在
	 * 
	 */
	public Boolean isUnique(final Line line) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer("from Line line");
				queryStr.append(" where line.lineName =:lineName");	

				Query query = session.createQuery(queryStr.toString());
				query.setString("lineName",line.getLineName());
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return Boolean.valueOf(false);
				} else {
					return Boolean.valueOf(true);
				}
			}
		};
		return (Boolean) getHibernateTemplate().execute(callback);
	}
	

	
	
	/**
	 * 
	 * @see com.boco.eoms.partner.serviceareamgr.LineDao#removeLines(java.lang.String)
	 *      
	 */
    public void removeLine(final String id) {
		getHibernateTemplate().delete(getLine(id));
	}

    
    /**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Line line = this.getLine(id);
		if(line==null){
			return "";
		}
		//TODO 请修改代码
		return null;
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.serviceareamgr.LineDao#getLines(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getLines(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Line line where isdel = 0";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;

				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
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
	
}