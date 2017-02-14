package com.boco.eoms.partner.tempTask.dao.hibernate;

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
import com.boco.eoms.partner.tempTask.dao.IPnrTempTaskMainDao;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskMain;

/**
 * <p>
 * Title:合作伙伴临时任务管理 dao的hibernate实现
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理主表信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrTempTaskMainDaoHibernate extends BaseDaoHibernate implements IPnrTempTaskMainDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskMainDao#getPnrTempTaskMains()
	 *      
	 */
	public List getPnrTempTaskMains() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskMain pnrTempTaskMain order by createTime desc";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskMainDao#getPnrTempTaskMain(java.lang.String)
	 *
	 */
	public PnrTempTaskMain getPnrTempTaskMain(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskMain pnrTempTaskMain where pnrTempTaskMain.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PnrTempTaskMain) result.iterator().next();
				} else {
					return new PnrTempTaskMain();
				}
			}
		};
		return (PnrTempTaskMain) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskMainDao#savePnrTempTaskMains(com.boco.eoms.partner.tempTask.PnrTempTaskMain)
	 *      
	 */
	public void savePnrTempTaskMain(final PnrTempTaskMain pnrTempTaskMain) {
		if ((pnrTempTaskMain.getId() == null) || (pnrTempTaskMain.getId().equals("")))
			getHibernateTemplate().save(pnrTempTaskMain);
		else
			getHibernateTemplate().saveOrUpdate(pnrTempTaskMain);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskMainDao#removePnrTempTaskMains(java.lang.String)
	 *      
	 */
    public void removePnrTempTaskMain(final String id) {
		getHibernateTemplate().delete(getPnrTempTaskMain(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		PnrTempTaskMain pnrTempTaskMain = this.getPnrTempTaskMain(id);
		if(pnrTempTaskMain==null){
			return "";
		}
		//TODO 请修改代码
		return pnrTempTaskMain.getTempTaskName();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskMainDao#getPnrTempTaskMains(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPnrTempTaskMains(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskMain pnrTempTaskMain";
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