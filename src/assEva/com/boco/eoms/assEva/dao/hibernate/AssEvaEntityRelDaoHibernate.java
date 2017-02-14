package com.boco.eoms.assEva.dao.hibernate;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.assEva.dao.IAssEvaEntityRelDao;
import com.boco.eoms.assEva.model.AssEvaEntityRel;

public class AssEvaEntityRelDaoHibernate extends BaseDaoHibernate implements
		IAssEvaEntityRelDao {

	public AssEvaEntityRel getAssEvaEntityRel(String id) {
		AssEvaEntityRel entityRel = (AssEvaEntityRel) getHibernateTemplate().get(
				AssEvaEntityRel.class, id);
		if (null == entityRel) {
			throw new ObjectRetrievalFailureException(AssEvaEntityRel.class, id);
		}
		return entityRel;
	}

	public void saveAssEvaEntityRel(AssEvaEntityRel assEvaEntityRel) {
		if (null == assEvaEntityRel.getId() || "".equals(assEvaEntityRel.getId())) {
			getHibernateTemplate().save(assEvaEntityRel);
		} else {
			getHibernateTemplate().saveOrUpdate(assEvaEntityRel);
		}
	}

	public AssEvaEntityRel getAssEvaEntityRelByTemplateId(String templateId) {
		List list = getHibernateTemplate().find(
				"from AssEvaEntityRel rel where rel.templateId='" + templateId + "'");
		AssEvaEntityRel assEvaEntityRel = (AssEvaEntityRel)list.get(0);
		return assEvaEntityRel;
	}

}
