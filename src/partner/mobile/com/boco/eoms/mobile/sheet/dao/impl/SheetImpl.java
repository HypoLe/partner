package com.boco.eoms.mobile.sheet.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.mobile.sheet.dao.SheetDao;

public class SheetImpl extends BaseDaoHibernate implements SheetDao{
	@Deprecated
	public Map<String, Integer> getUnDoneCheckInfo(String userId) {
		HashMap<String, Integer> returnMap = new HashMap<String, Integer>();
		final String[] table = new String[]{" PnrFaultDealTask task, PnrFaultDealMain main ",
				"PnrComplaintTask task, PnrComplaintMain main ",
				"PnrCommonTaskTask task, PnrCommonTaskMain main "};
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String baseSql = " from  ";
				String whereSql = " where  (task.taskOwner='OCCU_USERID' OR task.taskOwner='OCCU_DEPTID' "
					+" OR task.taskOwner IN (	SELECT s.subRoleid FROM TawSystemUserRefRole s WHERE s.userid='OCCU_USERID') "
					+" ) AND (task.taskStatus=2 OR task.taskStatus=8 OR task.taskStatus=1) AND "
					+" task.taskDisplayName<>'草稿' AND main.id=task.sheetKey AND main.deleted<>'1' AND main.status=0";
				Query query;
				for(int i = 0 ;i < table.length;i++){
					query = session.createQuery(baseSql+table[0]+whereSql);
					System.out.println("Sql  "+ baseSql+table[0]+whereSql);
					List  returnTempList = query.list();
				}
				return null;
			}
		});
		return returnMap;
	}

}
