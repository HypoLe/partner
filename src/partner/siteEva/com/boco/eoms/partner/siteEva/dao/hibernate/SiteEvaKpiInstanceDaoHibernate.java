package com.boco.eoms.partner.siteEva.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaKpiInstanceDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaKpiInstance;
import com.boco.eoms.partner.siteEva.model.SiteEvaTask;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;

public class SiteEvaKpiInstanceDaoHibernate extends BaseDaoHibernate implements
		ISiteEvaKpiInstanceDao, ID2NameDAO {

	public SiteEvaKpiInstance getKpiInstance(String id) {
		SiteEvaKpiInstance instance = (SiteEvaKpiInstance) getHibernateTemplate().get(
				SiteEvaKpiInstance.class, id);
		if (null == instance) {
			throw new ObjectRetrievalFailureException(SiteEvaKpiInstance.class, id);
		}
		return instance;
	}
 
	public void removeKpiInstance(SiteEvaKpiInstance kpiInstance) {
		getHibernateTemplate().delete(kpiInstance);
	}

	public void saveKpiInstance(SiteEvaKpiInstance kpiInstance) {
		if (null == kpiInstance.getId() || "".equals(kpiInstance.getId())) {
			getHibernateTemplate().save(kpiInstance);
		} else {
			getHibernateTemplate().saveOrUpdate(kpiInstance);
		}
	}

	public SiteEvaKpiInstance getKpiInstanceByTimeAndPartner(
			final String taskDetailId, final String timeType,
			final String time, final String partnerId) {
		SiteEvaKpiInstance eki = new SiteEvaKpiInstance();
		String hql = "from SiteEvaKpiInstance i where 1=1 ";
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
				eki = (SiteEvaKpiInstance) list.iterator().next();
			}
		}
		return eki;
	}
	
	public List getKpiInstanceListByTimeAndPartner(
			final String taskDetailId, final String partnerId, final String timeType,
			final String startTime, final String endTime, final String isPublish) {
		String hql = "from SiteEvaKpiInstance i where 1=1 ";
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

	public List listKpiInstanceOfTemplate(final String orgId,
			final String startDate, final String endDate) {
		final String hql = "from SiteEvaKpiInstance instance where instance.orgId='"
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

	public SiteEvaKpiInstance getKpiInstanceOfTemplate(final String orgId,
			final String kpiId, final String startDate, final String endDate) {
		final String hql = "from SiteEvaKpiInstance instance where instance.orgId='"
				+ orgId
				+ "' and instance.kpiId='"
				+ kpiId
				+ "' and instance.genTime>=:startDate and instance.genTime<=:endDate";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				SiteEvaKpiInstance instance = new SiteEvaKpiInstance();
				Query query = session.createQuery(hql);
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
				List list = query.list();
				Iterator it = list.iterator();
				if (it.hasNext()) {
					instance = (SiteEvaKpiInstance) it.next();
				}
				return instance;
			}
		};
		return (SiteEvaKpiInstance) getHibernateTemplate().execute(callback);
	}

	// 详细任务ID转换为模板名称
	// 王思轩 09-1-21
	public String id2Name(String id) {
		String kpiName = "";
		SiteEvaTaskDaoHibernate etd = new SiteEvaTaskDaoHibernate();
		if (id != null && !"".equals(id)) {
			SiteEvaTask et = etd.getTaskById(id);
			//SiteEvaTask et = etd.getTaskById(id);
			SiteEvaTemplateDaoHibernate eth = new SiteEvaTemplateDaoHibernate();
			if (et.getTemplateId() != null && !"".equals(et.getTemplateId())) {
				if(kpiName!=null&&!"".equals(kpiName)){
					kpiName = eth.id2Name(et.getTemplateId());
				}else{
					kpiName = SiteEvaConstants.NODE_NONAME;
				}
			} else {
				kpiName = SiteEvaConstants.NODE_NONAME;
			}
		}
		kpiName = SiteEvaConstants.NODE_NONAME;
		return kpiName;
	}
	
	public SiteEvaKpiInstance getKpiInstanceByTimeAndPartner(
			final String taskDetailId, final String timeType,
			final String time, final String partnerId, final String city) {
		SiteEvaKpiInstance eki = new SiteEvaKpiInstance();
		String hql = "from SiteEvaKpiInstance i where 1=1 ";
		if (taskDetailId != null && !"".equals(taskDetailId))
			hql = hql + " and i.taskDetailId='" + taskDetailId + "'";
		if (timeType != null && !"".equals(timeType))
			hql = hql + " and i.timeType='" + timeType + "'";
		if (time != null && !"".equals(time))
			hql = hql + " and i.time='" + time + "'";
		if (partnerId != null && !"".equals(partnerId))
			hql = hql + " and i.partnerId='" + partnerId + "'";
		if (city != null && !"".equals(city))
			hql = hql + " and i.city='" + city + "'";		
		List list = getHibernateTemplate().find(hql);
		if (list != null) {
			if (!list.isEmpty()) {
				eki = (SiteEvaKpiInstance) list.iterator().next();
			}
		}
		return eki;
	}	
}
