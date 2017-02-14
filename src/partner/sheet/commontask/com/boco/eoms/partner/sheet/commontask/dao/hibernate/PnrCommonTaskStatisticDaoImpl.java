package com.boco.eoms.partner.sheet.commontask.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.dao.jdbc.BaseDaoJdbc;
import com.boco.eoms.partner.sheet.commontask.dao.PnrCommonTaskStatisticDao;

public class PnrCommonTaskStatisticDaoImpl extends BaseDaoJdbc implements PnrCommonTaskStatisticDao{
	
	
	
	/**
	 * 根据部门Id获取对应的Areaid
	 */
	public String getDeptIdToAreaId(final String deptid){
		String areaid = "";
		String queryStr = "select areaid from taw_system_dept where deptid='"+deptid+"' and deleted=0";
		System.out.println(queryStr);
		List list = this.getJdbcTemplate().queryForList(queryStr);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			areaid = StaticMethod.nullObject2String(map.get("areaid"));
		}
		return areaid;
	}
	
	/**
	 * 根据Areaid获取对应的代维公司
	 */
	public List getAreaIdToCompanyIdList(final String deptid){
		List list = new ArrayList();
		String queryStr = "select id,dept_mag_id from pnr_dept where area_id='"+deptid+"' and deleted=0 " +
		"and deptlevel=(select min(deptlevel) from pnr_dept where area_id='"+deptid+"' and deleted=0 ) order by dept_mag_id";
		System.out.println(queryStr);
		list = this.getJdbcTemplate().queryForList(queryStr);
		return list;
	}
	
	/**
	 * 获取对应区域下子区域的id
	 */
	public List getAreaIdList(final String parentareaid){
		String queryStr = "select areaid from taw_system_area where parentareaid='"+parentareaid+"'";
		System.out.println(queryStr);
		List list = this.getJdbcTemplate().queryForList(queryStr);
		return list;
	}
	
	/**
	 * 获取对应代维公司下子代维的id
	 */
	public List getCompanyIdList(final String parentcompanyid){
		String queryStr = "select id,dept_mag_id,area_id as areaid from pnr_dept where interface_head_id='"+parentcompanyid+"' and id!=interface_head_id  and deleted=0";
		System.out.println(queryStr);
		List list = this.getJdbcTemplate().queryForList(queryStr);
		return list;
	}
	
	/**
	 * 获取对应代维公司deptid获取id
	 */
	public List getCompanyDeptToIdList(final String deptid){
		String queryStr = "select id from pnr_dept where dept_mag_id='"+deptid+"'  and deleted=0";
		System.out.println(queryStr);
		List list = this.getJdbcTemplate().queryForList(queryStr);
		return list;
	}
	
	public String getCompanyIdToDeptIdList(final String compid){
		String deptid = "";
		String queryStr = "select dept_mag_id from pnr_dept where id='"+compid+"'  and deleted=0";
		List list = this.getJdbcTemplate().queryForList(queryStr);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			deptid = StaticMethod.nullObject2String(map.get("dept_mag_id"));
		}
		return deptid;
	}

	
	/**
	 * 当只勾选了区域
	 */
	public List getSheetAreaIndexList(final String deptid, final String whereStr){
		
		List list = new ArrayList();
		String[] index01 = {"0","1","-12","-13","-14"}; //0运行中;1已归档;-12已作废;-13强制归档;-14强制作废;  --工单状态指标
		for(int m=0;m<index01.length;m++){					
			String queryStr = "select count(distinct id) as sheetnum from pnr_commontask_status where status='"+index01[m]+"' and operatedeptid like '"+deptid+"%'";
			if (whereStr != null && whereStr.length() > 0){
				queryStr += whereStr;
			}
			System.out.println(queryStr);
			List listIndex = this.getJdbcTemplate().queryForList(queryStr);
			Map map = (Map)listIndex.get(0);
			list.add(StaticMethod.nullObject2int(map.get("sheetnum")));
		}
		String[] index02 = {"0","1"}; //0未超时;1已超时; --未接单指标
		for(int m=0;m<index02.length;m++){					
			String queryStr = "select count(distinct id) as sheetnum from pnr_commontask_status where status=0 and taskstatus=2 and acceptflag='"+index02[m]+"' and tooperatedeptid like '"+deptid+"%'";
			if (whereStr != null && whereStr.length() > 0){
				String newWhereStr= whereStr;
				newWhereStr = newWhereStr.replace("operateuserid","tooperateobject");
				newWhereStr = newWhereStr.replace("operatedeptid","tooperateobject");
				queryStr += newWhereStr;
			}
			System.out.println(queryStr);
			List listIndex = this.getJdbcTemplate().queryForList(queryStr);
			Map map = (Map)listIndex.get(0);
			list.add(StaticMethod.nullObject2int(map.get("sheetnum")));
		}
		String[] index03 = {"0","1"}; //0未超时;1已超时;  --已接单指标
		for(int m=0;m<index03.length;m++){					
			String queryStr = "select count(distinct a.id) as sheetnum from " +
					"(select max(operatetime) as maxoperatetime,id from pnr_commontask_status " +
					"where status in(0,1) and acceptflag='"+index03[m]+"' and operatedeptid like '"+deptid+"%'"+whereStr+" group by id) tab1,pnr_commontask_status a " +
					"where tab1.maxoperatetime=a.operatetime and tab1.id=a.id";
			System.out.println(queryStr);
			List listIndex = this.getJdbcTemplate().queryForList(queryStr);
			Map map = (Map)listIndex.get(0);
			list.add(StaticMethod.nullObject2int(map.get("sheetnum")));
		}
		String[] index04 = {"0","1"}; //0未超时;1已超时; --已回复指标
		for(int m=0;m<index04.length;m++){					
			String queryStr = "select count(distinct a.id) as sheetnum from " +
					"(select max(operatetime) as maxoperatetime,id from pnr_commontask_status " +
					"where status in(0,1) and taskstatus=5 and operatetype='95' and taskname='HoldTask' and acceptflag='"+index04[m]+"' and operatedeptid like '"+deptid+"%'"+whereStr+" group by id) tab1,pnr_commontask_status a " +
					"where tab1.maxoperatetime=a.operatetime and tab1.id=a.id";
			System.out.println(queryStr);
			List listIndex = this.getJdbcTemplate().queryForList(queryStr);
			Map map = (Map)listIndex.get(0);
			list.add(StaticMethod.nullObject2int(map.get("sheetnum")));
		}
		String[] index05 = {">95","between 95 and 60","<60"}; //0满意;1一般;不满意;
		for(int m=0;m<index05.length;m++){					
			String queryStr = "select count(distinct id) as sheetnum from pnr_commontask_status where status=1 and taskstatus=5 and operatetype='95' and taskname='HoldTask' and scoretotal "+index05[m] +" and operatedeptid like '"+deptid+"%'";
			if (whereStr != null && whereStr.length() > 0){
				queryStr += whereStr;
			}
			System.out.println(queryStr);
			List listIndex = this.getJdbcTemplate().queryForList(queryStr);
			Map map = (Map)listIndex.get(0);
			list.add(StaticMethod.nullObject2int(map.get("sheetnum")));
		}
		return list;
	}
	
	public String getMainIdStr(final String sqlStr){	
		String queryStr = sqlStr;
		System.out.println(queryStr);
		List list = this.getJdbcTemplate().queryForList(queryStr);
		String id ="";
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				if(id.equals("")){
					id = "'"+StaticMethod.nullObject2String(map.get("id"))+"'";
				}else{
					id = id+",'"+StaticMethod.nullObject2String(map.get("id"))+"'";
				}
			}
		}
		return id;
	}
	
}
