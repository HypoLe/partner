package com.boco.eoms.eva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.eva.dao.IEvaTemplateTempDao;
import com.boco.eoms.eva.model.EvaTemplateTemp;
import com.boco.eoms.eva.util.EvaConstants;

public class EvaTemplateTempDaoHibernate extends BaseDaoHibernate implements
		IEvaTemplateTempDao, ID2NameDAO {

	public EvaTemplateTemp getTemplate(String id) {
		EvaTemplateTemp template = (EvaTemplateTemp) getHibernateTemplate().get(
				EvaTemplateTemp.class, id);
		if (null == template) {
			// throw new ObjectRetrievalFailureException(EvaTemplateTemp.class, id);
			template = new EvaTemplateTemp();
		}
		return template;
	}
	
	public EvaTemplateTemp getEvaTemplateTemp(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from EvaTemplateTemp evaTemplateTemp where evaTemplateTemp.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (EvaTemplateTemp) result.iterator().next();
				} else {
					return new EvaTemplateTemp();
				}
			}
		};
		return (EvaTemplateTemp) getHibernateTemplate().execute(callback);
	}
	
	public String id2Name(String id) {
		String templateName = "";
		EvaTemplateTemp template = getTemplate(id);
		if (null != template.getId() && !"".equals(template.getId())) {
			if (null != template.getTemplateName()
					&& !"".equals(template.getTemplateName())) {
				templateName = template.getTemplateName();
			} else {
				templateName = EvaConstants.NODE_NONAME;
			}
		} else {
			templateName = EvaConstants.NODE_NONAME;
		}
		return templateName;
	}

	public void removeEvaTemplateTemp(EvaTemplateTemp template) {
		getHibernateTemplate().delete(template);
	}

	public void saveEvaTemplateTemp(EvaTemplateTemp template) {
		if (null == template.getId() || "".equals(template.getId())) {
			getHibernateTemplate().save(template);
		} else {
			getHibernateTemplate().merge(template);
		}
	}
}
