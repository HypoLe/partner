package com.boco.eoms.materials.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.materials.dao.StoreInventoryDao;
import com.boco.eoms.materials.model.StoreInventory;

/**
 * 
 * 库存信息(StoreInventory) Dao实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreInventoryDaoHibernate extends BaseDaoHibernate implements
		StoreInventoryDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInventoryDao#getStoreInventory()
	 */
	@SuppressWarnings("unchecked")
	public List<StoreInventory> getStoreInventory() {
		return getHibernateTemplate().find("from StoreInventory");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInventoryDao#getStoreInventory(java.lang.String)
	 */
	public StoreInventory getStoreInventory(String id) {
		StoreInventory storeInventory = (StoreInventory) getHibernateTemplate()
				.get(StoreInventory.class, id);
		if (storeInventory == null) {
			throw new ObjectRetrievalFailureException(StoreInventory.class, id);
		}
		return storeInventory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInventoryDao#getStoreInventory(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map getStoreInventory(final Integer curPage, final Integer pageSize,
			final String hql) {
		
	
			// TODO 自动生成方法存根
	        HibernateCallback callback = new HibernateCallback() {

	            public Object doInHibernate(Session session)
	                throws HibernateException
	            {
	                Map map = new HashMap();
	                String whereStr = " from StoreInventory storeInventory ";
	                String countStr = " select count(*) from StoreInventory ";
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInventoryDao#removeStoreInventory(java.lang.String)
	 */
	public void removeStoreInventory(String id) {
		getHibernateTemplate().delete(getStoreInventory(id));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInventoryDao#saveStoreInventory(com.boco.eoms.materials.model.StoreInventory)
	 */
	public void saveStoreInventory(StoreInventory storeInventory) {
		if ((storeInventory.getId() == null)
				|| (storeInventory.getId().equals("")))
			getHibernateTemplate().save(storeInventory);
		else
			getHibernateTemplate().saveOrUpdate(storeInventory);
	}

	/**
	 * 根据材料ID 查询库存
	 * 
	 * @param materialId
	 * @return List<StoreInventory>
	 */
	@SuppressWarnings("unchecked")
	public StoreInventory getInventoryByMaterialId(String materialId,
			String storeSid) {
		List<StoreInventory> list = new ArrayList<StoreInventory>();
		if (materialId != null) {

			String hql = "from StoreInventory storeInventory where storeInventory.materialId = '"
					+ materialId
					+ "' and storeInventory.storeSid = '"
					+ storeSid + "'";

			list = getHibernateTemplate().find(hql);
		}
		int test = list.size();
		return test != 0 ? list.get(0) : null;
	}

	/**
	 * 更新
	 * 
	 * @param entity
	 */
	public void update(StoreInventory entity) {
		getHibernateTemplate().update(entity);
	}

	@SuppressWarnings("unchecked")
	public List<StoreInventory> selectByItem(StoreInventory storeInventory) {
		String storeSid = storeInventory.getStoreSid();
		String materialName = storeInventory.getMaterialName();
		String materialEncode = storeInventory.getMaterialEncode();
		List<StoreInventory> list = new ArrayList<StoreInventory>();
		StringBuffer sb = new StringBuffer();
		sb.append("from StoreInventory storeInventory where 1 = 1 ");
		if ("".equals(storeSid)) {
			if ("".equals(materialName)) {
				if ("".equals(materialEncode)) {

				} else {
					sb.append(" and storeInventory.materialEncode = '").append(
							materialEncode).append("'");
				}
			} else {
				if ("".equals(materialEncode)) {
					sb.append(" and storeInventory.materialName = '").append(
							materialName).append("'");
				} else {
					sb.append(" and storeInventory.materialEncode = '").append(
							materialEncode).append("'").append(
							" and storeInventory.materialName = '").append(
							materialName).append("'");
				}
			}
		} else {
			if ("".equals(materialName)) {
				if ("".equals(materialEncode)) {
					sb.append(" and storeInventory.storeSid = '").append(storeSid)
							.append("'");
				} else {
					sb.append(" and storeInventory.materialEncode = '").append(
							materialEncode).append("'").append(
							" and storeInventory.storeSid = '").append(storeSid).append(
							"'");
				}
			} else {
				if ("".equals(materialEncode)) {
					sb.append(" and storeInventory.materialName = '").append(
							materialName).append("'").append(
							" and storeInventory.storeSid = '").append(storeSid).append(
							"'");
				} else {
					sb.append(" and storeInventory.materialEncode = '").append(
							materialEncode).append("'").append(
							" and storeInventory.materialName = '").append(
							materialName).append("'").append(
							" and storeInventory.storeSid = '").append(storeSid).append(
							"'");
				}
			}
		}
		list = getHibernateTemplate().find(sb.toString());

		return list;
	}

}
