package com.boco.eoms.materials.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.materials.dao.BillMaterialDao;
import com.boco.eoms.materials.model.BillMaterial;

/**
 * 
 * 单据中详细货品信息表(出入库中间表BillMaterial) Dao实现类
 * 
 * @author wanghongliang
 * 
 */
public class BillMaterialDaoHibernate extends BaseDaoHibernate implements
		BillMaterialDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.BillMaterialDao#getBillMaterial()
	 */
	@SuppressWarnings("unchecked")
	public List<BillMaterial> getBillMaterial() {
		return getHibernateTemplate().find("from BillMaterial");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.BillMaterialDao#getBillMaterial(java.lang.String)
	 */
	public BillMaterial getBillMaterial(String id) {
		BillMaterial billMaterial = (BillMaterial) getHibernateTemplate().get(
				BillMaterial.class, id);
		try{
			if (billMaterial == null) {
				throw new ObjectRetrievalFailureException(BillMaterial.class, id);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return billMaterial;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.BillMaterialDao#getBillMaterial(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
		public Map getBillMaterial(final Integer curPage, final Integer pageSize,
			final String hql) {
		
	
			// TODO 自动生成方法存根
	        HibernateCallback callback = new HibernateCallback() {

	            public Object doInHibernate(Session session)
	                throws HibernateException
	            {
	                Map map = new HashMap();
	                String whereStr = " from BillMaterial billMaterial ";
	                String countStr = " select count(*) from BillMaterial ";
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
	 * @see com.boco.eoms.materials.dao.hibernate.BillMaterialDao#removeBillMaterial(java.lang.String)
	 */
	public void removeBillMaterial(String id) {
		getHibernateTemplate().delete(getBillMaterial(id));

	}
	//删除此工单号下所有货品
	public void removeBillMaterialall(String billId) {
		if (billId != null) {
//			List<BillMaterial> list = this.getBillMateralByBillId(billId);
//			for (int i=0;i<list.size();i++){
//				String id = list.get(i).getId();
//				this.removeBillMaterial(id);
//			}
			Transaction tx = null;
			tx = this.getSession().getTransaction();
			String sql = "delete from  mate_bill_material k where k.store_bill_id='"+billId+"'";
			int  query = getSession().createSQLQuery(sql).executeUpdate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.BillMaterialDao#saveBillMaterial(com.boco.eoms.materials.model.BillMaterial)
	 */
	public void saveBillMaterial(BillMaterial billMaterial) {
		if ((billMaterial.getId() == null) || (billMaterial.getId().equals("")))
			getHibernateTemplate().save(billMaterial);
		else
			getHibernateTemplate().saveOrUpdate(billMaterial);
	}

	@SuppressWarnings("unchecked")
	public List<BillMaterial> getBillMateralByBillId(String billId) {
		List<BillMaterial> list = new ArrayList<BillMaterial>();
		if (billId != null) {
			String hql = "from BillMaterial billMaterial where billMaterial.storeBillId = '"
					+ billId + "'";
			list = getHibernateTemplate().find(hql);
		}
		return list;
	
	}
	
	public void updateBillMaterial(BillMaterial billMaterial){
		getHibernateTemplate().update(billMaterial);
	}

}
