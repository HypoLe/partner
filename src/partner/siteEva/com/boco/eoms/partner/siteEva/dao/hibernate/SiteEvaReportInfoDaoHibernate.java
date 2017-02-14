package com.boco.eoms.partner.siteEva.dao.hibernate;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaReportInfoDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaReportInfo;
 
public class SiteEvaReportInfoDaoHibernate extends BaseDaoHibernate implements ISiteEvaReportInfoDao {

	public SiteEvaReportInfo getSiteEvaReportInfo(String id) {
		SiteEvaReportInfo ReportInfo = (SiteEvaReportInfo) getHibernateTemplate().get(SiteEvaReportInfo.class, id);
		if (null == ReportInfo) {
			throw new ObjectRetrievalFailureException(SiteEvaReportInfo.class, id);
		}
		return ReportInfo;
	} 

	public void saveSiteEvaReportInfo(SiteEvaReportInfo siteEvaReportInfo) {
		if (null == siteEvaReportInfo.getId() || "".equals(siteEvaReportInfo.getId())) {
			getHibernateTemplate().save(siteEvaReportInfo);
		} else {
			getHibernateTemplate().saveOrUpdate(siteEvaReportInfo);
		}
	}

	public void removeSiteEvaReportInfo(SiteEvaReportInfo siteEvaReportInfo) {
		getHibernateTemplate().delete(siteEvaReportInfo);
	}

	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where) {
		return getHibernateTemplate().find("from SiteEvaReportInfo eri where 1=1 " + where);
	}
}
