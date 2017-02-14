package com.boco.eoms.partner.res.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.res.dao.PnrResConfigDao;
import com.boco.eoms.partner.res.model.PnrResConfig;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */ 
public class PnrResConfigDaoHibernate extends CommonGenericDaoImpl<PnrResConfig, String> implements PnrResConfigDao {

	/**
	 * 资源分页列表
	 */
	public Map getResources(final Integer curPage, final Integer pageSize, final String whereStr) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				
				List<PnrResConfig> list = session.createQuery(" from PnrResConfig "+whereStr+" order by createTime desc").setFirstResult(curPage).setMaxResults(pageSize).list();
				
				int count = Integer.parseInt(session.createQuery(" select count(*) from PnrResConfig "+whereStr).uniqueResult().toString());
				
				HashMap map = new HashMap();
				map.put("total", new Integer(count));
				map.put("result", list);
				return map;
			}
		};
		
		return (Map) getHibernateTemplate().execute(callback);
	}

	/**
	 * 批量更新
	 */
	public void updateAllEntity(final Class c,final String setWhere,final String whereStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				return session.createQuery(" update "+c.getName()+setWhere+whereStr);
			}
		};
		getHibernateTemplate().execute(callback);
	}

}
