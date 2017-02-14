package com.boco.eoms.partner.sheet.commontask.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.partner.sheet.commontask.dao.PnrCommonTaskStatisticDaoHibernate;
import com.boco.eoms.partner.sheet.commontask.model.PnrCommonTaskStatisticInfo;
import com.boco.eoms.sheet.base.dao.hibernate.BaseSheetDaoHibernate;

public class PnrCommonTaskStatisticDaoHibernateImpl extends BaseSheetDaoHibernate implements PnrCommonTaskStatisticDaoHibernate{
	
	/**
	 * 当只勾选了区域
	 */
	
	public Map querySheetIndexList(final Integer total, final Integer curPage, final Integer pageSize,final String mainid){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String queryStr = "select distinct id,sheetid,title,sendtime,sheetacceptlimit,sheetcompletelimit,status from PnrCommonTaskStatisticInfo where id in("+mainid+")";				
				System.out.println(queryStr);
				Query query = session.createQuery(queryStr);
				query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List<Object[]> list = query.list();			
				List result = new ArrayList();
				for(Object[] object : list ){    
					PnrCommonTaskStatisticInfo info = new PnrCommonTaskStatisticInfo();
					 info.setId((String)object[0]);      
					 info.setSheetid((String)object[1]);      
					 info.setTitle((String)object[2]);    
					 info.setSendtime((String)object[3]); 
					 info.setSheetacceptlimit((String)object[4]); 
					 info.setSheetcompletelimit((String)object[5]); 
					 info.setStatus((String)object[6]);  
					 result.add(info);
		         }    
				HashMap map = new HashMap();
				map.put("total", total);
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
	
}
