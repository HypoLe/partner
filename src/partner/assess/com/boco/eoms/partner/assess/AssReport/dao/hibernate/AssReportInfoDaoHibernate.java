/**
 * 
 */
package com.boco.eoms.partner.assess.AssReport.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.assess.AssReport.dao.IAssReportInfoDao;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Dec 2, 2010 6:27:43 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssReportInfoDaoHibernate extends BaseDaoHibernate implements IAssReportInfoDao {

	/* (non-Javadoc)
	 * @see com.boco.eoms.partner.assess.AssReport.dao.IAssReportInfoDao#getAssReportInfo(java.lang.String)
	 */
	public AssReportInfo getAssReportInfo(String id) {
		AssReportInfo reportInfo = (AssReportInfo) getHibernateTemplate().get(AssReportInfo.class, id);
		if (null == reportInfo) {
			throw new ObjectRetrievalFailureException(AssReportInfo.class, id);
		}
		return reportInfo;
	}

	public List getReportInfoByTimeAndPartner(final String taskId,final String areaId,
			final String timeType,final String time, final String partnerId,final String publicFlag) {
		HibernateCallback callback = new HibernateCallback() {
		public Object doInHibernate(Session session)
				throws HibernateException {
			String hql = "from AssReportInfo report where 1=1 ";
			if (taskId != null && !"".equals(taskId))
				hql = hql + " and report.taskId='" + taskId + "'";
			if (areaId != null && !"".equals(areaId))
				hql = hql + " and report.area='" + areaId + "'";
			if (timeType != null && !"".equals(timeType))
				hql = hql + " and report.timeType='" + timeType + "'";
			if (time != null && !"".equals(time))
				hql = hql + " and report.time='" + time + "'";
			if (partnerId != null && !"".equals(partnerId))
				hql = hql + " and report.partnerId='" + partnerId + "'";
			if (publicFlag != null && !"".equals(publicFlag))
				hql = hql + " and report.state='" + publicFlag + "'";
				Query query = session.createQuery(hql);
				List list = query.list();
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	/* (non-Javadoc)
	 * @see com.boco.eoms.partner.assess.AssReport.dao.IAssReportInfoDao#saveAssReportInfo(com.boco.eoms.partner.assess.AssReport.model.AssReportInfo)
	 */
	public void saveAssReportInfo(AssReportInfo assReportInfo) {
		if (null == assReportInfo.getId() || "".equals(assReportInfo.getId())) {
			getHibernateTemplate().save(assReportInfo);
		} else {
			getHibernateTemplate().saveOrUpdate(assReportInfo);
		}
	}
	
	public List getReportInfoByTreeNodeId(final String treeNodeId,final String areaId,
			final String timeType,final String time, final String partnerId,final String publicFlag) {
		HibernateCallback callback = new HibernateCallback() {
		public Object doInHibernate(Session session)
				throws HibernateException {
			String hql = "from AssReportInfo report where 1=1 ";
			if (treeNodeId != null && !"".equals(treeNodeId))
				hql = hql + " and report.treeNodeId='" + treeNodeId + "'";
			if (areaId != null && !"".equals(areaId))
				hql = hql + " and report.area='" + areaId + "'";
			if (timeType != null && !"".equals(timeType))
				hql = hql + " and report.timeType='" + timeType + "'";
			if (time != null && !"".equals(time))
				hql = hql + " and report.time='" + time + "'";
			if (partnerId != null && !"".equals(partnerId))
				hql = hql + " and report.partnerId='" + partnerId + "'";
			if (publicFlag != null && !"".equals(publicFlag))
				hql = hql + " and report.state='" + publicFlag + "'";
				Query query = session.createQuery(hql);
				List list = query.list();
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}	
}
