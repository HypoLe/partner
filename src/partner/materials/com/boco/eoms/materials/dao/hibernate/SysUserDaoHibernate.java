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
import com.boco.eoms.materials.dao.SysUserDao;
import com.boco.eoms.materials.model.SysUser;

/**
 * 
 * 用户信息(SysUser) Dao实现类
 * 
 * @author wanghongliang
 * 
 */
public class SysUserDaoHibernate extends BaseDaoHibernate implements SysUserDao {

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.SysUserDao#getSysUser()
	 */
	@SuppressWarnings("unchecked")
	public List<SysUser> getSysUser() {
		return getHibernateTemplate().find("from SysUser");
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.SysUserDao#getSysUser(java.lang.String)
	 */
	public SysUser getSysUser(String id) {
		SysUser sysUser = (SysUser) getHibernateTemplate().get(
				SysUser.class, id);
		if (sysUser == null) {
			throw new ObjectRetrievalFailureException(SysUser.class, id);
		}
		return sysUser;
	}


	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.SysUserDao#getSysUser(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getSysUser(final Integer curPage,
			final Integer pageSize, final String hql) {
		// TODO 自动生成方法存根
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException
            {
                Map map = new HashMap();
                String whereStr = " from SysUser sysUser ";
                String countStr = " select count(*) from SysUser ";
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
	 * @see com.boco.eoms.materials.dao.hibernate.SysUserDao#removeSysUser(java.lang.String)
	 */
	public void removeSysUser(String id) {
		getHibernateTemplate().delete(getSysUser(id));

	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.SysUserDao#saveSysUser(com.boco.eoms.materials.model.SysUser)
	 */
	public void saveSysUser(SysUser sysUser) {
		if ((sysUser.getId() == null) || (sysUser.getId().equals("")))
			getHibernateTemplate().save(sysUser);
		else
			getHibernateTemplate().saveOrUpdate(sysUser);
	}

}
