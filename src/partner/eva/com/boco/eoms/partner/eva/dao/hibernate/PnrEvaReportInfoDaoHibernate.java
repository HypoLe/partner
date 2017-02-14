package com.boco.eoms.partner.eva.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.eva.dao.IPnrEvaReportInfoDao;
import com.boco.eoms.partner.eva.model.PnrEvaReportInfo;
 
public class PnrEvaReportInfoDaoHibernate extends BaseDaoHibernate implements IPnrEvaReportInfoDao {

	

	public PnrEvaReportInfo getPnrEvaReportInfo(String id) {
		PnrEvaReportInfo ReportInfo = (PnrEvaReportInfo) getHibernateTemplate().get(PnrEvaReportInfo.class, id);
		if (null == ReportInfo) {
			throw new ObjectRetrievalFailureException(PnrEvaReportInfo.class, id);
		}
		return ReportInfo;
	}

	public void savePnrEvaReportInfo(PnrEvaReportInfo evaReportInfo) {
		if (null == evaReportInfo.getId() || "".equals(evaReportInfo.getId())) {
			getHibernateTemplate().save(evaReportInfo);
		} else {
			getHibernateTemplate().saveOrUpdate(evaReportInfo);
		}
	}

	public void removePnrEvaReportInfo(PnrEvaReportInfo evaReportInfo) {
		getHibernateTemplate().delete(evaReportInfo);
	}

	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where) {
		return getHibernateTemplate().find("from PnrEvaReportInfo where 1=1 " + where);
	}
	
	public List getReportInfoByTimeAndPartner(String templateId,String area,
			String timeType,String time, String partnerId,String publicFlag){
		StringBuffer hql  = new StringBuffer();
		hql.append("select report from PnrEvaReportInfo report,PnrEvaTask task");
		hql.append(" where report.taskId = task.id");
		if(templateId!=null&&!templateId.equals("")){
			hql.append(" and task.templateId = '"+templateId+"'");
		}
		if(area!=null&&!area.equals("")){
			hql.append(" and  report.area = '"+area+"'");
		}
		if(timeType!=null&&!timeType.equals("")){
			hql.append(" and  report.timeType = '"+timeType+"'");
		}
		if(time!=null&&!time.equals("")){
			hql.append(" and  report.time = '"+time+"'");
		}
		if("-1".equals(partnerId)){
			hql.append(" and  report.partnerId = ''");
		}else if(partnerId!=null&&!partnerId.equals("")){
			hql.append(" and  report.partnerId = '"+partnerId+"'");
		}
		hql.append(" and  report.isPublish = '"+publicFlag+"'");
		hql.append(" order by report.createTime desc");
		List list = getHibernateTemplate().find(hql.toString());

		return list;
	}
	
	public Map listReportInfoForPage(final Integer curPage,final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrEvaReportInfo where 1=1 ";

				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;

				String queryCountStr = "select count(*) " + queryStr;

				Integer total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next());
				Query query = session.createQuery(queryStr+" order by createTime desc");
				query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", total);
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
	public List getReportInfosByTimeAndAllPartner(String templateId,String area,
			String timeType,String time,String publicFlag){
		StringBuffer hql  = new StringBuffer();
		hql.append("select report from PnrEvaReportInfo report,PnrEvaTask task");
		hql.append(" where report.taskId = task.id");
		hql.append(" and task.templateId = '"+templateId+"'");
		hql.append(" and  report.area = '"+area+"'");
		hql.append(" and  report.timeType = '"+timeType+"'");
		hql.append(" and  report.time = '"+time+"'");
		hql.append(" and  report.isPublish = '"+publicFlag+"'");
		hql.append(" order by report.createTime desc");
		List list = getHibernateTemplate().find(hql.toString());
		return list;
	}
	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.eva.dao.IPnrEvaReportInfoDao#getReportYearStaticsByTime(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getReportYearStaticsByTime(String belongNode,String startTime,
			String endTime,String publicFlag){
		StringBuffer hql  = new StringBuffer();
		hql.append("select report from PnrEvaReportInfo report");
		hql.append(" where report.belongNode = '"+belongNode+"'");
		hql.append(" and  report.time >= '"+startTime+"'");
		hql.append(" and  report.time <= '"+endTime+"'");
		hql.append(" and  report.isPublish = '"+publicFlag+"'");
		hql.append(" order by report.partnerId,report.area,report.time");
		List list = getHibernateTemplate().find(hql.toString());
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.eva.dao.IPnrEvaReportInfoDao#getReportMouthStaticsByTime(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	/*
select tree1.node_name,tree2.node_name,tree2.weight,report1.area,report1.partner_id,report1.total_score,report1.area,report1.partner_id,report2.total_score 
 from pnr_eva_report_info report1,pnr_eva_report_info report2,pnr_eva_tree tree1, pnr_eva_tree tree2
 where tree1.id = report1.belong_node 
 and report2.belong_node = tree2.id 
 and tree2.parent_node_id = tree1.node_id
 and  report1.time='201001' and report1.belong_Node='8aa081df264014db01264047036d000b'
 order by report1.area , report1.partner_id,tree2.node_id
	 */
	public List getReportMouthStaticsByTime(String belongNode,String area,
			String time,String publicFlag){
		StringBuffer hql  = new StringBuffer();
		hql.append("select t1,t2,r1 from PnrEvaTree t1,PnrEvaTree t2,PnrEvaReportInfo r1 ");
		hql.append(" where t2.parentNodeId=t1.nodeId and r1.belongNode = t2.id ");
		hql.append(" and t1.id='"+belongNode+"'");
		if(!"".equals(area)){
			hql.append(" and r1.area in ("+area+") ");
		}
		hql.append(" and r1.time='"+time+"' ");
		hql.append(" and  r1.isPublish = '"+publicFlag+"'");
		hql.append(" order by r1.area,r1.partnerId,t2.nodeId ");
		List list = getHibernateTemplate().find(hql.toString());
		return list;
	}
	
	public List getReportsByTime(String belongNode,String area,
			String time,String publicFlag){
		StringBuffer hql  = new StringBuffer();
		hql.append("select report from PnrEvaReportInfo report");
		hql.append(" where report.belongNode = '"+belongNode+"'");
		hql.append(" and  report.time = '"+time+"'");
		if(!"".equals(area)){
			hql.append(" and report.area in ("+area+") ");
		}
		hql.append(" and  report.isPublish = '"+publicFlag+"'");
		hql.append(" order by report.area,report.partnerId,report.time");
		List list = getHibernateTemplate().find(hql.toString());
		return list;
	}
	
}
