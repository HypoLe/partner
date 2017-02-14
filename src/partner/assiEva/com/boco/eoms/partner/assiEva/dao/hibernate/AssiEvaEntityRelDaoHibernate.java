package com.boco.eoms.partner.assiEva.dao.hibernate;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaEntityRelDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaEntityRel;

public class AssiEvaEntityRelDaoHibernate extends BaseDaoHibernate implements
		IAssiEvaEntityRelDao {

	public AssiEvaEntityRel getAssiEvaEntityRel(String id) {
		AssiEvaEntityRel entityRel = (AssiEvaEntityRel) getHibernateTemplate().get(
				AssiEvaEntityRel.class, id);
		if (null == entityRel) {
			throw new ObjectRetrievalFailureException(AssiEvaEntityRel.class, id);
		}
		return entityRel;
	}

	public void saveAssiEvaEntityRel(AssiEvaEntityRel assiEvaEntityRel) {
		if (null == assiEvaEntityRel.getId() || "".equals(assiEvaEntityRel.getId())) {
			getHibernateTemplate().save(assiEvaEntityRel);
		} else {
			getHibernateTemplate().saveOrUpdate(assiEvaEntityRel);
		}
	}

	public AssiEvaEntityRel getAssiEvaEntityRelByTemplateId(String templateId) {
		List list = getHibernateTemplate().find(
				"from AssiEvaEntityRel rel where rel.templateId='" + templateId + "'");
		AssiEvaEntityRel assiEvaEntityRel = (AssiEvaEntityRel)list.get(0);
		return assiEvaEntityRel;
	}

}
