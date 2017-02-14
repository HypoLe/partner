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
import com.boco.eoms.partner.baseinfo.dao.PartnerUserAndAreaDao;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndDept;

/**
 * <p>
 * Title:��Ա������� dao��hibernateʵ��
 * </p>
 * <p>
 * Description:��Ա�������
 * </p>
 * <p>
 * Tue Mar 10 16:24:32 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 */
public class PartnerUserAndAreaDaoHibernate extends BaseDaoHibernate implements PartnerUserAndAreaDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserAndAreaDao#getPartnerUserAndAreas()
	 *      
	 */
	public List getPartnerUserAndAreas() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUserAndArea partnerUserAndArea";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserAndAreaDao#getPartnerUserAndArea(java.lang.String)
	 *
	 */
	public PartnerUserAndArea getPartnerUserAndArea(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUserAndArea partnerUserAndArea where partnerUserAndArea.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerUserAndArea) result.iterator().next();
				} else {
					return new PartnerUserAndArea();
				}
			}
		};
		return (PartnerUserAndArea) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserAndAreaDao#savePartnerUserAndAreas(com.boco.eoms.partner.baseinfo.PartnerUserAndArea)
	 *      
	 */
	public void savePartnerUserAndArea(final PartnerUserAndArea partnerUserAndArea) {
		if ((partnerUserAndArea.getId() == null) || (partnerUserAndArea.getId().equals("")))
			getHibernateTemplate().save(partnerUserAndArea);
		else
			getHibernateTemplate().saveOrUpdate(partnerUserAndArea);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserAndAreaDao#removePartnerUserAndAreas(java.lang.String)
	 *      
	 */
    public void removePartnerUserAndArea(final String id) {
		getHibernateTemplate().delete(getPartnerUserAndArea(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		PartnerUserAndArea partnerUserAndArea = this.getPartnerUserAndArea(id);
		if(partnerUserAndArea==null){
			return "";
		}
		//TODO ���޸Ĵ���
		return partnerUserAndArea.getAreaNames();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserAndAreaDao#getPartnerUserAndAreas(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPartnerUserAndAreas(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUserAndArea partnerUserAndArea";
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
	
	//由userId得到人员地域信息
	public PartnerUserAndArea getObjectByUserId(String userId){
		String hql = "from PartnerUserAndArea partnerUserAndArea where partnerUserAndArea.userId = '"+userId+"'";
		List list = (List)getHibernateTemplate().find(hql);
		if(list.size()>0) return (PartnerUserAndArea)list.get(0);
		else return null;
	}
	
	/**
	 * 判断人力地域表userId是否唯一
	 * 
	 *      
	 */
	public Boolean isunique(final String userId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUserAndArea partnerUserAndArea where partnerUserAndArea.userId=:userId";
				Query query = session.createQuery(queryStr);
				query.setString("userId", userId);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return Boolean.valueOf(false);
				} else {
					return Boolean.valueOf(true);
				}
			}
		};
		return (Boolean) getHibernateTemplate().execute(callback);
	}
	

    /**
    *
    *根据当前用户 加载当前用户所在地市 列表 (地市或县区)
	 * @return 返回当前用户所在地市列表(地市或县区)
	 * 查询pnr_user_area表的地域信息（可查出多个）   
	 * add by wangjunfeng
    */

	public List listCityOfUser(final String userName) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql=" from  PartnerUserAndArea  where userId='"+userName+"')";
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

	
	public List listCityOfArea(final String areaid,final String areaType) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql="";
				if(!areaType.equals("province")){
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
	public List listCountyOfCity(final String cityId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				//String hql = "from TawSystemArea t  where t.areaid like '%" + cityId + "%' and length(areaid)="+ len +" ";
				String hql = "from TawSystemArea t  where t.parentAreaid = '" + cityId + "' ";
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}
	
	/**
	 * 通过地域节点ID转换为地市ID
	 */
	public List listCityIdByCityNodeId(final String cityId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "from AreaDeptTree a  where a.nodeId = '" + cityId + "' ";
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}	
	
	//二级联动菜单 加载合作伙伴 列表 (列出所在地市的合作伙伴)
	public List listProviderOfCity(final String cityId) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "from AreaDeptTree a  where  a.parentNodeId = (select nodeId from AreaDeptTree where  nodeType='area' and areaId = '" + cityId + "') ";
				
				List list = getHibernateTemplate().find(hql);
				return list;
			
			}
		};
		return (List) getHibernateTemplate().execute(callback);		

	}
	
	
	
	/**
	 * 取出所有地市
	 * @param province 当前用户所在省的ID
	 * 2010-4-6
	 * author:wangjunfeng
	 * @return
	 */
	public List listCityByProvince(final String province) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql=" from  TawSystemArea  where parentAreaid='"+province+"')";
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		

	}

    /**
     * 取出所有地市
     * @param province 当前用户所在省的ID
     * 2010-4-6
     * author:wangjunfeng
     * @return
     */
    public List listCityByRootArea(final String province) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                String hql=" from  TawSystemArea  where areaid like '"+province+"%' and ordercode=2)";
                List list = getHibernateTemplate().find(hql);
                return list;
            }
        };
        return (List) getHibernateTemplate().execute(callback);

    }
	
	/**
	 * 根据用户ID 查出pnr_user_area中的权限县区地域ID（多个）
	 * @return 县区
	 * 2010-4-6
	 * author:wangjunfeng	
	 */
	public List listCountyOfPnrUserArea(final String userId) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql=" from  PartnerUserAndArea  where userId='"+userId+"'" ;
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		

	}
	
	
	
	/**
	 * 根据所属地市、用户的县区权限 列出相应县区
	 * @return 县区
	 * 2010-4-6
	 * author:wangjunfeng	
	 */
	public List listCountyOfUserRight(final String countys,final String cityId) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql=" from  TawSystemArea  where parentAreaid='"+cityId+"' and areaid in (" + countys +") " ;
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		

	}

	@Override
	public PartnerUserAndArea getPartnerUserAndAreaByAreaId(String areaId) {
		// TODO Auto-generated method stub
		final String areaIdValue=areaId;
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUserAndArea p where p.areaId = :d1 or p.areaId like :d2 or p.areaId like :d3 or p.areaId like :d4 order by p.id desc";
				Query query = session.createQuery(queryStr);
				query.setString("d1", areaIdValue);
				query.setString("d2", areaIdValue+",%");
				query.setString("d3", "%,"+areaIdValue+",%");
				query.setString("d4", "%,"+areaIdValue);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerUserAndArea) result.iterator().next();
				} else {
					return new PartnerUserAndArea();
				}
			}
		};
		return (PartnerUserAndArea) getHibernateTemplate().execute(callback);	
	}
	
	
	
}