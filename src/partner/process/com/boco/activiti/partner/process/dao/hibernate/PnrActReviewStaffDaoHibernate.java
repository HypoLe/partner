package com.boco.activiti.partner.process.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.activiti.partner.process.dao.IPnrActReviewStaffDao;
import com.boco.activiti.partner.process.model.PnrActReviewStaff;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;

public class PnrActReviewStaffDaoHibernate extends BaseDaoHibernate implements
		IPnrActReviewStaffDao {

	/**
	 * 保存会审人员
	 */
	public void savePnrActReviewStaff(PnrActReviewStaff pnrActReviewStaff) {
		if ((pnrActReviewStaff.getId() == null)
				|| (pnrActReviewStaff.getId().equals(""))) {
			getHibernateTemplate().save(pnrActReviewStaff);
		} else {
			getHibernateTemplate().saveOrUpdate(pnrActReviewStaff);
		}

	}

	/**
	 * 通过主键ID获取会审人员
	 * 
	 * @param id
	 * @return
	 */
	public PnrActReviewStaff getPnrActReviewStaff(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrActReviewStaff pnrActReviewStaff where pnrActReviewStaff.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PnrActReviewStaff) result.iterator().next();
				} else {
					return new PnrActReviewStaff();
				}
			}
		};
		return (PnrActReviewStaff) getHibernateTemplate().execute(callback);
	}
	
	 /**
     * 查询会审人员
     * @param pageIndex
     * @param pageSize
     * @param whereStr
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map queryPnrActReviewStaff(final Integer curPage,final Integer pageSize,final String whereStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrActReviewStaff pnrActReviewStaff";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;

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
	 * 验证地市的审核人员是否已存在
	 * @param cityId
	 * @param id
	 * @return
	 */
    public int checkCityIdUnique(String cityId,String id){
    	return 1;
    }

}
