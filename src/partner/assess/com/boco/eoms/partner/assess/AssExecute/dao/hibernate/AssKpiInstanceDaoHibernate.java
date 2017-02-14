package com.boco.eoms.partner.assess.AssExecute.dao.hibernate;

import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.boco.eoms.partner.assess.AssExecute.dao.IAssKpiInstanceDao;
import com.boco.eoms.partner.assess.AssExecute.model.AssKpiInstance;
import com.boco.eoms.partner.assess.AssExecute.model.AssTask;
import com.boco.eoms.partner.assess.AssTree.dao.hibernate.AssTemplateDaoHibernate;
import com.boco.eoms.partner.assess.util.AssConstants;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
/**
 * <p>
 * Title:考核实例Dao基类
 * </p>
 * <p>
 * Description:考核实例Dao基类
 * </p>
 * <p>
 * Date:Nov 26, 2010 5:52:06 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssKpiInstanceDaoHibernate extends BaseDaoHibernate implements
		IAssKpiInstanceDao, ID2NameDAO {

	public AssKpiInstance getKpiInstance(String id) {
		AssKpiInstance instance = (AssKpiInstance) getHibernateTemplate().get(
				AssKpiInstance.class, id);
		if (null == instance) {
			throw new ObjectRetrievalFailureException(AssKpiInstance.class, id);
		}
		return instance;
	}

	public void removeKpiInstance(AssKpiInstance kpiInstance) {
		getHibernateTemplate().delete(kpiInstance);
	}

	public void saveKpiInstance(AssKpiInstance kpiInstance) {
		if (null == kpiInstance.getId() || "".equals(kpiInstance.getId())) {
			getHibernateTemplate().save(kpiInstance);
		} else {
			getHibernateTemplate().saveOrUpdate(kpiInstance);
		}
	}

	public AssKpiInstance getKpiInstanceByTimeAndPartner(
			final String taskDetailId, final String timeType,
			final String time, final String partnerId) {
		AssKpiInstance eki = new AssKpiInstance();
		String hql = "from AssKpiInstance i where 1=1 ";
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
				eki = (AssKpiInstance) list.iterator().next();
			}
		}
		return eki;
	}
	
	public List getKpiInstanceListByTimeAndPartner(
			final String taskDetailId, final String partnerId, final String timeType,
			final String startTime, final String endTime, final String isPublish) {
		String hql = "from AssKpiInstance i where 1=1 ";
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
		final String hql = "from AssKpiInstance instance where instance.orgId='"
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

	public AssKpiInstance getKpiInstanceOfTemplate(final String orgId,
			final String kpiId, final String startDate, final String endDate) {
		final String hql = "from AssKpiInstance instance where instance.orgId='"
				+ orgId
				+ "' and instance.kpiId='"
				+ kpiId
				+ "' and instance.genTime>=:startDate and instance.genTime<=:endDate";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				AssKpiInstance instance = new AssKpiInstance();
				Query query = session.createQuery(hql);
				query.setString("startDate", startDate);
				query.setString("endDate", endDate);
				List list = query.list();
				Iterator it = list.iterator();
				if (it.hasNext()) {
					instance = (AssKpiInstance) it.next();
				}
				return instance;
			}
		};
		return (AssKpiInstance) getHibernateTemplate().execute(callback);
	}

	// 详细任务ID转换为模板名称
	// 王思轩 09-1-21
	public String id2Name(String id) {
		String kpiName = "";

		return kpiName;
	}
	
	public AssKpiInstance getKpiInstanceByTimeAndPartner(
			final String taskDetailId, final String timeType,
			final String time, final String partnerId, final String city) {
		AssKpiInstance eki = new AssKpiInstance();
		String hql = "from AssKpiInstance i where 1=1 ";
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
				eki = (AssKpiInstance) list.iterator().next();
			}
		}
		return eki;
	}	
	public AssKpiInstance getKpiInstanceByReport(
			final String taskDetailId, final String reportId) {
		AssKpiInstance eki = new AssKpiInstance();
		String hql = "from AssKpiInstance i where 1=1 ";
		if (taskDetailId != null && !"".equals(taskDetailId))
			hql = hql + " and i.taskDetailId='" + taskDetailId + "'";	
		if (reportId != null && !"".equals(reportId))
			hql = hql + " and i.reportId='" + reportId + "'";	
		List list = getHibernateTemplate().find(hql);
		if (list != null) {
			if (!list.isEmpty()) {
				eki = (AssKpiInstance) list.iterator().next();
			}
		}
		return eki;
	}	

	
	public List listKpiInstance(final String parentNodeId,
			final String partnerId, final String timeStr, final String city) {
		final String hql = "select instance from AssKpiInstance instance , AssTaskDetail task where instance.taskDetailId=task.id and task.parentNodeId like '"
				+ parentNodeId + "%' and instance.partnerId = '"+partnerId+
				"' and instance.city = '"+city+"' and instance.time in ("+timeStr+")";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createQuery(hql);
				List list = query.list();
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}	

	
	public List getKpiInstancesByReport(final String reportId) {
		String hql = "from AssKpiInstance i where 1=1 ";
		if (reportId != null && !"".equals(reportId))
			hql = hql + " and i.reportId='" + reportId + "'";	
		List list = getHibernateTemplate().find(hql);
		return list;
	}	
	
	public List getKpiInstanceRealScore(final String nodeId,
			final String city, final String time,final String partnerId) {
		final String hql = "select aki.realScore  from AssKpiInstance aki,AssTaskDetail  atd,AssReportInfo ari where aki.taskDetailId = atd.id and aki.reportId=ari.id and ari.state='1'" +
				" and  atd.nodeId like '"+nodeId+"%' and aki.city ='"+city+"' and aki.time ='"+time+"' and aki.partnerId = '"+partnerId+"'";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}	
	
	public List getReportInfoBySpecialty(String reportId, String specialty) {
		String sql = "SELECT aki FROM AssKpiInstance aki ,AssTaskDetail atd,AssKpi ak where aki.taskDetailId =atd.id and atd.kpiId = ak.id " ;
		if(reportId!=null && !"".equals(reportId)){
			sql += " and aki.reportId='"+reportId+"' ";
		}
//		暂时按照001 取得对应数据001为产品质量
		if(specialty!=null && !"".equals(specialty)){
			sql += " and atd.nodeId like '"+specialty+"001%'  ";
		}
		List list = getHibernateTemplate().find(sql);
		return list;
	}
	
	public List getAssKpiInstanceByQuote(String reportId, String quote) {
		String sql = "SELECT aki FROM AssKpiInstance aki ,AssTaskDetail atd,AssKpi ak where aki.taskDetailId =atd.id and atd.kpiId = ak.id " ;
		if(reportId!=null && !"".equals(reportId)){
			sql += " and aki.reportId='"+reportId+"' ";
		}
		if(quote!=null && !"".equals(quote)){
			sql += " and ak.isQuote = '"+quote+"'  ";
		}
		List list = getHibernateTemplate().find(sql);
		return list;
	}	
}
