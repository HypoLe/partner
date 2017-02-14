package com.boco.eoms.partner.serviceArea.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.partner.serviceArea.dao.IPointDao;
import com.boco.eoms.partner.serviceArea.model.Point;

/**
 * <p>
 * Title:数据点 dao的hibernate实现
 * </p>
 * <p>
 * Description:服务资源配置 数据点
 * </p>
 * <p>
 * Mon Nov 23 11:36:10 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public class PointDaoHibernate extends BaseDaoHibernate implements IPointDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.servicearea.IPointDao#getPoints()
	 *      
	 */
	public List getPoints() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Point point";
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
	
	
	
	
    /**
	 * 判断数据点是否重复
	 * @see com.boco.eoms.partner.servicearea.IPointDao#getPoints()
	 *      
	 */
	public List getPointName(final String pointName) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				
				String queryStr = "from Point point where point.pointName = '"+pointName+"'";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}

	
	
	/**
	 *
	 * @see com.boco.eoms.partner.servicearea.IPointDao#getPoint(java.lang.String)
	 *
	 */
	public Point getPoint(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Point point where point.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Point) result.iterator().next();
				} else {
					return new Point();
				}
			}
		};
		return (Point) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.IPointDao#savePoints(com.boco.eoms.partner.servicearea.Point)
	 *      
	 */
	public void savePoint(final Point point) {
		if ((point.getId() == null) || (point.getId().equals("")))
			getHibernateTemplate().save(point);
		else
			getHibernateTemplate().saveOrUpdate(point);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.IPointDao#removePoints(java.lang.String)
	 *      
	 */
    public void removePoint(final String id) {
		getHibernateTemplate().delete(getPoint(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Point point = this.getPoint(id);
		if(point==null){
			return "";
		}
		//TODO 请修改代码
		return null;
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.IPointDao#getPoints(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPoints(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Point point where isdel=0";
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