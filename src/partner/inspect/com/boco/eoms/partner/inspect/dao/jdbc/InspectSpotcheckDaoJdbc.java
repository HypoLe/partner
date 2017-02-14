package com.boco.eoms.partner.inspect.dao.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.inspect.dao.IInspectSpotcheckDaoJdbc;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 23, 2012 4:09:09 PM 
 */
public class InspectSpotcheckDaoJdbc extends JdbcDaoSupport implements IInspectSpotcheckDaoJdbc {
	/**
	 * 查询巡检抽检模板列表
	 * @param inspectTemplateId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findSpotcheckTemplateList(String inspectTemplateId,String year,String month){
		String sql = "select a.id spottmpid,a.bigitem_name itemgroup,a.mark_rule markrule ,a.score score,count(a.id) count " +
		"from pnr_spotcheck_template a " +
			"left join pnr_spotcheck_template_item b on a.id=b.spotcheck_template_id " +
			"left join pnr_inspect_template_item_his c on b.inspect_template_item_id=c.id  " +
		"where a.template_id='"+inspectTemplateId+"' and (a.invalid_time is null or a.invalid_time='') and (b.invalid_time is null or b.invalid_time='')" +
			"group by a.id,a.bigitem_name,a.mark_rule,a.score";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
		
		for (Map<String, Object> map : list) {
			String spotcheckTemplateId = map.get("spottmpid").toString();
			String itemSql = "select inspect_item item,inspect_content content from pnr_spotcheck_template_item b " +
					"left join pnr_inspect_template_item_his c on b.inspect_template_item_id=c.pnr_inspect_template_item_id " +
					"where b.spotcheck_template_id='"+spotcheckTemplateId+"' and c.month='"+month+"' and c.year='"+year+"'";
			List<Map<String,String>> itemList = this.getJdbcTemplate().queryForList(itemSql);
			map.put("itemList", itemList);
		}
		return list;
	}
	
	/**
	 * 查询抽检模板已关联以及未关联的巡检项
	 * @param inspectTemplateId
	 * @param spotcheckTemplateId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> findInspectPlanItemList(String inspectTemplateId,String spotcheckTemplateId){
		String sql = "select a.id itemid,a.inspect_item it,a.inspect_content content,b.spotcheck_template_id spottmpid " +
				"from pnr_inspect_template_item a " +
					"left join pnr_spotcheck_template_item b on a.id=b.inspect_template_item_id " +
					"left join  pnr_inspect_template c on a.template_id=c.id " +
				"where c.id='"+inspectTemplateId+"' and (b.spotcheck_template_id is null or b.spotcheck_template_id='' ";
		if(!StringUtils.isEmpty(spotcheckTemplateId)){
			sql+= " or b.spotcheck_template_id='"+spotcheckTemplateId+"' ";
		}
		sql += ")";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 查询出当前模板下的所有模板大项
	 * @param inspectTemplateId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findInspectTemplateAllBigItem(String inspectTemplateId,String year,String month){
		
		String sql="select a.inspect_template_bigitem_id as id,a.name,xxx.mark_rule,xxx.score,xxx.id as spotTemplateId,nvl(xxx.item_num,0) as itemNum,nvl(xxx.select_item_num,0) as selectitemnum from pnr_inspect_template_bitem_his a  left join " +
				"(select b.id,b.template_id,bigitem_name,b.mark_rule,b.score,b.invalid_time,b.item_num,b.select_item_num from pnr_spotcheck_template b where " +
				"(invalid_time is null or invalid_time='') and b.template_id='"+inspectTemplateId+"') xxx on " +
				"a.template_id=xxx.template_id and a.name=xxx.bigitem_name where a.template_id='"+inspectTemplateId+"' and a.year="+year+" and a.month="+month+"";
		
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	public List getAllInspectTemplateItem(String templateBigItemId,String inspectTemplateId,String year,String month){
		String sql ="select a.pnr_inspect_template_item_id as TEMPLATEID,a.inspect_item as INSPECT_ITEM,a.inspect_content as INSPECT_CONTENT,XXX.id as ID from pnr_inspect_template_item_his a left join " +
		" (select id,spotcheck_template_id,inspect_template_item_id,invalid_time from pnr_spotcheck_template_item c where c.invalid_time is null or c.invalid_time='' )XXX " +
		" on a.pnr_inspect_template_item_id = XXX.inspect_template_item_id where a.template_id='"+inspectTemplateId+"' and a.bigitem_id='"+templateBigItemId+"'" +
				" and a.month="+month+" and a.year="+year+"";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 执行sql
	 * @param sql
	 */
	public void excuteSql(String sql){
		
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * 巡检资源抽检列表
	 * @param planId
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageModel findInspectPlanResSpotcheckList(String planId,int offset,int pagesize,InspectPlanRes res){
		String selectSql = "select a.id resId,a.res_name resName,a.specialty," +
				"a.res_type resType,a.city,a.country,a.execute_object executeObject,a.inspect_State inspectState,a.inspect_User inspectUser," +
				"b.spotcheck_user spotcheckUser,b.spotcheck_time spotcheckTime,b.score  ";
		String fromSql = " from pnr_inspect_plan_res a left join pnr_spotcheck_res b on a.id=b.plan_res_id where a.plan_id='"+planId+"' and inspect_state=1 " ;
		if(!StringUtils.isEmpty(res.getResName())){
			fromSql+= " and res_name like '%"+res.getResName()+"%'";
		}
		if(!StringUtils.isEmpty(res.getResType())){
			fromSql+= " and res_type = '"+res.getResType()+"'";
		}
		if(!StringUtils.isEmpty(res.getCity())){
			fromSql+= " and city = '"+res.getCity()+"'";
		}
		if(!StringUtils.isEmpty(res.getCountry())){
			fromSql+= " and country = '"+res.getCountry()+"'";
		}
		if(res.getInspectState()!=null){
			if(res.getInspectState()==0){
				fromSql+= " and score is null";
			}else if(res.getInspectState()==1){
				fromSql+= " and score is not null";
			}
		}
//		if(StringUtils.isEmpty(res.getInspectState())){
//			fromSql+= " and score is not null";
//		}
//		if(res.getInspectState()==0){
//			fromSql+= " and score is null";
//		}
		String pageSql = CommonSqlHelper.formatPageSql(selectSql + fromSql,offset,pagesize);
System.out.println(pageSql);
		List list = this.getJdbcTemplate().queryForList(pageSql);
		PageModel pageModel = new PageModel();
		pageModel.setDatas(list);
		
		String countSql = "select count(*) " + fromSql;
		pageModel.setTotal(this.getJdbcTemplate().queryForInt(countSql));
		return pageModel;
	}
	/**
	 * 巡检资源抽检列表手机请求
	 * @param planId
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageModel findInspectPlanResSpotcheckListMobile(String planId,int offset,int pagesize,InspectPlanRes res,HashMap<String, String> queryMap){
		String selectSql = "select skip "+offset+" first "+pagesize+" a.id resId,a.res_name resName,a.specialty," +
		"a.res_type resType,a.city,a.country,a.execute_object executeObject,a.inspect_State inspectState,a.inspect_User inspectUser," +
		"b.spotcheck_user spotcheckUser,b.spotcheck_time spotcheckTime,b.score  ";
		String fromSql = " from pnr_inspect_plan_res a left join pnr_spotcheck_res b on a.id=b.plan_res_id where a.plan_id='"+planId+"' and inspect_state=1 " ;
		
//		queryMap.put("cycleQuery", cycleQuery);
//		queryMap.put("radiusQuery", radiusQuery);
//		queryMap.put("res_longitude", res_longitude);
//		queryMap.put("res_latitude", res_latitude);
//		queryMap.put("resNameQuery", resNameQuery);
//		queryMap.put("completeQuery", completeQuery);
//		queryMap.put("userId", userId);
		
		if(null != queryMap.get("cycleQuery")&&!"".equals(queryMap.get("cycleQuery").toString())){
			
		}
		if(null != queryMap.get("radiusQuery")&&!"".equals(queryMap.get("radiusQuery").toString())){
			if(null != queryMap.get("res_longitude")&&!"".equals(queryMap.get("res_longitude").toString())&&null != queryMap.get("res_latitude")&&!"".equals(queryMap.get("res_latitude").toString())){
				
			}
		}
		if(null != queryMap.get("resNameQuery")&&!"".equals(queryMap.get("resNameQuery").toString())){
			fromSql+= " and res_name like '%"+queryMap.get("resNameQuery")+"%'";
		}
		if(null != queryMap.get("completeQuery")&&!"".equals(queryMap.get("completeQuery").toString())){
			
		}
		if(null != queryMap.get("userId")&&!"".equals(queryMap.get("userId").toString())){
			
		}
		
		if(!StringUtils.isEmpty(res.getResType())){
			fromSql+= " and res_type = '"+res.getResType()+"'";
		}
		if(!StringUtils.isEmpty(res.getCity())){
			fromSql+= " and city = '"+res.getCity()+"'";
		}
		if(!StringUtils.isEmpty(res.getCountry())){
			fromSql+= " and country = '"+res.getCountry()+"'";
		}
		
		System.out.println(selectSql + fromSql);
		List list = this.getJdbcTemplate().queryForList(selectSql + fromSql);
		PageModel pageModel = new PageModel();
		pageModel.setDatas(list);
		
		String countSql = "select count(*) " + fromSql;
		pageModel.setTotal(this.getJdbcTemplate().queryForInt(countSql));
		return pageModel;
	}
	
	/**
	 * 巡检项抽检列表
	 * @param inspectTemplateId
	 * @param inspectPlanResId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findInspectPlanItemSpotcheckList(String inspectTemplateId,String inspectPlanResId){
		String sql = "select a.id spottmpid,a.bigitem_name itemgroup,a.mark_rule markrule ,a.score score,count(a.id) count " +
		"from pnr_spotcheck_template a " +
			"left join pnr_spotcheck_template_item b on a.id=b.spotcheck_template_id " +
			"left join pnr_inspect_template_item c on b.inspect_template_item_id=c.id  " +
		"where (a.invalid_time is null or a.invalid_time='') and a.template_id='"+inspectTemplateId+"' " +
			"group by a.id,a.bigitem_name,a.mark_rule,a.score";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
		
		for (Map<String, Object> map : list) {
			String spotcheckTemplateId = map.get("spottmpid").toString();
			String itemSql = "select b.spotcheck_template_id spotTmpId,c.inspect_item item,d.item_result item_result,d.input_type input_type," +
			"c.inspect_content content,d.id planItemId,d.exception_flag expFlag,d.exception_content expContent,e.score score " +
			"from pnr_spotcheck_template_item b " +
				"left join pnr_inspect_template_item c on b.inspect_template_item_id=c.id " +
				"left join pnr_inspect_plan_item d on c.id=d.template_item_id " +
				"left join pnr_spotcheck_item e on d.id=e.plan_item_id " +
			"where b.spotcheck_template_id='"+spotcheckTemplateId+"' and d.plan_res_id="+inspectPlanResId;
			List<Map<String,String>> itemList = this.getJdbcTemplate().queryForList(itemSql);
			map.put("itemList", itemList);
		}
		return list;
	}
	
	public List getInspectTemplateHisList(String year,String month,int offset,int pagesize,String whereStr){
//		String sql ="select skip "+offset+" first "+pagesize+" id,template_id,templatename,dept,specialty,content,add_time,add_user,res_type,item_num from pnr_inspect_template_his " +
//				"where year="+year+" and month="+month+" "+whereStr;
		String sql ="select id,template_id,templatename,dept,specialty,content,add_time,add_user,res_type,item_num from pnr_inspect_template_his " +
			"where year="+year+" and month="+month+" "+whereStr;
		sql = CommonSqlHelper.formatPageSql(sql,offset,pagesize);
		
		String sql2 = "select count(*) from pnr_inspect_template_his where year="+year+" and month="+month+" "+whereStr;
		
		int total = this.getJdbcTemplate().queryForInt(sql2);
		
		List list = new ArrayList();
		list.add(total);
		list.add(this.getJdbcTemplate().queryForList(sql));
		
		return list;
	}
}
