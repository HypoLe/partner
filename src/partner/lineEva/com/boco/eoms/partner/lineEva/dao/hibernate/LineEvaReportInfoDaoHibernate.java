package com.boco.eoms.partner.lineEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.lineEva.dao.ILineEvaReportInfoDao;
import com.boco.eoms.partner.lineEva.model.LineEvaReportInfo;
 
public class LineEvaReportInfoDaoHibernate extends BaseDaoHibernate implements ILineEvaReportInfoDao {

	public LineEvaReportInfo getLineEvaReportInfo(String id) {
		LineEvaReportInfo ReportInfo = (LineEvaReportInfo) getHibernateTemplate().get(LineEvaReportInfo.class, id);
		if (null == ReportInfo) {
			throw new ObjectRetrievalFailureException(LineEvaReportInfo.class, id);
		}
		return ReportInfo;
	}

	public void saveLineEvaReportInfo(LineEvaReportInfo lineEvaReportInfo) {
		if (null == lineEvaReportInfo.getId() || "".equals(lineEvaReportInfo.getId())) {
			getHibernateTemplate().save(lineEvaReportInfo);
		} else {
			getHibernateTemplate().saveOrUpdate(lineEvaReportInfo);
		}
	}

	public void removeLineEvaReportInfo(LineEvaReportInfo lineEvaReportInfo) {
		getHibernateTemplate().delete(lineEvaReportInfo);
	}

	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where) {
		return getHibernateTemplate().find("from LineEvaReportInfo eri where 1=1 " + where);
	}
}
