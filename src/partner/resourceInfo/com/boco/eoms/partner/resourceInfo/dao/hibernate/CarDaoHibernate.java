package com.boco.eoms.partner.resourceInfo.dao.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.resourceInfo.dao.ICarDao;
import com.boco.eoms.partner.resourceInfo.model.Car;

public class CarDaoHibernate  extends CommonGenericDaoImpl<Car, String> implements ICarDao {


	public Map getCars(final Integer curPage, final Integer pageSize, final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String queryStr = "from Car car ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;
				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				 //如果pageSize.intValue() != -1则不是导出操作（导出需要全表导出）
                if (pageSize.intValue() != -1) {
                	query.setFirstResult(pageSize.intValue()	* (curPage.intValue()));
    				query.setMaxResults(pageSize.intValue());
                }
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				System.out.println("map:"+map.get("total").toString());
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	/**
	 * 
	 *@Description:hql更新车辆实体
	 *@date May 15, 2013 10:15:09 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param hql
	 *@throws Exception
	 */
	public void updateCarByHql(String hql) throws Exception {
		final String hql0="update Car "+hql;
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) 	throws HibernateException{
				Query query=session.createQuery(hql0);
				query.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}
	/**
	 * 
	 *@Description:通过车牌号更新车辆的坐标
	 *@date May 15, 2013 10:15:31 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param latitude
	 *@param longtitude
	 *@param carNumber
	 *@throws Exception
	 */
	public void updateCarLocationByCarNumber(String latitude,String longtitude,String carNumber) throws Exception {
		final String hql="update Car set latitude='"+latitude+"',longitude='"+longtitude+"' where carNumber='"+carNumber+"'";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) 	throws HibernateException{
				Query query=session.createQuery(hql);
				query.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}

}
