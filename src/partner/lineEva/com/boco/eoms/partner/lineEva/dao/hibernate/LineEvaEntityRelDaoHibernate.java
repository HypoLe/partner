package com.boco.eoms.partner.lineEva.dao.hibernate;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.lineEva.dao.ILineEvaEntityRelDao;
import com.boco.eoms.partner.lineEva.model.LineEvaEntityRel;

public class LineEvaEntityRelDaoHibernate extends BaseDaoHibernate implements
		ILineEvaEntityRelDao {

	public LineEvaEntityRel getLineEvaEntityRel(String id) {
		LineEvaEntityRel entityRel = (LineEvaEntityRel) getHibernateTemplate().get(
				LineEvaEntityRel.class, id);
		if (null == entityRel) {
			throw new ObjectRetrievalFailureException(LineEvaEntityRel.class, id);
		}
		return entityRel;
	}

	public void saveLineEvaEntityRel(LineEvaEntityRel lineEvaEntityRel) {
		if (null == lineEvaEntityRel.getId() || "".equals(lineEvaEntityRel.getId())) {
			getHibernateTemplate().save(lineEvaEntityRel);
		} else {
			getHibernateTemplate().saveOrUpdate(lineEvaEntityRel);
		}
	}

	public LineEvaEntityRel getLineEvaEntityRelByTemplateId(String templateId) {
		List list = getHibernateTemplate().find(
				"from LineEvaEntityRel rel where rel.templateId='" + templateId + "'");
		LineEvaEntityRel lineEvaEntityRel = (LineEvaEntityRel)list.get(0);
		return lineEvaEntityRel;
	}

}
