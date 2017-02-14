package com.boco.eoms.partner.tranEva.dao.hibernate;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.tranEva.dao.ITranEvaEntityRelDao;
import com.boco.eoms.partner.tranEva.model.TranEvaEntityRel;

public class TranEvaEntityRelDaoHibernate extends BaseDaoHibernate implements
		ITranEvaEntityRelDao {

	public TranEvaEntityRel getTranEvaEntityRel(String id) {
		TranEvaEntityRel entityRel = (TranEvaEntityRel) getHibernateTemplate().get(
				TranEvaEntityRel.class, id);
		if (null == entityRel) {
			throw new ObjectRetrievalFailureException(TranEvaEntityRel.class, id);
		}
		return entityRel;
	}

	public void saveTranEvaEntityRel(TranEvaEntityRel tranEvaEntityRel) {
		if (null == tranEvaEntityRel.getId() || "".equals(tranEvaEntityRel.getId())) {
			getHibernateTemplate().save(tranEvaEntityRel);
		} else {
			getHibernateTemplate().saveOrUpdate(tranEvaEntityRel);
		}
	}

	public TranEvaEntityRel getTranEvaEntityRelByTemplateId(String templateId) {
		List list = getHibernateTemplate().find(
				"from TranEvaEntityRel rel where rel.templateId='" + templateId + "'");
		TranEvaEntityRel tranEvaEntityRel = (TranEvaEntityRel)list.get(0);
		return tranEvaEntityRel;
	}

}
