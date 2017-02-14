package com.boco.eoms.partner.deviceAssess.dao.hibernate;

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
import com.boco.eoms.partner.deviceAssess.dao.PeventinfoDao;
import com.boco.eoms.partner.deviceAssess.model.Peventinfo;

/**
 * <p>
 * Title:peventinfo dao的hibernate实现
 * </p>
 * <p>
 * Description:peventinfo
 * </p>
 * <p>
 * Sat Sep 25 10:35:07 CST 2010
 * </p>
 * 
 * @author zhangxuesong 
 * @version 1.0
 * 
 */
public class PeventinfoDaoHibernate extends BaseDaoHibernate implements PeventinfoDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.PeventinfoDao#getPeventinfos()
	 *      
	 */
	public List getPeventinfos() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Peventinfo peventinfo";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.PeventinfoDao#getPeventinfo(java.lang.String)
	 *
	 */
	public Peventinfo getPeventinfo(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Peventinfo peventinfo where peventinfo.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Peventinfo) result.iterator().next();
				} else {
					return new Peventinfo();
				}
			}
		};
		return (Peventinfo) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.PeventinfoDao#savePeventinfos(com.boco.eoms.partner.deviceassess.Peventinfo)
	 *      
	 */
	public void savePeventinfo(final Peventinfo peventinfo) {
		if ((peventinfo.getId() == null) || (peventinfo.getId().equals("")))
			getHibernateTemplate().save(peventinfo);
		else
			getHibernateTemplate().saveOrUpdate(peventinfo);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.PeventinfoDao#removePeventinfos(java.lang.String)
	 *      
	 */
    public void removePeventinfo(final String id) {
		getHibernateTemplate().delete(getPeventinfo(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Peventinfo peventinfo = this.getPeventinfo(id);
		if(peventinfo==null){
			return "";
		}
		//TODO 请修改代码
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.PeventinfoDao#getPeventinfos(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPeventinfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Peventinfo peventinfo";
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
				map.put("total", Integer.valueOf(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
}