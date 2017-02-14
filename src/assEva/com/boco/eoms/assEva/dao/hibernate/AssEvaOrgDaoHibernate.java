package com.boco.eoms.assEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.assEva.dao.IAssEvaOrgDao;
import com.boco.eoms.assEva.model.AssEvaOrg;

public class AssEvaOrgDaoHibernate extends BaseDaoHibernate implements IAssEvaOrgDao {

	public AssEvaOrg getAssEvaOrg(String id) {
		AssEvaOrg org = (AssEvaOrg) getHibernateTemplate().get(AssEvaOrg.class, id);
		if (null == org) {
			throw new ObjectRetrievalFailureException(AssEvaOrg.class, id);
		}
		return org;
	}

	public void saveAssEvaOrg(AssEvaOrg assEvaOrg) {
		if (null == assEvaOrg.getId() || "".equals(assEvaOrg.getId())) {
			getHibernateTemplate().save(assEvaOrg);
		} else {
			getHibernateTemplate().saveOrUpdate(assEvaOrg);
		}
	}

	public void removeAssEvaOrg(AssEvaOrg assEvaOrg) {
		getHibernateTemplate().delete(assEvaOrg);
	}

	// 根据模板ID获取组织列表
	public List getOrgsByTempletId(String templateId) {
		return getHibernateTemplate().find(
				"from AssEvaOrg org where org.templateId='" + templateId + "'");
	}

	// 根据模板ID删除相关组织
	public void removeOrgOfTemplate(String templateId) {
		final String hql = "delete from AssEvaOrg assEvaOrg where assEvaOrg.templateId ='"
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
		final String hql = "delete from AssEvaOrg assEvaOrg where assEvaOrg.templateId ='"
				+ templateId + "' and assEvaOrg.actionType='" + actionType + "'";
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
		return getHibernateTemplate().find("from AssEvaOrg org " + where);
	}
}
