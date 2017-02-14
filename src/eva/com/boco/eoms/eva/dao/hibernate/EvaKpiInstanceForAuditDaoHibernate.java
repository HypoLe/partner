package com.boco.eoms.eva.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.eva.dao.IEvaKpiInstanceForAuditDao;
import com.boco.eoms.eva.mgr.IEvaTaskDetailMgr;
import com.boco.eoms.eva.mgr.IEvaTaskMgr;
import com.boco.eoms.eva.model.EvaKpi;
import com.boco.eoms.eva.model.EvaKpiInstanceForAudit;
import com.boco.eoms.eva.model.EvaTask;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.filemanager.model.TawFileMgrScheme;

public class EvaKpiInstanceForAuditDaoHibernate extends BaseDaoHibernate implements
		IEvaKpiInstanceForAuditDao, ID2NameDAO {

	public EvaKpiInstanceForAudit getKpiInstanceForAudit(String id) {
		EvaKpiInstanceForAudit instance = (EvaKpiInstanceForAudit) getHibernateTemplate().get(
				EvaKpiInstanceForAudit.class, id);
		if (null == instance) {
			throw new ObjectRetrievalFailureException(EvaKpiInstanceForAudit.class, id);
		}
		return instance;
	}

	public void removeKpiInstanceForAudit(EvaKpiInstanceForAudit kpiInstance) {
		getHibernateTemplate().delete(kpiInstance);
	}

	public void saveKpiInstanceForAudit(EvaKpiInstanceForAudit kpiInstance) {
		if (null == kpiInstance.getId() || "".equals(kpiInstance.getId())) {
			getHibernateTemplate().save(kpiInstance);
		} else {
			getHibernateTemplate().saveOrUpdate(kpiInstance);
		}
	}

	public EvaKpiInstanceForAudit getKpiInstanceForAuditByTimeAndPartner(
			final String taskDetailId, final String timeType,
			final String time, final String partnerId) {
		EvaKpiInstanceForAudit eki = new EvaKpiInstanceForAudit();
		String hql = "from EvaKpiInstanceForAudit i where 1=1 ";
		if (taskDetailId != null && !"".equals(taskDetailId))
			hql = hql + " and i.taskDetailId='" + taskDetailId + "'";
		if (timeType != null && !"".equals(timeType))
			hql = hql + " and i.timeType='" + timeType + "'";
		if (time != null && !"".equals(time))
			hql = hql + " and i.time='" + time + "'";
		if (partnerId != null && !"".equals(partnerId))
			hql = hql + " and i.partnerId='" + partnerId + "'";
		List list = getHibernateTemplate().find(hql);
		if (list != null) {
			if (!list.isEmpty()) {
				eki = (EvaKpiInstanceForAudit) list.iterator().next();
			}
		}
		return eki;
	}
	public List getKpiInstanceForAuditByScope(
			final String taskDetailId, final String scope) {
		EvaKpiInstanceForAudit eki = new EvaKpiInstanceForAudit();
		String hql = "from EvaKpiInstanceForAudit i where i.isPublish <>'3' ";
		if (taskDetailId != null && !"".equals(taskDetailId))
			hql = hql + " and i.taskDetailId='" + taskDetailId + "'";
		if (scope != null && !"".equals(scope))
			hql = hql + " and i.scope='" + scope + "'";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
	public List getKpiInstanceForAuditListByTimeAndPartner(
			final String taskDetailId, final String partnerId, final String timeType,
			final String startTime, final String endTime, final String isPublish) {
		String hql = "from EvaKpiInstanceForAudit i where 1=1 ";
		if (taskDetailId != null && !"".equals(taskDetailId))
			hql = hql + " and i.taskDetailId='" + taskDetailId + "'";
		if (partnerId != null && !"".equals(partnerId))
			hql = hql + " and i.partnerId='" + partnerId + "'";
		if (timeType != null && !"".equals(timeType))
			hql = hql + " and i.timeType='" + timeType + "'";
		if (startTime != null && !"".equals(startTime))
			hql = hql + " and i.time >='" + startTime + "'";
		if (endTime != null && !"".equals(endTime))
			hql = hql + " and i.time <='" + endTime + "'";
		if (isPublish != null && !"".equals(isPublish))
			hql = hql + " and i.isPublish ='" + isPublish + "'";
		hql = hql + " order by i.partnerId,i.time desc";
		return getHibernateTemplate().find(hql);
	}

	public List listKpiInstanceForAuditOfTemplate(final String orgId,
			final String startDate, final String endDate) {
		final String hql = "from EvaKpiInstanceForAudit instance where instance.orgId='"
				+ orgId
				+ "' and instance.genTime>=:startDate and instance.genTime<=:endDate";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createQuery(hql);
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
				List list = query.list();
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	public EvaKpiInstanceForAudit getKpiInstanceForAuditOfTemplate(final String orgId,
			final String kpiId, final String startDate, final String endDate) {
		final String hql = "from EvaKpiInstanceForAudit instance where instance.orgId='"
				+ orgId
				+ "' and instance.kpiId='"
				+ kpiId
				+ "' and instance.genTime>=:startDate and instance.genTime<=:endDate";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				EvaKpiInstanceForAudit instance = new EvaKpiInstanceForAudit();
				Query query = session.createQuery(hql);
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
				List list = query.list();
				Iterator it = list.iterator();
				if (it.hasNext()) {
					instance = (EvaKpiInstanceForAudit) it.next();
				}
				return instance;
			}
		};
		return (EvaKpiInstanceForAudit) getHibernateTemplate().execute(callback);
	}

	// 详细任务ID转换为模板名称
	// 王思轩 09-1-21
	public String id2Name(String id) {
		String kpiName = "";
		EvaTaskDaoHibernate etd = new EvaTaskDaoHibernate();
		if (id != null && !"".equals(id)) {
			EvaTask et = etd.getTaskById(id);
			//EvaTask et = etd.getTaskById(id);
			EvaTemplateDaoHibernate eth = new EvaTemplateDaoHibernate();
			if (et.getTemplateId() != null && !"".equals(et.getTemplateId())) {
				if(kpiName!=null&&!"".equals(kpiName)){
					kpiName = eth.id2Name(et.getTemplateId());
				}else{
					kpiName = EvaConstants.NODE_NONAME;
				}
			} else {
				kpiName = EvaConstants.NODE_NONAME;
			}
		}
		kpiName = EvaConstants.NODE_NONAME;
		return kpiName;
	}
	public List getKpiInstanceForAuditListByTask(final String taskId) {
		StringBuffer hql = new StringBuffer();
		hql.append("select kpi from EvaKpiInstanceForAudit kpi ,EvaTaskDetail detail where ");
		hql.append(" kpi.taskDetailId = detail.id ");
		hql.append(" and detail.taskId ='");
		hql.append(taskId);
		hql.append("' order by kpi.taskDetailId,kpi.time");
		return getHibernateTemplate().find(hql.toString());
	}
}
