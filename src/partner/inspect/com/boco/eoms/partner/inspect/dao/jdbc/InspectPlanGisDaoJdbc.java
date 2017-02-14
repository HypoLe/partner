package com.boco.eoms.partner.inspect.dao.jdbc;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.inspect.dao.IInspectPlanGisDaoJdbc;

public class InspectPlanGisDaoJdbc extends JdbcDaoSupport implements IInspectPlanGisDaoJdbc {

	public void deleteInspectGisCityres(){
		String sql="delete pnr_inspect_gis_cityres";
		this.getJdbcTemplate().execute(sql);
	}

	@SuppressWarnings("unchecked")
	public void saveInspectPlanGis() {
		//在轮训表里面统计数据（主要是保存计划总数）
//		String sql="insert into pnr_inspect_gis_cityres (city,res_num) select city,count(*) from pnr_inspect_plan_res where " +
//				"current>=plan_start_time and current<=plan_end_time group by city";
//		this.getJdbcTemplate().execute(sql);
//		String sql2 = "select city,count(*) as resCount from pnr_inspect_plan_res where current>=plan_start_time and current<=plan_end_time and inspect_state=1 group by city";
////		this.getJdbcTemplate().execute(sql2);
//		List  lis = this.getJdbcTemplate().queryForList(sql2);
//		for(int i=0;i<lis.size();i++){
//			String city = ((Map)lis.get(0)).get("city")+"";
//			String resCount = ((Map)lis.get(0)).get("resCount")+"";
//			String sql3 = "update pnr_inspect_gis_cityres set res_done_num="+resCount+" where city='"+city+"'";
//			this.getJdbcTemplate().execute(sql3);
//		}
		
		String data = CommonSqlHelper.formatDateTime(StaticMethod.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
		String sql="insert into pnr_inspect_gis_cityres (city,res_done_num,res_num)" +
				"select dd.city,nvl(resdonenum,0) as resdonenum,nvl(resnum,0) as resnum from (" +
				"select aa.CITY,bb.resdonenum,aa.resnum from (select city,count(*) as resnum from " +
				"pnr_inspect_plan_res where "+data+">=plan_start_time and "+data+"<=plan_end_time  and city is not null group by city ) aa left join " +
				"(select * from (select city,count(*) as resdonenum from " +
				"pnr_inspect_plan_res mm where "+data+">=plan_start_time and "+data+"<=plan_end_time and mm.inspect_state=1 and city is not null group by city ) xx )" +
				" bb on aa.city=bb.city) dd";
		
		String sql2 ="insert into pnr_inspect_gis_cityres (city,res_done_num,res_num)" +
				"select dd.country,nvl(resdonenum,0) as resdonenum,nvl(resnum,0) as resnum from (" +
				"select aa.country,bb.resdonenum,aa.resnum from (select country,count(*) as resnum from " +
				"pnr_inspect_plan_res where "+data+">=plan_start_time and "+data+"<=plan_end_time  and country is not null  group by country ) aa left join "+
				"(select * from (select country,count(*) as resdonenum from "+
				"pnr_inspect_plan_res mm where "+data+">=plan_start_time and "+data+"<=plan_end_time and mm.inspect_state=1 and country is not null group by country ) xx )"+
				"bb on aa.country=bb.country) dd";
		
		this.getJdbcTemplate().execute(sql);
		
		this.getJdbcTemplate().execute(sql2);
	}

	/**
	 * 删除按部门统计的数据
	 */
	public void deleteInspectGisPnrDept() {
		String sql = "delete pnr_inspect_gis_pnr_dept";
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 重新按钮部门统计任务的完成情况
	 */
	@SuppressWarnings("unchecked")
	public void saveInspectPlanGisPnrDept() {
//		String sql = "insert into pnr_inspect_gis_pnr_dept (pnr_id,pnr_dept,res_num) select execute_object,execute_dept,count(*) as res_count from" +
//				" pnr_inspect_plan_res where current>=plan_start_time and current<=plan_end_time  group by execute_object,execute_dept";
//		this.getJdbcTemplate().execute(sql);
//		String sql2 = "select execute_object,execute_dept,count(*) as res_count from" +
//		" pnr_inspect_plan_res where current>=plan_start_time and current<=plan_end_time and inspect_state=1 group by execute_object,execute_dept";
//		List  lis = this.getJdbcTemplate().queryForList(sql2);
//		for(int i=0;i<lis.size();i++){
//			String pnrId = ((Map)lis.get(0)).get("execute_object")+"";
//			String pnrDept = ((Map)lis.get(0)).get("execute_dept")+"";
//			String resDoneNum = ((Map)lis.get(0)).get("res_count")+"";
//			String sql3 = "update pnr_inspect_gis_pnr_dept set res_done_num="+resDoneNum+" where pnr_id='"+pnrId+"' and pnr_dept='"+pnrDept+"'";
//			this.getJdbcTemplate().execute(sql3);
//		}
		
	
		String dialectString ="sysdate";

		 if(CommonSqlHelper.isInformixDialect()){		
			 dialectString="current";
		}
		String sql ="insert into pnr_inspect_gis_pnr_dept (pnr_dept,pnr_id,res_done_num,res_num) " +
		"select aa.execute_dept,1,nvl(resdonenum,0),nvl(resnum,0) from "+
		"(select execute_dept,count(*) as resnum from pnr_inspect_plan_res where "+dialectString+">=plan_start_time and "+dialectString+"<=plan_end_time "+
		"group by execute_dept ) aa left join (select * from (select execute_dept,count(*) as resdonenum from "+
		"pnr_inspect_plan_res mm where "+dialectString+">=plan_start_time and "+dialectString+"<=plan_end_time and mm.inspect_state=1 "+
		"group by execute_dept ) xx ) bb on  bb.execute_dept=aa.execute_dept";
		
		
		this.getJdbcTemplate().execute(sql);
		
	}
	
	/**
	 * 查询当前地市下任务的完成数
	 * @param city
	 * @return
	 */
	public List findInspectplanGis(String city){
		String sql="";
		if("".equals(city)){ //当前按地市统计
			sql="select a.areaid,a.areaname,nvl(b.res_done_num,0) as resDoneNum,nvl(b.res_num,0) as resNum from taw_system_area a left join" +
					" (select city,res_done_num,res_num from pnr_inspect_gis_cityres)b " +
					"on a.areaid=b.city where length(a.areaid)=4";
		}else{
			sql="select a.areaid,a.areaname,nvl(b.res_done_num,0) as resDoneNum,nvl(b.res_num,0) as resNum from taw_system_area a left join " +
					"(select city,res_done_num,res_num from pnr_inspect_gis_cityres)b " +
					"on a.areaid=b.city where length(a.areaid)=6 and a.areaid like '"+city+"%'";
		}
		return this.getJdbcTemplate().queryForList(sql);
	}
}
