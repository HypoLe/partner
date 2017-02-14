package com.boco.eoms.partner.assess.AssWeight.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.assess.AssWeight.dao.IAssWeightDao;
import com.boco.eoms.partner.assess.AssWeight.model.AssWeight;

public class AssWeightDaoHibernate extends BaseDaoHibernate implements
		IAssWeightDao{
	
	public AssWeight getWeight(final String area,final String nodeId) {		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "FROM AssWeight as assWeight where assWeight.area = ? and assWeight.nodeId = ?";
				Query query = session.createQuery(queryStr);
				query.setString(0, area);
				query.setString(1, nodeId);
				List list = query.list();
				if (list.iterator().hasNext()) {
					return (AssWeight) list.iterator().next();
				}
				return new AssWeight();
			}
		};
		return (AssWeight) getHibernateTemplate().execute(callback);
    }

	public void removeWeight(AssWeight weight) {
		getHibernateTemplate().delete(weight);
	}

	public void saveWeight(AssWeight weight) {
		if (null == weight.getId() || "".equals(weight.getId())) {
			getHibernateTemplate().save(weight);
		} else {
			getHibernateTemplate().saveOrUpdate(weight);
		}
	}

	public void updateWeight(AssWeight weight) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(weight);
	}
	
}
