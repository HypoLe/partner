package com.boco.eoms.partner.baseinfo.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.partner.baseinfo.dao.IVehicleDao;
import com.boco.eoms.partner.baseinfo.model.Vehicle;

/**
 * <p>
 * Title:车辆配置 dao的hibernate实现
 * </p>
 * <p>
 * Description:资源配置管理 车辆配置
 * </p>
 * <p>
 * Mon Dec 07 19:17:45 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public class VehicleDaoHibernate extends BaseDaoHibernate implements IVehicleDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IVehicleDao#getVehicles()
	 *      
	 */
	public List getVehicles() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Vehicle vehicle";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
    /**
	 * 
	 * @see com.boco.eoms.partner.maintenance.CheckDao#getChecks()
	 *  根据当前用户 查询pnr_user_area表的地域信息（可查出多个）   
	 */
	public List listCityOfUser(final String userName) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql=" from  PartnerUserAndArea  where name='"+userName+"')";
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		

	}
	/**
	 * 根据当前地域信息 取出对应地域ID
	 * @param areaName
	 * @return
	 */
	public List listCityOfAreaName(final String areaName) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql=" from AreaDeptTree where nodeName = '"+ areaName +"')";
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}

	
	public List listCityOfArea(final String areaid) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql="";
				if(areaid.length()==4){
					 hql = "from TawSystemArea t  where t.areaid = '" + areaid + "' ";
				}else{
					 hql = "from TawSystemArea t  where t.parentAreaid = '" + areaid + "' ";
				}
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}

    /**
	 * 
	 * @see com.boco.eoms.partner.maintenance.CheckDao#getChecks()
	 *      
	 */
	public List listCountyOfCity(final String cityId,final String len) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "from TawSystemArea t  where t.areaid like '%" + cityId + "%' and length(areaid)="+ len +" ";
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}
	
	
	public List isUnique(final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "from Vehicle vehicle " + whereStr ;
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}

	
	
	/**
	 *
	 * @see com.boco.eoms.partner.baseinfo.IVehicleDao#getVehicle(java.lang.String)
	 *
	 */
	public Vehicle getVehicle(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Vehicle vehicle where vehicle.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Vehicle) result.iterator().next();
				} else {
					return new Vehicle();
				}
			}
		};
		return (Vehicle) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IVehicleDao#saveVehicles(com.boco.eoms.partner.baseinfo.Vehicle)
	 *      
	 */
	public void saveVehicle(final Vehicle vehicle) {
		if ((vehicle.getId() == null) || (vehicle.getId().equals("")))
			getHibernateTemplate().save(vehicle);
		else
			getHibernateTemplate().saveOrUpdate(vehicle);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IVehicleDao#removeVehicles(java.lang.String)
	 *      
	 */
    public void removeVehicle(final String id) {
		getHibernateTemplate().delete(getVehicle(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Vehicle vehicle = this.getVehicle(id);
		if(vehicle==null){
			return "";
		}
		//TODO 请修改代码
		return null;
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IVehicleDao#getVehicles(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getVehicles(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Vehicle vehicle";
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
	
}