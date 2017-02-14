package com.boco.eoms.partner.sheet.commontask.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.sheet.commontask.dao.PnrCommonTaskStatisticDao;
import com.boco.eoms.partner.sheet.commontask.dao.PnrCommonTaskStatisticDaoHibernate;
import com.boco.eoms.partner.sheet.commontask.service.PnrCommonTaskStatisticMgr;

public class PnrCommonTaskStatisticMgrImpl implements PnrCommonTaskStatisticMgr{

	private PnrCommonTaskStatisticDao pnrCommonTaskStatisticDao;
	
	private PnrCommonTaskStatisticDaoHibernate pnrCommonTaskStatisticDaoHibernate;

	
	
	public List getSheetAreaIndexList( final String deptid,final String whereStr){
		List list = pnrCommonTaskStatisticDao.getSheetAreaIndexList(deptid, whereStr);
		return list;
	}

	public String getDeptIdToAreaId(String deptid) {
		// TODO Auto-generated method stub
		String areaid = pnrCommonTaskStatisticDao.getDeptIdToAreaId(deptid);
		return areaid;
	}

	public List getAreaIdToCompanyIdList(String deptid) {
		// TODO Auto-generated method stub
		List list = pnrCommonTaskStatisticDao.getAreaIdToCompanyIdList(deptid);
		return list;
	}

	public List getAreaIdList(String parentareaid) {
		// TODO Auto-generated method stub
		List list = pnrCommonTaskStatisticDao.getAreaIdList(parentareaid);
		return list;
	}

	public List getCompanyIdList(String parentcompanyid) {
		// TODO Auto-generated method stub
		List list = pnrCommonTaskStatisticDao.getCompanyIdList(parentcompanyid);
		return list;
	}

	public List getCompanyDeptToIdList(String deptid) {
		// TODO Auto-generated method stub
		List list = pnrCommonTaskStatisticDao.getCompanyDeptToIdList(deptid);
		return list;
	}

	public String getCompanyIdToDeptIdList(String compid) {
		// TODO Auto-generated method stub
		String deptid = pnrCommonTaskStatisticDao.getCompanyIdToDeptIdList(compid);
		return deptid;
	}
	
	public PnrCommonTaskStatisticDao getPnrCommonTaskStatisticDao() {
		return pnrCommonTaskStatisticDao;
	}

	public void setPnrCommonTaskStatisticDao(
			PnrCommonTaskStatisticDao pnrCommonTaskStatisticDao) {
		this.pnrCommonTaskStatisticDao = pnrCommonTaskStatisticDao;
	}

	public PnrCommonTaskStatisticDaoHibernate getPnrCommonTaskStatisticDaoHibernate() {
		return pnrCommonTaskStatisticDaoHibernate;
	}

	public void setPnrCommonTaskStatisticDaoHibernate(
			PnrCommonTaskStatisticDaoHibernate pnrCommonTaskStatisticDaoHibernate) {
		this.pnrCommonTaskStatisticDaoHibernate = pnrCommonTaskStatisticDaoHibernate;
	}

	public Map querySheetIndexList(Integer total, Integer curPage,
			Integer pageSize, String deptid, String flag, String whereStr) {
		// TODO Auto-generated method stub
		String sqlStr = "";
		if(flag.equals("0")) {		
			sqlStr = "select id from pnr_commontask_status where status='0' and operatedeptid like '"+deptid+"%'"+whereStr;
		}
		if(flag.equals("1")) {
			sqlStr = "select id from pnr_commontask_status where status='1' and operatedeptid like '"+deptid+"%'"+whereStr;
		}
		if(flag.equals("2")) {
			sqlStr = "select id from pnr_commontask_status where status='-12' and operatedeptid like '"+deptid+"%'"+whereStr;
		}
		if(flag.equals("3")) {
			sqlStr = "select id from pnr_commontask_status where status='-13' and operatedeptid like '"+deptid+"%'"+whereStr;
		}
		if(flag.equals("4")) {
			sqlStr = "select id from pnr_commontask_status where status='-14' and operatedeptid like '"+deptid+"%'"+whereStr;	
		}
		if(flag.equals("5")) {
			whereStr = whereStr.replace("operateuserid","tooperateobject");
			whereStr = whereStr.replace("operatedeptid","tooperateobject");
			sqlStr = "select id from pnr_commontask_status where status=0 and taskstatus=2 and acceptflag='0' and tooperatedeptid like '"+deptid+"%'"+whereStr;
		}
		if(flag.equals("6")) {
			sqlStr = "select id from pnr_commontask_status where status=0 and taskstatus=2 and acceptflag='1' and tooperatedeptid like '"+deptid+"%'"+whereStr;	
		}
		if(flag.equals("7")) {
			sqlStr = "select max(operatetime) as maxoperatetime,id from pnr_commontask_status where status in(0,1) and acceptflag='0' and operatedeptid like '"+deptid+"%'"+whereStr+" group by id";
		}
		if(flag.equals("8")) {
			sqlStr = "select max(operatetime) as maxoperatetime,id from pnr_commontask_status where status in(0,1) and acceptflag='1' and operatedeptid like '"+deptid+"%'"+whereStr+" group by id";
		}	
		if(flag.equals("9")) {
			sqlStr = "select max(operatetime) as maxoperatetime,id from pnr_commontask_status where status in(0,1)  and taskstatus=5 and operatetype='95' and taskname='HoldTask' and acceptflag='0' and operatedeptid like '"+deptid+"%'"+whereStr+" group by id";
		}
		if(flag.equals("10")) {
			sqlStr = "select max(operatetime) as maxoperatetime,id from pnr_commontask_status where status in(0,1)  and taskstatus=5 and operatetype='95' and taskname='HoldTask' and acceptflag='1' and operatedeptid like '"+deptid+"%'"+whereStr+" group by id";
		}
		if(flag.equals("11")) {
			sqlStr = "select id from pnr_commontask_status where status=1 and taskstatus=5 and operatetype='95' and taskname='HoldTask' and scoretotal>95 and operatedeptid like '"+deptid+"%'"+whereStr;
		}
		if(flag.equals("12")) {
			sqlStr = "select id from pnr_commontask_status where status=1 and taskstatus=5 and operatetype='95' and taskname='HoldTask' and (scoretotal between 95 and 60) and operatedeptid like '"+deptid+"%'"+whereStr;
		}
		if(flag.equals("13")) {
			sqlStr = "select id from pnr_commontask_status where status=1 and taskstatus=5 and operatetype='95' and taskname='HoldTask' and scoretotal<60 and operatedeptid like '"+deptid+"%'"+whereStr;
		}
		String mainid = pnrCommonTaskStatisticDao.getMainIdStr(sqlStr);
		Map map = pnrCommonTaskStatisticDaoHibernate.querySheetIndexList(total, curPage, pageSize ,mainid);
		return map;
	}
	
	
}
