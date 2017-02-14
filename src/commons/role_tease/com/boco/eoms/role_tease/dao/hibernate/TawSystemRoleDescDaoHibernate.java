package com.boco.eoms.role_tease.dao.hibernate;

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
import com.boco.eoms.role_tease.dao.TawSystemRoleDescDao;
import com.boco.eoms.role_tease.model.TawSystemRoleDesc;

/**
 * <p>
 * Title:角色说明 dao的hibernate实现
 * </p>
 * <p>
 * Description:角色说明
 * </p>
 * <p>
 * Fri Aug 07 11:13:20 CST 2009
 * </p>
 * 
 * @author fengshaohong
 * @version 3.5
 * 
 */
public class TawSystemRoleDescDaoHibernate extends BaseDaoHibernate implements TawSystemRoleDescDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.role_tease.TawSystemRoleDescDao#getTawSystemRoleDescs()
	 *      
	 */
	public List getTawSystemRoleDescs() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawSystemRoleDesc tawSystemRoleDesc";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.role_tease.TawSystemRoleDescDao#getTawSystemRoleDesc(java.lang.String)
	 *
	 */
	public TawSystemRoleDesc getTawSystemRoleDesc(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawSystemRoleDesc tawSystemRoleDesc where tawSystemRoleDesc.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (TawSystemRoleDesc) result.iterator().next();
				} else {
					return new TawSystemRoleDesc();
				}
			}
		};
		return (TawSystemRoleDesc) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.role_tease.TawSystemRoleDescDao#saveTawSystemRoleDescs(com.boco.eoms.role_tease.TawSystemRoleDesc)
	 *      
	 */
	public void saveTawSystemRoleDesc(final TawSystemRoleDesc tawSystemRoleDesc) {
		if ((tawSystemRoleDesc.getId() == null) || (tawSystemRoleDesc.getId().equals("")))
			getHibernateTemplate().save(tawSystemRoleDesc);
		else
			getHibernateTemplate().saveOrUpdate(tawSystemRoleDesc);
	}

	/**
	 * 
	 * @see com.boco.eoms.role_tease.TawSystemRoleDescDao#removeTawSystemRoleDescs(java.lang.String)
	 *      
	 */
    public void removeTawSystemRoleDesc(final String id) {
		getHibernateTemplate().delete(getTawSystemRoleDesc(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		TawSystemRoleDesc tawSystemRoleDesc = this.getTawSystemRoleDesc(id);
		if(tawSystemRoleDesc==null){
			return "";
		}
		//TODO 请修改代码
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.role_tease.TawSystemRoleDescDao#getTawSystemRoleDescs(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getTawSystemRoleDescs(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawSystemRoleDesc tawSystemRoleDesc";
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
	 *     通过roleId得到TawSystemRoleDesc
	 * @see com.boco.eoms.role_tease.TawSystemRoleDescDao#getTawSystemRoleDesc(java.lang.String)
	 *
	 */
	public TawSystemRoleDesc getTawSystemRoleDescByRoleId(final long roleId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawSystemRoleDesc tawSystemRoleDesc where tawSystemRoleDesc.roleId=:roleId";
				Query query = session.createQuery(queryStr);
				query.setLong("roleId", roleId);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (TawSystemRoleDesc) result.iterator().next();
				} else {
					return new TawSystemRoleDesc();
				}
			}
		};
		return (TawSystemRoleDesc) getHibernateTemplate().execute(callback);
	}
}