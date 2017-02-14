package com.boco.eoms.partner.baseinfo.dao.hibernate;

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
import com.boco.eoms.partner.baseinfo.dao.IPnrBaseSiteDao;
import com.boco.eoms.partner.baseinfo.model.PnrBaseSite;

/**
 * <p>
 * Title:合作伙伴站址信息管理 dao的hibernate实现
 * </p>
 * <p>
 * Description:站址信息管理
 * </p>
 * <p>
 * Wed Mar 24 14:01:56 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrBaseSiteDaoHibernate extends BaseDaoHibernate implements IPnrBaseSiteDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PnrBaseSiteDao#getPnrBaseSites()
	 *      
	 */
	public List getPnrBaseSites() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrBaseSite pnrBaseSite";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.baseinfo.PnrBaseSiteDao#getPnrBaseSite(java.lang.String)
	 *
	 */
	public PnrBaseSite getPnrBaseSite(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrBaseSite pnrBaseSite where pnrBaseSite.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PnrBaseSite) result.iterator().next();
				} else {
					return new PnrBaseSite();
				}
			}
		};
		return (PnrBaseSite) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PnrBaseSiteDao#savePnrBaseSites(com.boco.eoms.partner.baseinfo.PnrBaseSite)
	 *      
	 */
	public void savePnrBaseSite(final PnrBaseSite pnrBaseSite) {
		if ((pnrBaseSite.getId() == null) || (pnrBaseSite.getId().equals("")))
			getHibernateTemplate().save(pnrBaseSite);
		else
			getHibernateTemplate().saveOrUpdate(pnrBaseSite);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PnrBaseSiteDao#removePnrBaseSites(java.lang.String)
	 *      
	 */
    public void removePnrBaseSite(final String id) {
		getHibernateTemplate().delete(getPnrBaseSite(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		PnrBaseSite pnrBaseSite = this.getPnrBaseSite(id);
		if(pnrBaseSite==null){
			return "";
		}
		//TODO 请修改代码
		return pnrBaseSite.getName();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PnrBaseSiteDao#getPnrBaseSites(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPnrBaseSites(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrBaseSite pnrBaseSite";
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
	
	//二级联动菜单 加载合作伙伴 列表 (列出所在地市的合作伙伴)
	public List listProviderOfCity(final String cityId) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "from AreaDeptTree a  where  a.parentNodeId = (select nodeId from AreaDeptTree where  node_type = 'area' and areaId = '" + cityId + "') ";
				
				List list = getHibernateTemplate().find(hql);
				return list;
			
			}
		};
		return (List) getHibernateTemplate().execute(callback);		

	}
}