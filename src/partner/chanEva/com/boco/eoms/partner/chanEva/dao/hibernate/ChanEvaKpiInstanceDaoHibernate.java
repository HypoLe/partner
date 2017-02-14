package com.boco.eoms.partner.chanEva.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.chanEva.dao.IChanEvaKpiInstanceDao;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTaskDetailMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTaskMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpi;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpiInstance;
import com.boco.eoms.partner.chanEva.model.ChanEvaTask;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;
import com.boco.eoms.filemanager.model.TawFileMgrScheme;

public class ChanEvaKpiInstanceDaoHibernate extends BaseDaoHibernate implements
		IChanEvaKpiInstanceDao, ID2NameDAO {

	public ChanEvaKpiInstance getKpiInstance(String id) {
		ChanEvaKpiInstance instance = (ChanEvaKpiInstance) getHibernateTemplate().get(
				ChanEvaKpiInstance.class, id);
		if (null == instance) {
			throw new ObjectRetrievalFailureException(ChanEvaKpiInstance.class, id);
		}
		return instance;
	}

	public void removeKpiInstance(ChanEvaKpiInstance kpiInstance) {
		getHibernateTemplate().delete(kpiInstance);
	}

	public void saveKpiInstance(ChanEvaKpiInstance kpiInstance) {
		if (null == kpiInstance.getId() || "".equals(kpiInstance.getId())) {
			getHibernateTemplate().save(kpiInstance);
		} else {
			getHibernateTemplate().saveOrUpdate(kpiInstance);
		}
	}

	public ChanEvaKpiInstance getKpiInstanceByTimeAndPartner(
			final String taskDetailId, final String timeType,
			final String time, final String partnerId) {
		ChanEvaKpiInstance eki = new ChanEvaKpiInstance();
		String hql = "from ChanEvaKpiInstance i where 1=1 ";
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
				eki = (ChanEvaKpiInstance) list.iterator().next();
			}
		}
		return eki;
	}
	
	public List getKpiInstanceListByTimeAndPartner(
			final String taskDetailId, final String partnerId, final String timeType,
			final String startTime, final String endTime, final String isPublish) {
		String hql = "from ChanEvaKpiInstance i where 1=1 ";
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
		final String hql = "from ChanEvaKpiInstance instance where instance.orgId='"
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

	public ChanEvaKpiInstance getKpiInstanceOfTemplate(final String orgId,
			final String kpiId, final String startDate, final String endDate) {
		final String hql = "from ChanEvaKpiInstance instance where instance.orgId='"
				+ orgId
				+ "' and instance.kpiId='"
				+ kpiId
				+ "' and instance.genTime>=:startDate and instance.genTime<=:endDate";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				ChanEvaKpiInstance instance = new ChanEvaKpiInstance();
				Query query = session.createQuery(hql);
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
				List list = query.list();
				Iterator it = list.iterator();
				if (it.hasNext()) {
					instance = (ChanEvaKpiInstance) it.next();
				}
				return instance;
			}
		};
		return (ChanEvaKpiInstance) getHibernateTemplate().execute(callback);
	}

	// 详细任务ID转换为模板名称
	// 王思轩 09-1-21
	public String id2Name(String id) {
		String kpiName = "";
		ChanEvaTaskDaoHibernate etd = new ChanEvaTaskDaoHibernate();
		if (id != null && !"".equals(id)) {
			ChanEvaTask et = etd.getTaskById(id);
			//ChanEvaTask et = etd.getTaskById(id);
			ChanEvaTemplateDaoHibernate eth = new ChanEvaTemplateDaoHibernate();
			if (et.getTemplateId() != null && !"".equals(et.getTemplateId())) {
				if(kpiName!=null&&!"".equals(kpiName)){
					kpiName = eth.id2Name(et.getTemplateId());
				}else{
					kpiName = ChanEvaConstants.NODE_NONAME;
				}
			} else {
				kpiName = ChanEvaConstants.NODE_NONAME;
			}
		}
		kpiName = ChanEvaConstants.NODE_NONAME;
		return kpiName;
	}
	
	public ChanEvaKpiInstance getKpiInstanceByTimeAndPartner(
			final String taskDetailId, final String timeType,
			final String time, final String partnerId, final String city) {
		ChanEvaKpiInstance eki = new ChanEvaKpiInstance();
		String hql = "from ChanEvaKpiInstance i where 1=1 ";
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
				eki = (ChanEvaKpiInstance) list.iterator().next();
			}
		}
		return eki;
	}	
}
