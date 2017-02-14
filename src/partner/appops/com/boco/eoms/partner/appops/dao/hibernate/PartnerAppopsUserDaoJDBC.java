package com.boco.eoms.partner.appops.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.appops.dao.PartnerAppopsUserJDBCDao;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsUser;
import com.boco.eoms.partner.appops.model.PnrAppOpsUserModel;
import com.boco.eoms.partner.appops.model.StatisticsAppopsUserBySpecaillty;
import com.boco.eoms.partner.appops.model.StatisticsByCompanyAndDept;
import com.boco.eoms.partner.appops.model.StatisticsBySpecialityAndDept;

public class PartnerAppopsUserDaoJDBC extends JdbcDaoSupport implements PartnerAppopsUserJDBCDao{

	@Override
	public List<StatisticsAppopsUserBySpecaillty> getStatisticsBySpecaillty(String userid,String areaid,String city,String specialty,PnrAppOpsUserModel pnrAppOpsUserModel) {
		String sql  = "select " +
				"dt.dictid as s," +
				"sum(case when pu.user_age < 26 then 1 else 0 end ) as a1," +
				"sum(case when pu.user_age between 26 and 30 then 1 else 0 end) as a2," +
				"sum(case when pu.user_age between 31 and 35 then 1 else 0 end) as a3," +
				"sum(case when pu.user_age between 36 and 40 then 1 else 0 end) as a4," +
				"sum(case when pu.user_age between 41 and 45 then 1 else 0 end) as a5," +
				"sum(case when pu.user_age between 46 and 50 then 1 else 0 end) as a6," +
				"sum(case when pu.user_age between 51 and 55 then 1 else 0 end) as a7," +
				"sum(case when pu.user_age >=56 then 1 else 0 end) as a8," +
				"sum(case when pu.highest_degree = '1020101' then 1 else 0 end) as d1, " +
				"sum(case when pu.highest_degree = '1020102' then 1 else 0 end) as d2," +
				"sum(case when pu.highest_degree = '1020103' then 1 else 0 end) as d3," +
				"sum(case when pu.highest_degree = '1020104' then 1 else 0 end) as d4," +
				"sum(case when pu.highest_degree not in( '1020101','1020102','1020103','1020104')then 1 else 0 end) as d5," +
				"sum(case when pu.management_level='1020601' then 1 else 0 end) as m1," +
				"sum(case when pu.management_level='1020602' then 1 else 0 end) as m2," +
				"sum(case when pu.management_level='1020603' then 1 else 0 end) as m3," +
				"sum(case when pu.management_level='1020604' then 1 else 0 end) as m4," +
				"sum(case when pu.work_zhuan is not null then 1 else 0 end) as w1," +
				"sum(case when pu.work_jian is not null then 1 else 0 end ) as w2," +
				"sum(case when pu.use_sort='1020501' then 1 else 0 end) as u1," +
				"sum(case when pu.use_sort='1020502' then 1 else 0 end) as u2," +
				"sum(case when pu.use_sort='1020503' then 1 else 0 end) as u3," +
				"sum(case when pu.use_sort='1020504' then 1 else 0 end) as u4" ;
		//获取登陆人所在地区的等级
//		String citySql = "select a.ordercode from taw_system_user u ," +
//				" taw_system_dept d," +
//				"taw_system_area a" +
//				" where u.deptid = d.deptid" +
//				" and d.areaid = a.areaid" +
//				"  and u.userid='"+userid+"'";
//		List<Map> levelList = this.getJdbcTemplate().queryForList(citySql);
//		String userCity="";
//		String whereCitySql=" where pu.company_id=(";
//		if(levelList!=null && levelList.size()>0){
//			userCity = levelList.get(0).get("ORDERCODE").toString();
//		}
//		if("2".equals(userCity)){
//			whereCitySql = whereCitySql+"select d1.dictid from taw_system_area a1,taw_system_dicttype d1" +
//					" where a1.areaid = (select a.parentareaid from taw_system_user u , taw_system_dept d," +
//					"  taw_system_area a" +
//					" where u.deptid = d.deptid and d.areaid = a.areaid and u.userid='"+userid+"' )" +
//					" and a1.areaname = d1.dictname)";
//		}else if("3".equals(userCity)){
//			whereCitySql = whereCitySql+"select d1.dictid from taw_system_area a1,taw_system_dicttype d1" +
//			" where a1.areaid = (select a.areaid from taw_system_user u , taw_system_dept d," +
//			"  taw_system_area a" +
//			" where u.deptid = d.deptid and d.areaid = a.areaid and u.userid='"+userid+"' )" +
//			" and a1.areaname = d1.dictname)";
//		}else{
//			whereCitySql = "";
//		}
		
		
		ArrayList whereList=new ArrayList();
	
		
		String whereSql="";
		
		//拼接分公司条件sql
		String company[]=null;
		String str1="";
		if(pnrAppOpsUserModel.getCompany()!=null){
			company=pnrAppOpsUserModel.getCompany();
			for(int i=0;i<company.length;i++){
				if(str1!=""){
					str1+=",";
				}
				str1+=company[i];
			}
			whereSql+=" and uu.COMPANY_ID in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str1);
		}
		//姓名
		if(pnrAppOpsUserModel.getUserName()!=null && !"".equals(pnrAppOpsUserModel.getUserName())){	
			whereSql+=" and uu.USER_NAME=?";
			whereList.add(pnrAppOpsUserModel.getUserName());
		}
		//性别
		if(pnrAppOpsUserModel.getUserSex()!=null && !"".equals(pnrAppOpsUserModel.getUserSex())){	
			whereSql+=" and uu.USER_SEX=?";
			whereList.add(pnrAppOpsUserModel.getUserSex());
		}
		//出生年份
		String[] userBorth=null;
		String str2="";
		if(pnrAppOpsUserModel.getUserBorth()!=null){
			userBorth=pnrAppOpsUserModel.getUserBorth();
			for(int i=0;i<userBorth.length;i++){
				if(str2!=""){
					str2+=",";
				}
				str2+=userBorth[i];
			}
			whereSql+=" and uu.USER_BORTH in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str2);
		}
		
		//手机
		if(pnrAppOpsUserModel.getPhone()!=null && !"".equals(pnrAppOpsUserModel.getPhone())){	
			whereSql+=" and uu.PHONE=?";
			whereList.add(pnrAppOpsUserModel.getPhone());
		}
		
		//email
		if(pnrAppOpsUserModel.getEmail()!=null && !"".equals(pnrAppOpsUserModel.getEmail())){	
			whereSql+=" and uu.EMAIL=?";
			whereList.add(pnrAppOpsUserModel.getEmail());
		}
		
		//参加工作时间
		String[] workTime=null;
		String str3="";
		if(pnrAppOpsUserModel.getWorkTime()!=null){
			workTime=pnrAppOpsUserModel.getWorkTime();
			for(int i=0;i<workTime.length;i++){
				if(str3!=""){
					str3+=",";
				}
				str3+=workTime[i];
			}
			whereSql+=" and uu.WORK_TIME in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str3);
		}
		
		//入司时间
		String[] companyTime=null;
		String str4="";
		if(pnrAppOpsUserModel.getCompanyTime()!=null){
			companyTime=pnrAppOpsUserModel.getCompanyTime();
			for(int i=0;i<companyTime.length;i++){
				if(str4!=""){
					str4+=",";
				}
				str4+=companyTime[i];
			}
			whereSql+=" and uu.COMPANY_TIME in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str4);
		}
		
		//从事运维工作时间
		String[] appopsWorkTime=null;
		String str5="";
		if(pnrAppOpsUserModel.getAppopsWorkTime()!=null){
			appopsWorkTime=pnrAppOpsUserModel.getAppopsWorkTime();
			for(int i=0;i<appopsWorkTime.length;i++){
				if(str5!=""){
					str5+=",";
				}
				str5+=appopsWorkTime[i];
			}
			whereSql+=" and uu.APPOPS_WORK_TIME in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str5);
		}
		
		//最高学历
		String[] highestDegree=null;
		String str6="";
		if(pnrAppOpsUserModel.getHighestDegree()!=null){
			highestDegree=pnrAppOpsUserModel.getHighestDegree();
			for(int i=0;i<highestDegree.length;i++){
				if(str6!=""){
					str6+=",";
				}
				str6+=highestDegree[i];
			}
			whereSql+=" and uu.HIGHEST_DEGREE in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str6);
		}
		
		//最高学历毕业院校
		if(pnrAppOpsUserModel.getSchool()!=null && !"".equals(pnrAppOpsUserModel.getSchool())){	
			whereSql+=" and uu.SCHOOL=?";
			whereList.add(pnrAppOpsUserModel.getSchool());
		}
		
		//员工类别
		String[] workSort=null;
		String str7="";
		if(pnrAppOpsUserModel.getWorkSort()!=null){
			workSort=pnrAppOpsUserModel.getWorkSort();
			for(int i=0;i<workSort.length;i++){
				if(str7!=""){
					str7+=",";
				}
				str7+=workSort[i];
			}
			whereSql+=" and uu.WORK_SORT in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str7);
		}
		
		//职称
		String[] jobTitle=null;
		String str8="";
		if(pnrAppOpsUserModel.getJobTitle()!=null){
			jobTitle=pnrAppOpsUserModel.getJobTitle();
			for(int i=0;i<jobTitle.length;i++){
				if(str8!=""){
					str8+=",";
				}
				str8+=jobTitle[i];
			}
			whereSql+=" and uu.JOB_TITLE in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str8);
		}
		//职称
		String[] useSort=null;
		String str9="";
		if(pnrAppOpsUserModel.getUseSort()!=null){
			useSort=pnrAppOpsUserModel.getUseSort();
			for(int i=0;i<useSort.length;i++){
				if(str9!=""){
					str9+=",";
				}
				str9+=useSort[i];
			}
			whereSql+=" and uu.USE_SORT in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str9);
		}
		//岗位职级
		String[] jobLevel=null;
		String str10="";
		if(pnrAppOpsUserModel.getJobLevel()!=null){
			jobLevel=pnrAppOpsUserModel.getJobLevel();
			for(int i=0;i<jobLevel.length;i++){
				if(str10!=""){
					str10+=",";
				}
				str10+=jobLevel[i];
			}
			whereSql+=" and uu.WORK_LEVEL in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str10);
		}
		//所在部门
		if(pnrAppOpsUserModel.getDept()!=null && !"".equals(pnrAppOpsUserModel.getDept())){	
			whereSql+=" and uu.DEPT_ID=?";
			whereList.add(pnrAppOpsUserModel.getDept());
		}
		//所在班组
		if(pnrAppOpsUserModel.getTeam()!=null && !"".equals(pnrAppOpsUserModel.getTeam())){	
			whereSql+=" and uu.TEAM_ID=?";
			whereList.add(pnrAppOpsUserModel.getTeam());
		}
		
		
		
		//工作岗位-专职
		if(pnrAppOpsUserModel.getOperatingPostZ()!=null && !"".equals(pnrAppOpsUserModel.getOperatingPostZ())){	
			whereSql+=" and uu.WORK_ZHUAN=?";
			whereList.add(pnrAppOpsUserModel.getOperatingPostZ());
		}
		
		//工作岗位-兼职
		if(pnrAppOpsUserModel.getOperatingPostJ()!=null && !"".equals(pnrAppOpsUserModel.getOperatingPostJ())){	
			whereSql+=" and uu.WORK_JIAN==?";
			whereList.add(pnrAppOpsUserModel.getOperatingPostJ());
		}
		//管理层级
		String[] managerLevel=null;
		String str11="";
		if(pnrAppOpsUserModel.getManagerLevel()!=null){
			managerLevel=pnrAppOpsUserModel.getManagerLevel();
			for(int i=0;i<managerLevel.length;i++){
				if(str11!=""){
					str11+=",";
				}
				str11+=managerLevel[i];
			}
			whereSql+=" and uu.MANAGEMENT_LEVEL in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str11);
		}
		
		//工作职责描述
		if(pnrAppOpsUserModel.getWorkDescribe()!=null && !"".equals(pnrAppOpsUserModel.getWorkDescribe())){	
			whereSql+=" and  instr(uu.DESCRIBLE,?)>0";
			whereList.add(pnrAppOpsUserModel.getWorkDescribe());
		}
		
		//专业类别
		String whereSpecialtySql="";
		if(pnrAppOpsUserModel.getSpecialty()!=null && !"".equals(pnrAppOpsUserModel.getSpecialty())){	
			whereSpecialtySql+=" where  dt.dictid=?";
			whereList.add(pnrAppOpsUserModel.getSpecialty());
		}
		
		//地市查看权限
		if(areaid!=null && !"".equals(areaid)){
			if((StaticMethod.nullObject2String(areaid)).length()==2){
				whereSql += "";
			}else if((StaticMethod.nullObject2String(areaid)).length()==4){
				whereSql += " and  instr(uu.area_id,?, 1, 1)=1";
				whereList.add(areaid);
			}else if((StaticMethod.nullObject2String(areaid)).length()==6){
				whereSql += " and uu.area_id=? ";
				whereList.add(areaid);
			}
		}
		
		sql = sql +" from (select * from pnr_act_appops_user uu where uu.is_delete = 0 "+whereSql+") pu right join (select d2.dictid from taw_system_dicttype d2 " +
		" where d2.parentdictid=( select d1.dictid from taw_system_dicttype d1 where d1.dictname='专业类别')) dt  on pu.specialty=dt.dictid "+whereSpecialtySql+" group by dt.dictid" ;
		
		Object values[]=whereList.toArray();
		
		System.out.println(sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		List<StatisticsAppopsUserBySpecaillty>  statisticsList = new ArrayList<StatisticsAppopsUserBySpecaillty>();
		if(list!=null && !list.isEmpty() && list.size()>0){
			int a1=0,a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0;
			int d1=0,d2=0,d3=0,d4=0,d5=0;
			int m1=0,m2=0,m3=0,m4=0;
			int w1=0,w2=0;
			int u1=0,u2=0,u3=0,u4=0;
			int count=0;
			int[] row = new int[list.size()+1];
			for(Map map :list){
				StatisticsAppopsUserBySpecaillty newOne = new StatisticsAppopsUserBySpecaillty();
				newOne.setSpeciallty(map.get("S").toString());
				int sum =0;
				newOne.setAge_one(map.get("A1").toString());
					a1=a1+Integer.parseInt(map.get("A1").toString());
					sum = sum+Integer.parseInt(map.get("A1").toString());
				newOne.setAge_two(map.get("A2").toString());
					a2=a2+Integer.parseInt(map.get("A2").toString());
					sum = sum+Integer.parseInt(map.get("A2").toString());
				newOne.setAge_tree(map.get("A3").toString());
					a3=a3+Integer.parseInt(map.get("A3").toString());
					sum = sum+Integer.parseInt(map.get("A3").toString());
				newOne.setAge_forth(map.get("A4").toString());
					a4=a4+Integer.parseInt(map.get("A4").toString());
					sum = sum+Integer.parseInt(map.get("A4").toString());
				newOne.setAge_five(map.get("A5").toString());
					a5=a5+Integer.parseInt(map.get("A5").toString());
					sum=sum+Integer.parseInt(map.get("A5").toString());
				newOne.setAge_sex(map.get("A6").toString());
					a6=a6+Integer.parseInt(map.get("A6").toString());
					sum = sum +Integer.parseInt(map.get("A6").toString());
				newOne.setAge_seven(map.get("A7").toString());
					a7=a7+Integer.parseInt(map.get("A7").toString());
					sum = sum+Integer.parseInt(map.get("A7").toString());
				newOne.setAge_ength(map.get("A8").toString());
					a8=a8+Integer.parseInt(map.get("A8").toString());
					sum =sum+Integer.parseInt(map.get("A8").toString());
				newOne.setDegree_one(map.get("D1").toString());
					d1=d1+Integer.parseInt(map.get("D1").toString());
					sum=sum+Integer.parseInt(map.get("D1").toString());
				newOne.setDegree_two(map.get("D2").toString());
					d2=d2+Integer.parseInt(map.get("D2").toString());
					sum = sum+Integer.parseInt(map.get("D2").toString());
				newOne.setDegree_three(map.get("D3").toString());
					d3=d3+Integer.parseInt(map.get("D3").toString());
					sum =sum+Integer.parseInt(map.get("D3").toString());
				newOne.setDegree_forth(map.get("D4").toString());
					d4=d4+Integer.parseInt(map.get("D4").toString());
					sum=sum+Integer.parseInt(map.get("D4").toString());
				newOne.setDegree_five(map.get("D5").toString());
					d5=d5+Integer.parseInt(map.get("D5").toString());
					sum=sum+Integer.parseInt(map.get("D5").toString());
				newOne.setManagment_one(map.get("M1").toString());
					m1=m1+Integer.parseInt(map.get("M1").toString());
					sum=sum+Integer.parseInt(map.get("M1").toString());
				newOne.setManagment_two(map.get("M2").toString());
					m2=m2+Integer.parseInt(map.get("M2").toString());
					sum=sum+Integer.parseInt(map.get("M2").toString());
				newOne.setManagment_three(map.get("M3").toString());
					m3=m3+Integer.parseInt(map.get("M3").toString());
					sum=sum+Integer.parseInt(map.get("M3").toString());
				newOne.setManagment_forth(map.get("M4").toString());
					m4=m4+Integer.parseInt(map.get("M4").toString());
					sum=sum+Integer.parseInt(map.get("M4").toString());
				newOne.setWorkState_zhuan(map.get("W1").toString());
					w1=w1+Integer.parseInt(map.get("W1").toString());
					sum=sum+Integer.parseInt(map.get("W1").toString());
				newOne.setWorkState_jian(map.get("W2").toString());
					w2=w2+Integer.parseInt(map.get("W2").toString());
					sum=sum+Integer.parseInt(map.get("W2").toString());
				newOne.setUseSort_one(map.get("U1").toString());
					u1=u1+Integer.parseInt(map.get("U1").toString());
					sum=sum+Integer.parseInt(map.get("U1").toString());
				newOne.setUseSort_two(map.get("U2").toString());
					u2=u2+Integer.parseInt(map.get("U2").toString());
					sum=sum+Integer.parseInt(map.get("U2").toString());
				newOne.setUseSort_three(map.get("U3").toString());
					u3=u3+Integer.parseInt(map.get("U3").toString());
					sum=sum+Integer.parseInt(map.get("U3").toString());
				newOne.setUseSort_forth(map.get("U4").toString());
					u4=u4+Integer.parseInt(map.get("U4").toString());
					sum=sum+Integer.parseInt(map.get("U4").toString());
				//合计每行数据
				newOne.setRowSum(sum);
				statisticsList.add(newOne);
			}//合计每列数据
			StatisticsAppopsUserBySpecaillty col = new StatisticsAppopsUserBySpecaillty();
			int colSum=0;
			col.setSpeciallty("合计");
			col.setAge_one(String.valueOf(a1));
			col.setAge_two(String.valueOf(a2));
			col.setAge_tree(String.valueOf(a3));
			col.setAge_forth(String.valueOf(a4));
			col.setAge_five(String.valueOf(a5));
			col.setAge_sex(String.valueOf(a6));
			col.setAge_seven(String.valueOf(a7));
			col.setAge_ength(String.valueOf(a8));
			col.setManagment_one(String.valueOf(m1));
			col.setManagment_two(String.valueOf(m2));
			col.setManagment_three(String.valueOf(m3));
			col.setManagment_forth(String.valueOf(m4));
			col.setDegree_one(String.valueOf(d1));
			col.setDegree_two(String.valueOf(d2));
			col.setDegree_three(String.valueOf(d3));
			col.setDegree_forth(String.valueOf(d4));
			col.setDegree_five(String.valueOf(d5));
			col.setWorkState_zhuan(String.valueOf(w1));
			col.setWorkState_jian(String.valueOf(w2));
			col.setUseSort_one(String.valueOf(u1));
			col.setUseSort_two(String.valueOf(u2));
			col.setUseSort_three(String.valueOf(u3));
			col.setUseSort_forth(String.valueOf(u4));
			colSum=a1+a2+a3+a4+a5+a6+a7+a8+d1+d2+d3+d4+d5+m1+m2+m3+m4+w1+w2+u1+u2+u3+u4;
			col.setRowSum(colSum);
			statisticsList.add(col);
			
		}else{
			statisticsList=null;
		}
		
		return statisticsList;
	}
	public List<StatisticsByCompanyAndDept> getStatisticsByCompanyAndDept(String userid,String areaid,String[] str,IPnrPartnerAppOpsUser appopsUser,PnrAppOpsUserModel pnrAppOpsUserModel){
		List<StatisticsByCompanyAndDept> statisticList= new ArrayList<StatisticsByCompanyAndDept>();
//		//条件查询语句
//		//String whereSql = " where 1=1 ";
//		String whereSql = " ";
//		//用工类别
//		String useSort = appopsUser.getUseSort();
//		if(useSort!=null && !"".equals(useSort)){
//			whereSql = whereSql + " and u.use_sort='"+useSort+"'";
//		}
//		//最高学历
//		String highDegree = appopsUser.getHighestDegree();
//		if(highDegree!=null && !"".equals(highDegree)){
//			whereSql = whereSql + " and u.highest_degree='"+highDegree+"'" ;
//		}
//		//职称
//		String jobTitle = appopsUser.getJobTitle();
//		if(jobTitle!=null && !"".equals(jobTitle)){
//			whereSql = whereSql + "  and u.job_title='"+jobTitle+"'";
//		}
//		//工作岗位（专职）
//		String zhuan = appopsUser.getOperatingPostZ();
//		if(zhuan!=null && !"".equals(zhuan)){
//			whereSql = whereSql + " and u.work_zhuan='"+zhuan+"'";
//		}
//		//工作岗位（兼职）
//		String jian = appopsUser.getOperatingPostJ();
//		if(jian!=null && !"".equals(jian)){
//			whereSql = whereSql + " and u.work_jian like'%"+jian+"%'";
//		}
		
		ArrayList whereList=new ArrayList();
		
		String sql="SELECT dictid";
		if(str.length>0){
			for(int i=0;i<str.length;i++){
				if(i==9){
					sql=sql+",sum(case when  instr(name,'"+str[i]+"',1,1)=1  then 1 else 0 end)as d"+(i+1);
				}else if(i==6){
					sql=sql+",sum(case when  instr(name,'"+str[i]+"',1,1)=1  then 1 else 0 end)as d"+(i+1);
				}else{
					sql=sql+",sum(case when  instr(name,'"+str[i]+"',1,1)>0  then 1 else 0 end)as d"+(i+1);
				}
				
			}
		}
		
		//获取登陆人所在地区的等级
//		String citySql = "select a.ordercode from taw_system_user u ," +
//				" taw_system_dept d," +
//				"taw_system_area a" +
//				" where u.deptid = d.deptid" +
//				" and d.areaid = a.areaid" +
//				"  and u.userid='"+userid+"'";
//		List<Map> levelList = this.getJdbcTemplate().queryForList(citySql);
//		String userCity="";
//		String whereCitySql=" and d.dictid=(";
//		if(levelList!=null && levelList.size()>0){
//			userCity = levelList.get(0).get("ORDERCODE").toString();
//		}
//		if("2".equals(userCity)){
//			whereCitySql = whereCitySql+"select d1.dictid from taw_system_area a1,taw_system_dicttype d1" +
//					" where a1.areaid = (select a.parentareaid from taw_system_user u , taw_system_dept d," +
//					"  taw_system_area a" +
//					" where u.deptid = d.deptid and d.areaid = a.areaid and u.userid='"+userid+"' )" +
//					" and a1.areaname = d1.dictname)";
//		}else if("3".equals(userCity)){
//			whereCitySql = whereCitySql+"select d1.dictid from taw_system_area a1,taw_system_dicttype d1" +
//			" where a1.areaid = (select a.areaid from taw_system_user u , taw_system_dept d," +
//			"  taw_system_area a" +
//			" where u.deptid = d.deptid and d.areaid = a.areaid and u.userid='"+userid+"' )" +
//			" and a1.areaname = d1.dictname)";
//		}else{
//			whereCitySql = "";
//		}
		
		
		
		String whereCitySql=""; //地市查看权限
		if(areaid!=null && !"".equals(areaid)){
			if((StaticMethod.nullObject2String(areaid)).length()==2){
				whereCitySql += "";
			}else if((StaticMethod.nullObject2String(areaid)).length()==4){
				whereCitySql += " and instr(uu.area_id,?,1,1)=1 ";
				whereList.add(areaid);
			}else if((StaticMethod.nullObject2String(areaid)).length()==6){
				whereCitySql += " and uu.area_id=? ";
				whereList.add(areaid);
			}
		}
		
		String whereCompanySql="";
		//拼接分公司条件sql
		String company[]=null;
		String str1="";
		if(pnrAppOpsUserModel.getCompany()!=null){
			company=pnrAppOpsUserModel.getCompany();
			for(int i=0;i<company.length;i++){
				if(str1!=""){
					str1+=",";
				}
				str1+=company[i];
			}
			whereCompanySql+=" and d.dictid in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str1);
		}
		
		String whereSql="";
		//姓名
		if(pnrAppOpsUserModel.getUserName()!=null && !"".equals(pnrAppOpsUserModel.getUserName())){	
			whereSql+=" and uu.USER_NAME=?";
			whereList.add(pnrAppOpsUserModel.getUserName());
		}
		//性别
		if(pnrAppOpsUserModel.getUserSex()!=null && !"".equals(pnrAppOpsUserModel.getUserSex())){	
			whereSql+=" and uu.USER_SEX=?";
			whereList.add(pnrAppOpsUserModel.getUserSex());
		}
		//出生年份
		String[] userBorth=null;
		String str2="";
		if(pnrAppOpsUserModel.getUserBorth()!=null){
			userBorth=pnrAppOpsUserModel.getUserBorth();
			for(int i=0;i<userBorth.length;i++){
				if(str2!=""){
					str2+=",";
				}
				str2+=userBorth[i];
			}
			whereSql+=" and uu.USER_BORTH in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str2);
		}
		
		//手机
		if(pnrAppOpsUserModel.getPhone()!=null && !"".equals(pnrAppOpsUserModel.getPhone())){	
			whereSql+=" and uu.PHONE=?";
			whereList.add(pnrAppOpsUserModel.getPhone());
		}
		
		//email
		if(pnrAppOpsUserModel.getEmail()!=null && !"".equals(pnrAppOpsUserModel.getEmail())){	
			whereSql+=" and uu.EMAIL=?";
			whereList.add(pnrAppOpsUserModel.getEmail());
		}
		
		//参加工作时间
		String[] workTime=null;
		String str3="";
		if(pnrAppOpsUserModel.getWorkTime()!=null){
			workTime=pnrAppOpsUserModel.getWorkTime();
			for(int i=0;i<workTime.length;i++){
				if(str3!=""){
					str3+=",";
				}
				str3+=workTime[i];
			}
			whereSql+=" and uu.WORK_TIME in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str3);
		}
		
		//入司时间
		String[] companyTime=null;
		String str4="";
		if(pnrAppOpsUserModel.getCompanyTime()!=null){
			companyTime=pnrAppOpsUserModel.getCompanyTime();
			for(int i=0;i<companyTime.length;i++){
				if(str4!=""){
					str4+=",";
				}
				str4+=companyTime[i];
			}
			whereSql+=" and uu.COMPANY_TIME in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str4);
		}
		
		//从事运维工作时间
		String[] appopsWorkTime=null;
		String str5="";
		if(pnrAppOpsUserModel.getAppopsWorkTime()!=null){
			appopsWorkTime=pnrAppOpsUserModel.getAppopsWorkTime();
			for(int i=0;i<appopsWorkTime.length;i++){
				if(str5!=""){
					str5+=",";
				}
				str5+=appopsWorkTime[i];
			}
			whereSql+=" and uu.APPOPS_WORK_TIME in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str5);
		}
		
		//最高学历
		String[] highestDegree=null;
		String str6="";
		if(pnrAppOpsUserModel.getHighestDegree()!=null){
			highestDegree=pnrAppOpsUserModel.getHighestDegree();
			for(int i=0;i<highestDegree.length;i++){
				if(str6!=""){
					str6+=",";
				}
				str6+=highestDegree[i];
			}
			whereSql+=" and uu.HIGHEST_DEGREE in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str6);
		}
		
		//最高学历毕业院校
		if(pnrAppOpsUserModel.getSchool()!=null && !"".equals(pnrAppOpsUserModel.getSchool())){	
			whereSql+=" and uu.SCHOOL=?";
			whereList.add(pnrAppOpsUserModel.getSchool());
		}
		
		//员工类别
		String[] workSort=null;
		String str7="";
		if(pnrAppOpsUserModel.getWorkSort()!=null){
			workSort=pnrAppOpsUserModel.getWorkSort();
			for(int i=0;i<workSort.length;i++){
				if(str7!=""){
					str7+=",";
				}
				str7+=workSort[i];
			}
			whereSql+=" and uu.WORK_SORT in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str7);
		}
		
		//职称
		String[] jobTitle=null;
		String str8="";
		if(pnrAppOpsUserModel.getJobTitle()!=null){
			jobTitle=pnrAppOpsUserModel.getJobTitle();
			for(int i=0;i<jobTitle.length;i++){
				if(str8!=""){
					str8+=",";
				}
				str8+=jobTitle[i];
			}
			whereSql+=" and uu.JOB_TITLE in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str8);
		}
		//职称
		String[] useSort=null;
		String str9="";
		if(pnrAppOpsUserModel.getUseSort()!=null){
			useSort=pnrAppOpsUserModel.getUseSort();
			for(int i=0;i<useSort.length;i++){
				if(str9!=""){
					str9+=",";
				}
				str9+=useSort[i];
			}
			whereSql+=" and uu.USE_SORT in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str9);
		}
		//岗位职级
		String[] jobLevel=null;
		String str10="";
		if(pnrAppOpsUserModel.getJobLevel()!=null){
			jobLevel=pnrAppOpsUserModel.getJobLevel();
			for(int i=0;i<jobLevel.length;i++){
				if(str10!=""){
					str10+=",";
				}
				str10+=jobLevel[i];
			}
			whereSql+=" and uu.WORK_LEVEL in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str10);
		}
		//所在部门
		if(pnrAppOpsUserModel.getDept()!=null && !"".equals(pnrAppOpsUserModel.getDept())){	
			whereSql+=" and uu.DEPT_ID=?";
			whereList.add(pnrAppOpsUserModel.getDept());
		}
		//所在班组
		if(pnrAppOpsUserModel.getTeam()!=null && !"".equals(pnrAppOpsUserModel.getTeam())){	
			whereSql+=" and uu.TEAM_ID=?";
			whereList.add(pnrAppOpsUserModel.getTeam());
		}
		
		//专业类别
		if(pnrAppOpsUserModel.getSpecialty()!=null && !"".equals(pnrAppOpsUserModel.getSpecialty())){	
			whereSql+=" and uu.SPECIALTY=?";
			whereList.add(pnrAppOpsUserModel.getSpecialty());
		}
		
		//工作岗位-专职
		if(pnrAppOpsUserModel.getOperatingPostZ()!=null && !"".equals(pnrAppOpsUserModel.getOperatingPostZ())){	
			whereSql+=" and uu.WORK_ZHUAN=?";
			whereList.add(pnrAppOpsUserModel.getOperatingPostZ());
		}
		
		//工作岗位-兼职
		if(pnrAppOpsUserModel.getOperatingPostJ()!=null && !"".equals(pnrAppOpsUserModel.getOperatingPostJ())){	
			whereSql+=" and uu.WORK_JIAN=?";
			whereList.add(pnrAppOpsUserModel.getOperatingPostJ());
		}
		//管理层级
		String[] managerLevel=null;
		String str11="";
		if(pnrAppOpsUserModel.getManagerLevel()!=null){
			managerLevel=pnrAppOpsUserModel.getManagerLevel();
			for(int i=0;i<managerLevel.length;i++){
				if(str11!=""){
					str11+=",";
				}
				str11+=managerLevel[i];
			}
			whereSql+=" and uu.MANAGEMENT_LEVEL in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str11);
		}
		
		//工作职责描述
		if(pnrAppOpsUserModel.getWorkDescribe()!=null && !"".equals(pnrAppOpsUserModel.getWorkDescribe())){	
			whereSql+=" and  instr(uu.DESCRIBLE,?, 1, 1)>0";
			whereList.add(pnrAppOpsUserModel.getWorkDescribe());
		}
		
		sql =sql+ " from (select * from pnr_act_appops_user uu where 1=1 "+whereCitySql+whereSql+") u  "+
				  "	  right join (select p.id, p.name from pnr_act_appops_dept p where 1=1 ) pp	"+
				  "      on u.dept_id = pp.id													"+
				  "   right join (select * from  taw_system_dicttype d where 1=1) d				"+           
				  "      on d.dictid = u.company_id												"+
				  "   where d.parentdictid = (select td.dictid	from taw_system_dicttype td where td.dictname = '分公司')	 "+
				  "     and d.dictname != '省公司' "+whereCompanySql+								
				  "   GROUP BY dictid ";                        
				   
		Object values[]=whereList.toArray();
				 
		System.out.println(sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		int d1=0,d2=0,d3=0,d4=0,d5=0,d6=0,d7=0,d8=0,d9=0,d10=0;
		if(list!=null && !list.isEmpty()&& list.size()>0){
			for(Map map :list){
				StatisticsByCompanyAndDept oneData = new StatisticsByCompanyAndDept();
				int rowSum = 0;
				oneData.setCompany(map.get("DICTID").toString());
				oneData.setDept_one(map.get("D1").toString());
				rowSum=rowSum+Integer.parseInt(map.get("D1").toString());
				d1 = d1+Integer.parseInt(map.get("D1").toString());
				oneData.setDept_two(map.get("D2").toString());
				rowSum=rowSum+Integer.parseInt(map.get("D2").toString());
				d2 = d2+Integer.parseInt(map.get("D2").toString());
				oneData.setDept_three(map.get("D3").toString());
				rowSum=rowSum+Integer.parseInt(map.get("D3").toString());
				d3 = d3+Integer.parseInt(map.get("D3").toString());
				oneData.setDept_forth(map.get("D4").toString());
				rowSum=rowSum+Integer.parseInt(map.get("D4").toString());
				d4 = d4+Integer.parseInt(map.get("D4").toString());
				oneData.setDept_five(map.get("D5").toString());
				rowSum=rowSum+Integer.parseInt(map.get("D5").toString());
				d5 = d5+Integer.parseInt(map.get("D5").toString());
				oneData.setDept_sex(map.get("D6").toString());
				rowSum=rowSum+Integer.parseInt(map.get("D6").toString());
				d6 = d6+Integer.parseInt(map.get("D6").toString());
				oneData.setDept_seven(map.get("D7").toString());
				rowSum=rowSum+Integer.parseInt(map.get("D7").toString());
				d7 = d7+Integer.parseInt(map.get("D7").toString());
				oneData.setDept_ength(map.get("D8").toString());
				rowSum=rowSum+Integer.parseInt(map.get("D8").toString());
				d8 = d8+Integer.parseInt(map.get("D8").toString());
				oneData.setDept_nine(map.get("D9").toString());
				rowSum=rowSum+Integer.parseInt(map.get("D9").toString());
				d9 = d9+Integer.parseInt(map.get("D9").toString());
				oneData.setDept_ten(map.get("D10").toString());
				rowSum=rowSum+Integer.parseInt(map.get("D10").toString());
				d10 = d10+Integer.parseInt(map.get("D10").toString());
				
				oneData.setRowSum(rowSum);
				statisticList.add(oneData);
			}
			StatisticsByCompanyAndDept lastData = new StatisticsByCompanyAndDept();
			lastData.setCompany("合计");
			lastData.setDept_one(String.valueOf(d1));
			lastData.setDept_two(String.valueOf(d2));
			lastData.setDept_three(String.valueOf(d3));
			lastData.setDept_forth(String.valueOf(d4));
			lastData.setDept_five(String.valueOf(d5));
			lastData.setDept_sex(String.valueOf(d6));
			lastData.setDept_seven(String.valueOf(d7));
			lastData.setDept_ength(String.valueOf(d8));
			lastData.setDept_nine(String.valueOf(d9));
			lastData.setDept_ten(String.valueOf(d10));
			
			int allSum = d1+d2+d3+d4+d5+d6+d7+d8+d9+d10;
			lastData.setRowSum(allSum);
			statisticList.add(lastData);
		}else{
			statisticList=null;
		}
		return statisticList;
	}
	
	public List<StatisticsBySpecialityAndDept> getStatisticsBySpecialtyAndDept(String userid,String areaid,String[] str,String[] specialty,IPnrPartnerAppOpsUser appopsUser,PnrAppOpsUserModel pnrAppOpsUserModel){
		
		
		
		ArrayList whereList =new ArrayList();
		
		String whereSql="";
		
		//拼接分公司条件sql
		String company[]=null;
		String str1="";
		if(pnrAppOpsUserModel.getCompany()!=null){
			company=pnrAppOpsUserModel.getCompany();
			for(int i=0;i<company.length;i++){
				if(str1!=""){
					str1+=",";
				}
				str1+=company[i];
			}
			whereSql+=" and uu.COMPANY_ID in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str1);
		}
		
		//姓名
		if(pnrAppOpsUserModel.getUserName()!=null && !"".equals(pnrAppOpsUserModel.getUserName())){	
			whereSql+=" and uu.USER_NAME=?";
			whereList.add(pnrAppOpsUserModel.getUserName());
		}
		//性别
		if(pnrAppOpsUserModel.getUserSex()!=null && !"".equals(pnrAppOpsUserModel.getUserSex())){	
			whereSql+=" and uu.USER_SEX=?";
			whereList.add(pnrAppOpsUserModel.getUserSex());
		}
		//出生年份
		String[] userBorth=null;
		String str2="";
		if(pnrAppOpsUserModel.getUserBorth()!=null){
			userBorth=pnrAppOpsUserModel.getUserBorth();
			for(int i=0;i<userBorth.length;i++){
				if(str2!=""){
					str2+=",";
				}
				str2+=userBorth[i];
			}
			whereSql+=" and uu.USER_BORTH in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str2);
		}
		
		//手机
		if(pnrAppOpsUserModel.getPhone()!=null && !"".equals(pnrAppOpsUserModel.getPhone())){	
			whereSql+=" and uu.PHONE=?";
			whereList.add(pnrAppOpsUserModel.getPhone());
		}
		
		//email
		if(pnrAppOpsUserModel.getEmail()!=null && !"".equals(pnrAppOpsUserModel.getEmail())){	
			whereSql+=" and uu.EMAIL=?";
			whereList.add(pnrAppOpsUserModel.getEmail());
		}
		
		//参加工作时间
		String[] workTime=null;
		String str3="";
		if(pnrAppOpsUserModel.getWorkTime()!=null){
			workTime=pnrAppOpsUserModel.getWorkTime();
			for(int i=0;i<workTime.length;i++){
				if(str3!=""){
					str3+=",";
				}
				str3+=workTime[i];
			}
			whereSql+=" and uu.WORK_TIME in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str3);
		}
		
		//入司时间
		String[] companyTime=null;
		String str4="";
		if(pnrAppOpsUserModel.getCompanyTime()!=null){
			companyTime=pnrAppOpsUserModel.getCompanyTime();
			for(int i=0;i<companyTime.length;i++){
				if(str4!=""){
					str4+=",";
				}
				str4+=companyTime[i];
			}
			whereSql+=" and uu.COMPANY_TIME in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str4);
		}
		
		//从事运维工作时间
		String[] appopsWorkTime=null;
		String str5="";
		if(pnrAppOpsUserModel.getAppopsWorkTime()!=null){
			appopsWorkTime=pnrAppOpsUserModel.getAppopsWorkTime();
			for(int i=0;i<appopsWorkTime.length;i++){
				if(str5!=""){
					str5+=",";
				}
				str5+=appopsWorkTime[i];
			}
			whereSql+=" and uu.APPOPS_WORK_TIME in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str5);
		}
		
		//最高学历
		String[] highestDegree=null;
		String str6="";
		if(pnrAppOpsUserModel.getHighestDegree()!=null){
			highestDegree=pnrAppOpsUserModel.getHighestDegree();
			for(int i=0;i<highestDegree.length;i++){
				if(str6!=""){
					str6+=",";
				}
				str6+=highestDegree[i];
			}
			whereSql+=" and uu.HIGHEST_DEGREE in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str6);
		}
		
		//最高学历毕业院校
		if(pnrAppOpsUserModel.getSchool()!=null && !"".equals(pnrAppOpsUserModel.getSchool())){	
			whereSql+=" and uu.SCHOOL=?";
			whereList.add(pnrAppOpsUserModel.getSchool());
		}
		
		//员工类别
		String[] workSort=null;
		String str7="";
		if(pnrAppOpsUserModel.getWorkSort()!=null){
			workSort=pnrAppOpsUserModel.getWorkSort();
			for(int i=0;i<workSort.length;i++){
				if(str7!=""){
					str7+=",";
				}
				str7+=workSort[i];
			}
			whereSql+=" and uu.WORK_SORT in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str7);
		}
		
		//职称
		String[] jobTitle=null;
		String str8="";
		if(pnrAppOpsUserModel.getJobTitle()!=null){
			jobTitle=pnrAppOpsUserModel.getJobTitle();
			for(int i=0;i<jobTitle.length;i++){
				if(str8!=""){
					str8+=",";
				}
				str8+=jobTitle[i];
			}
			whereSql+=" and uu.JOB_TITLE in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str8);
		}
		//职称
		String[] useSort=null;
		String str9="";
		if(pnrAppOpsUserModel.getUseSort()!=null){
			useSort=pnrAppOpsUserModel.getUseSort();
			for(int i=0;i<useSort.length;i++){
				if(str9!=""){
					str9+=",";
				}
				str9+=useSort[i];
			}
			whereSql+=" and uu.USE_SORT in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str9);
		}
		//岗位职级
		String[] jobLevel=null;
		String str10="";
		if(pnrAppOpsUserModel.getJobLevel()!=null){
			jobLevel=pnrAppOpsUserModel.getJobLevel();
			for(int i=0;i<jobLevel.length;i++){
				if(str10!=""){
					str10+=",";
				}
				str10+=jobLevel[i];
			}
			whereSql+=" and uu.WORK_LEVEL in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str10);
		}
		//所在部门
		if(pnrAppOpsUserModel.getDept()!=null && !"".equals(pnrAppOpsUserModel.getDept())){	
			whereSql+=" and uu.DEPT_ID=?";
			whereList.add(pnrAppOpsUserModel.getDept());
		}
		//所在班组
		if(pnrAppOpsUserModel.getTeam()!=null && !"".equals(pnrAppOpsUserModel.getTeam())){	
			whereSql+=" and uu.TEAM_ID=?";
			whereList.add(pnrAppOpsUserModel.getTeam());
		}
		
		//专业类别
		if(pnrAppOpsUserModel.getSpecialty()!=null && !"".equals(pnrAppOpsUserModel.getSpecialty())){	
			whereSql+=" and uu.SPECIALTY=?";
			whereList.add(pnrAppOpsUserModel.getSpecialty());
		}
		
		//工作岗位-专职
		if(pnrAppOpsUserModel.getOperatingPostZ()!=null && !"".equals(pnrAppOpsUserModel.getOperatingPostZ())){	
			whereSql+=" and uu.WORK_ZHUAN=?";
			whereList.add(pnrAppOpsUserModel.getOperatingPostZ());
		}
		
		//工作岗位-兼职
		if(pnrAppOpsUserModel.getOperatingPostJ()!=null && !"".equals(pnrAppOpsUserModel.getOperatingPostJ())){	
			whereSql+=" and uu.WORK_JIAN=?";
			whereList.add(pnrAppOpsUserModel.getOperatingPostJ());
		}
		//管理层级
		String[] managerLevel=null;
		String str11="";
		if(pnrAppOpsUserModel.getManagerLevel()!=null){
			managerLevel=pnrAppOpsUserModel.getManagerLevel();
			for(int i=0;i<managerLevel.length;i++){
				if(str11!=""){
					str11+=",";
				}
				str11+=managerLevel[i];
			}
			whereSql+=" and uu.MANAGEMENT_LEVEL in(select * from table( cast(f_str2List(?) as varchar2TableType))) ";
			whereList.add(str11);
		}
		
		//工作职责描述
		if(pnrAppOpsUserModel.getWorkDescribe()!=null && !"".equals(pnrAppOpsUserModel.getWorkDescribe())){	
			whereSql+=" and  instr(uu.DESCRIBLE,?, 1, 1)>0";
			whereList.add(pnrAppOpsUserModel.getWorkDescribe());
		}
		
		//获取登陆人所在地区的等级
//		String citySql = "select a.ordercode from taw_system_user u ," +
//				" taw_system_dept d," +
//				"taw_system_area a" +
//				" where u.deptid = d.deptid" +
//				" and d.areaid = a.areaid" +
//				"  and u.userid='"+userid+"'";
//		List<Map> levelList = this.getJdbcTemplate().queryForList(citySql);
//		String whereCitySql=" and uu.company_id=(";
//		String userCity="";
//		if(levelList!=null && levelList.size()>0){
//			userCity = levelList.get(0).get("ORDERCODE").toString();
//		}
		
		String whereCitySql=""; //地市查看权限
		if(areaid!=null && !"".equals(areaid)){
			if((StaticMethod.nullObject2String(areaid)).length()==2){
				whereCitySql += "";
			}else if((StaticMethod.nullObject2String(areaid)).length()==4){
				whereCitySql += " and instr(uu.area_id,?,1,1)=1";
				whereList.add(areaid);
			}else if((StaticMethod.nullObject2String(areaid)).length()==6){
				whereCitySql += " and uu.area_id=? ";
				whereList.add(areaid);
			}
		}
//		userCity="2";
//		if("2".equals(userCity)){
//			whereCitySql = whereCitySql+"select d1.dictid from taw_system_area a1,taw_system_dicttype d1" +
//					" where a1.areaid = (select a.parentareaid from taw_system_user u , taw_system_dept d," +
//					"  taw_system_area a" +
//					" where u.deptid = d.deptid and d.areaid = a.areaid and u.userid='"+userid+"' )" +
//					" and a1.areaname = d1.dictname)";
//		}else if("3".equals(userCity)){
//			whereCitySql = whereCitySql+"select d1.dictid from taw_system_area a1,taw_system_dicttype d1" +
//			" where a1.areaid = (select a.areaid from taw_system_user u , taw_system_dept d," +
//			"  taw_system_area a" +
//			" where u.deptid = d.deptid and d.areaid = a.areaid and u.userid='"+userid+"' )" +
//			" and a1.areaname = d1.dictname)";
//		}else{
//			whereCitySql = "";
//		}
		whereSql = whereSql +whereCitySql;
		//获取要查的部门
		String stris = "";
		if(str.length>0){
			for(int i=0;i<str.length;i++){
				if(i==0){
					stris = "'"+str[i]+"'";
				}else{
					stris = stris+",'"+str[i]+"'";
				}
			}
		}
		
		
		//专业和部门的统计语句
		String sql = "select d3.name";
		if(specialty!=null && specialty.length>0){
			for(int i=0;i<specialty.length;i++){
				sql =sql+",sum(case when u1.specialty  in(select d.dictid from  taw_system_dicttype d where d.dictname='"+specialty[i]+"' and d.dictid<>'1020901') then 1 else 0 end) as d"+i;
			}
		}
		sql = sql+" from (select * from pnr_act_appops_user uu where 1=1" +whereSql+
				") u1 right join (" +
				" select p.id, substr(p.name, 1, 4) as name "+
				"  from pnr_act_appops_dept p	"+
	            "  where instr(p.name, '维护中心', 1, 1)=1	"+
	            " union all	"+
	            " select p.id, substr(p.name, 1, 6) as name "+
	            "   from pnr_act_appops_dept p			"+
	            "  where instr(p.name, '线路维护中心', 1, 1)=1	 "+
	            " union all				"+
	            " select p.id,TRANSLATE(p.name, p.name, '运行维护部') as name	"+
	            "   from pnr_act_appops_dept p						"+
	            "  where instr(p.name, '运行维护部', 1, 1)>0			"+
	            " union all											"+
	            " select p.id,TRANSLATE(p.name, p.name, '网络维护中心') as name "+
	            "   from pnr_act_appops_dept p					"+
	            "  where instr(p.name, '网络维护中心', 1, 1)>0		"+
	            "  union all			"+
	            " select p.id,TRANSLATE(p.name, p.name, '网络优化中心') as name	"+
	            "   from pnr_act_appops_dept p		"+
	            "  where instr(p.name, '网络优化中心', 1, 1)>0		"+
	            "  union all			"+
	            " select p.id,TRANSLATE(p.name, p.name, '公众客户客响中心') as name	"+
	            "   from pnr_act_appops_dept p	"+
	            "  where instr(p.name, '公众客户响应中心', 1, 1)>0			"+
	            "  union all		"+
	            " select p.id,TRANSLATE(p.name, p.name, '集团客户客响中心') as name	"+
	            "   from pnr_act_appops_dept p		"+
	            "  where instr(p.name, '集团客户响应中心', 1, 1)>0		"+
	            "  union all		"+
	            " select p.id,TRANSLATE(p.name, p.name, '资源管理中心') as name	"+
	            "   from pnr_act_appops_dept p		"+
	            "  where instr(p.name, '资源管理中心', 1, 1)>0		"+
	            "   union all			"+
	            " select p.id,TRANSLATE(p.name, p.name, '省级平台维护中心') as name	"+
	            "   from pnr_act_appops_dept p			"+
	            "  where instr(p.name, '省级平台维护中心', 1, 1)>0			"+
	            "   union all	"+
	            " select p.id,TRANSLATE(p.name, p.name, '国际海缆监控中心') as name	"+
	            "   from pnr_act_appops_dept p	"+
	            "  where instr(p.name, '国际海缆监控中心', 1, 1)>0	) d3"+
				
	            " on u1.dept_id = d3.id group by d3.name";
		
		
		
		Object values[] =whereList.toArray();
		
		
		System.out.println("===="+sql);
		List<StatisticsBySpecialityAndDept> returnList = new ArrayList<StatisticsBySpecialityAndDept>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		int a1=0,a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,a10=0;
		int b1=0,b2=0,b3=0,b4=0,b5=0,b6=0,b7=0,b8=0;
		if(list!=null && list.size()>0){
			for(Map map:list){
				StatisticsBySpecialityAndDept sbsd = new StatisticsBySpecialityAndDept();
				int rowSum=0;
				sbsd.setDeptId(map.get("NAME").toString());
				sbsd.setDirector(map.get("D0").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D0").toString());
				a1 = a1+Integer.parseInt(map.get("D0").toString());
				
				sbsd.setCoreNetWork(map.get("D1").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D1").toString());
				a2 = a2+Integer.parseInt(map.get("D1").toString());
				
				sbsd.setInfiniteNet(map.get("D2").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D2").toString());
				a3 = a3+Integer.parseInt(map.get("D2").toString());
				
				sbsd.setTransmission(map.get("D3").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D3").toString());
				a4 = a4+Integer.parseInt(map.get("D3").toString());
				
				sbsd.setDataNetWork(map.get("D4").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D4").toString());
				a5 = a5+Integer.parseInt(map.get("D4").toString());
				
				sbsd.setAccessNetWork(map.get("D5").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D5").toString());
				a6 = a6+Integer.parseInt(map.get("D5").toString());
				
				sbsd.setBusinessPlatform(map.get("D6").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D6").toString());
				a7 = a7+Integer.parseInt(map.get("D6").toString());
				
				sbsd.setExchangeNetWork(map.get("D7").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D7").toString());
				a8 = a8+Integer.parseInt(map.get("D7").toString());
				
				sbsd.setLineMaintenance(map.get("D8").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D8").toString());
				a9 = a9+Integer.parseInt(map.get("D8").toString());
				
				sbsd.setSystemMaintenance(map.get("D9").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D9").toString());
				a10 = a10+Integer.parseInt(map.get("D9").toString());
				
				sbsd.setWebService(map.get("D10").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D10").toString());
				b1 = b1+Integer.parseInt(map.get("D10").toString());
				
				sbsd.setDynamicEnvironment(map.get("D11").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D11").toString());
				b2 = b2+Integer.parseInt(map.get("D11").toString());
				
				sbsd.setResourceAdministration(map.get("D12").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D12").toString());
				b3 = b3+Integer.parseInt(map.get("D12").toString());
				
				sbsd.setNetworkSecurity(map.get("D13").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D13").toString());
				b4 = b4+Integer.parseInt(map.get("D13").toString());
				
				sbsd.setNetworkMonitoring(map.get("D14").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D14").toString());
				b5 = b5+Integer.parseInt(map.get("D14").toString());
				
				sbsd.setTwister(map.get("D15").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D15").toString());
				b6 = b6+Integer.parseInt(map.get("D15").toString());
				
				sbsd.setMaintenance(map.get("D16").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D16").toString());
				b7 = b7+Integer.parseInt(map.get("D16").toString());
				
				sbsd.setComprehensive(map.get("D17").toString());
				rowSum = rowSum+Integer.parseInt(map.get("D17").toString());
				b8 = b8+Integer.parseInt(map.get("D17").toString());
				
				sbsd.setRowSum(rowSum);
				
				returnList.add(sbsd);
			}
			StatisticsBySpecialityAndDept sbsd = new StatisticsBySpecialityAndDept();
			sbsd.setDeptId("合计");
			sbsd.setDirector(String.valueOf(a1));
			sbsd.setCoreNetWork(String.valueOf(a2));
			sbsd.setInfiniteNet(String.valueOf(a3));
			sbsd.setTransmission(String.valueOf(a4));
			sbsd.setDataNetWork(String.valueOf(a5));
			sbsd.setAccessNetWork(String.valueOf(a6));
			sbsd.setBusinessPlatform(String.valueOf(a7));
			sbsd.setExchangeNetWork(String.valueOf(a8));
			sbsd.setLineMaintenance(String.valueOf(a9));
			sbsd.setSystemMaintenance(String.valueOf(a10));
			sbsd.setWebService(String.valueOf(b1));
			sbsd.setDynamicEnvironment(String.valueOf(b2));
			sbsd.setResourceAdministration(String.valueOf(b3));
			sbsd.setNetworkSecurity(String.valueOf(b4));
			sbsd.setNetworkMonitoring(String.valueOf(b5));
			sbsd.setTwister(String.valueOf(b6));
			sbsd.setMaintenance(String.valueOf(b7));
			sbsd.setComprehensive(String.valueOf(b8));
			
			int allSum = a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+b1+b2+b3+b4+b5+b6+b7+b8;
			sbsd.setRowSum(allSum);
			
			returnList.add(sbsd);
			
		}else{
			returnList=null;
		}
		
		return returnList;
	}
	
	
	@Override
	public List<StatisticsBySpecialityAndDept> getStatisticsByCityAndSpecialty(
			String userid,String organization, String city, String[] specialty,IPnrPartnerAppOpsUser appopsUser) {
		
		//条件查询语句
		String whereSql = " where 1=1 ";
		//用工类别
		String useSort = appopsUser.getUseSort();
		if(useSort!=null && !"".equals(useSort)){
			whereSql = whereSql + " and uu.use_sort='"+useSort+"'";
		}
		//最高学历
		String highDegree = appopsUser.getHighestDegree();
		if(highDegree!=null && !"".equals(highDegree)){
			whereSql = whereSql + " and uu.highest_degree='"+highDegree+"'" ;
		}
		//职称
		String jobTitle = appopsUser.getJobTitle();
		if(jobTitle!=null && !"".equals(jobTitle)){
			whereSql = whereSql + "  and uu.job_title='"+jobTitle+"'";
		}
		//工作岗位（专职）
		String zhuan = appopsUser.getOperatingPostZ();
		if(zhuan!=null && !"".equals(zhuan)){
			whereSql = whereSql + " and uu.work_zhuan='"+zhuan+"'";
		}
		//工作岗位（兼职）
		String jian = appopsUser.getOperatingPostJ();
		if(jian!=null && !"".equals(jian)){
			whereSql = whereSql + " and uu.work_jian like '%"+jian+"%'";
		}
		
		String sql ="select d3.name";
		if(specialty!=null &&specialty.length>0){
			for(int i=0;i<specialty.length;i++){
				
				sql = sql+",sum(case when pu.specialty in(select d4.dictid from  taw_system_dicttype d4 where d4.dictname='"+specialty[i]+"' and d4.dictid<>'1020901') then 1 else 0 end) as d"+i;
			}
		}
		sql = sql +" from (select *  from pnr_act_appops_user uu "+whereSql+") pu  right join pnr_act_appops_dept d3" +
				" on pu.dept_id=d3.id " +
				" where d3.id in(select d2.id from pnr_act_appops_dept d2 " +
				" where d2.interface_head_id=(select d1.id from pnr_act_appops_dept d1 " +
				" where d1.area_name=(select d.dictname  from taw_system_dicttype d where d.dictid='"+city+"')" +
				" and d1.interface_head_id=(select d.id from pnr_act_appops_dept d where d.name='运维人员')))" +
				" group by d3.name";
		
		System.out.println("sql========"+sql);
		List<StatisticsBySpecialityAndDept> returnList = new ArrayList<StatisticsBySpecialityAndDept>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		
			int a1=0,a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,a10=0;
			int b1=0,b2=0,b3=0,b4=0,b5=0,b6=0,b7=0,b8=0;
			if(list!=null && list.size()>0){
				for(Map map:list){
					StatisticsBySpecialityAndDept sbsd = new StatisticsBySpecialityAndDept();
					int rowSum=0;
					sbsd.setDeptId(map.get("NAME").toString());
					sbsd.setDirector(map.get("D0").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D0").toString());
					a1 = a1+Integer.parseInt(map.get("D0").toString());
					
					sbsd.setCoreNetWork(map.get("D1").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D1").toString());
					a2 = a2+Integer.parseInt(map.get("D1").toString());
					
					sbsd.setInfiniteNet(map.get("D2").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D2").toString());
					a3 = a3+Integer.parseInt(map.get("D2").toString());
					
					sbsd.setTransmission(map.get("D3").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D3").toString());
					a4 = a4+Integer.parseInt(map.get("D3").toString());
					
					sbsd.setDataNetWork(map.get("D4").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D4").toString());
					a5 = a5+Integer.parseInt(map.get("D4").toString());
					
					sbsd.setAccessNetWork(map.get("D5").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D5").toString());
					a6 = a6+Integer.parseInt(map.get("D5").toString());
					
					sbsd.setBusinessPlatform(map.get("D6").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D6").toString());
					a7 = a7+Integer.parseInt(map.get("D6").toString());
					
					sbsd.setExchangeNetWork(map.get("D7").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D7").toString());
					a8 = a8+Integer.parseInt(map.get("D7").toString());
					
					sbsd.setLineMaintenance(map.get("D8").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D8").toString());
					a9 = a9+Integer.parseInt(map.get("D8").toString());
					
					sbsd.setSystemMaintenance(map.get("D9").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D9").toString());
					a10 = a10+Integer.parseInt(map.get("D9").toString());
					
					sbsd.setWebService(map.get("D10").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D10").toString());
					b1 = b1+Integer.parseInt(map.get("D10").toString());
					
					sbsd.setDynamicEnvironment(map.get("D11").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D11").toString());
					b2 = b2+Integer.parseInt(map.get("D11").toString());
					
					sbsd.setResourceAdministration(map.get("D12").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D12").toString());
					b3 = b3+Integer.parseInt(map.get("D12").toString());
					
					sbsd.setNetworkSecurity(map.get("D13").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D13").toString());
					b4 = b4+Integer.parseInt(map.get("D13").toString());
					
					sbsd.setNetworkMonitoring(map.get("D14").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D14").toString());
					b5 = b5+Integer.parseInt(map.get("D14").toString());
					
					sbsd.setTwister(map.get("D15").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D15").toString());
					b6 = b6+Integer.parseInt(map.get("D15").toString());
					
					sbsd.setMaintenance(map.get("D16").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D16").toString());
					b7 = b7+Integer.parseInt(map.get("D16").toString());
					
					sbsd.setComprehensive(map.get("D17").toString());
					rowSum = rowSum+Integer.parseInt(map.get("D17").toString());
					b8 = b8+Integer.parseInt(map.get("D17").toString());
					
					sbsd.setRowSum(rowSum);
					
					returnList.add(sbsd);
				}
				StatisticsBySpecialityAndDept sbsd = new StatisticsBySpecialityAndDept();
				sbsd.setDeptId("合计");
				sbsd.setDirector(String.valueOf(a1));
				sbsd.setCoreNetWork(String.valueOf(a2));
				sbsd.setInfiniteNet(String.valueOf(a3));
				sbsd.setTransmission(String.valueOf(a4));
				sbsd.setDataNetWork(String.valueOf(a5));
				sbsd.setAccessNetWork(String.valueOf(a6));
				sbsd.setBusinessPlatform(String.valueOf(a7));
				sbsd.setExchangeNetWork(String.valueOf(a8));
				sbsd.setLineMaintenance(String.valueOf(a9));
				sbsd.setSystemMaintenance(String.valueOf(a10));
				sbsd.setWebService(String.valueOf(b1));
				sbsd.setDynamicEnvironment(String.valueOf(b2));
				sbsd.setResourceAdministration(String.valueOf(b3));
				sbsd.setNetworkSecurity(String.valueOf(b4));
				sbsd.setNetworkMonitoring(String.valueOf(b5));
				sbsd.setTwister(String.valueOf(b6));
				sbsd.setMaintenance(String.valueOf(b7));
				sbsd.setComprehensive(String.valueOf(b8));
				int allSum = a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+b1+b2+b3+b4+b5+b6+b7+b8;
				sbsd.setRowSum(allSum);
				
				returnList.add(sbsd);
			}else{
				returnList=null;
			}
		return returnList;
	}
	/**
	 * 根据专业名称，获取相应的专业字典值
	  * @author wangyue
	  * @title: getDictionary
	  * @date Oct 11, 2014 11:03:20 AM
	  * @param speciallyName
	  * @return String
	 */
	public String getDictionary(String speciallyName){
		String sql ="select t.dictid from taw_system_dicttype t where t.dictname='"+speciallyName+"'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		String dictId = "";
		if(list!=null && list.size()>0){
			dictId = (String)list.get(0).get("DICTID");
		}
		
		return dictId;
	}
	public String getAreaIdByTeam(String name){
		String sql = "select d.area_id,d.* from pnr_act_appops_dept d where d.name='"+name+"'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		String areaId = "";
		if(list!=null && list.size()>0){
			areaId = (String)list.get(0).get("AREA_ID");
		}
		return areaId;
	}
	/**
	 * 根据部门名称或者班组名称获取相应的id
	  * @author wangyue
	  * @title: getNameBydeptIdOrTeamId
	  * @date Oct 11, 2014 10:58:48 AM
	  * @return String
	 */
	public String getIdBydeptIdOrTeamName(String name,String organizationNumber,String flag){
		String sql="";
		if("1".equals(flag)){
			sql ="select d.id from pnr_act_appops_dept d where d.name='"+name+"' " +
			" and d.organizationno='"+organizationNumber+"'";
			
		}else if("2".equals(flag)){
			sql = "select d.id from pnr_act_appops_dept d where d.name='"+name+"' and d.organizationno like '"+organizationNumber+"%'";
		}
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		String common = "";
		if(list!=null && list.size()>0){
			common = (String)list.get(0).get("ID");
		}
		return common;
	}
	@Override
	public String getIdByOrganizationNumber(
			String organizationNumber,String flag) {
		String sql = "";
		if("1".equals(flag)){
			sql ="select d.id from pnr_act_appops_dept d where  d.name='"+organizationNumber+"'";

		}else if("2".equals(flag)){
			
			sql ="select d.id from pnr_act_appops_dept d where  d.organizationno='"+organizationNumber+"'";
		}
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		String common = "";
		if(list!=null && list.size()>0){
			common = (String)list.get(0).get("ID");
		}
		return common;
	}
	
	/**
	 * 通过部门id值获取部门编码
	 * @param id
	 * @return
	 */
	public String getDeptCodeFromDeptId(String id){
		String sql ="select d.organizationno from pnr_act_appops_dept d where d.id='"+id+"'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		String common = "";
		if(list!=null && list.size()>0){
			common = (String)list.get(0).get("ORGANIZATIONNO");
		}
		return common;
	}
	
	/**
	 * 验证手机号码唯一性
	 * @param mobile 手机号码
	 * @return 返回存在号码的个数，1为存在；0为不存在
	 */
	public int getMobileCheck(String mobile,String userId){
		String sql="";
		if(userId!=null&&!userId.equals("")){
			sql ="select u.id from pnr_act_appops_user u where u.phone='"+mobile+"' and u.id !='"+userId+"'";
		}else{
			sql ="select u.id from pnr_act_appops_user u where u.phone='"+mobile+"'";
		}	 
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		if(list!=null && list.size()>0){
			return 1;
		}else {
			return 0;
		}	
	}

}
