package com.boco.eoms.partner.chanEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.chanEva.dao.IChanEvaOrgDao;
import com.boco.eoms.partner.chanEva.model.ChanEvaOrg;

public class ChanEvaOrgDaoHibernate extends BaseDaoHibernate implements IChanEvaOrgDao {

	public ChanEvaOrg getChanEvaOrg(String id) {
		ChanEvaOrg org = (ChanEvaOrg) getHibernateTemplate().get(ChanEvaOrg.class, id);
		if (null == org) {
			throw new ObjectRetrievalFailureException(ChanEvaOrg.class, id);
		}
		return org;
	}

	public void saveChanEvaOrg(ChanEvaOrg chanEvaOrg) {
		if (null == chanEvaOrg.getId() || "".equals(chanEvaOrg.getId())) {
			getHibernateTemplate().save(chanEvaOrg);
		} else {
			getHibernateTemplate().saveOrUpdate(chanEvaOrg);
		}
	}

	public void removeChanEvaOrg(ChanEvaOrg chanEvaOrg) {
		getHibernateTemplate().delete(chanEvaOrg);
	}

	// 根据模板ID获取组织列表
	public List getOrgsByTempletId(String templateId) {
		return getHibernateTemplate().find(
				"from ChanEvaOrg org where org.templateId='" + templateId + "'");
	}

	// 根据模板ID删除相关组织
	public void removeOrgOfTemplate(String templateId) {
		final String hql = "delete from ChanEvaOrg chanEvaOrg where chanEvaOrg.templateId ='"
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
		final String hql = "delete from ChanEvaOrg chanEvaOrg where chanEvaOrg.templateId ='"
				+ templateId + "' and chanEvaOrg.actionType='" + actionType + "'";
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
		return getHibernateTemplate().find("from ChanEvaOrg org " + where);
	}
}
