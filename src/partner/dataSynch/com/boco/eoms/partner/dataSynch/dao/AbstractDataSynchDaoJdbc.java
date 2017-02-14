package com.boco.eoms.partner.dataSynch.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.dataSynch.model.SynchExceptionRecord;
import com.boco.eoms.partner.dataSynch.util.DataSynchJdbcUtil;
import com.google.common.base.Joiner;

/** 
 * Description: 数据抽象同步JDBC基类
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 26, 2012 11:43:41 AM
 */
public abstract class AbstractDataSynchDaoJdbc extends JdbcDaoSupport{
	public String tmpTableName;  //临时表表名
	public String hisTableName;  //历史表表名
	public String tableName;     //正式表表名
	public Map<String,String> selfColumns; //私有字段
	public DataSynchJdbcUtil dataSynchJdbcUtil;
	public ISynchExceptionRecordDao synchExceptionRecordDao;
	
	/**
	 * 获取批量插入临时表SQL
	 * @return
	 */
	public abstract String getBatchInsertSql(String table);
	
	/**
	 * 将数据添加到PreparedStatement中
	 * @param ps
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public abstract PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception;
	
	/**
	 * 比较临时表与正式表并保存以及更新数据
	 */
	public void mergeSaveData(Connection conn) throws Exception{};
	
	/**
	 * 比较临时表与设备主表并保存以及更新数据(设备专用)
	 * @param specialityNo 设备类型树中专业NodeId
	 * @param conn
	 * @throws Exception
	 */
	public void mergeSaveDataToMainDev(String specialityNo, Connection conn)throws Exception {
		
	}
	
	/**
	 * 更新未同步次数
	 */
	public void updateNumberOfUnsysnc(Connection conn) throws Exception{};
	
	/**
	 * 更新设备主表未同步次数(设备专用)
	 * @param conn
	 * @param deviceTypeNo 设备类型节点ID
	 * @throws Exception
	 */
	public  void updateNumberOfUnsysncToMainDev(Connection conn,String deviceTypeNo) throws Exception{
		String sql = "update pnr_inspect_device set NUMBER_OF_UNSYNC=NUMBER_OF_UNSYNC+1 " +
				"where device_type='"+deviceTypeNo+"' and datasource=1 and CREAT_TIME != trunc(sysdate,'dd')";
System.out.println("updateNumberOfUnsysncToMainDev:deviceTypeNo："+deviceTypeNo+"，sql:"+sql);
		dataSynchJdbcUtil.saveOrUpdate(sql, conn);
	}
	
	/**
	 * 删除临时表里ID重复的数据
	 * @param conn
	 * @param dataType
	 * @throws Exception
	 */
	public void deleteRepeatIdInTmp(Connection conn,String dataType) throws Exception{
		String selectSql = "select id from " + tmpTableName + " group by id having count(id) > 1";
		List<Object> resultList = dataSynchJdbcUtil.findOneColumnList(conn, selectSql);
		if(!resultList.isEmpty()){
			String repeatId = Joiner.on(',').join(resultList.toArray());
			String warn = "CUID重复：";
			if(repeatId.length() > 1500){
				repeatId = repeatId.substring(0, 1500);
				warn = "大量CUID重复，未完全记录，部分CUID如下：";
			}
			SynchExceptionRecord record = new SynchExceptionRecord();
			record.setExceptionField("CUID重复");
			record.setExceptionReason(warn + repeatId);
			record.setCreateTime(StaticMethod.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
			record.setDataType(dataType);
			record.setExceptionFileName("");
			synchExceptionRecordDao.saveObject(record);
			
			String deleteSql = "delete from " + tmpTableName + " where id in (" + selectSql + ")";
System.out.println("deleteSql:" + deleteSql);		
			dataSynchJdbcUtil.saveOrUpdate(deleteSql, conn);
		}
	}
	

	/**
	 * 创建临时表
	 * @param conn
	 * @throws Exception
	 */
	public void createTempTable(Connection conn) throws Exception{
		String sql = "";
		if(CommonSqlHelper.isOracleDialect()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select count(*) as tableCount from all_tables where TABLE_NAME = upper('"+tmpTableName+"')");
			rs.next();
			int tableCount = rs.getInt("tableCount");
			rs.close();
			stmt.close();
			if(tableCount<1) {
				sql = "Create Global Temporary Table "+tmpTableName+" ON COMMIT DELETE ROWS as select * from "+tableName+" where rownum=0";
				dataSynchJdbcUtil.saveOrUpdate(sql, conn);
			}
		} else {
			sql = "select * from "+tableName+" where 1<>1 into temp "+tmpTableName+" with no log";
			dataSynchJdbcUtil.saveOrUpdate(sql, conn);
		}
	}
	/**
	 * 删除临时表
	 * @param conn
	 */
	public void dropTempTable(Connection conn) throws Exception{
		String sql = "drop table "+tmpTableName;
		dataSynchJdbcUtil.saveOrUpdate(sql, conn);
	}
	/**
	 * 插入新增数据到备分表，并返回新增条数
	 * @param conn
	 * @param irms_datasynch_result_id 
	 * @throws Exception
	 */
	public int insertAddDataToBackTable(Connection conn, String irms_datasynch_result_id)throws Exception {
		String sql = "";
//		if(CommonSqlHelper.isOracleDialect()) {
//			sql = "insert into "+tableName+"_cr select t.*,'"+irms_datasynch_result_id+"' from "+tmpTableName+" t where id not in(select id from "+tableName+")";
//		} else {
//			sql = "insert into "+tableName+"_cr select *,'"+irms_datasynch_result_id+"' from "+tmpTableName+" where id not in(select id from "+tableName+")";
//		}
		
		String coloumnNames_1 = dataSynchJdbcUtil.getTableColumnNames(conn, tableName,"");
		String coloumnNames_2 = dataSynchJdbcUtil.getTableColumnNames(conn, tableName,"t");
		
		if(CommonSqlHelper.isOracleDialect()) {
			sql = "insert into "+tableName+"_cr ("+coloumnNames_1+",datasynch_result_id) select "+coloumnNames_2+",'"+irms_datasynch_result_id+"' from "+tmpTableName+" t where id not in(select id from "+tableName+")";
		} else {
			sql = "insert into "+tableName+"_cr ("+coloumnNames_1+",datasynch_result_id) select "+coloumnNames_1+",'"+irms_datasynch_result_id+"' from "+tmpTableName+" where id not in(select id from "+tableName+")";
		}
		int count = dataSynchJdbcUtil.saveOrUpdate(sql, conn);
		return count;
	}
	
	/**
	 * 插入删除的数据到备分表，并返回删除条数
	 * @param conn
	 * @throws Exception
	 */
	public int insertDeleteDataToBackTable(Connection conn, String irms_datasynch_result_id)throws Exception {
		String sql = "";
		String coloumnNames_1 = dataSynchJdbcUtil.getTableColumnNames(conn, tableName,"");
		String coloumnNames_2 = dataSynchJdbcUtil.getTableColumnNames(conn, tableName,"t");
		
//		if(CommonSqlHelper.isOracleDialect()) {
//			sql = "insert into "+tableName+"_cr select t.*,'"+irms_datasynch_result_id+"' from "+tableName+" t where id not in(select id from "+tmpTableName+")";
//		} else {
//			sql = "insert into "+tableName+"_cr select *,'"+irms_datasynch_result_id+"' from "+tableName+" where id not in(select id from "+tmpTableName+")";
//		}
		if(CommonSqlHelper.isOracleDialect()) {
			sql = "insert into "+tableName+"_cr ("+coloumnNames_1+",datasynch_result_id) select "+coloumnNames_2+",'"+irms_datasynch_result_id+"' from "+tableName+" t where id not in(select id from "+tmpTableName+")";
		} else {
			sql = "insert into "+tableName+"_cr ("+coloumnNames_1+",datasynch_result_id) select "+coloumnNames_1+",'"+irms_datasynch_result_id+"' from "+tableName+" where id not in(select id from "+tmpTableName+")";
		}
		int count = dataSynchJdbcUtil.saveOrUpdate(sql, conn);
		return count;
	}
	/**
	 * 查询新增数据条数
	 * @param conn
	 * @throws Exception
	 */
	public int findNewAddDataCount(Connection conn)throws Exception {
		String sql = "select count(*) from "+tmpTableName+" where id not in(select id from "+tableName+")";
		List list = dataSynchJdbcUtil.findOneColumnList(conn,sql);
		BigDecimal count = (BigDecimal)list.get(0);
		return count.intValue();
	}
	
	/**
	 * 查询删除数据条数
	 * @param conn
	 * @throws Exception
	 */
	public int findDeleteDataCount(Connection conn)throws Exception {
		String sql = "select count(*) from "+tableName+" where id not in(select id from "+tmpTableName+")";
		List list = dataSynchJdbcUtil.findOneColumnList(conn,sql);
		BigDecimal count = (BigDecimal)list.get(0);
		return count.intValue();
	}
	/**
	 * 查询新增数据
	 * @param conn
	 * @throws Exception
	 */
	public List findNewAddData(Connection conn)throws Exception {
		
		String sql = "select id from "+tmpTableName+" where id not in(select id from "+tableName+")";
		List list = dataSynchJdbcUtil.findOneColumnList(conn,sql);
		return list;
	}
	
	/**
	 * 查询删除数据
	 * @param conn
	 * @throws Exception
	 */
	public List findDeleteData(Connection conn)throws Exception {
		String sql = "select id from "+tableName+" where id not in(select id from "+tmpTableName+")";
		List list = dataSynchJdbcUtil.findOneColumnList(conn,sql);
		return list;
	}
	/**
	 * 删除主表数据
	 * @param conn
	 * @throws Exception
	 */
	public void deleteMainTableData(Connection conn)throws Exception {
//		String delSql = "delete from "+tableName;
//		int delCount = dataSynchJdbcUtil.saveOrUpdate(delSql,conn);
		
		Connection truncateConn = dataSynchJdbcUtil.getCon();
		String delSql = "truncate table "+tableName;
		int delCount = dataSynchJdbcUtil.saveOrUpdate(delSql,truncateConn);
		dataSynchJdbcUtil.closeConn(truncateConn);
	}
	/**
	 * 复制临时表数据到主表
	 * @param conn
	 * @throws Exception
	 */
	public int copyTempTableToMainTable(Connection conn) throws Exception {
		String sql = "insert into "+tableName+" select * from "+tmpTableName;
		int count = dataSynchJdbcUtil.saveOrUpdate(sql, conn);
		return count;
	}
	/**
	 * 删除临时表无效的私有数据
	 * @param conn
	 * @param dataType
	 */
	public void deleteInvalidDataInTmpSelf(Connection conn,String dataType) throws Exception{
	}
	
	/**
	 * 查询临时表总条数
	 * @param conn
	 * @throws Exception
	 */
	public int getTotalCountInTmp(Connection conn) throws Exception {
		String sql = "select count(*) from "+ tmpTableName;
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet set = ps.executeQuery();
		int totalCount = 0;
		while(set.next()){
			totalCount = set.getInt(1);
			break;
		}
		return totalCount;
	}

	public String getTmpTableName() {
		return tmpTableName;
	}

	public void setTmpTableName(String tmpTableName) {
		this.tmpTableName = tmpTableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, String> getSelfColumns() {
		return selfColumns;
	}

	public void setSelfColumns(Map<String, String> selfColumns) {
		this.selfColumns = selfColumns;
	}

	public DataSynchJdbcUtil getDataSynchJdbcUtil() {
		return dataSynchJdbcUtil;
	}

	public void setDataSynchJdbcUtil(DataSynchJdbcUtil dataSynchJdbcUtil) {
		this.dataSynchJdbcUtil = dataSynchJdbcUtil;
	}

	public ISynchExceptionRecordDao getSynchExceptionRecordDao() {
		return synchExceptionRecordDao;
	}

	public void setSynchExceptionRecordDao(
			ISynchExceptionRecordDao synchExceptionRecordDao) {
		this.synchExceptionRecordDao = synchExceptionRecordDao;
	}

	public String getHisTableName() {
		return hisTableName;
	}

	public void setHisTableName(String hisTableName) {
		this.hisTableName = hisTableName;
	}
}
