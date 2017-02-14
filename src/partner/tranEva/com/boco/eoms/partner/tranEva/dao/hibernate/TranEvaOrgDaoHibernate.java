package com.boco.eoms.partner.tranEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.tranEva.dao.ITranEvaOrgDao;
import com.boco.eoms.partner.tranEva.model.TranEvaOrg;

public class TranEvaOrgDaoHibernate extends BaseDaoHibernate implements ITranEvaOrgDao {

	public TranEvaOrg getTranEvaOrg(String id) {
		TranEvaOrg org = (TranEvaOrg) getHibernateTemplate().get(TranEvaOrg.class, id);
		if (null == org) {
			throw new ObjectRetrievalFailureException(TranEvaOrg.class, id);
		}
		return org;
	}

	public void saveTranEvaOrg(TranEvaOrg tranEvaOrg) {
		if (null == tranEvaOrg.getId() || "".equals(tranEvaOrg.getId())) {
			getHibernateTemplate().save(tranEvaOrg);
		} else {
			getHibernateTemplate().saveOrUpdate(tranEvaOrg);
		}
	}

	public void removeTranEvaOrg(TranEvaOrg tranEvaOrg) {
		getHibernateTemplate().delete(tranEvaOrg);
	}

	// 根据模板ID获取组织列表
	public List getOrgsByTempletId(String templateId) {
		return getHibernateTemplate().find(
				"from TranEvaOrg org where org.templateId='" + templateId + "'");
	}

	// 根据模板ID删除相关组织
	public void removeOrgOfTemplate(String templateId) {
		final String hql = "delete from TranEvaOrg tranEvaOrg where tranEvaOrg.templateId ='"
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
		final String hql = "delete from TranEvaOrg tranEvaOrg where tranEvaOrg.templateId ='"
				+ templateId + "' and tranEvaOrg.actionType='" + actionType + "'";
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
		return getHibernateTemplate().find("from TranEvaOrg org " + where);
	}
}
