package com.boco.eoms.partner.siteEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaOrgDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaOrg;

public class SiteEvaOrgDaoHibernate extends BaseDaoHibernate implements ISiteEvaOrgDao {

	public SiteEvaOrg getSiteEvaOrg(String id) {
		SiteEvaOrg org = (SiteEvaOrg) getHibernateTemplate().get(SiteEvaOrg.class, id);
		if (null == org) {
			throw new ObjectRetrievalFailureException(SiteEvaOrg.class, id);
		}
		return org;
	} 

	public void saveSiteEvaOrg(SiteEvaOrg siteEvaOrg) {
		if (null == siteEvaOrg.getId() || "".equals(siteEvaOrg.getId())) {
			getHibernateTemplate().save(siteEvaOrg);
		} else {
			getHibernateTemplate().saveOrUpdate(siteEvaOrg);
		}
	}

	public void removeSiteEvaOrg(SiteEvaOrg siteEvaOrg) {
		getHibernateTemplate().delete(siteEvaOrg);
	}

	// 根据模板ID获取组织列表
	public List getOrgsByTempletId(String templateId) {
		return getHibernateTemplate().find(
				"from SiteEvaOrg org where org.templateId='" + templateId + "'");
	}

	// 根据模板ID删除相关组织
	public void removeOrgOfTemplate(String templateId) {
		final String hql = "delete from SiteEvaOrg siteEvaOrg where siteEvaOrg.templateId ='"
				+ templateId + "'";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createQuery(hql);
				query.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}

	// 根据模板ID和动作类型删除相关组织
	public void removeOrgOfTemplateAndActionType(String templateId,
			String actionType) {
		final String hql = "delete from SiteEvaOrg siteEvaOrg where siteEvaOrg.templateId ='"
				+ templateId + "' and siteEvaOrg.actionType='" + actionType + "'";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createQuery(hql);
				query.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}

	// 根据条件获取模板列表
	public List getTempletByOrgId(String where) {
		return getHibernateTemplate().find("from SiteEvaOrg org " + where);
	}
}
