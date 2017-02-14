/**
 * 
 */
package com.boco.eoms.partner.assess.AssAlgorithm.dao.hibernate;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.assess.AssAlgorithm.dao.IAssSelectedInstanceDao;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssSelectedInstance;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Dec 30, 2010 6:19:08 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssSelectedInstanceDaoHibernate extends BaseDaoHibernate  implements IAssSelectedInstanceDao {

	/* (non-Javadoc)
	 * @see com.boco.eoms.partner.assess.AssAlgorithm.dao.IAssSelectedInstanceDao#saveAssSelectedInstance(com.boco.eoms.partner.assess.AssAlgorithm.model.AssSelectedInstance)
	 */
	public void saveAssSelectedInstance(AssSelectedInstance instance) {
		if ((instance.getId() == null) || (instance.getId().equals("")))
			getHibernateTemplate().save(instance);
		else
			getHibernateTemplate().saveOrUpdate(instance);

	}
	public List getAssSelectedInstanceMap(final String kpiId,final String taskId,final String areaId,final String time,final String partnerId){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from AssSelectedInstance instance where 1=1 ");
				if (kpiId != null && !kpiId.equals("")){
					queryStr.append(" and instance.kpiId ='");
					queryStr.append(kpiId);
					queryStr.append("' ");
				}
				if (taskId != null && !taskId.equals("")){
					queryStr.append(" and instance.taskId ='");
					queryStr.append(taskId);
					queryStr.append("' ");
				}
				if (areaId != null && !areaId.equals("")){
					queryStr.append(" and instance.areaId ='");
					queryStr.append(areaId);
					queryStr.append("' ");
				}
				if (time != null && !time.equals("")){
					queryStr.append(" and instance.time ='");
					queryStr.append(time);
					queryStr.append("' ");
				}
				if (partnerId != null && !partnerId.equals("")){
					queryStr.append(" and instance.partnerId ='");
					queryStr.append(partnerId);
					queryStr.append("' ");
				}
				Query query = session.createQuery(queryStr.toString());
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	public void removeAssSelectedInstances(final String kpiId,final String taskId,final String areaId,final String time,final String partnerId){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {				
				StringBuffer delStr = new StringBuffer();
				delStr.append("delete from AssSelectedInstance instance where 1=1 ");
				delStr.append(" and instance.kpiId ='");
				delStr.append(kpiId);
				delStr.append("' ");
				delStr.append(" and instance.taskId ='");
				delStr.append(taskId);
				delStr.append("' ");
				delStr.append(" and instance.areaId ='");
				delStr.append(areaId);
				delStr.append("' ");
				delStr.append(" and instance.time ='");
				delStr.append(time);
				delStr.append("' ");
				delStr.append(" and instance.partnerId ='");
				delStr.append(partnerId);
				delStr.append("' ");
				Query query = session.createQuery(delStr.toString());
				return Integer.valueOf(query.executeUpdate());
			}
		};
		getHibernateTemplate().execute(callback);
	}

	public List getAssSelectedInstanceListByQuote(final String taskId,final String areaId,final String time,final String partnerId,final String quote){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("select instance from AssSelectedInstance instance,AssKpi ak  where instance.kpiId = ak.id ");
				if (taskId != null && !taskId.equals("")){
					queryStr.append(" and instance.taskId ='");
					queryStr.append(taskId);
					queryStr.append("' ");
				}
				if (areaId != null && !areaId.equals("")){
					queryStr.append(" and instance.areaId ='");
					queryStr.append(areaId);
					queryStr.append("' ");
				}
				if (time != null && !time.equals("")){
					queryStr.append(" and instance.time ='");
					queryStr.append(time);
					queryStr.append("' ");
				}
				if (partnerId != null && !partnerId.equals("")){
					queryStr.append(" and instance.partnerId ='");
					queryStr.append(partnerId);
					queryStr.append("' ");
				}
				if (quote != null && !quote.equals("")){
					queryStr.append(" and ak.isQuote ='");
					queryStr.append(quote);
					queryStr.append("' ");
				}				
				Query query = session.createQuery(queryStr.toString());
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
}
