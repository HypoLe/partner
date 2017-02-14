package com.boco.activiti.partner.process.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.activiti.partner.process.dao.IPnrActCityBudgetAmountDao;
import com.boco.activiti.partner.process.model.PnrActCityBudgetAmount;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;

public class PnrActCityBudgetAmountDaoHibernate extends BaseDaoHibernate implements
	IPnrActCityBudgetAmountDao {

	/**
	 * 保存地市预算金额
	 */
	public void savePnrActCityBudgetAmount(PnrActCityBudgetAmount pnrActCityBudgetAmount) {
		if ((pnrActCityBudgetAmount.getId() == null)
				|| (pnrActCityBudgetAmount.getId().equals(""))) {
			getHibernateTemplate().save(pnrActCityBudgetAmount);
		} else {
			getHibernateTemplate().saveOrUpdate(pnrActCityBudgetAmount);
		}

	}

	/**
	 * 通过主键ID获取地市预算金额
	 * 
	 * @param id
	 * @return
	 */
	public PnrActCityBudgetAmount getPnrActCityBudgetAmount(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrActCityBudgetAmount pnrActCityBudgetAmount where pnrActCityBudgetAmount.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PnrActCityBudgetAmount) result.iterator().next();
				} else {
					return new PnrActCityBudgetAmount();
				}
			}
		};
		return (PnrActCityBudgetAmount) getHibernateTemplate().execute(callback);
	}
	
	 /**
     * 查询地市预算金额
     * @param pageIndex
     * @param pageSize
     * @param whereStr
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map queryPnrActCityBudgetAmount(final Integer curPage,final Integer pageSize,final String whereStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrActCityBudgetAmount pnrActCityBudgetAmount";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				
				String queryCountStr = "select count(*) " + queryStr;
				
				queryStr+=" ORDER BY to_number(pnrActCityBudgetAmount.cityId),to_number(pnrActCityBudgetAmount.budgetYear),to_number(pnrActCityBudgetAmount.budgetQuarter)";

				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
    
    /**
	 * 查询地市金额添加页面
	 */
	@SuppressWarnings("unchecked")
	public Map selectPnrActCityBudgetAmountList(final Integer pageIndex,final Integer pageSize,final String whereStr,final String orderStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrActCityBudgetAmount pnrActCityBudgetAmount";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				
				String queryCountStr = "select count(*) " + queryStr;
				
				queryStr+=orderStr;//排序

				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (pageIndex.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
	 /**
     * 查询区县预算金额
     * @param pageIndex
     * @param pageSize
     * @param whereStr
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map selectPnrActCountyBudgetAmountList(final String whereStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrActCountyBudgetAmount pnrActCountyBudgetAmount";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				
				String queryCountStr = "select count(*) " + queryStr;
				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				System.out.println("@@@@@"+queryStr);
				Query query = session.createQuery(queryStr);
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
}
