package com.boco.eoms.partner.netresource.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.dataSynch.util.DataSynchJdbcUtil;
import com.boco.eoms.partner.netresource.dao.IPnrNetResourceDaoJdbc;
import com.boco.eoms.partner.netresource.util.PnrNetResourceConfig;
import com.boco.eoms.partner.netresource.util.PnrNetResourceUtil;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 22, 2013 1:51:52 AM
 */
public class PnrNetResourceDaoJdbcImpl extends JdbcDaoSupport implements IPnrNetResourceDaoJdbc {
	private DataSynchJdbcUtil dataSynchJdbcUtil;
	
	/**
	 * 同步网络资源到巡检资源
	 * @param synchResultId irms_datasynch_result表id
	 * @param model 网络资源类型
	 */
	@SuppressWarnings("unchecked")
	public void synchNetResToResConfig(String synchResultId,String model) {
		long start = System.currentTimeMillis(); //获取开始时间
		Connection conn = null;
		Statement stmt = null;
		String now = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
		String tmpTableName = ""; //临时表名
		
		//根据model名解析配置文件
		PnrNetResourceConfig config = PnrNetResourceUtil.parsePnrNetResourceConfig(model);
		String specialty = config.getSpecialty();
		String resType = config.getResType();
		String sql = config.getWholeSql();
System.out.println(model+"配置的sql:" + sql);		
		if(StringUtils.isEmpty(specialty) || StringUtils.isEmpty(resType) ||StringUtils.isEmpty(sql) ||StringUtils.isEmpty(sql.trim())){
			String msg = "同步失败，"+model+"配置错误或者没有配置，请正确配置pnrnetresource-config.xml";
			System.out.println(msg);
			throw new RuntimeException(msg);
		}
		
		try{
			conn = dataSynchJdbcUtil.getCon();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			//informix动态创建临时表 oracle需要提前建立临时表
			if(CommonSqlHelper.isInformixDialect()){
				tmpTableName = "tmp_" + model; //临时表名
				String tempTableSql = sql + " into temp "+tmpTableName+" with no log";				
				dataSynchJdbcUtil.saveOrUpdate(tempTableSql, conn);
			}else if(CommonSqlHelper.isOracleDialect()){
				tmpTableName = "tmp_irms_datasynch";
				String tempTableSql = "insert into " + tmpTableName + sql;
				dataSynchJdbcUtil.saveOrUpdate(tempTableSql, conn);
			}
			
			String countSql = "select count(*) from "+tmpTableName;
			int totalCount = ((java.math.BigDecimal)dataSynchJdbcUtil.getOneValue(conn, countSql)).intValue();
			if(totalCount == 0){
				throw new RuntimeException("满足同步条件的数据条数为0，请查看相关表的数据");
			}
			
			String selectTmpSql = "select id,resname,specialty,restype,longitude,latitude,areaname from "+tmpTableName;
			int perCount = 200;
			int quo = totalCount/perCount; //商数
			int remainder = totalCount%perCount; //余数
			int times = 0; //循环次数
				if((quo == 0 && remainder!=0) || quo > 0 ){
					times = remainder==0 ? quo : quo +1;
				}
				for(int i=0;i<times;i++){
					int offset = perCount * i ;
//System.out.println(offset+","+(offset+perCount));
				String pageSql = CommonSqlHelper.formatPageSql(selectTmpSql, offset, perCount);
				List<Map<String,Object>> list = dataSynchJdbcUtil.queryForList(conn,pageSql);
				String city = "";
				String country = "";
				for (Map map : list) {
					String region = map.get("areaname").toString();
					if(region.length()==4){
						city = region;
						country = region;
					}else if(region.length()==6){
						city = region.substring(0,4);
						country = region;
					}
					String id = StaticMethod.nullObject2String(map.get("id"));
					String name = StaticMethod.nullObject2String(map.get("resname"));
					String longitude = StaticMethod.nullObject2String(map.get("longitude"));
					String latitude = StaticMethod.nullObject2String(map.get("latitude"));
					if("auto".equalsIgnoreCase(resType)){
						resType = StaticMethod.nullObject2String(map.get("restype"));
					}
					String insertSql = "insert into pnr_res_config (id,specialty,res_Type,res_name,res_longitude,res_latitude,city,country,create_time)" +
							" values('"+id+"','"+specialty+"','"+resType+"','"+name+"',"+longitude+","+latitude+",'"+city+"','"+country+"','"+now+"')";
					stmt.addBatch(insertSql);
				}
				stmt.executeBatch();
			}
			String updateSql = "update irms_datasynch_result set datasynch_flag=1 where id='"+synchResultId+"'";
			dataSynchJdbcUtil.saveOrUpdate(updateSql, conn);
			conn.commit();
			long end=System.currentTimeMillis(); //获取结束时间
			System.out.println(totalCount+"条数据同步完成，共耗时："+(end-start)+"毫秒");
		}catch(Exception e){
			System.out.println(model+"网络资源同步到巡检资源发生错误");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			throw new RuntimeException(e);
		}finally{
			//删除临时表
			String delTmpTable = "drop table "+tmpTableName;
			try {
				dataSynchJdbcUtil.saveOrUpdate(delTmpTable, conn);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}finally{
				if(stmt != null){
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				dataSynchJdbcUtil.closeConn(conn);
			}
		}
	}
	
	/**
	 * 网络资源同步统计
	 */
	@SuppressWarnings("unchecked")
	public List netResourceCount(Integer curPage, Integer pageSize) {
		String dateTime1 = CommonSqlHelper.formatDateTime("datasynch_time", true);
		String dateTime2 = CommonSqlHelper.formatDateTime("d.datasynch_time", true);
		
		String selectSql = "select a.datasynch_count addcount,b.datasynch_count deletecount,a.times times,a.datasynch_model datasynch_model,a.datasynch_flag flag ,a.id id ";
		String fromSql = " from ( " +
								"select * from irms_datasynch_result d, "+
									"( select max("+dateTime1+") times,datasynch_type,datasynch_model from irms_datasynch_result   "+
											"where datasynch_type='add' "+
											"group by  datasynch_type,datasynch_model "+
									") t where "+dateTime2+"=t.times and d.datasynch_type = t.datasynch_type and d.datasynch_model = t.datasynch_model  "+
								") a left join irms_datasynch_result b on a.datasynch_time = b.datasynch_time and a.datasynch_model=b.datasynch_model and b.datasynch_type='delete'";
		String sql =selectSql + fromSql;
		sql =  CommonSqlHelper.formatPageSql(sql, curPage, pageSize);
		String sql2 = " select count(*) " + fromSql;
		int count = this.getJdbcTemplate().queryForInt(sql2);
		List list = new ArrayList();
		list.add(count);
		list.add(this.getJdbcTemplate().queryForList(sql));
		return list;
	}
	
	
	
	/**
	 * 查询数据同步结果
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getDatasynchResult(String id){
		String sql = "select * from irms_datasynch_result where id='"+id+"'";
		return this.getJdbcTemplate().queryForMap(sql);
	}

	public DataSynchJdbcUtil getDataSynchJdbcUtil() {
		return dataSynchJdbcUtil;
	}

	public void setDataSynchJdbcUtil(DataSynchJdbcUtil dataSynchJdbcUtil) {
		this.dataSynchJdbcUtil = dataSynchJdbcUtil;
	}
	
}
