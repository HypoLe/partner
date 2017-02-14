package com.boco.eoms.partner.tranEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.tranEva.dao.ITranEvaReportInfoDao;
import com.boco.eoms.partner.tranEva.model.TranEvaReportInfo;
 
public class TranEvaReportInfoDaoHibernate extends BaseDaoHibernate implements ITranEvaReportInfoDao {

	public TranEvaReportInfo getTranEvaReportInfo(String id) {
		TranEvaReportInfo ReportInfo = (TranEvaReportInfo) getHibernateTemplate().get(TranEvaReportInfo.class, id);
		if (null == ReportInfo) {
			throw new ObjectRetrievalFailureException(TranEvaReportInfo.class, id);
		}
		return ReportInfo;
	}

	public void saveTranEvaReportInfo(TranEvaReportInfo tranEvaReportInfo) {
		if (null == tranEvaReportInfo.getId() || "".equals(tranEvaReportInfo.getId())) {
			getHibernateTemplate().save(tranEvaReportInfo);
		} else {
			getHibernateTemplate().saveOrUpdate(tranEvaReportInfo);
		}
	}

	public void removeTranEvaReportInfo(TranEvaReportInfo tranEvaReportInfo) {
		getHibernateTemplate().delete(tranEvaReportInfo);
	}

	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where) {
		return getHibernateTemplate().find("from TranEvaReportInfo eri where 1=1 " + where);
	}
}
