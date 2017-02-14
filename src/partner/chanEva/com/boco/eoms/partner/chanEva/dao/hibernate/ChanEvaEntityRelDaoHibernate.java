package com.boco.eoms.partner.chanEva.dao.hibernate;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.chanEva.dao.IChanEvaEntityRelDao;
import com.boco.eoms.partner.chanEva.model.ChanEvaEntityRel;

public class ChanEvaEntityRelDaoHibernate extends BaseDaoHibernate implements
		IChanEvaEntityRelDao {

	public ChanEvaEntityRel getChanEvaEntityRel(String id) {
		ChanEvaEntityRel entityRel = (ChanEvaEntityRel) getHibernateTemplate().get(
				ChanEvaEntityRel.class, id);
		if (null == entityRel) {
			throw new ObjectRetrievalFailureException(ChanEvaEntityRel.class, id);
		}
		return entityRel;
	}

	public void saveChanEvaEntityRel(ChanEvaEntityRel chanEvaEntityRel) {
		if (null == chanEvaEntityRel.getId() || "".equals(chanEvaEntityRel.getId())) {
			getHibernateTemplate().save(chanEvaEntityRel);
		} else {
			getHibernateTemplate().saveOrUpdate(chanEvaEntityRel);
		}
	}

	public ChanEvaEntityRel getChanEvaEntityRelByTemplateId(String templateId) {
		List list = getHibernateTemplate().find(
				"from ChanEvaEntityRel rel where rel.templateId='" + templateId + "'");
		ChanEvaEntityRel chanEvaEntityRel = (ChanEvaEntityRel)list.get(0);
		return chanEvaEntityRel;
	}

}
