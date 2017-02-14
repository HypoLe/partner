package com.boco.eoms.partner.tranEva.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.tranEva.dao.ITranEvaAuditInfoDao;
import com.boco.eoms.partner.tranEva.model.TranEvaAuditInfo;

public class TranEvaAuditInfoDaoHibernate extends BaseDaoHibernate implements
		ITranEvaAuditInfoDao {

	public TranEvaAuditInfo getTranEvaAuditInfo(String id) {
		TranEvaAuditInfo auditInfo = (TranEvaAuditInfo) getHibernateTemplate().get(
				TranEvaAuditInfo.class, id);
		if (null == auditInfo) {
			throw new ObjectRetrievalFailureException(TranEvaAuditInfo.class, id);
		}
		return auditInfo;
	}

	public void saveTranEvaAuditInfo(TranEvaAuditInfo tranEvaAuditInfo) {
		if (null == tranEvaAuditInfo.getId() || "".equals(tranEvaAuditInfo.getId())) {
			getHibernateTemplate().save(tranEvaAuditInfo);
		} else {
			getHibernateTemplate().saveOrUpdate(tranEvaAuditInfo);
		}
	}

	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		// filter on properties set in the threadHistory
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TranEvaAuditInfo";

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
