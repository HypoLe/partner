package com.boco.eoms.partner.assiEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaOrgDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaOrg;

public class AssiEvaOrgDaoHibernate extends BaseDaoHibernate implements IAssiEvaOrgDao {

	public AssiEvaOrg getAssiEvaOrg(String id) {
		AssiEvaOrg org = (AssiEvaOrg) getHibernateTemplate().get(AssiEvaOrg.class, id);
		if (null == org) {
			throw new ObjectRetrievalFailureException(AssiEvaOrg.class, id);
		}
		return org;
	}

	public void saveAssiEvaOrg(AssiEvaOrg assiEvaOrg) {
		if (null == assiEvaOrg.getId() || "".equals(assiEvaOrg.getId())) {
			getHibernateTemplate().save(assiEvaOrg);
		} else {
			getHibernateTemplate().saveOrUpdate(assiEvaOrg);
		}
	}

	public void removeAssiEvaOrg(AssiEvaOrg assiEvaOrg) {
		getHibernateTemplate().delete(assiEvaOrg);
	}

	// 根据模板ID获取组织列表
	public List getOrgsByTempletId(String templateId) {
		return getHibernateTemplate().find(
				"from AssiEvaOrg org where org.templateId='" + templateId + "'");
	}

	// 根据模板ID删除相关组织
	public void removeOrgOfTemplate(String templateId) {
		final String hql = "delete from AssiEvaOrg assiEvaOrg where assiEvaOrg.templateId ='"
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
		final String hql = "delete from AssiEvaOrg assiEvaOrg where assiEvaOrg.templateId ='"
				+ templateId + "' and assiEvaOrg.actionType='" + actionType + "'";
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
		return getHibernateTemplate().find("from AssiEvaOrg org " + where);
	}
}
