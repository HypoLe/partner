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
import com.boco.eoms.partner.deviceAssess.dao.FtraininfoDao;
import com.boco.eoms.partner.deviceAssess.model.Ftraininfo;

/**
 * <p>
 * Title:pnr_deviceassess_ftrain_info dao的hibernate实现
 * </p>
 * <p>
 * Description:pnr_deviceassess_ftrain_info
 * </p>
 * <p>
 * Sun Sep 26 09:11:03 CST 2010
 * </p>
 *  
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class FtraininfoDaoHibernate extends BaseDaoHibernate implements FtraininfoDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FtraininfoDao#getFtraininfos()
	 *      
	 */
	public List getFtraininfos() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Ftraininfo ftraininfo";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.FtraininfoDao#getFtraininfo(java.lang.String)
	 *
	 */
	public Ftraininfo getFtraininfo(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Ftraininfo ftraininfo where ftraininfo.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Ftraininfo) result.iterator().next();
				} else {
					return new Ftraininfo();
				}
			}
		};
		return (Ftraininfo) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FtraininfoDao#saveFtraininfos(com.boco.eoms.partner.deviceassess.Ftraininfo)
	 *      
	 */
	public void saveFtraininfo(final Ftraininfo ftraininfo) {
		if ((ftraininfo.getId() == null) || (ftraininfo.getId().equals("")))
			getHibernateTemplate().save(ftraininfo);
		else
			getHibernateTemplate().saveOrUpdate(ftraininfo);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FtraininfoDao#removeFtraininfos(java.lang.String)
	 *      
	 */
    public void removeFtraininfo(final String id) {
		getHibernateTemplate().delete(getFtraininfo(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Ftraininfo ftraininfo = this.getFtraininfo(id);
		if(ftraininfo==null){
			return "";
		}
		//TODO 请修改代码
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FtraininfoDao#getFtraininfos(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getFtraininfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Ftraininfo ftraininfo";
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