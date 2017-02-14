package com.boco.eoms.partner.eva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.eva.dao.IPnrEvaTaskDetailDao;
import com.boco.eoms.partner.eva.model.PnrEvaTaskDetail;

public class PnrEvaTaskDetailDaoHibernate extends BaseDaoHibernate implements
		IPnrEvaTaskDetailDao {

	public int getMaxListNoOfTask(final String taskId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "select max(cast(detail.listNo as integer)) from PnrEvaTaskDetail detail where detail.taskId='"
						+ taskId + "'";
				Query query = session.createQuery(hql);
				List result = query.list();
				Integer maxListNo = new Integer(0);
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
		String sql = "from PnrEvaTaskDetail detail where 1=1 ";
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
	
	public PnrEvaTaskDetail getTaskDetailById(String id) {	
		PnrEvaTaskDetail task = new PnrEvaTaskDetail();		
		List list = getHibernateTemplate().find(
				"from PnrEvaTaskDetail detail where detail.id='" + id + "'");
		if(!list.isEmpty()){
			task = (PnrEvaTaskDetail)list.get(0);
		}
		return task;
	}

	public void saveTask(PnrEvaTaskDetail taskDetail) {
		if (null == taskDetail.getId() || "".equals(taskDetail.getId())) {
			getHibernateTemplate().save(taskDetail);
		} else {
			getHibernateTemplate().saveOrUpdate(taskDetail);
		}
	}

	public List listDetailOfTaskId(String taskId) {
		String sql = "from PnrEvaTaskDetail detail where 1=1 ";
		if(taskId!=null && !"".equals(taskId)){
			sql += " and detail.taskId='" + taskId + "' ";
		}
		sql += " order by detail.taskId";
		List list = getHibernateTemplate().find(sql);
		return list;
	}

}
