package com.boco.eoms.partner.lineEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.lineEva.dao.ILineEvaOrgDao;
import com.boco.eoms.partner.lineEva.model.LineEvaOrg;

public class LineEvaOrgDaoHibernate extends BaseDaoHibernate implements ILineEvaOrgDao {

	public LineEvaOrg getLineEvaOrg(String id) {
		LineEvaOrg org = (LineEvaOrg) getHibernateTemplate().get(LineEvaOrg.class, id);
		if (null == org) {
			throw new ObjectRetrievalFailureException(LineEvaOrg.class, id);
		}
		return org;
	}

	public void saveLineEvaOrg(LineEvaOrg lineEvaOrg) {
		if (null == lineEvaOrg.getId() || "".equals(lineEvaOrg.getId())) {
			getHibernateTemplate().save(lineEvaOrg);
		} else {
			getHibernateTemplate().saveOrUpdate(lineEvaOrg);
		}
	}

	public void removeLineEvaOrg(LineEvaOrg lineEvaOrg) {
		getHibernateTemplate().delete(lineEvaOrg);
	}

	// 根据模板ID获取组织列表
	public List getOrgsByTempletId(String templateId) {
		return getHibernateTemplate().find(
				"from LineEvaOrg org where org.templateId='" + templateId + "'");
	}

	// 根据模板ID删除相关组织
	public void removeOrgOfTemplate(String templateId) {
		final String hql = "delete from LineEvaOrg lineEvaOrg where lineEvaOrg.templateId ='"
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
		final String hql = "delete from LineEvaOrg lineEvaOrg where lineEvaOrg.templateId ='"
				+ templateId + "' and lineEvaOrg.actionType='" + actionType + "'";
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
		return getHibernateTemplate().find("from LineEvaOrg org " + where);
	}
}
