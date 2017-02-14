package com.boco.eoms.sheet.base.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.util.xml.XMLProperties;
import com.boco.eoms.commons.util.xml.XmlManage;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.sheet.base.dao.IMainDAO;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.util.Constants;
import com.boco.eoms.sheet.base.util.QuerySqlInit;

/**
 *  
 */
public abstract class MainDAO extends BaseSheetDaoHibernate implements IMainDAO {

	public BaseMain loadSinglePO(String id, Object obj) throws HibernateException{
		BaseMain main = (BaseMain)getHibernateTemplate().get(obj.getClass(),id);
		return main;
	}

	@SuppressWarnings("unchecked")
	public BaseMain loadSinglePOByProcessId(String processId, Object obj)
			throws HibernateException{
		BaseMain main = new BaseMain();
		String sql = "from "+obj.getClass().getName()+" as main where main.piid ='"
				+ processId + "'";
		List list = getHibernateTemplate().find(sql);
		if (list.size() == 0) {
			main = null;
		} else {
			main = (BaseMain) list.get(0);
		}
		return main;
	}

	public void deleteMain(String id, Object mainObject) throws HibernateException{		
		BaseMain main = (BaseMain) getHibernateTemplate().get(BaseMain.class,id);
		main.setDeleted(new Integer(1));
		getHibernateTemplate().save(main);			
	}
	
	@SuppressWarnings("unchecked")
	public Map getHolds(final Map condition,final Integer curPage, final Integer pageSize, final Object mainObject)
			throws HibernateException {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				//添加查询的变量
				String beanName = StaticMethod.nullObject2String(condition.get("beanName"));
				String variables = QuerySqlInit.getAllDictItemsName(beanName);
				
				String sheetQueryRights = StaticMethod.nullObject2String(condition.get("sheetQueryRights")) ;
				String selectStr = "select "+variables+" ";
				String selectCountStr = "select count(*) ";
				String queryStr = "";
				if("true".equals(sheetQueryRights)){
					queryStr = " from " + mainObject.getClass().getName() + " main,PnrSheetQuery query where main.id=query.mainId and "
						+ "main.templateFlag <> 1 and (main.status=:status or main.status=:status1) and main.deleted<>'1'";
					String userDeptId = StaticMethod.nullObject2String(condition.get("userDeptId")) ;
					if(PartnerPrivUtils.isPartnerDept(userDeptId)){//如果是代维公司
						int level = PartnerPrivUtils.getPartnerDeptLevel(userDeptId);
						if(PartnerPrivUtils.Level_provinceDept == level){
							queryStr += " and query.provincePnrDept='"+userDeptId+"'";
						}else if(PartnerPrivUtils.Level_city == level){
							queryStr += " and query.cityPnrDept='"+userDeptId+"'";
						}else if(PartnerPrivUtils.Level_County== level){
							queryStr += " and query.countryPnrDept='"+userDeptId+"'";
						}else if(PartnerPrivUtils.Level_group == level){
							queryStr += " and query.groupPnrDept='"+userDeptId+"'";
						}
					}else{ //如果是移动公司
						String userAreaId = StaticMethod.nullObject2String(condition.get("userAreaId")) ;
						String userId = StaticMethod.nullObject2String(condition.get("userId")) ;
						PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) ApplicationContextHolder.getInstance().getBean("pnrBaseAreaIdList");
						String rootAreaId = pnrBaseAreaIdList.getRootAreaId();
						
						//如果不是admin并且所在的地域不是根地域
						if(!"admin".equals(userId) && !rootAreaId.equals(userAreaId)){
							if(userAreaId.length() == (rootAreaId.length()+2)){ //如果是地市
								queryStr += " and main.mainCity='"+userAreaId+"' ";
							}else if(userAreaId.length() == (rootAreaId.length()+4)){
								queryStr += " and main.mainCountry='"+userAreaId+"' ";
							}
						}
					}
				}else{
					//不带权限的归档工单查询
					queryStr = " from " + mainObject.getClass().getName() + " main where "
							+ "main.templateFlag <> 1 and (main.status=:status or main.status=:status1) and main.deleted<>'1'";
				}
				
				Query countQuery = session.createQuery(selectCountStr + queryStr);
				//设置归档标记
				countQuery.setInteger("status", Constants.SHEET_HOLD.intValue());
				countQuery.setInteger("status1", Constants.ACTION_FORCE_HOLD);
				Object countObj = countQuery.uniqueResult();
				Integer count = Integer.parseInt(countObj.toString());
				
				String orderCondition = StaticMethod.nullObject2String(condition.get("orderCondition")) ;
				StringBuffer hql = new StringBuffer(selectStr + queryStr);
				if(pageSize.intValue()!=-1){
					if(!orderCondition.equals("")){
						hql.append(" order by "+orderCondition);
					}
					else{ 
						hql.append(" order by main.sendTime desc");
					}
				}
				Query query = session.createQuery(hql.toString());
				//设置归档标记
				query.setInteger("status", Constants.SHEET_HOLD.intValue());
				query.setInteger("status1", Constants.ACTION_FORCE_HOLD);
				
				if(pageSize.intValue()!=-1){
					query.setFirstResult(pageSize.intValue()* (curPage.intValue()));
					query.setMaxResults(pageSize.intValue());
				}
				List list = query.list();
				
				Map map = new HashMap();
				map.put("count", count);
				map.put("list", list);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
	/**
     * 根据查询条件查询任务信息, 不进行分页处理
     * @param hsql 查询语句
     * @throws HibernateException
     */	
    @SuppressWarnings("unchecked")
	public List getMainListBySql(String hql) throws HibernateException {
    	return this.getHibernateTemplate().find(hql);
    }

	public Integer getStarterCount(final String userId, final Object mainObject)
			throws HibernateException {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				//hql，取userId启动流程的数量
				//增加条件main.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
				String queryStr = "select count(main.id) from " + mainObject.getClass().getName()
						+ " main where " + " main.sendUserId=:sendUserId and main.status=:status and main.templateFlag <> 1 and (title is not null or title='') and main.deleted<>'1'";
				Query query = session.createQuery(queryStr);
				query.setString("sendUserId", userId);
				query.setInteger("status", Constants.SHEET_RUN.intValue());
				Integer total = (Integer) query.iterate().next();
				return total;
			}
		};
		return (Integer) getHibernateTemplate().execute(callback);
	}

	@SuppressWarnings("unchecked")
	public HashMap getStarterList(final String userId, final Integer curPage,
			final Integer pageSize, final Object mainObject, final HashMap condition) throws HibernateException {
		//增加条件main.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
		//增加查询变量
		String beanName = StaticMethod.nullObject2String(condition.get("beanName"));
		String variables = QuerySqlInit.getAllDictItemsName(beanName);
		String sqlstr = "select "+variables+" from " + mainObject.getClass().getName() + " main where main.templateFlag <> 1 and "
		+ " main.sendUserId='"+userId+"' and status=0 and (title is not null or title='') and main.deleted<>'1'";		
		String orderCondition = StaticMethod.nullObject2String(condition.get("orderCondition")) ;
		StringBuffer hql = new StringBuffer(sqlstr);
		if(pageSize.intValue()!=-1){
		if(!orderCondition.equals("")){
			hql.append(" order by "+orderCondition);
		}
		else{ 
			hql.append(" order by main.sendTime desc");
		}
		}
		return getMainListBySql(hql.toString(),curPage,pageSize);
  
	}

	@SuppressWarnings("unchecked")
	public List getQuerySheetByCondition(final String hsql, 
			final Integer curPage, final Integer pageSize, int[] aTotal, String queryType)
			throws HibernateException {
		if (aTotal[0] <= 0){
			aTotal[0] = ((Integer)count(hsql)).intValue();
			if (aTotal[0] <=0) {
				return null;
			}
		}
		if (queryType != null && queryType.equals("number")) {
			return null;
		}
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createQuery(hsql);
				//分页查询条
				if(pageSize.intValue()!=-1){
					query.setFirstResult(pageSize.intValue()
									* (curPage.intValue()));
					query.setMaxResults(pageSize.intValue());
				}
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	public List getSQLQuerySheetByCondition(final String hsql,final String queryDict, 
			final Integer curPage, final Integer pageSize, int[] aTotal, String queryType)
			throws HibernateException {
		if (aTotal[0] <= 0){
			aTotal[0] = ((Integer)countSql(hsql)).intValue();
			if (aTotal[0] <=0) {
				return null;
			}
		}
		if (queryType != null && queryType.equals("number")) {
			return null;
		}
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
//				String queryDict = getQueryDict("commontask");
//				String sqlQuery = " select id,sheetid,title,sendTime,sheetAcceptLimit,sheetCompleteLimit from (" + hsql + ")";
				String sqlQuery = " select " + queryDict + " from (" + hsql + ")";
				SQLQuery query = session.createSQLQuery(sqlQuery);
				//分页查询条
				if(pageSize.intValue()!=-1){
					query.setFirstResult(pageSize.intValue()
									* (curPage.intValue()));
					query.setMaxResults(pageSize.intValue());
				}
//				query.addEntity(Map.class);
				List list = new ArrayList();
			 try{
				  list = query.list();
			 }catch(Exception e){
                  e.printStackTrace();
			 }
			 List listTemp = getMapListForQuery(list,queryDict);
			 return listTemp;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	
	public Integer count(final String hsql) throws HibernateException {
		int sql_distinct = hsql.indexOf("distinct");
		int sql_index = hsql.indexOf("from");
		int sql_orderby = hsql.indexOf("order by");

		String countStrTmp;
		if (sql_distinct > 0){
			int sql_comma = (hsql.substring(sql_distinct, sql_index)).indexOf(",");
			if(sql_comma>0){
				countStrTmp = "select count("
					+ hsql.substring(sql_distinct, sql_distinct+sql_comma) + ") ";
			}else{
				countStrTmp = "select count("
					+ hsql.substring(sql_distinct, sql_index) + ") ";
			}
		}
		else{
			countStrTmp = "select count(*) ";
		}

		if (sql_orderby > 0)
			countStrTmp += hsql.substring(sql_index, sql_orderby);
		else
			countStrTmp += hsql.substring(sql_index);

		final String countStr = countStrTmp;
		
		HibernateCallback callback = new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException {
				Integer amount;
				Query query = session.createQuery(countStr);
			    List resultList = query.list();
				if (resultList!= null&& !resultList.isEmpty()) {
					amount = (Integer) resultList.get(0);
				} else
					amount = new Integer(0);
				return amount;
			}
		};
		return (Integer) getHibernateTemplate().execute(callback);
	}

	public Integer countSql(final String hsql) throws HibernateException {
//		int sql_distinct = hsql.indexOf("distinct");
//		int sql_index = hsql.indexOf("from");
//		int sql_orderby = hsql.indexOf("order by");
//
//		String countStrTmp;
//		if (sql_distinct > 0){
//			int sql_comma = (hsql.substring(sql_distinct, sql_index)).indexOf(",");
//			if(sql_comma>0){
//				countStrTmp = "select count("
//					+ hsql.substring(sql_distinct, sql_distinct+sql_comma) + ") ";
//			}else{
//				countStrTmp = "select count("
//					+ hsql.substring(sql_distinct, sql_index) + ") ";
//			}
//		}
//		else{
//			countStrTmp = "select count(*) ";
//		}
//
//		if (sql_orderby > 0)
//			countStrTmp += hsql.substring(sql_index, sql_orderby);
//		else
//			countStrTmp += hsql.substring(sql_index);
		int sql_index = hsql.indexOf("from");
		int sql_orderby = hsql.indexOf("order by");
	
		String countStrTmp = "select count(*) as c ";		
		if (sql_orderby > 0)
		countStrTmp += hsql.substring(sql_index, sql_orderby);
	    else
		countStrTmp += hsql.substring(sql_index);		

//		final String countStr = "select count(*) as c from (" + hsql + " )";
		final String countStr = countStrTmp;		
		
		HibernateCallback callback = new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException {
				Integer amount;
				SQLQuery query = session.createSQLQuery(countStr);
				query.addScalar("c",Hibernate.INTEGER);
			    List resultList = query.list();
				if (resultList!= null&& !resultList.isEmpty()) {
					amount = (Integer) resultList.get(0);
				} else
					amount = new Integer(0);
				return amount;
			}
		};
		return (Integer) getHibernateTemplate().execute(callback);
	}	
	
	public int getXYZ(final String presheetId, final Object mainObject) throws HibernateException {
		int sheetMaxSize=0;
		HibernateCallback callback = new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException {
			 String sql="select main.sheetId from "+ mainObject.getClass().getName() +" as main where main.sheetId like '"+
		           presheetId+"%' order by main.sheetId desc";
			 Query query = session.createQuery(sql);
			 Integer maxSize=null;
			 Iterator iterate=query.iterate();
			 if(iterate.hasNext()){
				   String localSheetId=StaticMethod.nullObject2String(iterate.next()).trim();
				   maxSize=new Integer(
							localSheetId.substring(localSheetId.lastIndexOf("-")+2,localSheetId.length()));
				}			
				return maxSize;			
			}
		};
		Integer maxSheet=(Integer) getHibernateTemplate().execute(callback);
		if(maxSheet!=null)sheetMaxSize=maxSheet.intValue();
		return sheetMaxSize;
	}	
	public BaseMain getMain(String id ,String className) throws Exception{
		return null;
	}
	/**
	 * 实现根据用户查找出他的所有模板
	 * @author wangjianhua
	 * @date 2008-7-22
	 */
	@SuppressWarnings("unchecked")
	public List getTemplatesByUserIds(String userId, final Integer curPage, final Integer pageSize, int[] aTotal, Object mainObject) throws HibernateException {
		
		final String hql="from "+ mainObject.getClass().getName() +" as main where main.templateFlag=1 and main.sendUserId='"+userId+"' order by sendTime desc";
		String countHql = "select count(*) from " + mainObject.getClass().getName() +" as main where main.templateFlag=1 and main.sendUserId='"+userId+"'";
		
		aTotal[0] = ((Integer)getHibernateTemplate().find(countHql).listIterator().next()).intValue(); 
			
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createQuery(hql);
				//分页查询条
				query.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				return query.list();
			}
		};
		
		return (List) getHibernateTemplate().execute(callback);
	}
	
	@SuppressWarnings("unchecked")
	public List getAllAttachmentsBySheet(String where) throws HibernateException {
		String hql="from TawCommonsAccessories s where s.accessoriesName in (" + where +") order by accessoriesUploadTime";
		return (List) getHibernateTemplate().find(hql);
	}
	/**
     * 根据查询条件查询任务信息, 并进行分页处理
     * @param hsql 查询语句
     * @param curPage 分页起始
     * @param pageSize 单页显示数量
     * @return HashMap, 包括任务总量和任务列表
     * @throws HibernateException
     */
	@SuppressWarnings("unchecked")
	public HashMap getMainListBySql(final String queryStr, 
	          final Integer curPage,final Integer pageSize)
	          throws HibernateException {   	   
	   HibernateCallback callback = new HibernateCallback() {
        public Object doInHibernate(Session session) throws HibernateException {        
            //获取任务总数的查询sql
        	HashMap map = new HashMap();
        try{
        	int sql_distinct = queryStr.indexOf("distinct");
        
    		int sql_index = queryStr.indexOf("from");
    		int sql_orderby = queryStr.indexOf("order by");

    		String queryCountStr;
    		if (sql_distinct > 0){
    			int sql_comma = (queryStr.substring(sql_distinct, sql_index)).indexOf(",");
    			if(sql_comma>0){
     			queryCountStr = "select count("
    					+ queryStr.substring(sql_distinct, sql_distinct+sql_comma) + ") ";
    			}else{
    				queryCountStr = "select count("
    					+ queryStr.substring(sql_distinct, sql_index) + ") ";
    			}
    		}
    		else{
    			queryCountStr = "select count(*) ";
    		}

    		if (sql_orderby > 0)
    			queryCountStr += queryStr.substring(sql_index, sql_orderby);
    		else
    			queryCountStr += queryStr.substring(sql_index);
    		  		
	        Integer totalCount;
	        
	        Query totalQuery = session.createQuery(queryCountStr);
	        List result = totalQuery.list();
			if (result!=null&&!result.isEmpty()) {
				totalCount = (Integer) result.get(0);
			} else{
				totalCount = new Integer(0);
	        
			}
		    Query query = session.createQuery(queryStr);
		    if(pageSize.intValue()!=-1){
		    query.setFirstResult(pageSize.intValue()
					              * (curPage.intValue()));
		    query.setMaxResults(pageSize.intValue());
		    }
		    List resultList = query.list();
		    
		    map.put("sheetTotal", totalCount);
		    map.put("sheetList", resultList);
        }
        catch(Exception e){
        	System.out.println("-------main list error!---------");
        	e.printStackTrace();
        }
		    return map;
        }
     };
     return (HashMap) getHibernateTemplate().execute(callback);
	}

	
	public void saveOrUpdateMain(Object main) throws HibernateException{
		BaseMain baseMain=(BaseMain)main;
		if ((baseMain.getId()==null)){
        		getHibernateTemplate().save(baseMain);
        		getHibernateTemplate().flush();
            	getHibernateTemplate().clear();
        }
		else{
			getHibernateTemplate().flush();
        	getHibernateTemplate().clear();
			getHibernateTemplate().saveOrUpdate(baseMain);
		}
	}
	/**
	 * 撤销工单列表数量
	 */
	public Integer getCancelCount(final Object mainObject) throws HibernateException {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				
		    	//取列表数量
				//增加条件alias.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
				String queryCountStr = "select count(main.id) from " + mainObject.getClass().getName()
						+ " main where (main.status=:status or main.status=:status1 or main.status=:status2) and main.deleted<>'1' ";
				
				Query query = session.createQuery(queryCountStr);
				//TODO 归档标记，需确认
				query.setInteger("status", Constants.ACTION_CANCEL);
				query.setInteger("status1", Constants.ACTION_FORCE_NULLITY);
				query.setInteger("status2", Constants.ACTION_NULLITY);
				Integer total = (Integer) query.iterate().next();
				
				return total;
				
			}
		};
		return (Integer) getHibernateTemplate().execute(callback);
	}
	/**
	 * 撤销工单列表
	 */
	@SuppressWarnings("unchecked")
	public List getCancelList(final Integer curPage, final Integer pageSize, final Object mainObject,final HashMap condition)
			throws HibernateException {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				//hql，以备资源名称＋系统角色＋资源类别＋本机管理IP地址为条件查询
				//增加条件main.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
				//增加查询变量
				String beanName = StaticMethod.nullObject2String(condition.get("beanName"));
				String variables = QuerySqlInit.getAllDictItemsName(beanName);				
				String queryStr = "select "+variables+" from " + mainObject.getClass().getName() + " main where "
						+ "(main.status=:status or main.status=:status1 or main.status=:status2) and main.deleted<>'1' ";
				String orderCondition = StaticMethod.nullObject2String(condition.get("orderCondition")) ;
				StringBuffer hql = new StringBuffer(queryStr);			
				if(pageSize.intValue()!=-1){
					if(!orderCondition.equals("")){
						hql.append(" order by "+orderCondition);
					}
					else{ 
						hql.append(" order by main.sendTime desc");
					}	
				}
				Query query = session.createQuery(hql.toString());
				//设置归档标记
				query.setInteger("status", Constants.ACTION_CANCEL);
				query.setInteger("status1", Constants.ACTION_NULLITY);
				query.setInteger("status2", Constants.ACTION_FORCE_NULLITY);
				
				//分页查询条
				if(pageSize.intValue()!=-1){
				query.setFirstResult(pageSize.intValue()* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				}
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	@SuppressWarnings("unchecked")
	public BaseMain getMainBySheetId(String sheetId, Object object) throws HibernateException{
		BaseMain main = null;
		String sql = "from " + object.getClass().getName() + " main where main.sheetId ='"
				+ sheetId + "'";
		List list = getHibernateTemplate().find(sql);
		if (list.size() > 0) {
			main = (BaseMain) list.get(0);
		}
		return main;
	}
	
	public void removeMain(Object mainObject) throws HibernateException{
		getHibernateTemplate().delete(mainObject);
	}
	
	/**
	 * 整合用
	 */
	@SuppressWarnings("unchecked")
	public List getDraftListByUserIds(String userId, final Integer curPage, final Integer pageSize, int[] aTotal, Object object) throws HibernateException {
		final String hql = " from " + object.getClass().getName() + " as main where main.status = " + Constants.SHEET_DRAFT
						 + " and main.sendUserId = '" + userId + "' order by main.sendTime desc";
		String countHql = "select count(*) from " + object.getClass().getName()+" as main where main.status = " + Constants.SHEET_DRAFT
						 + " and main.sendUserId = '" + userId + "'";
		aTotal[0] = ((Integer)getHibernateTemplate().find(countHql).listIterator().next()).intValue(); 
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createQuery(hql);
				query.setFirstResult(pageSize.intValue()* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	/**
	 *  根据工单类型从配置文件中读取需要查询的列
	 */
    public String getQueryDict(String sheetModel){
    	String queryDict = "";
//    	try{
//    		 queryDict = XmlManage.getFile("/config/sheet-query-task.xml")
//    		.getProperty(sheetModel);    	
//    	}catch(Exception e){
//            e.printStackTrace();
//		 }
    	String mainDict = "";
    	String linkDict = "";
//    	String taskDict = "";
    	Map map = new HashMap();
    	try{
    		XMLProperties queryProperties = XmlManage.getFile("/config/sheet-query-task.xml");
    		mainDict = queryProperties.getProperty("main");  
    		linkDict = queryProperties.getProperty("link");  
//    		taskDict = queryProperties.getProperty("task");  
    		queryDict = mainDict + "," + linkDict;
//    		map.put("taskDict", taskDict);
    	}catch(Exception e){
            e.printStackTrace();
		 }    	
    	return queryDict;
    }
    /**
     * 根据配置文件中定义的列，将sqlquery查询出来的list拼接为带map的list 
     */
    public List getMapListForQuery(List queryList,String queryDict){
    	List listTemp = new ArrayList();
    	String [] queryDictList = queryDict.split(",");
    	for(int i = 0; i < queryList.size();i++){
    		Map map = new HashMap();
			Object [] temp =  (Object[]) queryList.get(i);  
			for(int j = 0; j < queryDictList.length ; j++){
	    		map.put(queryDictList[j],temp[j]);
			}
			listTemp.add(map);
    	}
    	
    	return listTemp;
    }
}
