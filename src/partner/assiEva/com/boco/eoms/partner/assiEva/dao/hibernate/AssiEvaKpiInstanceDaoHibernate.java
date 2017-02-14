package com.boco.eoms.partner.assiEva.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaKpiInstanceDao;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTaskDetailMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTaskMgr;
import com.boco.eoms.partner.assiEva.model.AssiEvaKpi;
import com.boco.eoms.partner.assiEva.model.AssiEvaKpiInstance;
import com.boco.eoms.partner.assiEva.model.AssiEvaTask;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;
import com.boco.eoms.filemanager.model.TawFileMgrScheme;

public class AssiEvaKpiInstanceDaoHibernate extends BaseDaoHibernate implements
		IAssiEvaKpiInstanceDao, ID2NameDAO {

	public AssiEvaKpiInstance getKpiInstance(String id) {
		AssiEvaKpiInstance instance = (AssiEvaKpiInstance) getHibernateTemplate().get(
				AssiEvaKpiInstance.class, id);
		if (null == instance) {
			throw new ObjectRetrievalFailureException(AssiEvaKpiInstance.class, id);
		}
		return instance;
	}

	public void removeKpiInstance(AssiEvaKpiInstance kpiInstance) {
		getHibernateTemplate().delete(kpiInstance);
	}

	public void saveKpiInstance(AssiEvaKpiInstance kpiInstance) {
		if (null == kpiInstance.getId() || "".equals(kpiInstance.getId())) {
			getHibernateTemplate().save(kpiInstance);
		} else {
			getHibernateTemplate().saveOrUpdate(kpiInstance);
		}
	}

	public AssiEvaKpiInstance getKpiInstanceByTimeAndPartner(
			final String taskDetailId, final String timeType,
			final String time, final String partnerId) {
		AssiEvaKpiInstance eki = new AssiEvaKpiInstance();
		String hql = "from AssiEvaKpiInstance i where 1=1 ";
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
				eki = (AssiEvaKpiInstance) list.iterator().next();
			}
		}
		return eki;
	}
	
	public List getKpiInstanceListByTimeAndPartner(
			final String taskDetailId, final String partnerId, final String timeType,
			final String startTime, final String endTime, final String isPublish) {
		String hql = "from AssiEvaKpiInstance i where 1=1 ";
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
		final String hql = "from AssiEvaKpiInstance instance where instance.orgId='"
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

	public AssiEvaKpiInstance getKpiInstanceOfTemplate(final String orgId,
			final String kpiId, final String startDate, final String endDate) {
		final String hql = "from AssiEvaKpiInstance instance where instance.orgId='"
				+ orgId
				+ "' and instance.kpiId='"
				+ kpiId
				+ "' and instance.genTime>=:startDate and instance.genTime<=:endDate";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				AssiEvaKpiInstance instance = new AssiEvaKpiInstance();
				Query query = session.createQuery(hql);
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
				List list = query.list();
				Iterator it = list.iterator();
				if (it.hasNext()) {
					instance = (AssiEvaKpiInstance) it.next();
				}
				return instance;
			}
		};
		return (AssiEvaKpiInstance) getHibernateTemplate().execute(callback);
	}

	// 详细任务ID转换为模板名称
	// 王思轩 09-1-21
	public String id2Name(String id) {
		String kpiName = "";
		AssiEvaTaskDaoHibernate etd = new AssiEvaTaskDaoHibernate();
		if (id != null && !"".equals(id)) {
			AssiEvaTask et = etd.getTaskById(id);
			//AssiEvaTask et = etd.getTaskById(id);
			AssiEvaTemplateDaoHibernate eth = new AssiEvaTemplateDaoHibernate();
			if (et.getTemplateId() != null && !"".equals(et.getTemplateId())) {
				if(kpiName!=null&&!"".equals(kpiName)){
					kpiName = eth.id2Name(et.getTemplateId());
				}else{
					kpiName = AssiEvaConstants.NODE_NONAME;
				}
			} else {
				kpiName = AssiEvaConstants.NODE_NONAME;
			}
		}
		kpiName = AssiEvaConstants.NODE_NONAME;
		return kpiName;
	}
	
	public AssiEvaKpiInstance getKpiInstanceByTimeAndPartner(
			final String taskDetailId, final String timeType,
			final String time, final String partnerId, final String city) {
		AssiEvaKpiInstance eki = new AssiEvaKpiInstance();
		String hql = "from AssiEvaKpiInstance i where 1=1 ";
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
				eki = (AssiEvaKpiInstance) list.iterator().next();
			}
		}
		return eki;
	}	
}
