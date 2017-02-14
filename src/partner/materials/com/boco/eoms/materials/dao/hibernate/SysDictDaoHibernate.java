package com.boco.eoms.materials.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.materials.dao.SysDictDao;
import com.boco.eoms.materials.model.SysDict;

/**
 * 
 * 字典表信息(SysDict) Dao实现类
 * 
 * @author wanghongliang
 * 
 */
public class SysDictDaoHibernate extends BaseDaoHibernate implements SysDictDao {

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.SysDictDao#getSysDict()
	 */
	@SuppressWarnings("unchecked")
	public List<SysDict> getSysDict() {
		return getHibernateTemplate().find("from SysDict");
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.SysDictDao#getSysDict(java.lang.String)
	 */
	public SysDict getSysDict(String id) {
		SysDict sysDict = (SysDict) getHibernateTemplate().get(
				SysDict.class, id);
		if (sysDict == null) {
			throw new ObjectRetrievalFailureException(SysDict.class, id);
		}
		return sysDict;
	}


	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.SysDictDao#getSysDict(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getSysDict(final Integer curPage,
			final Integer pageSize, final String hql) {
		// TODO 自动生成方法存根
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException
            {
                Map map = new HashMap();
                String whereStr = " from SysDict sysDict ";
                String countStr = " select count(*) from SysDict ";
                if(hql != null && !"".equals(hql))
                {
                    whereStr = whereStr + hql;
                    countStr = countStr + hql;
                }
                
                 
                Query countQuery = session.createQuery(countStr);
                List countList = countQuery.list();
                Number totalCount = 0;
                if(countList != null && countList.size() > 0)
                {
                    totalCount = (Number)countList.get(0);
                }
                Query query = session.createQuery(whereStr);
				//分页查询条
				if(pageSize.intValue()!=-1){
					query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
					query.setMaxResults(pageSize.intValue());
				}
                
                List result = query.list();
                map.put("total", totalCount.intValue());
                map.put("result", result);
                return map;
            }

        };
        return (HashMap)getHibernateTemplate().execute(callback);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.SysDictDao#removeSysDict(java.lang.String)
	 */
	public void removeSysDict(String id) {
		getHibernateTemplate().delete(getSysDict(id));

	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.SysDictDao#saveSysDict(com.boco.eoms.materials.model.SysDict)
	 */
	public void saveSysDict(SysDict sysDict) {
		if ((sysDict.getId() == null) || (sysDict.getId().equals("")))
			getHibernateTemplate().save(sysDict);
		else
			getHibernateTemplate().saveOrUpdate(sysDict);
	}

}
