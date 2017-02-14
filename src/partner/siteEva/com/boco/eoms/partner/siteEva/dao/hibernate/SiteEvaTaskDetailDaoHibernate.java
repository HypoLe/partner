package com.boco.eoms.partner.siteEva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaTaskDetailDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaTaskDetail;

public class SiteEvaTaskDetailDaoHibernate extends BaseDaoHibernate implements
		ISiteEvaTaskDetailDao {

	public int getMaxListNoOfTask(final String taskId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "select max(cast(detail.listNo as integer)) from SiteEvaTaskDetail detail where detail.taskId='"
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
		String sql = "from SiteEvaTaskDetail detail where 1=1 ";
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
	
	public SiteEvaTaskDetail getTaskDetailById(String id) {	
		SiteEvaTaskDetail task = new SiteEvaTaskDetail();		
		List list = getHibernateTemplate().find(
				"from SiteEvaTaskDetail detail where detail.id='" + id + "'");
		if(!list.isEmpty()){
			task = (SiteEvaTaskDetail)list.get(0);
		}
		return task;
	}

	public void saveTask(SiteEvaTaskDetail taskDetail) {
		if (null == taskDetail.getId() || "".equals(taskDetail.getId())) {
			getHibernateTemplate().save(taskDetail);
		} else {
			getHibernateTemplate().saveOrUpdate(taskDetail);
		}
	}

}
