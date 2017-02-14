package com.boco.eoms.partner.appops.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.dao.Name2IDDao;
import com.boco.eoms.partner.appops.dao.PartnerAppopsDeptDao;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsDept;
import com.boco.eoms.partner.baseinfo.dao.PartnerDeptDao;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.util.RoleIdList;
import com.google.common.base.Strings;

/**
 * <p>
 * Title:���� dao��hibernateʵ��
 * </p>
 * <p>
 * Description:����
 * </p>
 * <p>
 * Mon Feb 09 10:57:12 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 */
public class PartnerAppopsDeptHibernate extends BaseDaoHibernate implements
		PartnerAppopsDeptDao, ID2NameDAO, Name2IDDao {

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerDeptDao#getPartnerDepts()
	 * 
	 */
	public List getPartnerDepts() {

		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerDept partnerDept where deleted <> '1'";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * @author Steve Retrive all records according to hql, no paging.
	 */
	public List<IPnrPartnerAppOpsDept> getPartnerDeptsByHql(final String hql) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerDept partnerDept where 1=1 and "
						+ hql;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerDeptDao# getPartnerDepts(final
	 *      String where)
	 * 
	 */
	public List getPartnerDepts(final String where) {

		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from IPnrPartnerAppOpsDept partnerDept where deleted <> '1'";
				if (where != null && where.length() > 0)
					queryStr += where;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerDeptDao#getPartnerDept(java.lang.String)
	 * 
	 */
	public IPnrPartnerAppOpsDept getPartnerDept(final String id) {
		try {
			return (IPnrPartnerAppOpsDept) getObject(IPnrPartnerAppOpsDept.class, id);
		} catch (Exception ex) {
			return null;
		}
	}

	public Boolean getPartnerDeptByName(final String name) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerDept partnerDept where "
						+ "name= '" + name + "' and deleted <> '1'";
				Query query = session.createQuery(queryStr);
				List result = query.list();
				if (result.isEmpty())
					return true;
				else
					return false;
			}
		};
		return (Boolean) getHibernateTemplate().execute(callback);
		// return (PartnerDept)getObject(PartnerDept.class,id);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerDeptDao#savePartnerDepts(com.boco.eoms.partner.baseinfo.PartnerDept)
	 * 
	 */
	public void savePartnerDept(final IPnrPartnerAppOpsDept partnerDept) {
		if ((partnerDept.getId() == null) || (partnerDept.getId().equals("")))
			getHibernateTemplate().save(partnerDept);
		else
			getHibernateTemplate().saveOrUpdate(partnerDept);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerDeptDao#removePartnerDepts(java.lang.String)
	 * 
	 */
	public void removePartnerDept(final String id) {
		getHibernateTemplate().delete(getPartnerDept(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 * 
	 */
	public String id2Name(String id) {
		try {
			IPnrPartnerAppOpsDept partnerDept = this.getPartnerDept(id);
			if (partnerDept != null && partnerDept.getId() != null) {
				return partnerDept.getName();
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerDeptDao#getPartnerDepts(java.lang.Integer,java.lang.Integer,java.lang.String)
	 * 
	 */
	public Map getPartnerDepts(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerDept partnerDept";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr + " and deleted <> '1'";
				else
					queryStr += " where deleted <> '1'";
				String queryCountStr = "select count(*) " + queryStr;
				queryStr = queryStr + " order by createTime desc";

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
	 * 由字段treeId得到partnerDept
	 */
	public IPnrPartnerAppOpsDept getPartnerDeptByTreeId(final String treeId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerDept partnerDept where partnerDept.treeId=:treeId";
				Query query = session.createQuery(queryStr);
				query.setString("treeId", treeId);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerDept) result.iterator().next();
				} else {
					return null;
				}
			}
		};
		return (IPnrPartnerAppOpsDept) getHibernateTemplate().execute(callback);
	}

	/**
	 * 由字段treeNodeId得到partnerDept
	 */
	public IPnrPartnerAppOpsDept getPartnerDeptByTreeNodeId(final String treeNodeId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerDept partnerDept where partnerDept.treeNodeId=:treeNodeId";
				Query query = session.createQuery(queryStr);
				query.setString("treeNodeId", treeNodeId);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerDept) result.iterator().next();
				} else {
					return null;
				}
			}
		};
		return (IPnrPartnerAppOpsDept) getHibernateTemplate().execute(callback);
	}

	/**
	 * id:EOMS-liujinlong-20090924143614 开发人邮箱：liujinlong@boco.com.cn
	 * 时间：2009-09-24 开发目的：将删除改为设置删除字段置deleted为1
	 * 参数parentNodeId：合作伙伴父节点nodeID，也可能是合作伙伴的nodeId
	 */
	public void removePartnerDeptByNodeId(final String parentNodeId) {
		final String hql = "update PartnerDept partnerDept set deleted = '1' where partnerDept.treeNodeId like '"
				+ parentNodeId + "%'";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createQuery(hql);
				query.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}

	/**
	 * 根据地区查询对应的公司
	 */
	public List<IPnrPartnerAppOpsDept> getCompanyByProvience(String id,
			String firstdeptsimble, String user) {
		String _strHQL = null;
		PartnerDept partnerDept = null;
		if (user != null && user.equals("admin")) {
			_strHQL = "select new PartnerDept(id, name,interfaceHeadId) from PartnerDept p order by p.name asc";
		} else {
			_strHQL = "select new PartnerDept(id, name,interfaceHeadId) from PartnerDept p where deleted=0 and p.deptMagId like '"
					+ firstdeptsimble
					+ "%' and p.deptMagId like '"
					+ id
					+ "%' order by p.name asc";
		}
		return (List<IPnrPartnerAppOpsDept>) getHibernateTemplate().find(_strHQL);
	}

	/**
	 * 根据公司级别查询对应的公司
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerDeptDao#getPartnerDepts(java.lang.Integer,java.lang.Integer,java.lang.String)
	 * 
	 */
	public Map getPartnerDeptsByDeptLevel(final Integer curPage,final Integer pageSize, final String whereStr) {
			HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from IPnrPartnerAppOpsDept partnerDept";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr + " and deleted <> '1'";
				else
					queryStr += " where deleted <> '1'";
				String queryCountStr = "select count(*) " + queryStr;
				queryStr = queryStr + " order by createTime desc";
				int total = ((Integer) session.createQuery(queryCountStr).iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query.setFirstResult(pageSize.intValue()	* (curPage.intValue()));
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
	
	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.baseinfo.dao.PartnerDeptDao#getPartnerDeptsByAreaidOrDeptis(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map getPartnerDeptsByAreaidOrDeptis(final Integer curPage,final Integer pageSize, final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from IPnrPartnerAppOpsDept partnerDept";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr + " and deleted <> '1'";
				else
					queryStr += " where deleted <> '1'";
				String queryCountStr = "select count(*) " + queryStr;
				queryStr = queryStr + " order by createTime desc";
				int total = ((Integer) session.createQuery(queryCountStr).iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query.setFirstResult(pageSize.intValue()	* (curPage.intValue()));
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

	public String name2Id(final String name, String beanId, String field) {
		if (Strings.nullToEmpty(field).equals("areaId")) {
			HibernateCallback callback = new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException {
					String queryStr = "from TawSystemArea sysarea where sysarea.areaname = '"
							+ name + "'";
					Query query = session.createQuery(queryStr);
					query.setFirstResult(0);
					query.setMaxResults(1);
					List result = query.list();
					if (result != null && result.size() > 0) {
						return (TawSystemArea) result.get(0);
					}
					return new TawSystemArea();
				}
			};
			return ((TawSystemArea) getHibernateTemplate().execute(callback))
					.getAreaid();
		} else {
			HibernateCallback callback = new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException {
					String queryStr = "from PartnerDept partnerDept where partnerDept.name = '"
							+ name + "'";
					Query query = session.createQuery(queryStr);
					query.setFirstResult(0);
					query.setMaxResults(1);
					List result = query.list();
					if (result != null && result.size() > 0) {
						return (PartnerDept) result.get(0);
					}
					return new PartnerDept();
				}
			};
			return ((PartnerDept) getHibernateTemplate().execute(callback))
					.getId();
		}
	}

	public List<TawSystemArea> getAreas(final String parentareaid) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawSystemArea tawSystemArea where parentAreaid = '"
						+ parentareaid + "'";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * 得到下一级子部门的部门信息
	 * 
	 * @param pardeptid
	 * @param delid
	 * @return
	 */
	public List getNextLevecDepts(String pardeptid, String delid,
			String userdeptid,int isPartner) {
//		PartnerDeptDao dao = (PartnerDeptDao) ApplicationContextHolder.getInstance().getBean("partnerDeptDao");
//		PartnerDept dept = dao.getPartnerDept(userdeptid);
//		String magId = dept.getDeptMagId();
		String hql = "";
		String baseSql = "from IPnrPartnerAppOpsDept partnerDept where  ";
		RoleIdList roleidList = (RoleIdList)ApplicationContextHolder.getInstance().getBean("roleIdList");
//		userdeptid = userdeptid.startsWith(roleidList.getParDeptId().toString()) ? "1" : userdeptid;
		if(isPartner!=-1){
			baseSql+=" isPartner="+isPartner +" and partnerDept.interfaceHeadId ";
		}else{
			baseSql+=" partnerDept.interfaceHeadId ";
		}
		if(!userdeptid.startsWith(roleidList.getParDeptId().toString())){//如果不是以roleidList.getParDeptId().toString()开头，就是移动公司的人
			userdeptid = "1";
		}
		if ("".equals(userdeptid)) {
			if ("1".equals(userdeptid)) {
				if ("-1".equals(pardeptid)) {
					hql = baseSql
							+ " = partnerDept.id "
							+ "  and partnerDept.deptLevel = '1' and partnerDept.deleted='"
							+ delid + "' order by createTime";
				} else {
					hql = baseSql
							+ " = '"
							+ pardeptid
							+ "'  and partnerDept.id <> partnerDept.interfaceHeadId  and partnerDept.deleted='"
							+ delid + "' order by createTime";
				}
			} else if (!"1".equals(userdeptid)) {
				if ("-1".equals(pardeptid)) {
					hql = baseSql
							+ " =  partnerDept.id  "
							+ "  and partnerDept.deptLevel = '1' and partnerDept.deleted='"
							+ delid + "'     and partnerDept.deptMagId like '"
							+ userdeptid + "%'  order by createTime";
				} else

					hql = baseSql
							+ " = '"
							+ pardeptid
							+ "'  and partnerDept.id <> partnerDept.interfaceHeadId  and partnerDept.deleted='"
							+ delid + "'   and partnerDept.deptMagId like '"
							+ userdeptid + "%'    order by createTime";
			}
		} else if (!"".equals(userdeptid)) {
			if ("1".equals(userdeptid)) {
				if ("-1".equals(pardeptid)) {
					hql = baseSql
							+ " = partnerDept.id "
							+ "  and partnerDept.deptLevel = '1' and partnerDept.deleted='"
							+ delid + "' order by createTime";
				} else
					hql = baseSql
							+ " = '"
							+ pardeptid
							+ "'  and partnerDept.id <> partnerDept.interfaceHeadId  and partnerDept.deleted='"
							+ delid + "'    order by createTime";

			} else if (!"1".equals(userdeptid)) {
				if ("-1".equals(pardeptid)) {
					hql = baseSql
							+ " =  partnerDept.id  "
							+ "  and partnerDept.deptLevel = '1' and partnerDept.deleted='"
							+ delid + "'     and partnerDept.deptMagId like '"
							+ userdeptid.substring(0, PartnerPrivUtils.getProvinceDeptLength()) + "%'  order by createTime";
				} else{
//					switch (userdeptid.length()) {
//					case 7://区县级
//						PartnerDeptDao dao = (PartnerDeptDao) ApplicationContextHolder.getInstance().getBean("partnerDeptDao");
//						PartnerDept dept = dao.getPartnerDept(pardeptid);
//						String magId =null;
//						if(null != dept){
//							magId = dept.getDeptMagId();
//						}
//						if(null != magId && 5 == magId.length()){//如果长度为5则代表是树的第三级,如果是第三级则应该不截取
//							hql = baseSql
//							+ " = '"
//							+ pardeptid
//							+ "' and  partnerDept.deptMagId like '"
//							+ userdeptid+"%' ";
//						}else{//如果不是第三级,则应该从第五位开始截取
//							hql = baseSql
//							+ " = '"
//							+ pardeptid
//							+ "'    and partnerDept.deptMagId like '"
//							+ userdeptid.substring(0, 5)+"%' ";
//						}
//						break;
//					case 5:
//						hql = baseSql
//						+ " = '"
//						+ pardeptid
//						+ "'    and partnerDept.deptMagId like '"
//						+ userdeptid+"%' ";
//						break;
//					case 3:
//						hql = baseSql
//						+ " = '"
//						+ pardeptid
//						+ "'   and partnerDept.deptMagId like '"
//						+ userdeptid+"%' ";
//						break;
//					case 9:
//						hql = baseSql
//						+ " = '"
//						+ pardeptid
//						+ "'   and partnerDept.deptMagId like '"
//						+ userdeptid+"%' ";
//						break;
//					}
					
					if(PartnerPrivUtils.getProvinceDeptLength()==userdeptid.length()){
						hql = baseSql
						+ " = '"
						+ pardeptid
						+ "'   and partnerDept.deptMagId like '"
						+ userdeptid+"%' ";
					}else  if(PartnerPrivUtils.getCityDeptLength()==userdeptid.length()){
						hql = baseSql
						+ " = '"
						+ pardeptid
						+ "'    and partnerDept.deptMagId like '"
						+ userdeptid+"%' ";
					}else if(PartnerPrivUtils.getCountyDeptLength()==userdeptid.length()){//区县级
						PartnerDeptDao dao = (PartnerDeptDao) ApplicationContextHolder.getInstance().getBean("partnerDeptDao");
						PartnerDept dept = dao.getPartnerDept(pardeptid);
						String magId =null;
						if(null != dept){
							magId = dept.getDeptMagId();
						}
						if(null != magId && PartnerPrivUtils.getCityDeptLength() == magId.length()){//如果长度为5则代表是树的第三级,如果是第三级则应该不截取
							hql = baseSql
							+ " = '"
							+ pardeptid
							+ "' and  partnerDept.deptMagId like '"
							+ userdeptid+"%' ";
						}else{//如果不是第三级,则应该从第五位开始截取
							hql = baseSql
							+ " = '"
							+ pardeptid
							+ "'    and partnerDept.deptMagId like '"
							+ userdeptid.substring(0, PartnerPrivUtils.getCityDeptLength())+"%' ";
						}
					}else if(PartnerPrivUtils.getGroupDeptLength()==userdeptid.length()){
						hql = baseSql
						+ " = '"
						+ pardeptid
						+ "'   and partnerDept.deptMagId like '"
						+ userdeptid+"%' ";
					}
					hql = hql+" and partnerDept.id <> partnerDept.interfaceHeadId  and partnerDept.deleted='"+delid+"'    order by createTime";
				}
			}
		}
		List list = getHibernateTemplate().find(hql);
		return list;

	}

	public List listProviderByRegionOrByCity(final String region,
			final String city) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				/*
				 * //String hql = "from AreaDeptTree a where a.parentNodeId =
				 * (select nodeId from AreaDeptTree where node_type = 'area' and
				 * areaId = '" + cityId + "') "; String hql = "from AreaDeptTree
				 * a where a.parentNodeId = (select nodeId from AreaDeptTree
				 * where node_type = 'area' and areaId = '" + cityId + "') ";
				 * List list = getHibernateTemplate().find(hql); return list;
				 */

				String queryStr = " select name,id,interface_head_id from pnr_dept t where area_id='"
						+ city + "' order by area_id";

				SQLQuery countQuery = session.createSQLQuery(queryStr);
				List list = countQuery.list();
				if (list == null || list.isEmpty()) {
					queryStr = " select name,id,interface_head_id from pnr_dept t where area_id='"
							+ region + "' order by area_id";
					countQuery = session.createSQLQuery(queryStr);
					list = countQuery.list();
				}
				if (list == null || list.isEmpty()) {
					queryStr = " select name,id,interface_head_id from pnr_dept t order by area_id";
					countQuery = session.createSQLQuery(queryStr);
					list = countQuery.list();
				}

				return list;

			}
		};
		return (List) getHibernateTemplate().execute(callback);

	}
	/**
	 * 
	*@Title: getNextLevecCompByAreaid 
	*@Description: 根据areaid和树当前节点来获取部门,
	*@param parentdeptid：当前树节点的deptUUid
	*@param delid：是否删除标志,"0"-未删除,"1"-删除
	*@param areaId：区域id
	*@return ArrayList     
	*@author fengguangping 2013.04.19
	*@throws
	 */
	public List getNextLevecCompByAreaid(String parentdeptid, String delid,String areaId,int isPartner) {
		List list=null;
		String hql="";
		String baseSql = "from IPnrPartnerAppOpsDept partnerDept where  partnerDept.interfaceHeadId ";
		
		if ("-1".equals(parentdeptid)) {
			hql = baseSql
			+ " =  partnerDept.id  "
			+ "  and partnerDept.deptLevel = '1' and partnerDept.deleted='"
			+ delid + "'     and partnerDept.areaId like '"
			+ areaId.substring(0,PartnerPrivUtils.AreaId_length_Province) + "%'";
			
		}else {
			//deptlevel=1 <----> areaId.length()==2
			//deptlevel=2 <----> areaId.length()==4
			//deptlevel=3 <----> areaId.length()==6
			//deptlevel=4 <----> 无areaid长度与之对应;
			IPnrPartnerAppOpsDept dept=this.getPartnerDept(parentdeptid);
			String deptLevel=StaticMethod.null2String(dept.getDeptLevel(),"1");
			String nextdeptLevel=Integer.valueOf(dept.getDeptLevel())+1+"";
			String areaStr="";
			if ("2".equals(nextdeptLevel)) {
				if (areaId.length()>=PartnerPrivUtils.AreaId_length_City) {
					areaStr=areaId.substring(0, PartnerPrivUtils.AreaId_length_City);
				}
				hql = baseSql+"='"+ parentdeptid+ "' and partnerDept.deptLevel='"+nextdeptLevel+"' and partnerDept.deleted='" +delid+
						"' and partnerDept.areaId like '"+ areaId+"%' ";
			}else if ("3".equals(nextdeptLevel)) {
				if (areaId.length()==PartnerPrivUtils.AreaId_length_County) {
					areaStr=areaId.substring(0, PartnerPrivUtils.AreaId_length_County);
				}
				hql = baseSql+"='"+ parentdeptid+ "' and partnerDept.deptLevel='"+nextdeptLevel+"' and partnerDept.deleted='" +delid+
					"' and partnerDept.areaId like '"+ areaId+"%' ";
			}else if ("4".equals(nextdeptLevel)) {
				hql = baseSql+"='"+ parentdeptid+ "' and partnerDept.deptLevel='"+nextdeptLevel+"' and partnerDept.deleted='" +delid+
					"' and partnerDept.areaId like '"+ areaId+"%' ";
			}
		}
		if(isPartner!=-1){
			hql+=" and isPartner="+isPartner;
		}
		hql+=" order by createTime";
		
		list = getHibernateTemplate().find(hql);
		return list;
	}
}