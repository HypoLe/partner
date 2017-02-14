package com.boco.activiti.partner.process.dao.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.activiti.partner.process.dao.IPnrReviewResultsDao;
import com.boco.activiti.partner.process.model.PnrReviewResults;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;


public class PnrReviewResultsDaoHibernate extends CommonGenericDaoImpl<PnrReviewResults, String> implements IPnrReviewResultsDao {
	
	/**
	 * 根据条件查询会审结果列表
	 */
	@SuppressWarnings("unchecked")
	public Map getReviewResultsList(final Integer curPage, final Integer pageSize,final String whereStr,final String importStartTime,final String importEndTime) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrReviewResults res";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				//1.条数
				Query queryTotal =session.createQuery(queryCountStr);
				if (importStartTime != null && !"".equals(importStartTime)) {
				    Date tempImportStartTime=null;
					try {
						tempImportStartTime = sdf.parse(importStartTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					queryTotal.setTimestamp("dateStart",tempImportStartTime);
				}
				if (importEndTime != null && !"".equals(importEndTime)) {
					Date tempImportEndTime=null;
					try {
						tempImportEndTime = sdf.parse(importEndTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					queryTotal.setTimestamp("dateEnd",tempImportEndTime);
				}
				int total = ((Integer) queryTotal.iterate().next()).intValue();
				
				
				System.out.println("------------根据条件查询会审结果列表sql="+queryStr);
				
				//2.集合数
				Query query = session.createQuery(queryStr);
				query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				
				if (importStartTime != null && !"".equals(importStartTime)) {
				    Date tempImportStartTime=null;
					try {
						tempImportStartTime = sdf.parse(importStartTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					query.setTimestamp("dateStart",tempImportStartTime);
				}
				if (importEndTime != null && !"".equals(importEndTime)) {
					Date tempImportEndTime=null;
					try {
						tempImportEndTime = sdf.parse(importEndTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					query.setTimestamp("dateEnd",tempImportEndTime);
				}
				List<InspectPlanRes> result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

}
