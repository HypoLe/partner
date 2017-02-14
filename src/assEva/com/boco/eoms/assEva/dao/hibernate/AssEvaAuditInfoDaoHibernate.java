package com.boco.eoms.assEva.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.assEva.dao.IAssEvaAuditInfoDao;
import com.boco.eoms.assEva.model.AssEvaAuditInfo;

public class AssEvaAuditInfoDaoHibernate extends BaseDaoHibernate implements
		IAssEvaAuditInfoDao {

	public AssEvaAuditInfo getAssEvaAuditInfo(String id) {
		AssEvaAuditInfo auditInfo = (AssEvaAuditInfo) getHibernateTemplate().get(
				AssEvaAuditInfo.class, id);
		if (null == auditInfo) {
			throw new ObjectRetrievalFailureException(AssEvaAuditInfo.class, id);
		}
		return auditInfo;
	}

	public void saveAssEvaAuditInfo(AssEvaAuditInfo assEvaAuditInfo) {
		if (null == assEvaAuditInfo.getId() || "".equals(assEvaAuditInfo.getId())) {
			getHibernateTemplate().save(assEvaAuditInfo);
		} else {
			getHibernateTemplate().saveOrUpdate(assEvaAuditInfo);
		}
	}

	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		// filter on properties set in the threadHistory
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssEvaAuditInfo";

				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;

				String queryCountStr = "select count(*) " + queryStr;
				// 按时间排序
				queryStr += " order by auditTime desc";

				Integer total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next());
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", total);
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
}
