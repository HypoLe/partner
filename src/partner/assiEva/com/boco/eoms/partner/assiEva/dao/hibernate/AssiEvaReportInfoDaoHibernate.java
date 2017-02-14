package com.boco.eoms.partner.assiEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaReportInfoDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaReportInfo;
 
public class AssiEvaReportInfoDaoHibernate extends BaseDaoHibernate implements IAssiEvaReportInfoDao {

	public AssiEvaReportInfo getAssiEvaReportInfo(String id) {
		AssiEvaReportInfo ReportInfo = (AssiEvaReportInfo) getHibernateTemplate().get(AssiEvaReportInfo.class, id);
		if (null == ReportInfo) {
			throw new ObjectRetrievalFailureException(AssiEvaReportInfo.class, id);
		}
		return ReportInfo;
	}

	public void saveAssiEvaReportInfo(AssiEvaReportInfo assiEvaReportInfo) {
		if (null == assiEvaReportInfo.getId() || "".equals(assiEvaReportInfo.getId())) {
			getHibernateTemplate().save(assiEvaReportInfo);
		} else {
			getHibernateTemplate().saveOrUpdate(assiEvaReportInfo);
		}
	}

	public void removeAssiEvaReportInfo(AssiEvaReportInfo assiEvaReportInfo) {
		getHibernateTemplate().delete(assiEvaReportInfo);
	}

	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where) {
		return getHibernateTemplate().find("from AssiEvaReportInfo eri where 1=1 " + where);
	}
}
