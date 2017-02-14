package com.boco.eoms.eva.dao.hibernate;

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
import com.boco.eoms.eva.dao.IEvaConfirmDao;
import com.boco.eoms.eva.model.EvaConfirm;

public class EvaConfirmDaoHibernate extends BaseDaoHibernate implements IEvaConfirmDao,
		ID2NameDAO {

	public String id2Name(String id) throws DictDAOException {
		return null;
	}

	public void removeEvaConfirm(EvaConfirm evaConfirm) {
		getHibernateTemplate().delete(evaConfirm);
	}

	public void saveEvaConfirm(EvaConfirm evaConfirm) {
		if (null == evaConfirm.getId() || "".equals(evaConfirm.getId())) {
			getHibernateTemplate().save(evaConfirm);
		} else {
			getHibernateTemplate().merge(evaConfirm);
		}
	}
	
	public Map getTemplateUnConfirms(final Integer curPage,final Integer pageSize,final String userId,final String deptId) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from EvaConfirm evaConfirm where state ='1'  and ((evaConfirm.toOrgId = '"+userId+"' and evaConfirm.toOrgType = 'user') or (evaConfirm.toOrgId = '"+deptId+"' and evaConfirm.toOrgType = 'dept')) ";
				String queryCountStr = "select count(*) " + queryStr;
				String queryOrder = " order by evaConfirm.createTime desc";
				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr+queryOrder);
				query.setFirstResult(pageSize.intValue()
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
	
	public List getTemplateConfirms(final String evaTemplateId) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from EvaConfirm evaConfirm where evaConfirm.evaTemplateId=:evaTemplateId  order by evaConfirm.createTime desc";
				Query query = session.createQuery(queryStr);
				query.setString("evaTemplateId", evaTemplateId);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	
	public EvaConfirm getTemplateConfirmById(final String id) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from EvaConfirm evaConfirm where evaConfirm.id=:id  order by evaConfirm.createTime desc";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (EvaConfirm) result.iterator().next();
				} else {
					return new EvaConfirm();
				}
			}
		};
		return (EvaConfirm) getHibernateTemplate().execute(callback);		
	}	
}
