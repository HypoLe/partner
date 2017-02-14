package com.boco.eoms.partner.inspect.dao.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.inspect.model.InspectPlanItem;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;

/**
 * Description: Copyright: Copyright (c)2012 Company: BOCO
 * 
 * @author: LEE
 * @version: 1.0 Create at: Jul 16, 2012 10:45:22 AM
 */
public class InspectPlanItemDaoHibernate
		extends
		CommonGenericDaoImpl<com.boco.eoms.partner.inspect.model.InspectPlanItem, String>
		implements com.boco.eoms.partner.inspect.dao.IInspectPlanItemDao {
	public Map<String, List> queryBigItem(final String planResId) {
		final Map<String, List> returnMap = new HashMap<String, List>();
		String sql = "select distinct(bigitemName) from InspectPlanItem where plan_res_id='"
				+ planResId + "'";
		System.out.println(planResId);
		Session session = this.getSession();
		Query query = session.createQuery(sql);
		List list = query.list();
		if (null != list && !list.isEmpty()) {
			returnMap.put("bigItem", list);
		} else {
			returnMap.put("bigItem", new ArrayList());
		}
		return returnMap;
	}

	@Override
	public List<InspectPlanItem> findInspectPlanItem(
			final String inspectPlanResId, final int exceptionFlag,
			final int isHandle, final Integer pageIndex, final Integer pageSize) {
		final String whereStr = createWhereStr(inspectPlanResId, exceptionFlag,
				isHandle);
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InspectPlanItem rei ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += " where " + whereStr;

				Query query = session.createQuery(queryStr);
				query.setFirstResult(pageSize.intValue()
						* (pageIndex.intValue()));
				query.setMaxResults(pageSize.intValue());
				List<InspectPlanItem> result = query.list();
				return result;
			}
		};
		return (List<InspectPlanItem>) getHibernateTemplate().execute(callback);
	}

	@Override
	public Integer countInspectPlanItem(final String inspectPlanResId,
			final int exceptionFlag, final int isHandle) {
		final String whereStr = createWhereStr(inspectPlanResId, exceptionFlag,
				isHandle);
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InspectPlanItem rei ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += " where " + whereStr;
				String queryCountStr = "select count(*) " + queryStr;

				return ((Integer) session.createQuery(queryCountStr).iterate()
						.next()).intValue();
			}
		};
		return (Integer) getHibernateTemplate().execute(callback);
	}

	private String createWhereStr(String inspectPlanResId, int exceptionFlag,
			int isHandle) {
		String whereStr = null;
		if (inspectPlanResId != null) {
			if (whereStr == null) {
				whereStr = "planResId=" + inspectPlanResId;
			}
		}
		if (exceptionFlag != -1) {
			if (whereStr == null) {
				whereStr = "exceptionFlag=" + exceptionFlag;
			} else {
				whereStr += " and exceptionFlag=" + exceptionFlag;
			}
		}
		if (isHandle != -1) {
			if (whereStr == null) {
				whereStr = "isHandle=" + isHandle;
			} else {
				whereStr += " and isHandle=" + isHandle;
			}
		}
		return whereStr;
	}
}
