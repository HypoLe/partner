package com.boco.eoms.partner.contact.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.contact.mgr.ContactMainMgr;
import com.boco.eoms.partner.netresource.service.IEomsService;
	/**
 * <p>
 * Title: 分页查询
 * </p>
 * <p>
 * Description: 分页查询
 * </p>
 * <p>
 * Jul 31, 2012 3:45:07 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class SearchUtil<T>{
	/**
	 * 实体Class
	 */
	private Class<T> entryClass;
	/**
	 * 查询结果 别名
	 */
	private String alias;
	/**
	 *  实体结果集 sql语句
	 */
	private String entrySql;
	/**
	 * 总记录数 sql语句
	 */
	private String countSql;
	
	public SearchUtil() {
	}
	public SearchUtil(Class<T> entryClass,String alias,String entrySql,String countSql) {
		this.entryClass = entryClass;
		this.alias = alias;
		this.entrySql = entrySql;
		this.countSql = countSql;
	}
 /**
  * 
  * @param pageIndex 当前页数
  * @return
  */
	public  PageData<T> getDataList(int pageIndex,int pageSize){
		@SuppressWarnings("rawtypes")
			
			IEomsService eomsService = (IEomsService) ApplicationContextHolder.getInstance().getBean("eomsService");
		    Map map = eomsService.getDataList(this.entryClass, this.alias, this.entrySql, this.countSql, pageIndex, pageSize);
		 	
			int totalCount = StaticMethod.nullObject2int(map.get("totalCount"),0);
			PageData pageData= new PageData<T>(pageIndex, pageSize, totalCount);
			pageData.setTotalCount(totalCount);
			if(map.containsKey("list")){

				List list = (List)map.get("list");
				pageData.setList(list);
			}else{
				pageData.setList(new ArrayList());
			}
			return pageData;
	}
	
	public static List getDataBySql(final Class entryClass,final String alias,String sql){
		ContactMainMgr contactMainMgr=(ContactMainMgr)ApplicationContextHolder.getInstance().getBean("refcontactMainMgr");
		return contactMainMgr.getDataBySql(entryClass, alias, sql);
	}
}
