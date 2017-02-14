package com.boco.eoms.partner.net.dao.hibernate;

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
import com.boco.eoms.partner.net.dao.IGridDao;
import com.boco.eoms.partner.net.model.Gride;

/**
 * <p>
 * Title:网格 dao的hibernate实现
 * </p>
 * <p>
 * Description:网格
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0 
 * 
 */
public class GridDaoHibernate extends BaseDaoHibernate implements IGridDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.net.IGridDao#getGrids()
	 *      
	 */
	public List getGrids() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Gride grid";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
    /**
	 * 
	 * @see com.boco.eoms.partner.net.IGridDao#getByGridName(java.lang.String)
	 *      
	 */
	public List getGridsByGridName( final String gridName ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Gride grid where grid.gridName = '"+gridName+"'";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
    /**
	 * 
	 * @see com.boco.eoms.partner.net.IGridDao#getGridsByWhere(java.lang.String)
	 *      
	 */
	public List getGridsByWhere( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Gride grid ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
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
	 *
	 * @see com.boco.eoms.partner.net.IGridDao#getGrid(java.lang.String)
	 *
	 */
	public Gride getGrid(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Gride grid where grid.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Gride) result.iterator().next();
				} else {
					return new Gride();
				}
			}
		};
		return (Gride) getHibernateTemplate().execute(callback);
	}
	public Gride getGridNumber(final String gridNumber) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Gride grid where grid.gridNumber=:gridNumber";
				Query query = session.createQuery(queryStr);
				query.setString("gridNumber", gridNumber);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Gride) result.iterator().next();
				} else {
					return new Gride();
				}
			}
		};
		return (Gride) getHibernateTemplate().execute(callback);
	}
	/**
	 * 
	 * @see com.boco.eoms.partner.net.IGridDao#saveGrids(com.boco.eoms.partner.net.Grid)
	 *      
	 */
	public void saveGrid(final Gride grid) {
		if ((grid.getId() == null) || (grid.getId().equals("")))
			getHibernateTemplate().save(grid);
		else
			getHibernateTemplate().saveOrUpdate(grid);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.net.IGridDao#removeGrids(java.lang.String)
	 *      
	 */
    public void removeGrid(final String id) {
		getHibernateTemplate().delete(getGrid(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String gridNumber) throws DictDAOException {
		Gride grid = this.getGrid(gridNumber);
		if(grid==null){
			return "";
		}
		return grid.getGridName();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.net.IGridDao#getGrids(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getGrids(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Gride grid";
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
	
	
 	/**
	 * 抽查 基站巡检 (福建的基站即广西的网格)
	 * 
	 * @see com.boco.eoms.partner.net.ISiteDao#getSites(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getCycSite(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Gride grid ,Main main";
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
	

	
	
	/**
     * 根据id批量删除
     * @param id 主键
     * 
     */
    public void removeGrids(final String[] ids) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				//String queryStr = "update Grid grid set grid.isDel='1' where grid.id in (:ids)";
				//批量物理删除
				String queryStr = "delete Grid grid  where grid.id in (:ids)";
				Query query = session.createQuery(queryStr);
				query.setParameterList("ids", ids);
				int ret = query.executeUpdate();
				return new Integer(ret);
			}
		};
		
		getHibernateTemplate().execute(callback);
	}
    
    /**
	 * 
	 * @see com.boco.eoms.partner.net.ISiteDao#getSites(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getSites(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from StationPoint site";
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