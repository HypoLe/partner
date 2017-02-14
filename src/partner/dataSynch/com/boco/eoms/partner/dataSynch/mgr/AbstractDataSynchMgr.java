package com.boco.eoms.partner.dataSynch.mgr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import com.boco.eoms.base.util.InterfaceDataMonitor;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;
import com.boco.eoms.partner.dataSynch.model.CheckFile;
import com.boco.eoms.partner.dataSynch.rule.AbstractDataMappingRule;
import com.boco.eoms.partner.dataSynch.util.DataSynchConfig;
import com.boco.eoms.partner.dataSynch.util.DataSynchConstant;
import com.boco.eoms.partner.dataSynch.util.DataSynchJdbcUtil;
import com.boco.eoms.partner.dataSynch.util.ZipFileUnpackUtils;

/** 
 * Description: 数据同步
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Mar 28, 2012 2:48:26 PM
 */
public abstract class AbstractDataSynchMgr {
	public DataSynchConfig dataSynchConfig;
	public AbstractDataMappingRule dataMappingRule;
	public AbstractDataSynchDaoJdbc dataDaoJdbcImpl;
	public DataSynchJdbcUtil dataSynchJdbcUtil;
	public String dataType;  //数据类型
	public String deviceType="";//设备类型
	private String synchType = "";//同步类型（用于excel同步）
	private String dataFileDir = "";//数据文件目录（用于excel同步）

	/**
	 * 数据同步
	 * @param checkFileList 
	 */
	public void dataSynch(List<CheckFile> checkFileList){
		//创建dictMap
		dataMappingRule.buildDictMap();
		
		Connection conn = null;
		InputStream dataFileInputStream = null;
		String dataFileName = ""; //zip数据文件名
		try{
			conn = dataSynchJdbcUtil.getCon();
			conn.setAutoCommit(false);
			
			//创建临时表
			createTempTable(conn);
			
			//解析数据文件并导入数据
			long parseStartTime = System.currentTimeMillis();   //获取开始时间	
			int totalCount = 0;
			for (CheckFile checkFile : checkFileList) {
				dataFileName = checkFile.getDataFileName();
				if("excel".equals(this.getSynchType())) {
					File dataFile = new File(this.getDataFileDir(),dataFileName);
					dataFileInputStream = ZipFileUnpackUtils.getXmlFileInputStreamsInZipFile(dataFile.getAbsolutePath());
				} else {
					dataFileInputStream = ZipFileUnpackUtils.getXmlFileInputStreamsInZipFile( 
							dataSynchConfig.getFilePath()+ dataFileName);
				}
				
				int perCount = parseDataFile(dataFileInputStream,conn,dataFileName);
				totalCount += perCount;
				//验证核查文件和数据文件
				if(!validateFile(checkFile,perCount)){
					throw new Exception("核查文件和数据文件内容不匹配，数据文件名：" + dataFileName);
				}
			}
			
			long endTime_1 = System.currentTimeMillis();
			System.out.println("endTime_1-parseStartTime="+(endTime_1-parseStartTime));
			
			
			//移除临时表无效数据
			removeInvalidData(conn);
			
			//保存同步变更数据到备份表
			saveDataToBackTable(conn);
			long endTime_2 = System.currentTimeMillis();
			System.out.println("endTime_2-endTime_1="+(endTime_2-endTime_1));
			//删除主表数据
			deleteMainTableData(conn);
			long endTime_3 = System.currentTimeMillis();
			System.out.println("endTime_3-endTime_2="+(endTime_3-endTime_2));
			//复制临时表数据到主表
			int totalCountInTmp = copyTempTableToMainTable(conn);
			long endTime_4 = System.currentTimeMillis();
			System.out.println("endTime_4-endTime_3="+(endTime_4-endTime_3));
			
			conn.commit();
			
			long parseEndTime=System.currentTimeMillis(); //获取结束时间
			
			
			String msg = "解析并保存数据<"+dataType+"-"+deviceType+">完成，总数据量："+totalCount
				+"，异常数据量"+(totalCount-totalCountInTmp)+",共耗时:"+(parseEndTime-parseStartTime)+"ms";
System.out.println(msg);
			InterfaceDataMonitor monitor = new InterfaceDataMonitor();
			Map<String,Object> monitorMap = new HashMap<String,Object>();
			monitorMap.put("sheetkey", dataFileName);
			monitorMap.put("serCaller","over");//标明数据同步正常结束
			monitor.saveMonitorBySeq(monitorMap, msg, "综合资源调用属地化结束", "dataSynch");
		}catch(Exception e){
			e.printStackTrace();
			//创建接口日志
			InterfaceDataMonitor monitor = new InterfaceDataMonitor();
			Map<String,Object> monitorMap = new HashMap<String,Object>();
			monitorMap.put("sheetkey", dataFileName);
			monitor.saveMonitorBySeq(monitorMap, e.getMessage(),  "网络资源数据同步出现异常", "dataSynch");
			BocoLog.error(this.getClass(), e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				BocoLog.warn(this.getClass(), e1.getMessage());
			}
		}finally{
			try {
				dataFileInputStream.close();
				if(CommonSqlHelper.isInformixDialect()) {
					//删除临时表
					dropTempTable(conn);
				}
			} catch (IOException e) {
				e.printStackTrace();
				BocoLog.warn(this.getClass(), e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
			dataSynchJdbcUtil.closeConn(conn);
		}
	}

	/**
	 * 复制临时表数据到主表
	 * @param conn
	 * @return
	 */
	public int copyTempTableToMainTable(Connection conn) throws Exception {
		return dataDaoJdbcImpl.copyTempTableToMainTable(conn);
	}

	/**
	 * 删除主表数据
	 * @param conn
	 */
	public void deleteMainTableData(Connection conn) throws Exception {
		dataDaoJdbcImpl.deleteMainTableData(conn);
	}


	/**
	 * 解析数据文件
	 * @param dataFileInputStream 数据文件流
	 * @param dataFileName 数据文件名，如：SDH_jk_group20120515103709006.zip 
	 * @return 解析的总数据条数
	 */
	public int parseDataFile(InputStream dataFileInputStream,Connection conn,String dataFileName)throws Exception{
//		String sql = dataDaoJdbcImpl.getBatchInsertSql("his");
		String sql = dataDaoJdbcImpl.getBatchInsertSql("tmp");
		
		PreparedStatement ps = null;
		int totalCount = 0; //记录总条数
		int counter = 0; //计数器
		
		try {
			ps = conn.prepareStatement(sql);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(dataFileInputStream);
			
			boolean endFlag = false;
			String cuid = "";
			String fieldName = "";
			String fieldValue = "";
			Map<String,Object> dataMap = null;
			while (reader.hasNext()) {
				int event = reader.getEventType();
				switch (event) {
					case XMLStreamConstants.START_ELEMENT:
						String localName = reader.getLocalName();
//						if("attributes".equals(localName)){
						//西安
						if("class".equals(localName)){
							dataMap = new HashMap<String,Object>();
						}else if("resourceId".equals(localName)){
							cuid = reader.getElementText();
							dataMap.put("id", cuid);
						}else if("attribute".equals(localName)){
							for (int i = 0; i < reader.getAttributeCount(); i++) {
								if("name".equals(reader.getAttributeLocalName(i))){
									fieldName = reader.getAttributeValue(i);
								}else if("value".equals(reader.getAttributeLocalName(i))){
									fieldValue = reader.getAttributeValue(i);
								}
							}
							dataMap = dataMappingRule.mapping(fieldName, fieldValue,cuid, 
									dataMap,dataFileName,dataType+deviceType);
//							dataMap.put(fieldName.toLowerCase(), fieldValue);
						}
						break;
					case XMLStreamConstants.END_ELEMENT:
//						if("attributes".equals(reader.getLocalName())){
						//西安
						if("class".equals(reader.getLocalName())){
							//非异常数据
							if(!"Y".equals(dataMap.get(DataSynchConstant.EXCEPTION_KEY))){
								//添加额外数据
								dataMap.put("data_synch_time", StaticMethod.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
								ps = dataDaoJdbcImpl.addPsBatch(ps, dataMap);
							}
							totalCount++;
							counter++;
						}
						if("resource-data".equals(reader.getLocalName())){
							endFlag = true;
						}
				}
				
				if(counter == dataSynchConfig.getBatchListSize() || endFlag){
					dataSynchJdbcUtil.batchSaveData(conn,ps);
					if(dataSynchConfig.isTmpTableCountQuery()){
						System.out.println("临时表数量："+dataDaoJdbcImpl.getTotalCountInTmp(conn));
					}
					counter = 0;
				}
				if (reader.hasNext()) {
					event = reader.next();
				} else {
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps != null){
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				BocoLog.warn(this.getClass(), e.getMessage());
			}
		}
System.out.println(dataFileName + "解析完成后的数据量:" + totalCount);
		return totalCount;
	}
	
	/**
	 * 保存同步变更数据到备份表
	 * @param conn
	 * @throws Exception
	 */
	public void saveDataToBackTable(Connection conn) throws Exception{
		String pkAddValue = getUUID();
		String pkDeleteValue = getUUID();
		
		//将临时表数据插入主表,增量插入
		int insertCount = dataDaoJdbcImpl.insertAddDataToBackTable(conn,pkAddValue);
		//删除主表中id不在昨时表的数据
		int deleteCount = dataDaoJdbcImpl.insertDeleteDataToBackTable(conn,pkDeleteValue);
		
		saveIrmsDatasynchResult(conn,pkAddValue,"add",insertCount);
		saveIrmsDatasynchResult(conn,pkDeleteValue,"delete",deleteCount);
	}
	
	/**
	 * 保存同步变更结果到同步步结果表
	 * @param conn
	 * @param pkValue
	 * @param type
	 * @param count
	 * @throws Exception
	 */
	public void saveIrmsDatasynchResult(Connection conn,String pkValue,String type,int count) throws Exception{
		String table = this.dataDaoJdbcImpl.getTableName();
		String sql = "INSERT INTO irms_datasynch_result(id, datasynch_type, datasynch_count, datasynch_time,datasynch_model) "+
					 "VALUES(?, ?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,pkValue);
		ps.setString(2, type);
		ps.setInt(3, count);
		ps.setString(4, StaticMethod.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
		ps.setString(5, table);
			
		ps.executeUpdate();
	}
	
	public static String getUUID() {
		String str = UUID.randomUUID().toString();
		String uuid = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
		return uuid;
	}
	
	
	/**
	 * 验证核查文件和数据文件，验证不通过则发送短信
	 * @return
	 */
	public boolean validateFile(CheckFile checkFile,int totalCount){
		boolean isValidatePass = true;
		
		if(totalCount != checkFile.getDataFileRecordCount()){
			isValidatePass = false;
			sendSMS("核查文件内容与数据文件内容不匹配");
		}
		return isValidatePass;
	}
	
	
	/**
	 * 创建临时表
	 * @param conn
	 * @throws Exception 
	 */
	public void createTempTable(Connection conn) throws Exception {
		dataDaoJdbcImpl.createTempTable(conn);
	}
	/**
	 * 删除临时表
	 * @param conn
	 * @throws Exception 
	 */
	public void dropTempTable(Connection conn) throws Exception {
		dataDaoJdbcImpl.dropTempTable(conn);
	}
	
	
	/**
	 * 移除临时表无效数据
	 * @param conn
	 * @throws Exception
	 */
	public void removeInvalidData(Connection conn) throws Exception{
		dataDaoJdbcImpl.deleteRepeatIdInTmp(conn, dataType+deviceType);
		dataDaoJdbcImpl.deleteInvalidDataInTmpSelf(conn, dataType+deviceType);
	}
	
	/**
	 * 发送短信提醒
	 */
	public void sendSMS(String msg){
		System.out.println(msg);
	}
	
	public DataSynchConfig getDataSynchConfig() {
		return dataSynchConfig;
	}

	public void setDataSynchConfig(DataSynchConfig dataSynchConfig) {
		this.dataSynchConfig = dataSynchConfig;
	}

	public AbstractDataMappingRule getDataMappingRule() {
		return dataMappingRule;
	}

	public void setDataMappingRule(AbstractDataMappingRule dataMappingRule) {
		this.dataMappingRule = dataMappingRule;
	}

	public DataSynchJdbcUtil getDataSynchJdbcUtil() {
		return dataSynchJdbcUtil;
	}

	public void setDataSynchJdbcUtil(DataSynchJdbcUtil dataSynchJdbcUtil) {
		this.dataSynchJdbcUtil = dataSynchJdbcUtil;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public AbstractDataSynchDaoJdbc getDataDaoJdbcImpl() {
		return dataDaoJdbcImpl;
	}

	public void setDataDaoJdbcImpl(AbstractDataSynchDaoJdbc dataDaoJdbcImpl) {
		this.dataDaoJdbcImpl = dataDaoJdbcImpl;
	}
	public String getSynchType() {
		return synchType;
	}

	public void setSynchType(String synchType) {
		this.synchType = synchType;
	}

	public String getDataFileDir() {
		return dataFileDir;
	}

	public void setDataFileDir(String dataFileDir) {
		this.dataFileDir = dataFileDir;
	}
	
}
