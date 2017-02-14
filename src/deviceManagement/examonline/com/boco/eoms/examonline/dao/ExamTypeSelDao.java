package com.boco.eoms.examonline.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.user.dao.hibernate.TawSystemUserDaoHibernate;
import com.boco.eoms.examonline.model.ExamConfig;
import com.boco.eoms.examonline.model.ExamContent;
import com.boco.eoms.examonline.model.ExamInfo;
import com.boco.eoms.examonline.model.ExamIssue;
import com.boco.eoms.examonline.model.ExamSubmit;
import com.boco.eoms.examonline.model.ExamTypeSel;
import com.boco.eoms.examonline.model.ExamWarehouse;
import com.boco.eoms.examonline.model.TawSystemDeptExam;
import com.boco.eoms.examonline.qo.ExamDeptQueryVO;
import com.boco.eoms.examonline.qo.ExamTesterQueryVO;
import com.boco.eoms.examonline.util.ExamUtil;
/** 
 * Description:  
 * Copyright:   Copyright (c)2009 
 * Company:     BOCO 
 * @author:     LEE 
 * @version:    1.0 
 * Create at:   
 */
public class ExamTypeSelDao extends BaseDaoHibernate{
	public ExamTypeSelDao() {
	  }

	  public ExamWarehouse loadSingleOfExamWarehouse(String subId)
	      throws HibernateException {
	    //onlineWarehouse = (ExamWarehouse)getHibernateTemplate().load(ExamWarehouse.class ,subId);
		  ExamWarehouse onlineWarehouse= (ExamWarehouse)getHibernateTemplate().get(ExamWarehouse.class ,subId);
	    onlineWarehouse.setTitle(StaticMethod.null2String(onlineWarehouse.getTitle()) );
	    onlineWarehouse.setOptions(StaticMethod.null2String(onlineWarehouse.getOptions()) );
	    onlineWarehouse.setImage(StaticMethod.null2String(onlineWarehouse.getImage()));
	    onlineWarehouse.setComment( StaticMethod.null2String(onlineWarehouse.getComment()));
	    onlineWarehouse.setResult( StaticMethod.null2String(onlineWarehouse.getResult()));
	    return onlineWarehouse;
	  }
	  
		private String getCountQuery(String hql) {
			String countHql = "";
			int fromIndex = hql.indexOf("from");
			int orderbyIndex = hql.indexOf("order by");
			
			if(orderbyIndex > 0){
				hql = hql.substring(0,orderbyIndex);
			}
			int groupbyIndex = hql.indexOf("group by");
			if(groupbyIndex < 0){
				countHql = "select count(*) " + hql.substring(fromIndex);
			}else{
				countHql = "select count(*) from " + hql + "";
			}
			return countHql;
		}
		
		private String getCountSqlQuery(String sql) {
			String countHql = "";
			int fromIndex = sql.indexOf("from");
			int orderbyIndex = sql.indexOf("order by");
			
			if(orderbyIndex > 0){
				sql = sql.substring(0,orderbyIndex);
			}
			int groupbyIndex = sql.indexOf("group by");
			if(groupbyIndex < 0){
				countHql = "select count(*) " + sql;
			}else{
				countHql = "select count(*)+0 from (" + sql + ")";
			}
			return countHql;
		}
		
	  
		/**
		 * 分页公用代码
		 * @param hql    
		 * @param params  参数
		 * @param offset  分页偏移量
		 * @param pagesize 每页页数
		 * @return
		 */
	  public PageModel searchPaginated(final String hql, final Object[] params, 
			  final int offset,final int pagesize) {
		  		HibernateCallback callback = new HibernateCallback() {
		  			public Object doInHibernate(Session session) throws HibernateException {
		  				//获取记录总数
		  				String countHql = getCountQuery(hql);
		  				Query countquery = session.createQuery(countHql);
		  				if (params != null && params.length > 0) {
		  					for (int i = 0; i < params.length; i++) {
		  						countquery.setParameter(i, params[i]);
		  					}
		  				}
		  				Object a = countquery.uniqueResult();
		  				int total = (Integer)a;
		  				 
		  				// 获取当前页的结果集
		  				Query query  = session.createQuery(hql);
		  				if (params != null && params.length > 0) {
		  					for (int i = 0; i < params.length; i++) {
		  						query.setParameter(i, params[i]);
		  					}
		  				}
		  				query.setFirstResult(offset);
		  				query.setMaxResults(pagesize);
		  				List datas = query.list();
		  				
		  				PageModel pm = new PageModel();
		  				pm.setTotal(total);
		  				pm.setDatas(datas);
		  				return pm;
		  			}
		  		};
		  	return (PageModel) getHibernateTemplate().execute(callback);
		}

	  public PageModel searchSqlPaginated(final String sql, final Object[] params, 
			  final int offset,final int pagesize) {
		  		HibernateCallback callback = new HibernateCallback() {
		  			public Object doInHibernate(Session session) throws HibernateException {
		  				//获取记录总数
		  				String countHql = getCountSqlQuery(sql);
		  				Query countquery = session.createSQLQuery(countHql);
		  				if (params != null && params.length > 0) {
		  					for (int i = 0; i < params.length; i++) {
		  						countquery.setParameter(i, params[i]);
		  					}
		  				}
		  				Object a = countquery.uniqueResult();
		  				String countStr= (a+"").substring(0,(a+"").indexOf("."));
		  				int total = Integer.parseInt(countStr);
		  				 
		  				// 获取当前页的结果集
		  				Query query  = session.createSQLQuery(sql);
		  				if (params != null && params.length > 0) {
		  					for (int i = 0; i < params.length; i++) {
		  						query.setParameter(i, params[i]);
		  					}
		  				}
		  				query.setFirstResult(offset);
		  				query.setMaxResults(pagesize);
		  				List datas = query.list();
		  				
		  				PageModel pm = new PageModel();
		  				pm.setTotal(total);
		  				pm.setDatas(datas);
		  				return pm;
		  			}
		  		};
		  	return (PageModel) getHibernateTemplate().execute(callback);
		}
	  
	  
	  /**
	   * 取出题库中所有学习类型的题目
	   * @return List
	   */
	  public List getSubjectlist( String condition , int issueType ) throws HibernateException {
	    List sublist = null;
	    sublist =getHibernateTemplate().find(
	        "from ExamWarehouse onlinewarehouse where onlinewarehouse.issueType="
	        + issueType + " and onlinewarehouse.deleted=0" + condition);
	    return sublist;
	  }
	  public List<ExamTypeSel> getExamTypeSelList(String hql) throws HibernateException {
		  return getHibernateTemplate().find(hql);
	  }
	  public List<ExamWarehouse> getExamList(final String hql){
		  return  getHibernateTemplate().find(hql);
	  }
	  /**
	   * 保存生成的ExamTypeSel
	   * @param examTypeSel 生成的ExamTypeSel
	   */
	  public void addTypeSel(ExamTypeSel examTypeSel){
		  getHibernateTemplate().save(examTypeSel);
	  }
	  /**
	   * 返回用户的学习资料信息
	   * @param userId String
	   * @throws HibernateException
	   * @return List
	   */
	  public List getUserContent(String userId , String issueId) throws HibernateException {
	    List Contentlist = null;

	    Contentlist =getHibernateTemplate().find(
	        "from ExamContent examContent where examContent.userId ='" +
	        userId + "' and examContent.issueId = '"+ issueId +"'");
	    return Contentlist;
	  }

	  /**
	   * 按用户分组取每个用户测试成绩,按成绩多高到低排序
	 * @return
	 * @throws HibernateException
	 */
	public List getTopTen() throws HibernateException{
	    List toplist = null;

	    toplist =getHibernateTemplate().find(
	        "select sum(examContent.mark),examContent.userId from ExamContent examContent " +
	        "where examContent.issueId='study' group by examContent.userId order by sum(examContent.mark) desc");
	    return toplist;
	  }

	  public List getAllExamInfo() throws HibernateException{
	    List infolist = null;

	    infolist = getHibernateTemplate().find(
	      "from ExamInfo onlineInfo where onlineInfo.deleted=0 and onlineInfo.auditing=2");
	    return infolist;
	  }
	public int count(String hql)
	{
		List l=getHibernateTemplate().find(hql);
		return l.size();
	}
	  public List getInfoList(String condition) throws HibernateException{
	    List infolist = null;

	    infolist = getHibernateTemplate().find(
	      "from ExamInfo onlineInfo where onlineInfo.deleted=0 " + condition);

	    return infolist;
	  }


	  public List getAllExamWarehouse() throws HibernateException{
	    List onlineWarehouselist = null;

	    onlineWarehouselist = getHibernateTemplate().find(
	      "from ExamWarehouse onlineWarehouse where onlineWarehouse.deleted=0");
	/*
	    Iterator iterator = onlineWarehouselist.iterator();
	    while( iterator.hasNext() ){
	      ExamWarehouse onlineWarehouse = new ExamWarehouse();
	      onlineWarehouse = (ExamWarehouse)iterator.next();
	      onlineWarehouse.setTitle( StaticMethod.getGBString( onlineWarehouse.getTitle().trim() ) );
	      onlineWarehouse.setOptions( StaticMethod.getGBString( onlineWarehouse.getOptions().trim() ) );
	      onlineWarehouse.setComment( StaticMethod.getGBString( onlineWarehouse.getComment().trim() ) );
	    }*/

	    return onlineWarehouselist;
	  }

	  public void deleteSubs(String checkSel) throws HibernateException{
	    List updatelist = null;

	    updatelist = getHibernateTemplate().find( "from ExamWarehouse onlineWarehouse "
	       + "where onlineWarehouse.subId in ("+ checkSel +")" );

	    Iterator iterator = updatelist.iterator();
	    while( iterator.hasNext() ){
	      ExamWarehouse onlineWarehouse = new ExamWarehouse();
	      onlineWarehouse = (ExamWarehouse)iterator.next();
	      onlineWarehouse.setDeleted(1);
	    }

	  }

	  public void batchDelete(String batchSel) throws HibernateException{
	    List updatelist = null;
	    ExamInfo onlineInfo = new ExamInfo();
	    onlineInfo = (ExamInfo)getHibernateTemplate().load(ExamInfo.class,batchSel );
	    onlineInfo.setDeleted(1);

	    updatelist = getHibernateTemplate().find( "from ExamWarehouse onlineWarehouse "
	         + "where onlineWarehouse.importId in ('"+ onlineInfo.getImportId() +"')" );

	    Iterator iterator = updatelist.iterator();
	    while( iterator.hasNext() ){
	      ExamWarehouse onlineWarehouse = new ExamWarehouse();
	      onlineWarehouse = (ExamWarehouse)iterator.next();
	      onlineWarehouse.setDeleted(1);
	    }
	    getHibernateTemplate().update(onlineInfo);
	   // update(onlineInfo);   //系统会将本次进程中的所有对象update
	  }

	  public void updateImage(String subId , String value) throws HibernateException{
	    ExamWarehouse onlineWarehouse = (ExamWarehouse)getHibernateTemplate().load(ExamWarehouse.class ,subId);
	    onlineWarehouse.setImage(StaticMethod.getString(value));
	    getHibernateTemplate().update(onlineWarehouse);
	  }

	  /**旧,提取考卷信息
	 * @param currentTime
	 * @return
	 * @throws HibernateException
	 */
	public ExamConfig getIssueInfo(String currentTime) throws HibernateException{
	   
	    String hql="from ExamConfig onlineConfig where onlineConfig.startTime <= '"
	         + currentTime + "' and onlineConfig.endTime > '" + currentTime + "'";
	   List l=getHibernateTemplate().find(hql);
	   if(l.size()>0)
		   return (ExamConfig)l.get(0);
	    return null;
	  }
	public ExamConfig getIssueInfoByIssueId(String issueId) throws HibernateException{
		   
	   /* String hql="from ExamConfig onlineConfig where onlineConfig.startTime <= '"
	         + currentTime + "' and onlineConfig.endTime > '" + currentTime + "'";*/
	  
	    return (ExamConfig)getHibernateTemplate().get(ExamConfig.class, issueId);
	  }
	  /**
	   * 返回查询用户总分，正确数，正确率等信息
	   * @param condition String
	   * @throws HibernateException
	   * @return List
	   */
	  public List getStudyQOlist(String condition) throws HibernateException{
	    List qolist = null;
	    qolist = getHibernateTemplate().find("select sum(content.mark),sum(content.right),count(*),content.userId "
	         + "from ExamContent content where " + condition
	         + " group by content.userId order by sum(content.mark) desc,count(*) desc");
	    return qolist;
	  }

	  public List getConfiglist(String timeLimit) throws HibernateException{
	    List configlist = null;
	    String sql = timeLimit == "" ? "from ExamConfig config order by config.startTime desc"
	        : "from ExamConfig config where config.endTime<='"+timeLimit+"' order by config.startTime desc";
	    configlist = getHibernateTemplate().find(sql);
	    return configlist;
	  }
	  public List query(String hql)
	  {
		  return getHibernateTemplate().find(hql);
	  }
	public Object load(Class c,Serializable s)
	{
		return getHibernateTemplate().get(c, s);
	}
	  public List getConfiglistExamUnfinished(String timeLimit) throws HibernateException{
	    List configlist = null;
	    String sql = "from ExamConfig config where config.endTime>='"
	        +timeLimit+"' order by config.startTime desc";
	    configlist = getHibernateTemplate().find(sql);
	    return configlist;
	  }

	  public List getConfiglistBetweenOfIssue(String timeLimit) throws HibernateException{
	    List configlist = null;
	    String sql = "from ExamConfig config where config.issueStarttime<='"
	        +timeLimit+"' and config.issueEndtime>='"+timeLimit+"' order by config.startTime desc";
	    configlist = getHibernateTemplate().find(sql);
	    return configlist;
	  }


	  public void delExamInfo(String importId) throws HibernateException{
		 
		
		  String hql = "delete ExamInfo onlineInfo where onlineInfo.importId in("+ importId +")";
		  getHibernateTemplate().bulkUpdate(hql);
	  }
	  public void update(Object onlineInfo)
	  {
		  getHibernateTemplate().update(onlineInfo);
	  }

	  public void delExamWarehouseOfBatch(String importId) throws HibernateException{
	    String hql = "delete ExamWarehouse warehouse where warehouse.importId in("+ importId +")";
	    getHibernateTemplate().bulkUpdate(hql);
	  }
	  
		public void delete(String hql){
			getHibernateTemplate().bulkUpdate(hql);
		}
		public void delete(Object o){
			 getHibernateTemplate().delete(o);
		}
		public Serializable save(Object o){
			return getHibernateTemplate().save(o);
		}
	  public void delExamWarehouseOfSingle(String subId) throws HibernateException{
		  String hql = "delete from ExamWarehouse warehouse where warehouse.subId in("+ subId +")";
		  getHibernateTemplate().bulkUpdate(hql);
	  }

	  public void delExamAllInfo(String issueId) throws HibernateException{
		  getHibernateTemplate().bulkUpdate("delete from ExamConfig config where config.issueId ='"+ issueId +"'");
		  getHibernateTemplate().bulkUpdate("delete from ExamContent content where content.issueId ='"+ issueId +"'");
	  }

	  /**
	   * 获取试题数量
	   * @param specialtySel
	   * @param manufacturerSel
	   * @param difficulty
	   */
	  public int getExamCount(String  specialtySel,int manufacturerSel,int difficulty,int contype){
		  String hql = "select count(*) from ExamWarehouse where specialty=? and manufacturer=? and difficulty=? and contype=?";
		  List list = this.getHibernateTemplate().find(hql, new Object[]{specialtySel,manufacturerSel,difficulty,contype});
		  if(list != null && list.size()>0){
			  return Integer.parseInt(list.get(0).toString());
		  }
		  return 0;
	  }
	  
	  /**
	   * 根据部门查询考试成绩
	   * @param issueId  用逗号分隔的issueId
	   * @return
	   */
	  public List<ExamDeptQueryVO> findExamDeptQuery(String issueId){
		  String[] issueIdArray = issueId.split(",");
		  List<ExamDeptQueryVO> list = new ArrayList<ExamDeptQueryVO>();
		  for(int i=0;i<issueIdArray.length;i++){
			  String issueIdStr = issueIdArray[i];
			  ExamDeptQueryVO vo = new ExamDeptQueryVO();
			  vo.setIssueId(issueIdStr);
			  
			  ExamConfig config = (ExamConfig)this.getHibernateTemplate().get(ExamConfig.class, issueIdStr);
			  vo.setCompanyName(ExamUtil.examCompanyMap.get(config.getCompanyId()).toString());
			  vo.setTesterCountAll(config.getTesterCount());
			  //根据issueId查询该次考试提交了考试的参考人总数
			  String submithql = "select count(userId) from ExamSubmit where issueId=?";
			  List testerCountList = this.getHibernateTemplate().find(submithql, issueIdStr);
			  int testerCount = 0;
			  if(testerCountList !=null && testerCountList.size() > 0){
				  testerCount = Integer.parseInt(testerCountList.get(0).toString());
			  }
			  vo.setTesterCount(testerCount);
			  vo.setNotesterCount(vo.getTesterCountAll()-testerCount);
			  //根据issueId查询该次考试参考人总分
			  String contenthql = "select sum(mark) from ExamContent where issueId=?";
			  List contentList = this.getHibernateTemplate().find(contenthql, issueIdStr);
			  
			  int totalPoint = 0;
			  if(contentList !=null && contentList.size() > 0){
				  if(contentList.get(0) != null){
					  totalPoint = Integer.parseInt(contentList.get(0).toString());
				  }
			  }
			  //计算平均分
			  if(vo.getTesterCount() == 0){
				  vo.setAveragePoint("0");
			  }else{
				  vo.setAveragePoint(new java.text.DecimalFormat("0").format(totalPoint/vo.getTesterCount()));
			  }
			  list.add(vo);
		  }
		  return list;
	  }
	  
	  /**
	   * 查询某次考试各单位所有参考人员的总体考试情况
	   * @param issueId  用逗号分隔的issueId
	   * @return
	   */
	  public List<ExamTesterQueryVO> findExamTesterQuery(String issueId){ 
		  TawSystemUserDaoHibernate tsudh=(TawSystemUserDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemUserDao");
		  String[] issueIdArray = issueId.split(",");
		  List<ExamTesterQueryVO> list = new ArrayList<ExamTesterQueryVO>();
		  for(int i=0;i<issueIdArray.length;i++){
			  String issueIdStr = issueIdArray[i];
			  ExamTesterQueryVO vo = null;
			  ExamConfig config = (ExamConfig)this.getHibernateTemplate().get(ExamConfig.class, issueIdStr);
			  String companyName = ExamUtil.examCompanyMap.get(config.getCompanyId()).toString();
			  
			  String submithql = "from ExamSubmit where issueId=?";
			  List submitList = this.getHibernateTemplate().find(submithql, issueIdStr);
			  for(int j=0;j<submitList.size();j++){
				  vo = new ExamTesterQueryVO();
				  vo.setCompanyName(companyName);
				  
				  ExamSubmit submit = (ExamSubmit)submitList.get(j);
				  vo.setName(tsudh.getUserByuserid(submit.getUserId()).getUsername());
				  
				  //实际参考人提交时间
				  Timestamp submitTime = submit.getSubmitTime();
				  //考试开考时间
				  Timestamp startTime = config.getStartTime();
				  //计算考试时间
				  long distime = submitTime.getTime() - startTime.getTime();
				  vo.setExamTime(distime/(1000*60)+"分钟");
				  
				  String contentHql = "select sum(mark) from ExamContent where issueId=? and userId=?"; 
				  String totalPoint = this.getHibernateTemplate().find(contentHql, 
						  new Object[]{issueIdStr,submit.getUserId()}).get(0).toString();
				  vo.setPoint(totalPoint);
				  
				  list.add(vo);
			  }
		  }
		  //按分数排序
		  Collections.sort(list);   
		  return list;
	  }
	  
	  /**
	   * 翻页并获取试题
	   * @param userId
	   * @param issueId
	   * @param pagePra
	   * @return
	   * @throws Exception
	   */
	  public List getExamsubs(final String userId, final String issueId, final int[] pagePra) throws Exception {
			
			HibernateCallback callback = new HibernateCallback() {
	  			public Object doInHibernate(Session session) throws HibernateException {
	  				List sublist = null;
	  				String hsql =
	  				    "from ExamIssue issue where issue.subId in("
	  				    + "select examContent.subId from ExamContent examContent where "
	  				    + "examContent.userId='" + userId +
	  				    "' and examContent.issueId in ('"
	  				    + issueId + "'))" +
	  				    " order by issue.contype";
	  				Query query = session.createQuery(hsql);
	  				query.setFirstResult(pagePra[0]);
	  				query.setMaxResults(pagePra[1]);
	  				sublist = query.list();
	  				if (pagePra[2] <= 0) {
	  				  pagePra[2] = session.createQuery(hsql).list().size();//把长度放到第二个位置
	  				}
	  			    //将打乱的选项放入options中
	  				Iterator iterator = sublist.iterator();
	  				while (iterator.hasNext()) {
	  					ExamIssue issue = (ExamIssue) iterator.next();
	  					String hql="from ExamContent examContent where examContent.issueId='"+issueId+"' and examContent.subId='"
	  						+ issue.getSubId()+"' and "+"examContent.userId='"+userId+"'";
	  					ExamContent examContent = (ExamContent)session.createQuery(hql).uniqueResult();
	  				  //如果是非主观题，则将打乱的选项放入ExamIssue的options中
	  				  if (examContent != null && issue.getContype() !=4 && issue.getContype() !=5) {
	  					issue.setOptions(examContent.getOptions());
	  				  }
	  				}
	  				return sublist;
	  			}
	  		};
	  		return (List) getHibernateTemplate().execute(callback);
		}
	  
		/**
		 * 根据部门ID和删除标志 查询某部门信息
		 * 
		 * @param deptid
		 * @param delid
		 *            0表示全部查询 1表示查询停职或被删除的
		 * @return
		 */
		public TawSystemDeptExam getDeptinfobydeptid(String deptid, String delid) {
			TawSystemDeptExam sysdept = null;
			String hql = " from TawSystemDeptExam sysdept where sysdept.deptId='"
					+ deptid + "'" + " and sysdept.deleted='" + delid + "'";
			List list = getHibernateTemplate().find(hql);
			if (list != null) {
				if (!list.isEmpty()) {
					sysdept = (TawSystemDeptExam) list.iterator().next();
				}
			}
			return sysdept;
		}
		/**
		 * 获取实时联考管理员统计列表
		 * @param condition
		 * @return
		 */
		public PageModel getAdminSelectExamList(Map condition){
				StringBuffer hql = new StringBuffer("select c from ExamConfig c, TawSystemUser u where c.issueUser=u.userid ");
				Object starttime = condition.get("starttime");
				if(starttime != null && !"".equals(starttime.toString())){
					hql.append(" and c.startTime>='").append(condition.get("starttime")).append(" 00:00:00'");
				}
				Object endtime = condition.get("endtime");
				if(endtime != null && !"".equals(endtime.toString())){
					hql.append(" and c.endTime<='").append(condition.get("endtime")).append(" 23:59:59'");
				}
				Object provtype = condition.get("provtype");
				if(provtype != null && !"".equals(provtype.toString())){
					if("1".equals(provtype.toString())){
						hql.append(" and u.deptid in (" + ExamUtil.provDeptStr + ") ");
		    		}else if("2".equals(provtype.toString())){
		    			hql.append(" and u.deptid in (" + ExamUtil.netDeptStr + ") ");
		    		}else{
		    			hql.append(" and u.deptid not in (" + ExamUtil.provDeptStr + "," + ExamUtil.netDeptStr + ") ");
		    		}
				}
				Object company = condition.get("company");
				if(company != null && !"".equals(company.toString())){
					hql.append(" and c.companyId='").append(company.toString()).append("'");
				}
				Object specialty = condition.get("specialty");
				if(specialty != null && !"".equals(specialty.toString())){
					hql.append(" and c.typeSel like '%").append(specialty.toString()).append("%'");
				}
				hql.append(" order by c.startTime desc");
				PageModel pm = this.searchPaginated(hql.toString(), null, Integer.parseInt(condition.get("pageIndex").toString()),
						Integer.parseInt(condition.get("pageSize").toString()));
				List sublist = pm.getDatas();
				for(int i=0;i<sublist.size();i++){
					ExamConfig config = (ExamConfig)sublist.get(i);
					//根据issueId查询该次考试提交了考试的参考人总数
	  				String submithql = "select count(userId) from ExamSubmit where issueId='"+config.getIssueId()+"'";
	  	  			List testerCountList =this.getHibernateTemplate().find(submithql);
	  	  			int testerCount = 0;
	  	  			if(testerCountList !=null && testerCountList.size() > 0){
	  	  				testerCount = Integer.parseInt(testerCountList.get(0).toString());
	  	  			}
	  	  			config.setNotestedCount(config.getTesterCount()-testerCount);
				}
				return pm;
			}
		
		public int findExamWarehouseCount(String spec,String manu,String diff,String contype){
			String hql = "select count(*) from ExamWarehouse where issueType=2 and deleted=0 and specialty=? " +
					"and manufacturer=? and difficulty=? and contype=? ";
			Object[] params = {spec,Integer.parseInt(manu),Integer.parseInt(diff),Integer.parseInt(contype)};
			List list = this.getHibernateTemplate().find(hql, params);
			return Integer.parseInt(list.get(0).toString());
		}
}
