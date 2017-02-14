package com.boco.eoms.partner.assess.AssExecute.dao.hibernate;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.boco.eoms.partner.assess.AssExecute.dao.IAssTaskDetailDao;
import com.boco.eoms.partner.assess.AssExecute.model.AssTaskDetail;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.StaticMethod;
/**
 * <p>
 * Title:考核任务明细Dao基类
 * </p>
 * <p>
 * Description:考核任务明细Dao基类
 * </p>
 * <p>
 * Date:Nov 26, 2010 5:52:06 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssTaskDetailDaoHibernate extends BaseDaoHibernate implements
		IAssTaskDetailDao {

	public int getMaxListNoOfTask(final String taskId) { 
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "select max(cast(detail.listNo as integer)) from AssTaskDetail detail where detail.taskId='"
						+ taskId + "'";
				Query query = session.createQuery(hql);
				List result = query.list();
				Integer maxListNo = Integer.valueOf(0);
				if (result.iterator().hasNext()) {
					maxListNo = StaticMethod.nullObject2Integer(result
							.iterator().next());
				}
				return maxListNo;
			}
		};
		return StaticMethod.nullObject2int(getHibernateTemplate().execute(
				callback));
	}

	public List listDetailOfTaskByListNo(String taskId, String listNo) {
		String sql = "from AssTaskDetail detail where 1=1 ";
		if(taskId!=null && !"".equals(taskId)){
			sql += " and detail.taskId='" + taskId + "' ";
		}
		if(listNo!=null && !"".equals(listNo)){
			sql += " and detail.listNo='" + listNo + "' ";
		}
		sql += " order by detail.taskId"; 
		List list = getHibernateTemplate().find(sql);
		return list;
	}
	
	public AssTaskDetail getTaskDetailById(String id) {	
		AssTaskDetail task = new AssTaskDetail();		
		List list = getHibernateTemplate().find(
				"from AssTaskDetail detail where detail.id='" + id + "'");
		if(!list.isEmpty()){
			task = (AssTaskDetail)list.get(0);
		}
		return task;
	}

	public void saveTask(AssTaskDetail taskDetail) {
		if (null == taskDetail.getId() || "".equals(taskDetail.getId())) {
			getHibernateTemplate().save(taskDetail);
		} else {
			getHibernateTemplate().saveOrUpdate(taskDetail);
		}
	}

	public List getLeafNodesOfChild(String taskId, String parentNodeId) {
		String sql = "select detail from AssTaskDetail detail,AssKpi kpi where kpi.id=detail.kpiId and detail.leaf='1' ";
		if(taskId!=null && !"".equals(taskId)){
			sql += " and detail.taskId='" + taskId + "' ";
		}
		if(parentNodeId!=null && !"".equals(parentNodeId)){
			sql += " and detail.parentNodeId like '" + parentNodeId + "%' ";
		}
		sql += " and kpi.kpiType<>'text' and kpi.kpiType<>'total'";
		sql += " order by detail.taskId";
		List list = getHibernateTemplate().find(sql);
		return list;
	}
	public List getLeafNodesOfChildForDeduct(String taskId, String parentNodeId) {
		String sql = "select detail from AssTaskDetail detail,AssKpi kpi where kpi.id=detail.kpiId and detail.leaf='1' ";
		if(taskId!=null && !"".equals(taskId)){
			sql += " and detail.taskId='" + taskId + "' ";
		}
		if(parentNodeId!=null && !"".equals(parentNodeId)){
			sql += " and detail.parentNodeId like '" + parentNodeId + "%' ";
		}
		sql += " and kpi.kpiType<>'text' and kpi.kpiType<>'total' and kpi.kpiType<>'deductScore'";
		sql += " order by detail.taskId";
		List list = getHibernateTemplate().find(sql);
		return list;
	}
	public AssTaskDetail getTaskDetail(String where) {	
		AssTaskDetail task = new AssTaskDetail();		 
		List list = getHibernateTemplate().find(
				"from AssTaskDetail detail where 1=1  "+where);
		if(!list.isEmpty()){
			task = (AssTaskDetail)list.get(0);
		}
		return task;
	}	

}
