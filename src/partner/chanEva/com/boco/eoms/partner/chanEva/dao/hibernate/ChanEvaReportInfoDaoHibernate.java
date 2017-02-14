package com.boco.eoms.partner.chanEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.chanEva.dao.IChanEvaReportInfoDao;
import com.boco.eoms.partner.chanEva.model.ChanEvaReportInfo;
 
public class ChanEvaReportInfoDaoHibernate extends BaseDaoHibernate implements IChanEvaReportInfoDao {

	public ChanEvaReportInfo getChanEvaReportInfo(String id) {
		ChanEvaReportInfo ReportInfo = (ChanEvaReportInfo) getHibernateTemplate().get(ChanEvaReportInfo.class, id);
		if (null == ReportInfo) {
			throw new ObjectRetrievalFailureException(ChanEvaReportInfo.class, id);
		}
		return ReportInfo;
	}

	public void saveChanEvaReportInfo(ChanEvaReportInfo chanEvaReportInfo) {
		if (null == chanEvaReportInfo.getId() || "".equals(chanEvaReportInfo.getId())) {
			getHibernateTemplate().save(chanEvaReportInfo);
		} else {
			getHibernateTemplate().saveOrUpdate(chanEvaReportInfo);
		}
	}

	public void removeChanEvaReportInfo(ChanEvaReportInfo chanEvaReportInfo) {
		getHibernateTemplate().delete(chanEvaReportInfo);
	}

	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where) {
		return getHibernateTemplate().find("from ChanEvaReportInfo eri where 1=1 " + where);
	}
}
