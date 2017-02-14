package com.boco.eoms.partner.eva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.eva.dao.IPnrEvaWeightDao;
import com.boco.eoms.partner.eva.model.PnrEvaWeight;

public class PnrEvaWeightDaoHibernate extends BaseDaoHibernate implements
		IPnrEvaWeightDao{
	
	public PnrEvaWeight getWeight(final String area,final String nodeId) {		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "FROM PnrEvaWeight as pnrEvaWeight where pnrEvaWeight.area = ? and pnrEvaWeight.nodeId = ?";
				Query query = session.createQuery(queryStr);
				query.setString(0, area);
				query.setString(1, nodeId);
				List list = query.list();
				if (list.iterator().hasNext()) {
					return (PnrEvaWeight) list.iterator().next();
				}
				return new PnrEvaWeight();
			}
		};
		return (PnrEvaWeight) getHibernateTemplate().execute(callback);
    }

	public void removeWeight(PnrEvaWeight weight) {
		getHibernateTemplate().delete(weight);
	}

	public void saveWeight(PnrEvaWeight weight) {
		if (null == weight.getId() || "".equals(weight.getId())) {
			getHibernateTemplate().save(weight);
		} else {
			getHibernateTemplate().saveOrUpdate(weight);
		}
	}

	public void updateWeight(PnrEvaWeight weight) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(weight);
	}
	
}
