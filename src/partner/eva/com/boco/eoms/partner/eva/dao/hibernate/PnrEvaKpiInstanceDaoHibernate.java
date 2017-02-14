package com.boco.eoms.partner.eva.dao.hibernate;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.eva.dao.IPnrEvaKpiInstanceDao;
import com.boco.eoms.partner.eva.model.PnrEvaKpiInstance;
import com.boco.eoms.partner.eva.model.PnrEvaTask;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;

public class PnrEvaKpiInstanceDaoHibernate extends BaseDaoHibernate implements
		IPnrEvaKpiInstanceDao, ID2NameDAO {

	public PnrEvaKpiInstance getKpiInstance(String id) {
		PnrEvaKpiInstance instance = (PnrEvaKpiInstance) getHibernateTemplate().get(
				PnrEvaKpiInstance.class, id);
		if (null == instance) {
			throw new ObjectRetrievalFailureException(PnrEvaKpiInstance.class, id);
		}
		return instance;
	}

	public void removeKpiInstance(PnrEvaKpiInstance kpiInstance) {
		getHibernateTemplate().delete(kpiInstance);
	}

	public void saveKpiInstance(PnrEvaKpiInstance kpiInstance) {
		if (null == kpiInstance.getId() || "".equals(kpiInstance.getId())) {
			getHibernateTemplate().save(kpiInstance);
		} else {
			getHibernateTemplate().saveOrUpdate(kpiInstance);
		}
	}

	public PnrEvaKpiInstance getKpiInstanceByTimeAndPartner(
			final String taskDetailId, final String timeType,
			final String time, final String partnerId) {
		PnrEvaKpiInstance eki = new PnrEvaKpiInstance();
		String hql = "from PnrEvaKpiInstance i where 1=1 ";
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
				eki = (PnrEvaKpiInstance) list.iterator().next();
			}
		}
		return eki;
	}
	
	public List getKpiInstanceListByTimeAndPartner(
			final String taskDetailId, final String partnerId, final String timeType,
			final String startTime, final String endTime, final String isPublish) {
		String hql = "from PnrEvaKpiInstance i where 1=1 ";
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
//		if (isPublish != null && !"".equals(isPublish))
//		hql = hql + " and i.isPublish ='" + isPublish + "'";
	if (PnrEvaConstants.TASK_PUBLISHED.equals(isPublish)){
		hql = hql + " and i.isPublish ='" + isPublish + "'";
	}else if(PnrEvaConstants.TASK_NOT_PUBLISHED.equals(isPublish)){
		hql = hql + " and i.isPublish ='" + isPublish + "'";
		hql = hql + " and audit_flag in ('0','3')"; 
	}
		hql = hql + " order by i.time desc";
		return getHibernateTemplate().find(hql);
	}


	// 详细任务ID转换为模板名称
	// 王思轩 09-1-21
	public String id2Name(String id) {
		String kpiName = "";
		PnrEvaTaskDaoHibernate etd = new PnrEvaTaskDaoHibernate();
		if (id != null && !"".equals(id)) {
			PnrEvaTask et = etd.getTaskById(id);
			PnrEvaTemplateDaoHibernate eth = new PnrEvaTemplateDaoHibernate();
			if (et.getTemplateId() != null && !"".equals(et.getTemplateId())) {
				if(kpiName!=null&&!"".equals(kpiName)){
					kpiName = eth.id2Name(et.getTemplateId());
				}else{
					kpiName = PnrEvaConstants.NODE_NONAME;
				}
			} else {
				kpiName = PnrEvaConstants.NODE_NONAME;
			}
		}
		kpiName = PnrEvaConstants.NODE_NONAME;
		return kpiName;
	}
	
	/**
	 * 根据任务详情信息Id查询指标考核实例
	 * 贾智会 09-11-05
	 * @return
	 */
	public PnrEvaKpiInstance getKpiInstanceByTaskDetailId(final String taskDetailId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrEvaKpiInstance where taskDetailId = ? ";
				Query query = session.createQuery(queryStr);
				query.setString(0, taskDetailId);
				List list = query.list();
				if (list.iterator().hasNext()) {
					return (PnrEvaKpiInstance)list.iterator().next();
				}
				return new PnrEvaKpiInstance();
			}
		};
		return  (PnrEvaKpiInstance) getHibernateTemplate().execute(callback);
	}

	public List getDraftKpiInstanceList(final String areaId){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("select eki from PnrEvaKpiInstance eki,PnrEvaTaskDetail etd,PnrEvaTask etk ");
				queryStr.append(" where eki.taskDetailId=etd.id ");
				queryStr.append(" and etd.taskId=etk.id ");
				queryStr.append(" and eki.isPublish='"+PnrEvaConstants.TASK_NOT_PUBLISHED+"'");
				queryStr.append(" and eki.auditFlag !="+PnrEvaConstants.AUDIT_WAIT);
				if(areaId!=null&&!"".equals(areaId)){
					queryStr.append(" and etk.orgId =?");
				}
				queryStr.append(" order by eki.createTime");
				Query query = session.createQuery(queryStr.toString());
				if(areaId!=null&&!"".equals(areaId)){
					query.setString(0, areaId);
				}
				List list = query.list();
				return list;
			}
		};
		return  (List) getHibernateTemplate().execute(callback);
	}
	

	public List getKpiInstanceAndDetail(final String taskId,
			final String timeType,final String time, 
			final String partnerId,final String isPublish) {
		StringBuffer hql = new StringBuffer();
		hql.append("select kpi,detail from PnrEvaKpiInstance kpi,PnrEvaTaskDetail detail ");
		hql.append(" where kpi.taskDetailId=detail.id");
		if (taskId != null && !"".equals(taskId))
			hql.append(" and detail.taskId='"+taskId+"'");
		if (timeType != null && !"".equals(timeType))
			hql.append(" and kpi.timeType='"+timeType+"'");
		if (time != null && !"".equals(time))
			hql.append(" and kpi.time='"+time+"'");
		if (partnerId != null && !"".equals(partnerId))
			hql.append(" and kpi.partnerId='"+partnerId+"'");
		if (isPublish != null && !"".equals(isPublish))
			hql.append(" and kpi.isPublish='"+isPublish+"'");
		hql.append(" order by detail.area");
		return getHibernateTemplate().find(hql.toString());
	}
}
