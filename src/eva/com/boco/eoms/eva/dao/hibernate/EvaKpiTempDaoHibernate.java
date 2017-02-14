package com.boco.eoms.eva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.eva.dao.IEvaKpiTempDao;
import com.boco.eoms.eva.model.EvaKpiTemp;

public class EvaKpiTempDaoHibernate extends BaseDaoHibernate implements IEvaKpiTempDao,
		ID2NameDAO {


	public void removeKpiTemp(EvaKpiTemp kpi) {
		getHibernateTemplate().delete(kpi);
	}

	public void saveKpiTemp(EvaKpiTemp kpi) {
		if (kpi.getId() == null || "".equals(kpi.getId())) {
			getHibernateTemplate().save(kpi);
		} else {
			getHibernateTemplate().merge(kpi);
		}
	}
	
	public List getEvaKpiTemps(final String evaTemplateId){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from EvaKpiTemp evaKpiTemp where evaKpiTemp.evaTemplateId=:evaTemplateId ";
				Query query = session.createQuery(queryStr);
				query.setString("evaTemplateId", evaTemplateId);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	
	public String id2Name(String id) {
		String kpiName = "";
//		EvaKpi kpi = getKpi(id);
//		if (null != kpi.getId() && !"".equals(kpi.getId())) {
//			if (null != kpi.getKpiName() && !"".equals(kpi.getKpiName())) {
//				kpiName = kpi.getKpiName();
//			} else {
//				kpiName = EvaConstants.NODE_NONAME;
//			}
//		} else {
//			kpiName = EvaConstants.NODE_NONAME;
//		}
		return kpiName;
	}

}
