package com.boco.eoms.assEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.assEva.dao.IAssEvaReportInfoDao;
import com.boco.eoms.assEva.model.AssEvaReportInfo;
 
public class AssEvaReportInfoDaoHibernate extends BaseDaoHibernate implements IAssEvaReportInfoDao {

	public AssEvaReportInfo getAssEvaReportInfo(String id) {
		AssEvaReportInfo ReportInfo = (AssEvaReportInfo) getHibernateTemplate().get(AssEvaReportInfo.class, id);
		if (null == ReportInfo) {
			throw new ObjectRetrievalFailureException(AssEvaReportInfo.class, id);
		}
		return ReportInfo;
	}

	public void saveAssEvaReportInfo(AssEvaReportInfo assEvaReportInfo) {
		if (null == assEvaReportInfo.getId() || "".equals(assEvaReportInfo.getId())) {
			getHibernateTemplate().save(assEvaReportInfo);
		} else {
			getHibernateTemplate().saveOrUpdate(assEvaReportInfo);
		}
	}

	public void removeAssEvaReportInfo(AssEvaReportInfo assEvaReportInfo) {
		getHibernateTemplate().delete(assEvaReportInfo);
	}

	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where) {
		return getHibernateTemplate().find("from AssEvaReportInfo eri where 1=1 " + where);
	}
}
