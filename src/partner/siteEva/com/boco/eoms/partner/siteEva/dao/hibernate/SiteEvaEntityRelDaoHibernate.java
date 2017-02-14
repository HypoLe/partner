package com.boco.eoms.partner.siteEva.dao.hibernate;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaEntityRelDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaEntityRel;

public class SiteEvaEntityRelDaoHibernate extends BaseDaoHibernate implements
		ISiteEvaEntityRelDao {

	public SiteEvaEntityRel getSiteEvaEntityRel(String id) {
		SiteEvaEntityRel entityRel = (SiteEvaEntityRel) getHibernateTemplate().get(
				SiteEvaEntityRel.class, id); 
		if (null == entityRel) {
			throw new ObjectRetrievalFailureException(SiteEvaEntityRel.class, id);
		}
		return entityRel;
	}

	public void saveSiteEvaEntityRel(SiteEvaEntityRel siteEvaEntityRel) {
		if (null == siteEvaEntityRel.getId() || "".equals(siteEvaEntityRel.getId())) {
			getHibernateTemplate().save(siteEvaEntityRel);
		} else {
			getHibernateTemplate().saveOrUpdate(siteEvaEntityRel);
		}
	}

	public SiteEvaEntityRel getSiteEvaEntityRelByTemplateId(String templateId) {
		List list = getHibernateTemplate().find(
				"from SiteEvaEntityRel rel where rel.templateId='" + templateId + "'");
		SiteEvaEntityRel siteEvaEntityRel = (SiteEvaEntityRel)list.get(0);
		return siteEvaEntityRel;
	}

}
