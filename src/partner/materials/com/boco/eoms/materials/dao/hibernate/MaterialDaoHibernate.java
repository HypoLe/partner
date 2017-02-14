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
import com.boco.eoms.materials.dao.MaterialDao;
import com.boco.eoms.materials.model.Material;

/**
 * 
 * 货品信息(Material) Dao实现类
 * 
 * @author wanghongliang
 * 
 */
public class MaterialDaoHibernate extends BaseDaoHibernate implements MaterialDao {

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.MaterialDao#getMaterial()
	 */
	@SuppressWarnings("unchecked")
	public List<Material> getMaterial() {
		return getHibernateTemplate().find("from Material");
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.MaterialDao#getMaterial(java.lang.String)
	 */
	public Material getMaterial(String id) {
		Material material = (Material) getHibernateTemplate().get(
				Material.class, id);
		if (material == null) {
			throw new ObjectRetrievalFailureException(Material.class, id);
		}
		return material;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.MaterialDao#getMaterial(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getMaterial(Integer curPage,
			Integer pageSize) {
		return this.getMaterial(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.MaterialDao#getMaterial(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getMaterial(final Integer curPage,
			final Integer pageSize, final String hql) {
		// TODO 自动生成方法存根
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException
            {
                Map map = new HashMap();
                String whereStr = " from Material material ";
                String countStr = " select count(*) from Material ";
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
	 * @see com.boco.eoms.materials.dao.hibernate.MaterialDao#removeMaterial(java.lang.String)
	 */
	public void removeMaterial(String id) {
		getHibernateTemplate().delete(getMaterial(id));

	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.MaterialDao#saveMaterial(com.boco.eoms.materials.model.Material)
	 */
	public void saveMaterial(Material material) {
		if ((material.getId() == null) || (material.getId().equals("")))
			getHibernateTemplate().save(material);
		else
			getHibernateTemplate().saveOrUpdate(material);
	}

}
