package com.boco.eoms.partner.deviceInspect.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.deviceManagement.common.pojo.CommonSearch;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public interface ITransLineDao extends IEomsDao {
	/**
	 * 产生巡检任务（和以前的IInspectPlanMgr中的generateInspectPlanRes一样）
	 * @param cycle 周期
	 * @param city  地市     
	 * @param cycleStartTime 周期开始时间
	 * @param cycleEndTime   周期结束时间      
	 * @param createTime     资源产生日期 
	 */
	public void generateInspectPlanRes(String cycle,String city,String cycleStartTime,
			String cycleEndTime,String createTime);
	
	/**
	 * 产生巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public void generateInspectPlanItem(String city,String createTime);
	
	/**
	 * 产生突发巡检任务
	 * @param ids
	 * @return
	 */
	public String generateBurstInspectPlanRes(String ids);
	
	/**
	 * 产生突发巡检任务
	 * @param ids
	 * @return
	 */
	public String generateBurstInspectPlanRes(String ids,String planStartTime,String planEndTime);
	
	/**
	 * 产生突发巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public void generateBurstInspectPlanItem(String createTime);
}
